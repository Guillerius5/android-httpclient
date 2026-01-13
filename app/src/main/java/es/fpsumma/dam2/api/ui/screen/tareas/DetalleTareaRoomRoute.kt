package es.fpsumma.dam2.api.ui.screen.tareas



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import es.fpsumma.dam2.api.viewmodel.TareasViewModel

@Composable
fun DetalleTareaRoomRoute(
    id: Int,
    navController: NavController,
    vm: TareasViewModel,
    modifier: Modifier = Modifier
) {
    // 1. Obtenemos el flujo de datos del ViewModel
    val tareaFlow = remember(id) { vm.getTarea(id) }
    // 2. Convertimos el flujo en Estado para la UI
    val tarea by tareaFlow.collectAsStateWithLifecycle(initialValue = null)

    DetalleTareaContent(
        tarea = tarea,
        onBack = {
            navController.popBackStack()
        },
        onUpdate = { titulo, descripcion ->
            // Lógica de negocio
            vm.updateTarea(id, titulo, descripcion)
            // Navegación (volvemos atrás)
            navController.popBackStack()
        },
        modifier = modifier
    )
}