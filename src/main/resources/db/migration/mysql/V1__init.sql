CREATE TABLE `Cliente` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nome` VARCHAR(100) NOT NULL,
 `cpf` CHAR(11) NOT NULL,
 `uf` CHAR(2) NOT NULL,
 `data_Atualizacao` DATETIME NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC))
ENGINE = InnoDB;

CREATE TABLE `Cartao` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `numero` CHAR(16) NOT NULL,
 `data_Validade` DATETIME NOT NULL,
 `bloqueado` BIT NOT NULL,
 `data_Atualizacao` DATETIME NOT NULL,
 `cliente_id` INT NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE INDEX `numero_UNIQUE` (`numero` ASC),
 INDEX `fk_Cartao_Cliente_idx` (`cliente_id` ASC),
 CONSTRAINT `fk_Cartao_Cliente`
 FOREIGN KEY (`cliente_id`)
 REFERENCES `Cliente` (`id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION)
ENGINE = InnoDB;
