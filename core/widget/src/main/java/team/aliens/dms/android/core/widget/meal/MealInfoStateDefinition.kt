package team.aliens.dms.android.core.widget.meal

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object MealInfoStateDefinition : GlanceStateDefinition<MealInfo> {

    private const val DATA_STORE_FILENAME = "mealInfo"

    private val Context.datastore by dataStore(DATA_STORE_FILENAME, MealInfoSerializer)
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<MealInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object MealInfoSerializer : Serializer<MealInfo> {
        override val defaultValue: MealInfo
            get() = MealInfo.Loading

        override suspend fun readFrom(input: InputStream): MealInfo = try {
            Json.decodeFromString(
                MealInfo.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Could not read meal data: ${exception.message}")
        }

        override suspend fun writeTo(t: MealInfo, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(MealInfo.serializer(), t).encodeToByteArray()
                )
            }
        }

    }
}
