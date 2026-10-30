package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.junfeng52.servicio.GestorCSV
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import java.nio.file.Path


@Repository
class CircuitoRepository(
    @Value("classpath:data/circuitos.csv")
    private val archivo: Resource
) {
    private val gestor: GestorCSV = GestorCSV(Path.of(archivo.uri))
    // La lista en memoria se traslada aquí y actúa como nuestra "Base de Datos" temporal
    private val circuitos = gestor.leerCSV<Circuito>() as MutableList<Circuito>

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
        gestor.escribirCSV(circuitos)
    }

    fun borrarPorId(id_circuito: Int) {
        circuitos.removeAll { it.id_circuito == id_circuito }
        gestor.escribirCSV(circuitos)
    }
}