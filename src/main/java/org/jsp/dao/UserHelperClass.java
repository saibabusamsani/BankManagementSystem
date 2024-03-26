package org.jsp.dao;


public class UserHelperClass {
	public static UserDAO userHelperMethod() {
		UserDAO userDao = new UserDAOimp();
		return userDao;
	}
}

