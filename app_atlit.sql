-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 20, 2020 at 06:44 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_atlit`
--

-- --------------------------------------------------------

--
-- Table structure for table `atlit`
--

CREATE TABLE `atlit` (
  `ID` int(11) NOT NULL,
  `refid` int(11) DEFAULT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `tinggi_badan` float DEFAULT NULL,
  `berat_badan` float DEFAULT NULL,
  `jenis_kelamin` enum('Laki-Laki','Perempuan') DEFAULT NULL,
  `atlit` int(1) NOT NULL,
  `cabang_olahraga` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atlit`
--

INSERT INTO `atlit` (`ID`, `refid`, `nama`, `tanggal_lahir`, `tinggi_badan`, `berat_badan`, `jenis_kelamin`, `atlit`, `cabang_olahraga`) VALUES
(2, 10, 'b', '2000-03-02', 156, 56, 'Laki-Laki', 1, 'Lari'),
(3, 11, 'c', '2000-02-10', 156, 56, 'Perempuan', 1, 'Lari');

-- --------------------------------------------------------

--
-- Table structure for table `balke`
--

CREATE TABLE `balke` (
  `id` int(11) NOT NULL,
  `bulan` int(11) NOT NULL,
  `minggu` int(11) NOT NULL,
  `jarak_ditempuh` float NOT NULL,
  `vo2max` float NOT NULL,
  `tingkat_kebugaran` varchar(100) NOT NULL,
  `atlitid` int(11) NOT NULL,
  `Solusi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `balke`
--

INSERT INTO `balke` (`id`, `bulan`, `minggu`, `jarak_ditempuh`, `vo2max`, `tingkat_kebugaran`, `atlitid`, `Solusi`) VALUES
(3, 7, 4, 4295.89, 59.6835, 'Superior', 2, 'test'),
(4, 7, 4, 7067.37, 91.4632, 'Superior', 2, NULL),
(5, 7, 4, 15436.2, 187.426, 'Superior', 2, NULL),
(6, 7, 4, 4645.99, 63.698, 'Superior', 2, NULL),
(7, 7, 4, 5060.16, 68.4472, 'Superior', 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `beep`
--

CREATE TABLE `beep` (
  `id` int(11) NOT NULL,
  `bulan` int(11) DEFAULT NULL,
  `minggu` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `shuttle` int(11) DEFAULT NULL,
  `vo2max` float DEFAULT NULL,
  `tingkat_kebugaran` varchar(100) DEFAULT NULL,
  `atlitid` int(11) NOT NULL,
  `solusi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `beep`
--

INSERT INTO `beep` (`id`, `bulan`, `minggu`, `level`, `shuttle`, `vo2max`, `tingkat_kebugaran`, `atlitid`, `solusi`) VALUES
(1, 7, 4, 1, 0, 20.0966, 'Very Poor', 3, NULL),
(2, 7, 4, 1, 0, 20.0966, 'Very Poor', 2, NULL),
(3, 7, 4, 1, 0, 20.0966, 'Very Poor', 3, NULL),
(4, 7, 4, 1, 0, 20.0966, 'Very Poor', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cooper`
--

CREATE TABLE `cooper` (
  `id` int(11) NOT NULL,
  `bulan` int(11) NOT NULL,
  `minggu` int(11) NOT NULL,
  `vo2max` float NOT NULL,
  `tingkat_kebugaran` varchar(100) NOT NULL,
  `atlitid` int(11) NOT NULL,
  `waktu` int(11) NOT NULL,
  `Solusi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cooper`
--

INSERT INTO `cooper` (`id`, `bulan`, `minggu`, `vo2max`, `tingkat_kebugaran`, `atlitid`, `waktu`, `Solusi`) VALUES
(1, 1, 2, 60.5, 'test', 3, 100, NULL),
(2, 7, 2, 60.5, 'test', 2, 100, NULL),
(3, 7, 4, 85.6421, 'Superior', 2, 6, NULL),
(4, 7, 4, 85.6421, 'Superior', 2, 6, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `pelatih`
--

CREATE TABLE `pelatih` (
  `ID` int(11) NOT NULL,
  `refid` int(11) DEFAULT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `cabang_olahraga` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelatih`
--

INSERT INTO `pelatih` (`ID`, `refid`, `nama`, `tanggal_lahir`, `cabang_olahraga`) VALUES
(4, 6, 'a', '1999-10-01', 'Lari');

-- --------------------------------------------------------

--
-- Table structure for table `userlogin`
--

CREATE TABLE `userlogin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `unik_id` varchar(255) DEFAULT NULL,
  `enk_password` varchar(250) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `akses` enum('pelatih','atlit') DEFAULT NULL,
  `status` enum('active','noactive') DEFAULT NULL,
  `tgl_dibuat` datetime DEFAULT NULL,
  `tgl_diubah` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userlogin`
--

INSERT INTO `userlogin` (`id`, `username`, `unik_id`, `enk_password`, `salt`, `akses`, `status`, `tgl_dibuat`, `tgl_diubah`) VALUES
(6, 'a', '5d2fe6f99e74f6.61770890', 'CC524kfYvx7qQ4bg44cdD9K6ATM1OWVjNmU4MjY0', '59ec6e8264', 'pelatih', 'active', '2019-07-18 05:26:49', '2019-07-18 03:26:49'),
(10, 'b', '5d36669d33f862.12891455', '9ZOJEQ2D0LZVRRfell+LOzgslrJjNTJmOTk2MDAx', 'c52f996001', 'atlit', 'active', '2019-07-23 03:45:01', '2019-07-23 01:45:01'),
(11, 'c', '5d3729a88fd4a2.08407243', '+XT8Y+72F8N7txcnyEk2oXx0YT1jM2Y2NmRlYjcw', 'c3f66deb70', 'atlit', 'active', '2019-07-23 17:37:12', '2019-07-23 15:37:12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `atlit`
--
ALTER TABLE `atlit`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `refid` (`refid`);

--
-- Indexes for table `balke`
--
ALTER TABLE `balke`
  ADD PRIMARY KEY (`id`),
  ADD KEY `atlitid` (`atlitid`);

--
-- Indexes for table `beep`
--
ALTER TABLE `beep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `atlitid` (`atlitid`);

--
-- Indexes for table `cooper`
--
ALTER TABLE `cooper`
  ADD PRIMARY KEY (`id`),
  ADD KEY `atlitid` (`atlitid`);

--
-- Indexes for table `pelatih`
--
ALTER TABLE `pelatih`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `refid` (`refid`);

--
-- Indexes for table `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `atlit`
--
ALTER TABLE `atlit`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `balke`
--
ALTER TABLE `balke`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `beep`
--
ALTER TABLE `beep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `cooper`
--
ALTER TABLE `cooper`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pelatih`
--
ALTER TABLE `pelatih`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `userlogin`
--
ALTER TABLE `userlogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `atlit`
--
ALTER TABLE `atlit`
  ADD CONSTRAINT `atlit_ibfk_1` FOREIGN KEY (`refid`) REFERENCES `userlogin` (`id`);

--
-- Constraints for table `balke`
--
ALTER TABLE `balke`
  ADD CONSTRAINT `balke_ibfk_1` FOREIGN KEY (`atlitid`) REFERENCES `atlit` (`ID`);

--
-- Constraints for table `beep`
--
ALTER TABLE `beep`
  ADD CONSTRAINT `beep_ibfk_1` FOREIGN KEY (`atlitid`) REFERENCES `atlit` (`ID`);

--
-- Constraints for table `cooper`
--
ALTER TABLE `cooper`
  ADD CONSTRAINT `cooper_ibfk_1` FOREIGN KEY (`atlitid`) REFERENCES `atlit` (`ID`);

--
-- Constraints for table `pelatih`
--
ALTER TABLE `pelatih`
  ADD CONSTRAINT `pelatih_ibfk_1` FOREIGN KEY (`refid`) REFERENCES `userlogin` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
