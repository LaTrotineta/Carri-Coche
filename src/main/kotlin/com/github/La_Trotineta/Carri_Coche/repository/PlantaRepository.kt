package com.example.demo.repository

import com.example.demo.model.Planta
import org.springframework.stereotype.Repository

@Repository
class PlantaRepository {

    // La lista en memoria se traslada aquí y actúa como nuestra "Base de Datos" temporal
    private val plantas = mutableListOf(
        Planta(1, "Rosa", "Flor", 0.5, "rosa.jpg"),
        Planta(2, "Cactus", "Suculenta", 1.2, "cactus.jpg"),
        Planta(5, "Orquídea", "Flor", 0.3, "orquidea.jpg")
    )

    fun leerTodasPlantas(): MutableList<Planta> = plantas

    fun buscarPorID(id: Int): Planta? = plantas.find { it.id_planta == id }

    fun guardarPlanta(planta: Planta) {
        val index = plantas.indexOfFirst { it.id_planta == planta.id_planta }
        if (index != -1) {
            // Edición de planta existente
            plantas[index] = planta
        } else {
            // Creación de nueva planta (simulación de ID autoincremental)
            val nuevoId = (plantas.maxOfOrNull { it.id_planta } ?: 0) + 1
            plantas.add(planta.copy(id_planta = nuevoId))
        }
    }

    fun borrarPorId(id_planta: Int) {
        plantas.removeAll { it.id_planta == id_planta }
    }
}