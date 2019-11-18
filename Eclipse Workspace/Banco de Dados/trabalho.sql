CREATE TABLE Users (
	cod SERIAL PRIMARY KEY,
	codapi VARCHAR(80),
	api VARCHAR (20),
	nome VARCHAR (80),
	image TEXT
);

CREATE TABLE Pesquisa (
	pesquisa_cod SERIAL PRIMARY KEY,  
	user_cod integer,
	cep_1 VARCHAR (8),
	rua_1 VARCHAR (255),
	cidade_1 VARCHAR (255),
	estado_1 VARCHAR (255),
	cep_2 VARCHAR (8),
	rua_2 VARCHAR (255),
	cidade_2 VARCHAR (255),
	estado_2 VARCHAR (255),
	FOREIGN KEY (user_cod) REFERENCES Users (cod)
);
