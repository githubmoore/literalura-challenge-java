# LiterAlura - Catálogo Literario con Java y Spring Boot

¡Bienvenido al desafío **LiterAlura**! Este proyecto consiste en crear una aplicación que interactúe con la API Gutendex para construir un catálogo literario, incorporando funcionalidades avanzadas como consultas personalizadas, análisis de datos y persistencia en bases de datos. 🚀

## 🛠️ Requisitos

Asegúrate de contar con los siguientes programas y versiones:

- **Java JDK**: 17+
- **Maven**: 4+
- **Spring Boot**: 3.2.3
- **PostgreSQL**: 16+
- **IDE recomendado**: IntelliJ IDEA (opcional)

## 📚 Funcionalidades Principales

1. **Consulta de Libros y Autores**
   - Búsqueda de libros por título utilizando la API Gutendex.
   - Visualización de detalles como título, autor, idioma y número de descargas.
   - Listado de autores vivos en un año específico.

2. **Persistencia de Datos**
   - Guardar libros y autores en una base de datos PostgreSQL.
   - Relación entre libros y autores a través de identificadores únicos (IDs).

3. **Interacción con el Usuario**
   - Menú interactivo en consola con opciones de búsqueda y consulta.
   - Validación y manejo de errores en las entradas del usuario.

4. **Estadísticas y Listados**
   - Mostrar la cantidad de libros por idioma.
   - Listar los 10 libros más descargados.

## 🌐 API Utilizada

**Gutendex API**
Una rica fuente de datos sobre más de 70,000 libros gratuitos. [Documentación oficial](https://gutendex.com/).

Repositorio de la API: [GitHub - Gutendex](https://github.com/garethbjohnson/gutendex)

## 🚀 Configuración del Proyecto

### 1. Inicialización del Proyecto
- Configura tu entorno en [Spring Initializr](https://start.spring.io/) con:
  - Java 17
  - Maven
  - Spring Boot 3.2.3
  - Dependencias:
    - **Spring Data JPA**
    - **PostgreSQL Driver**

### 2. Base de Datos
- Crea una base de datos PostgreSQL.
- Configura el acceso en `application.properties`.

### 3. Agregar Dependencias
Incluye las siguientes dependencias en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.0</version>
</dependency>

### 📝 Ejecución del Proyecto

### Clona el repositorio:

```bash
git clone <url_del_repositorio>

### Compila y ejecuta el proyecto:

mvn spring-boot:run

Sigue las instrucciones del menú interactivo en la consola.

🎯 Funcionalidades Opcionales
Generar estadísticas detalladas de los libros.
Consultar autores por nombre.
Explorar otras estadísticas basadas en años de nacimiento o fallecimiento.
📖 Recursos Útiles
Documentación de Spring Boot
Documentación de PostgreSQL
¡Explora, aprende y diviértete construyendo este emocionante proyecto! 🌟
