
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdministrarVoosPanel extends JPanel {

    private FrameTelas frameTelas;

    // CORES
    private final Color FUNDO = new Color(11, 18, 32);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);
    private final Color COMPONENTE = new Color(30, 41, 59);

    // COMPONENTES
    private JTable tabelaVoos;

    private JComboBox<String> trajetoCombo;
    private JComboBox<String> aeronaveCombo;
    private JComboBox<String> pilotoCombo;
    private JComboBox<String> copilotoCombo;
    private JComboBox<String> comissario1Combo;
    private JComboBox<String> comissario2Combo;

    private JTextField partidaDataTxt;
    private JTextField partidaHoraTxt;
    private JTextField chegadaHoraTxt;
    private JTextField numeroVooTxt;

    private JButton voltarBtn;
    private JButton cadastrarBtn;
    private JButton editarBtn;
    private JButton excluirBtn;

    public AdministrarVoosPanel(FrameTelas frameTelas) {
        this.frameTelas = frameTelas;

        inicializacaoVariaveis();
        minhasConfiguracoes();
        configurarTela();
        estilizarComponentes();
    }

    private void inicializacaoVariaveis() {

        tabelaVoos = new JTable();

        trajetoCombo = criarCombo(
            "Selecione um trajeto",
            "São Paulo → Belo Horizonte",
            "Rio de Janeiro → Brasília",
            "Belo Horizonte → Salvador"
        );

        aeronaveCombo = criarCombo(
            "Selecione uma aeronave",
            "100", "101", "102"
        );

        pilotoCombo = criarCombo(
            "Selecione o piloto",
            "Piloto 01", "Piloto 02"
        );

        copilotoCombo = criarCombo(
            "Selecione o copiloto",
            "Copiloto 01", "Copiloto 02"
        );

        comissario1Combo = criarCombo(
            "Selecione o comissário",
            "Comissário 01", "Comissário 02"
        );

        comissario2Combo = criarCombo(
            "Selecione o comissário",
            "Comissário 01", "Comissário 02"
        );

        numeroVooTxt = criarCampo("Número do voo");
        partidaDataTxt = criarCampo("DD/MM/AAAA");
        partidaHoraTxt = criarCampo("HH:MM");
        chegadaHoraTxt = criarCampo("HH:MM");

        voltarBtn = criarBotaoSecundario("Voltar");
        cadastrarBtn = criarBotaoPrincipal("Cadastrar voo");
        editarBtn = criarBotaoSecundario("Editar voo");
        excluirBtn = criarBotaoSecundario("Excluir voo");
    }

    private void minhasConfiguracoes() {
        setLayout(new BorderLayout());
        setBackground(FUNDO);
        setPreferredSize(new Dimension(1280, 720));
    }

    private void estilizarComponentes() {

        tabelaVoos.setRowHeight(32);
        tabelaVoos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabelaVoos.setBackground(COMPONENTE);
        tabelaVoos.setForeground(BRANCO);
        tabelaVoos.setGridColor(new Color(51, 65, 85));
        tabelaVoos.setSelectionBackground(AZUL);
        tabelaVoos.setSelectionForeground(BRANCO);
        tabelaVoos.setShowVerticalLines(false);

        tabelaVoos.getTableHeader().setBackground(new Color(20, 35, 55));
        tabelaVoos.getTableHeader().setForeground(BRANCO);
        tabelaVoos.getTableHeader().setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );
        tabelaVoos.getTableHeader().setPreferredSize(
            new Dimension(0, 35)
        );
    }

    private void configurarTela() {

        // CABEÇALHO
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(FUNDO);
        cabecalho.setBorder(new EmptyBorder(20, 35, 15, 35));

        JLabel logo = new JLabel("DECOLAÊ");
        logo.setFont(new Font("SansSerif", Font.BOLD, 30));
        logo.setForeground(BRANCO);

        JLabel subtitulo = new JLabel("GERENCIAMENTO DE VOOS");
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
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

        // CONTEÚDO
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(new EmptyBorder(20, 35, 20, 35));

        conteudo.add(criarPainelListagem());
        conteudo.add(Box.createVerticalStrut(22));
        conteudo.add(criarPainelCadastro());

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);

        // BOTÃO VOLTAR
        voltarBtn.addActionListener(e ->
            frameTelas.trocarTela("menuAdmPanel")
        );

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(FUNDO);
        rodape.setBorder(new EmptyBorder(5, 35, 15, 35));
        rodape.add(voltarBtn, BorderLayout.WEST);

        add(rodape, BorderLayout.SOUTH);

        // AÇÕES PROVISÓRIAS
        cadastrarBtn.addActionListener(e ->
            mensagemProvisoria("Cadastrar voo")
        );

        editarBtn.addActionListener(e ->
            mensagemProvisoria("Editar voo")
        );

        excluirBtn.addActionListener(e ->
            mensagemProvisoria("Excluir voo")
        );
    }

    private JPanel criarPainelListagem() {

        JPanel painel = criarPainelBase();
        painel.setLayout(new BorderLayout(0, 12));

        JLabel titulo = criarTitulo("Voos cadastrados");
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {
            "Nº Voo", "Origem", "Destino",
            "Partida", "Chegada", "Aeronave"
        };

        Object[][] dados = {
            {"001", "São Paulo", "Belo Horizonte",
                "26/11/2026 08:00", "26/11/2026 09:10", "100"},
            {"002", "Rio de Janeiro", "Brasília",
                "26/11/2026 10:00", "26/11/2026 11:50", "101"},
            {"003", "Belo Horizonte", "Salvador",
                "27/11/2026 14:00", "27/11/2026 15:40", "102"}
        };

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVoos.setModel(modelo);

        JScrollPane tabelaScroll = new JScrollPane(tabelaVoos);
        tabelaScroll.setBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85))
        );
        tabelaScroll.getViewport().setBackground(COMPONENTE);

        painel.add(tabelaScroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        botoes.setBackground(FUNDO);

        botoes.add(editarBtn);
        botoes.add(excluirBtn);

        painel.add(botoes, BorderLayout.SOUTH);

        painel.setPreferredSize(new Dimension(1100, 245));

        return painel;
    }

    private JPanel criarPainelCadastro() {

        JPanel painel = criarPainelBase();
        painel.setLayout(new BorderLayout(0, 15));

        JLabel titulo = criarTitulo("Cadastrar novo voo");
        painel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(
            new GridLayout(0, 2, 25, 14)
        );
        formulario.setBackground(FUNDO);

        formulario.add(
            criarCampoComTitulo("Número do voo", numeroVooTxt)
        );

        formulario.add(
            criarCampoComTitulo("Trajeto", trajetoCombo)
        );

        formulario.add(
            criarCampoComTitulo("Aeronave", aeronaveCombo)
        );

        formulario.add(
            criarCampoComTitulo("Data", partidaDataTxt)
        );

        formulario.add(
            criarCampoComTitulo("Horário de partida", partidaHoraTxt)
        );

        formulario.add(
            criarCampoComTitulo("Horário de chegada", chegadaHoraTxt)
        );

        formulario.add(
            criarCampoComTitulo("Piloto", pilotoCombo)
        );

        formulario.add(
            criarCampoComTitulo("Copiloto", copilotoCombo)
        );

        formulario.add(
            criarCampoComTitulo("Comissário 1", comissario1Combo)
        );

        formulario.add(
            criarCampoComTitulo("Comissário 2", comissario2Combo)
        );

        painel.add(formulario, BorderLayout.CENTER);

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );
        botoes.setBackground(FUNDO);
        botoes.add(cadastrarBtn);

        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelBase() {
        JPanel painel = new JPanel();
        painel.setBackground(FUNDO);
        return painel;
    }

    private JLabel criarTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 21));
        titulo.setForeground(BRANCO);
        return titulo;
    }

    private JPanel criarCampoComTitulo(String titulo, JComponent campo) {

        JPanel painel = new JPanel(new BorderLayout(0, 7));
        painel.setBackground(FUNDO);

        JLabel label = new JLabel(titulo);
        label.setForeground(CINZA);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));

        painel.add(label, BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    private JComboBox<String> criarCombo(
        String padrao, String... opcoes
    ) {
        JComboBox<String> combo = new JComboBox<>();

        combo.addItem(padrao);

        for (String opcao : opcoes) {
            combo.addItem(opcao);
        }

        combo.setBackground(COMPONENTE);
        combo.setForeground(BRANCO);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(200, 36));

        return combo;
    }

    private JTextField criarCampo(String placeholder) {

        JTextField campo = new JTextField();
        campo.setText(placeholder);
        campo.setBackground(COMPONENTE);
        campo.setForeground(BRANCO);
        campo.setCaretColor(BRANCO);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        campo.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85)),
                new EmptyBorder(8, 10, 8, 10)
            )
        );

        return campo;
    }

    private JButton criarBotaoPrincipal(String texto) {

        JButton botao = new JButton(texto);
        botao.setBackground(AZUL);
        botao.setForeground(BRANCO);
        botao.setFont(new Font("SansSerif", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(180, 42));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return botao;
    }

    private JButton criarBotaoSecundario(String texto) {

        JButton botao = new JButton(texto);
        botao.setBackground(COMPONENTE);
        botao.setForeground(BRANCO);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setFocusPainted(false);

        botao.setBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85))
        );

        botao.setPreferredSize(new Dimension(120, 35));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return botao;
    }

    // MENSAGENS PROVISÓRIAS
    private void mensagemProvisoria(String acao) {

        JOptionPane.showMessageDialog(
            this,
            "A funcionalidade de " + acao.toLowerCase()
                + " será implementada posteriormente.",
            "Em desenvolvimento",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
