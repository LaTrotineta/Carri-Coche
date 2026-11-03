package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.junfeng52.servicio.GestorCSV
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import java.nio.file.Path

/**
 * @author Alexander Juan Drago
 * Entidad:Circuitos
 *
 */
@Repository
class CircuitoRepository(
    @Value("classpath:data/circuitos.csv") archivo: Resource
) {
    private val gestor: GestorCSV = GestorCSV(Path.of(archivo.uri))
    private val circuitos = gestor.leerCSV<Circuito>() as MutableList<Circuito>

    fun leerTodosCircuitos(): MutableList<Circuito> = circuitos

    fun buscarPorID(id: Int): Circuito? = circuitos.find { it.id_circuito == id }

    fun guardarCircuito(circuito: Circuito) {
        val index = circuito.id_circuito
        if (circuito.id_circuito != -1) {
            circuitos[index] = circuito
        } else {
            val nuevoId = (circuitos.maxOfOrNull { it.id_circuito } ?: 0) + 1
            circuitos.add(circuito.copy(id_circuito = nuevoId))
        }
        gestor.escribirCSV<Circuito>(circuitos)
    }

    fun borrarPorId(id_circuito: Int) {
        circuitos.removeAll { it.id_circuito == id_circuito }
        gestor.escribirCSV<Circuito>(circuitos)
    }
}