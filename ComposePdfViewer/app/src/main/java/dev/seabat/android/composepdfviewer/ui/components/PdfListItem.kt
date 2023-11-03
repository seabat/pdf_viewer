package dev.seabat.android.composepdfviewer.ui.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PdfListItem(
    pdf: PdfEntity,
    onClick: (PdfEntity) -> Unit,
) {
    Surface(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(pdf) }
            .height(60.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(pdf.title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    pdf.fileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${convertBytes(pdf.size)}",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                )
            }
        }
    }
}

fun convertBytes(bytes: Long): String {
    val kilobytes = bytes / 1024.0
    val megabytes = kilobytes / 1024.0
    val gigabytes = megabytes / 1024.0

    return when {
        gigabytes >= 1.0 -> "%.2f GB".format(gigabytes)
        megabytes >= 1.0 -> "%.2f MB".format(megabytes)
        kilobytes >= 1.0 -> "%.2f KB".format(kilobytes)
        else -> "%d B".format(bytes)
    }
}

@Preview
@Composable
fun PreviewPdfListItem() {
    PdfListItem(
        PdfEntity(
            "タイトル",
            "ファイル名",
            "ファイルパス",
            178,
            getNowTimeStamp(),
            getNowTimeStamp()
        )
    ){}
}
