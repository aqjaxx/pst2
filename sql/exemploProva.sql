
INSERT INTO Accounts(Account_ID, AvailableCreditLimit, AvailableWithdrawalLimit) VALUES (1 , 5000, 5000);
INSERT INTO Accounts(Account_ID, AvailableCreditLimit, AvailableWithdrawalLimit) VALUES (2 , 3000, 3000);

INSERT INTO Transactions(Account_ID, OperationType_ID, Amount, Balance, EventDate, DueDate ) VALUES (1, 1, -50, -50, '2017-04-05', '2017-05-10');
INSERT INTO Transactions(Account_ID, OperationType_ID, Amount, Balance, EventDate, DueDate ) VALUES (1, 1, -23.5, -23.5, '2017-04-10', '2017-05-10');
INSERT INTO Transactions(Account_ID, OperationType_ID, Amount, Balance, EventDate, DueDate ) VALUES (1, 1, -18.7, -18.7, '2017-04-05', '2017-05-10');
