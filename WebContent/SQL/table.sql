CREATE TABLE MEMBER(
	MEMBER_ID VARCHAR(20),
	MEMBER_PW VARCHAR(15),
	MEMBER_NAME VARCHAR(20),
	MEMBER_JUMIN1 INT,
	MEMBER_JUMIN2 INT,
	MEMBER_EMAIL VARCHAR(25),
	MEMBER_EMAIL_GET VARCHAR(7),
	MEMBER_MOBILE VARCHAR(13),
	MEMBER_PHONE VARCHAR(13),
	MEMBER_ZIPCODE VARCHAR(13),
	MEMBER_ADDR1 VARCHAR(70),
	MEMBER_ADDR2 VARCHAR(70),
	MEMBER_ADMIN INT,
	MEMBER_JOIN_DATE DATE
);

CREATE TABLE ZIPCODE(
	ID INT,
	ZIPCODE VARCHAR(7),
	SIDO VARCHAR(10),
	GUGUN VARCHAR(30),
	DONG VARCHAR(36),
	RI VARCHAR(60),
	BUNJI VARCHAR(50),
	PRIMARY KEY (ID)
);

CREATE TABLE BOOK(
	BOOK_NUM INT,
	BOOK_CATEGORY VARCHAR(20),
	BOOK_NAME VARCHAR(50),
	BOOK_CONTENT VARCHAR(3000),
	BOOK_SIZE VARCHAR(10),
	BOOK_COLOR VARCHAR(20),
	BOOK_AMOUNT INT,
	BOOK_PRICE INT,
	BOOK_IMAGE VARCHAR(100),
	BOOK_BEST INT,
	BOOK_DATE DATE,
	PRIMARY KEY(BOOK_NUM)
);

CREATE TABLE CART(
	CART_NUM INT,
	CART_MEMBER_ID VARCHAR(20),
	CART_BOOK_NUM INT,
	CART_BOOK_AMOUNT INT,
	CART_BOOK_SIZE VARCHAR(20),
	CART_BOOK_COLOR VARCHAR(20),
	CART_DATE DATE,
	PRIMARY KEY(CART_NUM)
);

CREATE TABLE BOOK_ORDER(
	ORDER_NUM INT,
	ORDER_TRADE_NUM VARCHAR(50),
	ORDER_TRANS_NUM VARCHAR(50),
	ORDER_BOOK_NUM INT,
	ORDER_BOOK_NAME VARCHAR(50),
	ORDER_BOOK_AMOUNT INT,
	ORDER_BOOK_SIZE VARCHAR(20),
	ORDER_BOOK_COLOR VARCHAR(20),
	ORDER_MEMBER_ID VARCHAR(20),
	ORDER_RECEIVE_NAME VARCHAR(20),
	ORDER_RECEIVE_ADDR1 VARCHAR(70),
	ORDER_RECEIVE_ADDR2 VARCHAR(70),
	ORDER_RECEIVE_PHONE VARCHAR(13),
	ORDER_RECEIVE_MOBILE VARCHAR(13),
	ORDER_MEMO VARCHAR(3000),
	ORDER_SUM_MONEY INT,
	ORDER_TRADE_TYPE VARCHAR(20),
	ORDER_TRADE_DATE DATE,
	ORDER_TRADE_PAYER VARCHAR(20),
	ORDER_DATE DATE,
	ORDER_STATUS INT,
	PRIMARY KEY(ORDER_NUM)
);





--以下、ドンゴン作成分

CREATE TABLE MEMBER(
	MEMBER_ID VARCHAR(32) NOT NULL,
	MEMBER_PW VARCHAR(64) NOT NULL,
	MEMBER_NAME VARCHAR(32) NOT NULL,
	MEMBER_NAME_KANA VARCHAR(32) NOT NULL,
	MEMBER_TEL VARCHAR(16) NOT NULL,
	MEMBER_MAIL VARCHAR(128) NOT NULL,
	MEMBER_ZIPCODE VARCHAR(16) NOT NULL,
	MEMBER_ADD_1 VARCHAR(128) NOT NULL,
	MEMBER_ADD_2 VARCHAR(128) NOT NULL,
	MEMBER_ADD_3 VARCHAR(128) NOT NULL,
	MEMBER_TYPE INT NOT NULL,
	DELETE_FLAG TINYINT NOT NULL,
	CREATE_USER BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	UPDATE_USER BIGINT NOT NULL,
	UPDATE_DATE DATETIME NOT NULL,
	PRIMARY KEY(MEMBER_ID)
);

CREATE TABLE ZIPCODE (
	ZIPCODE VARCHAR(16) NOT NULL,
	ADD_1 VARCHAR(128) NOT NULL,
	ADD_2 VARCHAR(128) NOT NULL,
	ADD_3 VARCHAR(128) NOT NULL,
	DELETE_FLAG TINYINT NOT NULL,
	CREATE_USER BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	UPDATE_USER BIGINT NOT NULL,
	UPDATE_DATE DATETIME NOT NULL
);

CREATE TABLE BOOK(
	BOOK_NO INT NOT NULL,
	BOOK_CATEGORY VARCHAR(32) NOT NULL,
	BOOK_NAME VARCHAR(32) NOT NULL,
	BOOK_WRITER VARCHAR(32) NOT NULL,
	BOOK_PUBLISHER VARCHAR(32) NOT NULL,
	BOOK_PUBLISHING_DATE DATETIME,
	BOOK_CONTENT VARCHAR(128),
	BOOK_PRICE FLOAT NOT NULL,
	BOOK_IMAGE VARCHAR(128) NOT NULL,
	BOOK_ISBN VARCHAR(16) NOT NULL,
	DELETE_FLAG TINYINT NOT NULL,
	CREATE_USER BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	UPDATE_USER BIGINT NOT NULL,
	UPDATE_DATE DATETIME NOT NULL,
	PRIMARY KEY(BOOK_NO)
);

CREATE TABLE CART(
	CART_NO INT NOT NULL,
	CART_MEMBER_ID VARCHAR(32) NOT NULL,
	CART_BOOK_NO INT NOT NULL,
	CART_COUNT TINYINT NOT NULL,
	CART_GENERATE_DATE DATETIME NOT NULL,
	DELETE_FLAG TINYINT NOT NULL,
	CREATE_USER BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	UPDATE_USER BIGINT NOT NULL,
	UPDATE_DATE DATETIME NOT NULL,
	PRIMARY KEY(CART_NO)
);

CREATE TABLE BOOK_ORDER(
	ORDER_NO INT NOT NULL,
	ORDER_TRANS_NO VARCHAR(32) NOT NULL,
	ORDER_BOOK_NO INT NOT NULL,
	ORDER_COUNT TINYINT NOT NULL,
	ORDER_MEMBER_ID VARCHAR(32) NOT NULL,
	ORDER_RECEIVE_NAME VARCHAR(32) NOT NULL,
	ORDER_RECEIVE_NAME_KANA VARCHAR(32) NOT NULL,
	ORDER_RECEIVE_EMAIL VARCHAR(128) NOT NULL,
	ORDER_RECEIVE_TEL VARCHAR(16) NOT NULL,
	ORDER_RECEIVE_ZIPCODE VARCHAR(16) NOT NULL,
	ORDER_RECEIVE_ADD_1 VARCHAR(128) NOT NULL,
	ORDER_RECEIVE_ADD_2 VARCHAR(128) NOT NULL,
	ORDER_RECEIVE_ADD_3 VARCHAR(128) NOT NULL,
	ORDER_MEMO VARCHAR(128),
	ORDER_TRADE_TYPE VARCHAR(32) NOT NULL,
	ORDER_TRADE_DATE DATETIME NOT NULL,
	ORDER_DATE DATETIME NOT NULL,
	ORDER_STATUS TINYINT NOT NULL,
	DELETE_FLAG TINYINT NOT NULL,
	CREATE_USER BIGINT NOT NULL,
	CREATE_DATE DATETIME NOT NULL,
	UPDATE_USER BIGINT NOT NULL,
	UPDATE_DATE DATETIME NOT NULL,
	PRIMARY KEY(ORDER_NO)
);
