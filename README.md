Energy Trading Management App
Project Overview

This is a console-based Java application that manages Energy Trade records using JDBC and MySQL.
The app demonstrates CRUD operations (Create, Read, Update, Delete) along with search functionality, providing a simple trade management system for commodities like Power, Gas, and Oil.

Features

*Add Trade → Insert a new trade record (Buy/Sell).
*View All Trades → Display all stored trades in a formatted view.
*Update Trade → Modify trade details (Price, Volume).
*Delete Trade → Remove a trade by TradeID.
*Search Trades → Search by Counterparty or Commodity.
*Menu-driven Console UI for smooth interaction.

Tech Stack

*Java 17+ (Core Java, JDBC API)
*MySQL 8.x (Relational Database)
*Maven / JDK (build & run environment)

Database Setup

*Run the following script in MySQL to create the database and table:
CREATE DATABASE EnergyTradingDB;

USE EnergyTradingDB;

CREATE TABLE Trades (
    TradeID INT PRIMARY KEY AUTO_INCREMENT,
    TradeDate DATE NOT NULL,
    Counterparty VARCHAR(100) NOT NULL,
    Commodity VARCHAR(50) NOT NULL,       -- e.g., Power, Gas, Oil
    Volume DECIMAL(10,2) NOT NULL,        -- e.g., MWh, MMBtu
    Price DECIMAL(10,2) NOT NULL,         -- Price per unit
    TradeType VARCHAR(10) CHECK (TradeType IN ('BUY','SELL'))
);

 How to Run

*Clone/download the project.
*Import into your IDE (Eclipse/IntelliJ/VS Code).
*Update DB credentials in TradeApp.java:

private static final String URL = "jdbc:mysql://localhost:3306/EnergyTradingDB";
private static final String USER = "root";
private static final String PASSWORD = "your_password_here";


Run the program:

*javac TradeApp.java
*java assignment.TradeApp

Use the console menu to perform CRUD operations.
