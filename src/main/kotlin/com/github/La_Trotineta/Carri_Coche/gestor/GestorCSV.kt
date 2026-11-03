package com.github.junfeng52.servicio

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Junfeng
 *
 * Gestor de CSV Generico
 *
 * @param path ruta del archivo
 * @param delimiterCSV asignacion del delimitador del CSV
 */
class GestorCSV(val path: Path, val delimiterCSV: Char = ';') {
    init {
        Files.createDirectories(path.parent)
        if (!Files.exists(path)) {
            Files.createFile(path)
        }
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
                                val matricula = columnas[0].uppercase()
                                val modelo = columnas[1].uppercase()
                                val marca = columnas[2].uppercase()
                                val potencia = columnas[3].toInt()
                                val consumo = columnas[4].toDouble()
                                val rutaImagen = "" // columnas[5]
                                Coche(matricula, modelo, marca, potencia, consumo, rutaImagen)
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
                                val nombre = columnas[1].uppercase()
                                val fabricante = columnas[2].uppercase()
                                val stock = columnas[3].toInt()
                                val precio = columnas[4].toDouble()
                                val rutaImagen = "" //columnas[5]
                                Pieza(id_pieza, nombre, fabricante, stock, precio, rutaImagen)
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
                                val nombre = columnas[1].uppercase()
                                val pais = columnas[2].uppercase()
                                val curvas = columnas[3].toInt()
                                val longitud = columnas[4].toDouble()
                                val rutaImagen = "" //columnas[5]
                                Circuito(id_circuito, nombre, pais, curvas, longitud, rutaImagen)
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
                                val nombre = columnas[1].uppercase()
                                val especialidad = columnas[2].uppercase()
                                val experiencia = columnas[3].toInt()
                                val tarifaHora = columnas[4].toDouble()
                                val rutaImagen = "" //columnas[5]
                                Mecanico(id_mecanico, nombre, especialidad, experiencia, tarifaHora, rutaImagen)
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
                        coche.matricula.uppercase(),
                        coche.modelo.uppercase(),
                        coche.marca.uppercase(),
                        coche.potencia,
                        coche.consumo,
                        coche.rutaImagen
                    )
                }
            }

            Pieza::class -> {
                val listaPiezas = valores as List<Pieza>
                listaPiezas.map { pieza ->
                    listOf(
                        pieza.id_pieza,
                        pieza.nombre.uppercase(),
                        pieza.fabricante.uppercase(),
                        pieza.stock,
                        pieza.precio,
                        pieza.rutaImagen
                    )
                }
            }

            Circuito::class -> {
                val listaCircuitos = valores as List<Circuito>
                listaCircuitos.map { circuitos ->
                    listOf(
                        circuitos.id_circuito,
                        circuitos.nombre.uppercase(),
                        circuitos.pais.uppercase(),
                        circuitos.curvas,
                        circuitos.longitud,
                        circuitos.rutaImagen
                    )
                }
            }

            Mecanico::class -> {
                val listaMecanicos = valores as List<Mecanico>
                listaMecanicos.map { mecanico ->
                    listOf(
                        mecanico.id_mecanico,
                        mecanico.nombre.uppercase(),
                        mecanico.especialidad.uppercase(),
                        mecanico.experiencia,
                        mecanico.tarifaHora,
                        mecanico.rutaImagen
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