
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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

    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel mensagem;
    private JLabel descricao;

    private JButton voosButton;
    private JButton usuariosButton;
    private JButton sairBtn;

    private JPanel opcoes;

    public MenuMembroPanel(FrameTelas frameTelas) {
        this.frameTelas = frameTelas;

        inicializacaoVariaveis();
        minhasConfiguracoes();
        configurarTela();
        estilizarComponentes();
    }

    private void inicializacaoVariaveis() {

        titulo = new JLabel("DECOLAÊ");
        subtitulo = new JLabel("PAINEL DO MEMBRO DA TRIPULAÇÃO");

        mensagem = new JLabel(
            "Bem-vindo!",
            SwingConstants.CENTER
        );

        descricao = new JLabel(
            "O que deseja fazer?",
            SwingConstants.CENTER
        );

        voosButton = criarBotao("Consultar voos escalados");
        usuariosButton = criarBotao("Navegar como cliente");

        sairBtn = new JButton("Sair da conta");

        opcoes = new JPanel(new GridLayout(4, 1, 0, 18));
    }

    private void minhasConfiguracoes() {

        setLayout(new BorderLayout());
        setBackground(FUNDO);
    }

    private void estilizarComponentes() {

        titulo.setForeground(BRANCO);
        titulo.setFont(new Font(FONTE, Font.BOLD, 32));

        subtitulo.setForeground(CIANO);
        subtitulo.setFont(new Font(FONTE, Font.BOLD, 12));

        mensagem.setFont(new Font(FONTE, Font.BOLD, 30));
        mensagem.setForeground(BRANCO);

        descricao.setFont(new Font(FONTE, Font.PLAIN, 15));
        descricao.setForeground(CINZA);

        estilizarBotaoPrincipal(voosButton);
        estilizarBotaoPrincipal(usuariosButton);

        sairBtn.setFont(new Font(FONTE, Font.PLAIN, 13));
        sairBtn.setForeground(CINZA);
        sairBtn.setBackground(FUNDO);
        sairBtn.setBorderPainted(false);
        sairBtn.setFocusPainted(false);
        sairBtn.setContentAreaFilled(false);
        sairBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void configurarTela() {

        // CABEÇALHO
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(FUNDO);
        cabecalho.setBorder(
            BorderFactory.createEmptyBorder(25, 40, 15, 40)
        );

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

        // CONTEÚDO CENTRAL
        JPanel conteudo = new JPanel(new GridBagLayout());
        conteudo.setBackground(FUNDO);

        opcoes.setBackground(FUNDO);
        opcoes.setPreferredSize(new Dimension(430, 350));

        usuariosButton.addActionListener(e ->
            frameTelas.abrirCliente("menuMembroPanel")
        );

        opcoes.add(mensagem);
        opcoes.add(descricao);
        opcoes.add(voosButton);
        opcoes.add(usuariosButton);

        conteudo.add(opcoes);

        add(conteudo, BorderLayout.CENTER);

        // RODAPÉ
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

    private void estilizarBotaoPrincipal(JButton botao) {

        botao.setPreferredSize(new Dimension(430, 65));
        botao.setBackground(AZUL);

        if (botao instanceof BotaoArredondado) {
            ((BotaoArredondado) botao).corNormal = AZUL;
        }

        botao.setForeground(BRANCO);
        botao.setFont(new Font(FONTE, Font.BOLD, 16));
        botao.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JButton criarBotao(String texto) {
        return new BotaoArredondado(texto);
    }

    private class BotaoArredondado extends JButton {

        private Color corNormal;
        private final Color corHover = new Color(30, 160, 220);

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
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());

            g2.fillRoundRect(
                0, 0,
                getWidth(), getHeight(),
                15, 15
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}
