package dev.seabat.android.composepdfviewer.data.repository

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.exception.PdfViewerException
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import dev.seabat.android.composepdfviewer.utils.getFileInfoFromUri
import dev.seabat.android.composepdfviewer.utils.getFileTimeStamp
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class LocalFileRepository @Inject constructor(@ApplicationContext private val context: Context) :
    LocalFileRepositoryContract {

    /**
     * アプリのプライベート領域の files ディレクトリに格納されたファイル一覧を取得する
     *
     * @return PdfListEntity
     */
    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            val filesDir = context.filesDir
            val fileList = filesDir.listFiles { _, name ->
                name.lowercase(Locale.ROOT).endsWith(".pdf")
            } ?: return@withContext PdfListEntity(mutableListOf())

            val pdfEntities = fileList.toList().map {
                val fileDateTimeString = getFileTimeStamp(
                    it
                ).format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                PdfResourceEntity(
                    it.name,
                    it.name,
                    it.absolutePath,
                    it.length(),
                    fileDateTimeString,
                    fileDateTimeString
                )
            }
            PdfListEntity(pdfEntities.toMutableList())
        }
    }

    /**
     * Uri に配置されたファイルをアプリのプライベート領域の files ディレクトリに追加する
     *
     * @param uri
     */
    @Throws(PdfViewerException::class)
    override suspend fun add(uri: Uri): PdfResourceEntity {
        val fileInfo = getFileInfoFromUri(context, uri)
            ?: throw PdfViewerException(
                "Uri からファイル情報を取得できませんでした"
            )
        return fileInfo.first?.let { fileName ->
            copyPdfToInternalStorage(fileName, uri)
            PdfResourceEntity(
                fileName,
                fileName,
                "${context.filesDir.absolutePath}/$fileName",
                fileInfo.second,
                getNowTimeStamp(),
                getNowTimeStamp()
            )
        } ?: throw PdfViewerException("Uri からファイル名を取得できませんでした")
    }

    @Throws(PdfViewerException::class)
    private fun copyPdfToInternalStorage(fileName: String, uri: Uri) {
        runCatching {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        }.onFailure {
            throw PdfViewerException("ファイルのコピーに失敗しました")
        }
    }

    /**
     * アセットファイルをアプリのプライベート領域の files ディレクトリに追加する
     *
     * @return
     */
    @Throws(PdfViewerException::class)
    override suspend fun importAssetsFile(): PdfResourceEntity {
        val outputFile = File(context.filesDir, "sample.pdf")
        runCatching {
            context.assets.open("sample.pdf").use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }.onFailure {
            throw PdfViewerException("サンプルのインポートに失敗しました")
        }

        val fileDateTimeString = getFileTimeStamp(
            outputFile
        ).format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        return PdfResourceEntity(
            outputFile.name,
            outputFile.name,
            "${context.filesDir.absolutePath}/${outputFile.name}",
            outputFile.length(),
            fileDateTimeString,
            fileDateTimeString
        )
    }

    /**
     * アプリのプライベート領域の files ディレクトリに格納されたファイルを削除する
     *
     * @param fileName ex. sample.pdf
     */
    override suspend fun remove(fileName: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                val file = File(context.filesDir, fileName)
                if (file.exists()) {
                    val deleted = file.delete()
                    if (!deleted) {
                        throw PdfViewerException("ファイルの削除に失敗しました")
                    }
                }
            }.onFailure {
                throw PdfViewerException("ファイル削除時に予期しないエラーが発生しました")
            }
        }
    }
}