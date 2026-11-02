package com.github.La_Trotineta.Carri_Coche.model

/**
 * @author Alexander Juan Drago
 * Entidad:Circuitos
 */
data class Circuito(
    val id_circuito : Int,
    var nombre :String,
    var pais :String,
    var curvas :Int,
    var longitud: Double)