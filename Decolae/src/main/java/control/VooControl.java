/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DAO.VooDAO;
import java.util.List;
import model.Aeronave;
import model.Funcionario;
import model.Trajeto;
import model.Voo;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Arrays;

public class VooControl {

    public static final String PILOTO = "Piloto";
    public static final String COPILOTO = "Copiloto";
    public static final String COMISSARIO = "Comissário de Bordo";
    private static final int ASSENTOS_INICIAIS = 102;
    private PessoaControl pessoaControl; 

    private VooDAO dao;
    private AeronaveControl aeronaveControl;

    public VooControl() {
        this.dao = new VooDAO();
        this.aeronaveControl = new AeronaveControl();
        this.pessoaControl = new PessoaControl();
    }

    public List<Voo> todosVoos() {
        return dao.listarTodos();
    }

    public List<Trajeto> todosTrajetos() {
        return dao.listarTrajetos();
    }

    public List<Aeronave> todasAeronaves() {
        return aeronaveControl.todasAeronaves();
    }

    public List<Funcionario> pilotos() {
        return dao.listarFuncionariosPorCargo(PILOTO);
    }

    public List<Funcionario> copilotos() {
        return dao.listarFuncionariosPorCargo(COPILOTO);
    }

    public List<Funcionario> comissarios() {
        return dao.listarFuncionariosPorCargo(COMISSARIO);
    }
    
    private String validarCargo(String cpf, String cargoEsperado, String nomeFuncao) {
    String cargo = pessoaControl.verificaCargo(cpf);

    if (cargo == null || !cargo.equalsIgnoreCase(cargoEsperado)) {
        return "O funcionário escolhido como " + nomeFuncao + " não possui esse cargo";
    }
    return "OK";
}

    private String validarDados(Voo v, boolean edicao) {

        if (v == null) return "Dados inválidos";
        
        String[] equipe = {v.getPilotoCpf(), v.getCopilotoCpf(), v.getComissario1Cpf(), v.getComissario2Cpf()};

        if (v.getNumVoo() <= 0) return "Número do voo inválido";

        if (v.getOrigem() == null || v.getDestino() == null
                || v.getHoraPartida() == null || v.getHoraChegada() == null) {
            return "Selecione um trajeto";
        }

        if (v.getIdAeronave() <= 0) return "Selecione uma aeronave";

        if (v.getData() == null) return "Informe a data do voo";

        if (v.getData().isBefore(LocalDate.now())) return "A data do voo não pode estar no passado";

        if (v.getAdmCpf() == null || v.getAdmCpf().trim().isEmpty()) return "Administrador não identificado";

        for (String cpf : equipe) {
            if (cpf == null || cpf.trim().isEmpty()) {
                return "O voo precisa de piloto, copiloto e dois comissários";
            }
        }

        if (new HashSet<>(Arrays.asList(equipe)).size() < 4) {  
            return "A mesma pessoa não pode ocupar duas posições no voo";
        }

        String r = validarCargo(v.getPilotoCpf(), PILOTO, "piloto");
        if (!r.equals("OK")) return r;

        r = validarCargo(v.getCopilotoCpf(), COPILOTO, "copiloto");
        if (!r.equals("OK")) return r;

        r = validarCargo(v.getComissario1Cpf(), COMISSARIO, "comissário 1");
        if (!r.equals("OK")) return r;

        r = validarCargo(v.getComissario2Cpf(), COMISSARIO, "comissário 2");
        if (!r.equals("OK")) return r;

        if (!edicao && dao.existeNumVoo(v.getNumVoo())) return "Já existe um voo com esse número";
        if (edicao && !dao.existeNumVoo(v.getNumVoo())) return "O voo não existe mais";
        
        return "OK";
    }

    public String cadastrarVoo(Voo voo) {

        String resposta = validarDados(voo, false);

        if (resposta.equals("OK")) {
            voo.setAssentosDisponiveis(ASSENTOS_INICIAIS);

            if (dao.salvar(voo)) return "Voo cadastrado.";
            return "Erro ao cadastrar voo.";
        }

        return resposta;
    }
    
    public Voo buscarVoo(int numVoo) {
        Voo v = dao.buscarPorNumero(numVoo);
        if (v == null) return null;

        for (Funcionario f : dao.listarTripulacao(numVoo)) {
            String cargo = f.getCargo();

            if (PILOTO.equalsIgnoreCase(cargo)) {
                v.setPilotoCpf(f.getCpf());
            } else if (COPILOTO.equalsIgnoreCase(cargo)) {
                v.setCopilotoCpf(f.getCpf());
            } else if (COMISSARIO.equalsIgnoreCase(cargo)) {
                if (v.getComissario1Cpf() == null) v.setComissario1Cpf(f.getCpf());
                else v.setComissario2Cpf(f.getCpf());
            }
        }
        return v;
    }

    public String atualizarVoo(Voo voo) {

        String resposta = validarDados(voo, true);
        if (!resposta.equals("OK")) return resposta;

        if (dao.contarPassagens(voo.getNumVoo()) > 0) {
            return "Este voo já tem passagens vendidas e não pode ser alterado.";
        }

        if (dao.atualizar(voo)) return "Voo atualizado.";
        return "Erro ao atualizar voo.";
    }

    public String excluirVoo(int numVoo) {

        if (numVoo <= 0) return "Voo inválido";

        if (dao.contarPassagens(numVoo) > 0) {
            return "Este voo já tem passagens vendidas e não pode ser excluído.";
        }

        if (dao.excluir(numVoo)) return "Voo excluído.";
        return "Não foi possível excluir o voo.";
    }
}
