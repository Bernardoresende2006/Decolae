
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import tool.Conexao;
import model.Pessoa;

public class PessoaDAO {
    private Connection c;
    
    public PessoaDAO(){
        this.c = Conexao.obterConexao();
    }
    
    public Pessoa consultaLogin(Pessoa pessoaEntrada){
        Pessoa pessoaResultado = null;
        
        String sql = "SELECT * FROM empresa.pessoa WHERE (cpf=? OR email=?) AND senha=?";
        
        try(PreparedStatement consulta = c.prepareStatement(sql)){
            consulta.setString(1, pessoaEntrada.getCpf());
            consulta.setString(2, pessoaEntrada.getEmail());
            consulta.setString(3, pessoaEntrada.getSenha());
            
            ResultSet resultado = consulta.executeQuery();
            if(resultado.next()){
                pessoaResultado = new Pessoa();
                        
                pessoaResultado.setCpf(resultado.getString("cpf"));
                pessoaResultado.setNome(resultado.getString("nome"));
                pessoaResultado.setEndereco(resultado.getString("endereco"));
                pessoaResultado.setDataNascimento(resultado.getDate("data_nascimento"));
                pessoaResultado.setSexo(resultado.getString("sexo"));
                pessoaResultado.setEmail(resultado.getString("email"));
                pessoaResultado.setSenha(resultado.getString("senha"));
            }
        }catch(SQLException ex){
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
        }
        
        return pessoaResultado;
    }
    
    public String consultaCargoFuncionario(String cpf){
        
        String sql = "SELECT cargo FROM empresa.funcionario WHERE funcionario_cpf=?";
        
        try(PreparedStatement consulta = c.prepareStatement(sql)){
            consulta.setString(1, cpf);
            
            ResultSet resultado = consulta.executeQuery();
            if(resultado.next()){
                return resultado.getString("cargo");
            }      
        }catch(SQLException ex){
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
        }
        return null;
    }
    
    public boolean salvar(Pessoa pessoa){
        
        String sql = "INSERT INTO empresa.pessoa (cpf, nome, email, senha, endereco, data_nascimento, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = c.prepareStatement(sql)){
            stmt.setString(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getSenha());
            stmt.setString(5, pessoa.getEndereco());
            java.sql.Date dataSql = (pessoa.getDataNascimento() != null)
            ? new java.sql.Date(pessoa.getDataNascimento().getTime())
            : null;
            stmt.setDate(6, dataSql);
            stmt.setString(7, pessoa.getSexo());
            stmt.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){

            return false;
        }
    }
    
    public boolean atualizar(Pessoa pessoa){
        
        String sql = "UPDATE empresa.pessoa SET nome = ?, email = ?, senha = ?, endereco = ?, data_nascimento = ?, sexo = ? WHERE cpf = ?";
        
        try(PreparedStatement stmt = c.prepareStatement(sql)){
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setString(3, pessoa.getSenha());
            stmt.setString(4, pessoa.getEndereco());
            java.sql.Date dataSql = (pessoa.getDataNascimento() != null)
            ? new java.sql.Date(pessoa.getDataNascimento().getTime())
            : null;
            stmt.setDate(5, dataSql);
            stmt.setString(6, pessoa.getSexo());
            stmt.setString(7, pessoa.getCpf());
            
            return stmt.executeUpdate() > 0;
            
        }catch(SQLException ex){

            return false;
        }
    }
    
    public boolean excluir(String cpf){
        
        String sql = "DELETE FROM empresa.pessoa WHERE cpf = ?";
        
        try(PreparedStatement stmt = c.prepareStatement(sql)){
            stmt.setString(1, cpf);

            return stmt.executeUpdate() > 0;

        }catch(SQLException ex){

            return false;
        }
    }
    
}
