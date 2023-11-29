insert into orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    values (1, 'address', 'city', '2023-11-29', 'test123@test.com', 'first_name', 'last_name', '1234567890', 1234567890, 56, 2);

insert into orders_perfumes (order_id, perfumes_id) values (1, 1);
insert into orders_perfumes (order_id, perfumes_id) values (1, 2);
