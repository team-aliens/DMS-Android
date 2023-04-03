package team.aliens.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import team.aliens.data._datasource.local.LocalStudentDataSource
import javax.inject.Inject

class LocalStudentDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalStudentDataSource {


}
