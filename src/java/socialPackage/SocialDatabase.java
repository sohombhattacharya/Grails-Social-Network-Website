package socialPackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SocialDatabase {
	Connection con;
	
	class FriendRequests{
		
		String aID;
		String friend;
		
		FriendRequests(){}
	}
	
	class FriendUpdates{
		
		String friendNM;
		String status;
		
		FriendUpdates(){}
	}
	
	
	SocialDatabase(){
		try {
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//con = DriverManager.getConnection("jdbc:sqlserver://104.38.19.191:53544;databaseName=SocialNetDB","sa","juee16east");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:login_dsm");
		
		}
	
		catch (Exception e){
		//think of something to figure out what exception it is and return somthing to 
		//front-end so that user knows what to do 
			System.out.println(e);

		}
	}
	public void runQuery(String command){
		try{
			Statement st = con.createStatement();
			st.executeUpdate(command);
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			}
	}
	
	public boolean checkAccount(String u, String p){
		
		boolean result = false; 
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from UserAccounts where UserNM='"+u+"' and PassWD='"+p+"'");//add where clause
			while (rs.next()){
				String userNM = rs.getString(1);
				String passWD = rs.getString(2);
				if (u.equals(userNM) && p.equals(passWD)){
					System.out.println("match");
					result = true; 
				}
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		return result;
		
	}
	
	public boolean createAccountCheck(String userName){
		
		boolean result = false; 
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from UserAccounts where UserNM='"+userName+"'");//add where clause
			while (rs.next()){
				String userNM = rs.getString(1);
				if (userName.equals(userNM)){
					System.out.println("match");
					result = true; 
				}
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		return result;
		
		
	}
	
	public boolean createAccount(String u, String p, String n){
		boolean result = false; 
		try{
			Statement st = con.createStatement();
			st.executeUpdate("insert UserAccounts values ('"+u+"', '"+p+"', '"+n+"')");
			String currentID = getAccountID(u);
			st.executeUpdate("insert Friends values ("+currentID+", "+currentID+")");
			//st.executeUpdate("insert UserOnline values ("");
			result = true; 
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			

			}
		return result;
		
	}
	
	public String getAccountID(String u){
		String aID = "";
		
		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from UserAccounts where UserNM='"+u+"'");
			while (ras.next()){
				String testId = ras.getString(4);
				aID = testId;
				System.out.println("test");
				break;
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		
		return aID;
	}
	
	public String getNickName(String aID){
		
		String nickName = "";
		
		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from UserAccounts where AccountID="+aID);
			while (ras.next()){
				String testN = ras.getString(3);
				nickName = testN;
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		
		return nickName;
	}
	
	public boolean addStatus(String status, String aID){
		boolean result = true;
		System.out.println("now in addStatus");
		List<Character> statusChar = new ArrayList<Character>();
		
		for (int i = 0; i < status.length(); i++){
			statusChar.add(status.charAt(i));
			
			if (status.charAt(i) == '\''){
				statusChar.add('\'');
			}
		}
		
		StringBuilder inputStatus = new StringBuilder(statusChar.size());
		for (Character c: statusChar){
			inputStatus.append(c);
		}
		System.out.println(inputStatus);
		try{
			
			Statement st = con.createStatement();
			st.executeUpdate("insert StatusUpdates values ("+aID+", '"+inputStatus+"', GETDATE())");
			result = true; 
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			result = false;

			}
		
		System.out.println("now out of addStatus");

		return result;
	}
	
	public List<String> getFriends(String aID){
		
		List<String> friendsList = new ArrayList<String>();
		System.out.println("now in getFriends");

		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from Friends where AccountID="+aID);
			while (ras.next()){
				String name = new String();
				name = ras.getString(2);
				friendsList.add(name);
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return something to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		
		
		System.out.println("now out of getFriends");

		return friendsList;
	}
	
	public boolean addFriend(String aID, String friend){
		
		boolean result = true;
		System.out.println("now in addFriend");
		try{
			Statement st = con.createStatement();
			String command = "If Not Exists(select * from FriendRequests where AccountID="+aID+" and RequestNM='"+friend+"') Begin insert FriendRequests values ("+aID+", '"+friend+"') End";
			st.executeUpdate("If Not Exists(select * from FriendRequests where AccountID="+aID+" and RequestNM='"+friend+"') Begin insert FriendRequests values ("+aID+", '"+friend+"') End");
			System.out.println(command);
			result = true; 
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			result = false;

			}
		System.out.println("now out of addFriend");

		return result;
	}
	
	public boolean ifAccountExists(String u){
		
		boolean result = true;
		System.out.println("now in ifAccountExists");

		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from UserAccounts where UserNM='"+u+"'");
			
			if (!ras.next()) 
				result = false;
			
			while(ras.next()){}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return something to 
			//front-end so that user knows what to do 
			System.out.println(e);
			result = false;
			}
		System.out.println("now out of checkFriendRequests");

		return result;
		
		
		
	}
	
	public List<FriendRequests> checkFriendRequests(String u){
		
		System.out.println("now in checkFriendRequests");
		List<FriendRequests> requestsList = new ArrayList<FriendRequests>();
		

		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from FriendRequests where RequestNM='"+u+"'");
			
				while(ras.next()){
				
					FriendRequests a = new FriendRequests();
					a.aID = ras.getString(1);
					a.friend = ras.getString(2);
					requestsList.add(a);
				
				}
			
			
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return something to 
			//front-end so that user knows what to do 
			System.out.println(e);
			requestsList = null;
		}
		System.out.println("now out of checkFriendRequests");

		return requestsList;
		
		
	}
	
	public String getUserName(String aID){
		
		String userName = "";
		System.out.println("now in getUserName");
		try{
			Statement st = con.createStatement();
			ResultSet ras = st.executeQuery("select * from UserAccounts where AccountID="+aID);
			while (ras.next()){
				String testN = ras.getString(1);
				userName = testN;
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		System.out.println("out of getUserName");
		return userName;
		
	}
	
	public boolean acceptFriend(String currentID, String currentUserName, String friend, String friendID){
		
		boolean test = true; 
		System.out.println("now in acceptFriend");
		try{
			Statement st = con.createStatement();
			st.executeUpdate("insert Friends values ("+currentID+", '"+friendID+"')");
			st.executeUpdate("insert Friends values ("+friendID+", '"+currentID+"')");
			String command = "delete from FriendRequests where AccountID="+friendID+" and RequestNM='"+currentUserName+"'";
			System.out.println(command);
			st.executeUpdate("delete from FriendRequests where AccountID="+friendID+" and RequestNM='"+currentUserName+"'");
			}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			test = false; 
			}
		
		System.out.println("now out of acceptFriend");

		return test;
	}
	
	public List<FriendUpdates> getStatusUpdates(String aID){
		
		List<FriendUpdates> friendsAndUpdates = new ArrayList<FriendUpdates>();
		String currentUserName = getUserName(aID);
		//use resultset to get all the status updates 
		System.out.println("now in getStatusUpdates");

		try{
			Statement st = con.createStatement( ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet ras = st.executeQuery("select u1.UserNM currentUser, u.UserNM friendName, su.StatusUpdate friendUpdate from StatusUpdates su join Friends f on f.FriendID=su.AccountID join UserAccounts u on u.AccountID = f.FriendID join UserAccounts u1 on u1.AccountID = f.AccountID where u1.UserNM='"+currentUserName+"' order by su.UpdateTM DESC");
		
			while (ras.next()){
				FriendUpdates a = new FriendUpdates();
				a.friendNM = ras.getString(2);
				a.status = ras.getString(3);
				System.out.println("friend: "+ a.friendNM+ " status: "+a.status);
				friendsAndUpdates.add(a);
			}
		}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);

			}
		System.out.println("now out of getStatusUpdates");

		return friendsAndUpdates;
		
		
	}
	
	public boolean denyFriend(String currentUserName, String friendID){
		
		boolean test = true; 
		System.out.println("now in denyFriend");
		try{
			Statement st = con.createStatement();
			st.executeUpdate("delete from FriendRequests where AccountID="+friendID+" and RequestNM='"+currentUserName+"'");
			}
		catch (Exception e){
			
			//think of something to figure out what exception it is and return somthing to 
			//front-end so that user knows what to do 
			System.out.println(e);
			test = false; 
			}
		
		System.out.println("now out of denyFriend");

		return test;
	}
}

