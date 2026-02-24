package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.DeleteAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.ObserveAsignaturaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListAsignaturaUiState(isLoading = true))
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        loadAsignaturas()
    }

    fun onEvent(event: ListAsignaturaUiEvent) {
        when (event) {
            ListAsignaturaUiEvent.Load -> loadAsignaturas()
            ListAsignaturaUiEvent.Refresh -> loadAsignaturas()
            is ListAsignaturaUiEvent.Delete -> onDelete(event.id)
            ListAsignaturaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListAsignaturaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListAsignaturaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListAsignaturaUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
        }
    }

    private fun loadAsignaturas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeAsignaturaUseCase().collectLatest { asignaturasList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        asignaturas = asignaturasList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(id)
             onEvent(ListAsignaturaUiEvent.ShowMessage("Asignatura eliminada"))
        }
    }

    fun onNavigationHandled() {
        _state.update {
            it.copy(
                navigateToCreate = false,
                navigateToEditId = null,
                message = null
            )
        }
    }
}
