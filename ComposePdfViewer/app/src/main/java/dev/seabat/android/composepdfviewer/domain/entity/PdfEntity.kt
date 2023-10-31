package dev.seabat.android.composepdfviewer.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi


@JsonClass(generateAdapter = true)
data class PdfEntity(
    @Json(name = "title") val title: String,
    @Json(name = "pathString") val pathString: String,
    @Json(name = "size") val size: Long,
    @Json(name = "dateString") val dateString: String

) {
    companion object {
        fun convertObjectToJson(pdfEntity: PdfEntity): String {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(PdfEntity::class.java)
            return jsonAdapter.toJson(pdfEntity)
        }

        fun convertJsonToObject(jsonString: String): PdfEntity? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(PdfEntity::class.java)
            return jsonAdapter.fromJson(jsonString)
        }
    }
}
