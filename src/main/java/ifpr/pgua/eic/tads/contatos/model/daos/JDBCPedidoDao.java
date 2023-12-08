package ifpr.pgua.eic.tads.contatos.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;
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
                    .prepareStatement("INSERT INTO oo_pedidos(id_bebida,id_lanche, observacao) VALUES (?,?,?)");

            pstm.setInt(1, pedido.getBebida().getId());
            pstm.setInt(2, pedido.getLanche().getId());
            pstm.setString(3, pedido.getObservacao());

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
                    "SELECT * FROM oo_pedidos inner join oo_bebidas inner join oo_lanches on oo_pedidos.id_bebida = oo_bebidas.id_bebida AND oo_pedidos.id_lanche = oo_lanches.id_lanche  \r\n"
                            + //
                            "ORDER BY `oo_pedidos`.`id_pedido` ASC");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_pedido");

                String observacao = rs.getString("observacao");

                Pedido pedido = new Pedido(id, observacao);

                lista.add(pedido);

            }
            con.close();
            return Resultado.sucesso("Pedidos carregados", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

}
