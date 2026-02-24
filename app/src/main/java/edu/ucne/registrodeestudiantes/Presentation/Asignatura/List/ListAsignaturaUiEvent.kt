package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

sealed interface ListAsignaturaUiEvent {
    data object Load : ListAsignaturaUiEvent
    data object Refresh : ListAsignaturaUiEvent
    data class Delete(val id: Int) : ListAsignaturaUiEvent
    data object CreateNew : ListAsignaturaUiEvent
    data class Edit(val id: Int) : ListAsignaturaUiEvent
    data class ShowMessage(val message: String) : ListAsignaturaUiEvent
    data object ClearMessage : ListAsignaturaUiEvent
}
