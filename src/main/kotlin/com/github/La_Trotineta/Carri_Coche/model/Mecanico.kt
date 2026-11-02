package com.github.La_Trotineta.Carri_Coche.model

/**
 *
 * @author Antonio de la torre Gimenez
 * Entidad: Vehiculos
 *
 * */

data class Mecanico(
    val id_mecanico: Int,
    val nombre: String,
    val especialidad: String,
    val experiencia : Int,
    val tarifaHora: Double
)