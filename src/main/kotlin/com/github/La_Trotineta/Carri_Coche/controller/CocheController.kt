package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Coche
import com.github.La_Trotineta.Carri_Coche.service.CocheService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CocheController(private val cocheService: CocheService) {
    @GetMapping("/coches")
    fun listarCoches(model: Model): String {
        model.addAttribute("coches", cocheService.listar())
        return "coches"
    }

    @GetMapping("/coche/{matricula}")
    fun verCoche(@PathVariable matricula: String, model: Model): String {
        val coche = cocheService.buscar(matricula) ?: return "error"
        model.addAttribute("coche", coche)
        return "detalleCoche"
    }

    @GetMapping("/coches/nuevo")
    fun nueva(model: Model): String {
        val cocheVacio = Coche("", "", "", 0, 0.0)
        model.addAttribute("coche", cocheVacio)
        model.addAttribute("titulo", "Nueva Coche")
        return "formCoche"
    }

    @GetMapping("/coches/editar/{matricula}")
    fun editarCoche(@PathVariable matricula: String, model: Model): String {
        val coche = cocheService.buscar(matricula) ?: return "redirect:/coches"
        model.addAttribute("coche", coche)
        model.addAttribute("titulo", "Editar Coche")
        return "formCoche"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/coches/guardar")
    fun guardarPlanta(@ModelAttribute coche: Coche): String {
        cocheService.guardar(coche)
        return "redirect:/coches"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/coches/borrar/{matricula}")
    fun borrarCoche(@PathVariable matricula: String): String {
        cocheService.borrar(matricula)
        return "redirect:/coches"
    }
}