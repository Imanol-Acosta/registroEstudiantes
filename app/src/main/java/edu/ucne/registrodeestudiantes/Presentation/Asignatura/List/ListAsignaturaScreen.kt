package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura

@Composable
fun AsignaturaListScreen(
    onDrawer: () -> Unit,
    goToAsignatura: (Int) -> Unit,
    createAsignatura: () -> Unit,
    viewModel: ListAsignaturaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AsignaturaListBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListAsignaturaUiEvent.Edit -> goToAsignatura(event.id)
                is ListAsignaturaUiEvent.CreateNew -> createAsignatura()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsignaturaListBody(
    state: ListAsignaturaUiState,
    onDrawer: () -> Unit,
    onEvent: (ListAsignaturaUiEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(ListAsignaturaUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListAsignaturaUiEvent.CreateNew) }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.asignaturas) { asignatura ->
                    AsignaturaCard(
                        asignatura = asignatura,
                        onClick = { onEvent(ListAsignaturaUiEvent.Edit(asignatura.asignaturaId ?: 0)) },
                        onDelete = { onEvent(ListAsignaturaUiEvent.Delete(asignatura.asignaturaId ?: 0)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AsignaturaCard(
    asignatura: Asignatura,
    onClick: () -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(asignatura.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Código: ${asignatura.codigo}", style = MaterialTheme.typography.bodySmall)
                Text("Aula: ${asignatura.aula} | Créditos: ${asignatura.creditos}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(asignatura.asignaturaId ?: 0) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AsignaturaListBodyPreview() {
    val asignaturas = listOf(
        Asignatura(1, "MAT-101", "Matemáticas I", "A-101", 4),
        Asignatura(2, "ESP-101", "Español I", "A-102", 3)
    )
    val state = ListAsignaturaUiState(asignaturas = asignaturas)
    
    MaterialTheme {
        AsignaturaListBody(
            state = state,
            onDrawer = {},
            onEvent = {}
        )
    }
}
