package ifpr.pgua.eic.tads.contatos.model.entities;

public class Pedido {
    private int id;
    private Bebida bebida;
    private Lanche lanche;

    public Pedido(int id, Bebida bebida, Lanche lanche) {
        this.id = id;
        this.bebida = bebida;
        this.lanche = lanche;
    }

    public Pedido(Bebida bebida, Lanche lanche) {
        this.bebida = bebida;
        this.lanche = lanche;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }

    public Lanche getLanche() {
        return lanche;
    }

    public void setLanche(Lanche lanche) {
        this.lanche = lanche;
    }

}