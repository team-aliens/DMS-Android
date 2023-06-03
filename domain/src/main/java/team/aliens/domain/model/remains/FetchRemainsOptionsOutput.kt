package team.aliens.domain.model.remains

import java.util.UUID

/**
 * A response returned when fetching remains options
 * @property remainOptions list of remain options
 */
data class FetchRemainsOptionsOutput(
    val remainOptions: List<RemainsOptionInformation>,
) {

    /**
     * A set of information about remains option
     * @property id the unique id of the remains option
     * @property title the title of the remains option
     * @property description description of the remains option
     * @property applied a boolean value whether current user applied the remains option
     */
    data class RemainsOptionInformation(
        val id: UUID,
        val title: String,
        val description: String,
        val applied: Boolean,
    )
}

fun FetchRemainsOptionsOutput.RemainsOptionInformation.toModel(): RemainsOption {
    return RemainsOption(
        id = this.id,
        title = this.title,
        description = this.description,
        applied = this.applied,
    )
}

fun List<FetchRemainsOptionsOutput.RemainsOptionInformation>.toModel(): List<RemainsOption> {
    return this.map(FetchRemainsOptionsOutput.RemainsOptionInformation::toModel)
}

fun FetchRemainsOptionsOutput.toModel(): List<RemainsOption> {
    return this.remainOptions.toModel()
}
