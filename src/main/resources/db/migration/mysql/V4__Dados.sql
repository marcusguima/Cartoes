INSERT INTO `usuario` (`id`, `nome`, `cpf`, `senha`, `ativo`) VALUES
(DEFAULT, 'Usuario 01', '14848682002', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', TRUE);
 
INSERT INTO `usuario` (`id`, `nome`, `cpf`, `senha`, `ativo`) VALUES
(DEFAULT, 'Usuario 02', '27546823099', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', TRUE);
 
INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_ADM_USUARIO', 'Permite acesso aos serviços de usuário', TRUE);
 
INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_EXCLUSAO', 'Permite exclusão de cartões', TRUE);
 
INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE cpf = '14848682002'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_ADM_USUARIO')
);
 
INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE cpf = '14848682002'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_EXCLUSAO')
);
