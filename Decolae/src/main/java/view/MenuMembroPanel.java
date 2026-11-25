
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuMembroPanel extends JPanel {

    private FrameTelas frameTelas;

    private final Color FUNDO = new Color(11, 18, 32);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);

    private final String FONTE = "SansSerif";

    public MenuMembroPanel(FrameTelas frameTelas) {
        this.frameTelas = frameTelas;
        configurarTela();
    }

    private void configurarTela() {
        setLayout(new BorderLayout());
        setBackground(FUNDO);

        // Cabeçalho
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(FUNDO);
        cabecalho.setBorder(
            BorderFactory.createEmptyBorder(25, 40, 15, 40)
        );

        JLabel titulo = new JLabel("DECOLAÊ");
        titulo.setForeground(BRANCO);
        titulo.setFont(new Font(FONTE, Font.BOLD, 32));

        JLabel subtitulo = new JLabel("PAINEL DO MEMBRO DA TRIPULAÇÃO");
        subtitulo.setForeground(CIANO);
        subtitulo.setFont(new Font(FONTE, Font.BOLD, 12));

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(subtitulo, BorderLayout.EAST);

        JPanel linha = new JPanel();
        linha.setBackground(CIANO);
        linha.setPreferredSize(new Dimension(0, 2));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(FUNDO);
        topo.add(cabecalho, BorderLayout.CENTER);
        topo.add(linha, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);

        // Área central
        JPanel conteudo = new JPanel(new GridBagLayout());
        conteudo.setBackground(FUNDO);

        JPanel opcoes = new JPanel(new GridLayout(4, 1, 0, 18));
        opcoes.setBackground(FUNDO);
        opcoes.setPreferredSize(new Dimension(430, 350));

        JLabel mensagem = new JLabel(
            "Bem-vindo!",
            SwingConstants.CENTER
        );
        mensagem.setFont(new Font(FONTE, Font.BOLD, 30));
        mensagem.setForeground(BRANCO);

        JLabel descricao = new JLabel(
            "O que deseja fazer?",
            SwingConstants.CENTER
        );
        descricao.setFont(new Font(FONTE, Font.PLAIN, 15));
        descricao.setForeground(CINZA);

        JButton voosButton = criarBotao("Consultar voos escalados");
        JButton usuariosButton = criarBotao("Navegar como cliente");

        usuariosButton.addActionListener(e ->
            frameTelas.abrirCliente("menuMembroPanel")
        );

        opcoes.add(mensagem);
        opcoes.add(descricao);
        opcoes.add(voosButton);
        opcoes.add(usuariosButton);

        conteudo.add(opcoes);
        add(conteudo, BorderLayout.CENTER);

        // Rodapé
        JButton sairBtn = new JButton("Sair da conta");
        sairBtn.setFont(new Font(FONTE, Font.PLAIN, 13));
        sairBtn.setForeground(CINZA);
        sairBtn.setBackground(FUNDO);
        sairBtn.setBorderPainted(false);
        sairBtn.setFocusPainted(false);
        sairBtn.setContentAreaFilled(false);
        sairBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        sairBtn.addActionListener(e ->
            frameTelas.trocarTela("loginPanel")
        );

        JPanel rodape = new JPanel();
        rodape.setBackground(FUNDO);
        rodape.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 20, 10)
        );
        rodape.add(sairBtn);

        add(rodape, BorderLayout.SOUTH);
    }

    // Botão arredondado com efeito hover
    private class BotaoArredondado extends JButton {

        private Color corNormal;
        private Color corHover = new Color(30, 160, 220);

        public BotaoArredondado(String texto) {
            super(texto);

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);

            addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    setBackground(corHover);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    setBackground(corNormal);
                }
            });
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 =
                (java.awt.Graphics2D) g.create();

            g2.setRenderingHint(
                java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());
            g2.fillRoundRect(
                0, 0, getWidth(), getHeight(), 15, 15
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private JButton criarBotao(String texto) {
        BotaoArredondado botao = new BotaoArredondado(texto);

        botao.setPreferredSize(new Dimension(430, 65));
        botao.setBackground(AZUL);
        botao.corNormal = AZUL;
        botao.setForeground(BRANCO);
        botao.setFont(new Font(FONTE, Font.BOLD, 16));
        botao.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return botao;
    }
}
