package com.github.La_Trotineta.Carri_Coche.service

import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.La_Trotineta.Carri_Coche.repository.PiezaRepository
import org.springframework.stereotype.Service

@Service
class PiezaService (private val repository: PiezaRepository){
    fun listar(): List<Pieza> = repository.leerTodasLasPiezas()
    
    fun buscar(id: Int): Pieza? = repository.buscarPorID(id);
    
    fun guardar(pieza : Pieza) = repository.guardarPieza(pieza);
    
    fun borrar(id_pieza: Int) = repository.borrarPorId(id_pieza)
}