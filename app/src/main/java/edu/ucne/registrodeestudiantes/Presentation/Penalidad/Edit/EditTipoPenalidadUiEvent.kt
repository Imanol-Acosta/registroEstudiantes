package edu.ucne.registrodeestudiantes.Presentation.Penalidad.Edit

sealed interface EditTipoPenalidadUiEvent {
    data class Load(val id: Int?) : EditTipoPenalidadUiEvent
    data class NombreChanged(val value: String) : EditTipoPenalidadUiEvent
    data class DescripcionChanged(val value: String) : EditTipoPenalidadUiEvent
    data class PuntosChanged(val value: String) : EditTipoPenalidadUiEvent
    data object Save : EditTipoPenalidadUiEvent
    data object Delete : EditTipoPenalidadUiEvent
}
