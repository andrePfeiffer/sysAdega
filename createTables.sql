CREATE TABLE Cliente (
idCliente integer PRIMARY KEY,
nomeCliente text NOT NULL,
cpf varchar(12) UNIQUE NOT NULL,
cep varchar(9) NOT NULL,
endereco varchar(255) NOT NULL,
complemento varchar(255) NOT NULL,
numero varchar(10) NOT NULL,
bairro varchar(100) NOT NULL,
cidade varchar(100) NOT NULL,
estado varchar(2) NOT NULL,
pais varchar(100) NOT NULL
);

CREATE TABLE ItemPedido (
id integer PRIMARY KEY,
idPedido integer NOT NULL,
idVinho integer NOT NULL,
qtdVinho integer NOT NULL,
valorTotalItem number DEFAULT 0.0,
FOREIGN KEY (idPedido) REFERENCES Pedido (idPedido),
FOREIGN KEY (idVinho) REFERENCES Vinho (idVinho)
ON DELETE CASCADE
);

CREATE TABLE Pedido (
idPedido integer PRIMARY KEY,
idCliente integer NOT NULL,
dtPedido date NOT NULL,
valorTotal number default 0,
dtEncerramento date,
estadoPedido text,
FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
ON DELETE CASCADE
);

CREATE TABLE Vinho (
idVinho integer PRIMARY KEY,
nomeVinho text NOT NULL,
anoVinho integer NOT NULL,
corVinho text NOT NULL,
precoVinho number NOT NULL,
qtdEstoque integer DEFAULT 0
);