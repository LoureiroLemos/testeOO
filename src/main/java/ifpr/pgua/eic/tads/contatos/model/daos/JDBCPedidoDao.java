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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criar'");
    }

    @Override
    public Resultado<List<Pedido>> listar() {
        ArrayList<Pedido> lista = new ArrayList<>();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement(
                    "SELECT p.id_pedido, b.nome_bebida as bebida, l.nome_lanche FROM oo_pedidos p inner join oo_bebidas b inner join  oo_lanches l on p.id_bebida = b.id_bebida AND p.id_lanche = l.id_lanche");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_pedido");
                String nomeBebida = rs.getString("bebida");
                String nomeLanche = rs.getString("nome_lanche");

                Pedido pedido = new Pedido(id, nomeBebida, nomeLanche);

                lista.add(pedido);
            }
            con.close();
            return Resultado.sucesso("Contatos carregados", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

}
