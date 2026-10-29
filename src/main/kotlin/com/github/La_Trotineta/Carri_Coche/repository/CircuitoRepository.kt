package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import org.springframework.stereotype.Repository

@Repository
class CircuitoRepository {

    // La lista en memoria se traslada aquí y actúa como nuestra "Base de Datos" temporal
    private val circuitos = mutableListOf(
        Circuito(1,"Spa-Francorchamps","Bélgica",20,7.004),
        Circuito(2, "Monza","Italia",11,5.793),
        Circuito(5, "Catalunya","España",14,4.657),
    )

    fun leerTodosCircuitos(): MutableList<Circuito> = circuitos

    fun buscarPorID(id: Int): Circuito? = circuitos.find { it.id_circuito == id }

    fun guardarCircuito(circuito: Circuito) {
        val index = circuitos.indexOfFirst { it.id_circuito == circuito.id_circuito }
        if (index != -1) {
            // Edición de planta existente
            circuitos[index] = circuito
        } else {
            // Creación de nueva planta (simulación de ID autoincremental)
            val nuevoId = (circuitos.maxOfOrNull { it.id_circuito } ?: 0) + 1
            circuitos.add(circuito.copy(id_circuito = nuevoId))
        }
    }

    fun borrarPorId(id_circuito: Int) {
        circuitos.removeAll { it.id_circuito == id_circuito }
    }
}