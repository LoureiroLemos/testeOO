package ifpr.pgua.eic.tads.contatos.model.daos;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;

public interface LancheDAO {
    Resultado<Lanche> criar(Lanche lanche);

    Resultado<List<Lanche>> listar();
}
