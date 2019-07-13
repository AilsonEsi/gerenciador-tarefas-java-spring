/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.twgerenciadortarefas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author programmer
 */
@Controller
public class HomeController {
    
    @GetMapping(value = "/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/home");
        mv.addObject("mensagem","Mesagem do controller");
        return mv;
    }
    
}
