package org.jsp.dao;

import org.jsp.model.BankUserDetails;

public interface AdminDao {
	 public boolean adminLogin(String emailId,String password);
	 public void userResgistration(BankUserDetails bankUserDetails);
	 public void selectingAllUserDetails();
	 public void updateDetails();
	 public void deleteUserDetails(int accountNumber);


}
