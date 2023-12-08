/* script de criação do BD */

Create table oo_bebidas(
    id_bebida int not null auto_increment,
    nome_bebida varchar(50) not null,
    valor_bebida float not null,
    primary key(id_bebida)
);

Create table oo_lanches(
    id_lanche int not null auto_increment,
    nome_lanche varchar(50) not null,
    valor_lanche float not null,
    primary key(id_lanche)
);

Create table oo_pedidos(
    id_pedido int not null auto_increment primary key,
    id_bebida int not null,
    id_lanche int not null,
    foreign key(id_bebida) references oo_bebidas(id_bebida),
    foreign key(id_lanche) references oo_lanches(id_lanche)
);

/* script para retornar pedidos com id_bebida e id_lanches iguais nas tabelas de pedidos e bebidas/lanches */

SELECT p.id_pedido, b.nome_bebida, l.nome_lanche FROM oo_pedidos p inner join oo_bebidas b inner join  oo_lanches l on p.id_bebida = b.id_bebida AND p.id_lanche = l.id_lanche

alter table oo_pedidos add column observacao varchar(255);