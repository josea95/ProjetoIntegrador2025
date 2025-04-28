-- TABELA USUARIOS --

CREATE TABLE tb_usuarios (
nome VARCHAR(100) NOT NULL, -- Nome do usuário, obrigatório
senha VARCHAR(60) NOT NULL, -- Senha criptografada, tamanho maior para bcrypt, por exemplo
email VARCHAR(100) NOT NULL UNIQUE -- Email deve ser único e obrigatório
);

-- TABELA  FILA PEDIDOS--

create table tb_fila_pedidos
(
    id            int primary key,
    data_pedido   date,
    hora_pedido   time,
    id_usuario    int,
    status_pedido varchar(30),
    senha_pedido  varchar(50),
    observacao    text,
    foreign key (id_usuario) references tb_usuarios (id)
);

--TABELA PRODUTOS PEDIDOS --

create table tb_produtos_pedidos
(
    id          int primary key,
    id_pedidos  int,
    id_produtos int,
    foreign key (id_pedidos) references tb_fila_pedidos (id),
    foreign key (id_produtos) references tb_produtos (id)
);
-- TABELA HISTORICO PEDIDOS --

create table tb_historico_pedidos
(
    id           int primary key,
    nome_cliente varchar(100),
    data_pedido  date,
    hora_pedido  time,
    id_usuario   int,
    valor_pedido float,
    senha_pedido varchar(50),
    observacao   text,
    foreign key (id_usuario) references tb_usuarios (id)
);

-- TABELA PRODUTOS--
CREATE TABLE tb_produtos (
 id SERIAL PRIMARY KEY,
 nome VARCHAR(255),
 descricao TEXT,
 preco NUMERIC(10,2),
 data_criacao DATE,
categoria VARCHAR(255)
);

-- TABELA PRODUTO HISTORICO PEDIDOS --
create table tb_produtos_historico_pedidos
(
    id                  int primary key,
    id_historico_pedido int,
    id_produto          int,
    foreign key (id_historico_pedido) references tb_produtos_historico_pedidos (id),
    foreign key (id_produto) references tb_produtos (id)
);
