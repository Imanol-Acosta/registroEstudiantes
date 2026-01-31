package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.ObserveAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.DeleteAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaListViewModel @Inject constructor(
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            observeAsignaturaUseCase().collect { asignaturas ->
                _uiState.update { it.copy(asignaturas = asignaturas, isLoading = false) }
            }
        }
    }

    fun delete(asignatura: Asignatura) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(asignatura)
        }
    }
}
