CREATE TABLE permissao (
    permissao_id INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE usuario_permissao (
    usuario_id INT NOT NULL,
    permissao_id INT NOT NULL,
    PRIMARY KEY (usuario_id, permissao_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    CONSTRAINT fk_permissao FOREIGN KEY (permissao_id) REFERENCES permissao(permissao_id)
);


