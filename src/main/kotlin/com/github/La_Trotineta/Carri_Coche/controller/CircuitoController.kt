package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Circuito
import com.github.La_Trotineta.Carri_Coche.service.CircuitoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class CircuitoController(private val circuitoService: CircuitoService) {

    // 1. LISTAR
    @GetMapping("/circuitos")
    fun listarPlantas(model: Model): String {
        model.addAttribute("circuitos", circuitoService.listar())
        return "circuitos"
    }

    // 2. DETALLE
    @GetMapping("/circuito/{id_circuito}")
    fun verCircuito(@PathVariable id_circuito: Int, model: Model): String {
        val circuito = circuitoService.buscar(id_circuito) ?: return "errorCircuitos"
        model.addAttribute("circuito", circuito)
        return "detalleCircuito"
    }

    // 3. FORMULARIO NUEVA PLANTA (pasa un objeto vacío con ID 0)
    @GetMapping("/circuitos/nueva")
    fun nuevoCircuito(model: Model): String {
        val circuitoVacia = Circuito(0, "", "", 0, 0.0)
        model.addAttribute("circuito", circuitoVacia)
        model.addAttribute("titulo", "Nueva Circuito")
        return "formCircuito"
    }

    // 4. FORMULARIO EDITAR PLANTA (pasa el objeto existente)
    @GetMapping("/circuitos/editar/{id_circuito}")
    fun editarCircuito(@PathVariable id_circuito: Int, model: Model): String {
        val circuito = circuitoService.buscar(id_circuito) ?: return "redirect:/circuitos"
        model.addAttribute("circuito", circuito)
        model.addAttribute("titulo", "Editar Circuito")
        return "formCircuito"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/circuitos/guardar")
    fun guardarCircuito(@ModelAttribute circuito: Circuito): String {
        circuitoService.guardar(circuito)
        return "redirect:/circuitos"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/circuitos/borrar/{id_circuito}")
    fun borrarCircuito(@PathVariable id_circuito: Int): String {
        circuitoService.borrar(id_circuito)
        return "redirect:/circuitos"
    }
}