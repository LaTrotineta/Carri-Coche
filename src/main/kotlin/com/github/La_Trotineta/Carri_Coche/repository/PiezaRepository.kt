package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.junfeng52.servicio.GestorCSV
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class PiezaRepository(
    @Value("classpath:data/piezas.csv")
    private val archivo: Resource
) {
    private val gestor: GestorCSV = GestorCSV(Path.of(archivo.uri))
    private val piezas: MutableList<Pieza> = gestor.leerCSV<Pieza>() as MutableList<Pieza>

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