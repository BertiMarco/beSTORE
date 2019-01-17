CREATE TABLE utente(
    nome VARCHAR,
    cognome VARCHAR,
    imei VARCHAR PRIMARY KEY
);

CREATE TABLE oggetto(
    id SERIAL PRIMARY KEY,
    nome VARCHAR NOT NULL,
    immagine VARCHAR,
    marca VARCHAR,
    anno INTEGER,
    lunghezza NUMERIC(5,2)    
);

CREATE TABLE prestito(
    id_oggetto SERIAL REFERENCES oggetto,
    imei_utente VARCHAR REFERENCES utente,
    preso_il TIMESTAMP,
    evento VARCHAR NOT NULL,
    restituito_il TIMESTAMP,
    restituito_da VARCHAR REFERENCES utente
    PRIMARY KEY(id_oggetto, imei_utente, preso_il)
);
