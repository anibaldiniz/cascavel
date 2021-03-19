-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 15-Mar-2021 às 15:33
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `solicitacoes`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `categoria`
--

CREATE TABLE `categoria` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `categoria`
--

INSERT INTO `categoria` (`id`, `nome`) VALUES
(1, 'Docente'),
(2, 'Discente'),
(3, 'Agente Universitário'),
(4, 'Cargo Comissionado'),
(5, 'Estagiário'),
(6, 'Externo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `documento`
--

CREATE TABLE `documento` (
  `id` bigint(20) NOT NULL,
  `caminho` varchar(255) DEFAULT NULL,
  `conteudo` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime DEFAULT NULL,
  `extensao` varchar(255) DEFAULT NULL,
  `mime_type` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `solicitacao_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `email`
--

CREATE TABLE `email` (
  `email` varchar(255) NOT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `evento`
--

CREATE TABLE `evento` (
  `id` bigint(20) NOT NULL,
  `data` datetime DEFAULT NULL,
  `servico` varchar(255) DEFAULT NULL,
  `solicitacao_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `grupo`
--

CREATE TABLE `grupo` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `localizacao_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `grupo`
--

INSERT INTO `grupo` (`id`, `nome`, `localizacao_id`) VALUES
(1, 'Secretaria Administrativa', NULL),
(2, 'Secretaria Financeira', NULL),
(3, 'Secretaria Acadêmica', NULL),
(4, 'Seção de Recursos Humanos', NULL),
(5, 'Seção de Patrimônio', NULL),
(6, 'Seção de Amoxarifado', NULL),
(7, 'Seção de Informática', NULL),
(8, 'Direção Geral', NULL),
(9, 'Seção de Compras', NULL),
(10, 'Seção de Licitação', NULL),
(11, 'Biblioteca', NULL),
(12, 'Seção de Planejamento e Convênios', NULL),
(13, 'Seção de Transportes', NULL),
(14, 'Seção de Serviços de Manutenção e Conservação', NULL),
(15, 'Seção de Segurança', NULL),
(16, 'Seção de Serviços de Apoio', NULL),
(17, 'Seção de Serviços Externos', NULL),
(18, 'Seção de Manutenção de Equipamentos e Laboratórios', NULL),
(19, 'Seção de Apoio Técnico aos Laboratórios', NULL),
(20, 'Seção de Contabilidade', NULL),
(21, 'Divisão Financeira', NULL),
(22, 'Divisão de Pós-Graduação', NULL),
(23, 'Divisão de Graduação', NULL),
(24, 'Assessoria Jurídica', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `lista`
--

CREATE TABLE `lista` (
  `id` bigint(20) NOT NULL,
  `nome_lista` varchar(255) DEFAULT NULL,
  `categoria_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `lista`
--

INSERT INTO `lista` (`id`, `nome_lista`, `categoria_id`) VALUES
(1, 'lista.docente-ccsc', 1),
(2, 'lista.academico-doutorado.ccsc', 2),
(3, 'lista.academico-especializacao.ccsc', 2),
(4, 'lista.academico-graduacao.ccsc', 2),
(5, 'lista.academico-mestrado.ccsc', 2),
(6, 'lista.academico-pos-doutorado.ccsc', 2),
(7, 'lista.agente-universitario.ccsc', 3),
(8, 'lista.cargo-comissionado.ccsc', 4),
(9, 'lista.estagiario.ccsc', 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `localizacao`
--

CREATE TABLE `localizacao` (
  `id` bigint(20) NOT NULL,
  `altitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `localizacao` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `notificacao`
--

CREATE TABLE `notificacao` (
  `id` bigint(20) NOT NULL,
  `celular_aplicativo_externo` bit(1) DEFAULT NULL,
  `celular_app` bit(1) DEFAULT NULL,
  `data_envio` datetime DEFAULT NULL,
  `email` bit(1) DEFAULT NULL,
  `mensagem_noticacao` varchar(255) DEFAULT NULL,
  `usuario_enviou_id` bigint(20) DEFAULT NULL,
  `usuario_recebeu_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfil`
--

CREATE TABLE `perfil` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `perfil`
--

INSERT INTO `perfil` (`id`, `nome`) VALUES
(1, 'Administrador'),
(2, 'Chefe de Seção'),
(3, 'Corresponsável'),
(4, 'usuário');

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicitacao`
--

CREATE TABLE `solicitacao` (
  `id` bigint(20) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `data_fim` datetime DEFAULT NULL,
  `data_inicio` datetime DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `solicitacao_id` bigint(20) DEFAULT NULL,
  `solicitacao_tipo_id` bigint(20) DEFAULT NULL,
  `unidade_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicitacao_sub_tipo`
--

CREATE TABLE `solicitacao_sub_tipo` (
  `id` bigint(20) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `solicitacao_tipo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicitacao_tipo`
--

CREATE TABLE `solicitacao_tipo` (
  `id` bigint(20) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `grupo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `status`
--

CREATE TABLE `status` (
  `id` bigint(20) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `cancelado` bit(1) DEFAULT NULL,
  `concluido` bit(1) DEFAULT NULL,
  `data_alteracao_status` datetime DEFAULT NULL,
  `suspenso` bit(1) DEFAULT NULL,
  `tramitando` bit(1) DEFAULT NULL,
  `solicitacao_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `telefone`
--

CREATE TABLE `telefone` (
  `id` bigint(20) NOT NULL,
  `cod_pais` tinyint(4) DEFAULT NULL,
  `ddd` tinyint(4) DEFAULT NULL,
  `fixo` bit(1) DEFAULT NULL,
  `prefixo` varchar(255) DEFAULT NULL,
  `ramal` bit(1) DEFAULT NULL,
  `sufixo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade`
--

CREATE TABLE `unidade` (
  `id` bigint(20) NOT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `denominacao_abreviada_institucional` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade_grupo`
--

CREATE TABLE `unidade_grupo` (
  `unidade_id` bigint(20) NOT NULL,
  `grupo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade_localizacao`
--

CREATE TABLE `unidade_localizacao` (
  `unidade_id` bigint(20) NOT NULL,
  `localizacao_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `esta_ativo` bit(1) DEFAULT NULL,
  `excluido_ldap` bit(1) DEFAULT NULL,
  `lingua` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `nome_completo` varchar(255) DEFAULT NULL,
  `primeiro_nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `ultima_alteracao_cadastro` datetime DEFAULT NULL,
  `ultimo_login` datetime DEFAULT NULL,
  `perfil_id` bigint(20) DEFAULT NULL,
  `unidade_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_categoria`
--

CREATE TABLE `usuario_categoria` (
  `usuario_id` bigint(20) NOT NULL,
  `categoria_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_grupo`
--

CREATE TABLE `usuario_grupo` (
  `usuario_id` bigint(20) NOT NULL,
  `grupo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_localizacao`
--

CREATE TABLE `usuario_localizacao` (
  `usuario_id` bigint(20) NOT NULL,
  `localizacao_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_responsavel_grupo`
--

CREATE TABLE `usuario_responsavel_grupo` (
  `usuario_id` bigint(20) NOT NULL,
  `grupo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_telefone`
--

CREATE TABLE `usuario_telefone` (
  `usuario_id` bigint(20) NOT NULL,
  `telefone_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaglfm6upcr73lsw18b0qkhr5n` (`solicitacao_id`),
  ADD KEY `FK12wwnlkdoe79h38nvn5huqo0b` (`usuario_id`);

--
-- Índices para tabela `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`email`),
  ADD KEY `FKkcch1rfv3cge9f9odplk9ouem` (`usuario_id`);

--
-- Índices para tabela `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmn5mbsx1td1vy04dr06yqcgwf` (`solicitacao_id`),
  ADD KEY `FKdgxunuenr1v7k0i72mvxm8hk6` (`usuario_id`);

--
-- Índices para tabela `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKvkr39hyeuaf8fg30h2kfm9jf` (`localizacao_id`);

--
-- Índices para tabela `lista`
--
ALTER TABLE `lista`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmc35ph2aiawlasqenptbnvmp` (`categoria_id`);

--
-- Índices para tabela `localizacao`
--
ALTER TABLE `localizacao`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `notificacao`
--
ALTER TABLE `notificacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeyml1o00l7mvpmsrw7hyw93he` (`usuario_enviou_id`),
  ADD KEY `FK25y7f3no2tck3eow6veltg53h` (`usuario_recebeu_id`);

--
-- Índices para tabela `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `solicitacao`
--
ALTER TABLE `solicitacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfrcr9s03mc8rkdbxn8nc2a800` (`solicitacao_id`),
  ADD KEY `FK86nmyf1aeo9iapqplf2rjwaum` (`solicitacao_tipo_id`),
  ADD KEY `FKl5pk9kiykqekt6g0458cv3rn4` (`unidade_id`),
  ADD KEY `FKffu0flughrpsk76ojkdh1rfdb` (`usuario_id`);

--
-- Índices para tabela `solicitacao_sub_tipo`
--
ALTER TABLE `solicitacao_sub_tipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjfg9dhv1ivjof2ycj2qqkcejj` (`solicitacao_tipo_id`);

--
-- Índices para tabela `solicitacao_tipo`
--
ALTER TABLE `solicitacao_tipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd0rujaugb90e3quofg50730bd` (`grupo_id`);

--
-- Índices para tabela `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKifbeedfb34w0nuo9ob2fih2sd` (`solicitacao_id`),
  ADD KEY `FKnbl3ow6btsi20yy96980tbn3y` (`usuario_id`);

--
-- Índices para tabela `telefone`
--
ALTER TABLE `telefone`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `unidade`
--
ALTER TABLE `unidade`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `unidade_grupo`
--
ALTER TABLE `unidade_grupo`
  ADD KEY `FKglhi4ptdrnbw12vljlq32pfg7` (`grupo_id`),
  ADD KEY `FKn5ofaf21ctm95eqf2i04kyc2i` (`unidade_id`);

--
-- Índices para tabela `unidade_localizacao`
--
ALTER TABLE `unidade_localizacao`
  ADD KEY `FKl0v93m7flbjc5kr0w5odvtxpv` (`localizacao_id`),
  ADD KEY `FKkb93uqc0894p75vkac62xkh97` (`unidade_id`);

--
-- Índices para tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9po12ytp6krwvwht1kmd0qgxf` (`perfil_id`),
  ADD KEY `FKsfuif7ykkynvxw4aqmk7shy7c` (`unidade_id`);

--
-- Índices para tabela `usuario_categoria`
--
ALTER TABLE `usuario_categoria`
  ADD KEY `FKqmtxu0f9s7nql4bidwu6j9iva` (`categoria_id`),
  ADD KEY `FKcjjx3pn1bytknm1dqey6uiax1` (`usuario_id`);

--
-- Índices para tabela `usuario_grupo`
--
ALTER TABLE `usuario_grupo`
  ADD KEY `FKk30suuy31cq5u36m9am4om9ju` (`grupo_id`),
  ADD KEY `FKdofo9es0esuiahyw2q467crxw` (`usuario_id`);

--
-- Índices para tabela `usuario_localizacao`
--
ALTER TABLE `usuario_localizacao`
  ADD KEY `FK54le21jb5w5nri4v9u2y5eil1` (`localizacao_id`),
  ADD KEY `FKaop8k25673ewvwjgrbtokb27c` (`usuario_id`);

--
-- Índices para tabela `usuario_responsavel_grupo`
--
ALTER TABLE `usuario_responsavel_grupo`
  ADD KEY `FK8w1xvagfepgti8xhu5r9nt6fw` (`grupo_id`),
  ADD KEY `FKsrk00du36gechnjtqs3d75bv7` (`usuario_id`);

--
-- Índices para tabela `usuario_telefone`
--
ALTER TABLE `usuario_telefone`
  ADD KEY `FKmxyfhhbecv5xcltoy9oa9v446` (`telefone_id`),
  ADD KEY `FK42mty7au98rlwjw5ie9bpnyv3` (`usuario_id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `documento`
--
ALTER TABLE `documento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `evento`
--
ALTER TABLE `evento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de tabela `lista`
--
ALTER TABLE `lista`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `localizacao`
--
ALTER TABLE `localizacao`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `notificacao`
--
ALTER TABLE `notificacao`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `perfil`
--
ALTER TABLE `perfil`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `solicitacao`
--
ALTER TABLE `solicitacao`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `solicitacao_sub_tipo`
--
ALTER TABLE `solicitacao_sub_tipo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `solicitacao_tipo`
--
ALTER TABLE `solicitacao_tipo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `status`
--
ALTER TABLE `status`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `telefone`
--
ALTER TABLE `telefone`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `unidade`
--
ALTER TABLE `unidade`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `FK12wwnlkdoe79h38nvn5huqo0b` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKaglfm6upcr73lsw18b0qkhr5n` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`);

--
-- Limitadores para a tabela `email`
--
ALTER TABLE `email`
  ADD CONSTRAINT `FKkcch1rfv3cge9f9odplk9ouem` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `FKdgxunuenr1v7k0i72mvxm8hk6` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKmn5mbsx1td1vy04dr06yqcgwf` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`);

--
-- Limitadores para a tabela `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `FKvkr39hyeuaf8fg30h2kfm9jf` FOREIGN KEY (`localizacao_id`) REFERENCES `localizacao` (`id`);

--
-- Limitadores para a tabela `lista`
--
ALTER TABLE `lista`
  ADD CONSTRAINT `FKmc35ph2aiawlasqenptbnvmp` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);

--
-- Limitadores para a tabela `notificacao`
--
ALTER TABLE `notificacao`
  ADD CONSTRAINT `FK25y7f3no2tck3eow6veltg53h` FOREIGN KEY (`usuario_recebeu_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKeyml1o00l7mvpmsrw7hyw93he` FOREIGN KEY (`usuario_enviou_id`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `solicitacao`
--
ALTER TABLE `solicitacao`
  ADD CONSTRAINT `FK86nmyf1aeo9iapqplf2rjwaum` FOREIGN KEY (`solicitacao_tipo_id`) REFERENCES `solicitacao_tipo` (`id`),
  ADD CONSTRAINT `FKffu0flughrpsk76ojkdh1rfdb` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKfrcr9s03mc8rkdbxn8nc2a800` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`),
  ADD CONSTRAINT `FKl5pk9kiykqekt6g0458cv3rn4` FOREIGN KEY (`unidade_id`) REFERENCES `unidade` (`id`);

--
-- Limitadores para a tabela `solicitacao_sub_tipo`
--
ALTER TABLE `solicitacao_sub_tipo`
  ADD CONSTRAINT `FKjfg9dhv1ivjof2ycj2qqkcejj` FOREIGN KEY (`solicitacao_tipo_id`) REFERENCES `solicitacao_tipo` (`id`);

--
-- Limitadores para a tabela `solicitacao_tipo`
--
ALTER TABLE `solicitacao_tipo`
  ADD CONSTRAINT `FKd0rujaugb90e3quofg50730bd` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`);

--
-- Limitadores para a tabela `status`
--
ALTER TABLE `status`
  ADD CONSTRAINT `FKifbeedfb34w0nuo9ob2fih2sd` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`),
  ADD CONSTRAINT `FKnbl3ow6btsi20yy96980tbn3y` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `unidade_grupo`
--
ALTER TABLE `unidade_grupo`
  ADD CONSTRAINT `FKglhi4ptdrnbw12vljlq32pfg7` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`),
  ADD CONSTRAINT `FKn5ofaf21ctm95eqf2i04kyc2i` FOREIGN KEY (`unidade_id`) REFERENCES `unidade` (`id`);

--
-- Limitadores para a tabela `unidade_localizacao`
--
ALTER TABLE `unidade_localizacao`
  ADD CONSTRAINT `FKkb93uqc0894p75vkac62xkh97` FOREIGN KEY (`unidade_id`) REFERENCES `unidade` (`id`),
  ADD CONSTRAINT `FKl0v93m7flbjc5kr0w5odvtxpv` FOREIGN KEY (`localizacao_id`) REFERENCES `localizacao` (`id`);

--
-- Limitadores para a tabela `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK9po12ytp6krwvwht1kmd0qgxf` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`),
  ADD CONSTRAINT `FKsfuif7ykkynvxw4aqmk7shy7c` FOREIGN KEY (`unidade_id`) REFERENCES `unidade` (`id`);

--
-- Limitadores para a tabela `usuario_categoria`
--
ALTER TABLE `usuario_categoria`
  ADD CONSTRAINT `FKcjjx3pn1bytknm1dqey6uiax1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKqmtxu0f9s7nql4bidwu6j9iva` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);

--
-- Limitadores para a tabela `usuario_grupo`
--
ALTER TABLE `usuario_grupo`
  ADD CONSTRAINT `FKdofo9es0esuiahyw2q467crxw` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKk30suuy31cq5u36m9am4om9ju` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`);

--
-- Limitadores para a tabela `usuario_localizacao`
--
ALTER TABLE `usuario_localizacao`
  ADD CONSTRAINT `FK54le21jb5w5nri4v9u2y5eil1` FOREIGN KEY (`localizacao_id`) REFERENCES `localizacao` (`id`),
  ADD CONSTRAINT `FKaop8k25673ewvwjgrbtokb27c` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `usuario_responsavel_grupo`
--
ALTER TABLE `usuario_responsavel_grupo`
  ADD CONSTRAINT `FK8w1xvagfepgti8xhu5r9nt6fw` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`),
  ADD CONSTRAINT `FKsrk00du36gechnjtqs3d75bv7` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `usuario_telefone`
--
ALTER TABLE `usuario_telefone`
  ADD CONSTRAINT `FK42mty7au98rlwjw5ie9bpnyv3` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKmxyfhhbecv5xcltoy9oa9v446` FOREIGN KEY (`telefone_id`) REFERENCES `telefone` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
