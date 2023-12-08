package ifpr.pgua.eic.tads.contatos.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;

public class JDBCLancheDAO implements LancheDAO {

    private FabricaConexoes fabricaConexao;

    public JDBCLancheDAO(FabricaConexoes fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Resultado<Lanche> criar(Lanche lanche) {
        try {
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con
                    .prepareStatement("INSERT INTO oo_lanches (nome_lanche,valor_lanche) VALUES (?,?)");

            pstm.setString(1, lanche.getNome());
            pstm.setDouble(2, lanche.getValor());

            pstm.executeUpdate();
            con.close();
            return Resultado.sucesso("Lanche cadastrado!", lanche);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<List<Lanche>> listar() {
        ArrayList<Lanche> lanches = new ArrayList<>();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM oo_lanches");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_lanche");
                String nome = rs.getString("nome_lanche");
                Double valor = rs.getDouble("valor_lanche");

                Lanche lanche = new Lanche(id, nome, valor);

                lanches.add(lanche);

            }
            con.close();
            return Resultado.sucesso("Contatos carregados", lanches);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

    @Override
    public Resultado<Lanche> buscarLanchePedido(Pedido pedido) {
        try (Connection con = fabricaConexao.getConnection();) {
            PreparedStatement pstm = con.prepareStatement(
                    "SELECT * from oo_lanches inner JOIN oo_pedidos on oo_lanches.id_lanche=oo_pedidos.id_lanche WHERE oo_pedidos.id_pedido=?");

            pstm.setInt(1, pedido.getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("oo_lanches.id_lanche");
                String nome = rs.getString("oo_lanches.nome_lanche");
                Double descricao = rs.getDouble("oo_lanches.valor_lanche");

                Lanche lanche1 = new Lanche(id, nome, descricao);
                return Resultado.sucesso("Lanches carregados", lanche1);

            }
            return Resultado.erro("Lanche não encontrado");

        } catch (SQLException e) {
            return Resultado.erro("Problema ao fazer seleção!! " + e.getMessage());
        }
    }

}
