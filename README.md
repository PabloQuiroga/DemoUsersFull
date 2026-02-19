# Auth API -- Quarkus + MySQL (Docker)

## 📌 Descripción

API REST para gestión de usuarios con:

-   Registro
-   Login con sesión tradicional (cookies)
-   Bloqueo temporal por intentos fallidos
-   Auditoría básica
-   Arquitectura escalable
-   Dockerizado para entorno local

Frontend consumidor: App Android nativa.

------------------------------------------------------------------------

## 🏗 Stack Tecnológico

-   Java + Quarkus (JVM)
-   Hibernate ORM + Panache
-   Flyway (migraciones)
-   MySQL
-   Docker + Docker Compose
-   BCrypt para hashing de passwords

------------------------------------------------------------------------

## 🔐 Autenticación

-   Login con:
    -   `email + password`
    -   `username + password`
-   Sesión tradicional con cookie
-   Expiración de sesión: 30 minutos
-   Sin JWT (por ahora)

------------------------------------------------------------------------

## 🔒 Seguridad

-   Passwords hasheadas con BCrypt
-   Máximo 3 intentos fallidos
-   Bloqueo temporal: 5 minutos
-   Sin roles
-   Usuario activo inmediatamente al registrarse
-   Sin verificación por email (por ahora)

------------------------------------------------------------------------

## 🗄 Modelo de Usuario (Escalable)

### Campos principales

-   `id`
-   `username` (único, obligatorio)
-   `email` (único)
-   `password_hash`
-   `status` (ACTIVE, BLOCKED, etc.)
-   `failed_attempts`
-   `blocked_until`
-   `last_login_at`
-   `last_login_ip`
-   `created_at`
-   `updated_at`

------------------------------------------------------------------------

## 🧱 Arquitectura

Estructura en capas:

    resource/
    service/
    repository/
    domain/
    dto/
    mapper/
    security/

Separación clara de responsabilidades.

------------------------------------------------------------------------

## 🔄 Flujo de Login

1.  Usuario envía username/email + password
2.  Se verifica:
    -   Usuario existe
    -   No esté bloqueado
3.  Se valida password (BCrypt)
4.  Si falla:
    -   Incrementa `failed_attempts`
    -   Si llega a 3 → `status = BLOCKED`
    -   `blocked_until = now + 5 min`
5.  Si éxito:
    -   Reset `failed_attempts`
    -   Actualiza `last_login_at`
    -   Crea sesión
    -   Devuelve cookie HTTP

------------------------------------------------------------------------

## 📦 Endpoints previstos

    POST   /auth/register
    POST   /auth/login
    POST   /auth/logout

    GET    /users/{id}
    PUT    /users/{id}
    DELETE /users/{id}

------------------------------------------------------------------------

## 🐳 Docker (Entorno Local)

Servicios:

-   mysql
-   backend (quarkus)

Sin herramientas adicionales. Acceso a base de datos mediante cliente
SQL externo.

------------------------------------------------------------------------

## 🧪 Entorno

-   Solo desarrollo local
-   No optimizado para producción todavía
-   Preparado para futura migración a:
    -   JWT
    -   Refresh tokens
    -   Confirmación por email
    -   Roles
    -   Desbloqueo por email

------------------------------------------------------------------------

## 🚀 Roadmap Futuro

-   Migración a JWT
-   Confirmación de email
-   Refresh automático de sesión
-   Sistema de roles
-   Desbloqueo por correo
-   Hardening de seguridad
