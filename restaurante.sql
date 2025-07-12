
CREATE DATABASE restaurante
go

use restaurante
go


-- Usar la base de datos creada
USE restaurante;
GO

-- Configuración inicial (opcional)
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;

-- Crear la tabla `config`
CREATE TABLE config (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ruc NVARCHAR(15) NOT NULL,
    nombre NVARCHAR(255) NOT NULL,
    telefono NVARCHAR(11) NOT NULL,
    direccion NVARCHAR(MAX) NOT NULL,
    mensaje NVARCHAR(255) NOT NULL
);


-- Crear la tabla `salas`
CREATE TABLE salas (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    mesas INT NOT NULL
);


-- Crear la tabla `usuarios`
CREATE TABLE usuarios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(200) NOT NULL,
    correo NVARCHAR(200) NOT NULL,
    pass NVARCHAR(50) NOT NULL,
    rol NVARCHAR(20) NOT NULL
);



-- Crear la tabla `platos`
CREATE TABLE platos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(200) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    fecha DATE
);

-- Crear la tabla `pedidos`
CREATE TABLE pedidos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_sala INT NOT NULL,
    num_mesa INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT GETDATE(),
    total DECIMAL(10,2) NOT NULL,
    estado NVARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    usuario NVARCHAR(100) NOT NULL,
    FOREIGN KEY (id_sala) REFERENCES salas(id)
);

-- Crear la tabla `detalle_pedidos`
CREATE TABLE detalle_pedidos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(200) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL,
    comentario NVARCHAR(MAX),
    id_pedido INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
);


-- Insertar datos en `config`
INSERT INTO config (ruc, nombre, telefono, direccion, mensaje) 
VALUES ('65479877', 'Machis Pollos y parrillas', '912426667', 'Lima - Perú', 'Gracias por la compra');


-- Insertar datos en `salas`
INSERT INTO salas (nombre, mesas)
VALUES 
('SALA PRINCIPAL', 15),
('SEGUNDO PISO', 10);


-- Insertar datos en `usuarios`
INSERT INTO usuarios (nombre, correo, pass, rol)
VALUES 
('admin', 'admin', 'admin', 'Administrador');


-- Insertar datos en `platos`
-- INSERT INTO platos (nombre, precio, fecha)
-- VALUES 
-- ('ARROZ CON POLLO', 10.00, '2024-05-17'),
-- ('CHAUFA', 20.00, '2024-05-17'),
-- ('GASEOSA COCA COLA 1.5 LITROS', 8.00, '2024-05-17');

-- Insertar datos en `pedidos`
-- INSERT INTO pedidos (id_sala, num_mesa, fecha, total, estado, usuario)
-- VALUES 
-- (1, 2, '2024-05-18 00:31:52', 78.00, 'FINALIZADO', 'LUIS SIFUENTES'),
-- (2, 8, '2024-05-18 00:32:20', 30.00, 'PENDIENTE', 'ANGEL GARCIA'),
-- (1, 9, '2024-05-18 00:32:29', 28.00, 'PENDIENTE', 'LENIN TORRES'),
-- (1, 11, '2024-05-18 01:04:47', 20.00, 'PENDIENTE', 'FABRIZIO CIEZA');
