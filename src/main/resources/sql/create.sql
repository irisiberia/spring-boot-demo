create table user_info(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL,
    `nick_name` varchar(255) DEFAULT NULL,
    `avatar` varchar(1024) DEFAULT NULL,
    `open_id` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY userId (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;
