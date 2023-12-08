-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 07 déc. 2023 à 15:48
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
('admin', 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `demande_ar`
--

CREATE TABLE `demande_ar` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `niveau` varchar(255) NOT NULL,
  `traité` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_ar`
--

INSERT INTO `demande_ar` (`id`, `user_id`, `niveau`, `traité`) VALUES
(1, 123, 'GC3', 0);

-- --------------------------------------------------------

--
-- Structure de la table `demande_as`
--

CREATE TABLE `demande_as` (
  `id` int(11) NOT NULL,
  `user_id` int(16) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp(),
  `traite` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_as`
--

INSERT INTO `demande_as` (`id`, `user_id`, `date`, `traite`) VALUES
(1, 123, '2023-12-06', 0),
(6, 123, '2023-12-06', 0);

-- --------------------------------------------------------

--
-- Structure de la table `demande_rn`
--

CREATE TABLE `demande_rn` (
  `id` int(11) NOT NULL,
  `user_id` int(15) NOT NULL,
  `niveau` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demande_rn`
--

INSERT INTO `demande_rn` (`id`, `user_id`, `niveau`) VALUES
(1, 123, '1°année préparatoire ');

-- --------------------------------------------------------

--
-- Structure de la table `notes_2ap1`
--

CREATE TABLE `notes_2ap1` (
  `id` varchar(255) NOT NULL,
  `algebre 1` varchar(255) NOT NULL,
  `analyse 1` varchar(255) NOT NULL,
  `physique 1` varchar(255) NOT NULL,
  `mecanique 1` varchar(255) NOT NULL,
  `informatique 1` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `algebre 2` varchar(255) NOT NULL,
  `analyse 2` varchar(255) NOT NULL,
  `physique 2` varchar(255) NOT NULL,
  `chime` varchar(255) NOT NULL,
  `MAO` varchar(255) NOT NULL,
  `langue et communication 2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_2ap2`
--

CREATE TABLE `notes_2ap2` (
  `id` varchar(255) NOT NULL,
  `algebre 3` varchar(255) NOT NULL,
  `analyse 3` varchar(255) NOT NULL,
  `electronique` varchar(255) NOT NULL,
  `mecanique 2` varchar(255) NOT NULL,
  `informatique 2` varchar(255) NOT NULL,
  `langue et communication 3` varchar(255) NOT NULL,
  `analyse 4` varchar(255) NOT NULL,
  `Math appliqué` varchar(255) NOT NULL,
  `physique 3` varchar(255) NOT NULL,
  `physique 4` varchar(255) NOT NULL,
  `management` varchar(255) NOT NULL,
  `langue et communication 4` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gc1`
--

CREATE TABLE `notes_gc1` (
  `id` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `probabilités et statistiques` varchar(255) NOT NULL,
  `réseaux informatique` varchar(255) NOT NULL,
  `Mécanique des solides déformables` varchar(255) NOT NULL,
  `theorie des graphes` varchar(255) NOT NULL,
  `Topographie SIG et Télédétection` varchar(255) NOT NULL,
  `Résistance des matériaux 1` varchar(255) NOT NULL,
  `Notions d'architecture, lecture des plans et métré` varchar(255) NOT NULL,
  `Mécanique des fluides` varchar(255) NOT NULL,
  `Aménagement du territoire et urbanisme` varchar(255) NOT NULL,
  `Géotechnique 1` varchar(255) NOT NULL,
  `Géologie de l'ingénieur` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gc2`
--

CREATE TABLE `notes_gc2` (
  `id` varchar(255) NOT NULL,
  `langue et communication 2` varchar(255) NOT NULL,
  `Mathématiques et méthodes numériques` varchar(255) NOT NULL,
  `Résistance des matériaux 2 et plasticité des poutres` varchar(255) NOT NULL,
  `Mécanique des sols` varchar(255) NOT NULL,
  `Matériaux de construction` varchar(255) NOT NULL,
  `Hydrologie, hydrogéologie et techniques de forage` varchar(255) NOT NULL,
  `Géotechnique 2` varchar(255) NOT NULL,
  `Management 2` varchar(255) NOT NULL,
  `Béton armé` varchar(255) NOT NULL,
  `Construction métallique` varchar(255) NOT NULL,
  `Routes` varchar(255) NOT NULL,
  `Hydraulique urbaine` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gc3`
--

CREATE TABLE `notes_gc3` (
  `id` varchar(255) NOT NULL,
  `Langues et communication 3` varchar(255) NOT NULL,
  `Management 3` varchar(255) NOT NULL,
  `Béton précontraint` varchar(255) NOT NULL,
  `Dynamique des structures et génie parasismique` varchar(255) NOT NULL,
  `Ponts` varchar(255) NOT NULL,
  `Barrages et ouvrages maritimes` varchar(255) NOT NULL,
  `Diagnostic, réhabilitation et maintenance` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gi1`
--

CREATE TABLE `notes_gi1` (
  `id` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `probabilités et statistiques` varchar(255) NOT NULL,
  `réseaux informatique` varchar(255) NOT NULL,
  `base de données relationnelles` varchar(255) NOT NULL,
  `theorie des graphes` varchar(255) NOT NULL,
  `Électronique Numérique` varchar(255) NOT NULL,
  `Programmation Web I` varchar(255) NOT NULL,
  `Programmation C Avancé et Complexité` varchar(255) NOT NULL,
  `Systèmes d''exploitation` varchar(255) NOT NULL,
  `Architecture des Ordinateurs et Assembleur` varchar(255) NOT NULL,
  `Théories des Langages et Compilation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gi2`
--

CREATE TABLE `notes_gi2` (
  `id` varchar(255) NOT NULL,
  `Modélisation et Programmation Objet` varchar(255) NOT NULL,
  `Vision Artificielle` varchar(255) NOT NULL,
  `Réseaux informatique II` varchar(255) NOT NULL,
  `Méthodologies et Génie Logiciel` varchar(255) NOT NULL,
  `Langues et Communication II` varchar(255) NOT NULL,
  `Administration et Optimisation des BD` varchar(255) NOT NULL,
  `Base de Données Relationnelle-Objet et Répartie` varchar(255) NOT NULL,
  `Programmation web II` varchar(255) NOT NULL,
  `Java Avancé` varchar(255) NOT NULL,
  `Intelligence Artificielle` varchar(255) NOT NULL,
  `Système d’Intégration et Progiciel` varchar(255) NOT NULL,
  `Management II` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gi3`
--

CREATE TABLE `notes_gi3` (
  `id` varchar(255) NOT NULL,
  `Qualité de Service et Optimisation des Réseaux` varchar(255) NOT NULL,
  `Management et Administration Réseaux` varchar(255) NOT NULL,
  `Sécurité Réseaux` varchar(255) NOT NULL,
  `Réseaux Sans-Fil et de Capteurs` varchar(255) NOT NULL,
  `Technologies DotNet et JEE` varchar(255) NOT NULL,
  `Langues et Communication III` varchar(255) NOT NULL,
  `Management III` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gm1`
--

CREATE TABLE `notes_gm1` (
  `id` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `probabilités et statistiques` varchar(255) NOT NULL,
  `réseaux informatique` varchar(255) NOT NULL,
  `base de données relationnelles` varchar(255) NOT NULL,
  `theorie des graphes` varchar(255) NOT NULL,
  `Électronique I` varchar(255) NOT NULL,
  `Automatique I` varchar(255) NOT NULL,
  `Mécanique` varchar(255) NOT NULL,
  `Réseaux électriques  et Signaux` varchar(255) NOT NULL,
  `Energétique` varchar(255) NOT NULL,
  `Matériaux et RDM` varchar(255) NOT NULL,
  `Modélisation et Programmation Objet` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gm2`
--

CREATE TABLE `notes_gm2` (
  `id` varchar(255) NOT NULL,
  `Mathématiques et Méthodes Numériques` varchar(255) NOT NULL,
  `Fabrication mécanique et productique` varchar(255) NOT NULL,
  `Conception des machines I` varchar(255) NOT NULL,
  `Mécanismes et Robotique` varchar(255) NOT NULL,
  `Electronique II` varchar(255) NOT NULL,
  `Automatique II` varchar(255) NOT NULL,
  `Electrotechnique et Électronique de puissance` varchar(255) NOT NULL,
  `Langues et TEC` varchar(255) NOT NULL,
  `Automatismes et Supervision` varchar(255) NOT NULL,
  `Actionneurs` varchar(255) NOT NULL,
  `Electronique embarquée` varchar(255) NOT NULL,
  `Technologie de l'Automobile` varchar(255) NOT NULL,
  `Instrumentation et Communication Industrielles` varchar(255) NOT NULL,
  `Conception des machines II` varchar(255) NOT NULL,
  `Management 2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gm3`
--

CREATE TABLE `notes_gm3` (
  `id` varchar(255) NOT NULL,
  `Systèmes mécatroniques` varchar(255) NOT NULL,
  `Systèmes embarqués` varchar(255) NOT NULL,
  `Qualité et Maintenance` varchar(255) NOT NULL,
  `Conception de produits et innovation` varchar(255) NOT NULL,
  `Gestion de la production` varchar(255) NOT NULL,
  `Langues&Communication 3` varchar(255) NOT NULL,
  `Management 3` varchar(255) NOT NULL,
  `Projets, Stages & Coaching` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gstr1`
--

CREATE TABLE `notes_gstr1` (
  `id` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `probabilités et statistiques` varchar(255) NOT NULL,
  `réseaux informatique` varchar(255) NOT NULL,
  `base de données relationnelles` varchar(255) NOT NULL,
  `theorie des graphes` varchar(255) NOT NULL,
  `Électronique Numérique` varchar(255) NOT NULL,
  `Réseaux de transport` varchar(255) NOT NULL,
  `Modélisation et Programmation  orienté objet` varchar(255) NOT NULL,
  `Electronique et Modulation  analogique` varchar(255) NOT NULL,
  `Traitement du signal` varchar(255) NOT NULL,
  `Ingénierie Hyperfréquence` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gstr2`
--

CREATE TABLE `notes_gstr2` (
  `id` varchar(255) NOT NULL,
  `Communications  numériques` varchar(255) NOT NULL,
  `Réseaux informatique 2` varchar(255) NOT NULL,
  `Réseaux cellulaires et sans-fil` varchar(255) NOT NULL,
  `Traitement numérique du signal et Modulation numérique` varchar(255) NOT NULL,
  `Propagation radio et réseaux optique` varchar(255) NOT NULL,
  `Les Antennes` varchar(255) NOT NULL,
  `Langues & TEC` varchar(255) NOT NULL,
  `Technologie DotNet` varchar(255) NOT NULL,
  `Traitement de l'image et de la parole` varchar(255) NOT NULL,
  `Programmation Réseau  et Systèmes Répartis` varchar(255) NOT NULL,
  `Informatique industrielle` varchar(255) NOT NULL,
  `Système embarqué &  temps réel 1` varchar(255) NOT NULL,
  `Management 2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_gstr3`
--

CREATE TABLE `notes_gstr3` (
  `id` varchar(255) NOT NULL,
  `Ingénierie Spatiale` varchar(255) NOT NULL,
  `Systèmes de communication numérique avancés` varchar(255) NOT NULL,
  `Sécurité des Réseaux 2` varchar(255) NOT NULL,
  `Réseaux mobiles et sans-fil  avancé` varchar(255) NOT NULL,
  `Systèmes embarqués 2 et Java mobile` varchar(255) NOT NULL,
  `Langues&Communication 3` varchar(255) NOT NULL,
  `Management 3` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_scm1`
--

CREATE TABLE `notes_scm1` (
  `id` varchar(255) NOT NULL,
  `langue et communication 1` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `probabilités et statistiques` varchar(255) NOT NULL,
  `réseaux informatique` varchar(255) NOT NULL,
  `base de données relationnelles` varchar(255) NOT NULL,
  `theorie des graphes` varchar(255) NOT NULL,
  `Analyse fonctionnelle et analyse de la valeur` varchar(255) NOT NULL,
  `Gestion de la production` varchar(255) NOT NULL,
  `Outils de modélisation et d'évaluation des performances` varchar(255) NOT NULL,
  `Management de la qualité` varchar(255) NOT NULL,
  `Optimisation combinatoire` varchar(255) NOT NULL,
  `Techniques d'achat et de réduction des coûts` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_scm2`
--

CREATE TABLE `notes_scm2` (
  `id` varchar(255) NOT NULL,
  `Modélisation et Programmation Objet` varchar(255) NOT NULL,
  `management 1` varchar(255) NOT NULL,
  `PL/SQL, Administration et optimisation de bases de données` varchar(255) NOT NULL,
  `Management de la chaîne logistique` varchar(255) NOT NULL,
  `Sûreté de fonctionnement et Gestion de maintenance` varchar(255) NOT NULL,
  `Management 2` varchar(255) NOT NULL,
  `Langues et TEC 2` varchar(255) NOT NULL,
  `Technologie entreprise` varchar(255) NOT NULL,
  `Ordonnancement de la production` varchar(255) NOT NULL,
  `Exploration et analyse des données` varchar(255) NOT NULL,
  `Gestion des stocks et entreposage` varchar(255) NOT NULL,
  `Excellence industrielle` varchar(255) NOT NULL,
  `Théorie de la décision et GSI` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notes_scm3`
--

CREATE TABLE `notes_scm3` (
  `id` varchar(255) NOT NULL,
  `Logistique de distribution` varchar(255) NOT NULL,
  `Système d’information en SCM` varchar(255) NOT NULL,
  `Simulation des systèmes industriels` varchar(255) NOT NULL,
  `Logistique portuaire, ferroviaire et aéroportuaire` varchar(255) NOT NULL,
  `Management 3` varchar(255) NOT NULL,
  `Langues et TEC 3` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id` int(11) NOT NULL,
  `user_id` int(15) NOT NULL,
  `dctype` varchar(255) NOT NULL,
  `msg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id`, `user_id`, `dctype`, `msg`) VALUES
(1, 123, 'hhhhhhhhhhhh', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `stage`
--

CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
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
  `traité` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `stage`
--

INSERT INTO `stage` (`id`, `user_id`, `nom_entreprise`, `secteur`, `email_entreprise`, `tel_entreprise`, `encadrant_entreprise`, `email_encadrant`, `encadrant_ensa`, `debut_stage`, `fin_stage`, `sujet_stage`, `traité`) VALUES
(1, 123, 'test', 'test', 'test@', '060000000', 'test', 'test', 'lmao', '2001-08-22', '2005-09-23', 'txt txt ', 0);

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

CREATE TABLE `student` (
  `CNE` int(10) NOT NULL,
  `CIN` varchar(255) NOT NULL,
  `Nom_complet` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `major` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `student`
--

INSERT INTO `student` (`CNE`, `CIN`, `Nom_complet`, `email`, `major`) VALUES
(123, 'D12', 'test', 'test', '2ap1'),
(1133, 'D31', 'MHAMED Mahmoud', 'mahmoud.mhamed@etu.uae.ac.ma', '2ap2'),
(1231, 'D19', 'BADR Basma', 'basma.badr@etu.uae.ac.ma', '2ap1'),
(1232, 'D20', 'RAHIL Ilyas', 'ilyas.rahil@etu.uae.ac.ma', '2ap1'),
(1233, 'D21', 'MSBAH Souhail', 'souhail.msbah@etu.uae.ac.ma', '2ap2'),
(1234, 'D13', 'SALIM Ahmed', 'ahmed.salim@etu.uae.ac.ma', '2ap2'),
(1235, 'D14', 'SALIM Kamal', 'salim.kamal@etu.uae.ac.ma', '2ap1'),
(1236, 'D15', 'NOUHA Rachid', 'nouha.rachid@etu.uae.ac.ma', 'GI1'),
(1237, 'D16', 'NALI Nawal', 'nawal.nali@etu.uae.ac.ma', 'GI2'),
(1238, 'D17', 'HAFIDI Houdayfa', 'hodayfa.hafaidi@etu.uae.ac.ma', 'GI2'),
(1239, 'D18', 'AMRI Omar', 'omar.amri@etu.uae.ac.ma', '2ap1'),
(1333, 'D41', 'YAACOUB Houda', 'houda.yaacoub@etu.uae.ac.ma', 'GC1'),
(1433, 'D51', 'LAMIAE Lamiss', 'lamiss.lamiae@etu.uae.ac.ma', 'GC2'),
(1533, 'D61', 'NEN Amina', 'amina.ben@etu.uae.ac.ma', 'GI1'),
(1633, 'D71', 'NAIM Fatima', 'fatima.naim@etu.uae.ac.ma', 'SCM1'),
(1733, 'D81', 'RAMY Saad', 'saad.ramy@etu.uae.ac.ma', 'SCM2'),
(1833, 'D91', 'ECH Alae', 'alae.ech@etu.uae.ac.ma', 'GI2'),
(1933, 'D32', 'NIM Tassnim', 'tassnim.nim@etu.uae.ac.ma', 'GI1'),
(2233, 'D34', 'NOON Nawar', 'nawar.noon@etu.uae.ac.ma', '2ap1'),
(2345, 'D99', 'MOTAHID Anawar', 'anawar.motahid@etu.uae.ac.ma', 'GC1'),
(3233, 'D35', 'DAVID Dave', 'dave.david@etu.uae.ac.ma', '2ap2'),
(4423, 'D78', 'KARBAL Said', 'said.karbal@etu.uae.ac.ma', 'GI2'),
(5533, 'D27', 'KENZ Amal', 'amal.kenz@etu.uae.ac.ma', '2ap1'),
(5555, 'D25', 'HAMID Kaoutar', 'kaoutar.hamid@etu.uae.ac.ma', '2ap1'),
(6709, 'D88', 'KOULI Nour', 'nour.kouli@etu.uae.ac.ma', 'GC3'),
(8345, 'D79', 'ASAAD Mohamed', 'mohamed.asaad@etu.uae.ac.ma', 'SCM3');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `demande_ar`
--
ALTER TABLE `demande_ar`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `demande_as`
--
ALTER TABLE `demande_as`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `demande_rn`
--
ALTER TABLE `demande_rn`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `stage`
--
ALTER TABLE `stage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`CNE`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `demande_ar`
--
ALTER TABLE `demande_ar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `demande_as`
--
ALTER TABLE `demande_as`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `demande_rn`
--
ALTER TABLE `demande_rn`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `stage`
--
ALTER TABLE `stage`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `stage`
--
ALTER TABLE `stage`
  ADD CONSTRAINT `stage_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `student` (`CNE`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
