package org.jsp.bank;

import java.time.LocalDate;
import java.util.Scanner;

import org.jsp.dao.AdminDao;
import org.jsp.dao.AdminHelperClass;
import org.jsp.dao.UserDAO;
import org.jsp.dao.UserHelperClass;
import org.jsp.model.BankUserDetails;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc=new Scanner(System.in);
        AdminDao adminDao=AdminHelperClass.adminHelperMethod();
        UserDAO userDao = UserHelperClass.userHelperMethod();
        System.out.println("Enter \n 1. For Admin Login \n 2. For User Login");
        
        int welcome = sc.nextInt();
        boolean status=true;
        while(status)
        {
        	switch(welcome)
        	{
	        	case 1:
	        	{
	        		int count=1;
	            	boolean check=true;
	        		System.out.println("Enter your Email-Id : ");
	            	String emailId=sc.next();
	            	System.out.println("Enter your Password : ");
	            	String password=sc.next();
	            	boolean result=adminDao.adminLogin(emailId, password);
	            	if(result)
	            	{
	            		System.out.println("Enter "
	            				+ "\n 1.For User Registration "
	            				+ "\n 2.For All USer Details "
	            				+ "\n 3.Update Details "
	            				+ "\n 4.For Delettion");
	            		int choice=sc.nextInt();
	            		switch(choice)
	            		{
		            		case 1:
			            		{
			            			BankUserDetails userDetails=new BankUserDetails();
		                			System.out.println("Enter Your Name : ");
		                			String name=sc.next();
		                			userDetails.setUserName(name);
		                			System.out.println("Enter Your Email Id : ");
		                			userDetails.setUserEmailId(sc.next());
		                			System.out.println("Enter Your Gender : ");
		                			userDetails.setUserGender(sc.next());
		                			System.out.println("Enter Your Address : ");
		                			userDetails.setUserAddress(sc.next());
		                			System.out.println("Enter Your Date Of Birth : ");
		                			String dob=sc.next();
		                			userDetails.setUserDateOfBirth(LocalDate.parse(dob));
		                			System.out.println("Enter Your Amount : ");
		                			Double amount=sc.nextDouble();
		                			userDetails.setUserAmount(amount);
		                			System.out.println("Enter Your Mobile Number : ");
		                			String mobileNum=sc.next();
		                			userDetails.setUserMobileNumber(mobileNum);
		                			adminDao.userResgistration(userDetails);
		                			
			            		}
			            		break;
		            		case 2:
		            		{
		            			adminDao.selectingAllUserDetails();
		            		}
		            		break;
		            		case 3:
		            		{
		            			adminDao.updateDetails();
		            		}
		            		break;
		            		case 4:
		            		{
		            			System.out.println("Enter Your Account Number : ");
	                			int accountNumber=sc.nextInt();
	                			adminDao.deleteUserDetails(accountNumber);
		            		}
		            		break;
		            		default:System.out.println("Enter a Valid Choice.");
		    
	            		}
	            	}
	            	else
	            	{
	            		System.out.println("Invalid Details.");
                		if(count==3) {
                			System.out.println("you have exceeded your limit");
                			check=false;
                		}
                		else 
                		{
                			count++;
                		}
	            	}
	        	}
	        	break;
	        	case 2:
	        	{
	        		System.out.println("enter your bank email id : ");
	            	String bankEmailId = sc.next();
	            	System.out.println("enter your password");
	            	int password = sc.nextInt();
	            	if(userDao.userLogin(bankEmailId, password)) 
	            	{
	            		System.out.println("Enter "
	            				+ "\n1. For Balance Enquiry "
	            				+ "\n2. For Withdraw "
	            				+ "\n3. For Credit "
	            				+ "\n4. For Change Password "
	            				+ "\n5. For check Statement"
	            				+ "\n6. For Mobile To Mobile Transaction");
	            		int choice=sc.nextInt();
	            		switch(choice)
	            		{
	            		case 1:
		            		{
		            			System.out.println("enter account number");
		            	        int accNumber = sc.nextInt();
		            	        System.out.println("enter your password");
		            	        int pass=sc.nextInt();
		            	        userDao.balanceEnquiry(accNumber, password);
		            		}
		            		break;
	            		case 2:
		            		{
		            			System.out.println("enter account number");
		            	        int accNumber = sc.nextInt();
		            	        System.out.println("enter your password");
		            	        int pass=sc.nextInt();
		            	        userDao.debit(accNumber, pass);
		            		}
		            		break;
	            		case 3:
	            		{
	            			System.out.println("enter account number");
	            			int accNumber = sc.nextInt();
	            			System.out.println("enter your password");
	            			int pass=sc.nextInt();
	            			userDao.credit(accNumber, pass);
	            		}
	            		break;
	            		case 4:
	            		{
	            			System.out.println("enter account number : ");
	            			int accnumber=sc.nextInt();
	            			userDao.checkStatement(accnumber);
	            		}
	            		break;
	            		case 6:
		            		{
		            			System.out.println("enter sender mobile number");
		            			String number=sc.next();
		            			userDao.mobileToMobileTransaction(number);
		            		}
		            		break;
	            		default:
	            			{
	            				System.out.println("Enter Valid choice");
	            			}
	            		}
	            		
	            	}
	            	else
	            	{
	            		System.out.println("Failed to Login.");
	            	}
	        	}
	        	break;
	        	default : System.out.println("Enter a Valid Choice.");
        	}
        	System.out.println("Do You Want To Continue : \n  Yes \n  No");
        	String choice=sc.next();
        	if(choice.equals("yes")) 
        	{
        		System.out.println("Enter \n 1. For Admin Login \n 2. For User Login");;
                welcome = sc.nextInt();
        		status=true;
        	}
        	else
        	{
        		System.out.println("Thank You Visit Again...!");
        		status=false;
        	}
        }
    }
}
