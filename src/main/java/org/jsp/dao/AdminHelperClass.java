package org.jsp.dao;

public class AdminHelperClass {
	public static AdminDao adminHelperMethod()
	{
		AdminDao adminDao=(AdminDao) new AdminDaoImp();
		return adminDao;
	}

}
