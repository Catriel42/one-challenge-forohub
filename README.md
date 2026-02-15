# Documentación - Proyecto Spring Boot

## Requisitos Previos

- Java 25
- Maven instalado
- Base de datos PostgreSQL (Supabase)

## Configuración Inicial

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd one-challenge-forohub
```

### 2. Configurar Variables de Entorno

Crear archivo `.env` en la raíz del proyecto:

```bash
SPRING_PROFILES_ACTIVE=dev
DEV_DB_URL=jdbc:postgresql://tu-host.supabase.com:5432/postgres
DEV_DB_USERNAME=tu-usuario
DEV_DB_PASSWORD=tu-password
PROD_DB_URL=jdbc:postgresql://tu-host-prod.supabase.com:5432/postgres
PROD_DB_USERNAME=tu-usuario-prod
PROD_DB_PASSWORD=tu-password-prod
```

**Importante:** Nunca subir el archivo `.env` a Git.

### 3. Cargar Variables de Entorno

```bash
export $(cat .env | xargs)
```

### 4. Verificar Variables

```bash
echo $SPRING_PROFILES_ACTIVE
echo $DEV_DB_URL
```

## Ejecutar el Proyecto

### Modo Desarrollo

```bash
./mvnw spring-boot:run
```

### Modo Producción

```bash
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/catodev/onechallengeforum/
│   │       └── OneChallengeForumApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── db/
│           └── migration/
│               └── V1__create_tables.sql
```

## Profiles

El proyecto usa Spring Profiles para separar configuraciones:

- **dev**: Desarrollo local
- **prod**: Producción

Activar profile:
```bash
export SPRING_PROFILES_ACTIVE=dev
```

## Migraciones de Base de Datos

Flyway ejecuta automáticamente las migraciones al iniciar la aplicación.

Ubicación de scripts: `src/main/resources/db/migration/`

Nomenclatura: `V{número}__{descripción}.sql`

Ejemplo: `V1__create_tables.sql`

## Solución de Problemas

### Error: "Failed to determine a suitable driver class"

Verificar que el driver de PostgreSQL esté en `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Error: "relation already exists"

Las tablas ya existen en la base de datos. Opciones:

1. Limpiar la base de datos manualmente
2. Usar `CREATE TABLE IF NOT EXISTS` en migraciones

### Variables de entorno no se cargan

```bash
# Recargar variables
export $(cat .env | xargs)

# Verificar
echo $SPRING_PROFILES_ACTIVE
```

## Endpoints

La aplicación corre en: `http://localhost:8080`

## Detener la Aplicación

Presionar `Ctrl + C` en la terminal donde corre la aplicación.