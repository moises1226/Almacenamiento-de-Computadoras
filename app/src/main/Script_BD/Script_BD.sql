CREATE DATABASE GuardadoDeBarras;
USE GuardadoDeBarras;

CREATE TABLE usuario (
    IdUsuario INT AUTO_INCREMENT NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    DNI CHAR(8) ,
    Curso VARCHAR(10) NOT NULL,
    PRIMARY KEY (IdUsuario)
);

CREATE TABLE computadora (
    IdCompu INT AUTO_INCREMENT NOT NULL,
    NroCompu SMALLINT UNIQUE NOT NULL,
    CodigoBarras VARCHAR(16) UNIQUE NOT NULL,
    PRIMARY KEY (IdCompu)
);

CREATE TABLE carrito (
    IdCarrito INT AUTO_INCREMENT NOT NULL,
    NroCarrito INT NOT NULL,
    IdCompu INT NOT NULL,
    PRIMARY KEY (IdCarrito),
    FOREIGN KEY (IdCompu) REFERENCES computadora(IdCompu)
);

CREATE TABLE retiro (
    IdRetiro INT AUTO_INCREMENT NOT NULL,
    IdUser INT NOT NULL,
    IdCarrito INT NOT NULL,
    FechaRetiro DATETIME NOT NULL,
    Descripcion VARCHAR(100),
    PRIMARY KEY (IdRetiro),
    FOREIGN KEY (IdUser) REFERENCES usuario(IdUsuario),
    FOREIGN KEY (IdCarrito) REFERENCES carrito(IdCarrito)
);

-- Ingresando las netbooks
INSERT INTO computadora (NroCompu, CodigoBarras) VALUES
('2102', 'PSBA210102014531'),
('2101', 'PSBA210102014508'),
('2104', 'PSBA210102014721'),
('2105', 'PSBA21012013664'),
('2106', 'PSBA210102014722'),
('2107', 'PSBA210102014410'),
('2108', 'PSBA210102014728'),
('2109', 'PSBA210102021652'),
('2111', 'PSBA210102021246'),
('2112', 'PSBA210102014648'),
('2113', 'PSBA210102016325'),
('2115', 'PSBA210102021653'),
('2116', 'PSBA210102022145'),
('2117', 'PSBA210102010709'),
('2118', 'PSBA210102013881'),
('2119', 'PSBA210102014301'),
('2120', 'PSBA210102011855'),
('2121', 'PSBA210102014982'),
('2122', 'PSBA210102013946'),
('2123', 'PSBA210102013611'),
('2124', 'PSBA210102013163'),
('2125', 'PSBA210102013164'),
('2126', 'PSBA210102014123'),
('2127', 'PSBA210102013673'),
('2128', 'PSBA210102010716'),
('2129', 'PSBA210102021170'),
('2130', 'PSBA210102021477'),
('2131', 'PSBA210102022108');


-- Ingresando datos en la tabla carrito
INSERT INTO carrito (NroCarrito, IdCompu) VALUES
(2100, 1),  -- PSBA210102014531
(2100, 2),  -- PSBA210102014508
(2100, 3),  -- PSBA210102014721
(2100, 4),  -- PSBA21012013664
(2100, 5),  -- PSBA210102014722
(2100, 6),  -- PSBA210102014410
(2100, 7),  -- PSBA210102014728
(2100, 8),  -- PSBA210102021652
(2100, 9),  -- PSBA210102021246
(2100, 10), -- PSBA210102014648
(2100, 11), -- PSBA210102016325
(2100, 12), -- PSBA210102021653
(2100, 13), -- PSBA210102022145
(2100, 14), -- PSBA210102010709
(2100, 15), -- PSBA210102013881
(2100, 16), -- PSBA210102014301
(2100, 17), -- PSBA210102011855
(2100, 18), -- PSBA210102014982
(2100, 19), -- PSBA210102013946
(2100, 20), -- PSBA210102013611
(2100, 21), -- PSBA210102013163
(2100, 22), -- PSBA210102013164
(2100, 23), -- PSBA210102014123
(2100, 24), -- PSBA210102013673
(2100, 25), -- PSBA210102010716
(2100, 26), -- PSBA210102021170
(2100, 27), -- PSBA210102021477
(2100, 28); -- PSBA210102022108
