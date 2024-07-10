create database GuardadoDeBarras;
use GuardadoDeBarras;	

create table Usuarios(
	
    id int auto_increment not null primary key,
	nombre varchar(30) not null,
	curso varchar(5) not null,
    nro_Compu int not null,
    descripcion varchar(255),
    nro_Carrito bigint not null,
    entrega datetime
   
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

