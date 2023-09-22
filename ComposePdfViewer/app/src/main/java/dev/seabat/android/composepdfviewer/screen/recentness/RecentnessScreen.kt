package dev.seabat.android.composepdfviewer.screen.recentness

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.seabat.android.composepdfviewer.component.Loading
import dev.seabat.android.composepdfviewer.entities.Pdf
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentnessScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentnessViewModel,
    onClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pdfs = listOf<Pdf>(Pdf("title", "desc", 1, Date()))

    HomeScreenContent(
        uiState = uiState,
        pdfs = pdfs,
        onClick = {}
    )
}

@Composable
fun HomeScreenContent(
    uiState: RecentnessViewModel.UiState,
    modifier: Modifier = Modifier,
    pdfs: List<Pdf>,
    onClick: (Pdf) -> Unit,
) {
    when (uiState.state) {
       is RecentnessViewModel.UiStateType.Loading -> {
           Loading()
       }
       is RecentnessViewModel.UiStateType.Loaded -> {
           LazyColumn(
//            modifier.fillMaxSize()
           ) {
               pdfs.forEach { pdf ->
                   item { PdfItem(pdf = pdf, onClick = onClick) }
                   item { Divider(Modifier.padding(start = 16.dp)) }
               }
           }
        }
       is RecentnessViewModel.UiStateType.Error -> {

       }
    }
}

@Composable
fun PdfItem(
    pdf: Pdf,
    onClick: (Pdf) -> Unit,
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
                pdf.description ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            )
        }
    }
}
