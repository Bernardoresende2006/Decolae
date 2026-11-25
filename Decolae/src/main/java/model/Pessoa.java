
package model;

import java.util.Date;
import java.util.Objects;

public class Pessoa{

    private String cpf;
    private  String nome;
    private String email;
    private String senha;
    private String endereco;
    private Date dataNascimento;
    private String sexo;

    public Pessoa(){

    }

    public Pessoa(String cpf, String nome, String email, String senha, String endereco, Date dataNascimento, String sexo){

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pessoa other = (Pessoa) obj;
        return Objects.equals(this.nome, other.nome)
            && Objects.equals(this.cpf, other.cpf)
            && Objects.equals(this.email, other.email)
            && Objects.equals(this.endereco, other.endereco)
            && Objects.equals(this.dataNascimento, other.dataNascimento);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getSenha(){
        return this.senha;
    }
    
    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
