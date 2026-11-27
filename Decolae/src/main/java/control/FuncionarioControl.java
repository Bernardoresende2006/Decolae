/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DAO.FuncionarioDAO;
import java.util.List;
import model.Funcionario;

public class FuncionarioControl {
    private FuncionarioDAO dao;
    
    public FuncionarioControl(){
        dao = new FuncionarioDAO();
    }
    
    public List<Funcionario> todosFuncionarios(){
        
        return dao.listarFuncionarios();
    }
    
}
