# ProyectoFinal CRM — Tienda Zapas (ZAPNN)

Aplicación CRM completa para una tienda de zapatillas, desarrollada como *proyecto final de 1º DAM*. El sistema gestiona clientes, productos, ventas, usuarios y detalles de venta a través de tres módulos independientes pero conectados a la misma lógica de negocio.

---

## Estructura del proyecto

```text
ProyectoFinalXtart/
├── Java/                  → Aplicación de consola (Maven + JDBC + MySQL)
│   └── src/main/java/com/javierberme/
│       ├── Controllers/   → Capa de controladores
│       ├── Entities/      → Entidades / modelos de datos
│       ├── Interfaz/      → Menús de consola (Main, Menu, submenús)
│       ├── Repositories/  → Acceso a datos (interfaces + implementaciones JDBC)
│       ├── Services/      → Lógica de negocio
│       └── Utils/         → DatabaseConnection, ExportadorCSV
│
├── Basededatos/
│   ├── sql/
│   │   └── dmlyddlfinal.sql       → DDL + DML para MySQL (usado por Java)
│   └── oracle/
│       ├── TiendaZapasDDL.sql     → DDL para Oracle
│       ├── TiendaZapasDML.sql     → Datos iniciales para Oracle
│       └── TiendaZapasPLSQL.sql   → Cursores, procedimientos y funciones PL/SQL
│
└── lenguaje/
    ├── index.html         → SPA del CRM web
    ├── style.css          → Estilos con metodología BEM
    └── app.js             → Lógica completa en JavaScript Vanilla

Autores
berme_ivan — bermevfx@gmail.com

javiervega120 — javiervegatorres01@gmail.com

Módulos del proyecto
Java — Java 21, Maven, JDBC, MySQL — Asignatura: Programación

Base de datos — MySQL 8 + Oracle 21c XE + PL/SQL — Asignatura: Bases de Datos

Web — HTML5, CSS3 (BEM), JavaScript ES6+ — Asignatura: Lenguajes de Marcas

MÓDULO JAVA — Aplicación de consola
Descripción
Aplicación de consola desarrollada en Java 21 con arquitectura en capas (Controller → Service → Repository → Entity). Se conecta a una base de datos MySQL mediante JDBC y permite gestionar de forma interactiva clientes, productos, ventas, usuarios y detalles de venta. También incluye exportación a CSV.

Arquitectura por capas
Interfaz (menús de consola)

Controllers (coordinan la entrada/salida)

Services (lógica de negocio, validaciones)

Repositories (interfaces + implementaciones JDBC)

MySQL Database (via JDBC)

Entidades del modelo
Cliente — idCliente, nombre, email, telefono, direccion

Producto — idProducto, nombre, marca, modelo, talla, color, precio, stock, categoria

Venta — idVenta, idCliente, idUsuario, fecha, estado, total

DetalleVenta — idDetalle, idVenta, idProducto, cantidad, precioUnitario

Usuario — idUsuario, nombre, email, rol, password_hash

Requisitos previos
Java 21 o superior

Maven 3.8 o superior

MySQL 8.x en ejecución

MySQL Workbench (recomendado para preparar la base de datos)

IntelliJ IDEA (o cualquier IDE con soporte Maven)

Configuración de MySQL Workbench
CRÍTICO: Los valores de MySQL Workbench deben coincidir exactamente con los definidos en DatabaseConnection.java.

Paso 1 — Crear la conexión en MySQL Workbench
Abre MySQL Workbench.

En la pantalla de inicio, haz clic en el símbolo + junto a "MySQL Connections".

Rellena los campos con estos valores (son los mismos que usa Java):

Connection Name: tienda_zapas (o el nombre que prefieras)

Hostname: localhost

Port: 3306

Username: root

Password: 1234

Default Schema: tienda_zapas (opcional)

Pulsa "Test Connection" para verificar que funciona.

Pulsa OK para guardar la conexión.

Paso 2 — Ejecutar el script SQL
Una vez conectado, abre el archivo Basededatos/sql/dmlyddlfinal.sql desde Workbench:

Menú File → Open SQL Script → selecciona el archivo.

Pulsa el rayo (Execute All) o Ctrl+Shift+Enter para ejecutar todo el script.

Esto creará la base de datos tienda_zapas, todas las tablas y cargará los datos de ejemplo.

Verifica en el panel izquierdo que aparece el schema tienda_zapas con las tablas: usuarios, clientes, productos, ventas, detalle_venta.

Paso 3 — Verificar que Java usa la misma configuración
El archivo de conexión Java es Java/src/main/java/com/javierberme/Utils/DatabaseConnection.java. Su contenido actual es:
