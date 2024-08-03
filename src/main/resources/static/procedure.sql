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