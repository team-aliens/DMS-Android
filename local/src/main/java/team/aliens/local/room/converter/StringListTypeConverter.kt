package team.aliens.local.room.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

@ProvidedTypeConverter
internal class StringListTypeConverter @Inject constructor(
    moshi: Moshi,
) {

    private val stringListType: ParameterizedType = Types.newParameterizedType(
        List::class.java,
        String::class.java,
    )

    private val stringListAdapter: JsonAdapter<List<String>> = moshi.adapter(
        stringListType,
    )

    @TypeConverter
    fun fromString(
        value: String,
    ): List<String> {

        val decodedValue = stringListAdapter.fromJson(value)

        return decodedValue ?: throw IOException()
    }

    @TypeConverter
    fun fromListOfStrings(
        value: List<String>,
    ): String {

        val decodedValue = stringListAdapter.toJson(value)

        return decodedValue ?: throw IOException()
    }
}
