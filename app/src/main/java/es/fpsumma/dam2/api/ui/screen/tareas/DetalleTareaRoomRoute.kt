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

    val tareaFlow = remember(id) { vm.getTarea(id) }

    val tarea by tareaFlow.collectAsStateWithLifecycle(initialValue = null)

    DetalleTareaContent(
        tarea = tarea,
        onBack = {
            navController.popBackStack()
        },
        onUpdate = { titulo, descripcion ->

            vm.updateTarea(id, titulo, descripcion)

            navController.popBackStack()
        },
        modifier = modifier
    )
}