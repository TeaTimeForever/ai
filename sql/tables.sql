CREATE  TABLE `tfr`.`users` (

  `username` VARCHAR(20) NOT NULL ,

  `password` CHAR(64) NOT NULL ,

  `salt` VARCHAR(20) NOT NULL ,

  `failed_attempts` INT NOT NULL ,

  `first_name` VARCHAR(50) NOT NULL ,

  `last_name` VARCHAR(50) NOT NULL ,

  `email` VARCHAR(80) NOT NULL ,

  `country` CHAR(2) NOT NULL ,

  PRIMARY KEY (`username`) );