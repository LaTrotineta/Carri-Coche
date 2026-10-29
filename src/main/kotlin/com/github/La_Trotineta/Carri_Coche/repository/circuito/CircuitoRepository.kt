package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.springframework.stereotype.Repository
import utilidad.leerDatosCircuitoCSV
import java.nio.file.Path


@Repository
class CircuitoRepository {
    val rutaCSV = Path.of("datos","circuitos","circuitos.csv")
    // La lista en memoria se traslada aquí y actúa como nuestra "Base de Datos" temporal
    private val circuitos = leerDatosCircuitoCSV(rutaCSV) as MutableList<Circuito>

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
        csvWriter { delimiter = ';' }.open(rutaCSV.toFile()) {
            circuitos.forEach { c ->
                writeRow(c.id_circuito, c.nombre, c.pais, c.curvas, c.longitud)
            }
        }
    }

    fun borrarPorId(id_circuito: Int) {
        circuitos.removeAll { it.id_circuito == id_circuito }
    }
}