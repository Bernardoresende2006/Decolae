
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

public class MenuGerentePanel extends JPanel {

    private FrameTelas frameTelas;

    // ===== CORES =====
    private final Color FUNDO = new Color(11, 18, 32);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);

    // ===== FONTES =====
    private final String FONTE = "SansSerif";

    public MenuGerentePanel(FrameTelas frameTelas) {
        this.frameTelas = frameTelas;
        configurarTela();
    }

    // ===== BOTÃO ARREDONDADO =====
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
                0, 0, getWidth(), getHeight(),
                15, 15
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private void configurarTela() {

        setLayout(new BorderLayout());
        setBackground(FUNDO);

        // ===== CABEÇALHO =====
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(FUNDO);
        cabecalho.setBorder(
            BorderFactory.createEmptyBorder(25, 40, 15, 40)
        );

        JLabel logo = new JLabel("DECOLAÊ");
        logo.setFont(new Font(FONTE, Font.BOLD, 32));
        logo.setForeground(BRANCO);

        JLabel subtitulo = new JLabel("PAINEL DO GERENTE");
        subtitulo.setFont(new Font(FONTE, Font.BOLD, 12));
        subtitulo.setForeground(CIANO);

        cabecalho.add(logo, BorderLayout.WEST);
        cabecalho.add(subtitulo, BorderLayout.EAST);

        JPanel linha = new JPanel();
        linha.setBackground(CIANO);
        linha.setPreferredSize(new Dimension(0, 2));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(FUNDO);
        topo.add(cabecalho, BorderLayout.CENTER);
        topo.add(linha, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);

        // ===== CONTEÚDO CENTRAL =====
        JPanel conteudo = new JPanel(new GridBagLayout());
        conteudo.setBackground(FUNDO);

        JPanel opcoes = new JPanel(new GridLayout(6, 1, 0, 15));
        opcoes.setBackground(FUNDO);
        opcoes.setPreferredSize(new Dimension(430, 440));

        JLabel mensagem = new JLabel(
            "Bem-vindo!",
            SwingConstants.CENTER
        );
        mensagem.setFont(new Font(FONTE, Font.BOLD, 30));
        mensagem.setForeground(BRANCO);

        JLabel descricao = new JLabel(
            "O que deseja?",
            SwingConstants.CENTER
        );
        descricao.setFont(new Font(FONTE, Font.PLAIN, 15));
        descricao.setForeground(CINZA);

        JButton funcionariosBtn =
            criarBotao("Gerenciar funcionários");

        JButton trajetoBtn =
            criarBotao("Gerenciar trajetos");

        JButton aeronavesBtn =
            criarBotao("Gerenciar aeronaves");

        JButton navegarBtn =
            criarBotao("Navegar como cliente");

        navegarBtn.addActionListener(e -> {
            frameTelas.abrirCliente("menuGerentePanel");
        });

        opcoes.add(mensagem);
        opcoes.add(descricao);
        opcoes.add(funcionariosBtn);
        opcoes.add(trajetoBtn);
        opcoes.add(aeronavesBtn);
        opcoes.add(navegarBtn);

        conteudo.add(opcoes);

        add(conteudo, BorderLayout.CENTER);

        // ===== RODAPÉ =====
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

    // ===== CRIAÇÃO DOS BOTÕES =====
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
