package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Coche
import org.springframework.stereotype.Repository

@Repository
class CocheRepository {
    private val coches: MutableMap<String, Coche> = mutableMapOf()

    fun leerTodosCoches(): List<Coche> = coches.values.toList()

    fun buscarPorMatricula(matricula: String): Coche? = coches[matricula]

    fun guardarCoche(coche: Coche) {
        coches[coche.matricula] = coche
    }

    fun borrarPorMatricula(matricula: String) {
        coches.remove(matricula)
    }
}