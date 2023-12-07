package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.daos.LancheDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;

public class ImplLancheRepository implements LancheRepository {

    private LancheDAO dao;
    private List<Lanche> lista;

    public ImplLancheRepository(LancheDAO dao) {
        this.dao = dao;
        this.lista = new ArrayList<>();
    };

    @Override
    public Resultado<Lanche> cadastrar(String nome, Double valor) {
        if (nome.isBlank() || nome.isEmpty()) {
            return Resultado.erro("Nome inválido");
        }

        if (valor.isNaN() || valor <= 0) {
            return Resultado.erro("Valor inválido");
        }

        Lanche lanche = new Lanche(nome, valor);

        return dao.criar(lanche);
    }

    @Override
    public Resultado<List<Lanche>> listarTodos() {
        var resultado = dao.listar();

        if (resultado.foiSucesso()) {
            lista.clear();
            lista.addAll(resultado.comoSucesso().getObj());
        }

        return dao.listar();
    }

    @Override
    public Resultado<Lanche> getById(int id) {
        if (lista.size() != 0) {
            Lanche ret = lista.stream().filter(categoria -> categoria.getId() == id).findFirst().get();
            return Resultado.sucesso("Lanche encontrado", ret);
        }
        return Resultado.erro("Problema ao buscar lanche!");
    }

}
