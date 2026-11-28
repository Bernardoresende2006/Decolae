    package view;

import java.awt.CardLayout;
import javax.swing.JPanel;
import model.Pessoa;

/**
 *
 * @author lflea
 */
public class FrameTelas extends javax.swing.JFrame {

    CardLayout cardLayout;
    private String telaAtual = "";
    private String telaAnterior = "loginPanel";
    private MenuClientePanel menuClientePanel;
    private Pessoa usuario;
    private AeronavePanel aeronavePanel;
    private AdministrarVoosPanel administrarVoosPanel;
    private FuncionarioPanel funcionarioPanel;
    
    public FrameTelas() {
        initComponents();        
        inicializacaoVariaveis();
        minhasConfiguracoes();
        
        this.configurarNavegacao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    private void minhasConfiguracoes() {       
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void inicializacaoVariaveis(){
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);
        this.usuario = null;
    }
    
    public static void main(String args[]) {
        com.formdev.flatlaf.FlatLightLaf.setup();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrameTelas frame = new FrameTelas();
                frame.setVisible(true);
                frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
            }
        });
    }

    private void configurarNavegacao() {
        JPanel loginPanel = new LoginPanel(this);
        JPanel cadastroPanel = new CadastroPanel(this);
        this.menuClientePanel = new MenuClientePanel(this);
        JPanel menuGerentePanel = new MenuGerentePanel(this);
        this.aeronavePanel = new AeronavePanel(this);
        JPanel menuAdmPanel = new MenuAdmPanel(this);
        JPanel menuMembroPanel = new MenuMembroPanel(this);
        this.administrarVoosPanel = new AdministrarVoosPanel(this);
        this.funcionarioPanel = new FuncionarioPanel(this);

        this.add(loginPanel, "loginPanel");
        this.add(cadastroPanel, "cadastroPanel");
        this.add(this.menuClientePanel, "menuClientePanel");
        this.add(menuGerentePanel, "menuGerentePanel");
        this.add(this.aeronavePanel, "aeronavePanel");
        this.add(menuAdmPanel, "menuAdmPanel");
        this.add(menuMembroPanel, "menuMembroPanel");
        this.add(administrarVoosPanel, "administrarVoosPanel");
        this.add(this.funcionarioPanel, "FuncionarioPanel");
        
        this.trocarTela("loginPanel");
    }

    public void trocarTela(String nomeTela) {
        
        if(nomeTela.equals("aeronavePanel") && this.aeronavePanel != null){            
            this.aeronavePanel.atualizarTabela();
        }
        if (nomeTela.equals("administrarVoosPanel") && this.administrarVoosPanel != null) {
            this.administrarVoosPanel.carregarDados();
        }
        if (nomeTela.equals("FuncionarioPanel") && this.funcionarioPanel != null) {
        this.funcionarioPanel.carregarFuncionarios(); 
    }
        
        this.cardLayout.show(this.getContentPane(), nomeTela);
    }

    public void abrirCliente(String origem) {
        this.telaAnterior = origem;
        this.menuClientePanel.atualizarTextoBotao();
        this.telaAtual = "menuClientePanel";
        trocarTela("menuClientePanel");
    }

    public void voltarTelaAnterior() {
        trocarTela(telaAnterior);
    }

    public String getTelaAnterior() {
        return telaAnterior;
    }
    
    public void setUsuario(Pessoa usuario){
        this.usuario = usuario;
    }
    
    public Pessoa getUsuario(){
        return this.usuario;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}