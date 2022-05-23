CREATE TABLE TINDER.PERSONS (
                                         ID INTEGER NOT NULL,
                                         GENDER VARCHAR(10),
                                         NAME VARCHAR(255),
                                         DESCRIPTION VARCHAR(2000),
                                         SEARCH_TERM VARCHAR(10),
                                         USER_ID VARCHAR(10),
                                         BOT_STATE VARCHAR(30),
                                         PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX I_PERSONS_ID ON TINDER.PERSONS (ID);
CREATE SEQUENCE TINDER.PERSONS_S;