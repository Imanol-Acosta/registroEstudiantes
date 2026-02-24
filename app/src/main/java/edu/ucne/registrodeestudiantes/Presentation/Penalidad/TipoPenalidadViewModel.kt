package edu.ucne.registrodeestudiantes.Presentation.Penalidad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val validateTipoPenalidadUseCase: ValidateTipoPenalidadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadUiState())
    val uiState = _uiState.asStateFlow()

    fun load(id: Int) {
        if (id > 0) {
            viewModelScope.launch {
                getTipoPenalidadUseCase(id)?.let { penalidad ->
                    _uiState.update { it.copy(
                        tipoId = penalidad.tipoId,
                        nombre = penalidad.nombre,
                        descripcion = penalidad.descripcion,
                        puntosDescuento = penalidad.puntosDescuento.toString()
                    ) }
                }
            }
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, nombreError = null) }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update { it.copy(descripcion = descripcion, descripcionError = null) }
    }

    fun onPuntosChange(puntos: String) {
        _uiState.update { it.copy(puntosDescuento = puntos, puntosError = null) }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            val validation = validateTipoPenalidadUseCase(
                nombre = state.nombre,
                descripcion = state.descripcion,
                puntos = state.puntosDescuento,
                currentId = state.tipoId
            )

            if (!validation.isValid) {
                _uiState.update { it.copy(
                    nombreError = validation.nombreError,
                    descripcionError = validation.descripcionError,
                    puntosError = validation.puntosError
                ) }
                return@launch
            }

            val penalidad = TipoPenalidad(
                tipoId = state.tipoId,
                nombre = state.nombre,
                descripcion = state.descripcion,
                puntosDescuento = state.puntosDescuento.toInt()
            )

            upsertTipoPenalidadUseCase(penalidad).onSuccess {
                _uiState.update { it.copy(success = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
