package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path


@Repository
class CircuitoRepository {/*
/home/alejuadra/IdeaProjects/Carri-Coche/src/main/resources/data*/
    val rutaCSV = Path.of("src","main","resources","data","circuitos.csv")
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
fun leerDatosCircuitoCSV(ruta: Path):List<Circuito>{
    //Crear una variable donde almacenaremos la lista
    var circuitos: List<Circuito> = emptyList()

    //Crear una variable que almacene todos los datos delimitando con un ;
    if (!Files.isReadable(ruta)) {
        println("Error: No se puede leer el fichero en la ruta: $ruta")
    } else {
        val reader = csvReader {
            delimiter = ';'
        }
// Leemos todas las filas del CSV con la variable que hemos creado antes(devuelve List<List<String>>)
        val filas: List<List<String>> = reader.readAll(ruta.toFile())
        // Convertimos las filas de texto en objetos Coche
        circuitos = filas.mapNotNull { columnas ->
            if (columnas.size >= 5) {
                try {
                    val id_circuito = columnas[0].toInt()
                    val nombre = columnas[1]
                    val pais = columnas[2]
                    val curvas = columnas[3].toInt()
                    val longitud = columnas[4].toDouble()
                    Circuito(id_circuito, nombre, pais, curvas, longitud)

                } catch (e: Exception) {
                    println("Fila inválida ignorada: $columnas -> Error: ${e.message}")
                    null
                }
            } else {
                println("Fila con formato incompleto ignorada: $columnas")
                null
            }
        }
    }
    //Devolver la lista parseada
    return circuitos
}