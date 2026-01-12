package es.fpsumma.dam2.api.ui.screen.tareas


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.fpsumma.dam2.api.model.Tarea

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoTareasContent(
    state: TareasUIState,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onOpenDetalle: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir Tarea")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            if (state.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else if (state.tareas.isEmpty()) {
                Text(
                    text = "No hay tareas aún",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(state.tareas) { tarea ->
                        TareaItem(
                            tarea = tarea,
                            onClick = { onOpenDetalle(tarea.id) },
                            onDelete = { onDelete(tarea.id) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TareaItem(tarea: Tarea, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")
            }
        }
    }
}