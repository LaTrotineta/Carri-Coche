package com.github.La_Trotineta.Carri_Coche.model

/**
 * @author Santiago Gonzalez Quijada
 * Entidad: Pieza
 */
data class Pieza(
    var id_pieza: Int,
    var nombre: String,
    var fabricante: String,
    var stock: Int,
    var precio: Double,
    var rutaImagen: String = ""
)
