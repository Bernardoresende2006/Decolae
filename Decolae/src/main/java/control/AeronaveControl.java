/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DAO.AeronaveDAO;
import java.util.List;
import model.Aeronave;

/**
 *
 * @author rot-civ
 */
public class AeronaveControl {
    
    private AeronaveDAO dao;
    
    public AeronaveControl(){
        dao = new AeronaveDAO();
    }
    
    private String validarDados(Aeronave viao){
        
        if(viao == null){
            
            return "Dados inválidos";
        }
        
        if(viao.getIdAeronave() <= 0){
            
            return "id aeronave inválido";
        }
        
        if(viao.getModelo() == null || viao.getModelo().trim().isEmpty() || viao.getModelo().length() > 50){
            
            return "Modelo da aeronave inválido";
        }
        
        if(viao.getGerenteCpf() == null || viao.getGerenteCpf().trim().isEmpty() || viao.getGerenteCpf().length() > 11){
            
            return "CPF do gerente inválido";
        }
        
        if(viao.getNumAssentos() < 1 || viao.getNumAssentos() > 100){
            
            return "Números de assentos inválido";
        }
        
        if(viao.getLocalizacao() == null || viao.getLocalizacao().trim().isEmpty() || viao.getLocalizacao().length() > 50){
            
            return "Localização inválida";
        }
        
        return "OK";
    }
    
    public String cadastrarAeronave(Aeronave viao){
        
        String resposta = validarDados(viao);
        
        if(resposta.equals("OK")){
            
            if(dao.salvar(viao)) return "Cadastro realizado.";
            return "Erro ao cadastrar aeronave.";
        }
        
        return resposta;
    }
    
    public String atualizarAeronave(Aeronave viao){
        
        String resposta = validarDados(viao);
        
        if(resposta.equals("OK")){
            
            if(dao.atualizar(viao)) return "Atualização dos dados realizada.";
            return "Erro ao atualizar os dados da aeronave.";
        }
        
        return resposta;
        
    }
    
    public String excluirAeronave(Aeronave viao){
        
        if(viao != null && viao.getIdAeronave() > 0){
            
            if(dao.excluir(viao.getIdAeronave())) return "Exclusão concluída com sucesso.";
            return "Aeronave não existe no banco.";
        }
        
        return "id inválido";
    }
    
    public List<Aeronave> todasAeronaves(){
        
        return dao.listarTodos();
    }
}
