/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.twgerenciadortarefas.repositories;

import br.com.treinaweb.twgerenciadortarefas.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author programmer
 */
@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuario, Long> {
    
    Usuario findUsuarioByEmail(String email);
}
