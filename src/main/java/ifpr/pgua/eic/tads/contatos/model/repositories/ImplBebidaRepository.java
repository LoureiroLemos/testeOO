package ifpr.pgua.eic.tads.contatos.model.repositories;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;

public class ImplBebidaRepository implements BebidaRepository {

    private BebidaDAO dao;

    public ImplBebidaRepository(BebidaDAO dao) {
        this.dao = dao;
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
        return dao.listar();
    }

}