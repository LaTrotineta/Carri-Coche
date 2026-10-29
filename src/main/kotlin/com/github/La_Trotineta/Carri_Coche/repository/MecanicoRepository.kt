package com.github.La_Trotineta.Carri_Coche.repository


import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Repository
class MecanicoRepository {
    val ruta = Path.of("src", "main", "resources", "data", "mecanicos.csv")
    private val mecanicos = leerDatosCSV(ruta) as MutableList<Mecanico>

    fun leerTodasMecanicos(): MutableList<Mecanico> = mecanicos

    fun buscarPorID(id: Int): Mecanico? = mecanicos.find { it.id_mecanico == id }

    fun guardarMecanico(mecanico: Mecanico) {
        val index = mecanicos.indexOfFirst { it.id_mecanico == mecanico.id_mecanico }
        if (index != -1) {
            // Edición de mecanico existente
            mecanicos[index] = mecanico
        } else {
            // Creación de nueva mecanico (simulación de ID autoincremental)
            val nuevoId = (mecanicos.maxOfOrNull { it.id_mecanico } ?: 0) + 1
            mecanicos.add(mecanico.copy(id_mecanico = nuevoId))
        }
        csvWriter { delimiter = ';' }.open(ruta.toFile()) {
            mecanicos.forEach { mecanico ->
                writeRow(
                    mecanico.id_mecanico,
                    mecanico.nombre,
                    mecanico.especialidad,
                    mecanico.experiencia,
                    mecanico.tarifaHora
                )
            }
        }
    }

    fun borrarPorId(id_mecanico: Int) {
        mecanicos.removeAll { it.id_mecanico == id_mecanico }
    }

    fun leerDatosCSV(ruta: Path): List<Mecanico> {
        var mecanicos: List<Mecanico> = emptyList()

        if (!Files.isReadable(ruta)) {
            println("Error: No se puede leer el fichero en la ruta: $ruta")

        } else {
            val reader = csvReader() {
                delimiter = ';'
            }

            val filas: List<List<String>> = reader.readAll(ruta.toFile())

            mecanicos = filas.mapNotNull { columnas ->
                if (columnas.size >= 5) {
                    try {
                        val idMecanico = columnas[0].toInt()
                        val nombreMecanico = columnas[1]
                        val especialidadMecanico = columnas[2]
                        val experienciaMecanico = columnas[3].toInt()
                        val tarifaHoraMecanico = columnas[4].toDouble()
                        Mecanico(
                            idMecanico,
                            nombreMecanico,
                            especialidadMecanico,
                            experienciaMecanico,
                            tarifaHoraMecanico
                        )
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
        for (mecanicos in mecanicos) {
            println(
                "ID: ${mecanicos.id_mecanico}, " +
                        "Nombre: ${mecanicos.nombre}, " +
                        "Especialidad: ${mecanicos.especialidad}, " +
                        "Experiencia: ${mecanicos.experiencia}, " +
                        "Tarifa: ${mecanicos.tarifaHora}"
            )
        }
        return mecanicos
    }
}
