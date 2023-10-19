package team.aliens.dms.android.core.designsystem.annotation

/**
 * 더 이상 업데이트 되지 않는 컴포넌트임을 의미합니다.
 *
 * 해당 컴포넌트는 추후 DMS의 새로운 디자인 시스템인 DDS로 교체되어야 합니다.
 * https://github.com/team-aliens/DDS-Android
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPEALIAS
)
@MustBeDocumented
public annotation class DormDeprecated(
    val replaceWith: ReplaceWith = ReplaceWith(""),
    val level: DeprecationLevel = DeprecationLevel.WARNING
)