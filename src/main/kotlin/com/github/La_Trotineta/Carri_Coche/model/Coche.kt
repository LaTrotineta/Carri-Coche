package com.github.La_Trotineta.Carri_Coche.model

/**
 * @author Junfeng
 * Entidad: Coche
 *
 *  @param matricula
 * @param modelo
 * @param marca
 * @param potencia
 * @param consumo
 */
data class Coche(
    val matricula: String,
    var modelo: String,
    var marca: String,
    var potencia: Int,
    var consumo: Double,
    var rutaImagen: String  = ""
)