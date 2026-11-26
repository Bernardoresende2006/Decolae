package view;

import com.formdev.flatlaf.FlatClientProperties;
import control.AeronaveControl;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Aeronave;

public class AeronavePanel extends JPanel {

    private FrameTelas frameTelas;
    private AeronaveControl control;
    private List<Aeronave> listaAeronaves;
    private Aeronave aeronaveSelecionada;

    // ===== CORES =====
    private final Color FUNDO = new Color(11, 18, 32);
    private final Color CARD = new Color(18, 28, 48);
    private final Color CARD_CLARO = new Color(30, 41, 59);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);

    // ===== FONTES =====
    private final String FONTE = "SansSerif";

    // ===== COMPONENTES DE ESTRUTURA =====
    private JLabel logo;
    private JLabel subtitulo;
    private JPanel Fundo;
    private JPanel PainelLista;
    private JPanel PainelDetalhes;

    // ===== COMPONENTES DA LISTA =====
    private JLabel Titulo;
    private JLabel lblLegenda;
    private DefaultTableModel modeloTabela;
    private JTable jTable1;
    private JScrollPane jScrollPane1;
    private JButton NovaAeronave;
    private JButton Voltar;

    // ===== COMPONENTES DE DETALHES =====
    private JLabel lblTituloDetalhes;
    private JLabel lblAvisoLeitura;
    private JLabel lblId;
    private JLabel lblModelo;
    private JLabel lblLocalizacao;
    private JLabel lblAssentos;
    private JLabel lblGerenteCpf;
    private JTextField txtId;
    private JTextField txtModelo;
    private JTextField txtLocalizacao;
    private JTextField txtAssentos;
    private JTextField txtGerenteCpf;
    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnVoltarDetalhes;

    public AeronavePanel(FrameTelas frameTelas) {
        this.frameTelas = frameTelas;

        inicializacaoVariaveis();
        minhasConfiguracoes();
        configurarTela();
        estilizarComponentes();
        atualizarTabela();
    }

    private void inicializacaoVariaveis() {
        control = new AeronaveControl();

        logo = new JLabel("DECOLAÊ");
        subtitulo = new JLabel("PAINEL DO GERENTE");

        Fundo = new JPanel(new CardLayout());
        PainelLista = new JPanel(new BorderLayout(0, 15));
        PainelDetalhes = new JPanel(new BorderLayout(0, 15));

        Titulo = new JLabel("GERENCIAMENTO DE AERONAVES");
        lblLegenda = new JLabel("Clique em uma aeronave para ver detalhes (linhas claras são de outros gerentes)");

        modeloTabela = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Modelo", "Localização", "Assentos"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1 = new JTable(modeloTabela);
        jScrollPane1 = new JScrollPane(jTable1);
        NovaAeronave = new JButton("Nova Aeronave");
        Voltar = new JButton("Voltar ao menu");

        lblTituloDetalhes = new JLabel("DETALHES DA AERONAVE");
        lblAvisoLeitura = new JLabel("Aeronave cadastrada por outro gerente (somente visualização)");

        lblId = new JLabel("ID da Aeronave:", SwingConstants.RIGHT);
        lblModelo = new JLabel("Modelo:", SwingConstants.RIGHT);
        lblLocalizacao = new JLabel("Localização:", SwingConstants.RIGHT);
        lblAssentos = new JLabel("Número de Assentos (1-100):", SwingConstants.RIGHT);
        lblGerenteCpf = new JLabel("CPF do Gerente Responsável:", SwingConstants.RIGHT);

        txtId = new JTextField(25);
        txtModelo = new JTextField(25);
        txtLocalizacao = new JTextField(25);
        txtAssentos = new JTextField(25);
        txtGerenteCpf = new JTextField(25);

        btnVoltarDetalhes = new JButton("Voltar");
        btnCadastrar = new JButton("Cadastrar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
    }

    private void minhasConfiguracoes(){
        
        setLayout(new BorderLayout());
        setBackground(FUNDO);
        setPreferredSize(new Dimension(1280, 720));

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setFillsViewportHeight(true);
        jTable1.getTableHeader().setReorderingAllowed(false);

        txtGerenteCpf.setEditable(false);
        lblAvisoLeitura.setVisible(false);
    }

    private void configurarTela(){
        
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(FUNDO);
        cabecalho.setBorder(BorderFactory.createEmptyBorder(25, 40, 15, 40));
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

        JPanel topoLista = new JPanel(new BorderLayout());
        topoLista.setOpaque(false);
        topoLista.add(Titulo, BorderLayout.NORTH);
        topoLista.add(lblLegenda, BorderLayout.SOUTH);
        PainelLista.add(topoLista, BorderLayout.NORTH);

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row >= 0 && listaAeronaves != null && row < listaAeronaves.size()) {
                    abrirDetalhesEdicao(listaAeronaves.get(row));
                }
            }
        });
        PainelLista.add(jScrollPane1, BorderLayout.CENTER);

        JPanel barraBotoesLista = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        barraBotoesLista.setOpaque(false);
        NovaAeronave.addActionListener(e -> abrirDetalhesCadastro());
        barraBotoesLista.add(NovaAeronave);
        PainelLista.add(barraBotoesLista, BorderLayout.SOUTH);

        JPanel topoDetalhes = new JPanel(new BorderLayout());
        topoDetalhes.setOpaque(false);
        topoDetalhes.add(lblTituloDetalhes, BorderLayout.NORTH);
        topoDetalhes.add(lblAvisoLeitura, BorderLayout.SOUTH);
        PainelDetalhes.add(topoDetalhes, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        adicionarLinhaForm(form, gbc, 0, lblId, txtId);
        adicionarLinhaForm(form, gbc, 1, lblModelo, txtModelo);
        adicionarLinhaForm(form, gbc, 2, lblLocalizacao, txtLocalizacao);
        adicionarLinhaForm(form, gbc, 3, lblAssentos, txtAssentos);
        adicionarLinhaForm(form, gbc, 4, lblGerenteCpf, txtGerenteCpf);
        PainelDetalhes.add(form, BorderLayout.CENTER);

        JPanel botoesDetalhes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        botoesDetalhes.setOpaque(false);

        btnVoltarDetalhes.addActionListener(e -> mostrarCartao("lista"));
        btnCadastrar.addActionListener(e -> acaoCadastrar());
        btnAtualizar.addActionListener(e -> acaoAtualizar());
        btnExcluir.addActionListener(e -> acaoExcluir());

        botoesDetalhes.add(btnVoltarDetalhes);
        botoesDetalhes.add(btnExcluir);
        botoesDetalhes.add(btnAtualizar);
        botoesDetalhes.add(btnCadastrar);
        PainelDetalhes.add(botoesDetalhes, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(FUNDO);

        Fundo.add(PainelLista, "lista");
        Fundo.add(PainelDetalhes, "detalhes");
        centro.add(Fundo);
        add(centro, BorderLayout.CENTER);

        Voltar.addActionListener(e -> frameTelas.trocarTela("menuGerentePanel"));

        JPanel rodape = new JPanel();
        rodape.setBackground(FUNDO);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        rodape.add(Voltar);
        add(rodape, BorderLayout.SOUTH);
    }

    private void estilizarComponentes() {
        // Cabeçalho
        logo.setFont(new Font(FONTE, Font.BOLD, 32));
        logo.setForeground(BRANCO);

        subtitulo.setFont(new Font(FONTE, Font.BOLD, 12));
        subtitulo.setForeground(CIANO);

        // Cartão central arredondado via FlatLaf
        Fundo.setBackground(CARD);
        Fundo.setPreferredSize(new Dimension(800, 520));
        Fundo.putClientProperty(FlatClientProperties.STYLE,
            "arc: 20; border: 1,1,1,1,#1e293b"
        );

        PainelLista.setOpaque(false);
        PainelLista.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        PainelDetalhes.setOpaque(false);
        PainelDetalhes.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Títulos e legendas
        Titulo.setFont(new Font(FONTE, Font.BOLD, 20));
        Titulo.setForeground(BRANCO);

        lblLegenda.setFont(new Font(FONTE, Font.PLAIN, 12));
        lblLegenda.setForeground(CINZA);

        lblTituloDetalhes.setFont(new Font(FONTE, Font.BOLD, 20));
        lblTituloDetalhes.setForeground(BRANCO);

        lblAvisoLeitura.setFont(new Font(FONTE, Font.ITALIC, 12));
        lblAvisoLeitura.setForeground(CIANO);

        // Tabela e ScrollPane estilizados via FlatLaf
        jScrollPane1.setBackground(CARD);
        jScrollPane1.getViewport().setBackground(CARD);
        jScrollPane1.putClientProperty(FlatClientProperties.STYLE,
            "arc: 12; border: 1,1,1,1,#1e293b"
        );
        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
            "track: #121c30; thumb: #1e293b; hoverThumbColor: #38bdf8; width: 8; trackArc: 999; thumbArc: 999"
        );

        jTable1.setBackground(CARD);
        jTable1.setRowHeight(34);
        jTable1.setFont(new Font(FONTE, Font.PLAIN, 14));
        jTable1.setShowGrid(false);
        jTable1.setIntercellSpacing(new Dimension(0, 1));
        jTable1.getTableHeader().setFont(new Font(FONTE, Font.BOLD, 13));
        jTable1.getTableHeader().setBackground(FUNDO);
        jTable1.getTableHeader().setForeground(CIANO);
        jTable1.getTableHeader().putClientProperty(FlatClientProperties.STYLE,
            "bottomSeparatorColor: #1e293b; separatorColor: #1e293b"
        );

        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                setHorizontalAlignment(SwingConstants.CENTER);

                if (isSelected) {
                    c.setBackground(AZUL);
                    c.setForeground(BRANCO);
                } else if (listaAeronaves != null && row < listaAeronaves.size()) {
                    Aeronave a = listaAeronaves.get(row);
                    if (isGerenteDono(a)) {
                        c.setBackground(CARD);
                        c.setForeground(BRANCO);
                    } else {
                        c.setBackground(CARD_CLARO);
                        c.setForeground(CINZA);
                    }
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
        
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(210);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(350);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);

        JLabel[] labelsForm = {lblId, lblModelo, lblLocalizacao, lblAssentos, lblGerenteCpf};
        for (JLabel lbl : labelsForm) {
            lbl.setFont(new Font(FONTE, Font.BOLD, 13));
            lbl.setForeground(CINZA);
        }

        JTextField[] camposForm = {txtId, txtModelo, txtLocalizacao, txtAssentos, txtGerenteCpf};
        for (JTextField campo : camposForm) {
            campo.setFont(new Font(FONTE, Font.PLAIN, 14));
            campo.setBackground(FUNDO);
            campo.setForeground(BRANCO);
            campo.setCaretColor(BRANCO);
            campo.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; margin: 6,10,6,10; borderColor: #1e293b; focusedBorderColor: #38bdf8; focusWidth: 1"
            );
        }

        txtId.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: 106");
        txtModelo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: Boeing 737-800");
        txtLocalizacao.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ex: Aeroporto de Guarulhos - SP");
        txtAssentos.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Número entre 1 e 100");

        estilizarBotao(NovaAeronave, "#087ebd", "#1ea0dc", 180, 42);
        estilizarBotao(btnVoltarDetalhes, "#1e293b", "#334155", 120, 40);
        estilizarBotao(btnCadastrar, "#087ebd", "#1ea0dc", 140, 40);
        estilizarBotao(btnAtualizar, "#087ebd", "#1ea0dc", 140, 40);
        estilizarBotao(btnExcluir, "#b91c1c", "#dc2626", 130, 40);

        Voltar.setFont(new Font(FONTE, Font.PLAIN, 13));
        Voltar.setForeground(CINZA);
        Voltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Voltar.putClientProperty(FlatClientProperties.STYLE,
            "buttonType: borderless; hoverForeground: #ffffff; focusWidth: 0"
        );
    }

    private void estilizarBotao(JButton botao, String hexNormal, String hexHover, int largura, int altura) {
        botao.setPreferredSize(new Dimension(largura, altura));
        botao.setForeground(BRANCO);
        botao.setFont(new Font(FONTE, Font.BOLD, 14));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.putClientProperty(FlatClientProperties.STYLE,
            "arc: 15; borderWidth: 0; focusWidth: 0; " +
            "background: " + hexNormal + "; " +
            "hoverBackground: " + hexHover
        );
    }

    private void adicionarLinhaForm(JPanel painel, GridBagConstraints gbc, int linha, JLabel rotulo, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0.35;
        painel.add(rotulo, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        painel.add(campo, gbc);
    }

    private void mostrarCartao(String nomeCartao) {
        CardLayout cl = (CardLayout) Fundo.getLayout();
        cl.show(Fundo, nomeCartao);
    }

    private String getCpfGerenteLogado() {
        if (frameTelas != null && frameTelas.getUsuario() != null) {
            return frameTelas.getUsuario().getCpf();
        }
        return "";
    }

    private boolean isGerenteDono(Aeronave a) {
        if (a == null || a.getGerenteCpf() == null) return false;
        return a.getGerenteCpf().equals(getCpfGerenteLogado());
    }

    public void atualizarTabela() {
        listaAeronaves = control.todasAeronaves();
        modeloTabela.setRowCount(0);

        for (Aeronave a : listaAeronaves) {
            modeloTabela.addRow(new Object[]{
                a.getIdAeronave(),
                a.getModelo(),
                a.getLocalizacao(),
                a.getNumAssentos()
            });
        }
        mostrarCartao("lista");
    }

    private void abrirDetalhesCadastro() {
        aeronaveSelecionada = null;
        lblTituloDetalhes.setText("CADASTRAR NOVA AERONAVE");
        lblAvisoLeitura.setVisible(false);

        txtId.setText("");
        txtModelo.setText("");
        txtLocalizacao.setText("");
        txtAssentos.setText("");
        txtGerenteCpf.setText(getCpfGerenteLogado());

        txtId.setEditable(true);
        txtModelo.setEditable(true);
        txtLocalizacao.setEditable(true);
        txtAssentos.setEditable(true);

        btnCadastrar.setVisible(true);
        btnAtualizar.setVisible(false);
        btnExcluir.setVisible(false);

        mostrarCartao("detalhes");
    }

    private void abrirDetalhesEdicao(Aeronave a) {
        aeronaveSelecionada = a;
        boolean ehDono = isGerenteDono(a);

        lblTituloDetalhes.setText(ehDono ? "EDITAR AERONAVE" : "VISUALIZAR AERONAVE");
        lblAvisoLeitura.setVisible(!ehDono);

        txtId.setText(String.valueOf(a.getIdAeronave()));
        txtModelo.setText(a.getModelo());
        txtLocalizacao.setText(a.getLocalizacao());
        txtAssentos.setText(String.valueOf(a.getNumAssentos()));
        txtGerenteCpf.setText(a.getGerenteCpf());

        txtId.setEditable(false);
        txtModelo.setEditable(ehDono);
        txtLocalizacao.setEditable(ehDono);
        txtAssentos.setEditable(ehDono);

        btnCadastrar.setVisible(false);
        btnAtualizar.setVisible(ehDono);
        btnExcluir.setVisible(ehDono);

        mostrarCartao("detalhes");
    }

    private Aeronave montarObjetoFormulario() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            int assentos = Integer.parseInt(txtAssentos.getText().trim());
            String modelo = txtModelo.getText().trim();
            String localizacao = txtLocalizacao.getText().trim();
            String cpf = txtGerenteCpf.getText().trim();

            return new Aeronave(id, modelo, cpf, localizacao, assentos);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID e Número de Assentos devem ser números inteiros.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void acaoCadastrar() {
        Aeronave nova = montarObjetoFormulario();
        if (nova == null) return;

        String resposta = control.cadastrarAeronave(nova);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Cadastro realizado.")) {
            atualizarTabela();
        }
    }

    private void acaoAtualizar() {
        if (!isGerenteDono(aeronaveSelecionada)) {
            JOptionPane.showMessageDialog(this, "Você só pode alterar aeronaves cadastradas por você.");
            return;
        }

        Aeronave atualizada = montarObjetoFormulario();
        if (atualizada == null) return;

        atualizada.setGerenteCpf(aeronaveSelecionada.getGerenteCpf());

        String resposta = control.atualizarAeronave(atualizada);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Atualização dos dados realizada.")) {
            atualizarTabela();
        }
    }

    private void acaoExcluir() {
        if (!isGerenteDono(aeronaveSelecionada)) {
            JOptionPane.showMessageDialog(this, "Você só pode excluir aeronaves cadastradas por você.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir a aeronave " + aeronaveSelecionada.getModelo() + " (ID " + aeronaveSelecionada.getIdAeronave() + ")?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String resposta = control.excluirAeronave(aeronaveSelecionada);
            JOptionPane.showMessageDialog(this, resposta);

            if (resposta.equals("Exclusão concluída com sucesso.")) {
                atualizarTabela();
            }
        }
    }
}