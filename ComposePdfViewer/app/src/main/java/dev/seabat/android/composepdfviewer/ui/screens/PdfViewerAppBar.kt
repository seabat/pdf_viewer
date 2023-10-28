package dev.seabat.android.composepdfviewer.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.seabat.android.composepdfviewer.MainViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.appbar.PickPdf

@Composable
fun PdfViewerAppBar(
    navController: NavHostController,
    onPdfImported: ((PdfEntity) -> Unit)? = null
) {
    val viewModel = hiltViewModel<MainViewModel>()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = getScreen(
        backStackEntry?.destination?.route ?: Screen.Favorite.route
    )

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.appBarTitleResId),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        navigationIcon = if (currentScreen.shouldShowTopClose) {
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
        actions = if (currentScreen.shouldShowAddAction) {
            {
                AddFileAction(
                    onPdfSelected = { uri ->
                        viewModel.importPdf(uri) { pdf ->
                            if (onPdfImported != null) {
                                onPdfImported(pdf)
                            }
                        }
                    }
                )
            }
        } else {
            {}
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
