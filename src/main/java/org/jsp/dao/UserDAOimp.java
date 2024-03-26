package org.jsp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

public class UserDAOimp implements UserDAO {
	final private String url="jdbc:mysql://localhost:3306/advancejavaproject?user=root&password=12345";
	final private String select="select * from bankuser_details where user_bank_email_id=? and user_password=?";
	final private String selectAccNumAndPassword = "select * from bankuser_details where user_account_number=? and user_password=?";
	final private String update="update bankuser_details set user_amount=? where user_password=?";
	final private String insertIntoStatement="insert into statement values(?,?,?,?,?,?,?,?,?)";
	final private String selectmobilenumber="select * from bankuser_details where user_mobile_number=?";
	final private String updateamount="update bankuser_details set user_amount=? where user_mobile_number=?";
	final private String selectAccNum = "select * from statement where bank_account_number=?";
	final private String updatePassword="update bankuser_details set user_password =? where user_password=?";
	
	

	Scanner scanner = new Scanner(System.in);

	@Override
	public boolean userLogin(String bankEmailId, int password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, bankEmailId);
			ps.setInt(2, password);
			ResultSet set = ps.executeQuery();
			if(set.next()) {
				return true;
			}
			else 
			{
				return false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void debit(int accountNumber, int password) {
	try {
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(selectAccNumAndPassword);
		ps.setInt(1,accountNumber);
		ps.setInt(2, password);
		ResultSet set=ps.executeQuery();
		if(set.next())
		{
			System.out.println("enter your Amount : ");
			double amount = scanner.nextDouble();
			if(amount>=0)
			{
				double databaseAmount = set.getDouble("user_amount");
				if(databaseAmount>=amount) 
				{
					double remainingAmount = databaseAmount-amount;
					PreparedStatement ps1 = connection.prepareStatement(update);
					ps1.setDouble(1, remainingAmount);
					ps1.setInt(2, password);
					int result = ps1.executeUpdate();
					if(result!=0)
					{
						PreparedStatement ps2 = connection.prepareStatement(insertIntoStatement);
						ps2.setString(1, "Debit");
						ps2.setDate(2, Date.valueOf(LocalDate.now()));
						ps2.setString(3, "Online");
						Random random = new Random();
						int transactionId=random.nextInt(10000000);
						if(transactionId<1000000) {
							transactionId+=1000000;
						}
						ps2.setInt(4, transactionId);
						ps2.setString(5, amount+"Dr");
						ps2.setInt(6, set.getInt("User_Id"));
						ps2.setInt(7, accountNumber);
						ps2.setTime(8, Time.valueOf(LocalTime.now()));
						ps2.setString(9, remainingAmount+"Cr");
						int update2 = ps2.executeUpdate();
						if(update2>0) {
							System.out.println("Amount Debited Successfully...");
						}
					}
					else
					{
						System.out.println("Error 404");
					}
				}
				else
				{
					System.out.println("insufficeint balance");
				}
				
			}
			else
			{
				System.out.println("invalid amount..");
			}
			
		}
		else
		{
			System.out.println("invalid Account detailes");
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	@Override
	public void mobileToMobileTransaction(String mobilenumber) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(selectmobilenumber);
			ps.setString(1, mobilenumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println("enter receiver mobile number");
				String rmobilenumber=scanner.next();
				PreparedStatement ps1=connection.prepareStatement(selectmobilenumber);
				ps1.setString(1, rmobilenumber);
				ResultSet rs1=ps1.executeQuery();
				if(rs1.next())
				{
					System.out.println("enter your amount");
					double userAmount=scanner.nextDouble();
					if(userAmount>=0)
					{
						double senderdatabaseamount=rs.getDouble("user_amount");
						double receiverdatabaseamount=rs1.getDouble("user_amount");
			
						if(senderdatabaseamount>userAmount)
						{
							double debit=senderdatabaseamount-userAmount;
							double credit=receiverdatabaseamount+userAmount;
							
							PreparedStatement psu=connection.prepareStatement(updateamount);
							psu.setDouble(1, debit);
							psu.setString(2,mobilenumber);
							
							PreparedStatement psu1=connection.prepareStatement(updateamount);
							psu1.setDouble(1,credit);
							psu1.setString(2,rmobilenumber);
							psu1.executeUpdate();
					
							int res=psu.executeUpdate();
							if(res!=0)
							{
								PreparedStatement pssus=connection.prepareStatement(insertIntoStatement);
								pssus.setString(1,"debit");
								pssus.setDate(2, Date.valueOf(LocalDate.now()));
								pssus.setString(3,"mobile Transaction");
								Random random=new Random();
								int transactionId=random.nextInt(10000000);
								if(transactionId<10000000)
								{
									transactionId+=10000000;
								}
								pssus.setInt(4, transactionId);
								pssus.setString(5,userAmount+"Dr");
								pssus.setInt(6,rs.getInt("user_id"));
								pssus.setInt(7,rs.getInt("user_account_number"));
								pssus.setTime(8,Time.valueOf(LocalTime.now()));
								pssus.setString(9, debit+"Rs");
								int statementResult=pssus.executeUpdate();
							
								if(statementResult!=0)
								{
									PreparedStatement psrus=connection.prepareStatement(insertIntoStatement);
									psrus.setString(1,"credit");
									psrus.setDate(2, Date.valueOf(LocalDate.now()));
									psrus.setString(3,"mobile Transaction");
									Random random1=new Random();
									int transactionId1=random1.nextInt(10000000);
									if(transactionId<10000000)
									{
										transactionId+=10000000;
									}
									psrus.setInt(4, transactionId);
									psrus.setString(5,userAmount+"Cr");
									psrus.setInt(6,rs.getInt("user_id"));
									psrus.setInt(7,rs.getInt("user_account_number"));
									psrus.setTime(8,Time.valueOf(LocalTime.now()));
									psrus.setString(9, userAmount+rs1.getDouble("user_amount")+"Rs");
									int finalResult=pssus.executeUpdate();
									if(finalResult!=0)
									{
										System.out.println("amount received successfully");
									}
								}
	
							}
							
							
						}
					}
				}
				else
				{
					System.out.println("invalid data");
				}
				
				
				
			}
			else
			{
				System.out.println("invalid user");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void credit(int accountNumber, int password) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(selectAccNumAndPassword);
			ps.setInt(1,accountNumber);
			ps.setInt(2, password);
			ResultSet set=ps.executeQuery();
			if(set.next())
			{
				System.out.println("enter amount");
				double amount=scanner.nextDouble();
				double totalAmount=set.getDouble("user_amount")+amount;
				
				PreparedStatement updateAmount = connection.prepareStatement(update);
				updateAmount.setDouble(1, totalAmount);
				updateAmount.setInt(2, password);
				int r=updateAmount.executeUpdate();
				if(r!=0)
				{
					
					PreparedStatement ps1=connection.prepareStatement(insertIntoStatement);
					ps1.setString(1,"credit");
					ps1.setDate(2, Date.valueOf(LocalDate.now()));
					ps1.setString(3,"online");
					Random random=new Random();
					int transactionId=random.nextInt(10000000);
					if(transactionId<10000000)
					{
						transactionId+=10000000;
					}
					ps1.setInt(4, transactionId);
					ps1.setString(5,amount+"Cr");
					ps1.setInt(6,set.getInt("user_id"));
					ps1.setInt(7,set.getInt("user_account_number"));
					ps1.setTime(8,Time.valueOf(LocalTime.now()));
					ps1.setString(9, totalAmount+"Cr");
					int result=ps1.executeUpdate();
					if(result>0)
					{
						System.out.println("Succesfully Credited");
					}
				}
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void balanceEnquiry(int accountNumber, int password) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(selectAccNumAndPassword);
			ps.setInt(1,accountNumber);
			ps.setInt(2, password);
			ResultSet set=ps.executeQuery();
			if(set.next())
			{
				double currentBalance=set.getDouble("user_amount");
				System.out.println("current balance : "+currentBalance);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void checkStatement(int accountNumber) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(selectAccNum);
			ps.setInt(1, accountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.isBeforeFirst())
			{
				System.out.println("status || date_of_transaction || type_of_payment ");
				while(rs.next())
				{
					System.out.println(rs.getString("status")+"  || "+rs.getDate("date_of_transaction")+"  || "+rs.getString("type_of_payment"));
				}
			
			}
			else
			{
				System.err.println("AccountNumber not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void changePassword(int accountNumber, int password) {
		
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(selectAccNumAndPassword);
			ps.setInt(1,accountNumber);
			ps.setInt(2, password);
			ResultSet set=ps.executeQuery();
			if(set.next())
			{
				Random random=new Random();
				int otp=random.nextInt(10000);
				if(otp<1000)
				{
					otp+=1000;
				}
				System.out.println("OTP : "+otp);
				System.out.println("enter abov otp");
				int inp=scanner.nextInt();
				if(inp==otp)
				{
					System.out.println("enter new Password");
					int pas=scanner.nextInt();
					System.out.println("confirm password");
					int con=scanner.nextInt();
					if(pas==con)
					{
						PreparedStatement ps1=connection.prepareStatement(updatePassword);
						ps1.setInt(inp,pas);
						ps1.executeUpdate();
					}
					else
					{
						System.err.println("Password does not match");
					}
				}
				else
				{
					System.err.println("Invalid otp...Try again");
				}
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



}
