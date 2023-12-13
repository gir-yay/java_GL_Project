-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 13 déc. 2023 à 02:10
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gl`
--

-- --------------------------------------------------------

--
-- Structure de la table `admins`
--

CREATE TABLE `admins` (
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admins`
--

INSERT INTO `admins` (`email`, `password`) VALUES
('admin', 'admin'),
('admin', 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `demande_ar`
--

CREATE TABLE `demande_ar` (
  `id` int(11) PRIMARY key NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `niveau` varchar(255) NOT NULL,
  `traité` tinyint(1) NOT NULL DEFAULT 0,
  `statuts` tinyint(1) DEFAULT NULL default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_ar`
--

INSERT INTO `demande_ar` ( `user_id`, `niveau`, `traité`, `statuts`) VALUES
( 123, 'GC3', 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `demande_as`
--

CREATE TABLE `demande_as` (
  `id` int(11) PRIMARY key NOT NULL AUTO_INCREMENT,
  `user_id` int(16) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp(),
  `traite` tinyint(1) NOT NULL DEFAULT 0,
  `statuts` tinyint(1) DEFAULT NULL default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_as`
--

INSERT INTO `demande_as` ( `user_id`, `date`, `traite`, `statuts`) VALUES
( 123, '2023-12-06', 1, 1),
( 5533, '2023-12-11', 1, 0),
( 123, '2023-12-12', 0, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `demande_rn`
--

CREATE TABLE `demande_rn` (
  `id` int(11) primary key NOT NULL AUTO_INCREMENT,
  `user_id` int(15) NOT NULL,
  `niveau` varchar(255) NOT NULL,
  `traité` tinyint(1) NOT NULL DEFAULT 0,
  `statuts` tinyint(1) DEFAULT NULL default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_rn`
--

INSERT INTO `demande_rn` (`user_id`, `niveau`, `traité`, `statuts`) VALUES
( 123, '2ap1 ', 0, NULL),
( 1232, 'GM1', 1, 1),
( 1231, '2ap2', 1, 0),
( 1233, 'GI2', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notes_2ap1`
--

CREATE TABLE `notes_2ap1` (
  `id` int(11) NOT NULL ,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notes_2ap1`
--

INSERT INTO `notes_2ap1` (`id`, `user_id`, `module`, `note`, `Résultat`, `pts jury`) VALUES
(1, 123, 'Algebre 1', 13, 'Validé', ' '),
(2, 123, 'Analyse 1', 11, 'Validé', ' '),
(3, 123, 'Physique 1', 12, 'Validé', ' '),
(4, 123, 'Mecanique 1', 13, 'Validé', ' '),
(5, 123, 'Informatique 1', 18, 'Validé', ' '),
(6, 123, 'Langue et communication 1', 17, 'Validé', ' '),
(7, 123, 'Analyse 2', 18, 'Validé', ' '),
(8, 123, 'Algebre 2', 16, 'Validé', ' '),
(9, 123, 'Physique 2', 13, 'Validé', ' '),
(10, 123, 'Chimie', 12, 'Validé', ' '),
(11, 123, 'MAO', 13, 'Validé', ' '),
(12, 123, 'Langue et communication 2', 16, 'Validé', ' '),
(1, 1231, 'Algebre 1', 15, 'Validé', ' '),
(2, 1231, 'Analyse 1', 13, 'Validé', ' '),
(3, 1231, 'Physique 1', 16, 'Validé', ' '),
(4, 1231, 'Mecanique 1', 14, 'Validé', ' '),
(5, 1231, 'Informatique 1', 17, 'Validé', ' '),
(6, 1231, 'Langue et communication 1', 16, 'Validé', ' '),
(7, 1231, 'Analyse 2', 14, 'Validé', ' '),
(8, 1231, 'Algebre 2', 16, 'Validé', ' '),
(9, 1231, 'Physique 2', 12, 'Validé', ' '),
(10, 1231, 'Chimie', 15, 'Validé', ' '),
(11, 1231, 'MAO', 14, 'Validé', ' '),
(12, 1231, 'Langue et communication 2', 16, 'Validé', ' '),
(1, 1232, 'Algebre 1', 14, 'Validé', ' '),
(2, 1232, 'Analyse 1', 10, 'Validé', ' '),
(3, 1232, 'Physique 1', 13, 'Validé', ' '),
(4, 1232, 'Mecanique 1', 13, 'Validé', ' '),
(5, 1232, 'Informatique 1', 16, 'Validé', ' '),
(6, 1232, 'Langue et communication 1', 15, 'Validé', ' '),
(7, 1232, 'Analyse 2', 13, 'Validé', ' '),
(8, 1232, 'Algebre 2', 17, 'Validé', ' '),
(9, 1232, 'Physique 2', 12, 'Validé', ' '),
(10, 1232, 'Chimie', 11, 'Validé', ' '),
(11, 1232, 'MAO', 14, 'Validé', ' '),
(12, 1232, 'Langue et communication 2', 15, 'Validé', ' '),
(1, 1233, 'Algebre 1', 16, 'Validé', ' '),
(2, 1233, 'Analyse 1', 10, 'Validé', ' '),
(3, 1233, 'Physique 1', 13, 'Validé', ' '),
(4, 1233, 'Mecanique 1', 13, 'Validé', ' '),
(5, 1233, 'Informatique 1', 19, 'Validé', ' '),
(6, 1233, 'Langue et communication 1', 14, 'Validé', ' '),
(7, 1233, 'Analyse 2', 13, 'Validé', ' '),
(8, 1233, 'Algebre 2', 14, 'Validé', ' '),
(9, 1233, 'Physique 2', 12, 'Validé', ' '),
(10, 1233, 'Chimie', 16, 'Validé', ' '),
(11, 1233, 'MAO', 13, 'Validé', ' '),
(12, 1233, 'Langue et communication 2', 17, 'Validé', ' ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_2ap2`
--

CREATE TABLE `notes_2ap2` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notes_2ap2`
--

INSERT INTO `notes_2ap2` (`id`, `user_id`, `module`, `note`, `Résultat`, `pts jury`) VALUES
(1, 123, 'algebre 3', 13, 'Validé', ' '),
(2, 123, 'analyse 3', 11, 'Validé', ' '),
(3, 123, 'electronique', 12, 'Validé', ' '),
(4, 123, 'mecanique 2', 13, 'Validé', ' '),
(5, 123, 'informatique 2', 18, 'Validé', ' '),
(6, 123, 'langue et communication 3', 17, 'Validé', ' '),
(7, 123, 'analyse 4', 18, 'Validé', ' '),
(8, 123, 'Math appliqué', 16, 'Validé', ' '),
(9, 123, 'physique 3', 13, 'Validé', ' '),
(10, 123, 'physique 4', 12, 'Validé', ' '),
(11, 123, 'management', 13, 'Validé', ' '),
(12, 123, 'langue et communication 4', 16, 'Validé', ' '),
(1, 1231, 'Algebre 3', 13, 'Validé', ' '),
(2, 1231, 'Analyse 3', 11, 'Validé', ' '),
(3, 1231, 'Electronique', 14, 'Validé', ' '),
(4, 1231, 'Mecanique 2', 13, 'Validé', ' '),
(5, 1231, 'Informatique 2', 18, 'Validé', ' '),
(6, 1231, 'Langue et communication 3', 17, 'Validé', ' '),
(7, 1231, 'Analyse 4', 16, 'Validé', ' '),
(8, 1231, 'Math appliqué', 16, 'Validé', ' '),
(9, 1231, 'Physique 3', 13, 'Validé', ' '),
(10, 1231, 'Physique 4', 12, 'Validé', ' '),
(11, 1231, 'Management', 10, 'Validé', ' '),
(12, 1231, 'Langue et communication 4', 15, 'Validé', ' '),
(1, 1232, 'Algebre 3', 13, 'Validé', ' '),
(2, 1232, 'Analyse 3', 11, 'Validé', ' '),
(3, 1232, 'Electronique', 14, 'Validé', ' '),
(4, 1232, 'Mecanique 2', 13, 'Validé', ' '),
(5, 1232, 'Informatique 2', 18, 'Validé', ' '),
(6, 1232, 'Langue et communication 3', 17, 'Validé', ' '),
(7, 1232, 'Analyse 4', 16, 'Validé', ' '),
(8, 1232, 'Math appliqué', 16, 'Validé', ' '),
(9, 1232, 'Physique 3', 13, 'Validé', ' '),
(10, 1232, 'Physique 4', 12, 'Validé', ' '),
(11, 1232, 'Management', 11, 'Validé', ' '),
(12, 1232, 'Langue et communication 4', 15, 'Validé', ' '),
(1, 1233, 'Algebre 3', 14, 'Validé', ' '),
(2, 1233, 'Analyse 3', 16, 'Validé', ' '),
(3, 1233, 'Electronique', 15, 'Validé', ' '),
(4, 1233, 'Mecanique 2', 12, 'Validé', ' '),
(5, 1233, 'Informatique 2', 14, 'Validé', ' '),
(6, 1233, 'Langue et communication 3', 14, 'Validé', ' '),
(7, 1233, 'Analyse 4', 13, 'Validé', ' '),
(8, 1233, 'Math appliqué', 14, 'Validé', ' '),
(9, 1233, 'Physique 3', 15, 'Validé', ' '),
(10, 1233, 'Physique 4', 14, 'Validé', ' '),
(11, 1233, 'Management', 15, 'Validé', ' '),
(12, 1233, 'Langue et communication 4', 14, 'Validé', ' ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_gi1`
--

CREATE TABLE `notes_gi1` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notes_gi1`
--

INSERT INTO `notes_gi1` (`id`, `user_id`, `module`, `note`, `Résultat`, `pts jury`) VALUES
(1, 1231, 'Langue et communication 1', 15, 'Validé', ' '),
(2, 1231, 'Management 1', 11, 'Validé', ' '),
(3, 1231, 'Probabilités et statistiques', 14, 'Validé', ' '),
(4, 1231, 'Réseaux informatique', 15, 'Validé', ' '),
(5, 1231, 'Base de données relationnelles', 14, 'Validé', ' '),
(6, 1231, 'Theorie des graphes', 17, 'Validé', ' '),
(7, 1231, 'Programmation Web I', 15, 'Validé', ' '),
(8, 1231, 'Électronique Numérique', 16, 'Validé', ' '),
(9, 1231, 'Programmation C Avancé et Complexité', 13, 'Validé', ' '),
(10, 1231, 'Systèmes d\'exploitation', 14, 'Validé', ' '),
(11, 1231, 'Architecture des Ordinateurs et Assembleur', 15, 'Validé', ' '),
(12, 1231, 'Théories des Langages et Compilation', 16, 'Validé', ' '),
(1, 1233, 'Langue et communication 1', 14, 'Validé', ' '),
(2, 1233, 'Management 1', 12, 'Validé', ' '),
(3, 1233, 'Probabilités et statistiques', 15, 'Validé', ' '),
(4, 1233, 'Réseaux informatique', 16, 'Validé', ' '),
(5, 1233, 'Base de données relationnelles', 13, 'Validé', ' '),
(6, 1233, 'Theorie des graphes', 17, 'Validé', ' '),
(7, 1233, 'Programmation Web I', 15, 'Validé', ' '),
(8, 1231, 'Électronique Numérique', 14, 'Validé', ' '),
(9, 1233, 'Programmation C Avancé et Complexité', 16, 'Validé', ' '),
(10, 1233, 'Systèmes d\'exploitation', 14, 'Validé', ' '),
(11, 1233, 'Architecture des Ordinateurs et Assembleur', 13, 'Validé', ' '),
(12, 1233, 'Théories des Langages et Compilation', 17, 'Validé', ' ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_gi2`
--

CREATE TABLE `notes_gi2` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notes_gi2`
--

INSERT INTO `notes_gi2` (`id`, `user_id`, `module`, `note`, `Résultat`, `pts jury`) VALUES
(1, 1233, 'langue et communication 2', 16, 'Validé', ' '),
(2, 1233, 'Modélisation et Programmation Objet', 13, 'Validé', ' '),
(3, 1233, 'Vision Artificielle', 12, 'Validé', ' '),
(4, 1233, 'Réseaux informatique II', 15, 'Validé', ' '),
(5, 1233, 'Administration et Optimisation des BD', 14, 'Validé', ' '),
(6, 1233, 'Base de Données Relationnelle-Objet et Répartie', 13, 'Validé', ' '),
(7, 1233, 'Programmation web II', 17, 'Validé', ' '),
(8, 1233, 'Java Avancé', 15, 'Validé', ' '),
(9, 1233, 'Management 2', 14, 'Validé', ' '),
(10, 1233, 'Intelligence Artificielle', 15, 'Validé', ' '),
(11, 1233, 'Système d’Intégration et Progiciel', 13, 'Validé', ' ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_gm1`
--

CREATE TABLE `notes_gm1` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notes_gm1`
--

INSERT INTO `notes_gm1` (`id`, `user_id`, `module`, `note`, `Résultat`, `pts jury`) VALUES
(1, 1232, 'Langue et communication 1', 15, 'Validé', ' '),
(2, 1232, 'Management 1', 16, 'Validé', ' '),
(3, 1232, 'Probabilités et statistiques', 14, 'Validé', ' '),
(4, 1232, 'Réseaux informatique', 16, 'Validé', ' '),
(5, 1232, 'Base de données relationnelles', 14, 'Validé', ' '),
(6, 1232, 'Theorie des graphes', 18, 'Validé', ' '),
(6, 1232, 'Électronique I', 16, 'Validé', ' '),
(7, 1232, 'Automatique I', 14, 'Validé', ' '),
(8, 1232, 'Mécanique', 12, 'Validé', ' '),
(9, 1232, 'Réseaux électriques  et Signaux', 13, 'Validé', ' '),
(10, 1232, 'Energétique', 14, 'Validé', ' '),
(11, 1232, 'Matériaux et RDM', 12, 'Validé', ' '),
(12, 1232, 'Modélisation et Programmation Objet', 14, 'Validé', ' ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_gm2`
--

CREATE TABLE `notes_gm2` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_scm1`
--

CREATE TABLE `notes_scm1` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_scm2`
--

CREATE TABLE `notes_scm2` (
  `id` int(11) NOT NULL ,
  `user_id` int(11) DEFAULT NULL,
  `module` varchar(255) NOT NULL,
  `note` int(10) NOT NULL,
  `Résultat` varchar(255) NOT NULL,
  `pts jury` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id` int(11) NOT NULL primary key AUTO_INCREMENT,
  `user_id` int(15) NOT NULL,
  `dctype` varchar(255) NOT NULL,
  `msg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` ( `user_id`, `dctype`, `msg`) VALUES
( 123, 'hhhhhhhhhhhh', 'Intellectum est enim mihi quidem in multis, et maxime in me ipso, sed paulo ante in omnibus, cum M. Marcellum senatui reique publicae concessisti, commemoratis praesertim offensionibus, te auctoritatem huius ordinis dignitatemque rei publicae tuis vel doloribus vel suspicionibus anteferre. Ille quidem fructum omnis ante actae vitae hodierno die maximum cepit, cum summo consensu senatus, tum iudicio tuo gravissimo et maximo. Ex quo profecto intellegis quanta in dato beneficio sit laus, cum in accepto sit tanta gloria.\r\n'),
( 123, 'Relevé de notes', 'test final');

-- --------------------------------------------------------

--
-- Structure de la table `stage`
--

CREATE TABLE `stage` (
  `id` int(11) primary key NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `nom_entreprise` varchar(255) DEFAULT NULL,
  `secteur` varchar(255) DEFAULT NULL,
  `email_entreprise` varchar(255) DEFAULT NULL,
  `tel_entreprise` varchar(20) DEFAULT NULL,
  `encadrant_entreprise` varchar(255) DEFAULT NULL,
  `email_encadrant` varchar(255) DEFAULT NULL,
  `encadrant_ensa` varchar(255) DEFAULT NULL,
  `debut_stage` date DEFAULT NULL,
  `fin_stage` date DEFAULT NULL,
  `sujet_stage` varchar(255) DEFAULT NULL,
  `traité` tinyint(1) NOT NULL DEFAULT 0,
  `statuts` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `stage`
--

INSERT INTO `stage` ( `user_id`, `nom_entreprise`, `secteur`, `email_entreprise`, `tel_entreprise`, `encadrant_entreprise`, `email_encadrant`, `encadrant_ensa`, `debut_stage`, `fin_stage`, `sujet_stage`, `traité`, `statuts`) VALUES
( 123, 'test', 'test', 'test@', '060000000', 'test', 'test', 'lmao', '2001-08-22', '2005-09-23', 'txt txt ', 1, 0),
( 123, 'SARL', 'lmao', 'test@gmail.com', '060000', 'lmao tes ', 'test encade@@ ', 'mr prof', '2022-02-22', '2022-07-22', 'chi la3ba dartha ymkn', 1, 1),
( 123, 'SARL', 'repo', 'testent@', '06000000', 'pr mr ', 'pr@', 'pr mr ensa', '2001-08-22', '2002-09-05', 'chi la3ba	', 0, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

CREATE TABLE `student` (
  `CNE` int(10) NOT NULL primary key,
  `CIN` varchar(255) NOT NULL,
  `Nom_complet` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `major` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `student`
--

INSERT INTO `student` (`CNE`, `CIN`, `Nom_complet`, `email`, `major`) VALUES
(123, 'D12', 'Ezzouak Mohammed', 'ezzouak2001@gmail.com', 'SCM1'),
(1134, 'D31', 'Biha Oumaima', 'oumaima.biha@etu.uae.ac.ma', '2ap2'),
(1231, 'D99', ' Zaoioui Salma', 'salma.zaoioui@etu.uae.ac.ma', 'GI2'),
(1232, 'D19', ' Risy Maryem', 'maryem.risy@etu.uae.ac.ma', 'GM2'),
(1233, 'LC34', ' Rahmouni Ghizlane', 'ghizlane.rahmouni@etu.uae.ac.ma', 'GI3'),
(1235, 'D20', 'Elidrissi Salma', 'salma1.elidrissi@etu.uae.ac.ma', 'SCM1'),
(1933, 'D32', 'NIM Tassnim', 'tassnim.nim@etu.uae.ac.ma', 'GI1'),
(2233, 'D34', 'NOON Nawar', 'nawar.noon@etu.uae.ac.ma', '2ap1'),
(2345, 'D99', 'MOTAHID Anawar', 'anawar.motahid@etu.uae.ac.ma', 'GC1'),
(3233, 'D35', 'DAVID Dave', 'dave.david@etu.uae.ac.ma', '2ap2'),
(4423, 'D78', 'KARBAL Said', 'said.karbal@etu.uae.ac.ma', 'GI2'),
(5533, 'D27', 'KENZ Amal', 'amal.kenz@etu.uae.ac.ma', 'GM3'),
(5555, 'D25', 'HAMID Kaoutar', 'kaoutar.hamid@etu.uae.ac.ma', 'GSTR2'),
(6709, 'D88', 'KOULI Nour', 'nour.kouli@etu.uae.ac.ma', 'GC3'),
(8345, 'D79', 'ASAAD Mohamed', 'mohamed.asaad@etu.uae.ac.ma', 'SCM3');


