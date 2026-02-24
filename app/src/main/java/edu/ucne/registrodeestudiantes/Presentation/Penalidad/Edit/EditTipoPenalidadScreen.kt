package edu.ucne.registrodeestudiantes.Presentation.Penalidad.Edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTipoPenalidadScreen(
    penalidadId: Int,
    onNavigateBack: () -> Unit,
    viewModel: EditTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva Penalidad" else "Editar Penalidad") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        EditTipoPenalidadBody(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun EditTipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.nombre,
            onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(it)) },
            label = { Text("Nombre") },
            isError = state.nombreError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.nombreError != null) {
            Text(
                text = state.nombreError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.descripcion,
            onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(it)) },
            label = { Text("Descripción") },
            isError = state.descripcionError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.descripcionError != null) {
            Text(
                text = state.descripcionError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.puntosDescuento?.toString() ?: "",
            onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosChanged(it)) },
            label = { Text("Puntos de Descuento") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.puntosError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.puntosError != null) {
            Text(
                text = state.puntosError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (state.generalError != null) {
            Spacer(Modifier.height(8.dp))
             Text(
                text = state.generalError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { onEvent(EditTipoPenalidadUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            if (!state.isNew) {
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { onEvent(EditTipoPenalidadUiEvent.Delete) },
                    enabled = !state.isDeleting,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTipoPenalidadBodyPreview() {
    val state = EditTipoPenalidadUiState(
        nombre = "Llegada Tardía",
        descripcion = "Llegó 15 minutos tarde",
        puntosDescuento = 5
    )
    MaterialTheme {
        EditTipoPenalidadBody(
            state = state,
            onEvent = {}
        )
    }
}
