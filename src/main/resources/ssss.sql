-- Inserir categorias
INSERT INTO categorias (id, descricao) VALUES (1, 'Alimentação');
INSERT INTO categorias (id, descricao) VALUES (2, 'Transporte');
INSERT INTO categorias (id, descricao) VALUES (3, 'Salário');

-- Inserir usuários
INSERT INTO users (id, username, password, role) VALUES (1, 'admin', '{noop}admin123', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (2, 'usuario', '{noop}usuario123', 'ROLE_USER');

-- Inserir movimentações
INSERT INTO movimentacoes (id, valor, data, descricao, categoria_id, user_id) VALUES
(1, 2500.00, '2025-06-01', 'Salário de Junho', 3, 1),
(2, -150.50, '2025-06-02', 'Compra no supermercado', 1, 2),
(3, -50.00, '2025-06-03', 'Taxi para o trabalho', 2, 2);
