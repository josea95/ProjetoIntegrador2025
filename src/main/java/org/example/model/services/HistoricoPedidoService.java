package org.example.model.services;

import org.example.model.entities.HistoricoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.HistoricoPedidoRepository;
import javax.persistence.EntityManager;
import java.util.List;

public class HistoricoPedidoService {

    private final HistoricoPedidoRepository historicoRepo;

    public HistoricoPedidoService(EntityManager em) {
        this.historicoRepo = new HistoricoPedidoRepository(em);
    }

    public List<HistoricoPedidoEntity> verHistoricoPedidos(UsuarioEntity usuario) {
        return historicoRepo.listarHistoricoPorUsuario(usuario);
    }
}