package dev.seabat.android.composepdfviewer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.seabat.android.composepdfviewer.R
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp

@Composable
fun PdfListItem(
    pdf: PdfResourceEntity,
    onClick: (PdfResourceEntity) -> Unit,
    onMoreHorizClick: (PdfResourceEntity) -> Unit
) {
    Surface(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(pdf) }
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text(pdf.title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        pdf.fileName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                    Text(
                        text = "${convertBytes(pdf.size)}",
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onMoreHorizClick(pdf)
                }
            )
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
        PdfResourceEntity(
            "タイトル",
            "ファイル名",
            "ファイルパス",
            178,
            getNowTimeStamp(),
            getNowTimeStamp()
        ),
        {},
        {}
    )
}
