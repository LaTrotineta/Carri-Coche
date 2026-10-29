package com.github.La_Trotineta.Carri_Coche.util

import java.nio.file.Files
import java.nio.file.Path

object Utils {
    fun crearArchivoSiNoExiste(path: Path) {
        Files.createDirectories(path.parent)
        if (!Files.exists(path)) {
            Files.createFile(path)
        }
    }

    fun validarMatricula(matricula: String): Boolean{
        val matriculasOrdinariasRegex: Regex = "^[0-9]{4}[B-DF-HJ-NP-TV-Z]{3}$".toRegex()

        return matriculasOrdinariasRegex.matches(matricula) && matricula.length == 7
    }
}