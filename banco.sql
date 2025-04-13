-- TABELA USUARIOS --

create table tb_usuarios(
id int primary key,
nome varchar(100),
senha varchar(12),
email varchar(100)
);

-- TABELA  FILA PEDIDOS--

create table tb_fila_pedidos(
id int primary key,
data_pedido date,
hora_pedido time,
id_usuario int,
status_pedido varchar(30),
senha_pedido varchar(50),
observacao text,
foreign key (id_usuario) references tb_usuarios(id)
);

--TABELA PRODUTOS PEDIDOS --

create table tb_produtos_pedidos(
    id int primary key,
    id_pedidos  int,
    id_produtos int,
    foreign key (id_pedidos) references tb_fila_pedidos(id),
    foreign key (id_produtos) references tb_produtos(id)
);
-- TABELA HISTORICO PEDIDOS --

create table tb_historico_pedidos(
id int primary key,
nome_cliente varchar(100),
data_pedido date,
hora_pedido time,
id_usuario int,
valor_pedido float,
senha_pedido varchar(50),
observacao text,
foreign key (id_usuario) references tb_usuarios(id)
);

-- TABELA PRODUTOS--
create table tb_produtos(
id int primary key,
nome varchar(100),
descricao text,
preco float,
data_criacao date,
data_atualizacao date
);

-- TABELA PRODUTO HISTORICO PEDIDOS --
create table tb_produtos_historico_pedidos(
id int primary key,
id_historico_pedido int,
id_produto int,
foreign key (id_historico_pedido) references tb_produtos_historico_pedidos(id),
foreign key (id_produto) references tb_produtos(id)
);