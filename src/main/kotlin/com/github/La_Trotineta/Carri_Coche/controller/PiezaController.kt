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
    // 1. LISTAR
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

    // 2. DETALLE
    @GetMapping("/pieza/{id_pieza}")
    fun verPieza(@PathVariable id_pieza: Int, model: Model): String {
        val pieza = piezaService.buscar(id_pieza) ?: return "errorPieza"
        model.addAttribute("pieza", pieza)
        return "piezas/detallePieza"
    }

    // 3. FORMULARIO NUEVA PLANTA (pasa un objeto vacío con ID 0)
    @GetMapping("/piezas/nueva")
    fun nuevaPieza(model: Model): String {
        val piezaVacia = Pieza(0, "", "", 0, 0.0)
        model.addAttribute("pieza", piezaVacia)
        model.addAttribute("titulo", "Nueva Pieza")
        return "piezas/formPieza"
    }

    // 4. FORMULARIO EDITAR PLANTA (pasa el objeto existente)
    @GetMapping("/piezas/editar/{id_pieza}")
    fun editarPieza(@PathVariable id_pieza: Int, model: Model): String {
        val pieza = piezaService.buscar(id_pieza) ?: return "redirect:/piezas"
        model.addAttribute("pieza", pieza)
        model.addAttribute("titulo", "Editar Pieza")
        return "piezas/formPieza"
    }

    // 5. PROCESAR GUARDADO (Sirve tanto para crear como para editar)
    @PostMapping("/piezas/guardar")
    fun guardarPieza(@ModelAttribute pieza: Pieza): String {
        piezaService.guardar(pieza)
        return "redirect:/piezas"
    }

    // 6. PROCESAR BORRADO
    @GetMapping("/piezas/borrar/{id_pieza}")
    fun borrarPieza(@PathVariable id_pieza: Int): String {
        piezaService.borrar(id_pieza)
        return "redirect:/piezas"
    }
}