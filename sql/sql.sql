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
    
    FOREIGN KEY(user_id1) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY(user_id2) REFERENCES users(user_id) ON DELETE CASCADE
);
/

COMMIT;
