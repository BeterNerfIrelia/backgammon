set serveroutput on;

DROP TABLE lobby;
/
DROP TABLE users;
/

CREATE TABLE users(
    user_id VARCHAR2(36),
    username VARCHAR2(100),
    
    PRIMARY KEY(user_id),
    UNIQUE(username)
);
/

CREATE TABLE lobby(
    user_id1 VARCHAR2(36),
    user_id2 VARCHAR2(36),
    code VARCHAR2(8),
    
    FOREIGN KEY(user_id1) REFERENCES users(user_id),
    FOREIGN KEY(user_id2) REFERENCES users(user_id)
);
/

commit;

DECLARE
    ceva DATE;
    ceva2 DATE;
BEGIN
    ceva := TO_DATE('31-01-2000','dd-mm-yyyy');
    ceva2 := TO_DATE('01-01-2000','dd-mm-yyyy');
    
    DBMS_OUTPUT.PUT_LINE(ceva-ceva2);
    DBMS_OUTPUT.PUT_LINE(ceva2-ceva);
    DBMS_OUTPUT.PUT_LINE(ceva+1);
    DBMS_OUTPUT.PUT_LINE(SYSDATE);
    DBMS_OUTPUT.PUT_LINE(SYSDATE+1);
END;