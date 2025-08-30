package assignment;

import java.sql.*;
import java.util.Scanner;

public class TradeApp {
    private static final String URL = "jdbc:mysql://localhost:3306/EnergyTradingDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Bhargav@2003";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("✅ Connected successfully to EnergyTradingDB");

            while (true) {
                System.out.println("\n--- Energy Trading Menu ---");
                System.out.println("1. Add Trade");
                System.out.println("2. View All Trades");
                System.out.println("3. Update Trade");
                System.out.println("4. Delete Trade");
                System.out.println("5. Search Trades by Counterparty / Commodity");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addTrade(con, sc);
                    case 2 -> viewTrades(con);
                    case 3 -> updateTrade(con, sc);
                    case 4 -> deleteTrade(con, sc);
                    case 5 -> searchTrades(con, sc);
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice, try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addTrade(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Trade Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter Counterparty: ");
        String counterparty = sc.nextLine();
        System.out.print("Enter Commodity: ");
        String commodity = sc.nextLine();
        System.out.print("Enter Volume: ");
        double volume = sc.nextDouble();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Trade Type (BUY/SELL): ");
        String tradeType = sc.nextLine();

        String sql = "INSERT INTO Trades (TradeDate, Counterparty, Commodity, Volume, Price, TradeType) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, counterparty);
            ps.setString(3, commodity);
            ps.setDouble(4, volume);
            ps.setDouble(5, price);
            ps.setString(6, tradeType);
            ps.executeUpdate();
            System.out.println("✅ Trade added successfully.");
        }
    }

    private static void viewTrades(Connection con) throws SQLException {
        String sql = "SELECT * FROM Trades";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | Date: %s | CP: %s | Cmdty: %s | Vol: %.2f | Price: %.2f | Type: %s%n",
                        rs.getInt("TradeID"),
                        rs.getDate("TradeDate"),
                        rs.getString("Counterparty"),
                        rs.getString("Commodity"),
                        rs.getDouble("Volume"),
                        rs.getDouble("Price"),
                        rs.getString("TradeType"));
            }
        }
    }

    private static void updateTrade(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Trade ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Volume: ");
        double volume = sc.nextDouble();

        String sql = "UPDATE Trades SET Price=?, Volume=? WHERE TradeID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setDouble(2, volume);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Trade updated.");
            else System.out.println("❌ Trade not found.");
        }
    }

    private static void deleteTrade(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Trade ID to delete: ");
        int id = sc.nextInt();
        String sql = "DELETE FROM Trades WHERE TradeID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Trade deleted.");
            else System.out.println("❌ Trade not found.");
        }
    }

    private static void searchTrades(Connection con, Scanner sc) throws SQLException {
        System.out.print("Search by (1) Counterparty or (2) Commodity? ");
        int opt = sc.nextInt();
        sc.nextLine();
        String sql;
        if (opt == 1) {
            System.out.print("Enter Counterparty: ");
            String cp = sc.nextLine();
            sql = "SELECT * FROM Trades WHERE Counterparty=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cp);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("ID: %d | Date: %s | CP: %s | Cmdty: %s | Vol: %.2f | Price: %.2f | Type: %s%n",
                                rs.getInt("TradeID"), rs.getDate("TradeDate"), rs.getString("Counterparty"),
                                rs.getString("Commodity"), rs.getDouble("Volume"),
                                rs.getDouble("Price"), rs.getString("TradeType"));
                    }
                }
            }
        } else {
            System.out.print("Enter Commodity: ");
            String cmdty = sc.nextLine();
            sql = "SELECT * FROM Trades WHERE Commodity=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cmdty);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("ID: %d | Date: %s | CP: %s | Cmdty: %s | Vol: %.2f | Price: %.2f | Type: %s%n",
                                rs.getInt("TradeID"), rs.getDate("TradeDate"), rs.getString("Counterparty"),
                                rs.getString("Commodity"), rs.getDouble("Volume"),
                                rs.getDouble("Price"), rs.getString("TradeType"));
                    }
                }
            }
        }
    }
}
