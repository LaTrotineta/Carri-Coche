package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.La_Trotineta.Carri_Coche.service.MecanicoServicio
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
 *
 * @author Antonio de la torre Gimenez
 * Entidad: Mecanicos
 *
 * */


@Controller
class MecanicoController(
    private val mecanicoServicio: MecanicoServicio,
    private val resourceLoader: ResourceLoader
) {

    private val rutaStatic = "img/circuito.jpg"


    @GetMapping("/mecanicos")
    fun listarMecanicos(@RequestParam(name = "especialidad", required = false) especialidad:String?, model: Model): String {
        val listaMecanicos = mecanicoServicio.listar()
        val valores = when {
            especialidad != null && especialidad != "" -> listaMecanicos.filter { it.especialidad == especialidad }
            else -> listaMecanicos
        }
        model.addAttribute("listamecanicos", listaMecanicos)
        model.addAttribute("mecanicos", valores)
        model.addAttribute("especialidad", especialidad)
        return "mecanicos/mecanicos"
    }

    // 2. DETALLE
    @GetMapping("/mecanico/{id_mecanico}")
    fun verMecanico(@PathVariable id_mecanico: Int, model: Model): String {
        val mecanico = mecanicoServicio.buscar(id_mecanico) ?: return "error"
        model.addAttribute("mecanico", mecanico)
        return "mecanicos/detalleMecanico"
    }

    // 3. FORMULARIO NUEVO MECANICO (pasa un objeto vacío con ID 0)
    @GetMapping("/mecanico/nuevo")
    fun nuevoMecanico(model: Model): String {
        val mecanicoVacio = Mecanico(0, "", "", 0, 0.0, rutaStatic)
        model.addAttribute("mecanico", mecanicoVacio)
        model.addAttribute("titulo", "Nuevo Mecanico")
        return "mecanicos/formMecanico"
    }

    // 4. FORMULARIO EDITAR MECANICO (pasa el objeto existente)
    @GetMapping("/mecanico/editar/{id_mecanico}")
    fun editarMecanico(@PathVariable id_mecanico: Int, model: Model): String {
        val mecanico = mecanicoServicio.buscar(id_mecanico) ?: return "redirect:/mecanicos"
        model.addAttribute("mecanico", mecanico)
        model.addAttribute("titulo", "Editar Mecanico")
        return "mecanicos/formMecanico"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/mecanico/guardar")
    fun guardarMecanico(@ModelAttribute mecanico: Mecanico, @RequestParam("archivoAdjunto") file: MultipartFile?): String {
        if (file != null) {
            try {
                val uploadDir = resourceLoader.getResource("classpath:static/uploads/mecanicos/").file.absolutePath

                val directory = File(uploadDir)
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val fileName = file.originalFilename

                val destinationPath = Paths.get(uploadDir, fileName)
                Files.copy(file.inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING)

                mecanico.rutaImagen = "/uploads/$fileName"
            }catch (e: IOException) {
                mecanico.rutaImagen = rutaStatic
            }
        } else {
            mecanico.rutaImagen = rutaStatic
        }
        mecanicoServicio.guardar(mecanico)
        return "redirect:/mecanicos"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/mecanico/borrar/{id_mecanico}")
    fun borrarMecanico(@PathVariable id_mecanico: Int): String {
        mecanicoServicio.borrar(id_mecanico)
        return "redirect:/mecanicos"
    }
}