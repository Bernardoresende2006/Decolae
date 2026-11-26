/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rot-civ
 */
package view;

import com.formdev.flatlaf.FlatClientProperties;
import control.PessoaControl;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import model.Pessoa;

public class CadastroPanel extends JPanel {

    private FrameTelas frame;
    private PessoaControl control;

    private JPanel pnlCard;
    private JLabel lblTitulo;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8, jLabel9;
    private JTextField TXTnome;
    private JTextField TXTemail;
    private JTextField TXTcpf;
    private JTextField TXTendereco;
    private JComboBox<String> cmbSexo;
    private JTextField TXTdata;
    private JPasswordField TXTsenha;
    private JPasswordField TXTconfirmaSenha;
    private JButton btnVoltar;
    private JButton btnCadastrar;

    public CadastroPanel(FrameTelas frame) {
        this.frame = frame;
        this.control = new PessoaControl();
        inicializarComponentes();
        estilizarComponentes();
        limparCampos();
    }

    private void inicializarComponentes() {
        // Fundo escuro e GridBagLayout para centralizar o cartão automaticamente
        this.setBackground(new Color(11, 19, 30));
        this.setLayout(new GridBagLayout());

        pnlCard = new JPanel();
        pnlCard.setBackground(Color.WHITE);
        pnlCard.setPreferredSize(new Dimension(560, 520));
        pnlCard.setMinimumSize(new Dimension(560, 520));

        lblTitulo = new JLabel("CADASTRE-SE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(15, 23, 42));

        jLabel7 = new JLabel("Nome Completo:");
        jLabel3 = new JLabel("E-mail:");
        jLabel1 = new JLabel("CPF:");
        jLabel8 = new JLabel("Endereço:");
        jLabel2 = new JLabel("Sexo:");
        jLabel9 = new JLabel("Data de Nascimento:");
        jLabel5 = new JLabel("Senha:");
        jLabel4 = new JLabel("Confirme a senha:");

        Font fonteLabel = new Font("SansSerif", Font.BOLD, 12);
        Color corLabel = new Color(51, 65, 85);
        for (JLabel lbl : new JLabel[]{jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8, jLabel9}) {
            lbl.setFont(fonteLabel);
            lbl.setForeground(corLabel);
        }

        TXTnome = new JTextField();
        TXTemail = new JTextField();
        TXTcpf = new JTextField();
        TXTendereco = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"", "M", "F"});
        TXTdata = new JTextField();
        TXTsenha = new JPasswordField();
        TXTconfirmaSenha = new JPasswordField();

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(evt -> {
            limparCampos();
            frame.trocarTela("loginPanel");
        });

        btnCadastrar = new JButton("Criar conta");
        btnCadastrar.addActionListener(evt -> cadastrar());

        // Organização interna do cartão em duas colunas alinhadas
        GroupLayout cardLayout = new GroupLayout(pnlCard);
        pnlCard.setLayout(cardLayout);

        cardLayout.setHorizontalGroup(
            cardLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(cardLayout.createSequentialGroup()
                    .addGap(40)
                    .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(cardLayout.createSequentialGroup()
                            // Coluna da esquerda
                            .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7)
                                .addComponent(TXTnome, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addComponent(TXTcpf)
                                .addComponent(jLabel2)
                                .addComponent(cmbSexo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addComponent(TXTsenha)
                                .addComponent(btnVoltar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(30)
                            // Coluna da direita
                            .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(TXTemail, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addComponent(TXTendereco)
                                .addComponent(jLabel9)
                                .addComponent(TXTdata)
                                .addComponent(jLabel4)
                                .addComponent(TXTconfirmaSenha)
                                .addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGap(40))
        );

        cardLayout.setVerticalGroup(
            cardLayout.createSequentialGroup()
                .addGap(30)
                .addComponent(lblTitulo)
                .addGap(25)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(TXTnome, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TXTemail, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addGap(16)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(TXTcpf, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TXTendereco, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addGap(16)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSexo, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TXTdata, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addGap(16)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(TXTsenha, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TXTconfirmaSenha, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addGap(30)
                .addGroup(cardLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addGap(30)
        );

        this.add(pnlCard);
    }

    private void estilizarComponentes() {
        pnlCard.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #FFFFFF;");

        String estiloCampo = "arc: 12; margin: 5,10,5,10;";
        TXTnome.putClientProperty(FlatClientProperties.STYLE, estiloCampo);
        TXTnome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Digite seu nome completo");

        TXTemail.putClientProperty(FlatClientProperties.STYLE, estiloCampo);
        TXTemail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "exemplo@email.com");

        TXTcpf.putClientProperty(FlatClientProperties.STYLE, estiloCampo);
        TXTcpf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "000.000.000-00");

        TXTendereco.putClientProperty(FlatClientProperties.STYLE, estiloCampo);
        TXTendereco.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Rua, número, bairro");

        TXTdata.putClientProperty(FlatClientProperties.STYLE, estiloCampo);
        TXTdata.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/mm/aaaa");

        cmbSexo.putClientProperty(FlatClientProperties.STYLE, "arc: 12;");

        String estiloSenha = "arc: 12; margin: 5,10,5,10; showRevealButton: true;";
        TXTsenha.putClientProperty(FlatClientProperties.STYLE, estiloSenha);
        TXTsenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mínimo 8 caracteres");

        TXTconfirmaSenha.putClientProperty(FlatClientProperties.STYLE, estiloSenha);
        TXTconfirmaSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Repita a senha");

        btnCadastrar.putClientProperty(FlatClientProperties.STYLE,
            "arc: 12; background: #0077CC; foreground: #FFFFFF; font: bold; borderWidth: 0;");

        btnVoltar.putClientProperty(FlatClientProperties.STYLE,
            "arc: 12; background: #D92323; foreground: #FFFFFF; font: bold; borderWidth: 0;");
    }

    private void cadastrar() {
        String nome = TXTnome.getText();
        String senha = new String(TXTsenha.getPassword());
        String sexo = cmbSexo.getSelectedItem().toString();
        String confirmarSenha = new String(TXTconfirmaSenha.getPassword());
        String email = TXTemail.getText();
        String cpf = TXTcpf.getText();
        String endereco = TXTendereco.getText();

        Date data = null;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataLocal = LocalDate.parse(TXTdata.getText().trim(), formato);
            data = java.sql.Date.valueOf(dataLocal);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd/mm/aaaa.");
            return;
        }

        Pessoa pessoa = new Pessoa(cpf, nome, email, senha, endereco, data, sexo);

        String resposta = control.cadastrarPessoa(pessoa, confirmarSenha);
        JOptionPane.showMessageDialog(this, resposta);

        if (resposta.equals("Cadastro realizado")) {
            limparCampos();
            frame.trocarTela("loginPanel");
        }
    }

    private void limparCampos() {
        TXTcpf.setText("");
        TXTconfirmaSenha.setText("");
        TXTdata.setText("");
        TXTemail.setText("");
        TXTnome.setText("");
        TXTsenha.setText("");
        cmbSexo.setSelectedIndex(0);
        TXTendereco.setText("");
    }
}
