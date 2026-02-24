package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.DeleteTipoPenalidadUseCase
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.ObserveTipoPenalidadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    private val observeTipoPenalidadUseCase: ObserveTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListTipoPenalidadUiState(isLoading = true))
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        loadPenalidades()
    }

    fun onEvent(event: ListTipoPenalidadUiEvent) {
        when (event) {
            ListTipoPenalidadUiEvent.Load -> loadPenalidades()
            ListTipoPenalidadUiEvent.Refresh -> loadPenalidades()
            is ListTipoPenalidadUiEvent.Delete -> onDelete(event.id)
            ListTipoPenalidadUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListTipoPenalidadUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListTipoPenalidadUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListTipoPenalidadUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
        }
    }

    private fun loadPenalidades() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeTipoPenalidadUseCase().collectLatest { penalidadesList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        penalidades = penalidadesList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteTipoPenalidadUseCase(id)
             onEvent(ListTipoPenalidadUiEvent.ShowMessage("Penalidad eliminada"))
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
