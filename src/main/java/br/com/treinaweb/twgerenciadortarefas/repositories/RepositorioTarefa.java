/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.twgerenciadortarefas.repositories;

import br.com.treinaweb.twgerenciadortarefas.modelos.Tarefa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author programmer
 */
@Repository
public interface RepositorioTarefa extends JpaRepository<Tarefa, Long> {
    
    @Query("SELECT t FROM tar_tarefa t WHERE t.usuario.email=:emailUsuario")
    List<Tarefa> carregarTarefasPorUsuario(@Param("emailUsuario") String email);
}
