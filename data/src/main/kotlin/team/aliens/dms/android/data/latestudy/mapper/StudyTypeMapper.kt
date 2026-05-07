package team.aliens.dms.android.data.latestudy.mapper

import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse

fun FetchStudyTypesResponse.toModel(): List<StudyType> =
    types.map {
        StudyType(
            id = it.id,
            name = it.name,
        )
    }
