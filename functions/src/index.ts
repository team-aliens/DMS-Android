import {initializeApp} from "firebase-admin/app";
import {getFunctions} from "firebase-admin/functions";
import {getFirestore} from "firebase-admin/firestore";
import {createHmac, timingSafeEqual} from "node:crypto";
import {GoogleAuth} from "google-auth-library";
import {
  onNewFatalIssuePublished,
  onRegressionAlertPublished,
  onVelocityAlertPublished,
} from "firebase-functions/v2/alerts/crashlytics";
import {logger} from "firebase-functions";
import {onRequest} from "firebase-functions/v2/https";
import {onTaskDispatched} from "firebase-functions/v2/tasks";
import {defineString, defineSecret} from "firebase-functions/params";

initializeApp();

const db = getFirestore();

const GEMINI_API_KEY = defineSecret("GEMINI_API_KEY");
const GITHUB_TOKEN = defineSecret("GITHUB_TOKEN");
const GITHUB_REPO = defineString("GITHUB_REPO");
const ANDROID_PACKAGE_NAME = defineString("ANDROID_PACKAGE_NAME");
const SLACK_BOT_TOKEN = defineSecret("SLACK_BOT_TOKEN");
const SLACK_CHANNEL_ID = defineString("SLACK_CHANNEL_ID");
const SLACK_SIGNING_SECRET = defineSecret("SLACK_SIGNING_SECRET");
const AUTO_FIX_RUNNER_URL = defineString("AUTO_FIX_RUNNER_URL");
const AUTO_FIX_RUNNER_TOKEN = defineSecret("AUTO_FIX_RUNNER_TOKEN");

type JsonObject = Record<string, unknown>;
type AlertKind = "fatal" | "regression" | "velocity";
type ProcessingStatus =
  | "issue_created"
  | "waiting_for_slack_action"
  | "auto_fix_requested"
  | "fix_in_progress"
  | "pr_created"
  | "failed";

const FUNCTIONS_REGION = "us-central1";
const AUTO_FIX_ACTION_ID = "attempt_auto_fix";

interface CrashEventData {
  kind: string;
  issueId: string;
  title: string;
  platform: string;
  appId: string;
  bundleId: string;
  version: string;
  buildVersion: string;
  affectedUsers: number;
  consoleUri: string;
  stackTrace: string;
  errorType: string;
  errorMessage: string;
  crashClass: string;
  crashMethod: string;
  topStackFrames: string[];
  occurredAt: string;
}

interface GithubIssueDraft {
  summary: string;
  severity: string;
  suspectedCause: string;
  reproductionHints: string;
  nextActions: string[];
  issue_title: string;
  issue_body: string;
  labels: string[];
}

interface GithubIssueMetadata {
  htmlUrl: string;
  number: number;
}

interface IncidentAnalysis {
  summary: string;
  severity: string;
  suspectedCause: string;
  reproductionHints?: string;
  nextActions?: string[];
  issueTitle: string;
  issueBody: string;
}

interface GithubIssueAnalysisDraft {
  summary: string;
  severity: string;
  suspectedCause: string;
  reproductionHints: string;
  nextActions: string[];
  issue_title: string;
  labels: string[];
}

interface IncidentGithub {
  issueCreated: boolean;
  issueNumber: number | null;
  issueUrl: string;
}

interface IncidentSlack {
  sent: boolean;
  channelId: string;
  messageTs: string;
  lastDuplicateNotifiedAt: string;
}

interface IncidentPr {
  created: boolean;
  url: string;
  branch: string;
  summary: string;
}

interface SlackMessageMetadata {
  channelId: string;
  messageTs: string;
}

interface SlackInteractionPayload {
  type: string;
  response_url: string;
  user: JsonObject;
  channel: JsonObject;
  container: JsonObject;
  actions: unknown[];
}

interface AutoFixTaskPayload {
  dedupeKey: string;
  issueUrl: string;
  issueNumber: number;
  title: string;
}

interface AgentRunnerRequest {
  dedupeKey: string;
  repo: string;
  issueNumber: number;
  issueUrl: string;
  title: string;
  platform: string;
  appVersion: string;
  buildVersion: string;
  errorType: string;
  errorMessage: string;
  crashClass: string;
  crashMethod: string;
  analysis: IncidentAnalysis;
}

interface AgentRunnerResult {
  ok: boolean;
  branch: string;
  prUrl: string;
  summary: string;
  failedStep: string;
  error: string;
}

interface SlackThreadMetadata {
  channelId: string;
  threadTs: string;
}

let auth: GoogleAuth | null = null;

/**
 * 입력값이 객체처럼 보이면 일반 객체로 반환한다.
 *
 * @param {unknown} value 확인할 입력값.
 * @return {JsonObject} 객체 형태의 값 또는 빈 객체.
 */
function asObject(value: unknown): JsonObject {
  if (value && typeof value === "object") {
    return value as JsonObject;
  }

  return {};
}

/**
 * 입력값들 중 첫 번째 비어 있지 않은 문자열을 반환한다.
 *
 * @param {...unknown} values 문자열 후보 값들.
 * @return {string} trim 처리된 첫 문자열 또는 빈 문자열.
 */
function getString(...values: unknown[]): string {
  for (const value of values) {
    if (typeof value === "string" && value.trim()) {
      return value.trim();
    }
  }

  return "";
}

/**
 * 입력값들 중 첫 번째 숫자 값을 반환한다.
 *
 * @param {...unknown} values 숫자 후보 값들.
 * @return {number} 첫 번째 유효한 숫자 또는 0.
 */
function getNumber(...values: unknown[]): number {
  for (const value of values) {
    if (typeof value === "number" && Number.isFinite(value)) {
      return value;
    }
  }

  return 0;
}

/**
 * 입력값들 중 첫 번째 boolean 값을 반환한다.
 *
 * @param {...unknown} values boolean 후보 값들.
 * @return {boolean} 첫 번째 boolean 값 또는 false.
 */
function getBoolean(...values: unknown[]): boolean {
  for (const value of values) {
    if (typeof value === "boolean") {
      return value;
    }
  }

  return false;
}

/**
 * 문자열의 첫 줄을 반환한다.
 *
 * @param {string} value 원본 문자열.
 * @return {string} 첫 번째 줄 또는 빈 문자열.
 */
function getFirstLine(value: string): string {
  return value.split("\n").find((line) => line.trim())?.trim() || "";
}

/**
 * Android 스타일 스택 프레임만 추출한다.
 *
 * @param {string} stack 원본 스택 트레이스.
 * @param {number} limit 유지할 최대 프레임 수.
 * @return {string[]} 정규화된 상위 스택 프레임 목록.
 */
function extractTopStackFrames(stack: string, limit = 5): string[] {
  return stack
    .split("\n")
    .map((line) => line.trim())
    .filter((line) => line.startsWith("at "))
    .slice(0, limit);
}

/**
 * 스택 트레이스에서 에러 타입을 추출한다.
 *
 * @param {string} title Crashlytics 이슈 제목.
 * @param {string} stack 원본 스택 트레이스.
 * @return {string} Android 예외 타입 또는 기본값.
 */
function extractErrorType(title: string, stack: string): string {
  const firstLine = getFirstLine(stack);
  const stackMatch = firstLine.match(/^([\w$.]+(?:Exception|Error))/);

  if (stackMatch?.[1]) {
    return stackMatch[1];
  }

  const titleMatch = title.match(/([\w$.]+(?:Exception|Error))/);

  return titleMatch?.[1] || "UnknownCrash";
}

/**
 * 스택 트레이스에서 에러 메시지를 추출한다.
 *
 * @param {string} stack 원본 스택 트레이스.
 * @param {string} title Crashlytics 이슈 제목.
 * @return {string} 에러 메시지 또는 제목.
 */
function extractErrorMessage(stack: string, title: string): string {
  const firstLine = getFirstLine(stack);
  const separatorIndex = firstLine.indexOf(":");

  if (separatorIndex >= 0) {
    return firstLine.slice(separatorIndex + 1).trim();
  }

  return title;
}

/**
 * 첫 번째 스택 프레임에서 클래스와 메서드 정보를 추출한다.
 *
 * @param {string[]} frames 정규화된 스택 프레임 목록.
 * @return {{crashClass: string, crashMethod: string}} 클래스/메서드 정보.
 */
function extractCrashLocation(frames: string[]): {
  crashClass: string;
  crashMethod: string;
} {
  const primaryFrame = frames[0] || "";
  const frameMatch = primaryFrame.match(/^at\s+([\w$.]+)\.([\w$<>]+)\(/);

  return {
    crashClass: frameMatch?.[1] || "UnknownClass",
    crashMethod: frameMatch?.[2] || "unknownMethod",
  };
}

/**
 * 원본 Crashlytics 이벤트에서 정규화된 데이터를 추출한다.
 *
 * @param {unknown} event 원본 Crashlytics 이벤트 payload.
 * @return {CrashEventData} 정규화된 이벤트 데이터.
 */
function extractPayload(event: unknown): CrashEventData {
  const eventObject = asObject(event);
  const data = asObject(eventObject.data);
  const payload = asObject(data.payload ?? data);
  const issue = asObject(payload.issue);
  const appInfo = asObject(payload.appInfo ?? payload.app ?? {});
  const variants = Array.isArray(payload.variants) ? payload.variants : [];
  const latestVariant = asObject(variants[0]);
  const subtitle = getString(issue.subtitle, payload.subtitle);
  const title =
    getString(issue.title, payload.issueTitle, latestVariant.title) ||
    "Crashlytics alert";
  const stackTrace = getString(latestVariant.stackTrace, payload.stackTrace);
  const topStackFrames = extractTopStackFrames(stackTrace);
  const titleLocation = title.match(/^(.*)\.([^.]+)$/);
  const crashLocation = topStackFrames.length > 0 ?
    extractCrashLocation(topStackFrames) : {
      crashClass: titleLocation?.[1] || "UnknownClass",
      crashMethod: titleLocation?.[2] || "unknownMethod",
    };
  const subtitleParts = subtitle.split(" - ");
  const subtitleErrorType = subtitleParts[0]?.trim() || "";
  const subtitleErrorMessage = subtitleParts.slice(1).join(" - ").trim();

  return {
    kind: getString(payload.alertType) || "fatal",
    issueId: getString(issue.id, payload.issueId) || "unknown",
    title,
    platform: getString(appInfo.platform, payload.platform) || "unknown",
    appId: getString(
      appInfo.appId,
      payload.appId,
      eventObject.appId,
      eventObject.appid
    ),
    bundleId: getString(appInfo.bundleId, payload.bundleId),
    version: getString(
      issue.appVersion,
      payload.displayVersion,
      payload.versionName,
      payload.appVersion
    ),
    buildVersion: getString(payload.buildVersion, payload.buildNumber),
    affectedUsers: getNumber(
      payload.affectedUsers,
      payload.impactedUsers
    ),
    consoleUri: getString(payload.consoleUri, payload.issueUri, issue.uri),
    stackTrace,
    errorType:
      subtitleErrorType ||
      extractErrorType(title, stackTrace),
    errorMessage:
      subtitleErrorMessage ||
      extractErrorMessage(stackTrace, title),
    crashClass: crashLocation.crashClass,
    crashMethod: crashLocation.crashMethod,
    topStackFrames,
    occurredAt: new Date().toISOString(),
  };
}

/**
 * 이벤트를 처리해야 하는지 판단한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {boolean} 이슈를 생성해야 하면 true.
 */
function shouldProcess(data: CrashEventData, kind: AlertKind): boolean {
  if (!data.issueId || !data.appId) {
    return false;
  }

  if (!matchesProjectPackage(data)) {
    return false;
  }

  if (kind !== "fatal" && kind !== "regression" && kind !== "velocity") {
    return false;
  }

  return true;
}

/**
 * 이벤트를 건너뛰어야 할 때 그 이유를 반환한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {string} 필터링 이유 또는 빈 문자열.
 */
function getFilterReason(data: CrashEventData, kind: AlertKind): string {
  if (!data.issueId || data.issueId === "unknown") {
    return "missing_issue_id";
  }

  if (!data.appId) {
    return "missing_app_id";
  }

  if (!matchesProjectPackage(data)) {
    return "package_name_mismatch";
  }

  if (kind !== "fatal" && kind !== "regression" && kind !== "velocity") {
    return "unsupported_kind";
  }

  return "";
}

/**
 * 스택 트레이스를 지정한 줄 수와 글자 수 안으로 잘라낸다.
 *
 * @param {string} stack 원본 스택 트레이스.
 * @param {number} maxLines 유지할 최대 줄 수.
 * @param {number} maxChars 유지할 최대 글자 수.
 * @return {string} 잘린 스택 트레이스.
 */
function trimStack(stack = "", maxLines = 30, maxChars = 4000): string {
  return stack
    .split("\n")
    .slice(0, maxLines)
    .join("\n")
    .slice(0, maxChars);
}

/**
 * fingerprint에 사용할 문자열 조각을 정규화한다.
 *
 * @param {string} value 원본 문자열.
 * @return {string} 정규화된 fingerprint 조각.
 */
function normalizeFingerprintSegment(value: string): string {
  return value
    .trim()
    .toLowerCase()
    .replace(/[^a-z0-9._:$-]+/g, "-")
    .replace(/-+/g, "-")
    .replace(/^-|-$/g, "") || "unknown";
}

/**
 * crash signature 기반 공통 식별자 조각을 만든다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @return {string[]} kind를 제외한 공통 signature 조각.
 */
function buildSignatureSegments(data: CrashEventData): string[] {
  const signatureFrames = data.topStackFrames.length > 0 ?
    data.topStackFrames.slice(0, 3) :
    trimStack(data.stackTrace, 3, 500).split("\n");

  return [
    normalizeFingerprintSegment(data.platform),
    normalizeFingerprintSegment(data.errorType),
    normalizeFingerprintSegment(data.crashClass),
    normalizeFingerprintSegment(data.crashMethod),
    normalizeFingerprintSegment(signatureFrames.join("|")),
  ];
}

/**
 * 현재 레포의 Android 패키지명과 이벤트 패키지명이 일치하는지 확인한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @return {boolean} 동일 패키지 이벤트면 true.
 */
function matchesProjectPackage(data: CrashEventData): boolean {
  const expectedPackageName = getString(ANDROID_PACKAGE_NAME.value());

  if (!expectedPackageName) {
    return true;
  }

  return data.bundleId === expectedPackageName;
}

/**
 * 알림 타입과 플랫폼에 맞는 GitHub 라벨 목록을 만든다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {string[]} GitHub 이슈 라벨 목록.
 */
function buildLabels(data: CrashEventData, kind: AlertKind): string[] {
  const labels = ["crashlytics"];

  if (data.platform) {
    labels.push(data.platform);
  }

  if (kind === "fatal") {
    labels.push("severity:high");
  }

  if (kind === "regression") {
    labels.push("regression");
  }

  if (kind === "velocity") {
    labels.push("velocity");
    labels.push("severity:high");
  }

  return labels;
}

/**
 * Android crash 시그니처를 기반으로 incident fingerprint를 생성한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {string} Firestore incident fingerprint.
 */
function buildDedupeKey(data: CrashEventData, kind: AlertKind): string {
  return [
    normalizeFingerprintSegment(kind),
    ...buildSignatureSegments(data),
  ].join(":");
}

/**
 * 처리 기록에서 Slack thread 메타데이터를 추출한다.
 *
 * @param {JsonObject} incident Firestore incident 문서 데이터.
 * @return {SlackThreadMetadata | null} Slack thread 정보 또는 null.
 */
function getSlackThreadMetadata(
  incident: JsonObject
): SlackThreadMetadata | null {
  const slack = asObject(incident.slack);
  const channelId = getString(slack.channelId);
  const threadTs = getString(slack.messageTs);

  if (!channelId || !threadTs) {
    return null;
  }

  return {channelId, threadTs};
}

/**
 * auto-fix 작업을 시작할 수 있는 상태인지 확인한다.
 *
 * @param {string} status 현재 incident 상태.
 * @return {boolean} auto-fix 요청을 받아도 되면 true.
 */
function canStartAutoFix(status: string): boolean {
  return status === "waiting_for_slack_action";
}

/**
 * 문자열 상태값을 ProcessingStatus로 정규화한다.
 *
 * @param {string} status 원본 상태 문자열.
 * @return {ProcessingStatus} 정규화된 상태값.
 */
function normalizeProcessingStatus(status: string): ProcessingStatus {
  if (status === "waiting_for_slack_action") {
    return status;
  }

  if (status === "auto_fix_requested") {
    return status;
  }

  if (status === "fix_in_progress") {
    return status;
  }

  if (status === "pr_created") {
    return status;
  }

  if (status === "failed") {
    return status;
  }

  return "issue_created";
}

/**
 * duplicate incident를 Slack thread에 다시 알릴지 판단한다.
 *
 * @param {number} count duplicate 반영 후 누적 횟수.
 * @param {string} lastDuplicateNotifiedAt 마지막 duplicate 알림 시각.
 * @return {boolean} thread reply를 다시 보낼지 여부.
 */
function shouldNotifyDuplicate(
  count: number,
  lastDuplicateNotifiedAt: string
): boolean {
  const milestoneReached = count === 3 || count === 5 || count % 10 === 0;

  if (!milestoneReached) {
    return false;
  }

  if (!lastDuplicateNotifiedAt) {
    return true;
  }

  const elapsedMs =
    Date.now() - new Date(lastDuplicateNotifiedAt).getTime();

  return elapsedMs >= 1000 * 60 * 30;
}

/**
 * 상태에 따른 중복 클릭 안내 문구를 만든다.
 *
 * @param {string} status 현재 incident 상태.
 * @param {JsonObject} incident Firestore incident 문서 데이터.
 * @return {string} Slack thread 및 ephemeral 응답 문구.
 */
function buildAutoFixAlreadyHandledMessage(
  status: string,
  incident: JsonObject
): string {
  if (status === "auto_fix_requested" || status === "fix_in_progress") {
    return "이미 auto-fix 작업이 진행 중입니다.";
  }

  if (status === "pr_created") {
    const pr = asObject(incident.pr);
    const prUrl = getString(pr.url);

    if (prUrl) {
      return `이미 Draft PR이 생성되었습니다: ${prUrl}`;
    }

    return "이미 Draft PR이 생성되었습니다.";
  }

  return "이 incident는 현재 auto-fix를 시작할 수 없는 상태입니다.";
}

/**
 * crash 처리 기록에 사용하는 Firestore 컬렉션을 반환한다.
 *
 * @return {FirebaseFirestore.CollectionReference} Firestore 컬렉션.
 */
function getProcessedCrashesCollection() {
  return db.collection("processed_crashes");
}

/**
 * JSON 문자열을 일반 객체 값으로 파싱한다.
 *
 * @param {string} value JSON 문자열 값.
 * @return {JsonObject} 파싱된 객체 또는 빈 객체.
 */
function parseJsonObject(value: string): JsonObject {
  try {
    const parsed = JSON.parse(value);

    return asObject(parsed);
  } catch {
    return {};
  }
}

/**
 * GitHub 버그 이슈 제목을 프로젝트 템플릿 형식으로 보정한다.
 *
 * @param {string} title AI 또는 fallback이 생성한 제목.
 * @return {string} [BUG] 접두사가 붙은 이슈 제목.
 */
function normalizeBugIssueTitle(title: string): string {
  const normalizedTitle = title.trim() ||
    "Android 크래시 이슈 확인 필요";

  if (normalizedTitle.startsWith("[BUG]")) {
    return normalizedTitle;
  }

  return `[BUG] ${normalizedTitle.replace(/^\[[^\]]+\]\s*/, "")}`;
}

/**
 * Gemini 응답을 GitHub 이슈 초안 형태로 파싱한다.
 *
 * @param {unknown} value Gemini에서 받은 파싱된 JSON 응답.
 * @return {GithubIssueDraft} 기본값이 보정된 GitHub 이슈 초안.
 */
function parseGeminiDraft(value: unknown): GithubIssueAnalysisDraft {
  const draft = asObject(value);
  const labels = Array.isArray(draft.labels) ? draft.labels : ["crashlytics"];
  const normalizedLabels = labels.filter(
    (label): label is string => typeof label === "string"
  );
  const nextActions = Array.isArray(draft.nextActions) ?
    draft.nextActions.filter((action): action is string =>
      typeof action === "string" && action.trim().length > 0
    ) : [];

  return {
    summary: getString(draft.summary) || "Android Crashlytics alert detected.",
    severity: getString(draft.severity) || "high",
    suspectedCause: getString(draft.suspectedCause) || "추정: 추가 분석 필요",
    reproductionHints: getString(draft.reproductionHints) || "재현 미확인",
    nextActions: nextActions.length > 0 ? nextActions : [
      "Crashlytics 콘솔에서 issue 상세 확인",
      "최근 변경사항과 stack trace 매핑",
      "영향 범위 확인",
    ],
    issue_title: normalizeBugIssueTitle(getString(draft.issue_title)),
    labels: normalizedLabels,
  };
}

/**
 * 프로젝트 버그 리포트 템플릿에 맞춰 GitHub 이슈 본문을 만든다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {Pick<GithubIssueDraft, "summary" | "severity" | "suspectedCause" |
 * "reproductionHints" | "nextActions">} analysis AI 또는 fallback 분석 결과.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {string} GitHub 이슈 본문.
 */
function buildIssueBodyFromTemplate(
  data: CrashEventData,
  analysis: Pick<
    GithubIssueDraft,
    "summary" |
    "severity" |
    "suspectedCause" |
    "reproductionHints" |
    "nextActions"
  >,
  kind: AlertKind
): string {
  const stackPreview = data.topStackFrames.length ?
    data.topStackFrames.join("\n") : trimStack(data.stackTrace);

  return [
    "🐛 버그 설명",
    analysis.summary,
    "",
    `- 오류 유형: ${data.errorType}`,
    `- 오류 메시지: ${data.errorMessage}`,
    `- 발생 위치: ${data.crashClass}.${data.crashMethod}`,
    `- 심각도: ${analysis.severity}`,
    `- 추정 원인: ${analysis.suspectedCause}`,
    "",
    "🔍 재현 방법",
    analysis.reproductionHints,
    "",
    "1. 앱에서 해당 Crashlytics issue가 발생한 화면 또는 플로우로 이동합니다.",
    `2. ${data.crashClass}.${data.crashMethod} 경로를 실행합니다.`,
    "3. 동일 오류가 재현되는지 확인합니다.",
    "",
    "🌟 예상되는 정상적인 동작",
    "해당 화면 또는 기능이 크래시 없이 정상적으로 동작해야 합니다.",
    "",
    "📸 스크린샷 (선택 사항)",
    data.consoleUri || "Crashlytics 콘솔 링크가 제공되지 않았습니다.",
    "",
    "📱 기기 환경 (안드로이드)",
    "기기명: Crashlytics 이벤트에서 확인 필요",
    "OS 버전: Crashlytics 이벤트에서 확인 필요",
    `앱 버전: ${data.version || "확인 필요"}${
      data.buildVersion ? ` (${data.buildVersion})` : ""
    }`,
    "",
    "💡 추가 정보",
    `- Crashlytics 종류: ${kind}`,
    `- 플랫폼: ${data.platform}`,
    `- 영향 사용자 수: ${data.affectedUsers}`,
    `- 발생 시각: ${data.occurredAt}`,
    "",
    "### Top Stack",
    "```",
    stackPreview,
    "```",
    "",
    "### Next Actions",
    ...analysis.nextActions.map((action) => `- ${action}`),
  ].join("\n");
}

/**
 * 기존 GitHub 이슈에 남길 회귀/빈도 증가 코멘트를 만든다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 현재 처리 중인 알림 타입.
 * @return {string} GitHub 코멘트 markdown.
 */
function buildRecurringIssueComment(
  data: CrashEventData,
  kind: AlertKind
): string {
  const summaryLine = kind === "velocity" ?
    "Increasing-velocity alert detected for an existing Crashlytics issue." :
    "Regression alert detected for an existing Crashlytics issue.";
  const stackPreview = data.topStackFrames.length > 0 ?
    data.topStackFrames.slice(0, 3).join("\n") :
    trimStack(data.stackTrace, 3, 500);

  return [
    summaryLine,
    "",
    `- Error Type: ${data.errorType}`,
    `- Error Message: ${data.errorMessage}`,
    `- Crash Location: ${data.crashClass}.${data.crashMethod}`,
    `- App Version: ${data.version} (${data.buildVersion})`,
    `- Affected Users: ${data.affectedUsers}`,
    `- Console: ${data.consoleUri || "N/A"}`,
    "",
    "```",
    stackPreview,
    "```",
  ].join("\n");
}

/**
 * Gemini 실패 시 사용할 기본 이슈 초안을 만든다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {GithubIssueDraft} 대체용 GitHub 이슈 초안.
 */
function buildFallbackDraft(
  data: CrashEventData,
  kind: AlertKind
): GithubIssueDraft {
  const nextActions = [
    "Crashlytics 콘솔에서 issue 상세 확인",
    "최근 변경사항과 stack trace 매핑",
    "영향 범위 확인",
  ];
  const summary =
    `Android crash detected in ${data.crashClass}.${data.crashMethod}`;
  const severity = kind === "fatal" ? "high" : "medium";
  const suspectedCause = `추정: ${data.errorMessage}`;
  const reproductionHints = "재현 미확인";
  const issueTitle =
    `[BUG] ${data.crashClass}.${data.crashMethod} 크래시 해결`;

  return {
    summary,
    severity,
    suspectedCause,
    reproductionHints,
    nextActions,
    issue_title: issueTitle,
    issue_body: buildIssueBodyFromTemplate(data, {
      summary,
      severity,
      suspectedCause,
      reproductionHints,
      nextActions,
    }, kind),
    labels: buildLabels(data, kind),
  };
}


/**
 * 생성된 이슈를 알리기 위한 Slack Block Kit payload를 만든다.
 *
 * @param {string} dedupeKey Firestore 중복 처리 키.
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {GithubIssueMetadata} issue 생성된 GitHub 이슈 메타데이터.
 * @param {string=} summaryLine Slack 카드 상단에 표시할 요약 문구.
 * @return {unknown[]} Slack Block Kit payload.
 */
function buildSlackBlocks(
  dedupeKey: string,
  data: CrashEventData,
  issue: GithubIssueMetadata,
  summaryLine = "*Android Crashlytics issue created*"
): unknown[] {
  const stackPreview = data.topStackFrames.length > 0 ?
    data.topStackFrames.slice(0, 3).join("\n") :
    trimStack(data.stackTrace, 3, 500);

  return [
    {
      type: "section",
      text: {
        type: "mrkdwn",
        text:
          `${summaryLine}\n` +
          `• errorType: ${data.errorType}\n` +
          `• message: ${data.errorMessage}\n` +
          `• location: ${data.crashClass}.${data.crashMethod}\n` +
          `• platform: ${data.platform}\n` +
          `• version: ${data.version} (${data.buildVersion})\n` +
          `• affectedUsers: ${data.affectedUsers}\n` +
          `• issue: <${issue.htmlUrl}|#${issue.number}>`,
      },
    },
    {
      type: "section",
      text: {
        type: "mrkdwn",
        text: `*Top Stack*\n\`\`\`${stackPreview}\`\`\``,
      },
    },
    {
      type: "actions",
      elements: [
        {
          type: "button",
          text: {
            type: "plain_text",
            text: "Attempt auto-fix",
            emoji: true,
          },
          style: "primary",
          action_id: AUTO_FIX_ACTION_ID,
          value: JSON.stringify({
            dedupeKey,
            issueUrl: issue.htmlUrl,
            issueNumber: issue.number,
            title: data.title,
          }),
        },
        {
          type: "button",
          text: {
            type: "plain_text",
            text: "Open GitHub issue",
            emoji: true,
          },
          url: issue.htmlUrl,
        },
      ],
    },
  ];
}

/**
 * Crash 이벤트를 바탕으로 Gemini에 GitHub 이슈 초안 생성을 요청한다.
 *
 * 역할:
 * - incident의 source of truth가 될 GitHub Issue 초안을 생성한다.
 * - AI는 문제 설명과 분석 문장을 작성하고, 브랜치/PR 같은 실행 메타데이터는 다루지 않는다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @return {Promise<GithubIssueAnalysisDraft>} 생성된 이슈 분석 초안 내용.
 */
async function callGemini(
  data: CrashEventData
): Promise<GithubIssueAnalysisDraft> {
  const prompt = `
너는 모바일 앱 crash triage assistant다.
반드시 JSON만 출력해라.

[팀 규칙]
- 제목 형식: [BUG] 로 시작한다
- 제목은 이 프로젝트의 GitHub 이슈 제목처럼 간결한 한국어 작업명으로 작성한다
- 제목에는 원인/위치가 드러나야 한다. 예: [BUG] 홈 화면 공지 조회 크래시 해결
- 사용자가 작성한 버그 리포트 템플릿 구조는 코드가 조립한다
- 너는 템플릿에 들어갈 버그 설명, 재현 단서, 정상 동작, 추가 분석 문장만 생성한다
- issue_body 전체 markdown을 만들지 말 것
- Full Stack Trace 전문은 본문에 포함하지 말 것
- 추측은 "추정"이라고 명시
- 재현이 불분명하면 "재현 미확인"이라고 명시
- 장황하게 쓰지 말고 실무적으로 작성
- issue_title은 "[BUG] " 접두사를 반드시 포함한다

[입력]
platformKind: Android
title: ${data.title}
platform: ${data.platform}
appId: ${data.appId}
bundleId: ${data.bundleId}
version: ${data.version}
buildVersion: ${data.buildVersion}
kind: ${data.kind}
errorType: ${data.errorType}
errorMessage: ${data.errorMessage}
crashClass: ${data.crashClass}
crashMethod: ${data.crashMethod}
affectedUsers: ${data.affectedUsers}
consoleUri: ${data.consoleUri}
occurredAt: ${data.occurredAt}
topStackFrames:
${data.topStackFrames.join("\n")}

stackTrace:
${trimStack(data.stackTrace)}

[출력 JSON]
{
  "summary": "string",
  "severity": "critical|high|medium|low",
  "suspectedCause": "string",
  "reproductionHints": "string",
  "nextActions": ["string"],
  "issue_title": "string",
  "labels": ["string"]
}
`;

  const response = await fetch(
    "https://generativelanguage.googleapis.com/v1beta/models/" +
      "gemini-2.0-flash:generateContent?key=" +
      encodeURIComponent(GEMINI_API_KEY.value()),
    {
      method: "POST",
      headers: {"content-type": "application/json"},
      body: JSON.stringify({
        contents: [{parts: [{text: prompt}]}],
      }),
    }
  );

  if (!response.ok) {
    throw new Error(
      `Gemini failed: ${response.status} ${await response.text()}`
    );
  }

  const json = asObject(await response.json());
  const candidates = Array.isArray(json.candidates) ? json.candidates : [];
  const firstCandidate = asObject(candidates[0]);
  const content = asObject(firstCandidate.content);
  const parts = Array.isArray(content.parts) ? content.parts : [];
  const firstPart = asObject(parts[0]);
  const text = getString(firstPart.text) || "{}";

  return parseGeminiDraft(JSON.parse(text));
}

/**
 * 최종 이슈 초안을 만들고 Gemini 실패 시 fallback 초안을 사용한다.
 *
 * 역할:
 * - Issue 생성 단계의 단일 진입점이다.
 * - AI 결과가 실패하거나 불완전해도 incident 기록이 끊기지 않도록 fallback 초안을 보장한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {Promise<GithubIssueDraft>} 최종 GitHub 이슈 초안.
 */
async function buildDraft(
  data: CrashEventData,
  kind: AlertKind
): Promise<GithubIssueDraft> {
  try {
    const draft = await callGemini(data);
    const issueTitle = getString(draft.issue_title) ||
      normalizeBugIssueTitle(
        `${data.crashClass}.${data.crashMethod} 크래시 해결`
      );
    const issueBody = buildIssueBodyFromTemplate(data, {
      summary: draft.summary,
      severity: draft.severity,
      suspectedCause: draft.suspectedCause,
      reproductionHints: draft.reproductionHints,
      nextActions: draft.nextActions,
    }, kind);

    return {
      summary: draft.summary,
      severity: draft.severity,
      suspectedCause: draft.suspectedCause,
      reproductionHints: draft.reproductionHints,
      nextActions: draft.nextActions,
      issue_title: issueTitle,
      issue_body: issueBody,
      labels: buildLabels(data, kind),
    };
  } catch (error) {
    logger.error("Gemini failed, using fallback draft", {
      error: String(error),
      issueId: data.issueId,
      kind,
    });

    return buildFallbackDraft(data, kind);
  }
}

/**
 * 생성된 GitHub 이슈를 Slack 메시지로 전송한다.
 *
 * @param {string} dedupeKey Firestore 중복 처리 키.
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {GithubIssueMetadata} issue 생성된 GitHub 이슈 메타데이터.
 * @param {string=} summaryLine Slack 카드 상단에 표시할 요약 문구.
 * @return {Promise<SlackMessageMetadata>} Slack 메시지 메타데이터.
 */
async function sendSlackNotification(
  dedupeKey: string,
  data: CrashEventData,
  issue: GithubIssueMetadata,
  summaryLine?: string
): Promise<SlackMessageMetadata> {
  const response = await fetch("https://slack.com/api/chat.postMessage", {
    method: "POST",
    headers: {
      "authorization": `Bearer ${SLACK_BOT_TOKEN.value()}`,
      "content-type": "application/json; charset=utf-8",
    },
    body: JSON.stringify({
      channel: SLACK_CHANNEL_ID.value(),
      text: `[Crash][${data.platform}] ${data.title}`,
      blocks: buildSlackBlocks(dedupeKey, data, issue, summaryLine),
    }),
  });

  if (!response.ok) {
    throw new Error(
      `Slack failed: ${response.status} ${await response.text()}`
    );
  }

  const payload = asObject(await response.json());

  if (!getBoolean(payload.ok)) {
    throw new Error(
      `Slack API failed: ${getString(payload.error) || "unknown"}`
    );
  }

  return {
    channelId: getString(payload.channel) || SLACK_CHANNEL_ID.value(),
    messageTs: getString(payload.ts),
  };
}

/**
 * Slack thread에 후속 상태 메시지를 전송한다.
 *
 * @param {string} channelId Slack 채널 ID.
 * @param {string} threadTs 원본 메시지 ts.
 * @param {string} text 회신 텍스트.
 * @return {Promise<void>} 전송 완료 시 resolve 되는 Promise.
 */
async function sendSlackThreadReply(
  channelId: string,
  threadTs: string,
  text: string
): Promise<void> {
  const response = await fetch("https://slack.com/api/chat.postMessage", {
    method: "POST",
    headers: {
      "authorization": `Bearer ${SLACK_BOT_TOKEN.value()}`,
      "content-type": "application/json; charset=utf-8",
    },
    body: JSON.stringify({
      channel: channelId,
      thread_ts: threadTs,
      text,
    }),
  });

  if (!response.ok) {
    throw new Error(
      `Slack thread reply failed: ${response.status} ${await response.text()}`
    );
  }

  const payload = asObject(await response.json());

  if (!getBoolean(payload.ok)) {
    throw new Error(
      `Slack thread reply API failed: ${getString(payload.error) || "unknown"}`
    );
  }
}

/**
 * incident 문서를 기반으로 auto-fix runner 요청 payload를 만든다.
 *
 * 역할:
 * - Firestore에 저장된 incident 문서를 GitHub 실행기용 payload로 변환한다.
 * - Issue 단계에서 확정된 incident 정보만 전달하고, runner가 별도 상태 저장소를 직접 조회하지 않게 한다.
 *
 * @param {string} dedupeKey incident 식별 키.
 * @param {JsonObject} incident Firestore incident 데이터.
 * @return {AgentRunnerRequest} runner 호출 payload.
 */
function buildAgentRunnerRequest(
  dedupeKey: string,
  incident: JsonObject
): AgentRunnerRequest {
  const analysis = asObject(incident.analysis);

  return {
    dedupeKey,
    repo: GITHUB_REPO.value(),
    issueNumber: getNumber(asObject(incident.github).issueNumber),
    issueUrl: getString(asObject(incident.github).issueUrl),
    title: getString(incident.title),
    platform: getString(incident.platform),
    appVersion: getString(incident.appVersion),
    buildVersion: getString(incident.buildVersion),
    errorType: getString(incident.errorType),
    errorMessage: getString(incident.errorMessage),
    crashClass: getString(incident.crashClass),
    crashMethod: getString(incident.crashMethod),
    analysis: {
      summary: getString(analysis.summary),
      severity: getString(analysis.severity),
      suspectedCause: getString(analysis.suspectedCause),
      issueTitle: getString(analysis.issueTitle),
      issueBody: getString(analysis.issueBody),
    },
  };
}

/**
 * auto-fix runner를 호출해 Draft PR 생성 결과를 받는다.
 *
 * 역할:
 * - Functions 계층의 오케스트레이션과 Cloud Run 실행기를 분리하는 경계 함수다.
 * - 브랜치 생성, 커밋, Draft PR 생성 같은 GitHub write 작업은 모두 runner에 위임한다.
 *
 * @param {AgentRunnerRequest} payload runner 요청 payload.
 * @return {Promise<AgentRunnerResult>} runner 실행 결과.
 */
async function callAgentRunner(
  payload: AgentRunnerRequest
): Promise<AgentRunnerResult> {
  const response = await fetch(AUTO_FIX_RUNNER_URL.value(), {
    method: "POST",
    headers: {
      "authorization": `Bearer ${AUTO_FIX_RUNNER_TOKEN.value()}`,
      "content-type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error(
      `Agent runner failed: ${response.status} ${await response.text()}`
    );
  }

  const result = asObject(await response.json());

  return {
    ok: getBoolean(result.ok),
    branch: getString(result.branch),
    prUrl: getString(result.prUrl),
    summary: getString(result.summary),
    failedStep: getString(result.failedStep),
    error: getString(result.error),
  };
}

/**
 * 생성된 초안을 사용해 GitHub 이슈를 만든다.
 *
 * 역할:
 * - incident의 공식 기록 문서를 GitHub Issue로 확정한다.
 * - 이후 Slack 알림, incident 저장, PR 연결은 여기서 생성한 issue 번호와 URL을 기준으로 진행된다.
 *
 * @param {GithubIssueDraft} draft 이슈 초안 내용.
 * @return {Promise<GithubIssueMetadata>} 생성된 GitHub 이슈 메타데이터.
 */
async function createGithubIssue(
  draft: GithubIssueDraft
): Promise<GithubIssueMetadata> {
  const response = await fetch(
    `https://api.github.com/repos/${GITHUB_REPO.value()}/issues`,
    {
      method: "POST",
      headers: {
        "authorization": `Bearer ${GITHUB_TOKEN.value()}`,
        "accept": "application/vnd.github+json",
        "content-type": "application/json",
        "x-github-api-version": "2022-11-28",
      },
      body: JSON.stringify({
        title: draft.issue_title,
        body: draft.issue_body,
        labels: draft.labels.length ? draft.labels : ["crashlytics"],
      }),
    }
  );

  if (!response.ok) {
    throw new Error(
      `GitHub failed: ${response.status} ${await response.text()}`
    );
  }

  const issue = asObject(await response.json());

  return {
    htmlUrl: getString(issue.html_url),
    number: getNumber(issue.number),
  };
}

/**
 * 기존 GitHub 이슈를 다시 열어 후속 대응 대상으로 만든다.
 *
 * @param {number} issueNumber GitHub 이슈 번호.
 * @return {Promise<void>} 업데이트가 끝나면 resolve 되는 Promise.
 */
async function reopenGithubIssue(issueNumber: number): Promise<void> {
  const response = await fetch(
    `https://api.github.com/repos/${GITHUB_REPO.value()}/issues/${issueNumber}`,
    {
      method: "PATCH",
      headers: {
        "authorization": `Bearer ${GITHUB_TOKEN.value()}`,
        "accept": "application/vnd.github+json",
        "content-type": "application/json",
        "x-github-api-version": "2022-11-28",
      },
      body: JSON.stringify({state: "open"}),
    }
  );

  if (!response.ok) {
    throw new Error(
      `GitHub reopen failed: ${response.status} ${await response.text()}`
    );
  }
}

/**
 * GitHub 이슈의 현재 open/closed 상태를 조회한다.
 *
 * @param {number} issueNumber GitHub 이슈 번호.
 * @return {Promise<string>} GitHub 이슈 state 문자열.
 */
async function getGithubIssueState(issueNumber: number): Promise<string> {
  const response = await fetch(
    `https://api.github.com/repos/${GITHUB_REPO.value()}/issues/${issueNumber}`,
    {
      method: "GET",
      headers: {
        "authorization": `Bearer ${GITHUB_TOKEN.value()}`,
        "accept": "application/vnd.github+json",
        "x-github-api-version": "2022-11-28",
      },
    }
  );

  if (!response.ok) {
    throw new Error(
      `GitHub issue fetch failed: ${response.status} ${await response.text()}`
    );
  }

  const issue = asObject(await response.json());

  return getString(issue.state);
}

/**
 * 기존 GitHub 이슈에 회귀/빈도 증가 사실을 코멘트로 남긴다.
 *
 * @param {number} issueNumber GitHub 이슈 번호.
 * @param {string} body 코멘트 본문.
 * @return {Promise<void>} 등록이 끝나면 resolve 되는 Promise.
 */
async function createGithubIssueComment(
  issueNumber: number,
  body: string
): Promise<void> {
  const response = await fetch(
    `https://api.github.com/repos/${GITHUB_REPO.value()}/issues/${issueNumber}/comments`,
    {
      method: "POST",
      headers: {
        "authorization": `Bearer ${GITHUB_TOKEN.value()}`,
        "accept": "application/vnd.github+json",
        "content-type": "application/json",
        "x-github-api-version": "2022-11-28",
      },
      body: JSON.stringify({body}),
    }
  );

  if (!response.ok) {
    throw new Error(
      `GitHub comment failed: ${response.status} ${await response.text()}`
    );
  }
}

/**
 * dedupe 키에 해당하는 처리 기록 문서를 조회한다.
 *
 * @param {string} key Firestore 중복 처리 키.
 * @return {Promise<FirebaseFirestore.DocumentSnapshot>} Firestore 문서.
 */
async function getProcessedRecord(key: string) {
  return getProcessedCrashesCollection().doc(key).get();
}

/**
 * 같은 crash signature의 기존 incident 기록을 찾는다.
 *
 * 역할:
 * - regression/velocity 알림이 왔을 때 기존 fatal 이슈를 우선 재사용한다.
 * - kind별 중복 이슈 생성을 줄이고 하나의 GitHub 이슈로 후속 상황을 누적한다.
 *
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {AlertKind} currentKind 현재 처리 중인 알림 타입.
 * @return {Promise<FirebaseFirestore.DocumentSnapshot | null>} 연결할 기존 문서.
 */
async function findRelatedIncidentRecord(
  data: CrashEventData,
  currentKind: AlertKind
): Promise<FirebaseFirestore.DocumentSnapshot | null> {
  const candidateKinds: AlertKind[] = ["fatal", "regression", "velocity"];
  const dedupeKeys = candidateKinds
    .filter((kind) => kind !== currentKind)
    .map((kind) => buildDedupeKey(data, kind));
  const snapshots = await Promise.all(
    dedupeKeys.map((key) => getProcessedRecord(key))
  );

  return snapshots.find((snapshot) => snapshot.exists) || null;
}

/**
 * 중복으로 들어온 incident의 재발 횟수와 마지막 시각을 갱신한다.
 *
 * @param {string} fingerprint Firestore 중복 처리 키.
 * @param {FirebaseFirestore.DocumentSnapshot} snapshot 기존 incident 문서.
 * @param {CrashEventData} data 최신 Crashlytics 이벤트 데이터.
 * @return {Promise<{count: number}>} 갱신 후 누적 횟수.
 */
async function recordDuplicateOccurrence(
  fingerprint: string,
  snapshot: FirebaseFirestore.DocumentSnapshot,
  data: CrashEventData
): Promise<{count: number}> {
  const reference = getProcessedCrashesCollection().doc(fingerprint);
  const incident = asObject(snapshot.data());
  const nextCount = getNumber(incident.count) + 1;

  await reference.update({
    count: nextCount,
    appVersion: data.version,
    buildVersion: data.buildVersion,
    lastSeenAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  });

  return {count: nextCount};
}

/**
 * 신규 incident 문서를 Firestore에 생성한다.
 *
 * 역할:
 * - Crash 이벤트, 분석 결과, GitHub Issue 메타데이터를 하나의 incident 기록으로 묶는다.
 * - 이후 Slack 상호작용과 auto-fix PR 생성 단계가 동일한 source of truth를 공유하도록 만든다.
 *
 * @param {string} fingerprint Firestore 중복 처리 키.
 * @param {CrashEventData} data 정규화된 Crashlytics 이벤트 데이터.
 * @param {GithubIssueDraft} draft 생성된 GitHub 이슈 초안.
 * @param {GithubIssueMetadata} issue 생성된 GitHub 이슈 메타데이터.
 * @param {boolean=} issueCreated 이번 이벤트에서 새 이슈를 만들었는지 여부.
 * @return {Promise<void>} 저장이 끝나면 resolve 되는 Promise.
 */
async function createIncidentRecord(
  fingerprint: string,
  data: CrashEventData,
  draft: GithubIssueDraft,
  issue: GithubIssueMetadata,
  issueCreated = true
): Promise<void> {
  const reference = getProcessedCrashesCollection().doc(fingerprint);
  const now = new Date().toISOString();

  await reference.set({
    fingerprint,
    source: "crashlytics",
    status: "issue_created",
    eventId: data.issueId,
    issueId: data.issueId,
    kind: data.kind,
    title: data.title,
    errorType: data.errorType,
    errorMessage: data.errorMessage,
    crashClass: data.crashClass,
    crashMethod: data.crashMethod,
    appId: data.appId,
    bundleId: data.bundleId,
    platform: data.platform,
    appVersion: data.version,
    buildVersion: data.buildVersion,
    consoleUri: data.consoleUri,
    stackTrace: trimStack(data.stackTrace),
    firstSeenAt: now,
    lastSeenAt: now,
    count: 1,
    analysis: {
      summary: draft.summary,
      severity: draft.severity,
      suspectedCause: draft.suspectedCause,
      reproductionHints: draft.reproductionHints,
      nextActions: draft.nextActions,
      issueTitle: draft.issue_title,
      issueBody: draft.issue_body,
    } satisfies IncidentAnalysis,
    github: {
      issueCreated,
      issueNumber: issue.number || null,
      issueUrl: issue.htmlUrl || "",
    } satisfies IncidentGithub,
    slack: {
      sent: false,
      channelId: "",
      messageTs: "",
      lastDuplicateNotifiedAt: "",
    } satisfies IncidentSlack,
    pr: {
      created: false,
      url: "",
      branch: "",
      summary: "",
    } satisfies IncidentPr,
    createdAt: now,
    updatedAt: now,
    autoFixRequestedAt: null,
    fixStartedAt: null,
    lastError: null,
    failedStep: null,
  });
}

/**
 * 처리 기록 문서의 워크플로 상태 필드를 갱신한다.
 *
 * 역할:
 * - Issue 생성, Slack 알림, auto-fix 요청, PR 생성, 실패 상태를 단일 상태 머신처럼 관리한다.
 * - 각 비동기 단계가 Firestore 문서 하나를 기준으로 진행 상황을 공유하도록 보장한다.
 *
 * @param {string} key Firestore 중복 처리 키.
 * @param {ProcessingStatus} status 갱신할 워크플로 상태.
 * @param {Record<string, unknown>} extra 함께 병합할 추가 필드.
 * @return {Promise<void>} 업데이트가 끝나면 resolve 되는 Promise.
 */
async function updateStatus(
  key: string,
  status: ProcessingStatus,
  extra: Record<string, unknown> = {}
): Promise<void> {
  const reference = getProcessedCrashesCollection().doc(key);

  await reference.update({
    status,
    updatedAt: new Date().toISOString(),
    ...extra,
  });
}

/**
 * v2 함수의 공개 서비스 URL을 조회한다.
 *
 * @param {string} name export 된 함수 이름.
 * @param {string} location 함수 리전.
 * @return {Promise<string>} 함수 서비스 URL.
 */
async function getFunctionUrl(
  name: string,
  location = FUNCTIONS_REGION
): Promise<string> {
  if (!auth) {
    auth = new GoogleAuth({
      scopes: "https://www.googleapis.com/auth/cloud-platform",
    });
  }

  const projectId = await auth.getProjectId();
  const client = await auth.getClient();
  const url =
    "https://cloudfunctions.googleapis.com/v2beta/" +
    `projects/${projectId}/locations/${location}/functions/${name}`;
  const response = await client.request({url});
  const responseData = asObject(response.data);
  const serviceConfig = asObject(responseData.serviceConfig);
  const uri = serviceConfig.uri;

  if (!uri || typeof uri !== "string") {
    throw new Error(`Unable to retrieve uri for function ${name}`);
  }

  return uri;
}

/**
 * 들어온 Slack 인터랙션 요청의 서명을 검증한다.
 *
 * @param {Buffer} rawBody 원본 요청 body.
 * @param {string} timestamp Slack timestamp 헤더 값.
 * @param {string} signature Slack signature 헤더 값.
 * @return {boolean} 서명이 유효하면 true.
 */
function verifySlackSignature(
  rawBody: Buffer,
  timestamp: string,
  signature: string
): boolean {
  if (!timestamp || !signature) {
    return false;
  }

  const currentTimestamp = Math.floor(Date.now() / 1000);

  if (Math.abs(currentTimestamp - Number(timestamp)) > 60 * 5) {
    return false;
  }

  const baseString = `v0:${timestamp}:${rawBody.toString("utf8")}`;
  const computedSignature =
    "v0=" +
    createHmac("sha256", SLACK_SIGNING_SECRET.value())
      .update(baseString)
      .digest("hex");
  const signatureBuffer = Buffer.from(signature, "utf8");
  const computedBuffer = Buffer.from(computedSignature, "utf8");

  if (signatureBuffer.length !== computedBuffer.length) {
    return false;
  }

  return timingSafeEqual(signatureBuffer, computedBuffer);
}

/**
 * Slack interactivity가 보낸 form-encoded body를 파싱한다.
 *
 * @param {Buffer} rawBody 원본 요청 body.
 * @return {SlackInteractionPayload} 파싱된 Slack payload.
 */
function parseSlackInteractionPayload(
  rawBody: Buffer
): SlackInteractionPayload {
  const params = new URLSearchParams(rawBody.toString("utf8"));
  const payload = parseJsonObject(params.get("payload") || "{}");

  return {
    type: getString(payload.type),
    response_url: getString(payload.response_url),
    user: asObject(payload.user),
    channel: asObject(payload.channel),
    container: asObject(payload.container),
    actions: Array.isArray(payload.actions) ? payload.actions : [],
  };
}

/**
 * Slack 버튼 액션에서 auto-fix task payload를 추출한다.
 *
 * @param {SlackInteractionPayload} payload 파싱된 Slack payload.
 * @return {AutoFixTaskPayload} 파싱된 task payload.
 */
function getAutoFixTaskPayload(
  payload: SlackInteractionPayload
): AutoFixTaskPayload {
  const action = asObject(payload.actions[0]);
  const value = parseJsonObject(getString(action.value));

  return {
    dedupeKey: getString(value.dedupeKey),
    issueUrl: getString(value.issueUrl),
    issueNumber: getNumber(value.issueNumber),
    title: getString(value.title),
  };
}

/**
 * 비동기 처리를 위해 auto-fix task를 큐에 등록한다.
 *
 * @param {AutoFixTaskPayload} payload task payload.
 * @return {Promise<void>} task 등록이 끝나면 resolve 되는 Promise.
 */
async function enqueueAutoFixTask(payload: AutoFixTaskPayload): Promise<void> {
  const queue = getFunctions().taskQueue("attemptAutoFixTask");
  const targetUri = await getFunctionUrl("attemptAutoFixTask");

  await queue.enqueue(payload, {
    dispatchDeadlineSeconds: 60 * 5,
    uri: targetUri,
  });
}

/**
 * Crashlytics 이벤트를 처리하고 필요하면 GitHub 이슈를 한 번만 생성한다.
 *
 * 역할:
 * - Crashlytics 이벤트를 incident 단위로 정규화하고 dedupe 처리한다.
 * - 신규 incident일 때만 Issue 생성 -> incident 저장 -> Slack 알림 순서를 실행하는 상위 오케스트레이터다.
 *
 * @param {unknown} event 원본 Crashlytics 이벤트 payload.
 * @param {AlertKind} kind 함수가 처리하는 알림 타입.
 * @return {Promise<void>} 처리 완료 시 resolve 되는 Promise.
 */
async function processEvent(event: unknown, kind: AlertKind): Promise<void> {
  const rawEvent = asObject(event);
  const rawData = asObject(rawEvent.data);
  const rawPayload = asObject(rawData.payload ?? rawData);

  logger.info("Raw Crashlytics event shape", {
    kind,
    eventKeys: Object.keys(rawEvent),
    dataKeys: Object.keys(rawData),
    payloadKeys: Object.keys(rawPayload),
    rawData,
  });

  const data = {
    ...extractPayload(event),
    kind,
  } satisfies CrashEventData;
  const dedupeKey = buildDedupeKey(data, kind);
  const filterReason = getFilterReason(data, kind);

  logger.info("Incoming Crashlytics event", {
    kind,
    dedupeKey,
    title: data.title,
    affectedUsers: data.affectedUsers,
  });

  const existingRecord = await getProcessedRecord(dedupeKey);

  if (existingRecord.exists) {
    const duplicateResult = await recordDuplicateOccurrence(
      dedupeKey,
      existingRecord,
      data
    );
    const incident = asObject(existingRecord.data());
    const slackThread = getSlackThreadMetadata(incident);
    const slack = asObject(incident.slack);

    if (
      slackThread &&
      shouldNotifyDuplicate(
        duplicateResult.count,
        getString(slack.lastDuplicateNotifiedAt)
      )
    ) {
      await sendSlackThreadReply(
        slackThread.channelId,
        slackThread.threadTs,
        `동일 incident가 다시 발생했습니다. count=${duplicateResult.count}, ` +
          `version=${data.version} (${data.buildVersion})`
      );
      await updateStatus(
        dedupeKey,
        normalizeProcessingStatus(getString(incident.status)),
        {
          "slack.lastDuplicateNotifiedAt": new Date().toISOString(),
        }
      );
    }

    logger.info("Skipped duplicate", {
      dedupeKey,
      existingStatus: existingRecord.data()?.status,
      nextCount: duplicateResult.count,
    });
    return;
  }

  if (!shouldProcess(data, kind)) {
    logger.info("Filtered out", {dedupeKey, filterReason});
    return;
  }

  const draft = await buildDraft(data, kind);
  const relatedIncident =
    kind === "fatal" ? null : await findRelatedIncidentRecord(data, kind);
  const relatedGithub = relatedIncident ?
    asObject(asObject(relatedIncident.data()).github) : {};
  const relatedIssueNumber = getNumber(relatedGithub.issueNumber);
  const relatedIssueUrl = getString(relatedGithub.issueUrl);
  const shouldReuseExistingIssue =
    relatedIssueNumber > 0 && relatedIssueUrl.length > 0;
  const relatedIssueState = shouldReuseExistingIssue ?
    await getGithubIssueState(relatedIssueNumber) : "";

  if (shouldReuseExistingIssue && relatedIssueState === "open") {
    const relatedIncidentKey = getString(relatedIncident?.id || "");
    const duplicateResult = await recordDuplicateOccurrence(
      relatedIncidentKey,
      relatedIncident as FirebaseFirestore.DocumentSnapshot,
      data
    );
    const incident = asObject(relatedIncident?.data());
    const slackThread = getSlackThreadMetadata(incident);
    const slack = asObject(incident.slack);

    if (
      slackThread &&
      shouldNotifyDuplicate(
        duplicateResult.count,
        getString(slack.lastDuplicateNotifiedAt)
      )
    ) {
      await sendSlackThreadReply(
        slackThread.channelId,
        slackThread.threadTs,
        "이미 open 상태인 기존 이슈에 동일 incident가 다시 유입되었습니다. " +
          `count=${duplicateResult.count}, ` +
          `version=${data.version} (${data.buildVersion})`
      );
      await updateStatus(
        relatedIncidentKey,
        normalizeProcessingStatus(getString(incident.status)),
        {
          "slack.lastDuplicateNotifiedAt": new Date().toISOString(),
        }
      );
    }

    logger.info(
      "Skipped issue creation because related issue is already open",
      {
        dedupeKey,
        relatedIncidentKey,
        relatedIssueNumber,
        nextCount: duplicateResult.count,
      }
    );
    return;
  }

  const githubIssue = shouldReuseExistingIssue ?
    {
      htmlUrl: relatedIssueUrl,
      number: relatedIssueNumber,
    } satisfies GithubIssueMetadata :
    await createGithubIssue(draft);
  const slackSummaryLine = shouldReuseExistingIssue ?
    kind === "velocity" ?
      "*Android Crashlytics issue updated from velocity alert*" :
      "*Android Crashlytics issue reopened from regression alert*" :
    "*Android Crashlytics issue created*";

  if (shouldReuseExistingIssue) {
    await reopenGithubIssue(githubIssue.number);
    await createGithubIssueComment(
      githubIssue.number,
      buildRecurringIssueComment(data, kind)
    );
  }

  await createIncidentRecord(
    dedupeKey,
    data,
    draft,
    githubIssue,
    !shouldReuseExistingIssue
  );

  try {
    const slackMessage = await sendSlackNotification(
      dedupeKey,
      data,
      githubIssue,
      slackSummaryLine
    );

    await updateStatus(dedupeKey, "waiting_for_slack_action", {
      "slack.sent": true,
      "slack.channelId": slackMessage.channelId,
      "slack.messageTs": slackMessage.messageTs,
      "slack.lastDuplicateNotifiedAt": new Date().toISOString(),
    });
  } catch (error) {
    logger.error("Slack notification failed", {
      dedupeKey,
      error: String(error),
    });

    await updateStatus(dedupeKey, "failed", {
      failedStep: "slack_send",
      lastError: `slack_notification_failed:${String(error)}`,
    });
  }

  logger.info("GitHub issue created", {
    dedupeKey,
    issueNumber: githubIssue.number,
    issueUrl: githubIssue.htmlUrl,
  });
}

export const attemptAutoFixTask = onTaskDispatched(
  {
    retryConfig: {
      maxAttempts: 5,
      minBackoffSeconds: 60,
    },
    rateLimits: {
      maxConcurrentDispatches: 3,
    },
    region: FUNCTIONS_REGION,
    secrets: [SLACK_BOT_TOKEN, AUTO_FIX_RUNNER_TOKEN],
  },
  async (request) => {
    const data = asObject(request.data);
    const dedupeKey = getString(data.dedupeKey);

    if (!dedupeKey) {
      throw new Error("Missing dedupeKey in auto-fix task payload.");
    }

    await updateStatus(dedupeKey, "fix_in_progress", {
      fixStartedAt: new Date().toISOString(),
    });

    const incidentSnapshot = await getProcessedRecord(dedupeKey);
    const incident = asObject(incidentSnapshot.data());
    const slackThread = getSlackThreadMetadata(incident);
    const runnerPayload = buildAgentRunnerRequest(dedupeKey, incident);

    if (slackThread) {
      await sendSlackThreadReply(
        slackThread.channelId,
        slackThread.threadTs,
        "Auto-fix 작업을 시작했습니다. Draft PR 생성 시 결과를 이어서 알려드릴게요."
      );
    }

    try {
      const result = await callAgentRunner(runnerPayload);

      if (result.ok) {
        await updateStatus(dedupeKey, "pr_created", {
          "pr.created": true,
          "pr.url": result.prUrl,
          "pr.branch": result.branch,
          "pr.summary": result.summary,
        });

        if (slackThread) {
          await sendSlackThreadReply(
            slackThread.channelId,
            slackThread.threadTs,
            `Draft PR created: ${result.prUrl}\nSummary: ${result.summary}`
          );
        }

        logger.info("Draft PR created", {
          dedupeKey,
          prUrl: result.prUrl,
          branch: result.branch,
        });

        return;
      }

      await updateStatus(dedupeKey, "failed", {
        failedStep: result.failedStep || "agent_runner",
        lastError: result.error || "unknown_runner_error",
      });

      if (slackThread) {
        await sendSlackThreadReply(
          slackThread.channelId,
          slackThread.threadTs,
          `Auto-fix failed at ${result.failedStep || "agent_runner"}: ` +
            `${result.error || "unknown error"}`
        );
      }
    } catch (error) {
      await updateStatus(dedupeKey, "failed", {
        failedStep: "agent_runner",
        lastError: String(error),
      });

      if (slackThread) {
        await sendSlackThreadReply(
          slackThread.channelId,
          slackThread.threadTs,
          `Auto-fix failed at agent_runner: ${String(error)}`
        );
      }

      throw error;
    }

    logger.info("Auto-fix task reserved", {
      dedupeKey,
      issueNumber: getNumber(data.issueNumber),
      issueUrl: getString(data.issueUrl),
      title: getString(data.title),
    });
  }
);

export const slackInteractions = onRequest(
  {region: FUNCTIONS_REGION, secrets: [SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET]},
  async (request, response) => {
    if (request.method !== "POST") {
      response.status(405).send("Method Not Allowed");
      return;
    }

    const timestamp = getString(request.headers["x-slack-request-timestamp"]);
    const signature = getString(request.headers["x-slack-signature"]);
    const rawBody = request.rawBody || Buffer.from("");

    if (!verifySlackSignature(rawBody, timestamp, signature)) {
      response.status(401).send("Invalid Slack signature");
      return;
    }

    const payload = parseSlackInteractionPayload(rawBody);

    if (payload.type !== "block_actions") {
      response.status(200).json({text: "Ignored unsupported Slack action."});
      return;
    }

    const action = asObject(payload.actions[0]);

    if (getString(action.action_id) !== AUTO_FIX_ACTION_ID) {
      response.status(200).json({text: "Ignored unsupported Slack action."});
      return;
    }

    const taskPayload = getAutoFixTaskPayload(payload);

    if (!taskPayload.dedupeKey) {
      response.status(400).json({text: "Missing dedupe key."});
      return;
    }

    const incidentSnapshot = await getProcessedRecord(taskPayload.dedupeKey);
    const incident = asObject(incidentSnapshot.data());
    const currentStatus = getString(incident.status);
    const slackThread = getSlackThreadMetadata(incident);

    if (!canStartAutoFix(currentStatus)) {
      const message = buildAutoFixAlreadyHandledMessage(
        currentStatus,
        incident
      );

      if (slackThread) {
        await sendSlackThreadReply(
          slackThread.channelId,
          slackThread.threadTs,
          message
        );
      }

      response.status(200).json({
        response_type: "ephemeral",
        replace_original: false,
        text: message,
      });
      return;
    }

    await updateStatus(taskPayload.dedupeKey, "auto_fix_requested", {
      "autoFixRequestedAt": new Date().toISOString(),
      "slackActionUserId": getString(payload.user.id),
      "slack.channelId": getString(
        payload.channel.id,
        payload.container.channel_id
      ),
      "slack.messageTs": getString(payload.container.message_ts),
      "lastError": null,
      "failedStep": null,
    });

    if (slackThread) {
      await sendSlackThreadReply(
        slackThread.channelId,
        slackThread.threadTs,
        `@${getString(payload.user.id)} 님이 auto-fix 작업을 요청했습니다.`
      );
    }

    await enqueueAutoFixTask(taskPayload);

    response.status(200).json({
      response_type: "ephemeral",
      replace_original: false,
      text: "Auto-fix task queued.",
    });
  }
);

export const crashlyticsFatal = onNewFatalIssuePublished(
  {secrets: [GEMINI_API_KEY, GITHUB_TOKEN, SLACK_BOT_TOKEN]},
  async (event) => {
    await processEvent(event, "fatal");
  }
);

export const crashlyticsRegression = onRegressionAlertPublished(
  {secrets: [GEMINI_API_KEY, GITHUB_TOKEN, SLACK_BOT_TOKEN]},
  async (event) => {
    await processEvent(event, "regression");
  }
);

export const crashlyticsVelocity = onVelocityAlertPublished(
  {secrets: [GEMINI_API_KEY, GITHUB_TOKEN, SLACK_BOT_TOKEN]},
  async (event) => {
    await processEvent(event, "velocity");
  }
);
