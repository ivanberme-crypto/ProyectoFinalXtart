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
