package org.example.services;
import org.example.entities.UsuarioEntity;
import org.example.repository.UsuarioRepository;
import javax.persistence.EntityManager;

import java.util.List;

public class UsuarioService {
private UsuarioService usuarioService;
public UsuarioService(EntityManager em) {
    this.usuarioService = new UsuarioService(em);
}

}
