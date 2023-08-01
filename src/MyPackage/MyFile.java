package MyPackage;

import java.util.*;
import java.sql.*;
abstract class Order{
	abstract public void insert(Connection con)throws Exception;
	
}
interface MyInterface{
	public void findprice(Connection con) throws Exception;
}
class Create extends Order implements MyInterface{
	private String name;
	private String email;
	private String pwd;
	private int price;
	public int getPrice(){
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	Create(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void insert(Connection con) throws Exception {
	String q2="insert into login values(?,?,?)";
	PreparedStatement st=con.prepareStatement(q2);
	st.setString(1, name);
	st.setString(2, email);
	st.setString(3, pwd);
	st.executeUpdate();
	}
	
	public void findprice(Connection con)throws Exception{
		String q="Select sum(price) from cart";
		PreparedStatement st=con.prepareStatement(q);
		ResultSet rs=st.executeQuery();
		while(rs.next()) {
			System.out.print(rs.getInt(1));
		}
		
	}
	public void findprice(Connection con,String name)throws Exception {
		String q="select price from cart where name=?";
		PreparedStatement st=con.prepareStatement(q);
		st.setString(1, name);
		ResultSet rs=st.executeQuery();
		while(rs.next()) {
			System.out.print(rs.getInt(1));
		}
		
		
	}
	
	
	
	
}
class MyFile {
	

	public static void main(String[] args) throws Exception {
		 
		Scanner sc=new Scanner(System.in);
		System.out.println("============================");
		System.out.println("E-COMMERCE ORDER MANAGEMENT");
		System.out.println("============================");
		System.out.println("\n\n");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.print("Enter email");
		
		String email=sc.nextLine();
		System.out.print("Enter Password");
		String pwd=sc.nextLine();
	    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/capp","root","biot");
	    String q="select * from login";
	    int flg=0;
	    PreparedStatement stt=con.prepareStatement(q);
	    ResultSet rss=stt.executeQuery();
	    while(rss.next()) {
	    	if((rss.getString(2).equals(email))&&(rss.getString(3).equals(pwd))) {
	    		System.out.print("\n  Authenticated :)");
	    		flg=1;
	    	}
	    }
	    if(flg==0) {
	    	System.out.println("\nInvalid credentials");
	    	return;
	    }
	    

	    
		int fg=1;
		while(fg==1){
		System.out.println("\n==================================================================================================================================================");
		System.out.println("1-View order history \n2-place order \n3-track order status \n4-cancel order \n5-request exchange \n6-customer registration \n7-customer analysis \n8-check price");
		System.out.println("==================================================================================================================================================");
		int choice=sc.nextInt();
		sc.nextLine();
		if(choice==1) {
			String q4="select * from orders";
			PreparedStatement st4=con.prepareStatement(q4);
			
			ResultSet rs4=st4.executeQuery();
			
			 System.out.println("Product"+"\t\t\t"+"payment_method"+"  "+"Status");
			 System.out.println("----------------------------------------------");
			 
			while(rs4.next()) {
				if(rs4.getString(1).equals(email))
				System.out.println(rs4.getString(2)+"\t\t"+rs4.getString(3)+"\t\t"+rs4.getString(4));
			}
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
			}
		}
		else if(choice==2) {
		
		    
		    System.out.println("Product"+"\t\t\t"+"price"+"\t\t"+"Availability");
		    System.out.println("----------------------------------------------");
		    String q1="select * from cart where email=?";
		    PreparedStatement st1=con.prepareStatement(q1);
		    st1.setString(1, email);
		    ResultSet rs1=st1.executeQuery();
		    while(rs1.next()) {
		    	System.out.println(rs1.getString(1)+"\t\t"+rs1.getInt(2)+"\t\t"+rs1.getString(3));
		    }
			System.out.println("\n\nWhat do you wanna buy?");
			String pname=sc.nextLine();
			System.out.println("\nChoose Payment method");
			String payment=sc.nextLine();
			String q2="insert into orders(email,product,payment_method) values(?,?,?)";
			PreparedStatement st2=con.prepareStatement(q2);
			st2.setString(1, email);
			st2.setString(2, pname);
			st2.setString(3,payment);
			st2.executeUpdate();
			System.out.println("============================");
			System.out.println("\n order placed :)");
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
	
		}
		else if(choice==3) {
			System.out.println("\nTrack order status here");
			System.out.print("Product_name: ");
			String nm=sc.nextLine();
			String q4="select * from orders";
			PreparedStatement st4=con.prepareStatement(q4);
			
			ResultSet rs4=st4.executeQuery();
			System.out.println("\nName\t\t\tStatus");
			System.out.println("------------------");
			while(rs4.next()) {
				if(rs4.getString(1).equals(email)&&(rs4.getString(2).equals(nm))) {
				System.out.println(rs4.getString(2)+"\t\t"+rs4.getString(4));
				}
			}
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
			
			
		}
		else if(choice==4) {
			System.out.println("\n Cancel order here");
			System.out.println("Product_name");
			String pn=sc.nextLine();
			String q1="delete from orders where product=?";
			
			PreparedStatement st=con.prepareStatement(q1);
			st.setString(1, pn);
			st.executeUpdate();
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
		}
		else if(choice==5) {
			System.out.println("\n request exchange here");
			System.out.println("Product_name");
			String pn=sc.nextLine();
			String q1="update orders set status=\"exchange\" where product=?" ;
			
			
			PreparedStatement st=con.prepareStatement(q1);
			st.setString(1, pn);
			st.executeUpdate();
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
			
		}
		else if( choice==6) {
			System.out.print("Enter name");
			String name1=sc.nextLine();
			System.out.print("Enter email");
			String email1=sc.nextLine();
			System.out.print("Enter Password");
			String pwd1=sc.nextLine();
			Create cr=new Create();
			cr.setName(name1);
			cr.setEmail(email1);
			cr.setPwd(pwd1);
			cr.insert(con);
			
			
			
			
//			String q2="insert into login values(?,?,?)";
//			PreparedStatement st=con.prepareStatement(q2);
//			st.setString(1, name1);
//			st.setString(2, email1);
//			st.setString(3, pwd1);
//			st.executeUpdate();
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
			
			
			
		}
		else if(choice==7) {
			System.out.println("Customers with no order history:  \n");
			String q1="select * from login where email not in(select email from orders)";
			Statement st1=con.createStatement();
			ResultSet rs=st1.executeQuery(q1);
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			String q2="select name,count(o.email) from login l  join orders o on l.email=o.email order by count(o.email) desc limit 1";
			Statement st2=con.createStatement();
			ResultSet rs1=st2.executeQuery(q2);
			System.out.println("\nMost Frequent Customer:  \n");
			while(rs1.next()) {
				System.out.println(rs1.getString(1));
			}
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
		
		}
		else if(choice==8) {
			System.out.print("Total Price:");
			Create cr=new Create();
			cr.findprice(con);
			System.out.print("\n\nIndividual price \nproduct : ");
			String name=sc.nextLine();
			System.out.print("\nprice:\n");
			cr.findprice(con,name);
			System.out.println("\nDo you want to continue Y/N");
			char c=sc.nextLine().charAt(0);
			if(c=='Y') {
				continue;
			}
			else {
				fg=0;
				
			}
		}
		
		}
		con.close();
		
		
		
		
		
	
	}

}