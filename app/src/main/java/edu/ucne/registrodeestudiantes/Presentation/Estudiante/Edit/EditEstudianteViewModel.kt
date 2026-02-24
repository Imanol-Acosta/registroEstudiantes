package edu.ucne.registrodeestudiantes.Presentation.Estudiante.Edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase.DeleteEstudianteUseCase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase.GetEstudianteUseCase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase.UpsertEstudianteUseCase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.validateEdad
import edu.ucne.registrodeestudiantes.Domain.Estudiante.validateEmail
import edu.ucne.registrodeestudiantes.Domain.Estudiante.validateNombres
import edu.ucne.registrodeestudiantes.Presentation.Navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val estudianteId: Int = savedStateHandle.toRoute<Screen.Estudiante>().estudianteId

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    init {
        loadEstudiante(estudianteId)
    }

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.Load -> loadEstudiante(event.id)
            is EditEstudianteUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }
            is EditEstudianteUiEvent.EmailChanged -> _state.update {
                it.copy(email = event.value, emailError = null)
            }
            is EditEstudianteUiEvent.EdadChanged -> {
                 val edadInt = event.value.toIntOrNull()
                _state.update { it.copy(edad = edadInt, edadError = null) }
            }
            EditEstudianteUiEvent.Save -> onSave()
            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun loadEstudiante(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, estudianteId = null) }
            return
        }

        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            if (estudiante != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        estudianteId = estudiante.estudianteId,
                        nombres = estudiante.nombres,
                        email = estudiante.email,
                        edad = estudiante.edad
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, estudianteId = null) }
            }
        }
    }

    private fun onSave() {
        if (state.value.isSaving) return

        val nombresValidation = validateNombres(state.value.nombres)
        val emailValidation = validateEmail(state.value.email)
        val edadValidation = validateEdad(state.value.edad)

        if (!nombresValidation.isValid || !emailValidation.isValid || !edadValidation.isValid) {
            _state.update {
                it.copy(
                    nombresError = nombresValidation.error,
                    emailError = emailValidation.error,
                    edadError = edadValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val estudiante = Estudiante(
                estudianteId = state.value.estudianteId ?: 0,
                nombres = state.value.nombres,
                email = state.value.email,
                edad = state.value.edad ?: 0
            )

            try {
                upsertEstudianteUseCase(estudiante)
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        isNew = false
                    )
                }
            } catch (e: Exception) {
                 _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.estudianteId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteEstudianteUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
               _state.update { it.copy(isDeleting = false) }
            }
        }
    }
}
