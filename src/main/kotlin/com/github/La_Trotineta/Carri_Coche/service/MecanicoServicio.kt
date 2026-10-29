package com.github.La_Trotineta.Carri_Coche.service


import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.La_Trotineta.Carri_Coche.repository.MecanicoRepository
import org.springframework.stereotype.Service

@Service
class MecanicoServicio(private val repository: MecanicoRepository) {
    
    fun listar(): List<Mecanico> = repository.leerTodasMecanicos()

    fun buscar(id: Int): Mecanico? = repository.buscarPorID(id)

    fun guardar(planta: Mecanico) = repository.guardarMecanico(planta)

    fun borrar(id_planta: Int) {
        repository.borrarPorId(id_planta)
    }

}