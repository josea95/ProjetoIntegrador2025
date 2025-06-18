package org.example.model.repository;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FilaPedidoRepository {

    private final EntityManager em;

    public FilaPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist( pedido );
        em.getTransaction().commit();
    }

    public void atualizar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge( pedido );
        em.getTransaction().commit();
    }

    public void deletar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove( em.contains( pedido ) ? pedido : em.merge( pedido ) );
        em.getTransaction().commit();
    }

    public FilaPedidoEntity buscarPorSenha(String senha) {
        return em.createQuery(
                        "SELECT f FROM FilaPedidoEntity f WHERE f.senhaPedido = :senha",
                        FilaPedidoEntity.class
                )
                .setParameter( "senha", senha )
                .getResultStream()
                .findFirst()
                .orElse( null );
    }

    public Integer buscarUltimaSenhaDoDia() {

        Integer ultimaSenha = em.createQuery(
                        // Busca a maior > MAX -senha gerada no dia atual, convertendo > CAST() - de texto para número .
                        "SELECT MAX(CAST(p.senhaPedido AS integer)) " +
                                "FROM FilaPedidoEntity p " +
                                "WHERE p.dataPedido = :hoje",
                        Integer.class
                )
                .setParameter( "hoje", LocalDate.now() )
                .getSingleResult();

        Integer ultimaNoHistorico = em.createQuery(
                "SELECT MAX(CAST(h.senhaPedido AS integer)) FROM HistoricoPedidoEntity h",
                Integer.class
        ).getSingleResult();

        /* Se 'ultimaSenha' não for nula, retorna ela mesmo.
         se historico  nao for nulo, retorna ela mesma.
         Se for nula (ou seja, não há nenhuma senha salva ainda), retorna 0 como valor padrão.*/

        int fila = ultimaSenha != null ? ultimaSenha : 0;
        int historico = ultimaNoHistorico != null ? ultimaNoHistorico : 0;


        // retorna o maior valor entre as variáveis fila e historico.//
        return Math.max(fila, historico);
    }

    public Integer buscarUltimaSenhaAposHorario(LocalDate data, LocalTime horario) {
        Integer ultimaSenha = em.createQuery(
                        // Busca a maior > MAX -senha gerada no dia atual, convertendo > CAST() - de texto para número .
                        "SELECT MAX(CAST(p.senhaPedido AS integer)) " +
                                "FROM FilaPedidoEntity p " +
                                "WHERE p.dataPedido = :data AND p.horaPedido >= :horario",
                        Integer.class
                )
                .setParameter( "data", data )
                .setParameter( "horario", horario )
                .getSingleResult();

        /* Se 'ultimaSenha' não for nula, retorna ela mesmo.
         Se for nula (ou seja, não há nenhuma senha salva ainda), retorna 0 como valor padrão.*/
        return ultimaSenha != null ? ultimaSenha : 0;
    }

    public List<FilaPedidoEntity> listarPorUsuario(UsuarioEntity usuario) {
        return em.createQuery(
                        "SELECT p FROM FilaPedidoEntity p WHERE p.usuario = :usuario",
                        FilaPedidoEntity.class
                )
                .setParameter( "usuario", usuario )
                .getResultList();
    }
}
