
CREATE TYPE public.estado_propiedad AS ENUM ('en venta', 'desactivado', 'vendido', 'reservado');
CREATE TYPE public.opcion_propiedad AS ENUM ('venta', 'alquiler', 'alquiler con opción a compra', 'traspaso');
CREATE TYPE public.tipo_vivienda_enum AS ENUM ('piso', 'chalet', 'adosado');
CREATE TYPE public.tipo_recurso AS ENUM ('imagen', 'video');
create table public.provincias
(
    id     uuid         not null
        primary key,
    nombre varchar(100) not null
        unique
);

alter table public.provincias
    owner to administrador;

create table public.localidadesJPAS
(
    id           uuid         not null
        primary key,
    nombre       varchar(100) not null,
    provincia_id uuid
        references public.provincias
            on delete cascade
);

alter table public.localidadesJPAS
    owner to administrador;

create table public.propiedadesJPAS
(
    id               uuid             not null
        primary key,
    referencia       varchar(20)      not null
        unique,
    direccion        varchar(255)     not null,
    localidad_id     uuid
        references public.localidadesJPAS,
    metros_cuadrados numeric(10, 2)   not null,
    coordenadas      point,
    precio           numeric(15, 2)   not null,
    precio_rebajado  numeric(15, 2),
    estado           estado_propiedad default 'en venta'::estado_propiedad,
    opcion           opcion_propiedad not null,
    fecha_creacion   timestamp        default CURRENT_TIMESTAMP
);

alter table public.propiedadesJPAS
    owner to administrador;

create table public.viviendas
(
    propiedad_id       uuid               not null
        primary key
        references public.propiedadesJPAS
            on delete cascade,
    metros_habitables  numeric(10, 2),
    habitaciones       integer default 0,
    aseos              integer default 0,
    baños              integer default 0,
    piscina            boolean default false,
    aire_acondicionado boolean default false,
    garaje             boolean default false,
    tipo_vivienda      tipo_vivienda_enum not null
);

alter table public.viviendas
    owner to administrador;

create table public.terrenos
(
    propiedad_id   uuid not null
        primary key
        references public.propiedadesJPAS
            on delete cascade,
    edificabilidad numeric(10, 2),
    urbanizable    boolean default false
);

alter table public.terrenos
    owner to administrador;

create table public.locales
(
    propiedad_id uuid not null
        primary key
        references public.propiedadesJPAS
            on delete cascade,
    escaparate   boolean default false,
    salida_humos boolean default false
);

alter table public.locales
    owner to administrador;

create table public.multimediaJPA
(
    id           uuid         not null
        primary key,
    propiedad_id uuid
        references public.propiedadesJPAS
            on delete cascade,
    fichero      varchar(255) not null,
    tipo         tipo_recurso default 'imagen'::tipo_recurso,
    orden        integer      default 0
);

alter table public.multimediaJPA
    owner to administrador;

INSERT INTO public.provincias (id, nombre) VALUES ('95ccb118-c924-4c5f-8961-3fca7c124123', 'Alicante');
INSERT INTO public.provincias (id, nombre) VALUES ('c9a7b22b-120e-4f7c-8269-f7d44b42cf97', 'Murcia');

INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('f873c7c1-ba02-4326-a757-a9036a8f13fc', 'Alicante', '95ccb118-c924-4c5f-8961-3fca7c124123');
INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('9ba4c5d0-f6e3-4591-8881-ca66613c6a2c', 'Elche', '95ccb118-c924-4c5f-8961-3fca7c124123');
INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('2d64d556-1fec-4dd7-a269-67dced7c729a', 'Torrevieja', '95ccb118-c924-4c5f-8961-3fca7c124123');
INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('78ffefc4-49b0-4c29-a8b3-4da6eee83100', 'Murcia', 'c9a7b22b-120e-4f7c-8269-f7d44b42cf97');
INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('72f8d045-f5ea-40f3-9f29-fba3627c9953', 'Cartagena', 'c9a7b22b-120e-4f7c-8269-f7d44b42cf97');
INSERT INTO public.localidadesJPAS (id, nombre, provincia_id) VALUES ('aea85bd4-7f6b-445d-acb7-1cab69a2df63', 'Lorca', 'c9a7b22b-120e-4f7c-8269-f7d44b42cf97');

INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3', 'ALV-001', 'Calle Mayor, 10', 'f873c7c1-ba02-4326-a757-a9036a8f13fc', 120.00, '(38.3452,-0.481)', 250000.00, 240000.00, 'en venta', 'venta', '2026-01-24 21:16:07.051888');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('6d0ca8a2-2afa-4f79-9ba7-b0a193af1a16', 'ALV-002', 'Av. de la Libertad, 5', '9ba4c5d0-f6e3-4591-8881-ca66613c6a2c', 95.00, '(38.269,-0.712)', 180000.00, null, 'en venta', 'venta', '2026-01-24 21:16:07.051888');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('fe94c946-2d33-44b2-8eb6-805bafcdc87c', 'ALV-003', 'Calle del Mar, 12', '2d64d556-1fec-4dd7-a269-67dced7c729a', 85.00, '(37.978,-0.682)', 160000.00, 155000.00, 'reservado', 'alquiler', '2026-01-24 21:16:07.051888');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('426aef97-e811-44de-a50a-31d99cd62ed2', 'MRV-001', 'Plaza de España, 8', '78ffefc4-49b0-4c29-a8b3-4da6eee83100', 110.00, '(37.9922,-1.1307)', 220000.00, null, 'en venta', 'venta', '2026-01-24 21:16:13.287406');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('b0f25f9e-7def-4e70-9ead-b3e6235f2324', 'MRV-002', 'Calle Mayor, 15', '72f8d045-f5ea-40f3-9f29-fba3627c9953', 130.00, '(37.605,-0.991)', 270000.00, 260000.00, 'vendido', 'venta', '2026-01-24 21:16:13.287406');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('fef99b3c-1a2a-47e4-a0f1-a35419d28739', 'MRV-003', 'Av. de Lorca, 2', 'aea85bd4-7f6b-445d-acb7-1cab69a2df63', 90.00, '(37.667,-1.7)', 175000.00, null, 'en venta', 'alquiler', '2026-01-24 21:16:13.287406');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('f5731596-9633-4f60-b5e8-dc47f9ed14bf', 'ALT-001', 'Polígono Industrial, Alicante', 'f873c7c1-ba02-4326-a757-a9036a8f13fc', 500.00, '(38.345,-0.48)', 80000.00, null, 'en venta', 'venta', '2026-01-24 21:16:17.932272');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('dcef2fb9-c3df-427b-8883-6d2e2c5cb920', 'ALT-002', 'Camino Rural, Elche', '9ba4c5d0-f6e3-4591-8881-ca66613c6a2c', 800.00, '(38.268,-0.71)', 120000.00, null, 'en venta', 'venta', '2026-01-24 21:16:17.932272');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('ccbfc754-78af-4b42-b672-4bc21e49ebdb', 'MRT-001', 'Parcela Norte, Murcia', '78ffefc4-49b0-4c29-a8b3-4da6eee83100', 1000.00, '(37.991,-1.128)', 150000.00, null, 'en venta', 'venta', '2026-01-24 21:16:17.932272');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('aedea77e-fc4c-4271-bc64-e2a84031b63d', 'MRT-002', 'Sector Sur, Lorca', 'aea85bd4-7f6b-445d-acb7-1cab69a2df63', 750.00, '(37.665,-1.702)', 90000.00, null, 'en venta', 'venta', '2026-01-24 21:16:17.932272');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('517f05ce-17ce-4c53-89d4-9a089b23f09a', 'ALC-001', 'Calle Comercio, Alicante', 'f873c7c1-ba02-4326-a757-a9036a8f13fc', 60.00, '(38.346,-0.482)', 95000.00, null, 'en venta', 'venta', '2026-01-24 21:16:21.693130');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('ea844b65-b05b-4636-aaf8-3a8374d2ebd8', 'ALC-002', 'Av. Central, Elche', '9ba4c5d0-f6e3-4591-8881-ca66613c6a2c', 80.00, '(38.269,-0.711)', 120000.00, null, 'reservado', 'venta', '2026-01-24 21:16:21.693130');
INSERT INTO public.propiedadesJPAS (id, referencia, direccion, localidad_id, metros_cuadrados, coordenadas, precio, precio_rebajado, estado, opcion, fecha_creacion) VALUES ('9d8eff9d-94ee-41cd-8f24-47fceb2fb59f', 'MRC-001', 'Plaza Mayor, Murcia', '78ffefc4-49b0-4c29-a8b3-4da6eee83100', 70.00, '(37.993,-1.132)', 110000.00, null, 'en venta', 'venta', '2026-01-24 21:16:21.693130');

INSERT INTO public.terrenos (propiedad_id, edificabilidad, urbanizable) VALUES ('f5731596-9633-4f60-b5e8-dc47f9ed14bf', 0.80, true);
INSERT INTO public.terrenos (propiedad_id, edificabilidad, urbanizable) VALUES ('dcef2fb9-c3df-427b-8883-6d2e2c5cb920', 0.50, true);
INSERT INTO public.terrenos (propiedad_id, edificabilidad, urbanizable) VALUES ('ccbfc754-78af-4b42-b672-4bc21e49ebdb', 1.20, true);
INSERT INTO public.terrenos (propiedad_id, edificabilidad, urbanizable) VALUES ('aedea77e-fc4c-4271-bc64-e2a84031b63d', 0.60, false);

INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3', 100.00, 3, 2, 1, true, true, true, 'piso');
INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('6d0ca8a2-2afa-4f79-9ba7-b0a193af1a16', 90.00, 2, 1, 1, false, true, false, 'adosado');
INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('fe94c946-2d33-44b2-8eb6-805bafcdc87c', 80.00, 2, 1, 1, true, false, true, 'chalet');
INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('426aef97-e811-44de-a50a-31d99cd62ed2', 105.00, 3, 2, 1, false, true, true, 'piso');
INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('b0f25f9e-7def-4e70-9ead-b3e6235f2324', 125.00, 4, 2, 2, true, true, true, 'chalet');
INSERT INTO public.viviendas (propiedad_id, metros_habitables, habitaciones, aseos, baños, piscina, aire_acondicionado, garaje, tipo_vivienda) VALUES ('fef99b3c-1a2a-47e4-a0f1-a35419d28739', 85.00, 2, 1, 1, false, false, true, 'adosado');

INSERT INTO public.locales (propiedad_id, escaparate, salida_humos) VALUES ('517f05ce-17ce-4c53-89d4-9a089b23f09a', true, false);
INSERT INTO public.locales (propiedad_id, escaparate, salida_humos) VALUES ('ea844b65-b05b-4636-aaf8-3a8374d2ebd8', true, true);
INSERT INTO public.locales (propiedad_id, escaparate, salida_humos) VALUES ('9d8eff9d-94ee-41cd-8f24-47fceb2fb59f', false, true);


INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('d4ba0e6c-8bc4-41bd-9bda-90395691cfd5', '083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3', 'alv-001_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('65893131-bbc3-4d1c-8aa4-d26a67ed5a27', '083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3', 'alv-001_2.jpg', 'imagen', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('f2e7ee5a-efba-4664-98aa-2b0aa7ad4278', '083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3', 'alv-001_video.mp4', 'video', 3);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('f42ed26c-8103-484b-9ba0-3d70ce4694cb', '6d0ca8a2-2afa-4f79-9ba7-b0a193af1a16', 'alv-002_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('e91a26c3-f8cc-42ed-b262-660bf0409714', '6d0ca8a2-2afa-4f79-9ba7-b0a193af1a16', 'alv-002_2.jpg', 'imagen', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('bef6a98a-b204-4506-b495-1695e7250858', 'fe94c946-2d33-44b2-8eb6-805bafcdc87c', 'alv-003_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('c702bc38-b396-434e-8bd5-f57f6fae617a', 'fe94c946-2d33-44b2-8eb6-805bafcdc87c', 'alv-003_video.mp4', 'video', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('d5ad8c66-3848-4646-8ce4-4afd6421d861', '426aef97-e811-44de-a50a-31d99cd62ed2', 'mrv-001_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('7fa73967-3807-40c6-bfea-ec4b46017c9d', '426aef97-e811-44de-a50a-31d99cd62ed2', 'mrv-001_2.jpg', 'imagen', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('fee742f7-ad52-4331-af06-47fb2e955482', 'b0f25f9e-7def-4e70-9ead-b3e6235f2324', 'mrv-002_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('d8b63d57-b29b-4a69-8cfe-33291e29bf83', 'b0f25f9e-7def-4e70-9ead-b3e6235f2324', 'mrv-002_video.mp4', 'video', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('310166cd-c24a-4d46-ab71-87c620b9d7be', 'fef99b3c-1a2a-47e4-a0f1-a35419d28739', 'mrv-003_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('25dfd36e-a285-416d-a685-ba4c63c5a67e', 'f5731596-9633-4f60-b5e8-dc47f9ed14bf', 'alt-001_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('d68a45bd-82c4-43be-b59a-996467cf576a', 'f5731596-9633-4f60-b5e8-dc47f9ed14bf', 'alt-001_2.jpg', 'imagen', 2);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('a52d4d15-22b5-409a-be00-bb3320e236cc', 'dcef2fb9-c3df-427b-8883-6d2e2c5cb920', 'alt-002_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('b8107ee4-c10f-416a-8ee7-e3a51362fa35', 'ccbfc754-78af-4b42-b672-4bc21e49ebdb', 'mrt-001_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('cffb7065-aef6-4978-8037-1628ec25896b', 'aedea77e-fc4c-4271-bc64-e2a84031b63d', 'mrt-002_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('0e40e771-af6a-482d-87c4-227498fdf32e', '517f05ce-17ce-4c53-89d4-9a089b23f09a', 'alc-001_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('8bcba428-6655-41c2-9744-5c7bbc46ea37', 'ea844b65-b05b-4636-aaf8-3a8374d2ebd8', 'alc-002_1.jpg', 'imagen', 1);
INSERT INTO public.multimediaJPA (id, propiedad_id, fichero, tipo, orden) VALUES ('739a14aa-5c2f-4691-976a-c4b19449ee33', '9d8eff9d-94ee-41cd-8f24-47fceb2fb59f', 'mrc-001_1.jpg', 'imagen', 1);
