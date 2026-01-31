package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.ObserveTipoPenalidadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TipoPenalidadListUiState(
    val penalidades: List<TipoPenalidad> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class TipoPenalidadListViewModel @Inject constructor(
    private val observeTipoPenalidadUseCase: ObserveTipoPenalidadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            observeTipoPenalidadUseCase().collect { list ->
                _uiState.update { it.copy(penalidades = list, isLoading = false) }
            }
        }
    }
}
