package ifpr.pgua.eic.tads.contatos.controllers;

import ifpr.pgua.eic.tads.contatos.model.repositories.PedidoRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AddPedidoController {

    private PedidoRepository repositorio;

    public AddPedidoController(PedidoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx) -> {

        ctx.render("templates/addPedido.peb");
    };

}
