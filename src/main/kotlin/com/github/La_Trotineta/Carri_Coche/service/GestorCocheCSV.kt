package com.github.junfeng52.servicio

import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.La_Trotineta.Carri_Coche.util.Utils
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.mutableListOf

class GestorCocheCSV(val path: Path, val delimiterCSV: Char = ';') {
    init {
        Utils.crearArchivoSiNoExiste(path)
    }

    companion object {
        fun leerCSV(path: Path, delimiterCSV: Char = ';'): List<Coche> {
            if (Files.isReadable(path)) {
                val reader = csvReader { delimiter = delimiterCSV }
                val filas: List<List<String>> = reader.readAll(path.toFile())
                val coches: List<Coche> = filas.mapNotNull { columnas ->
                    if (columnas.size >= 5) {
                        try {
                            val matricula = columnas[0]
                            val modelo = columnas[1]
                            val marca = columnas[2]
                            val potencia = columnas[3].toInt()
                            val consumo = columnas[4].toDouble()
                            Coche(matricula, modelo, marca, potencia, consumo)
                        } catch (ex: Exception) {
                            null
                        }
                    } else {
                        println("Columna invalida: $columnas")
                        null
                    }
                }

                return coches
            } else {
                return mutableListOf()
            }
        }

        fun escribirCSV(coches: List<Coche>, path: Path, delimiterCSV: Char = ';') {
            val archivo = path.toFile()
            csvWriter {
                delimiter = delimiterCSV
            }.writeAll(coches.map { coche ->
                listOf(
                    coche.matricula,
                    coche.modelo,
                    coche.marca,
                    coche.potencia,
                    coche.consumo)
            }, archivo)

        }
    }

    fun leerCSV(): List<Coche> {
        return leerCSV(path, delimiterCSV)
    }

    fun escribirCSV(coches: List<Coche>) {
        escribirCSV(coches, path, delimiterCSV)
    }
}