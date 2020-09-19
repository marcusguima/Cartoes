CREATE TABLE `usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `ativo` BIT NOT NULL,
  `data_Acesso` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `usuario_cpf_UNIQUE` (`cpf` ASC))
ENGINE = InnoDB;
 
CREATE TABLE `regra` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;
 
CREATE TABLE `usuario_regra` (
  `usuario_id` INT NOT NULL,
  `regra_id` INT NOT NULL,
  PRIMARY KEY (`usuario_id`, `regra_id`),
  INDEX `fk_usuario_regra_regra_idx` (`regra_id` ASC),
  INDEX `fk_usuario_regra_usuario_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_usuario_regra_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_regra_regra`
    FOREIGN KEY (`regra_id`)
    REFERENCES `regra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
