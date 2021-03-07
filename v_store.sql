CREATE DATABASE v_store;
use v_store;

CREATE TABLE `user` (
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avt` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT 'default.jpg',
  `age` int(3) NOT NULL,
  `wallet` int not null,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
	`preferred` varchar(254) COLLATE utf8mb4_unicode_ci DEFAULT 'user.getName()',
`enabled` tinyint(1) not null,
`role` varchar(45),
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;
  
create table `products` (
	`name_product` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `price` int not null,
`number` int not null,
`image` varchar(254) COLLATE utf8mb4_unicode_ci DEFAULT 'default.jpg',
    `id_product` int(11) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE `products`
  ADD PRIMARY KEY (`id_product`);
ALTER TABLE `products`
  MODIFY `id_product` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;
  
create table `bill` (
`id_user` int(11) not null,
`id_product` int(11) not null,
`date` date,
`number` int(11) not null,
`id_bill` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id_bill`);
ALTER TABLE `bill`
  MODIFY `id_bill` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;
ALTER TABLE `bill`
  ADD CONSTRAINT FK_id_user FOREIGN KEY(id_user) REFERENCES user(id_user);
ALTER TABLE `bill`
  ADD CONSTRAINT FK_id_product FOREIGN KEY(id_product) REFERENCES products(id_product);




INSERT INTO `user` (`user_name`, `password`, `name`, `age`, `wallet`, `email`,`enabled`,`role`, `id_user`) VALUES
('B', '1234', 'Nguyen van B', 22, 10000, 'vanb@gamil.com',1,'ROLE_USER', 1),
('A', '$2a$10$bOofW9zBkKrTwNVw3EyEWOQyRTM9J8qsKYn7vOcFHNCO.6E73NWNu', 'Nguyen A', 22, 20000, 'test@gmail.com',1,'ROLE_ADMIN', 2),
('C', '$10$sNagGOrbVxZ1SY/wm7LWfeDgU45/qVoPDCEimqkoA/KKSWgvE7eP2', 'Nguyen C', 23, 99000000, 'test@gmail.com',0,'ROLE_USER', 3),
('admin', '$2a$10$loJhP/xbwKbpMxp64iDw/Obp/4fl8.M.w2DnrW4FSEMWEILK04R0K', 'Nguyen Van Admin', 25, 1000000, 'test@gmail.com',1,'ROLE_ADMIN', 4);

insert into `products` (`name_product`,`price`,`number`, `id_product`) values
('Labtop ThinkPad', 10000000,30, 2),
('Chuot khong day', 200000,100, 3),
('Ban phim co', 1000000,20, 4),
('Keo keo', 10000,3, 5),
('Thuoc chuot', 5000,5, 6),
('Thoc cho', 10000,200, 7),
('Thuoc meo', 15000,0, 8),
('Thien dan', 1000,1, 9);

insert into `bill` values 
(5, 5, '2020-11-1',1, 1),
(6, 2, '2019-10-10',1, 2);

-- drop database v_store;

