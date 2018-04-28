-- Author: DVT HDH

SET NAMES utf8mb4;


-- default charset for database should be utf8mb4
-- default collate for database should be utf8mb4_unicode_520_ci


CREATE TABLE `word` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`english` VARCHAR(60) COLLATE utf8mb4_unicode_520_ci NOT NULL,
	`welsh` VARCHAR(60) COLLATE utf8mb4_unicode_520_ci NOT NULL,
	`welsh_gender` ENUM('masculine', 'feminine') COLLATE utf8mb4_unicode_520_ci NOT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_520_ci;


CREATE TABLE `user` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_name` VARCHAR(60) COLLATE utf8mb4_unicode_520_ci NOT NULL,
	`password` varchar(255) COLLATE utf8mb4_unicode_520_ci NOT NULL,
	`role` ENUM('student','instructor','administrator') COLLATE utf8mb4_unicode_520_ci NOT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_520_ci;


CREATE TABLE `test` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT UNSIGNED NOT NULL,
	`mark` INT UNSIGNED NOT NULL,
	`test_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	KEY `user_id` (`user_id`),
	CONSTRAINT `test_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_520_ci;
