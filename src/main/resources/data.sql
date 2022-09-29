INSERT INTO restaurant (id, cep, complement, name) VALUES
(1L, '0000001', 'Complement Address Restaurant 1', 'Restaurant 1'),
(2L, '0000002', 'Complement Address Restaurant 2', 'Restaurant 2');

INSERT INTO client (id, cep, complement, name) VALUES
(1L, '0000001', 'Complement Address Client 1', 'Client 1');

INSERT INTO product (id, disponivel, name, unitary_value, restaurant_id) VALUES
(1L, true, 'Product 1', 5.0, 1L),
(2L, true, 'Product 2', 6.0, 1L),
(3L, true, 'Product 3', 7.0, 2L);

INSERT INTO bag (id, formOfPayment, fechada, amount, client_id) VALUES
(1L, 0, false, 0.0, 1L);
Footer
© 2022 GitHub, Inc.
Footer navigation
Terms
Privacy
Security