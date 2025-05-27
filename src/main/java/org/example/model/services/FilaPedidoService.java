//package org.example.model.services;
//
//import org.example.model.entities.FilaPedidoEntity;
//import org.example.model.entities.ProdutoEntity;
//import org.example.model.entities.ProdutoPedidoEntity;
//import org.example.model.enums.StatusPedido;
//import org.example.model.repository.FilaPedidoRepository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class FilaPedidoService {
//
//    private final FilaPedidoRepository filaRepo;
//
//    public FilaPedidoService(EntityManager em) {
//        this.filaRepo = new FilaPedidoRepository(em);
//    }
//
//    public void listarFilaPedidos() {
//        List<FilaPedidoEntity> pedidos = filaRepo.listarTodos();
//        boolean encontrou = false;
//
//        if (pedidos.isEmpty()) {
//            System.out.println("Nenhum pedido na fila.");
//        } else {
//            for (FilaPedidoEntity pedido : pedidos) {
//                if (pedido.getStatusPedido() == StatusPedido.FILA || pedido.getStatusPedido() == StatusPedido.PREPARANDO) {
//                    encontrou = true;
//                    System.out.println("Senha: " + pedido.getSenhaPedido());
//                    for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
//                        ProdutoEntity produto = produtoPedido.getProduto();
//                        System.out.println(" - " + produto.getNome() + " - R$" + produto.getPreco());
//                    }
//                    System.out.println("Data: " + pedido.getDataPedido());
//                    System.out.println("Status: " + pedido.getStatusPedido());
//                    System.out.println("-----------------------------");
//                }
//            }
//            if (!encontrou) {
//                System.out.println("Nenhum pedido com status FILA ou PREPARANDO encontrado.");
//            }
//        }
//    }
//
//    // FilaPedidoService.java
//    public void cancelarPedido(String senha) {
//        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);
//
//        if (pedido == null) {
//            System.out.println("Pedido não encontrado.");
//        } else if (pedido.getStatusPedido() != StatusPedido.FILA) {
//            System.out.println("O pedido já está em preparo ou finalizado e não pode ser cancelado.");
//        } else {
//            filaRepo.deletar(pedido);
//            System.out.println("Pedido cancelado com sucesso.");
//        }
//    }
//
//    public void pesquisarPedido(String senha) {
//        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);
//
//        if (pedido == null) {
//            System.out.println("Pedido não encontrado.");
//        } else {
//            System.out.println("Senha: " + pedido.getSenhaPedido());
//            for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
//                ProdutoEntity produto = produtoPedido.getProduto();
//                System.out.println(" - " + produto.getNome() + " - R$" + produto.getPreco());
//            }
//            System.out.println("Data: " + pedido.getDataPedido());
//            System.out.println("Status: " + pedido.getStatusPedido());
//            System.out.println("-----------------------------");
//        }
//    }
//
//}
//
//
//package org.example.model.services;
//
//import org.example.model.entities.FilaPedidoEntity;
//import org.example.model.entities.ProdutoEntity;
//import org.example.model.entities.ProdutoPedidoEntity;
//import org.example.model.entities.UsuarioEntity;
//import org.example.model.enums.StatusPedido;
//import org.example.model.repository.FilaPedidoRepository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class FilaPedidoService {
//
//    private final FilaPedidoRepository filaRepo;
//
//    public FilaPedidoService(EntityManager em) {
//        this.filaRepo = new FilaPedidoRepository(em);
//    }
//
//    public void listarFilaPedidos() {
//        List<FilaPedidoEntity> pedidos = filaRepo.listarTodos();
//        boolean encontrou = false;
//
//        if (pedidos.isEmpty()) {
//            System.out.println("Nenhum pedido na fila.");
//        } else {
//            for (FilaPedidoEntity pedido : pedidos) {
//                if (pedido.getStatusPedido() == StatusPedido.FILA || pedido.getStatusPedido() == StatusPedido.PREPARANDO) {
//                    encontrou = true;
//                    System.out.println("Senha: " + pedido.getSenhaPedido());
//                    for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
//                        ProdutoEntity produto = produtoPedido.getProduto();
//                        System.out.println("- " + produto.getNome() + " - R$" + produto.getPreco());
//                    }
//                    System.out.println("Data: " + pedido.getDataPedido());
//                    System.out.println("Hora: " + pedido.getHoraPedido());
//                    System.out.println("Status: " + pedido.getStatusPedido());
//                    System.out.println("-----------------------------");
//                }
//            }
//            if (!encontrou) {
//                System.out.println("Nenhum pedido com status FILA ou PREPARANDO encontrado.");
//            }
//        }
//    }
//
//    public void cancelarPedido(String senha) {
//        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);
//
//        if (pedido == null) {
//            System.out.println("Pedido não encontrado.");
//        } else if (pedido.getStatusPedido() != StatusPedido.FILA) {
//            System.out.println("O pedido já está em preparo ou finalizado e não pode ser cancelado.");
//        } else {
//            filaRepo.deletar(pedido);
//            System.out.println("Pedido cancelado com sucesso.");
//        }
//    }
//
//    public void pesquisarPedido(String senha) {
//        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);
//
//        if (pedido == null) {
//            System.out.println("Pedido não encontrado.");
//        } else {
//            System.out.println("Senha: " + pedido.getSenhaPedido());
//            for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
//                ProdutoEntity produto = produtoPedido.getProduto();
//                System.out.println("- " + produto.getNome() + " - R$" + produto.getPreco());
//            }
//            System.out.println("Data: " + pedido.getDataPedido());
//            System.out.println("Hora: " + pedido.getHoraPedido());
//            System.out.println("Status: " + pedido.getStatusPedido());
//            System.out.println("-----------------------------");
//        }
//    }
//
//    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
//        List<FilaPedidoEntity> pedidos = filaRepo.listarPorUsuario(usuarioLogado);
//        boolean encontrouFinalizados = false;
//
//        if (pedidos.isEmpty()) {
//            System.out.println("Nenhum pedido encontrado.");
//        } else {
//            for (FilaPedidoEntity pedido : pedidos) {
//                if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
//                    encontrouFinalizados = true;
//                    System.out.println("Senha: " + pedido.getSenhaPedido());
//                    StringBuilder produtosComprados = new StringBuilder();
//                    for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
//                        ProdutoEntity produto = produtoPedido.getProduto();
//                        produtosComprados.append("- ")
//                                .append(produto.getNome())
//                                .append("- R$")
//                                .append(produto.getPreco())
//                                .append("\n");
//                    }
//                    System.out.println("Produto(s):\n" + produtosComprados.toString());
//                    System.out.println("Data: " + pedido.getDataPedido());
//                    System.out.println("Hora: " + pedido.getHoraPedido());
//                    System.out.println("Status: " + pedido.getStatusPedido());
//                    System.out.println("-----------------------------");
//                }
//            }
//            if (!encontrouFinalizados) {
//                System.out.println("Nenhum pedido FINALIZADO encontrado.");
//            }
//        }
//    }
//}

package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;

    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository(em);

    }

    public List<FilaPedidoEntity> listarFilaPedidos() {
        return filaRepo.listarTodos();
    }

    public FilaPedidoEntity pesquisarPedido(String senha) {
        return filaRepo.buscarPorSenha(senha);
    }

    public boolean cancelarPedido(String senha) {
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);

        if (pedido == null) {
            return false;
        }

        if (pedido.getStatusPedido() != StatusPedido.FILA) {
            return false;
        }

        filaRepo.deletar(pedido);
        return true;
    }

    public List<FilaPedidoEntity> verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        return filaRepo.listarPorUsuario(usuarioLogado);
    }
}

