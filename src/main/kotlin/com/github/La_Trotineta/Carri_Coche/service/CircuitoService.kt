package com.github.La_Trotineta.Carri_Coche.service


import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.La_Trotineta.Carri_Coche.repository.CircuitoRepository
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class CircuitoService(private val repository: CircuitoRepository) {

    fun listar(): List<Circuito> = repository.leerTodosCircuitos()

    fun buscar(id: Int): Circuito? = repository.buscarPorID(id)

    fun guardar(circuito: Circuito) = repository.guardarCircuito(circuito)

    fun borrar(id_circuito: Int) {
        repository.borrarPorId(id_circuito)
    }
}