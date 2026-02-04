package edu.ucne.registrodeestudiantes.Presentation.Penalidad.Edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.DeleteTipoPenalidadUseCase
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.GetTipoPenalidadUseCase
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase.UpsertTipoPenalidadUseCase
import edu.ucne.registrodeestudiantes.Domain.Penalidad.validateDescripcion
import edu.ucne.registrodeestudiantes.Domain.Penalidad.validateNombre
import edu.ucne.registrodeestudiantes.Domain.Penalidad.validatePuntos
import edu.ucne.registrodeestudiantes.Presentation.Navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val penalidadId: Int = savedStateHandle.toRoute<Screen.Penalidad>().penalidadId

    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state: StateFlow<EditTipoPenalidadUiState> = _state.asStateFlow()

    init {
        loadPenalidad(penalidadId)
    }

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.Load -> loadPenalidad(event.id)
            is EditTipoPenalidadUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }
            is EditTipoPenalidadUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }
            is EditTipoPenalidadUiEvent.PuntosChanged -> {
                val puntosInt = event.value.toIntOrNull()
                _state.update { it.copy(puntosDescuento = puntosInt, puntosError = null) }
            }
            EditTipoPenalidadUiEvent.Save -> onSave()
            EditTipoPenalidadUiEvent.Delete -> onDelete()
        }
    }

    private fun loadPenalidad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, tipoId = null) }
            return
        }

        viewModelScope.launch {
            val penalidad = getTipoPenalidadUseCase(id)
            if (penalidad != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        tipoId = penalidad.tipoId,
                        nombre = penalidad.nombre,
                        descripcion = penalidad.descripcion,
                        puntosDescuento = penalidad.puntosDescuento
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, tipoId = null) }
            }
        }
    }

    private fun onSave() {
        if (state.value.isSaving) return

        val nombreValidation = validateNombre(state.value.nombre)
        val descripcionValidation = validateDescripcion(state.value.descripcion)
        val puntosValidation = validatePuntos(state.value.puntosDescuento)

        if (!nombreValidation.isValid || !descripcionValidation.isValid || !puntosValidation.isValid) {
            _state.update {
                it.copy(
                    nombreError = nombreValidation.error,
                    descripcionError = descripcionValidation.error,
                    puntosError = puntosValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val penalidad = TipoPenalidad(
                tipoId = state.value.tipoId ?: 0,
                nombre = state.value.nombre,
                descripcion = state.value.descripcion,
                puntosDescuento = state.value.puntosDescuento ?: 0
            )

            try {
                upsertTipoPenalidadUseCase(penalidad)
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        isNew = false
                    )
                }
            } catch (e: Exception) {
                 _state.update { it.copy(isSaving = false, generalError = e.message) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.tipoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteTipoPenalidadUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
               _state.update { it.copy(isDeleting = false, generalError = e.message) }
            }
        }
    }
}
