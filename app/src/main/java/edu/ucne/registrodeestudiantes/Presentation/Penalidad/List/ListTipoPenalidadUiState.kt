package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad

data class ListTipoPenalidadUiState(
    val isLoading: Boolean = false,
    val penalidades: List<TipoPenalidad> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
