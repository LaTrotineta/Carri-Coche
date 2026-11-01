package com.github.La_Trotineta.Carri_Coche.repository


import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.junfeng52.servicio.GestorCSV
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class MecanicoRepository (
    @Value("classpath:/data/mecanicos.csv")
    private val archivo: Resource
) {
    val gestor: GestorCSV = GestorCSV(Path.of(archivo.uri))
    private val mecanicos = gestor.leerCSV<Mecanico>() as MutableList<Mecanico>

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
        gestor.escribirCSV<Mecanico>(mecanicos)
    }

    fun borrarPorId(id_mecanico: Int) {
        mecanicos.removeAll { it.id_mecanico == id_mecanico }
    }

}
