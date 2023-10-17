package dev.seabat.android.composepdfviewer.ui.appbar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.MainViewModel
import dev.seabat.android.composepdfviewer.ScaffoldState
import dev.seabat.android.composepdfviewer.ui.screen.Screen
import dev.seabat.android.composepdfviewer.ui.screen.recentness.RecentnessViewModel

@Composable
fun PdfViewerAppBar(
    navController: NavHostController,
    currentScreen: Screen,
    scaffoldState: State<ScaffoldState>,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.appBarTitleResId),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        navigationIcon = if (scaffoldState.value.shouldShowTopClose) {
            {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }
            }
        } else {
            null
        },
        actions = {
            AddFileAction(
                onPdfSelected = { uri -> viewModel.onPdfSelected(uri) }
            )
        }
    )
}

@Composable
fun AddFileAction(onPdfSelected: (Uri) -> Unit) {
    val pickPdfLauncher = rememberLauncherForActivityResult(PickPdf()) { result ->
        result?.let {
            onPdfSelected(result)
        }
    }
    IconButton(onClick = {
        pickPdfLauncher.launch(Unit)
    }) {
        Icon(
            Icons.Filled.AddCircle,
            contentDescription = "PDFファイルを追加",
            modifier = Modifier.size(35.dp),
            tint = MaterialTheme.colorScheme.primaryContainer
        )
    }
}
