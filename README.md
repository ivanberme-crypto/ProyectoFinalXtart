# ProyectoFinal CRM — Tienda Zapas (ZAPNN)

Aplicación CRM completa para una tienda de zapatillas, desarrollada como _proyecto final de 1º DAM_. El sistema gestiona clientes, productos, ventas, usuarios y detalles de venta a través de tres módulos independientes pero conectados a la misma lógica de negocio.

---

## Estructura del proyecto

ProyectoFinalXtart/
├── Java/ → Aplicación de consola (Maven + JDBC + MySQL)
│ └── src/main/java/com/javierberme/
│ ├── Controllers/ → Capa de controladores
│ ├── Entities/ → Entidades / modelos de datos
│ ├── Interfaz/ → Menús de consola (Main, Menu, submenús)
│ ├── Repositories/ → Acceso a datos (interfaces + implementaciones JDBC)
│ ├── Services/ → Lógica de negocio
│ └── Utils/ → DatabaseConnection, ExportadorCSV
│
├── Basededatos/
│ ├── sql/
│ │ └── dmlyddlfinal.sql → DDL + DML para MySQL (usado por Java)
│ └── oracle/
│ ├── TiendaZapasDDL.sql → DDL para Oracle
│ ├── TiendaZapasDML.sql → Datos iniciales para Oracle
│ └── TiendaZapasPLSQL.sql → Cursores, procedimientos y funciones PL/SQL
│
└── lenguaje/
├── index.html → SPA del CRM web
├── style.css → Estilos con metodología BEM
└── app.js → Lógica completa en JavaScript Vanilla

---

## Autores

- _berme_ivan_ — bermevfx@gmail.com
- _javiervega120_ — javiervegatorres01@gmail.com

---

## Módulos del proyecto

- _Java_ — Java 21, Maven, JDBC, MySQL — Asignatura: Programación
- _Base de datos_ — MySQL 8 + Oracle 21c XE + PL/SQL — Asignatura: Bases de Datos
- _Web_ — HTML5, CSS3 (BEM), JavaScript ES6+ — Asignatura: Lenguajes de Marcas

---

---

# MÓDULO JAVA — Aplicación de consola

## Descripción

Aplicación de consola desarrollada en _Java 21_ con arquitectura en capas (Controller → Service → Repository → Entity). Se conecta a una base de datos _MySQL_ mediante _JDBC_ y permite gestionar de forma interactiva clientes, productos, ventas, usuarios y detalles de venta. También incluye exportación a CSV.

## Arquitectura por capas

Interfaz (menús de consola)
↓
Controllers (coordinan la entrada/salida)
↓
Services (lógica de negocio, validaciones)
↓
Repositories (interfaces + implementaciones JDBC)
↓
MySQL Database (via JDBC)

## Entidades del modelo

- Cliente — idCliente, nombre, email, telefono, direccion
- Producto — idProducto, nombre, marca, modelo, talla, color, precio, stock, categoria
- Venta — idVenta, idCliente, idUsuario, fecha, estado, total
- DetalleVenta — idDetalle, idVenta, idProducto, cantidad, precioUnitario
- Usuario — idUsuario, nombre, email, rol, password_hash

## Requisitos previos

- _Java 21_ o superior
- _Maven 3.8_ o superior
- _MySQL 8.x_ en ejecución
- _MySQL Workbench_ (recomendado para preparar la base de datos)
- _IntelliJ IDEA_ (o cualquier IDE con soporte Maven)

## Configuración de MySQL Workbench

> Esta configuración es _crítica_: los valores de MySQL Workbench deben coincidir exactamente con los definidos en DatabaseConnection.java.

### Paso 1 — Crear la conexión en MySQL Workbench

1. Abre _MySQL Workbench_.
2. En la pantalla de inicio, haz clic en el símbolo _+_ junto a "MySQL Connections".
3. Rellena los campos con estos valores (son los mismos que usa Java):
   - _Connection Name:_ tienda_zapas (o el nombre que prefieras)
   - _Hostname:_ localhost
   - _Port:_ 3306
   - _Username:_ root
   - _Password:_ 1234
   - _Default Schema:_ tienda_zapas (opcional)
4. Pulsa _"Test Connection"_ para verificar que funciona.
5. Pulsa _OK_ para guardar la conexión.

### Paso 2 — Ejecutar el script SQL

1. Una vez conectado, abre el archivo Basededatos/sql/dmlyddlfinal.sql desde Workbench:
   - Menú _File → Open SQL Script_ → selecciona el archivo.
2. Pulsa el rayo (_Execute All_) o Ctrl+Shift+Enter para ejecutar todo el script.
3. Esto creará la base de datos tienda_zapas, todas las tablas y cargará los datos de ejemplo.
4. Verifica en el panel izquierdo que aparece el schema tienda_zapas con las tablas: usuarios, clientes, productos, ventas, detalle_venta.

### Paso 3 — Verificar que Java usa la misma configuración

El archivo de conexión Java es Java/src/main/java/com/javierberme/Utils/DatabaseConnection.java. Su contenido actual es:

java
private static final String URL =
"jdbc:mysql://localhost:3306/tienda_zapas" +
"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

private static final String USER = "root";
private static final String PASSWORD = "1234";

_Si tu MySQL Workbench usa configuración diferente_, edita DatabaseConnection.java para que coincida:

- Host distinto a localhost → reemplaza localhost en la URL por tu host
- Puerto distinto a 3306 → reemplaza 3306 por tu puerto
- Usuario distinto a root → cambia USER = "root" por tu usuario
- Contraseña distinta a 1234 → cambia PASSWORD = "1234" por tu contraseña

_Regla general:_ lo que ves en la pantalla de conexión de MySQL Workbench debe ser idéntico a los valores de DatabaseConnection.java. Si Workbench se conecta bien, Java también lo hará.

## Instalación y ejecución

### Opción A — Desde IntelliJ IDEA (recomendado)

1. Abre IntelliJ IDEA.
2. _File → Open_ → selecciona la carpeta Java/.
3. IntelliJ detectará el pom.xml automáticamente y descargará las dependencias (MySQL Connector).
4. Ve a src/main/java/com/javierberme/Interfaz/Main.java.
5. Haz clic derecho → _Run 'Main'_.

### Opción B — Desde la terminal con Maven

bash
cd Java/
mvn compile
mvn exec:java -Dexec.mainClass="com.javierberme.Interfaz.Main"

## Dependencia Maven (pom.xml)

El proyecto usa únicamente el conector oficial de MySQL y está configurado para Java 21:

xml
<dependency>
<groupId>com.mysql</groupId>
<artifactId>mysql-connector-j</artifactId>
<version>8.3.0</version>
</dependency>

## Menú de la aplicación

Al ejecutar, aparece el menú principal:

╔══════════════════════════╗
║ TIENDA ZAPNN ║
╠══════════════════════════╣
║ 1. Gestión de clientes ║
║ 2. Gestión de productos ║
║ 3. Gestión de ventas ║
║ 4. Gestión de usuarios ║
║ 5. Gestión de detalles ║
║ 0. Salir ║
╚══════════════════════════╝

Cada opción abre un submenú con operaciones _CRUD completas_ (listar, buscar por ID, crear, actualizar, eliminar). El módulo de clientes también incluye exportación a CSV a través de ExportadorCSV.java.

## Descripción de los paquetes

- Entities — POJOs con getters/setters que representan las tablas de la BD
- Repositories — Interfaz + implementación con consultas SQL mediante PreparedStatement
- Services — Orquesta los repositorios y aplica reglas de negocio
- Controllers — Recibe input del usuario desde los menús y llama a los servicios
- Interfaz — Main, Menu y cinco submenús (uno por entidad) con Scanner
- Utils — DatabaseConnection (singleton de conexión) y ExportadorCSV

---

---

# MÓDULO BASE DE DATOS

El proyecto incluye dos variantes de base de datos: _MySQL_ (usada por la aplicación Java) y _Oracle_ (usada para la asignatura de Bases de Datos con PL/SQL).

---

## MySQL — Basededatos/sql/dmlyddlfinal.sql

### Descripción

Script único que incluye DDL (creación de tablas) y DML (inserción, actualización y eliminación de datos de ejemplo) para _MySQL 8.x_.

### Requisitos

- _MySQL 8.x_ instalado y en ejecución
- _MySQL Workbench_ (recomendado) o cliente mysql desde terminal

### Cómo ejecutarlo en MySQL Workbench

1. Conectarse al servidor MySQL (ver configuración en la sección Java).
2. Abrir el archivo: _File → Open SQL Script → dmlyddlfinal.sql_
3. Ejecutar con el rayo o Ctrl+Shift+Enter.

### Cómo ejecutarlo desde la terminal

bash
mysql -u root -p1234 < Basededatos/sql/dmlyddlfinal.sql

### Tablas creadas

_usuarios_ — id_usuario, nombre, email (único), rol, password_hash

_clientes_ — id_cliente, nombre, email, telefono, direccion

_productos_ — id_producto, nombre, marca, modelo, talla, color, precio, stock, descripcion, categoria

_ventas_ — id_venta, id_cliente (FK), id_usuario (FK), fecha, estado, total. Si se elimina un cliente, sus ventas se eliminan en cascada. No se puede eliminar un usuario si tiene ventas asociadas.

_detalle_venta_ — id_detalle, id_venta (FK), id_producto (FK), cantidad, precio_unitario

---

## Oracle — Basededatos/oracle/

### Descripción

Scripts equivalentes adaptados para _Oracle Database 21c XE, usando la sintaxis Oracle (NUMBER, VARCHAR2, SYSDATE, IDENTITY, etc.). Incluye además un script extenso de \*\*PL/SQL_ con cursores y bloques anónimos.

### Requisitos

- _Oracle Database 21c XE_ instalado
- *SQL*Plus* o *Oracle SQL Developer\*

### Cómo ejecutarlo

En SQL\*Plus:

sql
@TiendaZapasDDL.sql
@TiendaZapasDML.sql
@TiendaZapasPLSQL.sql

En _Oracle SQL Developer: abrir cada archivo con \*\*File → Open_ y ejecutar con _Run Script (F5)_.

### Diferencias de sintaxis respecto a MySQL

- INT AUTO_INCREMENT → NUMBER GENERATED BY DEFAULT AS IDENTITY
- VARCHAR → VARCHAR2
- TIMESTAMP DEFAULT CURRENT_TIMESTAMP → DATE DEFAULT SYSDATE
- DECIMAL(10,2) → NUMBER(10,2)

### Script PL/SQL — TiendaZapasPLSQL.sql

El script incluye bloques anónimos con cursores para: listar todos los clientes, clientes con venta, clientes sin venta, gasto total por cliente, análisis de productos, ventas por usuario y estado de stock. Para ejecutarlo y ver la salida:

sql
SET SERVEROUTPUT ON;
@TiendaZapasPLSQL.sql

---

---

# MÓDULO WEB — Aplicación CRM en el navegador

## Descripción

Aplicación web tipo _SPA (Single Page Application)_ desarrollada con _HTML5, \*\*CSS3_ y _JavaScript Vanilla (ES6+). Replica la misma lógica de negocio del CRM directamente en el navegador, sin necesidad de servidor ni base de datos. Los datos se almacenan en \*\*SessionStorage_ (se pierden al cerrar la pestaña).

## Requisitos

- Cualquier navegador moderno (Chrome, Firefox, Edge, Safari)
- No necesita servidor, se ejecuta directamente abriendo el archivo

## Cómo ejecutar

1. Abre la carpeta lenguaje/ en el explorador de archivos.
2. Haz doble clic en index.html (o arrástralo al navegador).
3. La aplicación se cargará inmediatamente con datos de ejemplo precargados.

Los datos se guardan en _SessionStorage_: persisten mientras el navegador esté abierto, pero se borran al cerrar la pestaña o el navegador.

## Estructura de archivos

- index.html — Estructura HTML de la SPA: header con navegación, secciones, modal reutilizable y toast de notificaciones
- style.css — Estilos con _metodología BEM_ (.stats-card**titulo, .modal**footer, etc.)
- app.js — Toda la lógica: clases de entidades, gestión de estado en SessionStorage y renderizado dinámico del DOM
- img/imgzapnn.png — Logo de la tienda
- img/zapnn.jpeg — Imagen de producto

## Funcionalidades del dashboard

Al abrir la app se muestra el panel de inicio con 4 tarjetas de resumen en tiempo real: ventas completadas, stock total en almacén, número de clientes registrados y facturación total acumulada.

## Secciones del CRM web

Cada sección permite operaciones _CRUD completas_ mediante un modal reutilizable:

- _Clientes_ — nombre, email, teléfono, dirección
- _Productos_ — nombre, descripción, marca, talla, color, precio, stock, categoría
- _Ventas_ — cliente, usuario, estado, total
- _Detalle Ventas_ — venta, producto, cantidad, precio unitario
- _Usuarios_ — nombre, email, rol, contraseña

## Datos de ejemplo precargados

_Clientes:_ Messi, Lamine Yamal, Kylian Mbappé, Jude Bellingham, Vinicius Jr., Pedri, Lookman, Marc Pubill

_Productos:_ Nike Air Max Plus TN, Nike Shox TL, Nike P-6000, Nike Zoom Vomero 5, Adidas Yeezy Boost 350, New Balance 2002R, New Balance 530, Salomon ACS Pro, Jordan 1 High Chicago

_Usuarios:_ Javier Vega (Administrador), Ivan Bermejo (Administrador)

## Clases JavaScript

js
class Cliente { id, nombre, email, telefono, direccion }
class Producto { id, nombre, descripcion, marca, talla, color, precio, stock, categoria }
class Venta { id, idCliente, idUsuario, estado, total, fecha }
class DetalleVenta{ id, idVenta, idProducto, cantidad, precioUnitario }
class Usuario { id, nombre, email, rol, passwordHash }

El estado global se guarda y recupera así:

js
let db = JSON.parse(sessionStorage.getItem("crm")) || DATA_DEFAULT;
const save = () => sessionStorage.setItem("crm", JSON.stringify(db));

---

## Solución de problemas comunes

### Java no conecta a MySQL

- Verifica que MySQL esté corriendo: mysql -u root -p1234
- Comprueba que el puerto 3306 está abierto (por defecto en MySQL Workbench)
- Asegúrate de que en DatabaseConnection.java los valores de host, puerto, usuario y contraseña coinciden exactamente con los de tu MySQL Workbench
- Verifica que la base de datos tienda_zapas existe: ejecuta el script SQL antes de arrancar Java

### Error al ejecutar el script SQL en Workbench

- Si hay errores de clave foránea, asegúrate de ejecutar el script completo de una vez (no selecciones solo fragmentos)
- El script incluye DROP TABLE IF EXISTS en orden inverso de dependencias para evitar conflictos

### La web no guarda los datos al recargar

- Esto es el comportamiento esperado: _SessionStorage_ solo persiste en la misma sesión de pestaña
- Para datos permanentes habría que migrar a localStorage o conectar a una API

### Oracle: errores de usuario o esquema

- Conéctate con un usuario que tenga permisos de creación de tablas
- Si usas Oracle XE, el esquema por defecto es el nombre del propio usuario (no tienda_zapas)
