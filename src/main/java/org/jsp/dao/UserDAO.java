package org.jsp.dao;

public interface UserDAO {
	public boolean userLogin(String bankEmailId,int password);
	public void debit(int accountNumber,int password);
	public void mobileToMobileTransaction(String mobilenumber);
	public void credit(int accountNumber,int password);
	public void balanceEnquiry(int accountNumber,int password);
	public void checkStatement(int accountNumber);
	public void changePassword(int accountNumber,int password);

}
