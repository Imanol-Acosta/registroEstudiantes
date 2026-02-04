package edu.ucne.registrodeestudiantes.Presentation.Asignatura.Edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.DeleteAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.GetAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.UpsertAsignaturaUseCase
import edu.ucne.registrodeestudiantes.Domain.Asignatura.validateAula
import edu.ucne.registrodeestudiantes.Domain.Asignatura.validateCodigo
import edu.ucne.registrodeestudiantes.Domain.Asignatura.validateCreditos
import edu.ucne.registrodeestudiantes.Domain.Asignatura.validateNombre
import edu.ucne.registrodeestudiantes.Presentation.Navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val asignaturaId: Int = savedStateHandle.toRoute<Screen.Asignatura>().asignaturaId

    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    init {
        loadAsignatura(asignaturaId)
    }

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.Load -> loadAsignatura(event.id)
            is EditAsignaturaUiEvent.CodigoChanged -> _state.update {
                it.copy(codigo = event.value, codigoError = null)
            }
            is EditAsignaturaUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }
            is EditAsignaturaUiEvent.AulaChanged -> _state.update {
                it.copy(aula = event.value, aulaError = null)
            }
            is EditAsignaturaUiEvent.CreditosChanged -> _state.update {
                it.copy(creditos = event.value, creditosError = null)
            }
            EditAsignaturaUiEvent.Save -> onSave()
            EditAsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun loadAsignatura(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, asignaturaId = null) }
            return
        }

        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            if (asignatura != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString()
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, asignaturaId = null) }
            }
        }
    }

    private fun onSave() {
        if (state.value.isSaving) return

        val codigoValidation = validateCodigo(state.value.codigo)
        val nombreValidation = validateNombre(state.value.nombre)
        val aulaValidation = validateAula(state.value.aula)
        val creditosInt = state.value.creditos.toIntOrNull()
        val creditosValidation = validateCreditos(creditosInt)

        if (!codigoValidation.isValid || !nombreValidation.isValid || !aulaValidation.isValid || !creditosValidation.isValid) {
            _state.update {
                it.copy(
                    codigoError = codigoValidation.error,
                    nombreError = nombreValidation.error,
                    aulaError = aulaValidation.error,
                    creditosError = creditosValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val asignatura = Asignatura(
                asignaturaId = state.value.asignaturaId ?: 0,
                codigo = state.value.codigo,
                nombre = state.value.nombre,
                aula = state.value.aula,
                creditos = creditosInt ?: 0
            )

            try {
                upsertAsignaturaUseCase(asignatura)
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
        val id = state.value.asignaturaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteAsignaturaUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
               _state.update { it.copy(isDeleting = false, generalError = e.message) }
            }
        }
    }
}
