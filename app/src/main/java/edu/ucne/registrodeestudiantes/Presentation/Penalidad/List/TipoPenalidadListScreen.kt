package edu.ucne.registrodeestudiantes.Presentation.Penalidad.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadListScreen(
    onDrawer: () -> Unit,
    goToPenalidad: (Int) -> Unit,
    createPenalidad: () -> Unit,
    viewModel: TipoPenalidadListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tipos de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createPenalidad) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Tipo")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(uiState.penalidades) { penalidad ->
                    TipoPenalidadCard(
                        penalidad = penalidad,
                        onClick = { goToPenalidad(penalidad.tipoId ?: 0) }
                    )
                }
            }
        }
    }
}

@Composable
fun TipoPenalidadCard(
    penalidad: TipoPenalidad,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(penalidad.nombre, style = MaterialTheme.typography.titleMedium)
            Text(penalidad.descripcion, style = MaterialTheme.typography.bodyMedium)
            Text("Puntos: ${penalidad.puntosDescuento}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
