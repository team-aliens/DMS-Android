package team.aliens.local_database.datasource.implementation

import com.example.local_database.dao.StudyRoomDao
import com.example.local_database.datasource.declaration.LocalStudyRoomDataSource
import com.example.local_database.entity.studyroom.FetchApplyTimeRoomEntity
import javax.inject.Inject

class LocalStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomDao: StudyRoomDao,
) : LocalStudyRoomDataSource {
    //TODO: 나중에 합쳐지면 그냥 Entitiy로 바꿔야 함 .toEntity()
    override suspend fun fetchApplyTime(): FetchApplyTimeRoomEntity =
        studyRoomDao.fetchApplyTime()

    override suspend fun saveApplyTime(fetchApplyTimeRoomEntity: FetchApplyTimeRoomEntity) {
        studyRoomDao.insertApplyTime(fetchApplyTimeRoomEntity)
    }
}