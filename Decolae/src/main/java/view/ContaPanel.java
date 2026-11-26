
package view;

import control.PessoaControl;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Pessoa;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author lflea
 */
public class ContaPanel extends javax.swing.JPanel {

    private FrameTelas frameTelas;
    private JButton voltarBtn;
    private JButton salvarBtn;
    private Pessoa usuario;
    private PessoaControl control;
    
    public ContaPanel(FrameTelas frameTelas, Pessoa usuario) {
        initComponents();
        inicializaVariaveis(frameTelas, usuario);
        
        minhasConfiguracoes();
        estilizarComponentes();
        definirInformacoesCampos();
    }
    
    private void inicializaVariaveis(FrameTelas frameTelas, Pessoa usuario){
        this.control = new PessoaControl();
        this.usuario = usuario;
        this.frameTelas = frameTelas;
        this.voltarBtn = new JButton("Voltar");
        this.salvarBtn = new JButton("Salvar Dados");
        
        this.voltarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                definirInformacoesCampos();
                if(frameTelas.getTelaAnterior() == "loginPanel") frameTelas.trocarTela("menuClientePanel"); 
                else frameTelas.trocarTela(frameTelas.getTelaAnterior());
            }
        });
        
        this.salvarBtn.addActionListener(e -> {                
            Pessoa usuarioNovo = new Pessoa(this.cpfTextField.getText(), this.nomeTextField.getText(), this.emailTextField.getText(),
                    this.usuario.getSenha(), this.enderecoTextField.getText(), this.usuario.getDataNascimento(), this.usuario.getSexo());
                            
            boolean resultado = this.control.atualizaDadosConta(usuario, usuarioNovo);
            
            if(resultado) JOptionPane.showMessageDialog(this, "Atualização Feita");
            else{
                JOptionPane.showMessageDialog(this, "Nada a ser atualizado!!!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            this.usuario.setEndereco(usuarioNovo.getEndereco());
            this.usuario.setNome(usuarioNovo.getNome());
            this.frameTelas.setUsuario(usuarioNovo);
            this.frameTelas.trocarTela(this.frameTelas.getTelaAnterior());
        });
    }
    
    private void minhasConfiguracoes() {
        this.setBackground(new Color(11, 17, 32));
        this.setLayout(new MigLayout("fill"));
        this.removeAll();
        this.add(this.jPanel1, "center, w 480!");

        this.jPanel1.setBackground(Color.WHITE);
        this.jPanel1.putClientProperty("FlatLaf.style", "arc: 32; border: 1,1,1,1, #334155, 1, 32");
        this.jPanel1.setLayout(new MigLayout("wrap 2, fillx, insets 32 36 36 36, gapx 16, gapy 4", "[grow, fill][grow, fill]"));
        this.jPanel1.removeAll();

        JLabel tituloConta = new JLabel("<html>MINHA <font color='#0284C7'>CONTA</font></html>");
        tituloConta.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tituloConta.setForeground(new Color(15, 23, 42));

        JLabel subtituloConta = new JLabel("Informações pessoais cadastradas no sistema");
        subtituloConta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtituloConta.setForeground(new Color(100, 116, 139));

        this.jPanel1.add(tituloConta, "span 2");
        this.jPanel1.add(subtituloConta, "span 2, gapbottom 20");

        this.jPanel1.add(this.jLabel1, "span 2");
        this.jPanel1.add(this.nomeTextField, "span 2, h 42!, gapbottom 12");

        this.jPanel1.add(this.jLabel2);
        this.jPanel1.add(this.jLabel5);
        this.jPanel1.add(this.cpfTextField, "h 42!, gapbottom 12");
        this.jPanel1.add(this.dataNascTextField, "h 42!, gapbottom 12");

        this.jPanel1.add(this.jLabel4, "span 2");
        this.jPanel1.add(this.emailTextField, "span 2, h 42!, gapbottom 12");

        this.jPanel1.add(this.jLabel3, "span 2");
        this.jPanel1.add(this.enderecoTextField, "span 2, h 42!");
        
        this.jPanel1.add(this.salvarBtn, "span 2, h 44!, gaptop 24");
        this.jPanel1.add(this.voltarBtn, "span 2, h 44!, gaptop 24");

        this.revalidate();
        this.repaint();
    }

    private void estilizarComponentes() {
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 13);
        Color corLabel = new Color(51, 65, 85);

        JLabel[] labels = {this.jLabel1, this.jLabel2, this.jLabel3, this.jLabel4, this.jLabel5};
        for (JLabel lbl : labels) {
            lbl.setFont(fonteLabel);
            lbl.setForeground(corLabel);
        }

        JTextField[] campos = {
            this.nomeTextField,
            this.cpfTextField,
            this.enderecoTextField,
            this.emailTextField,
            this.dataNascTextField
        };

        for (JTextField campo : campos) {
            campo.setBackground(new Color(248, 250, 252));
            campo.setForeground(new Color(15, 23, 42));
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            campo.putClientProperty("FlatLaf.style",
                "arc: 12; borderColor: #CBD5E1; focusedBorderColor: #0284C7; borderWidth: 1; focusWidth: 2; margin: 0,12,0,12");
        }
        
        this.voltarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.voltarBtn.setFocusPainted(false);
        this.voltarBtn.putClientProperty("FlatLaf.style",
            "arc: 12; background: #0284C7; foreground: #FFFFFF; borderWidth: 0; " +
            "hoverBackground: #0369A1; pressedBackground: #075985; font: bold 14");
        
        this.salvarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.salvarBtn.setFocusPainted(false);
        this.salvarBtn.putClientProperty("FlatLaf.style",
        "arc: 12; background: #16A34A; foreground: #FFFFFF; borderWidth: 0; " +
        "hoverBackground: #15803D; pressedBackground: #166534; font: bold 14");
    }
    
    private void definirInformacoesCampos(){
        this.cpfTextField.setText(this.usuario.getCpf());
        this.emailTextField.setText(this.usuario.getEmail());
        this.enderecoTextField.setText(this.usuario.getEndereco());
        this.nomeTextField.setText(this.usuario.getNome());
        this.dataNascTextField.setText(this.usuario.getDataNascimento()+"");
        
        this.cpfTextField.setEnabled(false);
        this.emailTextField.setEnabled(false);
        this.dataNascTextField.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nomeTextField = new javax.swing.JTextField();
        cpfTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        dataNascTextField = new javax.swing.JTextField();
        enderecoTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nome:");

        jLabel2.setText("CPF:");

        jLabel3.setText("Endereço:");

        jLabel4.setText("Email:");

        jLabel5.setText("Data de Nascimento:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(enderecoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dataNascTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cpfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cpfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enderecoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataNascTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(480, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(421, 421, 421))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cpfTextField;
    private javax.swing.JTextField dataNascTextField;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JTextField enderecoTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nomeTextField;
    // End of variables declaration//GEN-END:variables
}
