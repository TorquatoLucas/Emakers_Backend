-- Criação da tabela Pessoa
CREATE TABLE usuario (
    usuario_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    cep CHAR(9),
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

-- Criação da tabela Livro
CREATE TABLE livro (
    livro_id INT SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    autor VARCHAR(100),
    data_lancamento DATE
);

-- Criação da tabela Emprestimo (tabela de junção)
CREATE TABLE emprestimo (
    emprestimo_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    usuario_id INT NOT NULL,
    livro_id INT NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    CONSTRAINT fk_livro FOREIGN KEY (livro_id) REFERENCES livro(livro_id)
);



