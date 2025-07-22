INSERT INTO permissao (permissao_id, nome)
VALUES (1, 'BASICO')
ON CONFLICT (permissao_id) DO NOTHING;

INSERT INTO permissao (permissao_id, nome)
VALUES (2, 'ADMIN')
ON CONFLICT (permissao_id) DO NOTHING;
