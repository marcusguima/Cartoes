CREATE TABLE `Transacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_Transacao` DATETIME NOT NULL,
  `cnpj` CHAR(14) NOT NULL,
  `valor` DOUBLE NOT NULL,
  `qdt_Parcelas` INT NOT NULL,
  `juros` DOUBLE NOT NULL,
  `cartao_id` INT NOT NULL,
 PRIMARY KEY (`id`),
 INDEX `fk_Transacao_Cartao_idx` (`cartao_id` ASC),
CONSTRAINT `fk_Transacao_Cartao`
FOREIGN KEY (`cartao_id`)
REFERENCES `Cartao` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB;