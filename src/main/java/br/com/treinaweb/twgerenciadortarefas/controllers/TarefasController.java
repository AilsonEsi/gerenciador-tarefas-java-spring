/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.twgerenciadortarefas.controllers;

import br.com.treinaweb.twgerenciadortarefas.modelos.Tarefa;
import br.com.treinaweb.twgerenciadortarefas.modelos.Usuario;
import br.com.treinaweb.twgerenciadortarefas.repositories.RepositorioTarefa;
import br.com.treinaweb.twgerenciadortarefas.services.ServicoUsuario;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author programmer
 */
@Controller
@RequestMapping("tarefas")
public class TarefasController {

    @Autowired
    private RepositorioTarefa rt;
    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/listar");
        String emailUsuario = request.getUserPrincipal().getName();
        mv.addObject("tarefas", rt.carregarTarefasPorUsuario(emailUsuario));
        return mv;
    }

    @GetMapping("/inserir")
    public ModelAndView inserir() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/inserir");
        mv.addObject("tarefa", new Tarefa());
        return mv;
    }

    @RequestMapping(value = "/inserir", method = RequestMethod.POST)
    //@CrossOrigin(origins = "*")
    public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();
        if (tarefa.getDataExpiracao() == null) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
                    "A data de Expiracao e Obrigatorio");
        } else {
            if (tarefa.getDataExpiracao().before(new Date())) {

                result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
                        "A data de Expiracao nao pode ser anterior a data atual");
            }
        }

        if (result.hasErrors()) {
            mv.setViewName("redirect:/tarefas/inserir");
            mv.addObject(tarefa);
        } else {
            String emailUsuario = request.getUserPrincipal().getName();
            Usuario usuariologado = servicoUsuario.encontrarPorEmail(emailUsuario);
            mv.setViewName("redirect:/tarefas/listar");
            tarefa.setUsuario(usuariologado);
            rt.save(tarefa);

        }
        return mv;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable Long id) {

        ModelAndView mv = new ModelAndView();
        Tarefa tarefa = rt.getOne(id);
        mv.addObject("tarefa", tarefa);
        mv.setViewName("tarefas/alterar");
        return mv;
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    //@CrossOrigin(origins = "*")
    public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result) {

        ModelAndView mv = new ModelAndView();
        if (tarefa.getDataExpiracao() == null) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
                    "A data de Expiracao e Obrigatorio");
        } else {
            if (tarefa.getDataExpiracao().before(new Date())) {

                result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
                        "A data de Expiracao nao pode ser anterior a data atual");
            }
        }

        if (result.hasErrors()) {
            mv.setViewName("redirect:/tarefas/alterar");
            mv.addObject(tarefa);
        } else {
            mv.setViewName("redirect:/tarefas/listar");
            rt.save(tarefa);

        }
        return mv;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable Long id) {

        ModelAndView mv = new ModelAndView();
        rt.deleteById(id);
        mv.setViewName("redirect:/tarefas/listar");
        return mv;
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable Long id) {

        Tarefa t = rt.getOne(id);
        t.setConcluida(true);
        rt.save(t);

        return "redirect:/tarefas/listar";
    }
}
