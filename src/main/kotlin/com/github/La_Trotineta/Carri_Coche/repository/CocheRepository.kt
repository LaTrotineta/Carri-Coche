package com.github.La_Trotineta.Carri_Coche.repository

import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.junfeng52.servicio.GestorCSV
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class CocheRepository(
    @Value("classpath:data/coches.csv")
    private val archivo: Resource
) {
    private val gestor: GestorCSV = GestorCSV(Path.of(archivo.uri))
    private val coches: MutableMap<String, Coche> = gestor.leerCSV<Coche>().associateBy { it.matricula } as MutableMap<String, Coche>

    fun leerTodosCoches(): List<Coche> = coches.values.toList()

    fun buscarPorMatricula(matricula: String): Coche? = coches[matricula]

    fun guardarCoche(coche: Coche) {
        coches[coche.matricula] = coche
        gestor.escribirCSV(coches.values.toList())
    }

    fun borrarPorMatricula(matricula: String) {
        coches.remove(matricula)
        gestor.escribirCSV(coches.values.toList())
    }
}