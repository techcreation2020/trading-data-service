INSERT INTO FUNDS (fund_name, units) VALUES
('ICIC',500),
('HDFC',500),
('KOTAK',500),
('RELIANCE', 500);
insert into users (balance, user_name, fund_id)  values (120, 'Ravi', 1);
insert into users (balance, user_name, fund_id)  values (300, 'Aravind', 2);
insert into users (balance, user_name, fund_id)  values (200, 'Aravind', 2);
insert into transaction (transaction_type, transaction_unit, fund_id, user_id) values (1, 250, 3, 2);
insert into transaction (transaction_type, transaction_unit, fund_id, user_id) values (1, 250, 1, 2);