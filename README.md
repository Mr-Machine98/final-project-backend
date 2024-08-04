# React + Semantic Ui React + Java SpringBoot + Jwt + PostgreSQL + PL/pgSQL

Hola!

En este repositorio he subido la parte backend de mi proyecto, básicamente este trata de un sistema de información
que simula la compra de items de una tienda virtual como un "Carrito de Compras", además, realiza
la admministración de usuarios almacenados en el sistema. Esto es, porque para ingresar al sistema
debes tener un usuario, y dependiendo si eres administrador o no, podrás realizar todo el proceso de lectura,
escritura, editado y borrado de los usuarios. Lo que significa que el sistema detecta el rol del usuario que ha
iniciado sesión y limita el acceso a recursos de este dependiendo de su rol.

La parte frontend está en el siguiente link  [final-project-frontend](https://github.com/Mr-Machine98/final-project/).

# Instalación
Básicamente clonas el repo y abres el proyecto con tu IDE para java y descargas las dependecias de GRADLE.

> [!IMPORTANT]
> El programa ejecuta un proceso almacenado, el se encuentra en la ruta **/api-final-app/src/main/resources/static/procedure.sql**, debe ejecutarse después de haber ejecutado la app, solo es abrir una hoja de sql y ejecutar:
```SQL
CREATE OR REPLACE PROCEDURE public.my_procedure(
	IN input_array log_sale[])
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    -- Procesa el array
    FOR i IN 1 .. array_length(input_array, 1) LOOP
        -- Realiza alguna operación con input_array[i]
        insert into log_ventas(quantity, sub_total, sale_id, date_sale, product, owner) 
					values (
						cast(replace(input_array[i].quantity, '''', '') as integer),
						cast(replace(input_array[i].sub_total, '''', '') as numeric),
						cast(replace(input_array[i].sale_id, '''', '') as bigint),
						cast(replace(input_array[i].date_sale, '''', '') as varchar),
						cast(replace(input_array[i].product, '''', '') as varchar),
						cast(replace(input_array[i].owner, '''', '') as varchar)
					);
    END LOOP;
END;
$BODY$;
```
