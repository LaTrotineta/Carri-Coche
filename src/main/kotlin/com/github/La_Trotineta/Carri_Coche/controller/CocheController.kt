package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.La_Trotineta.Carri_Coche.service.CocheService
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


/**
 * @author Junfeng
 * Entidad: Coche
 */
@Controller
class CocheController(
    private val cocheService: CocheService,
    private val resourceLoader: ResourceLoader
) {

    private val rutaStatic = "img/circuito.jpg"

    @GetMapping("/coches")
    fun listarCoches(@RequestParam(name = "marca", required = false) marca: String? , model: Model): String {
        val listaCoche = cocheService.listar()
        val valores = when {
            marca != null && marca != "" -> listaCoche.filter { it.marca == marca }
            else -> listaCoche
        }
        model.addAttribute("listaCoches", listaCoche)
        model.addAttribute("coches", valores)
        model.addAttribute("marca", marca)
        return "coches/coches"
    }

    @GetMapping("/coche/{matricula}")
    fun verCoche(@PathVariable matricula: String, model: Model): String {
        val coche: Coche = cocheService.buscar(matricula) ?: return "error"
        model.addAttribute("coche", coche)
        return "coches/detalleCoche"
    }

    @GetMapping("/coche/nuevo")
    fun nueva(model: Model): String {
        val cocheVacio = Coche("", "", "", 0, 0.0, rutaStatic)
        model.addAttribute("coche", cocheVacio)
        model.addAttribute("titulo", "Nueva Coche")

        return "coches/formCoche"
    }

    @GetMapping("/coche/editar/{matricula}")
    fun editarCoche(@PathVariable matricula: String, model: Model): String {
        val coche = cocheService.buscar(matricula) ?: return "redirect:/coches"
        model.addAttribute("coche", coche)
        model.addAttribute("titulo", "Editar Coche")
        return "coches/formCoche"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/coche/guardar")
    fun guardarCoche(@ModelAttribute coche: Coche, @RequestParam("archivoAdjunto") file: MultipartFile?): String {
        if (file != null) {
            try {
                val uploadDir = resourceLoader.getResource("classpath:static/uploads/coches/").file.absolutePath

                val directory = File(uploadDir)
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val fileName = file.originalFilename

                val destinationPath = Paths.get(uploadDir, fileName)
                Files.copy(file.inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING)

                coche.rutaImagen = "/uploads/$fileName"
            }catch (e: IOException) {
                coche.rutaImagen = rutaStatic
            }
        } else {
            coche.rutaImagen = rutaStatic
        }
        cocheService.guardar(coche)
        return "redirect:/coches"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/coche/borrar/{matricula}")
    fun borrarCoche(@PathVariable matricula: String): String {
        cocheService.borrar(matricula)
        return "redirect:/coches"
    }
}