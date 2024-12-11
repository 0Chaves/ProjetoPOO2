CREATE DATABASE "projeto";

CREATE TABLE fornecedores (
    "id" serial not null,
    "nome" varchar(50) not null,
    "cnpj"char(18) UNIQUE not null,
    "email" varchar(50) UNIQUE not null,
    "telefone" varchar(15),
    CONSTRAINT "fornecedores_PK" PRIMARY KEY ("id")
);

CREATE TABLE produtos (
    "id" serial not null,
    "descricao" text not null,
    "pregao" varchar(8) not null,
    "valor_unitario" numeric(10,2) not null,
    "qtd_max" int not null,
    "qtd_solicitada" int not null,
    "id_fornecedor" int not null,
    CONSTRAINT "produtos_PK" PRIMARY KEY ("id"),
    CONSTRAINT "fornecedor_FK" FOREIGN KEY ("id_fornecedor")
    REFERENCES "fornecedores"("id")
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
