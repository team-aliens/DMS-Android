#!/usr/bin/env bash
# Cloud Run runner 최초 세팅 스크립트
# 실행 전: gcloud auth login && gcloud config set project team-dms
set -euo pipefail

PROJECT_ID="team-dms"
REGION="us-central1"
REPO_NAME="auto-fix-runner"

echo "=== 1. Artifact Registry 레포지토리 생성 ==="
gcloud artifacts repositories create "$REPO_NAME" \
  --repository-format=docker \
  --location="$REGION" \
  --description="auto-fix-runner Docker images" \
  --project="$PROJECT_ID" 2>/dev/null || echo "이미 존재함, 스킵"

echo ""
echo "=== 2. Secret Manager 시크릿 생성 ==="
for SECRET_NAME in \
  RUNNER_SHARED_TOKEN \
  GITHUB_APP_ID \
  GITHUB_INSTALLATION_ID \
  GITHUB_APP_PRIVATE_KEY; do
  if gcloud secrets describe "$SECRET_NAME" --project="$PROJECT_ID" &>/dev/null; then
    echo "$SECRET_NAME 이미 존재함, 스킵"
  else
    gcloud secrets create "$SECRET_NAME" \
      --replication-policy="automatic" \
      --project="$PROJECT_ID"
    echo "$SECRET_NAME 생성됨"
  fi
done

echo ""
echo "=== 3. 각 시크릿에 값 넣기 ==="
echo "아래 명령으로 값을 설정하세요:"
echo ""
echo "  echo -n 'YOUR_SHARED_TOKEN' | gcloud secrets versions add RUNNER_SHARED_TOKEN --data-file=- --project=$PROJECT_ID"
echo "  echo -n 'YOUR_APP_ID'       | gcloud secrets versions add GITHUB_APP_ID --data-file=- --project=$PROJECT_ID"
echo "  echo -n 'YOUR_INSTALL_ID'   | gcloud secrets versions add GITHUB_INSTALLATION_ID --data-file=- --project=$PROJECT_ID"
echo "  cat your-private-key.pem    | gcloud secrets versions add GITHUB_APP_PRIVATE_KEY --data-file=- --project=$PROJECT_ID"

echo ""
echo "=== 4. GitHub Actions 시크릿 설정 안내 ==="
echo "GitHub 리포지토리 Settings > Secrets and variables > Actions 에서 아래 시크릿을 등록하세요:"
echo ""
echo "  GCP_WORKLOAD_IDENTITY_PROVIDER  - WIF provider 리소스 이름"
echo "  GCP_RUNNER_SERVICE_ACCOUNT      - Cloud Run 배포용 SA 이메일"
echo "  FIREBASE_TOKEN                  - firebase login:ci 출력값"
echo ""
echo "WIF 미사용 시 GCP_RUNNER_SERVICE_ACCOUNT 대신 서비스 계정 JSON을 사용할 수도 있습니다."
echo ""
echo "=== 완료 ==="
