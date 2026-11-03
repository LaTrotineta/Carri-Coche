package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.La_Trotineta.Carri_Coche.service.CircuitoService
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * @author Alexander Juan Drago
 * Entidad:Circuitos
* */
@Controller
class CircuitoController(
    private val circuitoService: CircuitoService,
    private val resourceLoader: ResourceLoader
) {
    private val rutaStatic = "img/circuito.jpeg"

    // 1. LISTAR
    @GetMapping("/circuitos")
    fun listarCircuitos(@RequestParam(name = "pais") pais: String? , model: Model): String {
        val listaCircuitos = circuitoService.listar()
        val valores = when {
            pais != null && pais != "" -> listaCircuitos.filter { it.pais == pais }
            else -> listaCircuitos
        }
        model.addAttribute("listaCircuitos", listaCircuitos)
        model.addAttribute("circuitos", valores)
        model.addAttribute("pais", pais)
        return "circuitos/circuitos"
    }

    // 2. DETALLE
    @GetMapping("/circuito/{id_circuito}")
    fun verCircuito(@PathVariable id_circuito: Int, model: Model): String {
        val circuito = circuitoService.buscar(id_circuito) ?: return "error"
        model.addAttribute("circuito", circuito)
        return "circuitos/detalleCircuito"
    }
    // 3. FORMULARIO NUEVA PLANTA (pasa un objeto vacío con ID 0)
    @GetMapping("/circuito/nuevo")
    fun nuevoCircuito(model: Model): String {
        val circuitoVacia = Circuito(circuitoService.listar().last().id_circuito+1, "", "", 0, 0.0, rutaStatic)
        model.addAttribute("circuito", circuitoVacia)
        model.addAttribute("titulo", "Nuevo Circuito")
        return "circuitos/formCircuito"
    }

    // 4. FORMULARIO EDITAR PLANTA (pasa el objeto existente)
    @GetMapping("/circuito/editar/{id_circuito}")
    fun editarCircuito(@PathVariable id_circuito: Int, model: Model): String {
        val circuito = circuitoService.buscar(id_circuito) ?: return "redirect:/circuitos"
        model.addAttribute("circuito", circuito)
        model.addAttribute("titulo", "Editar Circuito")
        return "circuitos/formCircuito"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/circuito/guardar")
    fun guardarCircuito(@ModelAttribute circuito: Circuito, @RequestParam("archivoAdjunto") file: MultipartFile?): String {
        if (file != null) {
            try {
                val uploadDir = resourceLoader.getResource("classpath:static/uploads/circuitos/").file.absolutePath

                val directory = File(uploadDir)
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val fileName = file.originalFilename

                val destinationPath = Paths.get(uploadDir, fileName)
                Files.copy(file.inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING)

                circuito.rutaImagen = "/uploads/$fileName"
            }catch (e: IOException) {
                circuito.rutaImagen = rutaStatic
            }
        } else {
            circuito.rutaImagen = rutaStatic
        }
        circuitoService.guardar(circuito)
        return "redirect:/circuitos"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/circuito/borrar/{id_circuito}")
    fun borrarCircuito(@PathVariable id_circuito: Int): String {
        circuitoService.borrar(id_circuito)
        return "redirect:/circuitos"
    }
}