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
itens_pedido text,
nome_cliente varchar(100),
data_pedido date,
hora_pedido time,
id_usuario int,
status_pedido varchar(30),
senha_pedido varchar(50),
observacao text,
foreign key (id_usuario) references tb_usuarios(id)
);

-- TABELA HISTORICO PEDIDOS --
create table historico_pedidos(
id int primary key,
itens_pedido text,
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