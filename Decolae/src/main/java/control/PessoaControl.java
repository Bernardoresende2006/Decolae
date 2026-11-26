
package control;

import DAO.PessoaDAO;
import model.Pessoa;


public class PessoaControl {
    private PessoaDAO dao;
    
    public PessoaControl(){
        dao = new PessoaDAO();
    }
    
    public Pessoa verificaLogin(Pessoa pessoaEntrada){
        return dao.consultaLogin(pessoaEntrada);
    }
    
    public String verificaCargo(String cpf){
        return dao.consultaCargoFuncionario(cpf);
    }
    
    public boolean atualizaDadosConta(Pessoa usuarioAntigo, Pessoa usuarioNovo){
        if(usuarioAntigo.equals(usuarioNovo)) return false;
        
        return dao.atualizar(usuarioNovo);
    }
    
    private String validarDados(Pessoa pessoa, String confirmarSenha){
        
        if(pessoa == null){
            
            return "Dados inválidos";
        }
        
        if(pessoa.getCpf() == null || pessoa.getCpf().trim().isEmpty()){
            
            return "Preencha o campo cpf";
        }
        
        String cpfLimpo = pessoa.getCpf().replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11) {
            
            return "O CPF deve conter 11 dígitos.";
        }
        
        pessoa.setCpf(cpfLimpo);
        
        if(pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()){
            
            return "Preencha o campo nome";
        }
        
        if(pessoa.getEmail() == null || pessoa.getEmail().trim().isEmpty()){
            
            return "Preencha o campo email";
        }
        
        if(!pessoa.getEmail().contains("@")){
            
            return "Informe um email válido";
        }
        
        if(pessoa.getSenha() == null || pessoa.getSenha().trim().isEmpty()){
            
            return "Preencha o campo senha";
        }
        
        if(pessoa.getSenha().length() < 8){
            
            return "A senha deve ter pelo menos 8 caracteres";
        }
        
        if (!pessoa.getSenha().equals(confirmarSenha)) {
            
            return "As senhas não coincidem.";
        }
        
        if(pessoa.getEndereco() == null || pessoa.getEndereco().trim().isEmpty()){
            
            return "Preencha o campo endereco";
        }
        
        if(pessoa.getDataNascimento() == null){
            
            return "Preencha o campo data de nascimento";
        }
        
        if(pessoa.getDataNascimento().after(new java.util.Date())){
            
            return "Informe uma data válida";
        }
        
        if(pessoa.getSexo() == null || pessoa.getSexo().trim().isEmpty()){
            
            return "Preencha o campo sexo";
        }
        
        return "OK";
    }
    
    public String cadastrarPessoa(Pessoa pessoa, String confirmarSenha){
        
        String resposta = validarDados(pessoa, confirmarSenha);
        
        if(resposta.equals("OK")){
            
            if(dao.salvar(pessoa)) return "Cadastro realizado";
            return "Erro ao cadastrar";
        }
        
        return resposta;
    }
}
