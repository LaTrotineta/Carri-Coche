package com.github.La_Trotineta.Carri_Coche.model

data class Mecanico(
    val id_mecanico: Int,
    val nombre: String,
    val especialidad: String,
    val experiencia : Int,
    val tarifaHora: Double
)