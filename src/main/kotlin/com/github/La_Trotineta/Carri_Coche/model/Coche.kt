package com.github.junfeng52.modelo

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coche(
    @SerialName("matricula")
    @JacksonXmlProperty(localName = "matricula")
    val matriculaCoche: String,

    @SerialName("modelo")
    @JacksonXmlProperty(localName = "modelo")
    var modelo: String,

    @SerialName("marca")
    @JacksonXmlProperty(localName = "marca")
    var marca: String,

    @SerialName("potencia")
    @JacksonXmlProperty(localName = "potencia")
    var potencia: Int,

    @SerialName("consumo")
    @JacksonXmlProperty(localName = "consumo")
    var consumo: Double
) {
    fun toCSV(): String {
        return "$matriculaCoche;$modelo;$marca;$potencia;$consumo"
    }

    override fun toString(): String {
        return "-- Matricula:'$matriculaCoche', Modelo:'$modelo', Marca:'$marca', Potencia:$potencia, Consumo:$consumo)"
    }
}
