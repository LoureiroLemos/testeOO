package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public interface PedidoRepository {
    Resultado<Pedido> cadastrar(Bebida bebida, Lanche lanche, String observacao);

    Resultado<List<Pedido>> listarTodos();
}
