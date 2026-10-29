package utilidad

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter

import java.nio.file.Path

fun modificarRegistroCircuito(ruta: Path, circuito: List<Circuito>) {
    // Obtenemos los datos y los pasamos a MutableList para poder reemplazarlos
    val listaCircuitos = leerDatosCircuitoCSV(ruta).toMutableList()
    var indexBusqueda = -1

    //Buscar el circuito por su ID y obtener su posición (índice) en la lista
    while (true) {
        println("Dime un ID para modificar: ")
        val validar = readln().toIntOrNull()
        if (validar == null) {
            println("Error, ID inválido. Introduce un número.")
        } else {
            // Buscamos el índice donde el ID coincida con el introducido
            indexBusqueda = listaCircuitos.indexOfFirst { it.id_circuito == validar }
            if (indexBusqueda != -1) {
                println("¡ID encontrado!")
                break
            } else {
                println("Error, el ID no existe en la base de datos.")
            }
        }
    }

    //Extraemos el circuito a partir de su índice real en la lista
    val circuitoEncontrado = listaCircuitos[indexBusqueda]

    // Guardamos los valores actuales por defecto
    var nuevoNombre = circuitoEncontrado.nombre
    var nuevoPais = circuitoEncontrado.pais
    var nuevasCurvas = circuitoEncontrado.curvas
    var nuevaLongitud = circuitoEncontrado.longitud

    print("--------------------------------------        \n" +
            "-Dime el parámetro que quieres cambiar-\n" +
            "--------------------------------------\n" +
            "1. Nombre\n" +
            "2. Pais\n" +
            "3. Curvas\n" +
            "4. Longitud\n" +
            "0. Cancelar\n")

    val opcion = readln()
    when (opcion) {
        "1" -> {
            println("Dime el nuevo nombre:")
            nuevoNombre = readln()
        }
        "2" -> {
            println("Dime el nuevo pais:")
            nuevoPais = readln()
        }
        "3" -> {
            println("Dime las curvas nuevas del circuito:")
            nuevasCurvas = readln().toIntOrNull() ?: circuitoEncontrado.curvas
        }
        "4" -> {
            println("Dime la nueva longitud del circuito:")
            nuevaLongitud = readln().toDoubleOrNull() ?: circuitoEncontrado.longitud
        }
        "0" -> {
            println("Cancelando modificación...")
            return
        }
        else -> {
            println("Opción inválida. Cancelando...")
            return
        }
    }

    // 3. Creamos una nueva instancia de Circuito con los datos actualizados
    val circuitoModificado = Circuito(
        id_circuito = circuitoEncontrado.id_circuito, // El ID nunca cambia
        nombre = nuevoNombre,
        pais = nuevoPais,
        curvas = nuevasCurvas,
        longitud = nuevaLongitud
    )

    // 4. Reemplazamos el circuito antiguo por el nuevo en nuestra lista mutable
    listaCircuitos[indexBusqueda] = circuitoModificado

    // 5. Sobrescribimos el archivo CSV completo con la lista actualizada (append = false)
    csvWriter { delimiter = ';' }.open(ruta.toFile(), append = false) {
        listaCircuitos.forEach { c ->
            writeRow(c.id_circuito, c.nombre, c.pais, c.curvas, c.longitud)
        }
    }

    println("Registro modificado y guardado en CSV correctamente.")
}