package org.jsp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

import org.jsp.model.BankUserDetails;

public class AdminDaoImp implements AdminDao{
	final private String url="jdbc:mysql://localhost:3306/advancejavaproject?user=root&password=12345";
	final String select="select * from bank_admin where admin_emailid=? and admin_password=?";
	final String insert="insert into bankuser_details(user_name,user_bank_email_id,user_email_id,user_password,user_gender,user_address,user_date_of_birth,user_account_number,user_amount,user_mobile_number,ifsc_code) values(?,?,?,?,?,?,?,?,?,?,?)";
	final private String selectAll="select * from bankuser_details";
	final private String delete="delete from bank_user_details where User_Account_Number=?";
	@Override
	public boolean adminLogin(String emailId, String password) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(select);
			ps.setString(1,emailId);
			ps.setString(2,password);
			ResultSet set=ps.executeQuery();
			if(set.isBeforeFirst())
			{
				if(set.next())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				System.out.println("no admin found");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void userResgistration(BankUserDetails bankUserDetails)
	{
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(insert);
			ps.setString(1,bankUserDetails.getUserName());
			Random random = new Random();
			int emailIdNum=random.nextInt(100);
			if(emailIdNum<10) {
				emailIdNum+=10;
			}
			String userBankEmailId=bankUserDetails.getUserName()+emailIdNum+"@teca53.com";
			ps.setString(2, userBankEmailId);
			ps.setString(3, bankUserDetails.getUserEmailId());
			int password=random.nextInt(10000);
			if(password<1000) {
				password+=1000;
			}
			ps.setInt(4, password);
			ps.setString(5, bankUserDetails.getUserGender());
			ps.setString(6, bankUserDetails.getUserAddress());
			LocalDate userDateOfBirth=bankUserDetails.getUserDateOfBirth();
			ps.setDate(7, Date.valueOf(userDateOfBirth));
			int userAccountNumber=random.nextInt(10000000);
			if(userAccountNumber<1000000) {
				userAccountNumber+=1000000;
			}
			ps.setInt(8, userAccountNumber);
			ps.setDouble(9, bankUserDetails.getUserAmount());
			ps.setString(10, bankUserDetails.getUserMobileNumber());
			ps.setString(11, "TECA530020529");
			int result=ps.executeUpdate();
			if(result!=0) {
				System.out.println("Registration Successful...");
			}else {
				System.out.println("Provide Valid Details");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void selectingAllUserDetails() {
		try {
			Connection connection=DriverManager.getConnection(url);
			Statement statement=connection.createStatement();
			ResultSet set=statement.executeQuery(selectAll);
			if(set.isBeforeFirst())
			{
				System.out.print("Loading");
				int count=1;
				while(count<=4) {
					try {
						Thread.sleep(1000);
						System.out.print(".");
						count++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println();
				while(set.next()) {
					System.out.println("Name of the User : "+set.getString("User_Name"));
					System.out.println("Bank Email Id of the User : "+set.getString("User_Bank_Email_Id"));
//					int pw=set.getInt("User_Password"); 
//					while(count>=1) {
//						
//					}
					System.out.println("Password of  the User : "+set.getInt("User_Password"));
					System.out.println("Mobile Number of the User : "+set.getString("User_Mobile_Number"));
					System.out.println("Gender Of the User : "+set.getString("User_Gender"));
					System.out.println("Amount : "+set.getDouble("User_Amount"));
				}
			}
			else
			{
				System.out.println("not data found");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateDetails() {
		System.out.println("Updated User Details.");
		
	}
	@Override
	public void deleteUserDetails(int accountNumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(delete);
			ps.setInt(1, accountNumber);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
