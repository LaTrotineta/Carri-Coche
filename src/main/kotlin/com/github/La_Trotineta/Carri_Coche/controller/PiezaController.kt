package com.github.La_Trotineta.Carri_Coche.controller

import com.github.La_Trotineta.Carri_Coche.model.Pieza
import com.github.La_Trotineta.Carri_Coche.service.PiezaService
import jakarta.websocket.server.PathParam
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Santiago Gonzalez Quijada
 * Entidad: PiezaController
 */

@Controller
class PiezaController (private val piezaService : PiezaService) {
    @GetMapping("/piezas")
    fun listarPiezas(@RequestParam(name = "fabricante", required = false) fabricante: String?, model: Model): String {
        val listaPiezas = piezaService.listar();

        val valores = when {
            fabricante != null && fabricante != "" -> listaPiezas.filter { it.fabricante == fabricante }
            else -> listaPiezas
        }
        model.addAttribute("listaPiezas", listaPiezas)
        model.addAttribute("piezas", valores)
        model.addAttribute("fabricante", fabricante)
        return "piezas/piezas"
    }

    @GetMapping("/pieza/{id_pieza}")
    fun verPieza(@PathVariable id_pieza: Int, model: Model): String {
        val pieza = piezaService.buscar(id_pieza) ?: return "error"
        model.addAttribute("pieza", pieza)
        return "piezas/detallePieza"
    }

    @GetMapping("/pieza/nuevo")
    fun nuevaPieza(model: Model): String {
        val piezaVacia = Pieza(0, "", "", 0, 0.0)
        model.addAttribute("pieza", piezaVacia)
        model.addAttribute("titulo", "Nueva Pieza")
        return "piezas/formPieza"
    }

    @GetMapping("/pieza/editar/{id_pieza}")
    fun editarPieza(@PathVariable id_pieza: Int, model: Model): String {
        val pieza = piezaService.buscar(id_pieza) ?: return "redirect:/piezas"
        model.addAttribute("pieza", pieza)
        model.addAttribute("titulo", "Editar Pieza")
        return "piezas/formPieza"
    }

    @PostMapping("/pieza/guardar")
    fun guardarPieza(@ModelAttribute pieza: Pieza): String {
        piezaService.guardar(pieza)
        return "redirect:/piezas"
    }

    @GetMapping("/pieza/borrar/{id_pieza}")
    fun borrarPieza(@PathVariable id_pieza: Int): String {
        piezaService.borrar(id_pieza)
        return "redirect:/piezas"
    }
}