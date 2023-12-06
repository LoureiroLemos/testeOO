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
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public class JDBCPedidoDao implements PedidoDAO {

    private FabricaConexoes fabricaConexao;

    public JDBCPedidoDao(FabricaConexoes fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Resultado<Pedido> criar(Pedido pedido) {
        try (Connection con = fabricaConexao.getConnection();) {

            PreparedStatement pstm = con
                    .prepareStatement("INSERT INTO oo_pedidos(id_pedido,id_bebida,id_lanche) VALUES (?,?,?)");

            pstm.setInt(1, pedido.getId());
            pstm.setInt(2, pedido.getBebida().getId());
            pstm.setInt(3, pedido.getLanche().getId());

            pstm.executeUpdate();

            return Resultado.sucesso("Pedido cadastrado!", pedido);
        } catch (SQLException e) {
            return Resultado.erro("Problema ao conectar " + e.getMessage());
        }
    }

    @Override
    public Resultado<List<Pedido>> listar() {
        ArrayList<Pedido> lista = new ArrayList<>();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement(
                    "SELECT * FROM oo_pedidos inner join oo_bebidas inner join oo_lanches on oo_pedidos.id_bebida = oo_bebidas.id_bebida AND oo_pedidos.id_lanche = oo_lanches.id_lanche");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_pedido");
                Bebida bebida = new Bebida(rs.getInt("id_bebida"), rs.getString("nome_bebida"),
                        rs.getDouble("valor_bebida"));
                Lanche lanche = new Lanche(rs.getInt("id_lanche"), rs.getString("nome_lanche"),
                        rs.getDouble("valor_lanche"));

                Pedido pedido = new Pedido(id, bebida, lanche);

                lista.add(pedido);

            }
            con.close();
            return Resultado.sucesso("Contatos carregados", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

}
