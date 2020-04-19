CREATE SCHEMA `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `chess`.`board` (
  `game_id` VARCHAR(45) NOT NULL,
  `position` VARCHAR(2) NOT NULL,
  `symbol` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`game_id`, `position`));

CREATE TABLE `chess`.`turn_info` (
  `game_id` VARCHAR(45) NOT NULL,
  `current_team` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE INDEX `game_id_UNIQUE` (`game_id` ASC));
