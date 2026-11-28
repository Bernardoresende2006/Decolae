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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.Trajeto;
import model.Voo;
import tool.Conexao;
import model.Aeronave;

public class VooDAO {

    private Connection c;

    public VooDAO() {
        this.c = Conexao.obterConexao();
    }

    public List<Voo> listarTodos() {
        List<Voo> voos = new ArrayList<>();

        String sql = "SELECT num_voo, adm_cpf, id_aeronave, data, assentos_disponiveis, "
                   + "cidade_origem, cidade_destino, horario_partida, horario_chegada "
                   + "FROM empresa.voo ORDER BY data, horario_partida";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                voos.add(montarVoo(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return voos;
    }

    public List<Trajeto> listarTrajetos() {
        List<Trajeto> trajetos = new ArrayList<>();

        String sql = "SELECT cidade_origem, cidade_destino, horario_partida, horario_chegada "
                   + "FROM empresa.trajeto ORDER BY cidade_origem, cidade_destino, horario_partida";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajetos.add(new Trajeto(
                    rs.getString("cidade_origem"),
                    rs.getString("cidade_destino"),
                    rs.getTime("horario_partida").toLocalTime(),
                    rs.getTime("horario_chegada").toLocalTime()
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return trajetos;
    }

    public List<Aeronave> listarAeronavesLivres(java.time.LocalDateTime janelaInicio,
                                                java.time.LocalDateTime janelaFim,
                                                int numVooIgnorar) {
        List<Aeronave> lista = new ArrayList<>();

        String sql = "SELECT a.id_aeronave, a.modelo, a.gerente_cpf, a.localizacao, a.num_assentos "
                   + "FROM empresa.aeronave a "
                   + "WHERE a.id_aeronave NOT IN ( "
                   + "  SELECT v.id_aeronave FROM empresa.voo v "
                   + "  WHERE v.num_voo <> ? "
                   + "  AND v.data + v.horario_partida < ? "
                   + "  AND v.data + v.horario_chegada "
                   + "      + CASE WHEN v.horario_chegada <= v.horario_partida "
                   + "             THEN INTERVAL '1 day' ELSE INTERVAL '0 day' END > ? "
                   + ") ORDER BY a.id_aeronave";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, numVooIgnorar);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(janelaFim));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(janelaInicio));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aeronave a = new Aeronave();
                a.setIdAeronave(rs.getInt("id_aeronave"));
                a.setModelo(rs.getString("modelo"));
                a.setGerenteCpf(rs.getString("gerente_cpf"));
                a.setLocalizacao(rs.getString("localizacao"));
                a.setNumAssentos(rs.getInt("num_assentos"));
                lista.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<Funcionario> listarFuncionariosPorCargo(String cargo) {
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT p.cpf, p.nome, f.cargo "
                   + "FROM empresa.funcionario f "
                   + "JOIN empresa.pessoa p ON p.cpf = f.funcionario_cpf "
                   + "WHERE f.cargo = ? "
                   + "ORDER BY p.nome";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, cargo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setCpf(rs.getString("cpf"));
                f.setNome(rs.getString("nome"));
                f.setCargo(rs.getString("cargo"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
    
    public boolean existeNumVoo(int numVoo) {
    String sql = "SELECT 1 FROM empresa.voo WHERE num_voo = ?";

    try (PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, numVoo);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    } catch (SQLException ex) {
        Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        return true; // na dúvida, bloqueia o cadastro
    }
}

    public boolean salvar(Voo v) {
        String sqlVoo = "INSERT INTO empresa.voo (num_voo, adm_cpf, id_aeronave, data, assentos_disponiveis, "
                      + "cidade_origem, cidade_destino, horario_partida, horario_chegada) "
                      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlTrip = "INSERT INTO empresa.trabalha_em (num_voo, mt_cpf) VALUES (?, ?)";

        try {
            c.setAutoCommit(false);

            try (PreparedStatement stmt = c.prepareStatement(sqlVoo)) {
                stmt.setInt(1, v.getNumVoo());
                stmt.setString(2, v.getAdmCpf());
                stmt.setInt(3, v.getIdAeronave());
                stmt.setDate(4, java.sql.Date.valueOf(v.getData()));
                stmt.setInt(5, v.getAssentosDisponiveis());
                stmt.setString(6, v.getOrigem());
                stmt.setString(7, v.getDestino());
                stmt.setTime(8, java.sql.Time.valueOf(v.getHoraPartida()));
                stmt.setTime(9, java.sql.Time.valueOf(v.getHoraChegada()));
                stmt.executeUpdate();
            }

            String[] equipe = {v.getPilotoCpf(), v.getCopilotoCpf(),
                               v.getComissario1Cpf(), v.getComissario2Cpf()};

            try (PreparedStatement stmt = c.prepareStatement(sqlTrip)) {
                for (String cpf : equipe) {
                    stmt.setInt(1, v.getNumVoo());
                    stmt.setString(2, cpf);
                    stmt.executeUpdate();
                }
            }

            c.commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
            try { c.rollback(); } catch (SQLException ignore) {}
            return false;

        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }
    
    public Voo buscarPorNumero(int numVoo) {
    String sql = "SELECT num_voo, adm_cpf, id_aeronave, data, assentos_disponiveis, "
               + "cidade_origem, cidade_destino, horario_partida, horario_chegada "
               + "FROM empresa.voo WHERE num_voo = ?";

    try (PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, numVoo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return montarVoo(rs);
    } catch (SQLException ex) {
        Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}

    public List<Funcionario> listarTripulacao(int numVoo) {
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT f.funcionario_cpf, f.cargo "
                   + "FROM empresa.trabalha_em t "
                   + "JOIN empresa.funcionario f ON f.funcionario_cpf = t.mt_cpf "
                   + "WHERE t.num_voo = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, numVoo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setCpf(rs.getString("funcionario_cpf"));
                f.setCargo(rs.getString("cargo"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public int contarPassagens(int numVoo) {
        String sql = "SELECT COUNT(*) FROM empresa.passagem WHERE num_voo = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, numVoo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    private void inserirTripulacao(Voo v) throws SQLException {
        String sql = "INSERT INTO empresa.trabalha_em (num_voo, mt_cpf) VALUES (?, ?)";
        String[] equipe = {v.getPilotoCpf(), v.getCopilotoCpf(),
                           v.getComissario1Cpf(), v.getComissario2Cpf()};

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            for (String cpf : equipe) {
                stmt.setInt(1, v.getNumVoo());
                stmt.setString(2, cpf);
                stmt.executeUpdate();
            }
        }
    }
    
    private Voo montarVoo(ResultSet rs) throws SQLException {
        Voo v = new Voo();
        v.setNumVoo(rs.getInt("num_voo"));
        v.setAdmCpf(rs.getString("adm_cpf"));
        v.setIdAeronave(rs.getInt("id_aeronave"));
        v.setData(rs.getDate("data").toLocalDate());
        v.setAssentosDisponiveis(rs.getInt("assentos_disponiveis"));
        v.setOrigem(rs.getString("cidade_origem"));
        v.setDestino(rs.getString("cidade_destino"));
        v.setHoraPartida(rs.getTime("horario_partida").toLocalTime());
        v.setHoraChegada(rs.getTime("horario_chegada").toLocalTime());
        return v;
    }

    public boolean atualizar(Voo v) {
        String sqlVoo = "UPDATE empresa.voo SET id_aeronave = ?, data = ?, cidade_origem = ?, "
                      + "cidade_destino = ?, horario_partida = ?, horario_chegada = ? "
                      + "WHERE num_voo = ?";
        String sqlApagaTrip = "DELETE FROM empresa.trabalha_em WHERE num_voo = ?";

        try {
            c.setAutoCommit(false);

            int linhas;
            
            try (PreparedStatement stmt = c.prepareStatement(sqlVoo)) {
                stmt.setInt(1, v.getIdAeronave());
                stmt.setDate(2, java.sql.Date.valueOf(v.getData()));
                stmt.setString(3, v.getOrigem());
                stmt.setString(4, v.getDestino());
                stmt.setTime(5, java.sql.Time.valueOf(v.getHoraPartida()));
                stmt.setTime(6, java.sql.Time.valueOf(v.getHoraChegada()));
                stmt.setInt(7, v.getNumVoo());
                linhas = stmt.executeUpdate();
            }

            if (linhas == 0) {
                c.rollback();
                return false;
            }

            try (PreparedStatement stmt = c.prepareStatement(sqlApagaTrip)) {
                stmt.setInt(1, v.getNumVoo());
                stmt.executeUpdate();
            }

            inserirTripulacao(v);

            c.commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
            try { c.rollback(); } catch (SQLException ignore) {}
            return false;

        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }

    public boolean excluir(int numVoo) {
        String sqlTrip = "DELETE FROM empresa.trabalha_em WHERE num_voo = ?";
        String sqlVoo = "DELETE FROM empresa.voo WHERE num_voo = ?";

        try {
            c.setAutoCommit(false);

            try (PreparedStatement stmt = c.prepareStatement(sqlTrip)) {
                stmt.setInt(1, numVoo);
                stmt.executeUpdate();
            }

            int linhas;
            try (PreparedStatement stmt = c.prepareStatement(sqlVoo)) {
                stmt.setInt(1, numVoo);
                linhas = stmt.executeUpdate();
            }

            c.commit();
            return linhas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(VooDAO.class.getName()).log(Level.SEVERE, null, ex);
            try { c.rollback(); } catch (SQLException ignore) {}
            return false;

        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }
}
