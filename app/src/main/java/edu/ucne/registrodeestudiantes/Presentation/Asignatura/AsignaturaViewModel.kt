package edu.ucne.registrodeestudiantes.Presentation.Asignatura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val validateAsignaturaUseCase: ValidateAsignaturaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaUiState())
    val uiState = _uiState.asStateFlow()

    fun load(id: Int) {
        if (id > 0) {
            viewModelScope.launch {
                getAsignaturaUseCase(id)?.let { asignatura ->
                    _uiState.update { it.copy(
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString()
                    ) }
                }
            }
        }
    }

    fun onCodigoChange(codigo: String) {
        _uiState.update { it.copy(codigo = codigo, error = null) }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, error = null) }
    }

    fun onAulaChange(aula: String) {
        _uiState.update { it.copy(aula = aula, error = null) }
    }

    fun onCreditosChange(creditos: String) {
        _uiState.update { it.copy(creditos = creditos, error = null) }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            
            val validation = validateAsignaturaUseCase(
                codigo = state.codigo,
                nombre = state.nombre,
                aula = state.aula,
                creditos = state.creditos,
                currentId = state.asignaturaId
            )

            if (!validation.isValid) {
                val firstError = validation.nombreError 
                    ?: validation.codigoError 
                    ?: validation.aulaError 
                    ?: validation.creditosError
                _uiState.update { it.copy(error = firstError) }
                return@launch
            }

            val asignatura = Asignatura(
                asignaturaId = state.asignaturaId,
                codigo = state.codigo,
                nombre = state.nombre,
                aula = state.aula,
                creditos = state.creditos.toInt()
            )

            upsertAsignaturaUseCase(asignatura).onSuccess {
                _uiState.update { it.copy(success = true, error = null) }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
