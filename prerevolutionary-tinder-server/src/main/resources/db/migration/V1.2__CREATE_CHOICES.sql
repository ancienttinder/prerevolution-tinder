CREATE TABLE TINDER.CHOICES (
                                         ID INTEGER NOT NULL,
                                         PERSON_ID INTEGER,
                                         SELECTED_ID INTEGER,
                                         PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX I_CHOICES_ID ON TINDER.CHOICES (ID);
CREATE SEQUENCE TINDER.CHOICES_S;