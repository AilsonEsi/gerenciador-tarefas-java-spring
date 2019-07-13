/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.twgerenciadortarefas.services;

import br.com.treinaweb.twgerenciadortarefas.modelos.Usuario;
import br.com.treinaweb.twgerenciadortarefas.repositories.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author programmer
 */
@Service
public class ServicoUsuario {

    @Autowired
    private UsuariosRepositorio ur;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario encontrarPorEmail(String email) {

        return ur.findUsuarioByEmail(email);
    }

    public void salvar(Usuario usuario) {
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        ur.save(usuario);
    }
}
