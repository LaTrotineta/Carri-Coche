package com.github.junfeng52.servicio

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.La_Trotineta.Carri_Coche.util.Utils
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.nio.file.Files
import java.nio.file.Path

class GestorCSV(val path: Path, val delimiterCSV: Char = ';') {
    init {
        Utils.crearArchivoSiNoExiste(path)
    }

    inline fun <reified T> leerCSV(): List<T> {
        if (Files.isReadable(path)) {
            val reader = csvReader { delimiter = delimiterCSV }
            val filas: List<List<String>> = reader.readAll(path.toFile())
            val result: List<Any> = when (T::class) {
                Coche::class -> {
                    filas.mapNotNull { columnas ->
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
                            null
                        }
                    }
                }

                Pieza::class -> {
                    filas.mapNotNull { columnas ->
                        if (columnas.size >= 5) {
                            try {
                                val id_pieza = columnas[0].toInt()
                                val nombre = columnas[1]
                                val fabricante = columnas[2]
                                val stock = columnas[3].toInt()
                                val precio = columnas[4].toDouble()
                                Pieza(id_pieza, nombre, fabricante, stock, precio)
                            } catch (ex: Exception) {
                                null
                            }
                        } else {
                            null
                        }
                    }
                }

                Circuito::class -> {
                    filas.mapNotNull { columnas ->
                        if (columnas.size >= 5) {
                            try {
                                val id_circuito = columnas[0].toInt()
                                val nombre = columnas[1]
                                val pais = columnas[2]
                                val curvas = columnas[3].toInt()
                                val longitud = columnas[4].toDouble()
                                Circuito(id_circuito, nombre, pais, curvas, longitud)
                            } catch (ex: Exception) {
                                null
                            }
                        } else {
                            null
                        }
                    }
                }

                Mecanico::class -> {
                    filas.mapNotNull { columnas ->
                        if (columnas.size >= 5) {
                            try {
                                val id_mecanico = columnas[0].toInt()
                                val nombre = columnas[1]
                                val especialidad = columnas[2]
                                val experiencia = columnas[3].toInt()
                                val tarifaHora = columnas[4].toDouble()
                                Mecanico(id_mecanico, nombre, especialidad, experiencia, tarifaHora)
                            } catch (ex: Exception) {
                                null
                            }
                        } else {
                            null
                        }
                    }
                }

                else -> {
                    emptyList()
                }
            }
            return result as List<T>
        } else {
            return listOf<T>()
        }
    }

    inline fun <reified T> escribirCSV(valores: List<T>) {
        val archivo = path.toFile()

        val lista: List<List<Any>> = when(T::class) {
            Coche::class -> {
                val listaCoches = valores as List<Coche>
                listaCoches.map { coche ->
                    listOf(
                        coche.matricula,
                        coche.modelo,
                        coche.marca,
                        coche.potencia,
                        coche.consumo
                    )
                }
            }

            Pieza::class -> {
                val listaPiezas = valores as List<Pieza>
                listaPiezas.map { coche ->
                    listOf(
                        coche.id_pieza,
                        coche.nombre,
                        coche.fabricante,
                        coche.stock,
                        coche.precio
                    )
                }
            }

            Circuito::class -> {
                val listaPiezas = valores as List<Circuito>
                listaPiezas.map { coche ->
                    listOf(
                        coche.id_circuito,
                        coche.nombre,
                        coche.pais,
                        coche.curvas,
                        coche.longitud
                    )
                }
            }

            Mecanico::class -> {
                val listaPiezas = valores as List<Mecanico>
                listaPiezas.map { coche ->
                    listOf(
                        coche.id_mecanico,
                        coche.nombre,
                        coche.especialidad,
                        coche.experiencia,
                        coche.tarifaHora
                    )
                }
            }

            else -> emptyList()
        }

        csvWriter {
            delimiter = delimiterCSV
        }.writeAll(lista, archivo)
    }
}