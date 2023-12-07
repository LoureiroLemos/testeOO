package ifpr.pgua.eic.tads.contatos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;
import ifpr.pgua.eic.tads.contatos.model.repositories.BebidaRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.PedidoRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AddPedidoController {

    private PedidoRepository repository;
    private BebidaRepository repositorioBebida;
    private LancheRepository repositorioLanche;

    public AddPedidoController(PedidoRepository repository, BebidaRepository repositorioBebida,
            LancheRepository repositorioLanche) {
        this.repository = repository;
        this.repositorioBebida = repositorioBebida;
        this.repositorioLanche = repositorioLanche;
    }

    public Handler get = (Context ctx) -> {
        Map<String, Object> model = new HashMap<>();

        Resultado<List<Bebida>> resultadoBebida = repositorioBebida.listarTodos();
        Resultado<List<Lanche>> resultadoLanche = repositorioLanche.listarTodos();

        model.put("bebidas", resultadoBebida.comoSucesso().getObj());
        model.put("lanches", resultadoLanche.comoSucesso().getObj());

        ctx.render("templates/addPedido.peb", model);
    };

    public Handler post = (Context ctx) -> {
        String bebidaId = ctx.formParam("bebida");
        String lancheId = ctx.formParam("lanche");

        Resultado<Bebida> resultadoBebida = repositorioBebida.getById(Integer.valueOf(bebidaId));
        Resultado<Lanche> resultadoLanche = repositorioLanche.getById(Integer.valueOf(lancheId));

        Bebida bebida = resultadoBebida.comoSucesso().getObj();
        Lanche lanche = resultadoLanche.comoSucesso().getObj();

        Resultado<Pedido> resultado = repository.cadastrar(bebida, lanche);

        Map<String, Object> model = new HashMap<>();
        model.put("resultado", resultado);
        if (resultado.foiErro()) {
            model.put("bebidaId", Integer.valueOf(bebidaId));
            Resultado<List<Bebida>> ret1 = repositorioBebida.listarTodos();
            model.put("bebidas", ret1.comoSucesso().getObj());

            model.put("lancheId", Integer.valueOf(lancheId));
            Resultado<List<Lanche>> ret2 = repositorioLanche.listarTodos();
            model.put("lanches", ret2.comoSucesso().getObj());
        }

        ctx.render("templates/addPedido.peb", model);
    };

}
