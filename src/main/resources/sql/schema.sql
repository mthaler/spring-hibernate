CREATE TABLE SINGER (
       ID SERIAL PRIMARY KEY,
       FIRST_NAME VARCHAR(60) NOT NULL,
       LAST_NAME VARCHAR(40) NOT NULL,
       BIRTH_DATE DATE,
       VERSION INT NOT NULL DEFAULT 0
       UNIQUE (FIRST_NAME, LAST_NAME)
);


CREATE TABLE ALBUM (
       ID SERIAL PRIMARY KEY,
       SINGER_ID INT NOT NULL REFERENCES SINGER (ID) ON DELETE CASCADE,
       TITLE VARCHAR(100) NOT NULL,
       RELEASE_DATE DATE,
       VERSION INT NOT NULL DEFAULT 0
       UNIQUE (SINGER_ID, TITLE)
);

CREATE TABLE INSTRUMENT (
  INSTRUMENT_ID VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE SINGER_INSTRUMENT (
    SINGER_ID INT NOT NULL REFERENCES SINGER (ID) ON DELETE CASCADE,
    INSTRUMENT_ID VARCHAR(20) NOT NULL REFERENCES INSTRUMENT (INSTRUMENT_ID) ON DELETE CASCADE,
    PRIMARY KEY (SINGER_ID, INSTRUMENT_ID)
);