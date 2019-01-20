CREATE TABLE utente(
    nome VARCHAR(255),
    cognome VARCHAR(255),
    imei VARCHAR(255) PRIMARY KEY
);

CREATE TABLE oggetto(
    id VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    immagine VARCHAR(255),
    marca VARCHAR(255),
    anno INTEGER,
    lunghezza NUMERIC(5,2),
    posizione VARCHAR(255),
    descrizione VARCHAR(500)    
);

CREATE TABLE prestito(
    id_oggetto VARCHAR(255) REFERENCES oggetto,
    imei_utente VARCHAR(255) REFERENCES utente,
    preso_il TIMESTAMP,
    evento VARCHAR(255) NOT NULL,
    restituito_il TIMESTAMP NULL,
    restituito_da VARCHAR(255) REFERENCES utente,
    PRIMARY KEY(id_oggetto, imei_utente, preso_il)
);
