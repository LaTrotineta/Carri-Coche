package com.github.La_Trotineta.Carri_Coche.model

/**
 * @author Junfeng
 *
 * Dataclass de Coches
 * @param matricula
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
    var consumo: Double
)