package com.example.demo.service

import com.example.demo.model.Planta
import com.example.demo.repository.PlantaRepository
import org.springframework.stereotype.Service

@Service
class PlantaService(private val repository: PlantaRepository) {

    fun listar(): List<Planta> = repository.leerTodasPlantas()

    fun buscar(id: Int): Planta? = repository.buscarPorID(id)

    fun guardar(planta: Planta) = repository.guardarPlanta(planta)

    fun borrar(id_planta: Int) {
        repository.borrarPorId(id_planta)
    }
}