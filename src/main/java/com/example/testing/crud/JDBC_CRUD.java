package com.example.testing.crud;

import java.sql.*;
import java.util.Scanner;

public class JDBC_CRUD {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/crudtesting";
		String user = "root";
		String password = "Admin@123";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Optional

			Connection dbConnection = DriverManager.getConnection(url, user, password);
			Scanner scanner = new Scanner(System.in);

			System.out.println("----- JDBC CRUD OPERATIONS -----");
			System.out.println("1. Fetch Data");
			System.out.println("2. Insert Data");
			System.out.println("3. Update Data");
			System.out.println("4. Delete Data");
			System.out.println("5. Alter Table");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			String Query;

			switch (choice) {

			// FETCH DATA
			case 1:
				Statement dbStatement = dbConnection.createStatement();
				Query = "SELECT * FROM datas";
				ResultSet dbFetch = dbStatement.executeQuery(Query);

				while (dbFetch.next()) {
					System.out.println(dbFetch.getInt(1) + " : " + dbFetch.getString(2) + " : " + dbFetch.getString(3));
				}

				dbFetch.close();
				dbStatement.close();
				break;

			// INSERT DATA
			case 2:
				System.out.print("Enter Id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				System.out.print("Enter Name: ");
				String name = scanner.nextLine();

				System.out.print("Enter Email: ");
				String email = scanner.nextLine();

				Query = "INSERT INTO datas(id, name, email) VALUES (?, ?, ?)";
				PreparedStatement dbInsert = dbConnection.prepareStatement(Query);
				dbInsert.setInt(1, id);
				dbInsert.setString(2, name);
				dbInsert.setString(3, email);

				int rowInsert = dbInsert.executeUpdate();
				System.out.println(rowInsert > 0 ? "Data Inserted" : "Insert Failed");

				dbInsert.close();
				break;

			// UPDATE DATA
			case 3:
				System.out.print("Enter Id: ");
				id = scanner.nextInt();
				scanner.nextLine();

				System.out.print("Enter New Email: ");
				email = scanner.nextLine();

				Query = "UPDATE datas SET email=? WHERE id=?";
				PreparedStatement dbUpdate = dbConnection.prepareStatement(Query);
				dbUpdate.setString(1, email);
				dbUpdate.setInt(2, id);

				int rowUpdate = dbUpdate.executeUpdate();
				System.out.println(rowUpdate > 0 ? "Data Updated" : "Update Failed");

				dbUpdate.close();
				break;

			// DELETE DATA
			case 4:
				System.out.print("Enter Id: ");
				id = scanner.nextInt();

				Query = "DELETE FROM datas WHERE id=?";
				PreparedStatement dbDelete = dbConnection.prepareStatement(Query);
				dbDelete.setInt(1, id);

				int rowDelete = dbDelete.executeUpdate();
				System.out.println(rowDelete > 0 ? "Data Deleted" : "Delete Failed");

				dbDelete.close();
				break;

			// ALTER TABLE
			case 5:
				Query = "ALTER TABLE datas ADD phone VARCHAR(15)";
				PreparedStatement dbAlter = dbConnection.prepareStatement(Query);
				dbAlter.executeUpdate();

				System.out.println("Column Added Successfully");

				dbAlter.close();
				break;

			default:
				System.out.println("Invalid Choice");
			}

			dbConnection.close();
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}