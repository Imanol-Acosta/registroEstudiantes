package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaListScreen(
    onDrawer: () -> Unit,
    goToAsignatura: (Int) -> Unit,
    createAsignatura: () -> Unit,
    viewModel: AsignaturaListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createAsignatura) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Asignatura")
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
                items(uiState.asignaturas) { asignatura ->
                    AsignaturaCard(
                        asignatura = asignatura,
                        onClick = { goToAsignatura(asignatura.asignaturaId ?: 0) },
                        onDelete = { viewModel.delete(asignatura) }
                    )
                }
            }
        }
    }
}

@Composable
fun AsignaturaCard(
    asignatura: Asignatura,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(asignatura.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Código: ${asignatura.codigo}", style = MaterialTheme.typography.bodyMedium)
                Text("Aula: ${asignatura.aula} - Créditos: ${asignatura.creditos}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
