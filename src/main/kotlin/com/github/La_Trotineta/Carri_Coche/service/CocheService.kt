package com.github.La_Trotineta.Carri_Coche.service;

import com.github.La_Trotineta.Carri_Coche.model.Coche;
import com.github.La_Trotineta.Carri_Coche.repository.CocheRepository
import org.springframework.stereotype.Service;

@Service
class CocheService(private val repository: CocheRepository) {

    fun listar(): List<Coche> = repository.leerTodosCoches()

    fun buscar(matricula: String): Coche? = repository.buscarPorMatricula(matricula)

    fun guardar(coche: Coche) = repository.guardarCoche(coche)

    fun borrar(matricula: String) {
        repository.borrarPorMatricula(matricula)
    }
}