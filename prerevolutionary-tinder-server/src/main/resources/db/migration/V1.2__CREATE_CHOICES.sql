CREATE TABLE TINDER.CHOICES (
                                         ID INTEGER NOT NULL,
                                         PERSON_ID VARCHAR(255),
                                         SELECTED_ID VARCHAR(255),
                                         PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX I_CHOICES_ID ON TINDER.CHOICES (ID);
CREATE SEQUENCE TINDER.CHOICES_S;