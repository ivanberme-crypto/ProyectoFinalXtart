class Cliente {
  constructor(id, nombre, email, telefono, direccion) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.direccion = direccion;
  }
}

class Producto {
  constructor(id, nombre, descripcion, marca, talla, color, precio, stock, categoria) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.marca = marca;
    this.talla = talla;
    this.color = color;
    this.precio = parseFloat(precio);
    this.stock = parseInt(stock);
    this.categoria = categoria;
  }
}

class Venta {
  constructor(id, idCliente, idUsuario, estado, total) {
    this.id = id;
    this.idCliente = idCliente;
    this.idUsuario = idUsuario;
    this.estado = estado;
    this.total = parseFloat(total);
    this.fecha = new Date().toLocaleDateString("es-ES");
  }
}

class DetalleVenta {
  constructor(id, idVenta, idProducto, quantity, precioUnitario) {
    this.id = id;
    this.idVenta = parseInt(idVenta);
    this.idProducto = parseInt(idProducto);
    this.cantidad = parseInt(quantity);
    this.precioUnitario = parseFloat(precioUnitario);
  }
}

class Usuario {
  constructor(id, nombre, email, rol, passwordHash) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.rol = rol;
    this.passwordHash = passwordHash;
  }
}

const DATA_DEFAULT = {
  clientes: [
    new Cliente(1,  "Messi",           "messi@gmail.com",       "600111222", "Madrid"),
    new Cliente(2,  "Lamine Yamal",    "lamine@gmail.com",      "611111111", "Barcelona"),
    new Cliente(3,  "Kylian Mbappe",   "mbappe@gmail.com",      "600100002", "Madrid Centro"),
    new Cliente(4,  "Jude Bellingham", "jude@realmadrid.com",   "600100003", "Madrid"),
    new Cliente(5,  "Vinicius Junior", "vinicius@gmail.com",    "622222222", "Madrid"),
    new Cliente(6,  "Pedri Gonzalez",  "pedri@gmail.com",       "600100005", "Canarias"),
    new Cliente(7,  "Ademola Lookman", "lookman@gmail.com",     "600200002", "Nigeria"),
    new Cliente(8,  "Marc Pubill",     "pubill@gmail.com",      "600200003", "Barcelona"),
  ],
  productos: [
    new Producto(1,  "Nike Air Max Plus TN",   "Air Max Plus TN",       "Nike",        42, "Negro",       179.99, 10, "Lifestyle"),
    new Producto(2,  "Nike Shox TL",           "Shox TL",               "Nike",        42, "Negro",       189.99, 13, "Lifestyle"),
    new Producto(3,  "Nike P-6000",            "P-6000",                "Nike",        41, "Plateado",    129.99, 14, "Running"),
    new Producto(4,  "Nike Zoom Vomero 5",     "Zoom Vomero 5",         "Nike",        43, "Gris Plata",  169.99,  9, "Running"),
    new Producto(5,  "Adidas Yeezy Boost 350", "Yeezy Boost 350",       "Adidas",      44, "Cream",       289.99,  3, "Streetwear"),
    new Producto(6,  "New Balance 2002R",      "2002R",                 "New Balance", 42, "Gris",        159.99, 10, "Premium"),
    new Producto(7,  "New Balance 530",        "530",                   "New Balance", 41, "Silver",      119.99, 13, "Casual"),
    new Producto(8,  "Salomon ACS Pro",        "ACS Pro",               "Salomon",     43, "Black",       209.99,  4, "Trail"),
    new Producto(9,  "Jordan 1 High Chicago",  "Jordan 1 High Chicago", "Jordan",      43, "Rojo/Blanco", 319.99,  2, "Basket"),
  ],
  ventas: [
    new Venta(1, 2, 1, "Enviada",    189.99),
    new Venta(2, 3, 2, "Preparando", 249.99),
    new Venta(3, 4, 1, "Preparando", 129.99),
    new Venta(4, 5, 2, "Completada", 169.99),
    new Venta(5, 6, 1, "Pendiente",  159.99),
  ],
  detalles: [
    new DetalleVenta(1, 1, 2, 1, 189.99),
    new DetalleVenta(2, 2, 3, 1, 129.99),
    new DetalleVenta(3, 3, 4, 1, 169.99),
    new DetalleVenta(4, 4, 5, 1,  99.99),
  ],
  usuarios: [
    new Usuario(1, "Javier Vega",  "javier@tienda.com", "Administrador", "pass1"),
    new Usuario(2, "Ivan Bermejo", "ivan@tienda.com",   "Administrador", "pass2"),
  ],
  nextId: { clientes: 9, productos: 10, ventas: 6, detalles: 5, usuarios: 3 }
};

let db = JSON.parse(sessionStorage.getItem("crm")) || DATA_DEFAULT;

const save = () => {
    sessionStorage.setItem("crm", JSON.stringify(db));
    updateCounts();
};

const updateCounts = () => {
    document.getElementById("clientes-count").textContent = `${db.clientes.length} clientes`;
    document.getElementById("productos-count").textContent = `${db.productos.length} productos`;
    document.getElementById("ventas-count").textContent = `${db.ventas.length} ventas`;
    document.getElementById("detalles-count").textContent = `${db.detalles?.length || 0} detalles`;
    document.getElementById("usuarios-count").textContent = `${db.usuarios.length} usuarios`;

    const totalFacturado = db.ventas.reduce((acc, v) => acc + parseFloat(v.total || 0), 0);
    const totalStock = db.productos.reduce((acc, p) => acc + parseInt(p.stock || 0), 0);

    document.getElementById("dash-facturacion").textContent = `${totalFacturado.toFixed(2)}€`;
    document.getElementById("dash-ventas").textContent = db.ventas.length;
    document.getElementById("dash-stock").textContent = totalStock;
    document.getElementById("dash-clientes").textContent = db.clientes.length;
};

function procesarColeccionConCallback(coleccion, funcionCallback) {
  return coleccion.filter(funcionCallback);
}

document.querySelectorAll(".header__btn").forEach(btn => {
  btn.onclick = () => {
    document.querySelectorAll(".header__btn").forEach(b => b.classList.remove("header__btn--activo"));
    document.querySelectorAll(".seccion").forEach(s => s.classList.add("seccion--oculta"));

    btn.classList.add("header__btn--activo");
    document.getElementById("seccion-" + btn.dataset.seccion).classList.remove("seccion--oculta");
  };
});

const modal = document.getElementById("modal");
const title = document.getElementById("modal-title");
const body = document.getElementById("modal-body");
let cb = null;

function openModal(t, html, callback) {
  title.textContent = t;
  body.innerHTML = html;
  cb = callback;
  modal.classList.add("modal--activo");
}

function closeModal() {
  modal.classList.remove("modal--activo");
  cb = null;
}

document.getElementById("modal-close").onclick = closeModal;
document.getElementById("modal-cancel").onclick = closeModal;
document.getElementById("modal-save").onclick = () => { if(cb) cb(); };

window.onclick = (event) => {
  if (event.target === modal) {
    closeModal();
  }
};

const actualizarAnchoVentana = () => {
  const widthSpan = document.getElementById("window-width");
  if (widthSpan) widthSpan.textContent = window.innerWidth;
};
window.onresize = actualizarAnchoVentana;

function toast(msg) {
  const t = document.getElementById("toast");
  t.textContent = msg;
  t.classList.add("toast--activo");
  setTimeout(() => t.classList.remove("toast--activo"), 2000);
}

function renderClientes() {
  const tbody = document.getElementById("tabla-clientes");
  tbody.innerHTML = db.clientes.map(c => `
    <tr>
      <td>${c.id}</td><td>${c.nombre}</td><td>${c.email}</td><td>${c.telefono}</td><td>${c.direccion}</td>
      <td class="acciones">
        <button onclick="editCliente(${c.id})">Editar</button>
        <button onclick="deleteCliente(${c.id})">Borrar</button>
      </td>
    </tr>`).join("");
}

document.getElementById("btn-nuevo-cliente").onclick = () => {
  openModal("Nuevo Cliente", `
    <input id="n" class="form__input" placeholder="Nombre">
    <input id="e" class="form__input" placeholder="Email">
    <input id="t" class="form__input" placeholder="Teléfono">
    <input id="d" class="form__input" placeholder="Dirección">
  `, () => {
    db.clientes.push(new Cliente(db.nextId.clientes++, n.value, e.value, t.value, d.value));
    save(); renderClientes(); closeModal(); toast("Cliente añadido");
  });
};

window.editCliente = id => {
  const c = db.clientes.find(item => item.id === id);
  openModal("Editar Cliente", `
    <input id="n" class="form__input" value="${c.nombre}" placeholder="Nombre">
    <input id="e" class="form__input" value="${c.email}" placeholder="Email">
    <input id="t" class="form__input" value="${c.telefono}" placeholder="Teléfono">
    <input id="d" class="form__input" value="${c.direccion}" placeholder="Dirección">
  `, () => {
    c.nombre = n.value; c.email = e.value; c.telefono = t.value; c.direccion = d.value;
    save(); renderClientes(); closeModal(); toast("Cliente actualizado");
  });
};

window.deleteCliente = id => {
  db.clientes = procesarColeccionConCallback(db.clientes, c => c.id !== id);
  save(); renderClientes();
};

function renderProductos() {
  const tbody = document.getElementById("tabla-productos");
  tbody.innerHTML = db.productos.map(p => `
    <tr>
      <td>${p.id}</td><td>${p.nombre}</td><td>${p.descripcion || ''}</td><td>${p.marca}</td><td>${p.talla}</td><td>${p.color}</td><td>${p.precio}€</td><td>${p.stock}</td><td>${p.categoria}</td>
      <td class="acciones">
        <button onclick="editProducto(${p.id})">Editar</button>
        <button onclick="deleteProducto(${p.id})">Borrar</button>
      </td>
    </tr>`).join("");
}

document.getElementById("btn-nuevo-producto").onclick = () => {
  openModal("Nuevo Producto", `
    <input id="p_nom" class="form__input" placeholder="Nombre">
    <input id="p_desc" class="form__input" placeholder="Descripción">
    <input id="p_mar" class="form__input" placeholder="Marca">
    <input id="p_tal" class="form__input" placeholder="Talla">
    <input id="p_col" class="form__input" placeholder="Color">
    <input id="p_pre" class="form__input" type="number" placeholder="Precio">
    <input id="p_sto" class="form__input" type="number" placeholder="Stock">
    <input id="p_cat" class="form__input" placeholder="Categoría">
  `, () => {
    db.productos.push(new Producto(db.nextId.productos++, p_nom.value, p_desc.value, p_mar.value, p_tal.value, p_col.value, p_pre.value, p_sto.value, p_cat.value));
    save(); renderProductos(); closeModal(); toast("Producto añadido");
  });
};

window.editProducto = id => {
  const p = db.productos.find(item => item.id === id);
  openModal("Editar Producto", `
    <input id="p_nom" class="form__input" value="${p.nombre}" placeholder="Nombre">
    <input id="p_desc" class="form__input" value="${p.descripcion || ''}" placeholder="Descripción">
    <input id="p_mar" class="form__input" value="${p.marca}" placeholder="Marca">
    <input id="p_tal" class="form__input" value="${p.talla}" placeholder="Talla">
    <input id="p_col" class="form__input" value="${p.color}" placeholder="Color">
    <input id="p_pre" class="form__input" type="number" value="${p.precio}" placeholder="Precio">
    <input id="p_sto" class="form__input" type="number" value="${p.stock}" placeholder="Stock">
    <input id="p_cat" class="form__input" value="${p.categoria}" placeholder="Categoría">
  `, () => {
    p.nombre = p_nom.value; p.descripcion = p_desc.value; p.marca = p_mar.value; p.talla = p_tal.value; p.color = p_col.value; p.precio = p_pre.value; p.stock = p_sto.value; p.categoria = p_cat.value;
    save(); renderProductos(); closeModal(); toast("Producto actualizado");
  });
};

window.deleteProducto = id => {
  db.productos = procesarColeccionConCallback(db.productos, p => p.id !== id);
  save(); renderProductos();
};

function renderVentas() {
  const tbody = document.getElementById("tabla-ventas");
  tbody.innerHTML = db.ventas.map(v => `
    <tr>
      <td>${v.id}</td><td>${v.idCliente}</td><td>Usuario ${v.idUsuario}</td><td>${v.estado}</td><td>${v.total}€</td><td>${v.fecha}</td>
      <td class="acciones">
        <button onclick="editVenta(${v.id})">Editar</button>
        <button onclick="deleteVenta(${v.id})">Borrar</button>
      </td>
    </tr>`).join("");
}

document.getElementById("btn-nueva-venta").onclick = () => {
    openModal("Nueva Venta", `
      <input id="v_cli" class="form__input" placeholder="ID Cliente">
      <input id="v_est" class="form__input" value="Completada" placeholder="Estado">
      <input id="v_tot" class="form__input" type="number" placeholder="Total">
    `, () => {
      db.ventas.push(new Venta(db.nextId.ventas++, v_cli.value, 1, v_est.value, v_tot.value));
      save(); renderVentas(); closeModal(); toast("Venta registrada");
    });
};

window.editVenta = id => {
  const v = db.ventas.find(item => item.id === id);
  openModal("Editar Venta", `
    <input id="v_cli" class="form__input" value="${v.idCliente}" placeholder="ID Cliente">
    <input id="v_est" class="form__input" value="${v.estado}" placeholder="Estado">
    <input id="v_tot" class="form__input" type="number" value="${v.total}" placeholder="Total">
  `, () => {
    v.idCliente = v_cli.value; v.estado = v_est.value; v.total = v_tot.value;
    save(); renderVentas(); closeModal(); toast("Venta actualizada");
  });
};

window.deleteVenta = id => {
    db.ventas = procesarColeccionConCallback(db.ventas, v => v.id !== id);
    save(); renderVentas();
};

function renderDetalles() {
  const tbody = document.getElementById("tabla-detalles");
  if (!db.detalles) db.detalles = [];
  tbody.innerHTML = db.detalles.map(d => `
    <tr>
      <td>${d.id}</td><td>${d.idVenta}</td><td>${d.idProducto}</td><td>${d.cantidad}</td><td>${d.precioUnitario}€</td>
      <td class="acciones">
        <button onclick="editDetalle(${d.id})">Editar</button>
        <button onclick="deleteDetalle(${d.id})">Borrar</button>
      </td>
    </tr>`).join("");
}

document.getElementById("btn-nuevo-detalle").onclick = () => {
  openModal("Nuevo Detalle de Venta", `
    <input id="d_vnt" class="form__input" type="number" placeholder="ID Venta">
    <input id="d_prd" class="form__input" type="number" placeholder="ID Producto">
    <input id="d_can" class="form__input" type="number" placeholder="Cantidad">
    <input id="d_pre" class="form__input" type="number" placeholder="Precio Unitario">
  `, () => {
    if(!db.detalles) db.detalles = [];
    db.detalles.push(new DetalleVenta(db.nextId.detalles++, d_vnt.value, d_prd.value, d_can.value, d_pre.value));
    save(); renderDetalles(); closeModal(); toast("Detail añadido");
  });
};

window.editDetalle = id => {
  const d = db.detalles.find(item => item.id === id);
  openModal("Editar Detalle Venta", `
    <input id="d_vnt" class="form__input" type="number" value="${d.idVenta}" placeholder="ID Venta">
    <input id="d_prd" class="form__input" type="number" value="${d.idProducto}" placeholder="ID Producto">
    <input id="d_can" class="form__input" type="number" value="${d.cantidad}" placeholder="Cantidad">
    <input id="d_pre" class="form__input" type="number" value="${d.precioUnitario}" placeholder="Precio Unitario">
  `, () => {
    d.idVenta = d_vnt.value; d.idProducto = d_prd.value; d.cantidad = d_can.value; d.precioUnitario = d_pre.value;
    save(); renderDetalles(); closeModal(); toast("Detalle actualizado");
  });
};

window.deleteDetalle = id => {
  db.detalles = procesarColeccionConCallback(db.detalles, d => d.id !== id);
  save(); renderDetalles();
};

function renderUsuarios() {
  const tbody = document.getElementById("tabla-usuarios");
  tbody.innerHTML = db.usuarios.map(u => `
    <tr>
      <td>${u.id}</td><td>${u.nombre}</td><td>${u.email}</td><td>${u.rol}</td><td>${u.passwordHash || 'N/A'}</td>
      <td class="acciones">
        <button onclick="editUsuario(${u.id})">Editar</button>
        <button onclick="deleteUsuario(${u.id})">Borrar</button>
      </td>
    </tr>`).join("");
}

document.getElementById("btn-nuevo-usuario").onclick = () => {
    openModal("Nuevo Usuario", `
      <input id="u_nom" class="form__input" placeholder="Nombre">
      <input id="u_ema" class="form__input" placeholder="Email">
      <select id="u_rol" class="form__select">
        <option>Vendedor</option>
        <option>Administrador</option>
      </select>
      <input id="u_pass" class="form__input" placeholder="Password Hash">
    `, () => {
      db.usuarios.push(new Usuario(db.nextId.usuarios++, u_nom.value, u_ema.value, u_rol.value, u_pass.value));
      save(); renderUsuarios(); closeModal(); toast("Usuario creado");
    });
};

window.editUsuario = id => {
  const u = db.usuarios.find(item => item.id === id);
  openModal("Editar Usuario", `
    <input id="u_nom" class="form__input" value="${u.nombre}" placeholder="Nombre">
    <input id="u_ema" class="form__input" value="${u.email}" placeholder="Email">
    <select id="u_rol" class="form__select">
      <option ${u.rol === 'Vendedor' ? 'selected' : ''}>Vendedor</option>
      <option ${u.rol === 'Administrador' ? 'selected' : ''}>Administrador</option>
    </select>
    <input id="u_pass" class="form__input" value="${u.passwordHash || ''}" placeholder="Password Hash">
  `, () => {
    u.nombre = u_nom.value; u.email = u_ema.value; u.rol = u_rol.value; u.passwordHash = u_pass.value;
    save(); renderUsuarios(); closeModal(); toast("Usuario actualizado");
  });
};

window.deleteUsuario = id => {
    db.usuarios = procesarColeccionConCallback(db.usuarios, u => u.id !== id);
    save(); renderUsuarios();
};

function mostrarFechaActual() {
  const fechaEl = document.getElementById("fecha-actual");
  if (fechaEl) {
    const opciones = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    fechaEl.textContent = new Date().toLocaleDateString("es-ES", opciones);
  }
}

renderClientes();
renderProductos();
renderVentas();
renderDetalles();
renderUsuarios();
updateCounts();
actualizarAnchoVentana();
mostrarFechaActual();
// Added SessionStorage persistence for navigation state
