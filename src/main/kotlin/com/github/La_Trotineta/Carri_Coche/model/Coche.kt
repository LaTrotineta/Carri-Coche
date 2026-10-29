package com.github.La_Trotineta.Carri_Coche.model


data class Coche(
    val matricula: String,
    var modelo: String,
    var marca: String,
    var potencia: Int,
    var consumo: Double
)