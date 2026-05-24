# ProyectoFinal CRM - Tienda de Zapas

Aplicacion CRM para una tienda de zapatillas - proyecto final de 1o DAM.

## Modulos

- **Java** - Aplicacion de consola Maven (POO, JDBC, MySQL, arquitectura por capas)
- **Base de datos** - Oracle (DDL, DML, PL/SQL) + migracion MySQL
- **Web** - JavaScript Vanilla, HTML5, CSS3 (BEM), SessionStorage

## Requisitos

- Java 21+, Maven 3.8+
- MySQL 8.x (para la aplicacion Java)
- Oracle Database 21c XE (para los scripts PL/SQL)
- Cualquier navegador moderno (para la aplicacion web)

## Configuracion - Aplicacion Java

1. Importa `Basededatos/sql/dmlyddlfinal.sql` en tu instancia MySQL
2. Edita `Java/src/main/java/com/javierberme/Utils/DatabaseConnection.java`:
   - Pon tu `host`, `puerto`, `usuario` y `contrasena` de MySQL
3. `cd Java && mvn compile exec:java -Dexec.mainClass="com.javierberme.Interfaz.Main"`

## Configuracion - Base de datos Oracle

1. Conectate a Oracle como SYSDBA
2. Ejecuta `Basededatos/oracle/TiendaZapasDDL.sql`
3. Ejecuta `Basededatos/oracle/TiendaZapasDML.sql`
4. Ejecuta `Basededatos/oracle/TiendaZapasPLSQL.sql`

## Configuracion - Aplicacion web

1. Abre `lenguaje/index.html` en cualquier navegador (no necesita servidor)
2. Los datos se guardan en SessionStorage y se borran al cerrar el navegador

## Autores

- **berme_ivan** - bermevfx@gmail.com
- **javiervega120** - javiervegatorres01@gmail.com
