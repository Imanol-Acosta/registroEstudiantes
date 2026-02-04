package edu.ucne.registrodeestudiantes.Presentation.Asignatura.Edit

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
fun EditAsignaturaScreen(
    asignaturaId: Int,
    onNavigateBack: () -> Unit,
    viewModel: EditAsignaturaViewModel = hiltViewModel()
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
                title = { Text(if (state.isNew) "Nueva Asignatura" else "Editar Asignatura") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        EditAsignaturaBody(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun EditAsignaturaBody(
    state: EditAsignaturaUiState,
    onEvent: (EditAsignaturaUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.codigo,
            onValueChange = { onEvent(EditAsignaturaUiEvent.CodigoChanged(it)) },
            label = { Text("Código") },
            isError = state.codigoError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.codigoError != null) {
            Text(
                text = state.codigoError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.nombre,
            onValueChange = { onEvent(EditAsignaturaUiEvent.NombreChanged(it)) },
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
            value = state.aula,
            onValueChange = { onEvent(EditAsignaturaUiEvent.AulaChanged(it)) },
            label = { Text("Aula") },
            isError = state.aulaError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.aulaError != null) {
            Text(
                text = state.aulaError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.creditos,
            onValueChange = { onEvent(EditAsignaturaUiEvent.CreditosChanged(it)) },
            label = { Text("Créditos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.creditosError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.creditosError != null) {
            Text(
                text = state.creditosError,
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
                onClick = { onEvent(EditAsignaturaUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            if (!state.isNew) {
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { onEvent(EditAsignaturaUiEvent.Delete) },
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
private fun EditAsignaturaBodyPreview() {
    val state = EditAsignaturaUiState(
        codigo = "MAT-101",
        nombre = "Matemáticas I",
        aula = "A-101",
        creditos = "4"
    )
    MaterialTheme {
        EditAsignaturaBody(
            state = state,
            onEvent = {}
        )
    }
}
