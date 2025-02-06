use spring_shampoos;

INSERT INTO ingredients (id, name, price) VALUES
(1, 'Aloe Vera', 2.50),
(2, 'Coconut Oil', 1.80),
(3, 'Argan Oil', 3.20),
(4, 'Tea Tree Oil', 2.90),
(5, 'Honey Extract', 2.10),
(6, 'Vitamin E', 1.50),
(7, 'Shea Butter', 2.70),
(8, 'Lavender Oil', 2.60),
(9, 'Chamomile Extract', 1.90),
(10, 'Keratin', 3.50);

INSERT INTO shampoos (id, brand, size, price, label_id) 
VALUES 
(1, 'Brand A', 1, 9.99, 1), 
(2, 'Brand B', 2, 6.49, 2), 
(3, 'Brand C', 0, 8.99, 3), 
(4, 'Brand D', 1, 5.49, 4), 
(5, 'Brand E', 2, 7.99, 5), 
(6, 'Brand F', 0, 8.49, 1), 
(7, 'Brand G', 1, 10.99, 2), 
(8, 'Brand H', 2, 6.99, 3), 
(9, 'Brand I', 0, 7.49, 4), 
(10, 'Brand J', 1, 9.49, 5);

INSERT INTO shampoos_ingredients (shampoo_id, ingredient_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10),
(6, 1),
(6, 3),
(7, 5),
(7, 7),
(8, 2),
(8, 8),
(9, 4),
(9, 10),
(10, 6),
(10, 9);
