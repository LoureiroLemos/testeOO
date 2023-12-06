package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.LancheDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.PedidoDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public class ImplPedidoRepository implements PedidoRepository {
    private PedidoDAO dao;

    public ImplPedidoRepository(PedidoDAO dao, BebidaDAO bebidaDao, LancheDAO lancheDAO) {
        this.dao = dao;
    }

    @Override
    public Resultado<Pedido> cadastrar(Bebida bebida, Lanche lanche) {
        Pedido pedido = new Pedido(bebida, lanche);
        return dao.criar(pedido);
    }

    @Override
    public Resultado<List<Pedido>> listarTodos() {
        return dao.listar();
    }

}
