package dev.seabat.android.composepdfviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.usecase.ImportSampleUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val importSampleUseCase: ImportSampleUseCaseContract
) : ViewModel() {

    fun copyPdfToInternalStorage() {
        viewModelScope.launch {
            importSampleUseCase()
        }
    }
}