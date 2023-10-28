package dev.seabat.android.composepdfviewer.ui.screens.recentness

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.UiStateType
import java.lang.Exception
import java.util.Date

@Composable
fun RecentnessScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentnessViewModel,
    onClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    RecentnessScreenContent(
        uiState = uiState,
        onRefresh = { viewModel.reload() },
        onClick = {}
    )
}

@Composable
fun RecentnessScreenContent(
    uiState: RecentnessUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (PdfEntity) -> Unit,
) {
    when (uiState.state) {
       is UiStateType.Loading -> {
           LoadingComponent()
       }
       is UiStateType.Loaded -> {
           LazyColumn {
               uiState.pdfs.forEach { pdf ->
                   item { PdfItem(pdf = pdf, onClick = onClick) }
                   item { Divider(Modifier.padding(start = 16.dp, end = 16.dp)) }
               }
           }
        }
        is UiStateType.Error -> {
           ErrorComponent(uiState.state.e) {
               onRefresh()
           }
       }
    }
}

@Composable
fun PdfItem(
    pdf: PdfEntity,
    onClick: (PdfEntity) -> Unit,
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .clickable { onClick(pdf) }
            .padding(horizontal = 16.dp)
            .height(60.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(pdf.title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(
                pdf.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            )
        }
    }
}

@Preview
@Composable
fun `Loaded状態のHomeScreenContent`() {
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = UiStateType.Loaded,
            pdfs = PdfListEntity(
                mutableListOf(
                    PdfEntity("title1", "desc1", 178, Date()),
                    PdfEntity("title2", "desc2", 298, Date()),
                    PdfEntity("title3", "desc3", 587, Date()),
                    PdfEntity("title4", "desc4", 319, Date()),
                    PdfEntity("title5", "desc5", 287, Date())
                )
            )
        ),
        onRefresh = {},
        onClick =  {},
    )
}

@Preview
@Composable
fun `Loading状態のHomeScreenContent`() {
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = UiStateType.Loading,
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
    )
}

@Preview
@Composable
fun `Error状態のHomeScreenContent`() {
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = UiStateType.Error(Exception("エラー内容")),
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
    )
}
