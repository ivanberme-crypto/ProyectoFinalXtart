SET SERVEROUTPUT ON;

-- =====================================================
-- 1. CURSOR: LISTAR CLIENTES
-- =====================================================

DECLARE
    CURSOR c_clientes IS
        SELECT id_cliente, nombre, email, telefono, direccion
        FROM clientes;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- LISTADO DE CLIENTES ---');

    FOR cliente IN c_clientes LOOP
        DBMS_OUTPUT.PUT_LINE(
            cliente.id_cliente || ' - ' ||
            cliente.nombre || ' - ' ||
            cliente.email || ' - ' ||
            cliente.telefono || ' - ' ||
            cliente.direccion
        );
    END LOOP;
END;
/


-- =====================================================
-- 2. CURSOR: LISTAR USUARIOS
-- =====================================================

DECLARE
    CURSOR c_usuarios IS
        SELECT id_usuario, nombre, email, rol
        FROM usuarios;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- LISTADO DE USUARIOS ---');

    FOR usuario IN c_usuarios LOOP
        DBMS_OUTPUT.PUT_LINE(
            usuario.id_usuario || ' - ' ||
            usuario.nombre || ' - ' ||
            usuario.email || ' - ' ||
            usuario.rol
        );
    END LOOP;
END;
/


-- =====================================================
-- 3. CURSOR: LISTAR PRODUCTOS
-- =====================================================

DECLARE
    CURSOR c_productos IS
        SELECT id_producto, nombre, marca, modelo, precio, stock
        FROM productos;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- LISTADO DE PRODUCTOS ---');

    FOR producto IN c_productos LOOP
        DBMS_OUTPUT.PUT_LINE(
            producto.id_producto || ' - ' ||
            producto.nombre || ' - ' ||
            producto.marca || ' - ' ||
            producto.modelo || ' - ' ||
            producto.precio || '€ - Stock: ' ||
            producto.stock
        );
    END LOOP;
END;
/


-- =====================================================
-- 4. CURSOR: LISTAR VENTAS CON CLIENTE Y USUARIO
-- =====================================================

DECLARE
    CURSOR c_ventas IS
        SELECT v.id_venta, c.nombre AS cliente, u.nombre AS usuario,
               v.estado, v.total
        FROM ventas v
        JOIN clientes c ON v.id_cliente = c.id_cliente
        JOIN usuarios u ON v.id_usuario = u.id_usuario;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- LISTADO DE VENTAS ---');

    FOR venta IN c_ventas LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Venta ' || venta.id_venta ||
            ' | Cliente: ' || venta.cliente ||
            ' | Usuario: ' || venta.usuario ||
            ' | Estado: ' || venta.estado ||
            ' | Total: ' || venta.total || '€'
        );
    END LOOP;
END;
/


-- =====================================================
-- 5. CURSOR: DETALLE DE VENTAS
-- =====================================================

DECLARE
    CURSOR c_detalle IS
        SELECT dv.id_detalle, p.nombre AS producto,
               dv.cantidad, dv.precio_unitario,
               dv.cantidad * dv.precio_unitario AS subtotal
        FROM detalle_venta dv
        JOIN productos p ON dv.id_producto = p.id_producto;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- DETALLE DE VENTAS ---');

    FOR detalle IN c_detalle LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Detalle ' || detalle.id_detalle ||
            ' | Producto: ' || detalle.producto ||
            ' | Cantidad: ' || detalle.cantidad ||
            ' | Precio: ' || detalle.precio_unitario ||
            ' | Subtotal: ' || detalle.subtotal || '€'
        );
    END LOOP;
END;
/


-- =====================================================
-- PROCEDIMIENTO 1: MOSTRAR PRODUCTOS CON STOCK BAJO
-- =====================================================

CREATE OR REPLACE PROCEDURE mostrar_stock_bajo IS
    CURSOR c_stock IS
        SELECT nombre, stock
        FROM productos
        WHERE stock < 10;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- PRODUCTOS CON STOCK BAJO ---');

    FOR producto IN c_stock LOOP
        DBMS_OUTPUT.PUT_LINE(
            producto.nombre || ' tiene solo ' || producto.stock || ' unidades.'
        );
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al mostrar stock bajo.');
END;
/


-- Ejecutar procedimiento
BEGIN
    mostrar_stock_bajo;
END;
/


-- =====================================================
-- PROCEDIMIENTO 2: SUBIR PRECIO DE PRODUCTOS POR MARCA
-- =====================================================

CREATE OR REPLACE PROCEDURE subir_precio_marca (
    p_marca IN VARCHAR2,
    p_incremento IN NUMBER
) IS
    v_productos_actualizados NUMBER;

BEGIN
    UPDATE productos
    SET precio = precio + p_incremento
    WHERE UPPER(marca) = UPPER(p_marca);

    v_productos_actualizados := SQL%ROWCOUNT;

    DBMS_OUTPUT.PUT_LINE(
        'Productos actualizados de la marca ' ||
        p_marca || ': ' || v_productos_actualizados
    );

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al actualizar precios.');
        ROLLBACK;
END;
/


-- Ejecutar procedimiento
BEGIN
    subir_precio_marca('Nike', 5);
END;
/


-- =====================================================
-- FUNCIÓN 1: CALCULAR VALOR TOTAL DEL STOCK
-- =====================================================

CREATE OR REPLACE FUNCTION calcular_valor_stock
RETURN NUMBER
IS
    v_total NUMBER;

BEGIN
    SELECT SUM(precio * stock)
    INTO v_total
    FROM productos;

    RETURN v_total;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN -1;
END;
/


-- Probar función
DECLARE
    v_resultado NUMBER;
BEGIN
    v_resultado := calcular_valor_stock;
    DBMS_OUTPUT.PUT_LINE('Valor total del stock: ' || v_resultado || '€');
END;
/


-- =====================================================
-- FUNCIÓN 2: CONTAR VENTAS POR ESTADO
-- =====================================================

CREATE OR REPLACE FUNCTION contar_ventas_estado (
    p_estado IN VARCHAR2
)
RETURN NUMBER
IS
    v_total NUMBER;

BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM ventas
    WHERE UPPER(estado) = UPPER(p_estado);

    RETURN v_total;

EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/


-- Probar función
DECLARE
    v_total NUMBER;
BEGIN
    v_total := contar_ventas_estado('Completada');
    DBMS_OUTPUT.PUT_LINE('Ventas completadas: ' || v_total);
END;
/


-- =====================================================
-- PROCEDIMIENTO 3: MOSTRAR CLIENTES CON BUCLE WHILE
-- =====================================================

CREATE OR REPLACE PROCEDURE contar_clientes IS
    v_total NUMBER;
    v_contador NUMBER := 1;

BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM clientes;

    DBMS_OUTPUT.PUT_LINE('Total de clientes: ' || v_total);

    WHILE v_contador <= v_total LOOP
        DBMS_OUTPUT.PUT_LINE('Cliente número: ' || v_contador);
        v_contador := v_contador + 1;
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al contar clientes.');
END;
/


BEGIN
    contar_clientes;
END;
/


-- =====================================================
-- PROCEDIMIENTO 4: MOSTRAR PRODUCTOS CAROS
-- =====================================================

CREATE OR REPLACE PROCEDURE productos_caros (
    p_precio_minimo IN NUMBER
) IS
    CURSOR c_caros IS
        SELECT nombre, precio
        FROM productos
        WHERE precio >= p_precio_minimo;

BEGIN
    DBMS_OUTPUT.PUT_LINE('--- PRODUCTOS CAROS ---');

    FOR producto IN c_caros LOOP
        DBMS_OUTPUT.PUT_LINE(
            producto.nombre || ' cuesta ' || producto.precio || '€'
        );
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al mostrar productos caros.');
END;
/


BEGIN
    productos_caros(180);
END;
/
-- Added EXCEPTION blocks to all procedures
