
CREATE DATABASE IF NOT EXISTS pisdb;

USE pisdb;

DROP TABLE IF EXISTS OperationsTypes;
CREATE TABLE OperationsTypes (
OperationType_ID INT NOT NULL AUTO_INCREMENT,
Description VARCHAR(20),
ChargeOrder INT,
PRIMARY KEY (OperationType_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO OperationsTypes(Description, ChargeOrder) VALUES ('COMPRA A VISTA', 2);
INSERT INTO OperationsTypes(Description, ChargeOrder) VALUES ('COMPRA PARCELADA', 1);
INSERT INTO OperationsTypes(Description, ChargeOrder) VALUES ('SAQUE', 0);
INSERT INTO OperationsTypes(Description, ChargeOrder) VALUES ('PAGAMENTO', 0);

DROP TABLE IF EXISTS Accounts;
CREATE TABLE IF NOT EXISTS Accounts (
Account_ID INT NOT NULL,
AvailableCreditLimit DECIMAL(9,1),
AvailableWithdrawalLimit DECIMAL(9,1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS Transactions;
CREATE TABLE Transactions (
Transaction_ID INT NOT NULL AUTO_INCREMENT,
Account_ID INT NOT NULL,
OperationType_ID INT NOT NULL,
Amount DECIMAL(9,1),
Balance DECIMAL(9,1),
EventDate DATE,
DueDate DATE,
PRIMARY KEY (Transaction_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PaymentsTracking;
CREATE TABLE PaymentsTracking (
PaymentTracking_ID INT NOT NULL AUTO_INCREMENT,
CreditTransaction_ID INT,
DebitTransaction_ID INT,
Amount DECIMAL(9,1),
PRIMARY KEY (PaymentTracking_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


