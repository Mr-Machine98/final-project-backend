-- ADD MOTOS
INSERT INTO productos (id, name, model, price, description, url_img) VALUES (1, 'Suzuki Dr 150', 'Model 2025', '12999000', 'Suzuki evoluciona con la nueva DR150 FI ABS que desafía todo terreno como lo hacen sus hermanas mayores; su estilo, su look moderno e increíble comodidad, tanto en trayectos largos como en ciudad, entregan una conducción con bajas vibraciones y gran suavidad en la suspensión. La nueva DR150 FI ABS puede rodar sobre varios terrenos para que la aventura continúe cuando el asfalto termine.', 'https://suzuki.com.co/sites/default/files/2024-04/DR-AZUL-1385x800.png');
INSERT INTO productos (id, name, model, price, description, url_img) VALUES (2, 'Honda XR 150L', 'Model 2025', '10300000', 'La XR 150L es una motocicleta diseñada para durar y enfrentar cualquier terreno, ¡una verdadera on/off!. Sus especificaciones y tecnología la convierten en la opción perfecta doble-propósito.', 'https://motos.honda.com.co/images/cms/honda-xr150l.png');
INSERT INTO productos (id, name, model, price, description, url_img) VALUES (3, 'Yamaha XTZ 150', 'Model 2025', '12900000', 'El modelo llega con un carácter utilitario-deportivo, cómodo y agresivo. El asiento biplaza está diseñado para garantizar una buena sensación para el piloto y una mayor comodidad para el pasajero, el asiento trasero es 95 mm más alto que el asiento del conductor para proporcionarle al pasajero una buena visibilidad hacia adelante.', 'https://www.incolmotos-yamaha.com.co/wp-content/uploads/2023/01/XTZ150_home_carrusel_2.png');
INSERT INTO productos (id, name, model, price, description, url_img) VALUES (4, 'Victory MRX 150', 'Model 2025', '8599000', 'La MRX es la moto enduro de Auteco Mobility hecha para los terrenos más salvajes. Con la MRX podrás disfrutar de todos los terrenos sin tener que preocuparte por nada; con su tablero digital tendrás visibilidad de los instrumentos en todo momento, su altura te da la protección suficiente para sobrepasar cualquier obstáculo, su suspensión mono amortiguador trasera te da la estabilidad y flexibilidad para afrontar los más grandes retos, y sus llantas doble propósito no te abandonan en ningun terreno.', 'https://media.autecomobility.com/recursos/marcas/victory/mrx-150/interna-ropo/moto-victory-mrx-150-fondo-tooltips.webp');

INSERT INTO especificaciones (peso, motor, potencia_motor, torque_motor, product_id) VALUES ('139 Kg', '4 Tiempos SOHC, Refrigerado por aire, cc 149.', '12.20 HP a 8000 RPM', '12.7 Nm a 6000 RPM', 1);
INSERT INTO especificaciones (peso, motor, potencia_motor, torque_motor, product_id) VALUES ('129 Kg', '4 Tiempos SOHC, Refrigerado por aire, cc 149.', '11.7 Hp @ 8000 rpm', '12.1 NM @ 6000 rpm', 2);
INSERT INTO especificaciones (peso, motor, potencia_motor, torque_motor, product_id) VALUES ('131 Kg', '4 Tiempos SOHC , refrigerado por aire, cc 149.', '12.3 Hp a 7500 rpm', '13.1 Nm a 6000 rpm', 3);
INSERT INTO especificaciones (peso, motor, potencia_motor, torque_motor, product_id) VALUES ('120 kg', '4 Tiempos SOHC , refrigerado por aire, cc 149.2.', '10.5 HP @ 7000 rpm', '11.5 Nm @ 5500 rpm', 4);

-- ADD Items
INSERT INTO items_store (lote, category, quantity, product_id) VALUES ('Lote001', 'Dual Purpose Motorcycles 150 CC', 200, 1),('Lote001', 'Dual Purpose Motorcycles 150 CC', 100, 2),('Lote001', 'Dual Purpose Motorcycles 150 CC', 200, 3), ('Lote001', 'Dual Purpose Motorcycles 150 CC', 150, 4);

-- ADD Users // every users have their passwords like 123
INSERT INTO users (username, password, email) VALUES ('admin', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'mr.machineman98@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admin1', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin1@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admin2', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin2@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admin3', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin3@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admin4', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin4@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admim5', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin5@gmail.com');
INSERT INTO users (username, password, email) VALUES ('admin6', '$2a$12$rNBkM/7TWErVjxmsEVE5ZO02KCO59OnIhwzr/j1jD0re3rxGilowW', 'admin6@gmail.com');


-- ADD Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

-- ADD User_Roles
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (1, 2);


-- Procedimiento para la bitacora
DROP PROCEDURE IF EXISTS my_procedure;
DROP TYPE IF EXISTS log_sale;
CREATE TYPE log_sale AS ( quantity varchar, product VARCHAR, sale_id VARCHAR, sub_total VARCHAR, date_sale VARCHAR, owner VARCHAR);
