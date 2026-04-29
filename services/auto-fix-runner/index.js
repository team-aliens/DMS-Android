const crypto = require("node:crypto");
const express = require("express");
const {SignJWT} = require("jose");

const app = express();

app.use(express.json({limit: "1mb"}));

function requireEnv(name) {
  const value = process.env[name];

  if (!value) {
    throw new Error(`Missing required environment variable: ${name}`);
  }

  return value;
}

function optionalEnv(name) {
  const value = process.env[name];

  if (!value) {
    return "";
  }

  return value;
}

function requireIntegerEnv(name) {
  const value = Number(requireEnv(name));

  if (!Number.isInteger(value)) {
    throw new Error(`Environment variable must be an integer: ${name}`);
  }

  return value;
}

function getGitHubAppPrivateKey() {
  const value = requireEnv("GITHUB_APP_PRIVATE_KEY");
  const normalizedValue = value.replace(/\\n/g, "\n").trim();

  return crypto.createPrivateKey(normalizedValue);
}

function parseRepo(repo) {
  const [owner, name] = String(repo).split("/");

  if (!owner || !name) {
    throw new Error(`Invalid repo format: ${repo}`);
  }

  return {owner, name};
}

function encodeGitHubPath(path) {
  return path.split("/").map(encodeURIComponent).join("/");
}

/**
 * auto-fix 작업 전용 Git 브랜치 이름을 생성한다.
 *
 * 역할:
 * - 하나의 issue에 대한 작업 시작점을 고유한 브랜치로 분리한다.
 * - 실행기는 브랜치 규칙을 코드로 고정하고, 호출자는 issue 번호만 전달한다.
 *
 * @param {number} issueNumber GitHub issue 번호.
 * @return {string} auto-fix 작업 브랜치 이름.
 */
function buildBranchName(issueNumber) {
  return `auto-fix/incident-${issueNumber}-${Date.now()}`;
}

function buildSummary(payload) {
  return payload.analysis?.summary ||
    `${payload.errorType} in ${payload.crashClass}.${payload.crashMethod}`;
}

/**
 * 새 브랜치에 남길 incident report 문서를 생성한다.
 *
 * 역할:
 * - 실제 코드 수정 전에 작업 시작 근거를 저장소에 남긴다.
 * - PR 본문보다 조금 더 자세한 incident 요약을 남기되, source of truth는 여전히 Issue와 incident 상태 문서다.
 *
 * @param {object} payload runner 요청 payload.
 * @return {string} incident report markdown 본문.
 */
function buildIncidentReport(payload) {
  return [
    `# Auto-fix incident report #${payload.issueNumber}`,
    "",
    `- Repo: ${payload.repo}`,
    `- Issue: ${payload.issueUrl}`,
    `- Platform: ${payload.platform}`,
    `- Version: ${payload.appVersion} (${payload.buildVersion})`,
    `- Error Type: ${payload.errorType}`,
    `- Error Message: ${payload.errorMessage}`,
    `- Crash Location: ${payload.crashClass}.${payload.crashMethod}`,
    "",
    "## Analysis",
    "",
    `- Summary: ${payload.analysis?.summary || "N/A"}`,
    `- Severity: ${payload.analysis?.severity || "N/A"}`,
    `- Suspected Cause: ${payload.analysis?.suspectedCause || "N/A"}`,
    "",
    "## Next Steps",
    "",
    "- Review the generated incident scaffold",
    "- Apply the actual code fix on this branch",
    "- Update tests and push follow-up commits",
  ].join("\n");
}

/**
 * Draft PR 본문을 생성한다.
 *
 * 역할:
 * - Issue를 문제 문서로 두고, PR은 실행 시작점이라는 관점을 반영한다.
 * - PR에는 관련 issue 링크, 작업 브랜치, incident report 경로 같은 실행 메타데이터만 축약해서 담는다.
 *
 * @param {object} payload runner 요청 payload.
 * @param {string} branch 생성된 작업 브랜치 이름.
 * @param {string} reportPath incident report 파일 경로.
 * @return {string} Draft PR markdown 본문.
 */
function buildPullRequestBody(payload, branch, reportPath) {
  return [
    "💡 작업 요약",
    payload.analysis?.summary || "Incident scaffold created for follow-up work.",
    "",
    "🔗 관련 이슈",
    "<!-- 본 PR과 관련된 이슈 번호를 작성해주세요. (예: Closes #123) -->",
    `Closes #${payload.issueNumber}`,
    "",
    "🛠️ 작업 내역",
    `- [x] Crash incident scaffold added at \`${reportPath}\``,
    `- [x] Draft PR created from auto-fix branch \`${branch}\``,
    `- [ ] 실제 수정 구현 및 검증`,
    "",
    "📸 스크린샷 / GIF (선택)",
    "<!-- UI 변경 사항이 있다면 스크린샷이나 화면 녹화(GIF)를 첨부해주세요. -->",
    "| AS-IS (변경 전) | TO-BE (변경 후) |",
    "| :---: | :---: |",
    "| <!-- 이미지 첨부 --> | <!-- 이미지 첨부 --> |",
    "",
    "🧪 테스트 방법",
    "<!-- 리뷰어가 이 PR의 변경 사항을 어떻게 테스트해 볼 수 있는지 설명해주세요. -->",
    "- [ ] Crashlytics issue 링크와 incident report 경로 확인",
    "- [ ] auto-fix 브랜치에서 실제 수정 후 재검증",
    "",
    "⚠️ 리뷰어에게 할 말 (선택)",
    `- GitHub Issue: [#${payload.issueNumber}](${payload.issueUrl})`,
    `- Error Type: \`${payload.errorType}\``,
    `- Crash Location: \`${payload.crashClass}.${payload.crashMethod}\``,
    `- Incident Report: \`${reportPath}\``,
    "- 이 PR은 `auto-fix-runner`가 자동 생성했으며, 실제 수정은 후속 커밋에서 진행됩니다.",
    "",
    "✅ 체크리스트",
    "- [x] 코드를 스스로 리뷰하고 수정했나요?",
    "- [ ] Android Studio에서 빌드가 정상적으로 완료되나요?",
    "- [x] 이해하기 어려운 코드에 주석을 달았나요?",
    "- [x] 컨벤션에 맞게 코드를 작성했나요?",
  ].join("\n");
}

async function githubRequest(path, options, token) {
  const response = await fetch(`https://api.github.com${path}`, {
    ...options,
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: "application/vnd.github+json",
      "X-GitHub-Api-Version": "2022-11-28",
      ...(options?.headers || {}),
    },
  });

  if (!response.ok) {
    const body = await response.text();
    throw new Error(`GitHub API failed (${path}): ${response.status} ${body}`);
  }

  if (response.status === 204) {
    return {};
  }

  return response.json();
}

async function getInstallationToken() {
  const appId = requireIntegerEnv("GITHUB_APP_ID");
  const installationId = requireEnv("GITHUB_INSTALLATION_ID");
  const key = getGitHubAppPrivateKey();
  const now = Math.floor(Date.now() / 1000);
  const jwt = await new SignJWT({})
    .setProtectedHeader({alg: "RS256"})
    .setIssuedAt(now - 60)
    .setExpirationTime(now + 60 * 9)
    .setIssuer(appId)
    .sign(key);

  const response = await fetch(
    `https://api.github.com/app/installations/${installationId}/access_tokens`,
    {
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/vnd.github+json",
        "X-GitHub-Api-Version": "2022-11-28",
      },
    }
  );

  if (!response.ok) {
    throw new Error(
      `GitHub installation token failed: ${response.status} ${await response.text()}`
    );
  }

  const payload = await response.json();

  return payload.token;
}

/**
 * GitHub API 호출에 사용할 인증 토큰을 결정한다.
 *
 * 역할:
 * - 운영 편의를 위해 `GITHUB_TOKEN`을 우선 사용하고, 없으면 GitHub App 인증으로 fallback 한다.
 * - runner가 인증 전략 차이를 숨기고 동일한 GitHub write 인터페이스를 제공하게 한다.
 *
 * @return {Promise<string>} GitHub API 호출에 사용할 bearer token.
 */
async function getGithubToken() {
  const token = optionalEnv("GITHUB_TOKEN").trim();

  if (token) {
    return token;
  }

  return getInstallationToken();
}

/**
 * auto-fix Draft PR 생성 전체 흐름을 실행한다.
 *
 * 역할:
 * - GitHub 실행기의 메인 엔트리 포인트다.
 * - 기본 브랜치 조회 -> 작업 브랜치 생성 -> incident report 커밋 -> Draft PR 생성 순서를 하나의 실행 단위로 묶는다.
 *
 * @param {object} payload runner 요청 payload.
 * @return {Promise<{ok: boolean, branch: string, prUrl: string, summary: string}>} 생성 결과 요약.
 */
async function createDraftPullRequest(payload) {
  const token = await getGithubToken();
  const {owner, name} = parseRepo(payload.repo);
  const repo = await githubRequest(`/repos/${owner}/${name}`, {}, token);
  const defaultBranch = repo.default_branch;
  const branch = buildBranchName(payload.issueNumber);
  const defaultRef = await githubRequest(
    `/repos/${owner}/${name}/git/ref/heads/${defaultBranch}`,
    {},
    token
  );

  await githubRequest(
    `/repos/${owner}/${name}/git/refs`,
    {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({
        ref: `refs/heads/${branch}`,
        sha: defaultRef.object.sha,
      }),
    },
    token
  );

  const reportPath = `.auto-fix/incidents/incident-${payload.issueNumber}.md`;
  const reportContent = Buffer
    .from(buildIncidentReport(payload), "utf8")
    .toString("base64");

  await githubRequest(
    `/repos/${owner}/${name}/contents/${encodeGitHubPath(reportPath)}`,
    {
      method: "PUT",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({
        message: `chore(auto-fix): scaffold incident report (#${payload.issueNumber})`,
        content: reportContent,
        branch,
      }),
    },
    token
  );

  const pr = await githubRequest(
    `/repos/${owner}/${name}/pulls`,
    {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({
        title: `chore(auto-fix): incident #${payload.issueNumber}`,
        head: branch,
        base: defaultBranch,
        body: buildPullRequestBody(payload, branch, reportPath),
        draft: true,
      }),
    },
    token
  );

  return {
    ok: true,
    branch,
    prUrl: pr.html_url,
    summary: buildSummary(payload),
  };
}

app.post("/", async (req, res) => {
  try {
    const expectedToken = requireEnv("RUNNER_SHARED_TOKEN");
    const authorization = req.get("authorization") || "";

    if (authorization !== `Bearer ${expectedToken}`) {
      res.status(401).json({ok: false, failedStep: "auth", error: "unauthorized"});
      return;
    }

    const payload = req.body || {};

    if (!payload.repo || !payload.issueNumber || !payload.issueUrl) {
      res.status(400).json({
        ok: false,
        failedStep: "validation",
        error: "missing repo, issueNumber, or issueUrl",
      });
      return;
    }

    const result = await createDraftPullRequest(payload);
    res.status(200).json(result);
  } catch (error) {
    res.status(500).json({
      ok: false,
      failedStep: "create_pr",
      error: String(error),
    });
  }
});

const port = Number(process.env.PORT || 8080);
app.listen(port, () => {
  console.log(`auto-fix-runner listening on ${port}`);
});
