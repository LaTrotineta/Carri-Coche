package com.github.La_Trotineta.Carri_Coche.model

data class Pieza(
    var id_pieza: Int,
    var nombre: String,
    var fabricante: String,
    var stock: Int,
    var precio: Double
)
