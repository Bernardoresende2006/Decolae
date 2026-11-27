/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import tool.Conexao;

public class FuncionarioDAO {
    
    private Connection c;
    
    public FuncionarioDAO(){
        this.c = Conexao.obterConexao();
    }
    
    public ArrayList<String> consultarFuncionarios(){
        
        ArrayList<String> FuncResults = new ArrayList<>();
        
        String sql = "SELECT cpf, nome, endereco, data_nascimento, sexo FROM funcionario";
        
        try(PreparedStatement consulta = c.prepareStatement(sql)){
            ResultSet resultado = consulta.executeQuery();
            
            while(resultado.next()){
//                Funcionario f = new Funcionario();
            }
        } catch(SQLException ex){
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
        }
        return FuncResults;
    }
    
}
