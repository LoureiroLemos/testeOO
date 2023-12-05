package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;

public interface LancheRepository {
    Resultado<Lanche> cadastrar(String nome, Double valor);

    Resultado<List<Lanche>> listarTodos();
}
