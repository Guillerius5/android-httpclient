package es.fpsumma.dam2.api.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TareaDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String
)

