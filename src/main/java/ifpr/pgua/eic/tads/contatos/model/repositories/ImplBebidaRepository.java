package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;

public class ImplBebidaRepository implements BebidaRepository {

    private BebidaDAO dao;
    private List<Bebida> lista;

    public ImplBebidaRepository(BebidaDAO dao) {
        this.dao = dao;
        this.lista = new ArrayList<>();
    }

    @Override
    public Resultado<Bebida> cadastrar(String nome, Double valor) {
        if (nome.isBlank() || nome.isEmpty()) {
            return Resultado.erro("Nome inválido");
        }

        if (valor.isNaN() || valor <= 0) {
            return Resultado.erro("Valor inválido");
        }

        Bebida bebida = new Bebida(nome, valor);

        return dao.criar(bebida);
    }

    @Override
    public Resultado<List<Bebida>> listarTodos() {
        var resultado = dao.listar();

        if (resultado.foiSucesso()) {
            lista.clear();
            lista.addAll(resultado.comoSucesso().getObj());
        }

        return dao.listar();
    }

    @Override
    public Resultado<Bebida> getById(int id) {
        if (lista.size() != 0) {
            Bebida ret = lista.stream().filter(categoria -> categoria.getId() == id).findFirst().get();
            return Resultado.sucesso("Bebida encontrada", ret);
        }
        return Resultado.erro("Problema ao buscar bebida!");
    }

}