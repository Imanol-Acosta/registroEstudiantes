package edu.ucne.registrodeestudiantes.Presentation.Estudiante.Edit

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
fun EditEstudianteScreen(
    estudianteId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: EditEstudianteViewModel = hiltViewModel()
) {
    LaunchedEffect(estudianteId) {
        viewModel.onEvent(EditEstudianteUiEvent.Load(estudianteId))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Estudiante" else "Editar Estudiante") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        EditEstudianteBody(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun EditEstudianteBody(
    state: EditEstudianteUiState,
    onEvent: (EditEstudianteUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.nombres,
            onValueChange = { onEvent(EditEstudianteUiEvent.NombresChanged(it)) },
            label = { Text("Nombres") },
            isError = state.nombresError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.nombresError != null) {
            Text(
                text = state.nombresError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(EditEstudianteUiEvent.EmailChanged(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.emailError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.edad?.toString() ?: "",
            onValueChange = { onEvent(EditEstudianteUiEvent.EdadChanged(it)) },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.edadError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.edadError != null) {
            Text(
                text = state.edadError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { onEvent(EditEstudianteUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            if (!state.isNew) {
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { onEvent(EditEstudianteUiEvent.Delete) },
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
private fun EditEstudianteBodyPreview() {
    val state = EditEstudianteUiState(
        nombres = "Jose Duarte",
        email = "jose@email.com",
        edad = 20
    )
    MaterialTheme {
        EditEstudianteBody(
            state = state,
            onEvent = {}
        )
    }
}
