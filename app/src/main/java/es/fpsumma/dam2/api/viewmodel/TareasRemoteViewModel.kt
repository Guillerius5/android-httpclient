package es.fpsumma.dam2.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fpsumma.dam2.api.data.remote.RetrofitClient
import es.fpsumma.dam2.api.model.Tarea
import es.fpsumma.dam2.api.ui.screen.tareas.TareasUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TareasRemoteViewModel : ViewModel() {


    private val api = RetrofitClient.tareaAPI


    private val _state = MutableStateFlow(TareasUIState())
    val state: StateFlow<TareasUIState> = _state


    fun loadTareas() = viewModelScope.launch {

        _state.update { it.copy(loading = true, error = null) }


        runCatching {
            val response = api.listar()

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                error("Error ${response.code()}")
            }
        }.onSuccess { listaDto ->

            val listaMapeada = listaDto.map { dto ->
                Tarea(
                    id = dto.id,
                    titulo = dto.titulo,
                    descripcion = dto.descripcion
                )
            }

            _state.update { it.copy(tareas = listaMapeada, loading = false) }

        }.onFailure { e ->

            _state.update {
                it.copy(
                    loading = false,
                    error = e.message ?: "Error al cargar datos"
                )
            }
        }
    }
}