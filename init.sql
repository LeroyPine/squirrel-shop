INSERT INTO squirrel_shop.admin_user_info
    (id, user_id, user_name, nick_name, user_pwd, avatar, update_date)
VALUES (1, 1, 'admin', 'admin', '123456',
        'http://localhost:8999/squirrel-shop/files/AB5B51D3-8771-4440-9AC2-A49B44AB340B.jpeg', '2024-07-27 16:06:33');


INSERT INTO squirrel_shop.user_role (id, user_id, role_id, create_date, update_date)
VALUES (1, 1, 'admin', '2024-06-23 17:25:46', '2024-06-23 17:25:47');


INSERT INTO squirrel_shop.role_info (id, role_id, role_name, role_status, create_date, update_date) VALUES (1, 'admin', '管理员', 1, '2024-06-23 17:13:15', '2024-06-23 17:13:19');
INSERT INTO squirrel_shop.role_info (id, role_id, role_name, role_status, create_date, update_date) VALUES (2, 'normal', '普通用户', 1, '2024-06-23 17:13:45', '2024-06-23 17:13:47');
