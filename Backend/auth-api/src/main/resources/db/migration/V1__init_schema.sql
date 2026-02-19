CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,

                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,

                       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                       failed_attempts INT NOT NULL DEFAULT 0,
                       blocked_until DATETIME NULL,

                       last_login_at DATETIME NULL,
                       last_login_ip VARCHAR(45) NULL,

                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_blocked_until ON users(blocked_until);
