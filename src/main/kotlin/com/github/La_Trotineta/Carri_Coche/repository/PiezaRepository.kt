package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.springframework.stereotype.Repository
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.toString

@Repository
class PiezaRepository {
    private val piezas = leerCSV().toMutableList();
    fun leerTodasLasPiezas() : List<Pieza> = leerCSV();
    fun buscarPorID (id : Int) : Pieza? = piezas.find { it.id_pieza == id }
    fun guardarPieza(pieza : Pieza) {

        val index = piezas.indexOfFirst { it.id_pieza == pieza.id_pieza }
        if (index != -1) {
            piezas[index] = pieza
        } else {
            val nuevoId = (piezas.maxOfOrNull { it.id_pieza } ?: 0) + 1
            piezas.add(pieza.copy(id_pieza = nuevoId))
        }
        escribirCSVEntero(piezas)
    }

    fun borrarPorId(id_pieza : Int) {
        piezas.removeAll { it.id_pieza == id_pieza }
        escribirCSVEntero(piezas)
    }

    fun leerCSV() : List<Pieza> {
        var Piezas: List<Pieza> = emptyList();
        var ruta = Path.of("src/main/resources/data/piezas.csv")
        try {

            if (!Files.isReadable(ruta)) {
                println("Error: No se puede leer el fichero en la ruta: $ruta")
            } else {
                val reader = csvReader {
                    delimiter = ';'
                }

                val filas: List<List<String>> = reader.readAll(ruta.toFile())

                Piezas = filas.mapNotNull { columnas ->
                    if (columnas.size >= 5) {
                        try {
                            val idPieza = columnas[0].toInt()
                            val nombre = columnas[1]
                            val fabricante = columnas[2]
                            val stock = columnas[3].toInt()
                            val precio = columnas[4].toDouble()
                            Pieza(idPieza, nombre, fabricante, stock, precio)
                        } catch (e: Exception) {
                            null
                        }
                    } else {
                        null
                    }
                }
            }
            return Piezas
        } catch (e : Exception) {
            println("Error: ${e.message}")
            return Piezas;
        }
    }

    fun escribirCSVEntero(piezas: List<Pieza>) {
        try {
            val ruta = Path.of("src/main/resources/data/piezas.csv")
            val fichero: File = ruta.toFile()
            if (!fichero.exists()) println("El archivo ${ruta.fileName} no existe")
            else {

                csvWriter {
                    delimiter = ';'
                }.writeAll(
                    piezas.map { pieza ->
                        listOf(
                            pieza.id_pieza.toString(),
                            pieza.nombre,
                            pieza.fabricante,
                            pieza.stock.toString(),
                            pieza.precio.toString()
                        )
                    },
                    fichero,
                    append = false
                )

            }
        } catch (e: Exception) {
            println("Error al escribir el fichero CSV: ${e.message}")
        }
    }
}