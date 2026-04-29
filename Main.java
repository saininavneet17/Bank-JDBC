import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Bank",
                        "root",
                        "2020"
                );
                Scanner sc = new Scanner(System.in);
        ) {

            System.out.println("1. Add Customer");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit Money");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ✅ Insert Customer
                case 1:
                    String customerQuery = "INSERT INTO Customers VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps1 = con.prepareStatement(customerQuery);

                    System.out.print("Enter Customer ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();

                    ps1.setInt(1, cid);
                    ps1.setString(2, name);
                    ps1.setString(3, email);
                    ps1.setString(4, phone);
                    ps1.setString(5, address);

                    ps1.executeUpdate();
                    System.out.println("Customer added successfully!");
                    break;

                // ✅ Create Account
                case 2:
                    String accountQuery = "INSERT INTO Accounts VALUES (?, ?, ?, ?)";
                    PreparedStatement ps2 = con.prepareStatement(accountQuery);

                    System.out.print("Enter Account ID: ");
                    int accId = sc.nextInt();

                    System.out.print("Enter Customer ID: ");
                    int custId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Account Type (Savings/Current): ");
                    String type = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();

                    ps2.setInt(1, accId);
                    ps2.setInt(2, custId);
                    ps2.setString(3, type);
                    ps2.setDouble(4, balance);

                    ps2.executeUpdate();
                    System.out.println("Account created successfully!");
                    break;

                // ✅ Deposit Money
                case 3:
                    String updateBalance = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
                    String insertTxn = "INSERT INTO Transactions (transaction_id, account_id, transaction_type, amount) VALUES (?, ?, 'deposit', ?)";

                    System.out.print("Enter Account ID: ");
                    int accountId = sc.nextInt();

                    System.out.print("Enter Amount to Deposit: ");
                    double amount = sc.nextDouble();

                    // Update balance
                    PreparedStatement ps3 = con.prepareStatement(updateBalance);
                    ps3.setDouble(1, amount);
                    ps3.setInt(2, accountId);
                    ps3.executeUpdate();

                    // Insert transaction
                    PreparedStatement ps4 = con.prepareStatement(insertTxn);
                    ps4.setInt(1, (int)(Math.random() * 10000));
                    ps4.setInt(2, accountId);
                    ps4.setDouble(3, amount);
                    ps4.executeUpdate();

                    System.out.println("Deposit successful!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}