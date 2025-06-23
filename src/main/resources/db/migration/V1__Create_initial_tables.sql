CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE movimentacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(15,2) NOT NULL,
    data DATE NOT NULL,
    descricao VARCHAR(500),
    tipo VARCHAR(20) NOT NULL,
    categoria_id BIGINT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ===== DADOS INICIAIS =====
-- Arquivo: src/main/resources/db/migration/V2__Insert_initial_data.sql

-- Inserir categorias padrão
INSERT INTO categorias (id, descricao) VALUES
(1, 'Alimentação'),
(2, 'Transporte'),
(3, 'Salário'),
(4, 'Lazer'),
(5, 'Saúde'),
(6, 'Educação'),
(7, 'Casa'),
(8, 'Outros');

-- Inserir usuário admin (senha: admin123)
INSERT INTO users (id, username, password, role) VALUES
(1, 'admin', '$2a$10$TIjOPg8sY7iasigw.JCupOSpQ1iVdQ.nY8gqMIJ/yd9ggi/kRIjO6', 'ROLE_ADMIN');

-- Inserir usuário teste (senha: user123)
INSERT INTO users (id, username, password, role) VALUES
(2, 'usuario', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ROLE_USER');

-- Inserir algumas movimentações de exemplo
INSERT INTO movimentacoes (id, valor, data, descricao, tipo, categoria_id, user_id) VALUES
(1, 2500.00, '2025-06-01', 'Salário de Junho', 'ENTRADA', 3, 1),
(2, -150.50, '2025-06-02', 'Compra no supermercado', 'SAIDA', 1, 2),
(3, -50.00, '2025-06-03', 'Taxi para o trabalho', 'SAIDA', 2, 2),
(4, 1000.00, '2025-06-05', 'Freelance', 'ENTRADA', 8, 2),
(5, -200.00, '2025-06-07', 'Conta de luz', 'SAIDA', 7, 1);
