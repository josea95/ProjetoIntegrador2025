package org.example;

import org.example.entity.FilaPedido;
import org.example.entity.HistoricoPedido;
import org.example.entity.Usuario;
import repository.FilaPedidoRepository;
import repository.UsuarioRepository;
import repository.PedidoRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UsuarioRepository usuarioRepo = new UsuarioRepository();
        FilaPedidoRepository filaRepo = new FilaPedidoRepository();
        PedidoRepository historicoRepo = new PedidoRepository();

        // 1. Criar um novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome("José Cliente");
        usuario.setEmail("cliente@marmita.com");
        usuario.setSenha("123456");
        usuarioRepo.salvar(usuario);
        System.out.println("Usuário criado: " + usuario.getNome());

        // 2. Criar um pedido na fila
        FilaPedido pedido = new FilaPedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(Date.valueOf(LocalDate.now()));
        pedido.setHoraPedido(Time.valueOf(LocalTime.now()));
        pedido.setStatusPedido("Em preparo");
        pedido.setSenhaPedido("M123"); // isso depois pode ser gerado automaticamente
        pedido.setObservacao("Sem cebola");
        filaRepo.salvar(pedido);
        System.out.println("Pedido inserido na fila!");

        // 3. Listar os pedidos da fila
        List<FilaPedido> fila = filaRepo.listarTodos();
        System.out.println("\nPedidos na fila:");
        for (FilaPedido p : fila) {
            System.out.println("- Senha: " + p.getSenhaPedido() + " | Status: " + p.getStatusPedido());
        }

        // 4. Mover o pedido da fila para o histórico
        HistoricoPedido historico = new HistoricoPedido();
        historico.setNomeCliente(usuario.getNome());
        historico.setUsuario(usuario);
        historico.setDataPedido(pedido.getDataPedido());
        historico.setHoraPedido(pedido.getHoraPedido());
        historico.setSenhaPedido(pedido.getSenhaPedido());
        historico.setValorPedido(22.50f); // valor fictício
        historico.setObservacao(pedido.getObservacao());
        historicoRepo.salvar(historico);

        // 5. Remover da fila
        filaRepo.deletar(pedido);
        System.out.println("\nPedido movido para o histórico e removido da fila.");
    }
}
