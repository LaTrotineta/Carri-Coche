package utilidad

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.nio.file.Files
import java.nio.file.Path

fun leerDatosCircuitoCSV(ruta: Path):List<Circuito>{
    //Crear una variable donde almacenaremos la lista
    var circuitos: List<Circuito> = emptyList()

    //Crear una variable que almacene todos los datos delimitando con un ;
    if (!Files.isReadable(ruta)) {
        println("Error: No se puede leer el fichero en la ruta: $ruta")
    } else {
        val reader = csvReader {
            delimiter = ';'
        }
// Leemos todas las filas del CSV con la variable que hemos creado antes(devuelve List<List<String>>)
        val filas: List<List<String>> = reader.readAll(ruta.toFile())
        // Convertimos las filas de texto en objetos Coche
        circuitos = filas.mapNotNull { columnas ->
            if (columnas.size >= 5) {
                try {
                    val id_circuito = columnas[0].toInt()
                    val nombre = columnas[1]
                    val pais = columnas[2]
                    val curvas = columnas[3].toInt()
                    val longitud = columnas[4].toDouble()
                    Circuito(id_circuito, nombre, pais, curvas, longitud)

                } catch (e: Exception) {
                    println("Fila inválida ignorada: $columnas -> Error: ${e.message}")
                    null
                }
            } else {
                println("Fila con formato incompleto ignorada: $columnas")
                null
            }
        }
    }
    //Devolver la lista parseada
    return circuitos
}
