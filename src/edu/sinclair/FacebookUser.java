package edu.sinclair;

import java.util.ArrayList;
import java.util.List;

public class FacebookUser extends UserAccount implements Comparable<FacebookUser> {

	private static final long serialVersionUID = -1339454390531323515L;
	private String passwordHint;
	private List<FacebookUser> friends = new ArrayList<>();

	FacebookUser(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public void setPasswordHint(String hintText) {
		this.passwordHint = hintText;
	}
	
	public void addFriend(FacebookUser friend) throws FacebookException {
		if(friends.contains(friend)) {
			throw new FacebookException("You are already friends with this user");
		}else {
			friends.add(friend);
		}	
	}

	public void removeFriend(FacebookUser friend) throws FacebookException {
		if (friends.contains(friend)) {
			friends.remove(friend);
		}else {
			throw new FacebookException("You are NOT friends with this user");
		}
	}
	
	public List<FacebookUser> getFriends(){
		List<FacebookUser> copy = new ArrayList<>(friends);
		return copy;
	}

	@Override
	public String getPasswordHelp() {
		// TODO Auto-generated method stub
		return this.passwordHint;
	}
	
	@Override
	public int compareTo(FacebookUser user) {
		if (user == null) {
			return -1;
		}
		if (this.getUsername().compareToIgnoreCase(user.getUsername()) != 0){
			return this.getUsername().compareToIgnoreCase(user.getUsername());
		}
		
		return 0;
	}

}
