# DMS-Android 디자인 시스템 구현 가이드

> **목적**: Figma 디자인을 Compose로 구현할 때 프로젝트 규칙을 따르고, 기존 문제를 반복하지 않으며, 기존 코드와 충돌 없이 통합하기 위한 실행 가이드

**마지막 업데이트**: 2025-11-13
**대상**: Claude AI Assistant
**프로젝트**: DMS-Android (Dormitory Management System)

---

## 목차

1. [프로젝트 컨텍스트](#1-프로젝트-컨텍스트)
2. [반드시 회피해야 할 문제들](#2-반드시-회피해야-할-문제들)
3. [컴포넌트 구현 체크리스트](#3-컴포넌트-구현-체크리스트)
4. [프로젝트 코딩 규칙](#4-프로젝트-코딩-규칙)
5. [기존 코드와 통합 전략](#5-기존-코드와-통합-전략)
6. [Figma → Compose 변환 가이드](#6-figma--compose-변환-가이드)
7. [자주 추가될 컴포넌트별 가이드](#7-자주-추가될-컴포넌트별-가이드)
8. [디자인 토큰 시스템 구축](#8-디자인-토큰-시스템-구축)
9. [파일 구조 템플릿](#9-파일-구조-템플릿)
10. [빠른 참조: 주요 파일 경로](#10-빠른-참조-주요-파일-경로)
11. [구현 워크플로우](#11-구현-워크플로우)

---

## 1. 프로젝트 컨텍스트

### 모듈 구조
```
DMS-Android/
├── core/
│   ├── design-system/     ← 디자인 시스템 컴포넌트 (여기에 추가)
│   ├── ui/                ← MVI 프레임워크, 일부 UI 유틸리티
│   └── [other cores]/
├── feature/               ← UI 화면들 (디자인 시스템 사용)
├── data/                  ← 비즈니스 로직
└── network/               ← API 레이어
```

**디자인 시스템 위치**:
```
/Users/yuseob/Documents/android-project/DMS-Android/core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/
```

### 기술 스택
- **Compose**: 1.5.4
- **Material 3**: androidx.compose.material3
- **Min SDK**: 23
- **Target SDK**: 34
- **Kotlin**: 1.9.10

### 테마 시스템
```kotlin
DmsTheme {
    // 내부에서 사용 가능:
    DmsTheme.colorScheme  // Colors
    DmsTheme.typography   // Typography
    DmsTheme.shapes       // Shapes
}
```

---

## 2. ⚠️ 반드시 회피해야 할 문제들

> 기존 디자인 시스템에 존재하는 문제들을 반복하지 않기 위한 안티패턴 목록

### ❌ 하지 말아야 할 것들

#### 1. Mutable State로 Theme 정의하지 않기
```kotlin
// ❌ 나쁜 예 (기존 Colors.kt Line 93-133에 존재)
class Colors(...) {
    var primary by mutableStateOf(primary)
    var secondary by mutableStateOf(secondary)
}

// ✅ 좋은 예
data class Colors(
    val primary: Color,
    val secondary: Color,
)
```

#### 2. 하드코딩된 값 사용하지 않기
```kotlin
// ❌ 나쁜 예
Spacer(modifier = Modifier.height(20.dp))
Box(modifier = Modifier.padding(16.dp))

// ✅ 좋은 예
Spacer(modifier = Modifier.height(DmsSpacing.large))
Box(modifier = Modifier.padding(DmsSpacing.medium))
```

#### 3. Material 2 ripple 사용하지 않기
```kotlin
// ❌ 나쁜 예 (기존 Clickable.kt에 존재)
import androidx.compose.material.ripple.rememberRipple

// ✅ 좋은 예
import androidx.compose.material3.ripple
```

#### 4. AndroidView 사용 자제
```kotlin
// ❌ 나쁜 예 (기존 Calendars.kt에 존재)
AndroidView(
    factory = { CalendarView(it) }
)

// ✅ 좋은 예 - Compose native 구현 사용
// Material3 DatePicker 또는 커스텀 Compose 구현
```

#### 5. 도메인 로직 섞지 않기
```kotlin
// ❌ 나쁜 예 (기존 Buttons.kt에 존재)
fun containedRefuseButtonColors() = ButtonColors(...)  // "Refuse"는 도메인 개념

// ✅ 좋은 예
fun containedButtonColors(
    containerColor: Color = DmsTheme.colorScheme.error  // 색상 역할만
) = ButtonColors(...)
```

#### 6. Copy 함수에서 다른 속성 참조하지 않기
```kotlin
// ❌ 나쁜 예 (기존 Colors.kt Line 144, Shapes.kt Line 23에 버그 존재)
fun copy(
    errorContainer: Color = this.onErrorContainer,  // 잘못된 참조!
    extraSmall: Shape = this.small,  // 잘못된 참조!
)

// ✅ 좋은 예
fun copy(
    errorContainer: Color = this.errorContainer,
    extraSmall: Shape = this.extraSmall,
)
```

#### 7. 접근성 시맨틱 누락하지 않기
```kotlin
// ❌ 나쁜 예
Icon(imageVector = Icons.Default.Close, contentDescription = null)

// ✅ 좋은 예
Icon(
    imageVector = Icons.Default.Close,
    contentDescription = "닫기",
    modifier = Modifier.semantics { role = Role.Button }
)
```

#### 8. toString() 메서드에서 속성 참조 확인
```kotlin
// ❌ 나쁜 예 (기존 Typography.kt Line 171에 버그 존재)
override fun toString(): String = "headline2=$headline1"  // 잘못된 참조!

// ✅ 좋은 예
override fun toString(): String = "headline2=$headline2"
```

---

## 3. ✅ 컴포넌트 구현 체크리스트

> 새 컴포넌트를 추가할 때 반드시 확인해야 할 항목들

### 📁 파일 구조
- [ ] 파일명: PascalCase + 복수형 (예: `Buttons.kt`, `Cards.kt`, `Chips.kt`)
- [ ] 위치: `/core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/`
- [ ] package 선언: `package team.aliens.dms.android.core.designsystem`

### 🎨 컴포넌트 API
- [ ] 일관된 네이밍 (접두사 `Dms` 없이, Material3와 구분되는 이름)
- [ ] `modifier` 파라미터 첫 번째 위치, 기본값 `Modifier`
- [ ] `enabled` 파라미터 포함 (인터랙티브 컴포넌트의 경우)
- [ ] 색상/크기 커스터마이징용 `Defaults` 객체 제공

### 🔄 상태 관리
- [ ] 모든 인터랙티브 상태 지원: `enabled`, `disabled`, `pressed`, `focused`
- [ ] `loading` 상태 지원 (버튼, 입력 필드 등)
- [ ] `error` 상태 지원 (입력 필드 등)
- [ ] `remember`로 상태 관리, `LaunchedEffect`로 부수효과 처리
- [ ] 불필요한 recomposition 방지 (`remember`, `derivedStateOf` 활용)

### ♿ 접근성
- [ ] `semantics { }` 블록으로 역할 명시
- [ ] `contentDescription` 파라미터 제공 (이미지, 아이콘)
- [ ] `role = Role.Button/Checkbox` 등 명시
- [ ] 최소 터치 영역 48.dp 보장
- [ ] 색상 대비율 WCAG AA 기준 (4.5:1) 확인
- [ ] 포커스 인디케이터 제공

### 🎯 Material 3 준수
- [ ] Material3 컴포넌트를 감싸는 경우, M3 가이드라인 준수
- [ ] 표준 M3 색상 역할 사용 (`primary`, `secondary`, `tertiary`, `error`, `surface` 등)
- [ ] M3 Shape system 사용 (`DmsTheme.shapes`)
- [ ] M3 Typography 사용 (`DmsTheme.typography`)

### 🎨 테마 통합
- [ ] `DmsTheme.colorScheme` 색상 사용
- [ ] `DmsTheme.typography` 타이포그래피 사용
- [ ] `DmsTheme.shapes` 도형 사용
- [ ] 디자인 토큰만 사용, 하드코딩 금지

### ⚡ 성능
- [ ] 무거운 연산은 `remember { }` 로 캐싱
- [ ] `key` 파라미터로 recomposition 제어
- [ ] `LazyColumn` 등에서 item keys 제공
- [ ] `derivedStateOf`로 계산된 상태 최적화

### 📝 문서화
- [ ] KDoc으로 컴포넌트 설명
- [ ] 파라미터 설명
- [ ] `@Preview` 작성 (기본, 다크모드, 각 상태별)

---

## 4. 프로젝트 코딩 규칙

### 네이밍 컨벤션

| 요소 | 규칙 | 예시 |
|------|------|------|
| Composable | PascalCase | `ContainedButton`, `TextField` |
| 파일명 | 컴포넌트명 + 복수형 | `Buttons.kt`, `Cards.kt` |
| Defaults 객체 | `ComponentNameDefaults` | `ButtonDefaults`, `CardDefaults` |
| Colors 함수 | `componentNameColors()` | `containedButtonColors()` |
| 파라미터 | camelCase | `onClick`, `backgroundColor` |

### 파라미터 순서

```kotlin
@Composable
fun MyComponent(
    // 1. modifier (항상 첫 번째)
    modifier: Modifier = Modifier,

    // 2. 상태 관련 파라미터
    enabled: Boolean = true,
    loading: Boolean = false,

    // 3. 필수 content/data 파라미터
    text: String,
    icon: ImageVector? = null,

    // 4. 선택적 스타일링 파라미터
    colors: ComponentColors = ComponentDefaults.colors(),
    shape: Shape = DmsTheme.shapes.medium,
    contentPadding: PaddingValues = ComponentDefaults.contentPadding,

    // 5. 콜백 (마지막)
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
)
```

### 디자인 토큰 사용 규칙

#### ✅ 올바른 사용
```kotlin
// 색상
DmsTheme.colorScheme.primary
DmsTheme.colorScheme.onPrimary
DmsTheme.colorScheme.error

// 타이포그래피
DmsTheme.typography.title1
DmsTheme.typography.body1

// 도형
DmsTheme.shapes.medium
DmsTheme.shapes.large

// 스페이싱 (추가 예정)
DmsSpacing.medium
DmsSpacing.large
```

#### ❌ 잘못된 사용
```kotlin
// 직접 색상 값 사용
PrimaryDefault
Gray5
Color(0xFF123456)

// 직접 TextStyle 생성
TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)

// 하드코딩된 값
16.dp
24.dp
RoundedCornerShape(8.dp)
```

### Import 규칙

```kotlin
// Material3 import 최소화
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

// 디자인 시스템 우선
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
```

---

## 5. 기존 코드와 통합 전략

### 점진적 마이그레이션 접근법

#### 1️⃣ 새 컴포넌트 추가
기존 것과 다른 이름으로 추가하여 충돌 방지
```kotlin
// 기존: Toast.kt에 커스텀 toast 시스템 존재
// 신규: Snackbar.kt로 M3 SnackbarHost 기반 구현

// 기존: TextField.kt (755줄의 복잡한 구현)
// 신규: OutlinedTextField.kt로 M3 기반 간단한 구현
```

#### 2️⃣ 새 화면부터 적용
```kotlin
// feature/newfeature/NewFeatureScreen.kt
import team.aliens.dms.android.core.designsystem.DmsCard  // 새 컴포넌트 사용
```

#### 3️⃣ 기존 화면 점진적 교체
PR 단위로 하나씩 마이그레이션
```kotlin
// Before
import androidx.compose.material3.Card

// After
import team.aliens.dms.android.core.designsystem.DmsCard
```

#### 4️⃣ Deprecated 표시
```kotlin
@Deprecated(
    message = "Use DmsCard instead",
    replaceWith = ReplaceWith("DmsCard", "team.aliens.dms.android.core.designsystem.DmsCard")
)
@Composable
fun OldCard(...) { }
```

#### 5️⃣ 완전 제거
모든 사용처 교체 후 구 컴포넌트 파일 제거

### 충돌 방지 전략

#### ✅ 안전한 방법
- 새 컴포넌트는 별도 파일에 추가
- 기존 컴포넌트 수정 최소화
- Breaking change 회피
- 새 `Defaults` 객체 생성 (기존 것 수정 금지)

#### ⚠️ 주의사항
```kotlin
// ❌ 기존 ButtonDefaults 수정 금지
object ButtonDefaults {
    fun colors(...) = ...  // 기존 코드 (30+ 곳에서 사용 중)
}

// ✅ 새로운 것 추가
object ButtonDefaults {
    fun colors(...) = ...  // 기존 유지

    fun colorsV2(...) = ...  // 새로 추가
}
```

### 테스트 전략

#### Preview로 즉시 확인
```kotlin
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyComponentPreview() {
    DmsTheme {
        Surface {
            MyComponent(...)
        }
    }
}
```

#### 빌드 확인
```bash
./gradlew :core:design-system:build
```

#### 실제 화면에서 테스트
1. 새 화면에 적용
2. Preview로 확인
3. 실제 앱에서 동작 확인

---

## 6. Figma → Compose 변환 가이드

### Figma 속성 → Compose 매핑

| Figma | Compose | 예시 |
|-------|---------|------|
| Auto Layout (Horizontal) | `Row` | `Row(horizontalArrangement = Arrangement.spacedBy(8.dp))` |
| Auto Layout (Vertical) | `Column` | `Column(verticalArrangement = Arrangement.spacedBy(8.dp))` |
| Frame | `Box` 또는 `Surface` | `Box(modifier = Modifier.size(100.dp))` |
| Padding | `Modifier.padding()` | `Modifier.padding(16.dp)` |
| Gap | `Arrangement.spacedBy()` | `Arrangement.spacedBy(DmsSpacing.medium)` |
| Corner Radius | `shape = RoundedCornerShape()` | `shape = RoundedCornerShape(8.dp)` |
| Fill | `backgroundColor` 또는 `containerColor` | `containerColor = DmsTheme.colorScheme.primary` |
| Stroke | `border()` | `Modifier.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))` |
| Effect: Drop Shadow | `Modifier.shadow()` | `Modifier.shadow(4.dp, RoundedCornerShape(8.dp))` |
| Typography | `DmsTheme.typography` | `style = DmsTheme.typography.title1` |
| Colors | `DmsTheme.colorScheme` | `color = DmsTheme.colorScheme.primary` |

### 디자인 토큰 우선 생성 워크플로우

#### 1단계: 색상 팔레트 추출
```kotlin
// Figma에서 사용된 색상 확인
// Colors.kt 업데이트

val Primary = Color(0xFF4CAF50)
val Secondary = Color(0xFF2196F3)
// ... 등등

// colorScheme에 추가
val lightColors = Colors(
    primary = Primary,
    secondary = Secondary,
    // ...
)
```

#### 2단계: Typography 스타일 추출
```kotlin
// Figma Text Styles 확인
// Typography.kt 업데이트

val headline1 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
)
```

#### 3단계: Spacing 값 추출
```kotlin
// Figma에서 자주 사용되는 spacing 값 확인
// Spacing.kt 생성

object DmsSpacing {
    val none = 0.dp
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
    val extraExtraLarge = 40.dp
}
```

#### 4단계: Corner Radius 추출
```kotlin
// Figma에서 사용된 corner radius 확인
// Shapes.kt 업데이트

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    // ...
)
```

#### 5단계: 토큰 먼저 정의 후 컴포넌트 구현
모든 디자인 토큰을 먼저 정의한 다음, 컴포넌트에서 토큰을 참조

### 컴포넌트 분해 전략

#### Figma Component → Compose Composable

```
Figma: Card Component
├── Variant: Default
├── Variant: Elevated
├── Variant: Outlined
└── Property: showIcon (Boolean)

↓

Compose:
├── DmsCard (기본)
├── ElevatedDmsCard
├── OutlinedDmsCard
└── 각각 icon 파라미터 지원
```

#### Atomic Design으로 분해
```
Figma Complex Component
↓
Atoms (가장 작은 단위):
- Icon
- Text
- Button

Molecules (작은 조합):
- IconButton
- LabeledInput

Organisms (큰 조합):
- Card with header and content
- List item with icon and text
```

---

## 7. 자주 추가될 컴포넌트별 가이드

### Card 컴포넌트

```kotlin
// Cards.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults as M3CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * DMS 디자인 시스템의 Card 컴포넌트
 * Material 3 Card를 래핑
 */
@Composable
fun DmsCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: CardColors = CardDefaults.colors(),
    elevation: CardElevation = CardDefaults.elevation(),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            colors = colors,
            elevation = elevation,
            content = content,
        )
    } else {
        Card(
            modifier = modifier,
            colors = colors,
            elevation = elevation,
            content = content,
        )
    }
}

object CardDefaults {
    @Composable
    fun colors(
        containerColor: Color = DmsTheme.colorScheme.surface,
        contentColor: Color = DmsTheme.colorScheme.onSurface,
    ): CardColors = M3CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = contentColor,
    )

    @Composable
    fun elevation(
        defaultElevation: Dp = 1.dp,
        pressedElevation: Dp = 2.dp,
    ): CardElevation = M3CardDefaults.cardElevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
    )

    val contentPadding = PaddingValues(16.dp)
}
```

### Icon / IconButton 컴포넌트

```kotlin
// Icons.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.material3.Icon as M3Icon
import androidx.compose.material3.IconButton as M3IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics

/**
 * DMS 아이콘 컴포넌트
 * contentDescription 필수
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = DmsTheme.colorScheme.onSurface,
) {
    M3Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

/**
 * DMS 아이콘 버튼
 * 최소 터치 영역 48.dp 보장
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
) {
    M3IconButton(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
    ) {
        icon()
    }
}
```

### Chip 컴포넌트

```kotlin
// Chips.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.material3.FilterChip
import androidx.compose.material3.ChipDefaults as M3ChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * DMS 필터 칩
 * 선택 가능한 칩
 */
@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: ChipColors = ChipDefaults.filterChipColors(),
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        colors = colors,
    )
}

object ChipDefaults {
    @Composable
    fun filterChipColors(
        containerColor: Color = DmsTheme.colorScheme.surface,
        labelColor: Color = DmsTheme.colorScheme.onSurface,
        selectedContainerColor: Color = DmsTheme.colorScheme.primaryContainer,
        selectedLabelColor: Color = DmsTheme.colorScheme.onPrimaryContainer,
    ): ChipColors = M3ChipDefaults.filterChipColors(
        containerColor = containerColor,
        labelColor = labelColor,
        selectedContainerColor = selectedContainerColor,
        selectedLabelColor = selectedLabelColor,
    )
}
```

### Progress Indicators

```kotlin
// Progress.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.material3.CircularProgressIndicator as M3CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator as M3LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * DMS 원형 프로그레스 인디케이터
 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DmsTheme.colorScheme.primary,
) {
    M3CircularProgressIndicator(
        modifier = modifier,
        color = color,
    )
}

/**
 * DMS 선형 프로그레스 인디케이터
 */
@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DmsTheme.colorScheme.primary,
    trackColor: Color = DmsTheme.colorScheme.surfaceVariant,
) {
    M3LinearProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,
    )
}
```

### List Items

```kotlin
// ListItems.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.material3.ListItem as M3ListItem
import androidx.compose.material3.ListItemDefaults as M3ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * DMS 리스트 아이템
 */
@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
) {
    M3ListItem(
        headlineContent = headlineContent,
        modifier = modifier,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors,
    )
}

object ListItemDefaults {
    @Composable
    fun colors(
        containerColor: Color = DmsTheme.colorScheme.surface,
        headlineColor: Color = DmsTheme.colorScheme.onSurface,
        leadingIconColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        trailingIconColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
    ): ListItemColors = M3ListItemDefaults.colors(
        containerColor = containerColor,
        headlineColor = headlineColor,
        leadingIconColor = leadingIconColor,
        trailingIconColor = trailingIconColor,
    )
}
```

---

## 8. 디자인 토큰 시스템 구축

### 추가 필요한 토큰 파일

#### Spacing.kt (신규 생성)

```kotlin
// /core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/Spacing.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * DMS 디자인 시스템의 스페이싱 토큰
 *
 * Figma에서 추출한 일관된 간격 값들
 */
object DmsSpacing {
    /** 0dp - 간격 없음 */
    val none: Dp = 0.dp

    /** 4dp - 매우 작은 간격 (아이콘과 텍스트 사이 등) */
    val extraSmall: Dp = 4.dp

    /** 8dp - 작은 간격 (관련 요소 사이) */
    val small: Dp = 8.dp

    /** 12dp - 작은-중간 간격 */
    val smallMedium: Dp = 12.dp

    /** 16dp - 중간 간격 (카드 내부 패딩 등) */
    val medium: Dp = 16.dp

    /** 20dp - 중간-큰 간격 */
    val mediumLarge: Dp = 20.dp

    /** 24dp - 큰 간격 (섹션 사이) */
    val large: Dp = 24.dp

    /** 32dp - 매우 큰 간격 */
    val extraLarge: Dp = 32.dp

    /** 40dp - 초대형 간격 */
    val extraExtraLarge: Dp = 40.dp

    /** 48dp - 최대 간격 */
    val huge: Dp = 48.dp
}

/**
 * 스크린 엣지 패딩
 * 화면 가장자리와 콘텐츠 사이의 표준 여백
 */
object ScreenPadding {
    /** 가로 방향 기본 패딩 */
    val horizontal: Dp = DmsSpacing.medium

    /** 세로 방향 기본 패딩 */
    val vertical: Dp = DmsSpacing.medium

    /** 스크린 상단 패딩 (StatusBar 아래) */
    val top: Dp = DmsSpacing.large

    /** 스크린 하단 패딩 (NavigationBar 위) */
    val bottom: Dp = DmsSpacing.large
}
```

#### Elevation.kt (신규 생성)

```kotlin
// /core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/Elevation.kt
package team.aliens.dms.android.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * DMS 디자인 시스템의 Elevation 토큰
 * Material 3 elevation system
 */
object DmsElevation {
    /** Level 0 - 기본 (그림자 없음) */
    val level0: Dp = 0.dp

    /** Level 1 - 매우 약한 그림자 (카드 기본) */
    val level1: Dp = 1.dp

    /** Level 2 - 약한 그림자 (카드 호버) */
    val level2: Dp = 3.dp

    /** Level 3 - 중간 그림자 (FAB 기본) */
    val level3: Dp = 6.dp

    /** Level 4 - 강한 그림자 (Navigation drawer) */
    val level4: Dp = 8.dp

    /** Level 5 - 매우 강한 그림자 (Modal) */
    val level5: Dp = 12.dp
}
```

#### Duration.kt (신규 생성)

```kotlin
// /core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/Duration.kt
package team.aliens.dms.android.core.designsystem

/**
 * DMS 디자인 시스템의 애니메이션 Duration 토큰
 *
 * Material Design 3 motion guidelines 기반
 */
object DmsDuration {
    /** 50ms - 매우 짧은 애니메이션 (ripple 시작) */
    const val INSTANT: Int = 50

    /** 100ms - 짧은 애니메이션 (작은 변화) */
    const val SHORT1: Int = 100

    /** 150ms - 짧은 애니메이션 (아이콘 변화) */
    const val SHORT2: Int = 150

    /** 200ms - 기본 짧은 애니메이션 */
    const val SHORT3: Int = 200

    /** 250ms - 중간 애니메이션 */
    const val MEDIUM1: Int = 250

    /** 300ms - 기본 중간 애니메이션 (화면 전환) */
    const val MEDIUM2: Int = 300

    /** 400ms - 긴 중간 애니메이션 */
    const val MEDIUM3: Int = 400

    /** 500ms - 긴 애니메이션 (복잡한 변화) */
    const val LONG1: Int = 500

    /** 600ms - 매우 긴 애니메이션 */
    const val LONG2: Int = 600
}

/**
 * Easing 곡선
 */
object DmsEasing {
    // Material 3 standard easing
    val standard = androidx.compose.animation.core.FastOutSlowInEasing
    val emphasized = androidx.compose.animation.core.CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
    val decelerated = androidx.compose.animation.core.FastOutSlowInEasing
    val accelerated = androidx.compose.animation.core.LinearOutSlowInEasing
}
```

#### Colors.kt 확장

기존 `Colors.kt`에 추가할 색상 역할:

```kotlin
// Colors.kt에 추가
data class Colors(
    // 기존 색상들...
    val primary: Color,
    val onPrimary: Color,
    // ...

    // 🆕 추가할 시맨틱 색상들
    /** 성공 상태 색상 */
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,

    /** 경고 상태 색상 */
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,

    /** 정보 상태 색상 */
    val info: Color,
    val onInfo: Color,
    val infoContainer: Color,
    val onInfoContainer: Color,

    /** 테두리/구분선 색상 */
    val border: Color,
    val divider: Color,
)

// Light theme 색상 정의
val lightColors = Colors(
    // 기존 색상들...

    // 성공
    success = Color(0xFF4CAF50),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFC8E6C9),
    onSuccessContainer = Color(0xFF1B5E20),

    // 경고
    warning = Color(0xFFFFA726),
    onWarning = Color(0xFF000000),
    warningContainer = Color(0xFFFFE0B2),
    onWarningContainer = Color(0xFFE65100),

    // 정보
    info = Color(0xFF2196F3),
    onInfo = Color(0xFFFFFFFF),
    infoContainer = Color(0xFFBBDEFB),
    onInfoContainer = Color(0xFF0D47A1),

    // 테두리
    border = Color(0xFFE0E0E0),
    divider = Color(0xFFBDBDBD),
)

// Dark theme 색상 정의
val darkColors = Colors(
    // 기존 색상들...

    // 성공
    success = Color(0xFF81C784),
    onSuccess = Color(0xFF000000),
    successContainer = Color(0xFF2E7D32),
    onSuccessContainer = Color(0xFFC8E6C9),

    // 경고
    warning = Color(0xFFFFB74D),
    onWarning = Color(0xFF000000),
    warningContainer = Color(0xFFF57C00),
    onWarningContainer = Color(0xFFFFE0B2),

    // 정보
    info = Color(0xFF64B5F6),
    onInfo = Color(0xFF000000),
    infoContainer = Color(0xFF1565C0),
    onInfoContainer = Color(0xFFBBDEFB),

    // 테두리
    border = Color(0xFF424242),
    divider = Color(0xFF616161),
)
```

### Theme.kt에 토큰 통합

```kotlin
// Theme.kt
object DmsTheme {
    val colorScheme: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    // 🆕 새로운 토큰들
    val spacing: DmsSpacing
        @Composable
        @ReadOnlyComposable
        get() = DmsSpacing

    val elevation: DmsElevation
        @Composable
        @ReadOnlyComposable
        get() = DmsElevation
}
```

---

## 9. 파일 구조 템플릿

### 새 컴포넌트 파일 템플릿

```kotlin
// /core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/[ComponentName]s.kt
package team.aliens.dms.android.core.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * DMS [ComponentName] 컴포넌트
 *
 * [Figma 링크 또는 간단한 설명]
 *
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the component is enabled or disabled
 * @param [otherParams] ...
 *
 * Example:
 * ```
 * MyComponent(
 *     text = "Hello",
 *     onClick = { /* ... */ }
 * )
 * ```
 */
@Composable
fun MyComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    // ... 다른 파라미터들
    onClick: () -> Unit = {},
) {
    // 구현
    Box(modifier = modifier) {
        // ...
    }
}

/**
 * Defaults for [MyComponent]
 */
object MyComponentDefaults {
    /**
     * Creates [MyComponentColors] with default values
     */
    @Composable
    fun colors(
        containerColor: Color = DmsTheme.colorScheme.primary,
        contentColor: Color = DmsTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = DmsTheme.colorScheme.surface,
        disabledContentColor: Color = DmsTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    ): MyComponentColors = MyComponentColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    /**
     * Default shape for [MyComponent]
     */
    val shape: Shape
        @Composable
        @ReadOnlyComposable
        get() = DmsTheme.shapes.medium

    /**
     * Default content padding for [MyComponent]
     */
    val contentPadding: PaddingValues = PaddingValues(
        horizontal = DmsSpacing.medium,
        vertical = DmsSpacing.small,
    )

    /**
     * Default minimum size for [MyComponent]
     */
    val minSize: Dp = 48.dp  // 접근성을 위한 최소 터치 영역
}

/**
 * Colors for [MyComponent]
 */
@Immutable
data class MyComponentColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
) {
    /**
     * Returns the container color based on [enabled] state
     */
    @Composable
    fun containerColor(enabled: Boolean): Color {
        return if (enabled) containerColor else disabledContainerColor
    }

    /**
     * Returns the content color based on [enabled] state
     */
    @Composable
    fun contentColor(enabled: Boolean): Color {
        return if (enabled) contentColor else disabledContentColor
    }
}

// ========================================
// Previews
// ========================================

@Preview(name = "Light Mode - Enabled")
@Composable
private fun MyComponentPreview() {
    DmsTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MyComponent(
                    enabled = true,
                    // ...
                )
            }
        }
    }
}

@Preview(name = "Dark Mode - Enabled", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyComponentDarkPreview() {
    DmsTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MyComponent(
                    enabled = true,
                    // ...
                )
            }
        }
    }
}

@Preview(name = "Disabled State")
@Composable
private fun MyComponentDisabledPreview() {
    DmsTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MyComponent(
                    enabled = false,
                    // ...
                )
            }
        }
    }
}

@Preview(name = "All States")
@Composable
private fun MyComponentAllStatesPreview() {
    DmsTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Enabled:", style = DmsTheme.typography.body1)
                MyComponent(enabled = true)

                Text("Disabled:", style = DmsTheme.typography.body1)
                MyComponent(enabled = false)

                // 다른 상태들...
            }
        }
    }
}
```

---

## 10. 빠른 참조: 주요 파일 경로

### 📂 디자인 시스템 루트
```
/Users/yuseob/Documents/android-project/DMS-Android/core/design-system/src/main/java/team/aliens/dms/android/core/designsystem/
```

### 🎨 테마 파일들

| 파일 | 경로 | 주의사항 |
|------|------|----------|
| **Colors.kt** | `.../designsystem/Colors.kt` | ⚠️ Line 144: `errorContainer` copy 버그 존재 |
| **Typography.kt** | `.../designsystem/Typography.kt` | ⚠️ Line 171: `headline2` toString 버그 존재 |
| **Shapes.kt** | `.../designsystem/Shapes.kt` | ⚠️ Line 23: `extraSmall` copy 버그 존재 |
| **Theme.kt** | `.../designsystem/Theme.kt` | 테마 설정 및 CompositionLocal 제공 |
| **Shadows.kt** | `.../designsystem/Shadows.kt` | Shadow modifier 정의 |

### 🧩 기존 컴포넌트들

| 파일 | 경로 | 설명 | 참고사항 |
|------|------|------|----------|
| **Buttons.kt** | `.../designsystem/Buttons.kt` | 버튼 컴포넌트들 | 18가지 color variants (참고용) |
| **TextFields.kt** | `.../designsystem/TextFields.kt` | 텍스트 입력 필드 | ⚠️ 755줄로 매우 복잡, 신규는 간단하게 |
| **Modal.kt** | `.../designsystem/Modal.kt` | ModalBottomSheet 래퍼 | |
| **Dialog.kt** | `.../designsystem/Dialog.kt` | AlertDialog 래퍼 | |
| **Toast.kt** | `.../designsystem/Toast.kt` | 커스텀 Toast | ⚠️ 복잡한 mutex 기반, M3 Snackbar 고려 |
| **Checkboxes.kt** | `.../designsystem/Checkboxes.kt` | Checkbox 래퍼 | |
| **RadioButtons.kt** | `.../designsystem/RadioButtons.kt` | RadioButton 래퍼 | |
| **Switches.kt** | `.../designsystem/Switches.kt` | Switch 래퍼 | |
| **Picker.kt** | `.../designsystem/Picker.kt` | 커스텀 Picker | ⚠️ Integer.MAX_VALUE 사용 (메모리 주의) |
| **Calendars.kt** | `.../designsystem/Calendars.kt` | Calendar 래퍼 | ⚠️ AndroidView 사용 (성능 이슈) |
| **Layout.kt** | `.../designsystem/Layout.kt` | Faded column 레이아웃 | |
| **Scaffold.kt** | `.../designsystem/Scaffold.kt` | Scaffold 래퍼 | |
| **AppBars.kt** | `.../designsystem/AppBars.kt` | TopAppBar 래퍼 | |

### 🔧 유틸리티

| 파일 | 경로 | 설명 | 참고사항 |
|------|------|------|----------|
| **Clickable.kt** | `.../designsystem/Clickable.kt` | 커스텀 clickable modifier | ⚠️ M2 ripple 사용 중, M3로 교체 필요 |
| **Animations.kt** | `.../designsystem/Animations.kt` | 네비게이션 애니메이션 | |
| **DmsIcon.kt** | `.../designsystem/DmsIcon.kt` | 아이콘 리소스 상수 | 42개 아이콘 정의 |

### ➕ 추가해야 할 파일들 (신규)

| 파일 | 경로 | 설명 |
|------|------|------|
| **Cards.kt** | `.../designsystem/Cards.kt` | Card 컴포넌트 (30+ 사용처) |
| **Icons.kt** | `.../designsystem/Icons.kt` | Icon/IconButton 래퍼 |
| **Chips.kt** | `.../designsystem/Chips.kt` | Chip 컴포넌트들 |
| **Progress.kt** | `.../designsystem/Progress.kt` | Progress indicators |
| **ListItems.kt** | `.../designsystem/ListItems.kt` | List item 템플릿 |
| **Snackbars.kt** | `.../designsystem/Snackbars.kt` | M3 Snackbar 래퍼 |
| **Spacing.kt** | `.../designsystem/Spacing.kt` | Spacing 토큰 시스템 |
| **Elevation.kt** | `.../designsystem/Elevation.kt` | Elevation 토큰 |
| **Duration.kt** | `.../designsystem/Duration.kt` | Animation duration 토큰 |

### 🏗️ Core UI 모듈 (일부 컴포넌트가 여기 있음)

```
/Users/yuseob/Documents/android-project/DMS-Android/core/ui/src/main/java/team/aliens/dms/android/core/ui/
```

| 파일 | 설명 | 이동 필요 |
|------|------|----------|
| **PaddingDefaults.kt** | 스크린 패딩 상수 | ✅ design-system으로 이동 필요 |
| **Banner.kt** | 배너 컴포넌트 | ✅ design-system으로 이동 필요 |
| **FloatingNotice.kt** | 플로팅 공지 | ✅ design-system으로 이동 필요 |
| **composable/PasswordTextField.kt** | 비밀번호 입력 필드 | ✅ design-system으로 이동 필요 |
| **composable/AppLogo.kt** | 앱 로고 | ✅ design-system으로 이동 필요 |

---

## 11. 구현 워크플로우

### 🎨 Figma 디자인을 받았을 때

#### 1단계: 디자인 토큰 먼저 추출

- [ ] **색상 팔레트 확인**
  - Figma에서 사용된 모든 색상 추출
  - `Colors.kt` 업데이트
  - Light/Dark 테마 모두 정의
  - ⚠️ Line 144 버그 수정 필요

- [ ] **타이포그래피 스케일 확인**
  - Figma Text Styles 추출
  - `Typography.kt` 업데이트
  - fontFamily, fontSize, fontWeight, lineHeight 정의
  - ⚠️ Line 171 버그 수정 필요

- [ ] **Spacing 값 확인**
  - Figma에서 자주 사용되는 spacing 확인
  - `Spacing.kt` 생성 또는 업데이트
  - DmsSpacing 객체에 추가

- [ ] **Corner Radius 확인**
  - Figma에서 사용된 corner radius 추출
  - `Shapes.kt` 업데이트
  - ⚠️ Line 23 버그 수정 필요

- [ ] **Elevation/Shadow 확인**
  - Drop shadow 효과 분석
  - `Elevation.kt` 생성 또는 업데이트

#### 2단계: 컴포넌트 구현 준비

- [ ] **Figma 컴포넌트 분석**
  - Variants 확인 (예: Default, Outlined, Elevated)
  - States 확인 (예: Enabled, Disabled, Pressed, Focused)
  - Properties 확인 (예: showIcon, size)
  - Auto Layout 구조 파악

- [ ] **기존 유사 컴포넌트 확인**
  - 비슷한 컴포넌트가 이미 있는지 확인
  - 있다면 확장 vs 새로 만들기 결정
  - 없다면 새로 생성

- [ ] **파일명 결정**
  - PascalCase + 복수형 (예: `Cards.kt`, `Chips.kt`)
  - 기존 파일과 충돌하지 않는지 확인

- [ ] **구현 체크리스트 확인**
  - [섹션 3](#3-컴포넌트-구현-체크리스트) 참고
  - 모든 필수 항목 숙지

#### 3단계: 컴포넌트 구현

- [ ] **파일 생성 및 기본 구조 작성**
  - [섹션 9](#9-파일-구조-템플릿)의 템플릿 사용
  - package 선언
  - 필요한 imports

- [ ] **모든 States 구현**
  - enabled / disabled
  - loading (필요시)
  - error (필요시)
  - focused / pressed (인터랙티브 컴포넌트)

- [ ] **접근성 추가**
  - `semantics { }` 블록
  - `contentDescription` 파라미터
  - `role` 명시
  - 최소 터치 영역 48.dp

- [ ] **Defaults 객체 작성**
  - `colors()` 함수
  - `shape` 속성
  - `contentPadding` 등
  - 디자인 토큰 사용

- [ ] **KDoc 작성**
  - 컴포넌트 설명
  - 파라미터 설명
  - 사용 예제

#### 4단계: Preview 작성

- [ ] **기본 Preview**
  - Light mode, enabled state
  - 다양한 변형 (variants)

- [ ] **다크모드 Preview**
  - `@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)`

- [ ] **각 상태별 Preview**
  - Disabled state
  - Loading state
  - Error state
  - 모든 states를 한눈에 보는 Preview

#### 5단계: 통합 및 테스트

- [ ] **빌드 확인**
  ```bash
  ./gradlew :core:design-system:build
  ```

- [ ] **새 화면에서 사용해보기**
  - 새로운 feature 화면에 적용
  - Preview로 먼저 확인
  - 실제 앱에서 동작 확인

- [ ] **기존 화면에서 테스트 (선택)**
  - 기존 화면 하나 골라서 마이그레이션
  - UI 동작 확인
  - 문제 없으면 다른 화면들도 점진적 마이그레이션

#### 6단계: 문서화

- [ ] **사용 예제 추가 (필요시)**
  - KDoc에 코드 예제
  - 복잡한 경우 별도 문서

- [ ] **마이그레이션 가이드 업데이트 (필요시)**
  - 기존 컴포넌트를 대체하는 경우
  - 기존 것에 `@Deprecated` 추가

- [ ] **이 가이드 문서 업데이트**
  - 새로 추가된 컴포넌트 기록
  - 특별한 주의사항 추가

---

## 📋 Quick Checklist (빠른 점검용)

새 컴포넌트 추가 시 이 체크리스트를 빠르게 확인하세요:

### 필수 항목
- [ ] 파일명: PascalCase + 복수형
- [ ] 위치: `/core/design-system/.../designsystem/`
- [ ] `modifier` 파라미터 첫 번째
- [ ] 디자인 토큰만 사용 (하드코딩 금지)
- [ ] 접근성: semantics, contentDescription, role
- [ ] 최소 터치 영역 48.dp (인터랙티브 컴포넌트)
- [ ] `Defaults` 객체 제공
- [ ] KDoc 작성
- [ ] `@Preview` 작성 (light, dark, states)

### 회피할 것
- [ ] ❌ `mutableStateOf` 사용하지 않기
- [ ] ❌ 하드코딩된 spacing/padding 금지
- [ ] ❌ Material 2 ripple 금지
- [ ] ❌ AndroidView 자제
- [ ] ❌ 도메인 로직 섞지 않기
- [ ] ❌ copy() 함수에서 다른 속성 참조 금지

### 성능
- [ ] `remember` 사용
- [ ] `derivedStateOf` 사용 (계산된 상태)
- [ ] 불필요한 recomposition 방지

### 테스트
- [ ] 빌드 성공 확인
- [ ] Preview에서 시각적 확인
- [ ] 실제 화면에서 동작 확인

---

## 💡 추가 팁

### Figma 플러그인 활용
- **Figma to Compose**: 자동 코드 생성 (참고용)
- **Design Tokens**: 토큰 추출 자동화

### 자주 하는 실수
1. **modifier를 맨 마지막에 두기** → 항상 첫 번째!
2. **Colors를 직접 참조** → DmsTheme.colorScheme 사용
3. **접근성 무시** → semantics 필수
4. **Preview 없이 커밋** → 반드시 Preview 추가
5. **하드코딩된 값** → 디자인 토큰 사용

### 유용한 Compose 패턴
```kotlin
// State hoisting
@Composable
fun MyComponent(
    value: String,
    onValueChange: (String) -> Unit,
) {
    // Stateless component
}

// Remember with key
val state = remember(key) {
    // Only recompute when key changes
}

// Derived state
val isValid by remember {
    derivedStateOf {
        value.length >= 8
    }
}

// Side effects
LaunchedEffect(key) {
    // Runs when key changes
}
```

---

## 🔗 참고 자료

### Material Design 3
- [Material 3 Guidelines](https://m3.material.io/)
- [Compose Material 3](https://developer.android.com/jetpack/compose/designsystems/material3)

### Jetpack Compose
- [Compose Documentation](https://developer.android.com/jetpack/compose)
- [Accessibility in Compose](https://developer.android.com/jetpack/compose/accessibility)
- [Performance in Compose](https://developer.android.com/jetpack/compose/performance)

### DMS 프로젝트
- Git Repository: `/Users/yuseob/Documents/android-project/DMS-Android`
- Main Branch: `develop`

---

**이 문서는 지속적으로 업데이트됩니다.**
**새로운 컴포넌트를 추가하거나 문제를 발견하면 이 문서를 업데이트해주세요.**
