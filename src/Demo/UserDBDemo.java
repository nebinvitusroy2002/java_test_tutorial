package Demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserDBDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstSelect = null;
		PreparedStatement pstInsert = null;
		PreparedStatement pstUpdate = null;
		PreparedStatement pstDelete = null;
		ResultSet rs = null;
		int userId;
		String userName;
		int choice;
		System.out.println("1.View\n2.Insert\n3.Update\n4.Delete");
		System.out.println("Enter your choice: ");
		choice = sc.nextInt();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flycatch", "root", "Nebin@2002");
		} catch (SQLException e) {
			e.getMessage();
		}

		switch (choice) {
		case 1:
			try {
				System.out.println("The DB table: ");
				pstSelect = conn.prepareStatement("Select * from employee");
				rs = pstSelect.executeQuery();
			} catch (SQLException e) {
				e.getMessage();
			}
			break;
		case 2:
			try {
				System.out.println("Enter the user_id and user_name: ");
				userId = sc.nextInt();
				sc.nextLine();
				userName = sc.nextLine();
				pstInsert = conn.prepareStatement("Insert into employee(user_id,user_name)values(?,?)");
				pstInsert.setInt(1, userId);
				pstInsert.setString(2, userName);
				pstInsert.executeUpdate();
				pstSelect = conn.prepareStatement("Select * from employee");
				rs = pstSelect.executeQuery();
			} catch (SQLException e) {
				e.getMessage();
			}
			break;
		case 3:
			try {
				System.out.println("Enter the user_id to update the new user_name: ");
				userId = sc.nextInt();
				sc.nextLine();
				userName = sc.nextLine();
				pstUpdate = conn.prepareStatement("Update employee set user_name = ? where user_id = ?");
				pstUpdate.setString(1, userName);
				pstUpdate.setInt(2, userId);
				pstUpdate.executeUpdate();
				pstSelect = conn.prepareStatement("Select * from employee");
				rs = pstSelect.executeQuery();
			} catch (SQLException e) {
				e.getMessage();
			}
			break;
		case 4:
			try {
				System.out.println("Enter the user_id to delete the details of the employee: ");
				userId = sc.nextInt();
				pstDelete = conn.prepareStatement("delete from employee where user_id = ?");
				pstDelete.setInt(1, userId);
				pstDelete.executeUpdate();
				pstSelect = conn.prepareStatement("Select * from employee");
				rs = pstSelect.executeQuery();
			} catch (SQLException e) {
				e.getMessage();
			}
			break;
		default:
			try {
				System.out.println("Wrong Input...");
			} catch (Exception e) {
				e.getMessage();
			}
		}
		try {
			if (rs != null) {
				while (rs.next()) {
					System.out.println(rs.getInt(1) + " " + rs.getString(2));
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstSelect != null)
					pstSelect.close();
				if (pstInsert != null)
					pstInsert.close();
				if (pstUpdate != null)
					pstUpdate.close();
				if (pstDelete != null)
					pstDelete.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
			sc.close();
		}
	}

}
