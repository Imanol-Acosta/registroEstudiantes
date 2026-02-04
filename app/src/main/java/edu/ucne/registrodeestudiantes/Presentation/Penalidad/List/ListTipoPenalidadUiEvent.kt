package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

sealed interface ListTipoPenalidadUiEvent {
    data object Load : ListTipoPenalidadUiEvent
    data object Refresh : ListTipoPenalidadUiEvent
    data class Delete(val id: Int) : ListTipoPenalidadUiEvent
    data object CreateNew : ListTipoPenalidadUiEvent
    data class Edit(val id: Int) : ListTipoPenalidadUiEvent
    data class ShowMessage(val message: String) : ListTipoPenalidadUiEvent
    data object ClearMessage : ListTipoPenalidadUiEvent
}
