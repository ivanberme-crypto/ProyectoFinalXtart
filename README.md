# ProyectoFinal CRM — Tienda de Zapas

CRM application for a shoe store — final project for 1º DAM.

## Modules
- **Java** — Maven console app (OOP, JDBC, MySQL, layered architecture)
- **Database** — Oracle (DDL, DML, PL/SQL) + MySQL migration
- **Web** — Vanilla JS, HTML5, CSS3 (BEM), SessionStorage

## Prerequisites
- Java 17+, Maven 3.8+
- MySQL 8.x (for the Java app)
- Oracle Database 21c XE (for PL/SQL scripts)
- Any modern browser (for the web app)

## Setup — Java application
1. Import `Basededatos/sql/dmlyddlfinal.sql` into your MySQL instance
2. Edit `Java/src/main/java/com/javierberme/Utils/DatabaseConnection.java`:
   - Set your MySQL `host`, `port`, `user` and `password`
3. `cd Java && mvn compile exec:java -Dexec.mainClass="com.javierberme.Interfaz.Main"`

## Setup — Oracle database
1. Connect to Oracle as SYSDBA
2. Run `Basededatos/oracle/TiendaZapasDDL.sql`
3. Run `Basededatos/oracle/TiendaZapasDML.sql`
4. Run `Basededatos/oracle/TiendaZapasPLSQL.sql`

## Setup — Web application
1. Open `lenguaje/index.html` in any browser (no server needed)
2. Data is stored in SessionStorage and resets on browser close

## Authors
- **berme_ivan** — bermevfx@gmail.com
- **javiervega120** — javiervegatorres01@gmail.com
