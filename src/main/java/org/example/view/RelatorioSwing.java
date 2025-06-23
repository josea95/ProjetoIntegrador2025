package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.example.model.services.RelatorioService;

public class RelatorioSwing extends JFrame {

    private final RelatorioService relatorioService;
    private final JTextArea texto;

    public RelatorioSwing(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;

        // Título da janela
        setTitle("Relatório de Vendas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto
        texto = new JTextArea();
        texto.setEditable(false);
        JScrollPane scroll = new JScrollPane(texto);
        add(scroll, BorderLayout.CENTER);

        // Botão Atualizar
        JButton btnAtualizar = new JButton("Atualizar Relatório");
        btnAtualizar.addActionListener(e -> atualizarRelatorio());

        JPanel painelInferior = new JPanel();
        painelInferior.add(btnAtualizar);

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");
        btnVoltar.addActionListener(e -> {
            dispose();// Fecha a tela atual
        });
        painelInferior.add(btnVoltar);
        add(painelInferior, BorderLayout.SOUTH);

        // Mostra relatório ao abrir
        atualizarRelatorio();

        setVisible(true);
    }

    private void atualizarRelatorio() {
        // Captura apenas a saída do relatório, não os logs do Hibernate
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(buffer);
        PrintStream original = System.out;

        System.setOut(ps);  // redireciona temporariamente
        relatorioService.gerarResumoVendasPorCategoria(LocalDate.now());
        System.out.flush();
        System.setOut(original); // restaura saída padrão

        texto.setText(buffer.toString());
    }
}
