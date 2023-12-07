package ifpr.pgua.eic.tads.contatos.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;

public class JDBCBebidaDAO implements BebidaDAO {

    FabricaConexoes fabricaConexao;

    public JDBCBebidaDAO(FabricaConexoes fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Resultado<Bebida> criar(Bebida bebida) {

        try {
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con
                    .prepareStatement("INSERT INTO oo_bebidas (nome_bebida,valor_bebida) VALUES (?,?)");

            pstm.setString(1, bebida.getNome());
            pstm.setDouble(2, bebida.getValor());

            pstm.executeUpdate();
            con.close();
            return Resultado.sucesso("Bebida cadastrada!", bebida);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<List<Bebida>> listar() {
        ArrayList<Bebida> bebidas = new ArrayList<>();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM oo_bebidas");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_bebida");
                String nome = rs.getString("nome_bebida");
                Double valor = rs.getDouble("valor_bebida");

                Bebida bebida = new Bebida(id, nome, valor);

                bebidas.add(bebida);

            }
            con.close();
            return Resultado.sucesso("Bebidas carregadas", bebidas);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

}
