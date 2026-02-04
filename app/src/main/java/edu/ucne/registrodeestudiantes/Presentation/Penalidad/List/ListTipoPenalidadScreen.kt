package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

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
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad

@Composable
fun ListTipoPenalidadScreen(
    onDrawer: () -> Unit,
    goToPenalidad: (Int) -> Unit,
    createPenalidad: () -> Unit,
    viewModel: ListTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListTipoPenalidadBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListTipoPenalidadUiEvent.Edit -> goToPenalidad(event.id)
                is ListTipoPenalidadUiEvent.CreateNew -> createPenalidad()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    onDrawer: () -> Unit,
    onEvent: (ListTipoPenalidadUiEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(ListTipoPenalidadUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListTipoPenalidadUiEvent.CreateNew) }) {
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
                items(state.penalidades) { penalidad ->
                    TipoPenalidadCard(
                        penalidad = penalidad,
                        onClick = { onEvent(ListTipoPenalidadUiEvent.Edit(penalidad.tipoId)) },
                        onDelete = { onEvent(ListTipoPenalidadUiEvent.Delete(penalidad.tipoId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TipoPenalidadCard(
    penalidad: TipoPenalidad,
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
                Text(penalidad.nombre, style = MaterialTheme.typography.titleMedium)
                Text(penalidad.descripcion, style = MaterialTheme.typography.bodySmall)
                Text("Puntos: ${penalidad.puntosDescuento}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(penalidad.tipoId) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTipoPenalidadBodyPreview() {
    val penalidades = listOf(
        TipoPenalidad(1, "Tardanza", "Llegar tarde", 5),
        TipoPenalidad(2, "Uniforme", "Sin uniforme", 10)
    )
    val state = ListTipoPenalidadUiState(penalidades = penalidades)
    
    MaterialTheme {
        ListTipoPenalidadBody(
            state = state,
            onDrawer = {},
            onEvent = {}
        )
    }
}
