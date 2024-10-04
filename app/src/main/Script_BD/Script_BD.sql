create database GuardadoDeBarras;
use GuardadoDeBarras;	

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_User INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    curso VARCHAR(5) NOT NULL,
    descripcion VARCHAR(255),
    nro_Carrito INT NOT NULL
);

-- Tabla de Netbooks
CREATE TABLE Netbooks (
    id_Net INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    codigo_barras VARCHAR(16) NOT NULL UNIQUE,
    CONSTRAINT chk_codigo_barras CHECK (CHAR_LENGTH(codigo_barras) = 16)
);

-- Tabla de Carrito con relación a Usuarios
CREATE TABLE Carrito (
    id_Carrito INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    id_User INT NOT NULL,
    num_carrito VARCHAR(2),
    cantidad_netbook INT,
    FOREIGN KEY (id_User) REFERENCES Usuarios(id_User) ON DELETE CASCADE
);

-- Tabla intermedia para la relación muchos a muchos entre Carrito y Netbooks
CREATE TABLE Carrito_Netbooks (
    id_Carrito INT NOT NULL,
    id_Net INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (id_Carrito, id_Net),
    FOREIGN KEY (id_Carrito) REFERENCES Carrito(id_Carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_Net) REFERENCES Netbooks(id_Net) ON DELETE CASCADE
);


-- ESTO ELIMINA LA TABLA!!!!!
drop table Usuarios;

-- elimino la restrcion para si poder eliminar las tablas
SET SQL_SAFE_UPDATES = 0;
-- verfico si la restriccion esta activa
SHOW VARIABLES LIKE 'sql_safe_updates';



select * from Usuarios;

-- el truncate me elimina todos los registros pero el autoincement me 
-- lo deja en 1 en vez de sigueinte numero al ingresar otra vez el numero
truncate table Usuarios;

