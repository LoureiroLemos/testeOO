package ifpr.pgua.eic.tads.contatos;

import ifpr.pgua.eic.tads.contatos.controllers.AddBebidaController;
import ifpr.pgua.eic.tads.contatos.controllers.AddLancheController;
import ifpr.pgua.eic.tads.contatos.controllers.AddPedidoController;
import ifpr.pgua.eic.tads.contatos.controllers.IndexController;
import ifpr.pgua.eic.tads.contatos.controllers.ListBebidaController;
import ifpr.pgua.eic.tads.contatos.controllers.ListLancheController;
import ifpr.pgua.eic.tads.contatos.controllers.ListPedidoController;
import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.JDBCBebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.JDBCLancheDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.JDBCPedidoDao;
import ifpr.pgua.eic.tads.contatos.model.daos.LancheDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.PedidoDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;
import ifpr.pgua.eic.tads.contatos.model.repositories.BebidaRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.ImplBebidaRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.ImplLancheRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.ImplPedidoRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.PedidoRepository;
import ifpr.pgua.eic.tads.contatos.utils.JavalinUtils;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        Javalin app = JavalinUtils.makeApp(8080);

        BebidaDAO bebidaDAO = new JDBCBebidaDAO(FabricaConexoes.getInstance());
        BebidaRepository bebidaRepository = new ImplBebidaRepository(bebidaDAO);

        LancheDAO lancheDAO = new JDBCLancheDAO(FabricaConexoes.getInstance());
        LancheRepository lancheRepository = new ImplLancheRepository(lancheDAO);

        PedidoDAO pedidoDAO = new JDBCPedidoDao(FabricaConexoes.getInstance());
        PedidoRepository pedidoRepository = new ImplPedidoRepository(pedidoDAO, bebidaDAO, lancheDAO);

        IndexController indexController = new IndexController();

        AddBebidaController addBebidaController = new AddBebidaController(bebidaRepository);
        ListBebidaController listBebidaController = new ListBebidaController(bebidaRepository);

        AddLancheController addLancheController = new AddLancheController(lancheRepository);
        ListLancheController listLancheController = new ListLancheController(lancheRepository);

        // AddPedidoController addPedidoController = new
        // AddPedidoController(pedidoRepository);
        ListPedidoController listPedidoController = new ListPedidoController(pedidoRepository);

        app.get("/", indexController.get);

        app.get("/addBebida.peb", addBebidaController.get);
        app.post("/addBebida.peb", addBebidaController.post);

        app.get("/listBebidas.peb", listBebidaController.get);

        app.get("/addLanche.peb", addLancheController.get);
        app.post("/addLanche.peb", addLancheController.post);

        app.get("/listLanches.peb", listLancheController.get);

        // app.get("/addPedido.peb", addPedidoController.get);
        // app.post("/addPedido.peb", addPedidoController.post);
        app.get("/listPedidos.peb", listPedidoController.get);
    }
}
