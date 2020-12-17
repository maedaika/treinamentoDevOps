CREATE TABLE TBL_CIDADE (
  id_cidade serial PRIMARY KEY,
  dc_temperatura DOUBLE precision NULL,
  v10m DOUBLE precision NULL,
  dc_nome VARCHAR(255) NULL
 -- PRIMARY KEY(id_cidade)
);

CREATE TABLE TBL_INVERSOR (
  id_inversor serial PRIMARY KEY,
  nm_modulo VARCHAR(255) NULL,
  eficiencia DOUBLE precision NULL,
  tipo INTEGER NULL,
  potencia DOUBLE  precision NULL
  --PRIMARY KEY(id_inversor)
);

CREATE TABLE TBL_MODULO (
  id_modulo serial PRIMARY KEY,
  nm_modulo VARCHAR(255) NULL,
  temp_pmax DOUBLE precision NULL,
  eficiencia DOUBLE precision NULL,
  area_capacitacao DOUBLE precision NULL,
  potencia DOUBLE precision NULL
  --PRIMARY KEY(id_modulo)
);

CREATE TABLE TBL_RADIACAO ( 
	id_cidade INTEGER NOT NULL,
  mes INTEGER  NOT NULL,
  valor DOUBLE precision NULL, 
  FOREIGN KEY (id_cidade) references TBL_CIDADE(id_cidade),
  PRIMARY KEY(id_cidade,mes)  
);

CREATE TABLE TBL_TIPO_COMPRA (
  id_tipo_compra serial PRIMARY KEY,
  nm_tipo_compra VARCHAR(255) NULL
  --PRIMARY KEY(id_tipo_compra)
);

CREATE TABLE TBL_TIPO_LIGACAO (
  id_tipo_ligacao serial PRIMARY KEY,
  nm_tipo_ligacao VARCHAR(255) NULL,
  a DOUBLE precision NULL,
  b DOUBLE precision NULL,
  tx_minima INTEGER NULL
  --PRIMARY KEY(id_tipo_ligacao)
);

CREATE TABLE TBL_TIPO_MONTAGEM (
  id_tipo_montagem serial PRIMARY KEY,
  nm_tipo_montagem VARCHAR(255) NULL,
  a DOUBLE precision NULL,
  b DOUBLE precision NULL,
  delta_t INTEGER  NULL
  --PRIMARY KEY(id_tipo_montagem)
);
