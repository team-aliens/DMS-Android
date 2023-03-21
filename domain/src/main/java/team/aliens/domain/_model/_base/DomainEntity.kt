package team.aliens.domain._model._base


/**
 * @author junsuPark
 * An annotation expresses target class is an 'entity', some code generating could be added in the future
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class DomainEntity