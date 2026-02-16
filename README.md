# One Challenge Forum - API REST

![Java](https://img.shields.io/badge/Java_25-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_4-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-%23CC0200.svg?style=for-the-badge&logo=flyway&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)

Este proyecto es una solucion backend para el desafio "Foro Hub" de Oracle Next Education (ONE) y Alura. Consiste en una API RESTful desarrollada con **Spring Boot** para gestionar topicos de discusion, implementando las mejores practicas de desarrollo, persistencia de datos y seguridad.

## Tecnologias

*   **Java 25** (Compatible con versiones LTS recientes como 21)
*   **Spring Boot 4**
*   **Spring Data JPA** (Hibernate)
*   **PostgreSQL** (Base de datos)
*   **Flyway** (Migraciones de base de datos)
*   **Spring Security** (Autenticacion y Autorizacion)
*   **Lombok** (Reduccion de boilerplate)
*   **MapStruct** (Mapeo eficiente de Entidades <-> DTOs)
*   **Validation** (Bean Validation estandar)

## Configuracion del Entorno

Para ejecutar la aplicacion, es necesario configurar las siguientes **variables de entorno** en tu sistema o IDE. Estas variables son utilizadas por el perfil de desarrollo (`dev`).

| Variable | Descripcion | Ejemplo |
| :--- | :--- | :--- |
| `DEV_DB_URL` | URL de conexion a PostgreSQL | `jdbc:postgresql://localhost:5432/foro_hub` |
| `DEV_DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DEV_DB_PASSWORD` | Contrasena del usuario | `admin123` |

> **Nota:** La aplicacion utiliza Flyway, por lo que las tablas se crearan automaticamente al iniciar si la base de datos existe.

## Como Ejecutar

### Usando Maven
Puedes ejecutar la aplicacion directamente desde la terminal con Maven:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Usando JAR
Si prefieres empaquetar y ejecutar:

```bash
./mvnw clean package
java -jar target/one-challenge-forum-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

## Endpoints Principales

La API expone los siguientes recursos bajo el path base `/topics` (o configurado en properties).

| Metodo | Endpoint | Descripcion |
| :--- | :--- | :--- |
| **POST** | `/topics` | **Crear Topico**. Requiere titulo, mensaje, autor y curso. Valida duplicados. |
| **GET** | `/topics` | **Listar Topicos**. Retorna lista paginada de topicos activos. Filtra los eliminados. |
| **GET** | `/topics/{id}` | **Detalle Topico**. Obtiene la informacion completa de un topico por su ID. |
| **PUT** | `/topics/{id}` | **Actualizar Topico**. Actualiza titulo o mensaje. Transaccional. |
| **DELETE** | `/topics/{id}` | **Eliminar Topico (Logico)**. Cambia el estado a `DELETED`. |

## Caracteristicas Clave

*   **Soft Delete**: Los registros no se borran fisicamente de la base de datos; cambian su estado a `DELETED` para mantener integridad referencial y auditoria.
*   **Validaciones**:
    *   **Formato**: `DTO` con `@NotBlank`, `@NotNull`.
    *   **Negocio**: Reglas de unicidad (no duplicados) validadas en la capa de Servicio.
*   **Transaccionalidad**: Uso de `@Transactional` para garantizar atomicidad en operaciones de modificacion (`create`, `update`, `delete`).
*   **Mappers**: Conversion limpia entre DTOs y Entidades usando MapStruct.

## Contribucion

Este proyecto sigue la convencion de **Conventional Commits** para el historial de cambios:
*   `feat`: Nueva funcionalidad.
*   `fix`: Correccion de errores.
*   `refactor`: Cambios de codigo que no alteran la funcionalidad.
*   `docs`: Cambios en documentacion.