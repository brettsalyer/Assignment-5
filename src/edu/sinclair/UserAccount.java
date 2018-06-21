package edu.sinclair;

import java.io.Serializable;

public abstract class UserAccount implements Serializable {

	private static final long serialVersionUID = 6008396624964832583L;
	private String username;
	private String password;
	private boolean active;
	
	UserAccount(String username, String password){
		this.username = username;
		this.password = password;
		this.active = true;
		
	}
	
	public abstract String getPasswordHelp();
	

	public boolean isPasswordCorrect(String password) {
		if (this.password == password) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setActive(boolean status){
		this.active = status;
	}
	
	public boolean isActive() {
		return this.active;
	}
	

	//Username is the unique indentifier. 
	@Override
	public int hashCode(){
		return this.username.hashCode();
	}
	
	//this probably isnt right
	@Override
	public String toString(){
		return "Username: " + this.username;
	}
	//compare usernames?
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof UserAccount)) {
			return false;
		}else {
			UserAccount comparedUser = (UserAccount) o;
			return this.username.equals(comparedUser.username);
		}
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	
}
