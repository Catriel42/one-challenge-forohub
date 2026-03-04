# One Challenge Forum

![Java](https://img.shields.io/badge/Java_25-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_4-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-%23CC0200.svg?style=for-the-badge&logo=flyway&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Este proyecto es una solución backend para el desafío "Foro Hub" de Oracle Next Education (ONE) y Alura. Consiste en una API RESTful desarrollada con **Spring Boot** para gestionar tópicos de discusión, implementando las mejores prácticas de desarrollo, persistencia de datos y **seguridad robusta**.

## Tecnologías

*   **Java 25** (Compatible con versiones recientes)
*   **Spring Boot 4**
*   **Spring Security 6+** (Autenticación y Autorización)
*   **JWT (JSON Web Tokens)** (Seguridad Stateless)
*   **Spring Data JPA** (Hibernate)
*   **PostgreSQL** (Infrasctructura de Datos)
*   **Flyway** (Migraciones de base de datos)
*   **Lombok** (Reducción de boilerplate)
*   **MapStruct** (Mapeo eficiente de Entidades <-> DTOs)

## Configuración del Entorno y Ejecución

Para ejecutar la aplicación, primero define las siguientes variables de entorno (por ejemplo, en un archivo `.env` en la raíz del proyecto):

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `DEV_DB_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5432/foro_hub` |
| `DEV_DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DEV_DB_PASSWORD` | Contraseña del usuario | `admin123` |
| `JWT_SECRET` | Clave secreta para firmar tokens | `mi_super_secreto_123` |

### Cómo correr el proyecto (Linux/macOS)

Si usas un entorno basado en Unix y tienes tus variables configuradas en un archivo `.env`, puedes usar este atajo (joyita) para exportar las variables e iniciar el servidor de Spring Boot en un solo comando:

```bash
export $(cat .env | xargs) && ./mvnw spring-boot:run
```

Esto cargará la base de datos, ejecutará las migraciones de Flyway y levantará la API en el puerto `8080`.

## Endpoints Principales

### Autenticación (Públicos)
| Método | Endpoint | Descripción | Body Requerido |
| :--- | :--- | :--- | :--- |
| **POST** | `/auth/register` | **Registrar Usuario**. Crea un nuevo usuario con rol `ROLE_STUDENT`. | `{ "name": "...", "email": "...", "password": "..." }` |
| **POST** | `/auth/login` | **Iniciar Sesión**. Devuelve un JWT válido por 2 horas. | `{ "email": "...", "password": "..." }` |

### Tópicos (Protegidos - Requieren Bearer Token)
Todo request a estos endpoints debe incluir el header: `Authorization: Bearer <tu_token>`

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/topics` | **Crear Tópico**. Requiere título, mensaje, autor y curso. Valida duplicados. |
| **GET** | `/topics` | **Listar Tópicos**. Retorna lista paginada de tópicos activos. |
| **GET** | `/topics/{id}` | **Detalle Tópico**. Obtiene la información completa de un tópico por su ID. |
| **PUT** | `/topics/{id}` | **Actualizar Tópico**. Actualiza título o mensaje de forma transaccional. |
| **DELETE** | `/topics/{id}` | **Eliminar Tópico (Lógico)**. Cambia el estado a `DELETED`. |

## Documentación de la API (Swagger UI)

Este proyecto integra **Springdoc OpenAPI 3** para la generación automática de la documentación interactiva de la API. Esta interfaz reemplaza la necesidad de usar herramientas externas como Postman para las pruebas manuales.

Para acceder a la documentación interactiva:
1. Inicia la aplicación.
2. Navega en tu navegador a: `http://localhost:8080/api/v1/swagger-ui.html`

### Cómo probar Endpoints Protegidos en Swagger
1. Ve a la sección **Autenticación** y ejecuta el endpoint `POST /auth/login` con tus credenciales.
2. Copia el `token` (JWT) de la respuesta.
3. Desplázate hacia arriba y haz clic en el botón verde **Authorize**.
4. Pega el token y haz clic en "Authorize".
5. ¡Listo! Ahora todos los endpoints que requieren autenticación enviarán automáticamente el header `Authorization: Bearer <token>`.

## Arquitectura y Diseño (Diagrama de Clases)

El sistema sigue una arquitectura en capas con separación de responsabilidades para la seguridad.

```mermaid
classDiagram
    class AuthController {
        +register(RegisterUserDto)
        +login(LoginUserDto)
    }

    class AuthenticationService {
        +register(RegisterUserDto)
        +login(LoginUserDto): JwtTokenDto
    }

    class UserDetailsServiceImpl {
        +loadUserByUsername(String): UserDetails
    }

    class TokenService {
        +generateToken(User): String
        +getSubject(String): String
    }

    class SecurityFilter {
        +doFilterInternal(Request, Response, Chain)
    }

    class UserRepository {
        +findByEmail(String): Optional<User>
    }

    class User {
        -Long id
        -String email
        -String password
        -Profile profile
        +getAuthorities()
    }

    AuthController --> AuthenticationService : uses
    AuthenticationService --> UserDetailsServiceImpl : decoupled from
    AuthenticationService --> TokenService : uses
    AuthenticationService --> UserRepository : uses
    UserDetailsServiceImpl --> UserRepository : uses
    SecurityFilter --> TokenService : validates token
    SecurityFilter --> UserRepository : loads user
    User ..|> UserDetails : implements
```

## Seguridad Implementada

1.  **Stateless Authentication**: No se usan sesiones de servidor. Cada petición es validada independientemente vía JWT.
2.  **Password Encryption**: Todas las contraseñas se almacenan hasheadas con **BCrypt**.
3.  **Role Based Access Control (RBAC)**: Preparado para manejar roles (`ROLE_ADMIN`, `ROLE_STUDENT`) asociados a cada `User` mediante la entidad `Profile`.
4.  **Segregación de Interfaces**:
    *   `AuthenticationService`: Maneja la lógica de negocio (login/registro).
    *   `UserDetailsServiceImpl`: Maneja la carga técnica de usuarios para Spring Security, rompiendo dependencias circulares.

## Contribución

Este proyecto sigue la convención de **Conventional Commits**:
*   `feat`: Nueva funcionalidad.
*   `fix`: Corrección de errores.
*   `refactor`: Cambios de código que no alteran la funcionalidad.
*   `docs`: Cambios en documentación.
*   `build`: Cambios en dependencias o scripts de construcción.