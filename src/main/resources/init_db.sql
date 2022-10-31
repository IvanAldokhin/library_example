CREATE DATABASE `library_db` /*!40100
DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE library_db;

CREATE TABLE `literary_formats` (
  `format` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL,
  `price` DECIMAL NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `literary_format_id` BIGINT(11) NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT `books_literary_formats_fk`
  FOREIGN KEY (`literary_format_id`)
        REFERENCES `library_db`.`literary_formats` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `books_authors` (
  `book_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  KEY `books_authors_books_fk` (`book_id`),
  KEY `books_authors_authors_fk` (`author_id`),
  CONSTRAINT `books_authors_authors_fk` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`),
  CONSTRAINT `books_authors_books_fk` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

