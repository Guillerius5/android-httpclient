package es.fpsumma.dam2.api.ui.screen.tareas


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.fpsumma.dam2.api.ui.navegation.Routes
import es.fpsumma.dam2.api.viewmodel.TareasRemoteViewModel

@Composable
fun ListadoTareasRemoteRoute(
    navController: NavController,
    // Aquí es donde ocurre la magia: Usamos el ViewModel Remoto
    vm: TareasRemoteViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // 1. Observamos el estado del ViewModel remoto
    val state by vm.state.collectAsState()

    // 2. IMPORTANTE: Al entrar en la pantalla, pedimos cargar los datos
    // LaunchedEffect(Unit) se ejecuta solo una vez al iniciar este componente
    LaunchedEffect(Unit) {
        vm.loadTareas()
    }

    // 3. Reutilizamos el MISMO diseño que ya tenías
    ListadoTareasContent(
        state = state,
        onBack = {
            // Opcional, si quieres volver atrás o cerrar
            navController.popBackStack()
        },
        onAdd = {
            // Navegamos a crear tarea (se guardará en Room, pero navegamos igual)
            navController.navigate(Routes.TAREA_ADD)
        },
        onOpenDetalle = { id ->
            // Navegamos al detalle para ver la tarea (aunque sea remota)
            // Asegúrate de que tu ruta sea correcta, por ejemplo:
            navController.navigate("tarea_view/$id")
        },
        onDelete = {
            // La instrucción dice explícitamente que lo dejemos vacío por ahora
        },
        modifier = modifier
    )
}