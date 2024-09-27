package dev.seabat.android.composepdfviewer.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class PdfResourceEntity(
    @Json(name = "title") val title: String,
    @Json(name = "file_name") val fileName: String,
    @Json(name = "path_string") val pathString: String,
    @Json(name = "size") val size: Long,
    @Json(name = "imported_date_string") val importedDateString: String,
    @Json(name = "opened_date_string") val openedDateString: String
) {
    companion object {
        fun convertObjectToJson(pdfEntity: PdfResourceEntity): String {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(PdfResourceEntity::class.java)
            return jsonAdapter.toJson(pdfEntity)
        }

        fun convertJsonToObject(jsonString: String): PdfResourceEntity? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(PdfResourceEntity::class.java)
            return jsonAdapter.fromJson(jsonString)
        }
    }
}
