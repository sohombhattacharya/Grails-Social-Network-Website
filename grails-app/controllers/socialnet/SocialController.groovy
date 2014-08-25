package socialnet
import socialPackage.SocialDatabase;
import socialPackage.SocialDatabase.FriendRequests;
import socialPackage.SocialDatabase.FriendUpdates;

class SocialController {

	SocialDatabase db;
	
	int appCounter = 0;
	def index() {
		
		if (appCounter == 0){
			db = new SocialDatabase();
		}
		appCounter++;
	}
	
	def login(){
		
		String userName = String.valueOf(params.userNM)
		String passWord = String.valueOf(params.passWD)
		
		boolean accountCheck = db.checkAccount(userName, passWord);
		if (accountCheck == true){
			String aID = db.getAccountID(userName);
			String nickName = db.getNickName(aID);
			
			
			redirect(action: "account", params: [userID: aID, nickName: nickName])
		}
		else
			flash.error = "Login unsuccessful, please try again"
		
	}
	
	def createAccount(){
		String userName = String.valueOf(params.userNm);
		String passWord = String.valueOf(params.passWd);
		String nickName = String.valueOf(params.nickname);
		
		if (db.createAccountCheck(userName) == false){
			boolean createAccount = db.createAccount(userName, passWord, nickName);
			
			if (createAccount == true)
				redirect(action: "account", params: [userID: db.getAccountID(userName), nickName: nickName])
			else
				flash.error = "Error please try again"
		}
		else
			flash.error = "User name already exists, please choose another. "
			
	}
	def account(){
		String aID = params.userID;
		String nickName = params.nickName;
		List<FriendRequests> requestsList = db.checkFriendRequests(db.getUserName(aID));
		String[] friendsWhoRequested = new String[requestsList.size()];
		int i = 0;
		
		for (FriendRequests e: requestsList){
			friendsWhoRequested[i] = db.getUserName(e.aID);
			i++;
		}
		
		
		//status updates stuff
		List<FriendUpdates> friendsAndUpdates = db.getStatusUpdates(aID);
		List<FriendUpdates> friendAndUpdatesLatest = new ArrayList<FriendUpdates>();

		for (i = friendsAndUpdates.size()-1; i >= 0; i--){
			friendAndUpdatesLatest.add(friendsAndUpdates[i]);
		}
		
		
		
		return [accountID: aID, nickName: nickName, responseMessage: params.errorMessage, friendsWhoRequested: friendsWhoRequested, statusUpdates: friendsAndUpdates]
		
	}
	
	def addStatus(){
		
		String status = String.valueOf(params.status);
		String aID = String.valueOf(params.accountID);
		String nickName = String.valueOf(db.getNickName(aID));
		boolean test = db.addStatus(status, aID);
		
		if (test == true)
			redirect(action:"account", params: [userID: aID, nickName: nickName])
		else
			flash.error = "Error posting status, please try again."
		
		
	}
	
	def getFriends(){
		
		String aID = String.valueOf(params.accountID);
		List<String> friendsIDList = db.getFriends(aID);
		String[] friendStrs = new String[friendsIDList.size()];
		
		int i = 0;
		for (String e: friendsIDList){
			friendStrs[i] = db.getUserName(e);
			print db.getUserName(e);
			i++;
		}
		
		return [friends: friendStrs]
			
	}
	
	def addFriend(){
		
		String aID = String.valueOf(params.accountID);
		String friendName = String.valueOf(params.friendNM);
		String nickName = String.valueOf(params.nickName);
		String friendID = db.getAccountID(friendName);
		
		if (db.ifAccountExists(friendName) == true){
			boolean test = db.addFriend(aID, friendName);
			if (test == true){
				redirect(action:"account", params: [userID: aID, nickName: nickName, errorMessage: "Friend Request Sent!"])
			}
			
		}
		
		else{
			redirect(action:"account", params: [userID: aID, nickName: nickName, errorMessage: "Looks like that person doesn't exist!"])
		}
		
	}
	
	def accept(){
		
		String aID = String.valueOf(params.accountID);
		String aUserName = db.getUserName(aID);
		String friend = String.valueOf(params.friend);	
		String friendID = db.getAccountID(friend);
		String nickName = String.valueOf(params.nickName);
		
		boolean test = db.acceptFriend(aID, aUserName, friend, friendID);
		
		if (test == true){
				redirect(action:"account", params: [userID: aID, nickName: nickName])
			}
			
		else{
			redirect(action:"account", params: [userID: aID, nickName: nickName])
		}
		
		
	}
	
	def deny(){
		
		String aID = String.valueOf(params.accountID);
		String aUserName = db.getUserName(aID);
		String friend = String.valueOf(params.friend);
		String friendID = db.getAccountID(friend);
		String nickName = String.valueOf(params.nickName);
		
		boolean test = db.denyFriend(aUserName, friendID);
		
		if (test == true){
				redirect(action:"account", params: [userID: aID, nickName: nickName])
			}
			
		else{
			redirect(action:"account", params: [userID: aID, nickName: nickName])
		}
		
	}
	
	
}
