-- =========================
-- INSERTS CLIENTES
-- =========================

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Messi', 'messi@gmail.com', '600111222', 'Madrid');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Lamine Yamal', 'lamine@gmail.com', '600100001', 'Barcelona');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Kylian Mbappe', 'mbappe@gmail.com', '600100002', 'Madrid');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Jude Bellingham', 'bellingham@gmail.com', '600100003', 'Madrid');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Vinicius Junior', 'vinicius@gmail.com', '600100004', 'Madrid');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Pedri Gonzalez', 'pedri@gmail.com', '600100005', 'Barcelona');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Nahuel Molina', 'nahuel@atleti.com', '600200001', 'Madrid');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Ademola Lookman', 'lookman@gmail.com', '600200002', 'Nigeria');

INSERT INTO clientes (nombre, email, telefono, direccion)
VALUES ('Marc Pubill', 'pubill@gmail.com', '600200003', 'Barcelona');


-- =========================
-- INSERTS USUARIOS
-- =========================

INSERT INTO usuarios (nombre, email, rol, password_hash)
VALUES ('Javier Vega', 'javier@tienda.com', 'Administrador', 'pass1');

INSERT INTO usuarios (nombre, email, rol, password_hash)
VALUES ('Ivan Bermejo', 'ivan@tienda.com', 'Administrador', 'pass2');


-- =========================
-- INSERTS PRODUCTOS
-- =========================

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Nike Air Max Plus TN', 'Nike', 'TN', 42, 'Negro', 189.99, 10, 'Lifestyle');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Nike Shox TL', 'Nike', 'Shox TL', 42, 'Negro', 189.99, 8, 'Lifestyle');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Nike P-6000', 'Nike', 'P-6000', 41, 'Plateado', 129.99, 14, 'Running');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Nike Zoom Vomero 5', 'Nike', 'Vomero 5', 43, 'Blanco', 169.99, 9, 'Running');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Adidas Gazelle', 'Adidas', 'Gazelle', 42, 'Azul', 99.99, 18, 'Classic');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Adidas Yeezy Boost 350', 'Adidas', 'Yeezy 350', 44, 'Cream', 299.99, 3, 'Streetwear');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('New Balance 2002R', 'New Balance', '2002R', 42, 'Gris', 159.99, 10, 'Lifestyle');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('New Balance 530', 'New Balance', '530', 41, 'Silver', 119.99, 13, 'Casual');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Salomon ACS Pro', 'Salomon', 'ACS Pro', 43, 'Black', 209.99, 4, 'Trail');

INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria)
VALUES ('Jordan 1 High Chicago', 'Jordan', 'Jordan 1', 43, 'Rojo/Blanco', 319.99, 2, 'Basket');


-- =========================
-- INSERTS VENTAS
-- =========================

INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (1, 1, 'Completada', 189.99);

INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (2, 2, 'Enviada', 189.99);

INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (3, 1, 'Preparando', 129.99);

INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (4, 2, 'Completada', 169.99);

INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (5, 1, 'Pendiente', 99.99);


-- =========================
-- INSERTS DETALLE_VENTA
-- =========================

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (1, 1, 1, 189.99);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (2, 2, 1, 189.99);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (3, 3, 1, 129.99);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (4, 4, 1, 169.99);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (5, 5, 1, 99.99);

COMMIT;


-- =========================
-- UPDATES CLIENTES
-- =========================

UPDATE clientes
SET telefono = '611111111'
WHERE nombre = 'Lamine Yamal';

UPDATE clientes
SET direccion = 'Madrid Centro'
WHERE nombre = 'Kylian Mbappe';

UPDATE clientes
SET email = 'jude@realmadrid.com'
WHERE nombre = 'Jude Bellingham';

UPDATE clientes
SET telefono = '622222222'
WHERE nombre = 'Vinicius Junior';

UPDATE clientes
SET direccion = 'Canarias'
WHERE nombre = 'Pedri Gonzalez';


-- =========================
-- UPDATES PRODUCTOS
-- =========================

UPDATE productos
SET precio = 179.99
WHERE modelo = 'TN';

UPDATE productos
SET stock = stock + 5
WHERE modelo = 'Shox TL';

UPDATE productos
SET color = 'Gris Plata'
WHERE modelo = 'Vomero 5';

UPDATE productos
SET categoria = 'Premium'
WHERE modelo = '2002R';

UPDATE productos
SET precio = 289.99
WHERE modelo = 'Yeezy 350';


-- =========================
-- UPDATES VENTAS
-- =========================

UPDATE ventas
SET estado = 'Enviada'
WHERE id_venta = 1;

UPDATE ventas
SET total = 249.99
WHERE id_venta = 2;

UPDATE ventas
SET estado = 'Preparando'
WHERE id_venta = 3;

UPDATE ventas
SET estado = 'Completada'
WHERE id_venta = 4;

UPDATE ventas
SET total = 159.99
WHERE id_venta = 5;

COMMIT;


-- =========================
-- DELETES
-- =========================

DELETE FROM detalle_venta
WHERE id_detalle = 1;

DELETE FROM ventas
WHERE id_venta = 1;

DELETE FROM productos
WHERE categoria = 'Classic';

DELETE FROM usuarios
WHERE nombre = 'Ivan Bermejo';

DELETE FROM clientes
WHERE nombre = 'Nahuel Molina';

COMMIT;