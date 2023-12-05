package ifpr.pgua.eic.tads.contatos.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
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
        ArrayList<Lanche> lista = new ArrayList<>();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM oo_lanches");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_lanche");
                String nome = rs.getString("nome_lanche");
                Double valor = rs.getDouble("valor_lanche");

                Lanche lanche = new Lanche(id, nome, valor);

                lista.add(lanche);
            }
            con.close();
            return Resultado.sucesso("Contatos carregados", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

}
