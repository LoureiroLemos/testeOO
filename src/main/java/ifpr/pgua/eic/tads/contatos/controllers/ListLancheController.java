package ifpr.pgua.eic.tads.contatos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ListLancheController {
    private LancheRepository repositorio;

    public ListLancheController(LancheRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx) -> {

        Resultado<List<Lanche>> resultado = repositorio.listarTodos();

        Map<String, Object> model = new HashMap<>();

        model.put("resultado", resultado);
        if (resultado.foiSucesso()) {
            model.put("lista", resultado.comoSucesso().getObj());
        }

        ctx.render("templates/listLanches.peb", model);
    };
}
