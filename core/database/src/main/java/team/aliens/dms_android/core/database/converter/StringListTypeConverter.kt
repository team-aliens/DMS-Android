package team.aliens.dms_android.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

@ProvidedTypeConverter
internal class StringListTypeConverter @Inject constructor(moshi: Moshi) {

    private val stringListType: ParameterizedType = Types.newParameterizedType(
        List::class.java,
        String::class.java,
    )

    private val stringListAdapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

    @TypeConverter
    fun jsonToListOfStrings(value: String): List<String> {
        val decodedValue = stringListAdapter.fromJson(value)
        return decodedValue ?: throw IOException()
    }

    @TypeConverter
    fun listOfStringsToJson(value: List<String>): String {
        val encodedValue = stringListAdapter.toJson(value)
        return encodedValue ?: throw IOException()
    }
}
