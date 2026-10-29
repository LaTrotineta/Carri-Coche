package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Pieza
import org.springframework.stereotype.Repository

@Repository
class PiezaRepository {
    private val piezas = mutableListOf<Pieza>()
    fun leerTodasLasPiezas() : List<Pieza> = piezas;
    fun buscarPorID (id : Int) : Pieza? = piezas.find { it.id_pieza == id }
    fun guardarPieza(pieza : Pieza) {
        val index = piezas.indexOfFirst { it.id_pieza == pieza.id_pieza }
        if (index != -1) {
            piezas[index] = pieza
        } else {
            val nuevoId = (piezas.maxOfOrNull { it.id_pieza } ?: 0) + 1
            piezas.add(pieza.copy(id_pieza = nuevoId))
        }
    }

    fun borrarPorId(id_pieza : Int) {
        piezas.removeAll { it.id_pieza == id_pieza }
    }
}