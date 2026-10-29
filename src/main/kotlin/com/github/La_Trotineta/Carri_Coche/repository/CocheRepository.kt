package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.junfeng52.servicio.GestorCocheCSV
import org.springframework.stereotype.Repository
import java.nio.file.Path
import java.nio.file.Paths

@Repository
class CocheRepository {
    private val gestor: GestorCocheCSV = GestorCocheCSV(Paths.get(object {}.javaClass.getResource("/data/coche.csv").toURI()))
    private val coches: MutableMap<String, Coche> = gestor.leerCSV().associateBy { it.matricula } as MutableMap<String, Coche>

    fun leerTodosCoches(): List<Coche> = coches.values.toList()

    fun buscarPorMatricula(matricula: String): Coche? = coches[matricula]

    fun guardarCoche(coche: Coche) {
        coches[coche.matricula] = coche
        gestor.escribirCSV(coches.values.toList())
    }

    fun borrarPorMatricula(matricula: String) {
        coches.remove(matricula)
    }
}