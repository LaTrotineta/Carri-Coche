package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Mecanico
import com.github.La_Trotineta.Carri_Coche.service.MecanicoServicio
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class MecanicoController(private val mecanicoServicio: MecanicoServicio) {

    // 1. LISTAR
    @GetMapping("/mecanicos")
    fun listarMecanicos(model: Model): String {
        model.addAttribute("mecanicos", mecanicoServicio.listar())
        return "mecanicos"
    }

    // 2. DETALLE
    @GetMapping("/mecanico/{id_mecanico}")
    fun verMecanico(@PathVariable id_mecanico: Int, model: Model): String {
        val mecanico = mecanicoServicio.buscar(id_mecanico) ?: return "errorMecanico"
        model.addAttribute("planta", mecanico)
        return "detalleMecanico"
    }

    // 3. FORMULARIO NUEVA PLANTA (pasa un objeto vacío con ID 0)
    @GetMapping("/mecanicos/nuevo")
    fun nuevoMecanico(model: Model): String {
        val mecanicoVacio = Mecanico(0, "", "", 0, 0.0)
        model.addAttribute("mecanico", mecanicoVacio)
        model.addAttribute("titulo", "Nuevo Mecanico")
        return "formMecanico"
    }

    // 4. FORMULARIO EDITAR PLANTA (pasa el objeto existente)
    @GetMapping("/plantas/editar/{id_planta}")
    fun editarMecanico(@PathVariable id_mecanico: Int, model: Model): String {
        val mecanico = mecanicoServicio.buscar(id_mecanico) ?: return "redirect:/mecanicos"
        model.addAttribute("mecanico", mecanico)
        model.addAttribute("titulo", "Editar Mecanico")
        return "formMecanico"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/mecanicos/guardar")
    fun guardarMecanico(@ModelAttribute mecanico: Mecanico): String {
        mecanicoServicio.guardar(mecanico)
        return "redirect:/mecanicos"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/mecanicos/borrar/{id_mecanico}")
    fun borrarMecanico(@PathVariable id_mecanico: Int): String {
        mecanicoServicio.borrar(id_mecanico)
        return "redirect:/plantas"
    }
}