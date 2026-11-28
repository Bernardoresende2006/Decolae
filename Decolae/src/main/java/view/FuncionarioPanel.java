/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import DAO.FuncionarioDAO;
import control.FuncionarioControl;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;

public class FuncionarioPanel extends javax.swing.JPanel {
    
    private FrameTelas frameTelas;
    private FuncionarioControl control;
    private List<Funcionario> listaFuncionarios;
    private Funcionario funcionarioSelecionado;
    private FuncionarioDialog funcionarioDialog;
    
    private final Color FUNDO = new Color(11, 18, 32);
    private final Color AZUL = new Color(8, 126, 189);
    private final Color CIANO = new Color(56, 189, 248);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA = new Color(148, 163, 184);
    private final Color FUNDO_COMPONENTE = new Color(30, 41, 59);
    private final Color CINZA_CLARO = new Color(190, 190, 190);
    private final Color PRETO = Color.BLACK; 
    
    
    public FuncionarioPanel(FrameTelas frameTelas){
        this.frameTelas = frameTelas;
        initComponents();
        inicializarVariaveis();
        
        carregarFuncionarios();
    }
    
    public void inicializarVariaveis(){
        this.funcionarioDialog = new FuncionarioDialog(this.frameTelas, true);
    }
    
    public FuncionarioPanel() {
        initComponents();
        carregarFuncionarios();
    }
    
    public Funcionario getFuncionarioSelecionado() {
        return this.funcionarioSelecionado;
    }
    
    public void carregarFuncionarios(){
        FuncionarioDAO dao = new FuncionarioDAO();
        ArrayList<Funcionario> funcionarios = dao.listarFuncionarios();
        DefaultListModel<Funcionario> model = new DefaultListModel<>();
        for (Funcionario f : funcionarios) {
            model.addElement(f);
        }

        jList2.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jTextField1 = new javax.swing.JTextField();
        VoltarButton = new javax.swing.JButton();
        EditarButton = new javax.swing.JButton();
        VoltarButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 40)); // NOI18N
        jLabel1.setText("Funcionários");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jList2.setFont(new java.awt.Font("Noto Sans", 0, 27)); // NOI18N
        jList2.setModel(new DefaultListModel<Funcionario>());
        jScrollPane2.setViewportView(jList2);

        jTextField1.setText("                    CPF do Funcionário                    |                              Nome                              |                        Cargo                         |                                        Salário ");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        VoltarButton.setText("Voltar");
        VoltarButton.addActionListener(this::VoltarButtonActionPerformed);

        EditarButton.setText("Editar");
        EditarButton.addActionListener(this::EditarButtonActionPerformed);

        VoltarButton1.setText("Cadastrar Novo Funcionário");
        VoltarButton1.addActionListener(this::VoltarButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(387, 387, 387)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(EditarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(VoltarButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(VoltarButton)))))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VoltarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VoltarButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(231, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(384, 384, 384)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(731, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(677, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        //frameTelas.trocarTela("FuncionarioDialog");
        
        this.funcionarioDialog.carregarFuncionarioSelecionado(this.getFuncionarioSelecionado());
        this.funcionarioDialog.setVisible(true);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void VoltarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarButtonActionPerformed
        frameTelas.trocarTela("menuGerentePanel");
    }//GEN-LAST:event_VoltarButtonActionPerformed

    private void EditarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarButtonActionPerformed
        Funcionario selecionado = jList2.getSelectedValue();
        
        if(selecionado == null){
            JOptionPane.showMessageDialog(
            this,
            "Nenhum funcionário foi selecionado.\nSelecione um funcionário na lista para editar.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        this.funcionarioSelecionado = selecionado;
        //frameTelas.trocarTela("FuncionarioDialog");
        this.funcionarioDialog.carregarFuncionarioSelecionado(funcionarioSelecionado);
        this.funcionarioDialog.setVisible(true);
    }//GEN-LAST:event_EditarButtonActionPerformed

    private void VoltarButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VoltarButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditarButton;
    private javax.swing.JButton VoltarButton;
    private javax.swing.JButton VoltarButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<Funcionario> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}


