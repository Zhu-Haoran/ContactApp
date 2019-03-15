-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Mar 13, 2019 at 10:35 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `contact_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `idperson` int(11) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `nickname` varchar(45) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `email_address` varchar(150) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `Category` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`idperson`, `lastname`, `firstname`, `nickname`, `phone_number`, `address`, `email_address`, `birth_date`, `Category`) VALUES
(1, 'GENG', 'Mingxue', 'Mingxuexue', '0761559161', '5 rue de FF', 'mingxue@gmail.com', '1997-06-16', 'MySlef'),
(2, 'PAN', 'Yuanxiang', 'Panpan', '0766291882', '5 rue de LL', 'yuanxiang@gamil.com', '1997-02-24', 'Friend'),
(3, 'ZHENG', 'Wanqi', 'Qiqi', '0655193872', '5 rue de EE', 'wanqi@gmail.com', '1996-07-27', 'Friend'),
(4, 'CAO', 'Yu', 'Yuyu', '0567532098', '5 rue de NN', 'yuyu@gmail.com', '1996-07-08', 'Friend'),
(5, 'WANG', 'Xin', 'Xinxin', '0736524438', '5 rue de DD', 'xinxin@gmail.com', '1996-01-30', 'Friend'),
(6, 'XU', 'Lu', 'Lulu', '0536784291', '10 rue de BB', 'lulu@gmail.com', '1995-05-09', 'Friend'),
(7, 'ZHU', 'Haoran', 'Haoran', '0647386554', '10 rue de AA', 'haoran@gmail.com', '1995-01-01', 'Friend');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`idperson`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `idperson` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
