SET SERVEROUTPUT ON;
DECLARE
CURSOR c_clientes IS
SELECT id_cliente, nombre, email, telefono, direccion
FROM clientes
ORDER BY nombre;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TODOS LOS CLIENTES ===');
FOR r IN c_clientes LOOP
        DBMS_OUTPUT.PUT_LINE(
            r.id_cliente || ' | ' || r.nombre || ' | ' ||
            r.email || ' | ' || r.telefono || ' | ' || r.direccion
        );
END LOOP;
END;
/




DECLARE
CURSOR c_clientes_con_venta IS
SELECT DISTINCT c.id_cliente, c.nombre, c.email
FROM clientes c
         JOIN ventas v ON c.id_cliente = v.id_cliente
ORDER BY c.nombre;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== CLIENTES CON VENTAS ===');
FOR r IN c_clientes_con_venta LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_cliente || ' | ' || r.nombre || ' | ' || r.email);
END LOOP;
END;
/




DECLARE
CURSOR c_clientes_sin_venta IS
SELECT c.id_cliente, c.nombre, c.email
FROM clientes c
WHERE NOT EXISTS (
    SELECT 1 FROM ventas v WHERE v.id_cliente = c.id_cliente
);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== CLIENTES SIN VENTAS ===');
FOR r IN c_clientes_sin_venta LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_cliente || ' | ' || r.nombre || ' | ' || r.email);
END LOOP;
END;
/




DECLARE
CURSOR c_gasto_cliente IS
SELECT c.nombre, SUM(v.total) AS total_gastado
FROM clientes c
         JOIN ventas v ON c.id_cliente = v.id_cliente
GROUP BY c.nombre
ORDER BY total_gastado DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== GASTO POR CLIENTE ===');
FOR r IN c_gasto_cliente LOOP
        DBMS_OUTPUT.PUT_LINE(r.nombre || ' | Total: ' || r.total_gastado || 'EUR');
END LOOP;
END;
/




DECLARE
v_letra VARCHAR2(1) := 'A';
CURSOR c_clientes_letra IS
SELECT id_cliente, nombre, telefono
FROM clientes
WHERE UPPER(nombre) LIKE UPPER(v_letra) || '%'
ORDER BY nombre;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== CLIENTES QUE EMPIEZAN POR ' || v_letra || ' ===');
FOR r IN c_clientes_letra LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_cliente || ' | ' || r.nombre || ' | ' || r.telefono);
END LOOP;
END;
/




CREATE OR REPLACE PROCEDURE crear_cliente (
    p_nombre    IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_telefono  IN VARCHAR2,
    p_direccion IN VARCHAR2
) IS
    v_existe NUMBER;
BEGIN
    IF p_nombre IS NULL OR TRIM(p_nombre) = '' THEN
        RAISE_APPLICATION_ERROR(-20001, 'El nombre del cliente no puede estar vacio.');
END IF;
SELECT COUNT(*)
INTO v_existe
FROM clientes
WHERE UPPER(email) = UPPER(p_email);
IF v_existe > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Ya existe un cliente con el email: ' || p_email);
ELSE
        INSERT INTO clientes (nombre, email, telefono, direccion)
        VALUES (INITCAP(p_nombre), LOWER(p_email), p_telefono, p_direccion);
COMMIT;
DBMS_OUTPUT.PUT_LINE('Cliente ' || p_nombre || ' creado correctamente.');
END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al crear cliente: ' || SQLERRM);
ROLLBACK;
END;
/


BEGIN
    crear_cliente('Ana Lopez', 'ana@mail.com', '600000001', 'Calle Mayor 1');
END;
/




CREATE OR REPLACE PROCEDURE listar_clientes_con_ventas IS
    CURSOR c IS
SELECT c.nombre,
       COUNT(v.id_venta) AS num_ventas,
       NVL(SUM(v.total), 0) AS total
FROM clientes c
         LEFT JOIN ventas v ON c.id_cliente = v.id_cliente
GROUP BY c.nombre
ORDER BY num_ventas DESC;
v_linea VARCHAR2(200);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== RESUMEN DE CLIENTES ===');
FOR r IN c LOOP
        v_linea := RPAD(r.nombre, 25) || ' | Ventas: ' || r.num_ventas ||
                   ' | Total: ' || r.total || 'EUR';
        DBMS_OUTPUT.PUT_LINE(v_linea);
END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/


BEGIN
    listar_clientes_con_ventas;
END;
/




CREATE OR REPLACE FUNCTION contar_clientes
RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT COUNT(*) INTO v_total FROM clientes;
RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/



DECLARE
v_result NUMBER;
BEGIN
    v_result := contar_clientes;
    DBMS_OUTPUT.PUT_LINE('Total de clientes: ' || v_result);
END;
/



CREATE OR REPLACE FUNCTION obtener_nombre_cliente (
    p_id IN NUMBER
) RETURN VARCHAR2 IS
    v_nombre VARCHAR2(100);
BEGIN
SELECT nombre INTO v_nombre
FROM clientes
WHERE id_cliente = p_id;
RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Cliente no encontrado';
WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
/



DECLARE
v_nombre VARCHAR2(100);
BEGIN
    v_nombre := obtener_nombre_cliente(1);
    DBMS_OUTPUT.PUT_LINE('Nombre del cliente 1: ' || v_nombre);
END;
/



DECLARE
CURSOR c_usuarios IS
SELECT id_usuario, nombre, email, rol
FROM usuarios
ORDER BY rol, nombre;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TODOS LOS USUARIOS ===');
FOR r IN c_usuarios LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_usuario || ' | ' || r.nombre || ' | ' || r.email || ' | ' || r.rol);
END LOOP;
END;
/



DECLARE
CURSOR c_admins IS
SELECT id_usuario, nombre, email
FROM usuarios
WHERE UPPER(rol) = 'ADMINISTRADOR';
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== ADMINISTRADORES ===');
FOR r IN c_admins LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_usuario || ' | ' || r.nombre || ' | ' || r.email);
END LOOP;
END;
/



DECLARE
CURSOR c_vendedores IS
SELECT id_usuario, nombre, email
FROM usuarios
WHERE UPPER(rol) = 'VENDEDOR';
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENDEDORES ===');
FOR r IN c_vendedores LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_usuario || ' | ' || r.nombre || ' | ' || r.email);
END LOOP;
END;
/



DECLARE
CURSOR c_ventas_usuario IS
SELECT u.nombre, COUNT(v.id_venta) AS ventas_gestionadas
FROM usuarios u
         LEFT JOIN ventas v ON u.id_usuario = v.id_usuario
GROUP BY u.nombre
ORDER BY ventas_gestionadas DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS POR USUARIO ===');
FOR r IN c_ventas_usuario LOOP
        DBMS_OUTPUT.PUT_LINE(r.nombre || ' | Ventas: ' || r.ventas_gestionadas);
END LOOP;
END;
/



DECLARE
CURSOR c_usuarios_sin_ventas IS
SELECT u.id_usuario, u.nombre, u.rol
FROM usuarios u
WHERE NOT EXISTS (
    SELECT 1 FROM ventas v WHERE v.id_usuario = u.id_usuario
);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== USUARIOS SIN VENTAS ===');
FOR r IN c_usuarios_sin_ventas LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_usuario || ' | ' || r.nombre || ' | ' || r.rol);
END LOOP;
END;
/



CREATE OR REPLACE PROCEDURE crear_usuario (
    p_nombre        IN VARCHAR2,
    p_email         IN VARCHAR2,
    p_rol           IN VARCHAR2,
    p_password_hash IN VARCHAR2
) IS
    v_rol_valido BOOLEAN := FALSE;
    v_roles      VARCHAR2(100) := 'ADMINISTRADOR,VENDEDOR';
BEGIN
    IF p_nombre IS NULL OR TRIM(p_nombre) = '' THEN
        RAISE_APPLICATION_ERROR(-20010, 'El nombre no puede estar vacio.');
END IF;
    IF UPPER(p_rol) IN ('ADMINISTRADOR', 'VENDEDOR') THEN
        v_rol_valido := TRUE;
END IF;
    IF NOT v_rol_valido THEN
        RAISE_APPLICATION_ERROR(-20011, 'Rol no valido. Roles permitidos: ' || v_roles);
END IF;
INSERT INTO usuarios (nombre, email, rol, password_hash)
VALUES (INITCAP(p_nombre), LOWER(p_email), INITCAP(p_rol), p_password_hash);
COMMIT;
DBMS_OUTPUT.PUT_LINE('Usuario ' || p_nombre || ' creado con rol ' || p_rol || '.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al crear usuario: ' || SQLERRM);
ROLLBACK;
END;
/



BEGIN
    crear_usuario('Carlos Ruiz', 'carlos@zapnn.com', 'Vendedor', 'hash_seguro_123');
END;
/



CREATE OR REPLACE PROCEDURE resumen_usuarios_por_rol IS
    CURSOR c_roles IS
SELECT DISTINCT rol FROM usuarios ORDER BY rol;
v_cantidad NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== RESUMEN DE USUARIOS POR ROL ===');
FOR r IN c_roles LOOP
SELECT COUNT(*) INTO v_cantidad
FROM usuarios
WHERE rol = r.rol;
DBMS_OUTPUT.PUT_LINE('Rol: ' || RPAD(r.rol, 20) || ' | Cantidad: ' || v_cantidad);
END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/


BEGIN
    resumen_usuarios_por_rol;
END;
/



CREATE OR REPLACE FUNCTION contar_usuarios_por_rol (
    p_rol IN VARCHAR2
) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT COUNT(*) INTO v_total
FROM usuarios
WHERE UPPER(rol) = UPPER(p_rol);
RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/



DECLARE
v_total NUMBER;
BEGIN
    v_total := contar_usuarios_por_rol('Administrador');
    DBMS_OUTPUT.PUT_LINE('Administradores: ' || v_total);
END;
/



CREATE OR REPLACE FUNCTION obtener_nombre_usuario (
    p_id IN NUMBER
) RETURN VARCHAR2 IS
    v_nombre VARCHAR2(100);
BEGIN
SELECT nombre INTO v_nombre
FROM usuarios
WHERE id_usuario = p_id;
RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Usuario no encontrado';
WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
/



DECLARE
v_nombre VARCHAR2(100);
BEGIN
    v_nombre := obtener_nombre_usuario(1);
    DBMS_OUTPUT.PUT_LINE('Usuario ID 1: ' || v_nombre);
END;
/




DECLARE
CURSOR c_productos IS
SELECT id_producto, nombre, marca, modelo, precio, stock, categoria
FROM productos
ORDER BY marca, nombre;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TODOS LOS PRODUCTOS ===');
FOR r IN c_productos LOOP
        DBMS_OUTPUT.PUT_LINE(
            r.id_producto || ' | ' || r.nombre || ' | ' || r.marca ||
            ' | ' || r.modelo || ' | ' || r.precio || 'EUR | Stock: ' || r.stock
        );
END LOOP;
END;
/



DECLARE
CURSOR c_stock_bajo IS
SELECT nombre, marca, stock
FROM productos
WHERE stock < 10
ORDER BY stock;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== PRODUCTOS CON STOCK BAJO ===');
FOR r IN c_stock_bajo LOOP
        DBMS_OUTPUT.PUT_LINE(r.nombre || ' (' || r.marca || ') | Stock: ' || r.stock);
END LOOP;
END;
/



DECLARE
CURSOR c_por_categoria IS
SELECT categoria,
       COUNT(*) AS cantidad,
       ROUND(AVG(precio), 2) AS precio_medio,
       MIN(precio) AS precio_min,
       MAX(precio) AS precio_max
FROM productos
GROUP BY categoria
ORDER BY categoria;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== RESUMEN POR CATEGORIA ===');
FOR r IN c_por_categoria LOOP
        DBMS_OUTPUT.PUT_LINE(
            r.categoria || ' | Items: ' || r.cantidad ||
            ' | Precio medio: ' || r.precio_medio ||
            ' | Min: ' || r.precio_min || ' | Max: ' || r.precio_max
        );
END LOOP;
END;
/




DECLARE
CURSOR c_mas_vendidos IS
SELECT p.nombre, p.marca, SUM(dv.cantidad) AS unidades_vendidas
FROM productos p
         JOIN detalle_venta dv ON p.id_producto = dv.id_producto
GROUP BY p.nombre, p.marca
ORDER BY unidades_vendidas DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== PRODUCTOS MAS VENDIDOS ===');
FOR r IN c_mas_vendidos LOOP
        DBMS_OUTPUT.PUT_LINE(r.nombre || ' (' || r.marca || ') | Vendidos: ' || r.unidades_vendidas);
END LOOP;
END;
/



DECLARE
CURSOR c_sin_ventas IS
SELECT p.id_producto, p.nombre, p.marca, p.stock
FROM productos p
WHERE NOT EXISTS (
    SELECT 1 FROM detalle_venta dv WHERE dv.id_producto = p.id_producto
);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== PRODUCTOS SIN VENTAS ===');
FOR r IN c_sin_ventas LOOP
        DBMS_OUTPUT.PUT_LINE(r.id_producto || ' | ' || r.nombre || ' | ' || r.marca || ' | Stock: ' || r.stock);
END LOOP;
END;
/



CREATE OR REPLACE PROCEDURE aplicar_descuento_categoria (
    p_categoria  IN VARCHAR2,
    p_descuento  IN NUMBER
) IS
    v_actualizados NUMBER;
    v_precio_min   NUMBER;
BEGIN
    IF p_descuento <= 0 OR p_descuento >= 100 THEN
        RAISE_APPLICATION_ERROR(-20020, 'El descuento debe estar entre 1 y 99.');
END IF;
SELECT COUNT(*) INTO v_actualizados
FROM productos
WHERE UPPER(categoria) = UPPER(p_categoria);
IF v_actualizados = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No se encontraron productos de la categoria: ' || p_categoria);
        RETURN;
END IF;
UPDATE productos
SET precio = ROUND(precio - (precio * p_descuento / 100), 2)
WHERE UPPER(categoria) = UPPER(p_categoria);
v_actualizados := SQL%ROWCOUNT;
COMMIT;
DBMS_OUTPUT.PUT_LINE(
        'Descuento del ' || p_descuento || '% aplicado a ' ||
        v_actualizados || ' productos de la categoria ' || p_categoria || '.'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al aplicar descuento: ' || SQLERRM);
ROLLBACK;
END;
/



BEGIN
    aplicar_descuento_categoria('Running', 10);
END;
/



CREATE OR REPLACE PROCEDURE alerta_reposicion (
    p_stock_minimo IN NUMBER DEFAULT 10
) IS
    CURSOR c_bajo IS
SELECT nombre, marca, stock
FROM productos
WHERE stock <= p_stock_minimo
ORDER BY stock;
v_contador NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== ALERTA DE REPOSICION (stock <= ' || p_stock_minimo || ') ===');
FOR r IN c_bajo LOOP
        v_contador := v_contador + 1;
        IF r.stock = 0 THEN
            DBMS_OUTPUT.PUT_LINE('[SIN STOCK] ' || r.nombre || ' | ' || r.marca);
        ELSIF r.stock <= 5 THEN
            DBMS_OUTPUT.PUT_LINE('[CRITICO]   ' || r.nombre || ' | ' || r.marca || ' | Stock: ' || r.stock);
ELSE
            DBMS_OUTPUT.PUT_LINE('[BAJO]      ' || r.nombre || ' | ' || r.marca || ' | Stock: ' || r.stock);
END IF;
END LOOP;
    IF v_contador = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Todos los productos tienen stock suficiente.');
ELSE
        DBMS_OUTPUT.PUT_LINE('Total de productos a reponer: ' || v_contador);
END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/


BEGIN
    alerta_reposicion(15);
END;
/



CREATE OR REPLACE FUNCTION calcular_valor_stock
RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT SUM(precio * stock) INTO v_total FROM productos;
RETURN NVL(v_total, 0);
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/



DECLARE
v_resultado NUMBER;
BEGIN
    v_resultado := calcular_valor_stock;
    DBMS_OUTPUT.PUT_LINE('Valor total del stock: ' || v_resultado || 'EUR');
END;
/



CREATE OR REPLACE FUNCTION obtener_precio_producto (
    p_id IN NUMBER
) RETURN NUMBER IS
    v_precio NUMBER;
BEGIN
SELECT precio INTO v_precio
FROM productos
WHERE id_producto = p_id;
RETURN v_precio;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN -1;
WHEN OTHERS THEN
        RETURN -2;
END;
/



DECLARE
v_precio NUMBER;
BEGIN
    v_precio := obtener_precio_producto(1);
    IF v_precio >= 0 THEN
        DBMS_OUTPUT.PUT_LINE('Precio del producto 1: ' || v_precio || 'EUR');
ELSE
        DBMS_OUTPUT.PUT_LINE('Producto no encontrado.');
END IF;
END;
/



DECLARE
CURSOR c_ventas IS
SELECT v.id_venta, c.nombre AS cliente, u.nombre AS usuario,
       v.fecha, v.estado, v.total
FROM ventas v
         JOIN clientes c ON v.id_cliente = c.id_cliente
         JOIN usuarios u ON v.id_usuario = u.id_usuario
ORDER BY v.fecha DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TODAS LAS VENTAS ===');
FOR r IN c_ventas LOOP
        DBMS_OUTPUT.PUT_LINE(
            'V' || r.id_venta || ' | ' || r.cliente || ' | ' ||
            r.usuario || ' | ' || r.fecha || ' | ' ||
            r.estado || ' | ' || r.total || 'EUR'
        );
END LOOP;
END;
/



DECLARE
CURSOR c_por_estado IS
SELECT estado,
       COUNT(*) AS cantidad,
       SUM(total) AS importe_total,
       ROUND(AVG(total), 2) AS ticket_medio
FROM ventas
GROUP BY estado
ORDER BY importe_total DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS POR ESTADO ===');
FOR r IN c_por_estado LOOP
        DBMS_OUTPUT.PUT_LINE(
            r.estado || ' | Cantidad: ' || r.cantidad ||
            ' | Total: ' || r.importe_total ||
            ' | Ticket medio: ' || r.ticket_medio
        );
END LOOP;
END;
/



DECLARE
CURSOR c_ventas_mes IS
SELECT v.id_venta, c.nombre AS cliente, v.total
FROM ventas v
         JOIN clientes c ON v.id_cliente = c.id_cliente
WHERE EXTRACT(MONTH FROM v.fecha) = EXTRACT(MONTH FROM SYSDATE)
  AND EXTRACT(YEAR FROM v.fecha) = EXTRACT(YEAR FROM SYSDATE)
ORDER BY v.id_venta;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS DEL MES ACTUAL ===');
FOR r IN c_ventas_mes LOOP
        DBMS_OUTPUT.PUT_LINE('Venta ' || r.id_venta || ' | ' || r.cliente || ' | ' || r.total || 'EUR');
END LOOP;
END;
/



DECLARE
CURSOR c_top5 IS
SELECT v.id_venta, c.nombre AS cliente, v.total
FROM ventas v
         JOIN clientes c ON v.id_cliente = c.id_cliente
ORDER BY v.total DESC
    FETCH FIRST 5 ROWS ONLY;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TOP 5 VENTAS POR IMPORTE ===');
FOR r IN c_top5 LOOP
        DBMS_OUTPUT.PUT_LINE('Venta ' || r.id_venta || ' | ' || r.cliente || ' | ' || r.total || 'EUR');
END LOOP;
END;
/



DECLARE
CURSOR c_canceladas IS
SELECT v.id_venta, c.nombre AS cliente, v.fecha, v.total
FROM ventas v
         JOIN clientes c ON v.id_cliente = c.id_cliente
WHERE UPPER(v.estado) = 'CANCELADA'
ORDER BY v.fecha DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS CANCELADAS ===');
FOR r IN c_canceladas LOOP
        DBMS_OUTPUT.PUT_LINE('Venta ' || r.id_venta || ' | ' || r.cliente || ' | ' || r.fecha || ' | ' || r.total || 'EUR');
END LOOP;
END;
/



CREATE OR REPLACE PROCEDURE registrar_venta (
    p_id_cliente IN NUMBER,
    p_id_usuario IN NUMBER,
    p_estado     IN VARCHAR2,
    p_total      IN NUMBER
) IS
    v_existe_cliente NUMBER;
    v_existe_usuario NUMBER;
BEGIN
    IF p_total < 0 THEN
        RAISE_APPLICATION_ERROR(-20030, 'El total no puede ser negativo.');
END IF;
SELECT COUNT(*) INTO v_existe_cliente
FROM clientes WHERE id_cliente = p_id_cliente;
IF v_existe_cliente = 0 THEN
        RAISE_APPLICATION_ERROR(-20031, 'El cliente con ID ' || p_id_cliente || ' no existe.');
END IF;
SELECT COUNT(*) INTO v_existe_usuario
FROM usuarios WHERE id_usuario = p_id_usuario;
IF v_existe_usuario = 0 THEN
        RAISE_APPLICATION_ERROR(-20032, 'El usuario con ID ' || p_id_usuario || ' no existe.');
END IF;
    IF UPPER(p_estado) NOT IN ('PENDIENTE', 'COMPLETADA', 'CANCELADA') THEN
        RAISE_APPLICATION_ERROR(-20033, 'Estado no valido. Use: pendiente, completada o cancelada.');
END IF;
INSERT INTO ventas (id_cliente, id_usuario, estado, total)
VALUES (p_id_cliente, p_id_usuario, INITCAP(p_estado), p_total);
COMMIT;
DBMS_OUTPUT.PUT_LINE('Venta registrada correctamente. Total: ' || p_total || 'EUR');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al registrar venta: ' || SQLERRM);
ROLLBACK;
END;
/

BEGIN
    registrar_venta(1, 1, 'pendiente', 129.99);
END;
/



CREATE OR REPLACE PROCEDURE resumen_ventas_por_mes IS
    CURSOR c_meses IS
SELECT EXTRACT(YEAR FROM fecha) AS anio,
       EXTRACT(MONTH FROM fecha) AS mes,
       COUNT(*) AS cantidad,
       SUM(total) AS importe
FROM ventas
GROUP BY EXTRACT(YEAR FROM fecha), EXTRACT(MONTH FROM fecha)
ORDER BY anio, mes;
v_mes_nombre VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS POR MES ===');
FOR r IN c_meses LOOP
        CASE r.mes
            WHEN 1  THEN v_mes_nombre := 'Enero';
WHEN 2  THEN v_mes_nombre := 'Febrero';
WHEN 3  THEN v_mes_nombre := 'Marzo';
WHEN 4  THEN v_mes_nombre := 'Abril';
WHEN 5  THEN v_mes_nombre := 'Mayo';
WHEN 6  THEN v_mes_nombre := 'Junio';
WHEN 7  THEN v_mes_nombre := 'Julio';
WHEN 8  THEN v_mes_nombre := 'Agosto';
WHEN 9  THEN v_mes_nombre := 'Septiembre';
WHEN 10 THEN v_mes_nombre := 'Octubre';
WHEN 11 THEN v_mes_nombre := 'Noviembre';
WHEN 12 THEN v_mes_nombre := 'Diciembre';
ELSE v_mes_nombre := 'Desconocido';
END CASE;
        DBMS_OUTPUT.PUT_LINE(
            r.anio || ' - ' || RPAD(v_mes_nombre, 12) ||
            ' | Ventas: ' || r.cantidad ||
            ' | Total: ' || r.importe || 'EUR'
        );
END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

BEGIN
    resumen_ventas_por_mes;
END;
/




CREATE OR REPLACE FUNCTION contar_ventas_estado (
    p_estado IN VARCHAR2
) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT COUNT(*) INTO v_total
FROM ventas
WHERE UPPER(estado) = UPPER(p_estado);
RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/




DECLARE
v_total NUMBER;
BEGIN
    v_total := contar_ventas_estado('Completada');
    DBMS_OUTPUT.PUT_LINE('Ventas completadas: ' || v_total);
END;
/





CREATE OR REPLACE FUNCTION ingresos_cliente (
    p_id_cliente IN NUMBER
) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT NVL(SUM(total), 0) INTO v_total
FROM ventas
WHERE id_cliente = p_id_cliente
  AND UPPER(estado) = 'COMPLETADA';
RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/




DECLARE
v_resultado NUMBER;
BEGIN
    v_resultado := ingresos_cliente(1);
    DBMS_OUTPUT.PUT_LINE('Ingresos del cliente 1: ' || v_resultado || 'EUR');
END;
/




DECLARE
CURSOR c_detalle IS
SELECT dv.id_detalle, v.id_venta, p.nombre AS producto,
       dv.cantidad, dv.precio_unitario,
       dv.cantidad * dv.precio_unitario AS subtotal
FROM detalle_venta dv
         JOIN ventas v ON dv.id_venta = v.id_venta
         JOIN productos p ON dv.id_producto = p.id_producto
ORDER BY v.id_venta, dv.id_detalle;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TODOS LOS DETALLES DE VENTA ===');
FOR r IN c_detalle LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Detalle ' || r.id_detalle ||
            ' | Venta ' || r.id_venta ||
            ' | ' || r.producto ||
            ' | Qty: ' || r.cantidad ||
            ' | Precio: ' || r.precio_unitario ||
            ' | Subtotal: ' || r.subtotal || 'EUR'
        );
END LOOP;
END;
/




DECLARE
v_id_venta NUMBER := 1;
CURSOR c_detalle_venta IS
SELECT p.nombre, dv.cantidad, dv.precio_unitario,
       dv.cantidad * dv.precio_unitario AS subtotal
FROM detalle_venta dv
         JOIN productos p ON dv.id_producto = p.id_producto
WHERE dv.id_venta = v_id_venta;
v_total NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== DETALLE DE VENTA ' || v_id_venta || ' ===');
FOR r IN c_detalle_venta LOOP
        DBMS_OUTPUT.PUT_LINE(r.nombre || ' | x' || r.cantidad || ' | ' || r.precio_unitario || 'EUR | Subtotal: ' || r.subtotal || 'EUR');
        v_total := v_total + r.subtotal;
END LOOP;
    DBMS_OUTPUT.PUT_LINE('TOTAL: ' || v_total || 'EUR');
END;
/



DECLARE
CURSOR c_top_ingresos IS
SELECT p.nombre, p.marca,
       SUM(dv.cantidad) AS unidades,
       SUM(dv.cantidad * dv.precio_unitario) AS ingresos
FROM detalle_venta dv
         JOIN productos p ON dv.id_producto = p.id_producto
GROUP BY p.nombre, p.marca
ORDER BY ingresos DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TOP PRODUCTOS POR INGRESOS ===');
FOR r IN c_top_ingresos LOOP
        DBMS_OUTPUT.PUT_LINE(
            r.nombre || ' (' || r.marca || ')' ||
            ' | Unidades: ' || r.unidades ||
            ' | Ingresos: ' || r.ingresos || 'EUR'
        );
END LOOP;
END;
/



DECLARE
CURSOR c_sobre_media IS
SELECT dv.id_detalle, p.nombre, dv.precio_unitario
FROM detalle_venta dv
         JOIN productos p ON dv.id_producto = p.id_producto
WHERE dv.precio_unitario > (SELECT AVG(precio_unitario) FROM detalle_venta)
ORDER BY dv.precio_unitario DESC;
v_media NUMBER;
BEGIN
SELECT ROUND(AVG(precio_unitario), 2) INTO v_media FROM detalle_venta;
DBMS_OUTPUT.PUT_LINE('=== LINEAS SOBRE EL PRECIO MEDIO (' || v_media || 'EUR) ===');
FOR r IN c_sobre_media LOOP
        DBMS_OUTPUT.PUT_LINE('Detalle ' || r.id_detalle || ' | ' || r.nombre || ' | ' || r.precio_unitario || 'EUR');
END LOOP;
END;
/




DECLARE
CURSOR c_multilinea IS
SELECT id_venta, COUNT(*) AS num_lineas, SUM(cantidad * precio_unitario) AS total
FROM detalle_venta
GROUP BY id_venta
HAVING COUNT(*) > 1
ORDER BY num_lineas DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VENTAS CON VARIOS PRODUCTOS ===');
FOR r IN c_multilinea LOOP
        DBMS_OUTPUT.PUT_LINE('Venta ' || r.id_venta || ' | Lineas: ' || r.num_lineas || ' | Total: ' || r.total || 'EUR');
END LOOP;
END;
/




CREATE OR REPLACE PROCEDURE añadir_detalle (
    p_id_venta       IN NUMBER,
    p_id_producto    IN NUMBER,
    p_cantidad       IN NUMBER,
    p_precio_unitario IN NUMBER
) IS
    v_stock_actual NUMBER;
    v_existe_venta NUMBER;
BEGIN
    IF p_cantidad <= 0 THEN
        RAISE_APPLICATION_ERROR(-20040, 'La cantidad debe ser mayor que 0.');
END IF;
    IF p_precio_unitario <= 0 THEN
        RAISE_APPLICATION_ERROR(-20041, 'El precio unitario debe ser mayor que 0.');
END IF;
SELECT COUNT(*) INTO v_existe_venta FROM ventas WHERE id_venta = p_id_venta;
IF v_existe_venta = 0 THEN
        RAISE_APPLICATION_ERROR(-20042, 'La venta ' || p_id_venta || ' no existe.');
END IF;
SELECT stock INTO v_stock_actual
FROM productos WHERE id_producto = p_id_producto;
IF v_stock_actual < p_cantidad THEN
        RAISE_APPLICATION_ERROR(-20043,
            'Stock insuficiente. Disponible: ' || v_stock_actual || ', Solicitado: ' || p_cantidad);
END IF;
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (p_id_venta, p_id_producto, p_cantidad, p_precio_unitario);
UPDATE productos SET stock = stock - p_cantidad WHERE id_producto = p_id_producto;
COMMIT;
DBMS_OUTPUT.PUT_LINE('Detalle añadido. Subtotal: ' || (p_cantidad * p_precio_unitario) || 'EUR');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Producto no encontrado.');
ROLLBACK;
WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al añadir detalle: ' || SQLERRM);
ROLLBACK;
END;
/


BEGIN
    añadir_detalle(1, 1, 2, 89.99);
END;
/



CREATE OR REPLACE PROCEDURE desglose_ventas IS
    CURSOR c_ventas IS
SELECT id_venta FROM ventas ORDER BY id_venta;
CURSOR c_lineas (p_venta NUMBER) IS
SELECT p.nombre, dv.cantidad, dv.precio_unitario,
       dv.cantidad * dv.precio_unitario AS subtotal
FROM detalle_venta dv
         JOIN productos p ON dv.id_producto = p.id_producto
WHERE dv.id_venta = p_venta;
v_total_venta NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== DESGLOSE COMPLETO DE VENTAS ===');
FOR v IN c_ventas LOOP
        v_total_venta := 0;
        DBMS_OUTPUT.PUT_LINE('Venta ' || v.id_venta);
FOR l IN c_lineas(v.id_venta) LOOP
            DBMS_OUTPUT.PUT_LINE(
                '  ' || l.nombre || ' x' || l.cantidad ||
                ' @ ' || l.precio_unitario || 'EUR = ' || l.subtotal || 'EUR'
            );
            v_total_venta := v_total_venta + l.subtotal;
END LOOP;
        DBMS_OUTPUT.PUT_LINE('  TOTAL VENTA: ' || v_total_venta || 'EUR');
END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/



BEGIN
    desglose_ventas;
END;
/




CREATE OR REPLACE FUNCTION calcular_total_venta (
    p_id_venta IN NUMBER
) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
SELECT NVL(SUM(cantidad * precio_unitario), 0)
INTO v_total
FROM detalle_venta
WHERE id_venta = p_id_venta;
RETURN ROUND(v_total, 2);
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/



DECLARE
v_total NUMBER;
BEGIN
    v_total := calcular_total_venta(1);
    DBMS_OUTPUT.PUT_LINE('Total calculado de la venta 1: ' || v_total || 'EUR');
END;
/




CREATE OR REPLACE FUNCTION contar_lineas_venta (
    p_id_venta IN NUMBER
) RETURN NUMBER IS
    v_lineas NUMBER;
BEGIN
SELECT COUNT(*) INTO v_lineas
FROM detalle_venta
WHERE id_venta = p_id_venta;
RETURN v_lineas;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/



DECLARE
v_lineas NUMBER;
BEGIN
    v_lineas := contar_lineas_venta(1);
    DBMS_OUTPUT.PUT_LINE('Lineas en la venta 1: ' || v_lineas);
END;
/