DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `birthdate` date NOT NULL,
    `country` varchar(50) NOT NULL,
    `phone` varchar(20) DEFAULT NULL,
    `gender` enum('M','F') DEFAULT NULL,
    PRIMARY KEY (`id`)
    );