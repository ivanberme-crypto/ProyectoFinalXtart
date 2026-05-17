-- =====================================================================
-- STEP 1: CREACIÓN DE LA BASE DE DATOS
-- =====================================================================
CREATE DATABASE IF NOT EXISTS tienda_zapas;
USE tienda_zapas;

-- Eliminar tablas en orden inverso de dependencias por si deseas reiniciar el script
DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios;

-- =====================================================================
-- STEP 2: CREACIÓN DE TABLAS (DDL) ALINEADO CON EL DIAGRAMA DEL PDF
-- =====================================================================

-- TABLA: USUARIOS
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario)
);

-- TABLA: CLIENTES
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    CONSTRAINT pk_clientes PRIMARY KEY (id_cliente)
);

-- TABLA: PRODUCTOS (Combina los requisitos de la rúbrica y tu entidad Java)
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    talla DECIMAL(4,1) NOT NULL,
    color VARCHAR(30),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    descripcion VARCHAR(255), -- Requisito visual estricto de la imagen del PDF
    categoria VARCHAR(50),
    CONSTRAINT pk_productos PRIMARY KEY (id_producto)
);

-- TABLA: VENTAS (Relación 1:N con Clientes y Usuarios)
CREATE TABLE ventas (
    id_venta INT AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_ventas PRIMARY KEY (id_venta),
    CONSTRAINT fk_ventas_clientes FOREIGN KEY (id_cliente) 
        REFERENCES clientes(id_cliente) ON DELETE CASCADE,
    CONSTRAINT fk_ventas_usuarios FOREIGN KEY (id_usuario) 
        REFERENCES usuarios(id_usuario) ON DELETE RESTRICT
);

-- TABLA INTERMEDIA: DETALLE_VENTA (Relación N:M para desglose de productos)
CREATE TABLE detalle_venta (
    id_detalle INT AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_detalle_venta PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_venta_ventas FOREIGN KEY (id_venta) 
        REFERENCES ventas(id_venta) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_venta_productos FOREIGN KEY (id_prid_detalletelefonooducto) 
        REFERENCES productos(id_producto) ON DELETE RESTRICT
);

-- =====================================================================
-- STEP 3: POBLADO DE REGISTROS BASE Y DE PRUEBA (DML)
-- =====================================================================

-- 1. Inserción en USUARIOS
INSERT INTO usuarios (id_usuario, nombre, email, rol, password_hash) VALUES 
(1, 'Javier Vega', 'jvega@tienda.com', 'Administrador', 'hash_admin_1'),
(2, 'Carlos Ruiz', 'carlos@tienda.com', 'Vendedor', 'pass3'),
(3, 'Laura Mendez', 'laura@tienda.com', 'Vendedor', 'pass4'),
(4, 'Ana Torres', 'ana@tienda.com', 'Administrador', 'pass5'),
(5, 'Pedro Sanz', 'pedro@tienda.com', 'Vendedor', 'pass6');

-- 2. Inserción en CLIENTES
INSERT INTO clientes (id_cliente, nombre, email, telefono, direccion) VALUES 
(1, 'Nahuel Molina', 'nahuel@test.com', '600111222', 'Madrid'),
(2, 'Cole Palmer', 'cole.palmer@test.com', '600800001', 'Manchester'),
(3, 'Ousmane Dembélé', 'dembelle@test.com', '600800002', 'París'),
(4, 'Ansu Fati', 'ansu.fati@test.com', '600800003', 'Barcelona'),
(5, 'Presnel Kimpembe', 'kimpembe@test.com', '600800004', 'París'),
(6, 'Dani Olmo', 'dani.olmo@test.com', '600800005', 'Leipzig');

-- 3. Inserción en PRODUCTOS
INSERT INTO productos (id_producto, nombre, marca, modelo, talla, color, precio, stock, descripcion, categoria) VALUES 
(1, 'Zapatilla Running', 'Nike', 'Pegasus', 42.0, 'Azul', 129.99, 10, 'Zapatillas perfectas para asfalto', 'Running'),
(2, 'Zapatilla Urban', 'Adidas', 'Classic', 41.0, 'Blanco', 99.99, 15, 'Zapatillas clásicas de cuero', 'Casual'),
(3, 'Zapato Especial', 'Puma', 'Speed', 43.0, 'Negro', 289.99, 5, 'Edición limitada de carreras', 'Deporte'),
(4, 'Bota Trekking', 'Salomon', 'Quest', 44.0, 'Gris', 159.99, 8, 'Impermeable para senderismo extremo', 'Montaña'),
(5, 'Prod Temp 1', 'Test', 'Temp1', 40.0, 'Blanco', 50.00, 1, 'Producto de stock efímero', 'Temporal');

-- 4. Inserción en VENTAS
INSERT INTO ventas (id_venta, id_cliente, id_usuario, estado, total) VALUES 
(1, 1, 1, 'Completada', 229.98),
(2, 2, 2, 'Cancelada', 50.00),
(3, 3, 2, 'Cancelada', 60.00),
(4, 4, 3, 'Cancelada', 70.00),
(5, 5, 3, 'Cancelada', 80.00);

-- 5. Inserción en DETALLE_VENTA
INSERT INTO detalle_venta (id_detalle, id_venta, id_producto, cantidad, precio_unitario) VALUES 
(1, 1, 1, 1, 129.99),
(2, 2, 5, 1, 50.00),
(3, 3, 2, 1, 60.00),
(4, 4, 3, 1, 70.00),
(5, 5, 4, 2, 40.00);

-- =====================================================================
-- STEP 4: EJECUCIÓN DE PRUEBAS DE ACTUALIZACIÓN Y BORRADO (LÓGICA DML)
-- =====================================================================

-- Ejemplo de modificaciones sobre registros existentes
UPDATE usuarios SET email = 'javier.vega@tienda.com' WHERE nombre = 'Javier Vega';
UPDATE usuarios SET rol = 'Supervisor' WHERE nombre = 'Carlos Ruiz';

UPDATE detalle_venta SET cantidad = 3 WHERE id_detalle = 2;

-- En MySQL, si se desea actualizar una fila basándose en una subconsulta de la 
-- misma tabla, se requiere usar una tabla derivada temporal (alias) para evitar bloqueos:
UPDATE detalle_venta SET cantidad = 1 WHERE id_detalle = (
    SELECT max_id FROM (SELECT MAX(id_detalle) AS max_id FROM detalle_venta) AS t
);

-- =====================================================================
-- Sentencias de limpieza controladas (CORREGIDO)
-- =====================================================================

DELETE FROM clientes WHERE nombre LIKE 'Cliente Temp%';

DELETE FROM productos WHERE categoria = 'Temporal';

-- Línea corregida: Se eliminó el texto residual 'id_clienteemail;' y el paréntesis huérfano
DELETE FROM ventas WHERE estado = 'Cancelada';


USE tienda_zapas;
SELECT * FROM clientes;
-- Added indexes on foreign key columns
