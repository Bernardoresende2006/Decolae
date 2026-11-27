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

import com.formdev.flatlaf.FlatClientProperties;
import control.VooControl;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Aeronave;
import model.Funcionario;
import model.Trajeto;
import model.Voo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public class AdministrarVoosPanel extends JPanel {

    private FrameTelas frameTelas;

    // CORES
    private final Color FUNDO = new Color(11, 18, 32);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);
    private final Color COMPONENTE = new Color(30, 41, 59);
    private JButton limparBtn;
    private int numVooSelecionado = -1;  
    // COMPONENTES
    private VooControl control;

    private JTable tabelaVoos;

    private JComboBox<Trajeto> trajetoCombo;
    private JComboBox<Aeronave> aeronaveCombo;
    private JComboBox<Funcionario> pilotoCombo;
    private JComboBox<Funcionario> copilotoCombo;
    private JComboBox<Funcionario> comissario1Combo;
    private JComboBox<Funcionario> comissario2Combo;

    private JTextField partidaDataTxt;
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

        control = new VooControl();

        trajetoCombo = criarCombo();
        aeronaveCombo = criarCombo();
        pilotoCombo = criarCombo();
        copilotoCombo = criarCombo();
        comissario1Combo = criarCombo();
        comissario2Combo = criarCombo();

        numeroVooTxt = criarCampo("Número do voo");
        partidaDataTxt = criarCampo("DD/MM/AAAA");

        voltarBtn = criarBotaoSecundario("Voltar");
        cadastrarBtn = criarBotaoPrincipal("Cadastrar voo");
        editarBtn = criarBotaoSecundario("Editar voo");
        excluirBtn = criarBotaoSecundario("Excluir voo");
        limparBtn = criarBotaoSecundario("Limpar");
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

        voltarBtn.addActionListener(e ->
            frameTelas.trocarTela("menuAdmPanel")
        );

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(FUNDO);
        rodape.setBorder(new EmptyBorder(5, 35, 15, 35));
        rodape.add(voltarBtn, BorderLayout.WEST);

        add(rodape, BorderLayout.SOUTH);

        cadastrarBtn.addActionListener(e -> cadastrarVoo());
        editarBtn.addActionListener(e -> editarVoo());
        excluirBtn.addActionListener(e -> excluirVoo());
        limparBtn.addActionListener(e -> limparFormulario());

        tabelaVoos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherFormulario();
        });
    }

    private JPanel criarPainelListagem() {

        JPanel painel = criarPainelBase();
        painel.setLayout(new BorderLayout(0, 12));

        JLabel titulo = criarTitulo("Voos cadastrados");
        painel.add(titulo, BorderLayout.NORTH);

        // A tabela é preenchida por atualizarTabela()
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

        formulario.add(criarCampoComTitulo("Número do voo", numeroVooTxt));
        formulario.add(criarCampoComTitulo("Trajeto", trajetoCombo));
        formulario.add(criarCampoComTitulo("Aeronave", aeronaveCombo));
        formulario.add(criarCampoComTitulo("Data", partidaDataTxt));
        formulario.add(criarCampoComTitulo("Piloto", pilotoCombo));
        formulario.add(criarCampoComTitulo("Copiloto", copilotoCombo));
        formulario.add(criarCampoComTitulo("Comissário 1", comissario1Combo));
        formulario.add(criarCampoComTitulo("Comissário 2", comissario2Combo));

        painel.add(formulario, BorderLayout.CENTER);

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );
        
        botoes.setBackground(FUNDO);
        botoes.add(limparBtn);
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

    private <T> JComboBox<T> criarCombo() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setBackground(COMPONENTE);
        combo.setForeground(BRANCO);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(200, 36));
        return combo;
    }

    private <T> void carregarCombo(JComboBox<T> combo, String placeholder, List<T> itens) {
        combo.removeAllItems();
        combo.addItem(null);                 // posição 0 = "nada selecionado"
        for (T item : itens) combo.addItem(item);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) setText(placeholder);   // mostra o texto no lugar do null
                return this;
            }
        });

        combo.setSelectedIndex(0);
    }

    private JTextField criarCampo(String placeholder) {

        JTextField campo = new JTextField();
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
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

    public void carregarDados() {
        carregarCombos();
        atualizarTabela();
    }

    private void carregarCombos() {
        carregarCombo(trajetoCombo, "Selecione um trajeto", control.todosTrajetos());
        carregarCombo(aeronaveCombo, "Selecione uma aeronave", control.todasAeronaves());
        carregarCombo(pilotoCombo, "Selecione o piloto", control.pilotos());
        carregarCombo(copilotoCombo, "Selecione o copiloto", control.copilotos());

        List<Funcionario> comissarios = control.comissarios();
        carregarCombo(comissario1Combo, "Selecione o comissário", comissarios);
        carregarCombo(comissario2Combo, "Selecione o comissário", comissarios);
    }

    private void atualizarTabela() {
        String[] colunas = {"Nº Voo", "Origem", "Destino", "Data", "Partida", "Chegada", "Aeronave"};
        DateTimeFormatter fData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Voo v : control.todosVoos()) {
            modelo.addRow(new Object[]{
                v.getNumVoo(),
                v.getOrigem(),
                v.getDestino(),
                v.getData().format(fData),
                v.getHoraPartida(),
                v.getHoraChegada(),
                v.getIdAeronave()
            });
        }

        tabelaVoos.setModel(modelo);
    }

    private Voo lerFormulario() {

        int numVoo;
        try {
            numVoo = Integer.parseInt(numeroVooTxt.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número do voo inválido. Use apenas números.");
            return null;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(partidaDataTxt.getText().trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa.");
            return null;
        }

        Trajeto trajeto = (Trajeto) trajetoCombo.getSelectedItem();
        Aeronave aeronave = (Aeronave) aeronaveCombo.getSelectedItem();
        Funcionario piloto = (Funcionario) pilotoCombo.getSelectedItem();
        Funcionario copiloto = (Funcionario) copilotoCombo.getSelectedItem();
        Funcionario com1 = (Funcionario) comissario1Combo.getSelectedItem();
        Funcionario com2 = (Funcionario) comissario2Combo.getSelectedItem();

        if (trajeto == null || aeronave == null || piloto == null
                || copiloto == null || com1 == null || com2 == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione trajeto, aeronave, piloto, copiloto e os dois comissários.");
            return null;
        }

        Voo voo = new Voo();
        voo.setNumVoo(numVoo);
        voo.setData(data);
        voo.setOrigem(trajeto.getOrigem());
        voo.setDestino(trajeto.getDestino());
        voo.setHoraPartida(trajeto.getHoraPartida());
        voo.setHoraChegada(trajeto.getHoraChegada());
        voo.setIdAeronave(aeronave.getIdAeronave());
        voo.setPilotoCpf(piloto.getCpf());
        voo.setCopilotoCpf(copiloto.getCpf());
        voo.setComissario1Cpf(com1.getCpf());
        voo.setComissario2Cpf(com2.getCpf());

        if (frameTelas.getUsuario() != null) {
            voo.setAdmCpf(frameTelas.getUsuario().getCpf());
        }
        return voo;
    }

    private void cadastrarVoo() {
        if (numVooSelecionado >= 0) {
            JOptionPane.showMessageDialog(this,
                    "Há um voo selecionado. Clique em Limpar para cadastrar um novo.");
            return;
        }

        Voo voo = lerFormulario();
        if (voo == null) return;

        String resposta = control.cadastrarVoo(voo);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Voo cadastrado.")) {
            limparFormulario();
            atualizarTabela();
        }
    }

    private void editarVoo() {
        if (numVooSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na tabela para editar.");
            return;
        }

        Voo voo = lerFormulario();
        if (voo == null) return;

        voo.setNumVoo(numVooSelecionado);   // o número do voo não muda na edição

        String resposta = control.atualizarVoo(voo);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Voo atualizado.")) {
            int num = numVooSelecionado;
            atualizarTabela();
            selecionarLinha(num);
        }
    }

    private void excluirVoo() {
        if (numVooSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na tabela para excluir.");
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this,
                "Excluir o voo " + numVooSelecionado + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

        if (opcao != JOptionPane.YES_OPTION) return;

        String resposta = control.excluirVoo(numVooSelecionado);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Voo excluído.")) {
            limparFormulario();
            atualizarTabela();
        }
    }

    private void preencherFormulario() {
        int linha = tabelaVoos.getSelectedRow();
        if (linha < 0) return;

        int numVoo = (int) tabelaVoos.getValueAt(linha, 0);
        Voo v = control.buscarVoo(numVoo);
        if (v == null) return;

        numVooSelecionado = numVoo;

        numeroVooTxt.setText(String.valueOf(numVoo));
        numeroVooTxt.setEnabled(false);   // o número é a chave: não se altera
        partidaDataTxt.setText(v.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        selecionarCombo(trajetoCombo, t ->
                t.getOrigem().equals(v.getOrigem())
                && t.getDestino().equals(v.getDestino())
                && t.getHoraPartida().equals(v.getHoraPartida())
                && t.getHoraChegada().equals(v.getHoraChegada()));

        selecionarCombo(aeronaveCombo, a -> a.getIdAeronave() == v.getIdAeronave());
        selecionarCombo(pilotoCombo, f -> f.getCpf().equals(v.getPilotoCpf()));
        selecionarCombo(copilotoCombo, f -> f.getCpf().equals(v.getCopilotoCpf()));
        selecionarCombo(comissario1Combo, f -> f.getCpf().equals(v.getComissario1Cpf()));
        selecionarCombo(comissario2Combo, f -> f.getCpf().equals(v.getComissario2Cpf()));
    }
    
    private void selecionarLinha(int numVoo) {
        for (int i = 0; i < tabelaVoos.getRowCount(); i++) {
            if ((int) tabelaVoos.getValueAt(i, 0) == numVoo) {
                tabelaVoos.setRowSelectionInterval(i, i);   // dispara preencherFormulario()
                return;
            }
        }
    }

    private <T> void selecionarCombo(JComboBox<T> combo, Predicate<T> criterio) {
        // começa em 1: a posição 0 é o placeholder (null)
        for (int i = 1; i < combo.getItemCount(); i++) {
            if (criterio.test(combo.getItemAt(i))) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(0);
    }

    private void limparFormulario() {
        numVooSelecionado = -1;
        tabelaVoos.clearSelection();

        numeroVooTxt.setEnabled(true);
        numeroVooTxt.setText("");
        partidaDataTxt.setText("");

        trajetoCombo.setSelectedIndex(0);
        aeronaveCombo.setSelectedIndex(0);
        pilotoCombo.setSelectedIndex(0);
        copilotoCombo.setSelectedIndex(0);
        comissario1Combo.setSelectedIndex(0);
        comissario2Combo.setSelectedIndex(0);
    }

}