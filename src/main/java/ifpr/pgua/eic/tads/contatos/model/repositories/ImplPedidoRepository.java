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
    private BebidaDAO bebidaDao;
    private LancheDAO lancheDao;

    public ImplPedidoRepository(PedidoDAO dao, BebidaDAO bebidaDao, LancheDAO lancheDAO) {
        this.dao = dao;
        this.bebidaDao = bebidaDao;
        this.lancheDao = lancheDAO;
    }

    @Override
    public Resultado<Pedido> cadastrar(Bebida bebida, Lanche lanche, String observacao) {
        Pedido pedido = new Pedido(bebida, lanche, observacao);
        return dao.criar(pedido);
    }

    @Override
    public Resultado<List<Pedido>> listarTodos() {
        Resultado<List<Pedido>> resultado = dao.listar();

        if (resultado.foiSucesso()) {
            List<Pedido> lista = resultado.comoSucesso().getObj();
            for (Pedido p : lista) {
                Resultado<Bebida> res2 = bebidaDao.buscarBebidaPedido(p);
                Resultado<Lanche> res3 = lancheDao.buscarLanchePedido(p);
                if (res2.foiErro()) {
                    return res2.comoErro();
                }
                if (res3.foiErro()) {
                    return res3.comoErro();
                } else {
                    p.setBebida(res2.comoSucesso().getObj());
                    p.setLanche(res3.comoSucesso().getObj());
                }
            }
        }
        return resultado;
    }

}
