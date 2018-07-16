package edu.sinclair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class Facebook implements Comparator<FacebookUser> {
	private List<FacebookUser> users;
	HashSet<FacebookUser> friends = new HashSet<>();
	HashSet<FacebookUser> recommendations = new HashSet<>();
	
	private static final String PATH = System.getProperty("user.home") + File.separator + "sinclair" + File.separator;
	private static final String FILE_NAME = "FacebookUsers.dat";
	
	Scanner sc = new Scanner(System.in);
	FacebookUser fbuser;
	
	public static void main(String args[]) throws FileNotFoundException, ClassNotFoundException, FacebookException {
		boolean shouldContinue = true;
		Scanner sc = new Scanner(System.in);
		Facebook fb = new Facebook();
		String user;

		fb.load();
		
		do {
			File file = new File(PATH + FILE_NAME);
			
			
			System.out.println("Menu");
			System.out.println("1: List Users Alphabetically,");
			System.out.println("2: List Users by Number of Friends,");
			System.out.println("3: Add User,");
			System.out.println("4: Delete User,");
			System.out.println("5: Get Password Hint,");
			System.out.println("6 List Friends,");
			System.out.println("7: Add friend,");
			System.out.println("8: Remove friend,");
			System.out.println("9: Recommend friend,");
			System.out.println("10: Like,");
			System.out.println("11: List likes,");
			System.out.println("12: Quit");
			
			int menuSelection = sc.nextInt();
			
			if(menuSelection < 1 || menuSelection > 12) {
				System.out.println("Invalid selection");
			}else {
			switch(menuSelection) {
			
			case 1: 
				fb.listUsersAZ();
				break;
			
			case 2: 
				fb.listUsersByFriends();
				break;
				
			case 3:
				fb.addUser();

				break;
			
			case 4: 
				fb.deleteUser();

				break;
			
			case 5:
				sc.nextLine();
				System.out.println("Enter a username: ");
				user = sc.nextLine();
				fb.displayPasswordHint(user);
				break;
				
			case 6:
				fb.listFriends(sc);

				break;
			
			case 7:
				fb.addFriend(sc);	

				break;
				
			case 8:
				fb.deleteFriend(sc);

				break;
				
			case 9: 
				FacebookUser recieving = fb.getValidUser(sc);
				
				if(fb.users.contains(recieving)) {
				fb.recommendations.addAll(fb.getRecommendations(recieving));
					
				List<FacebookUser> temp = new ArrayList<>(fb.recommendations);
				Collections.sort(temp, new UserCompareByFriends());
				
				for (FacebookUser friend : temp) {
					System.out.println(friend.toString());
				}
				}else {
					System.out.println("User does not exist");
				}
				break;
				
			case 10:
				fb.like(sc);
			
				break;
			case 11:
				fb.listLikes(sc);
				break;
			case 12:
				if(file.exists()) {
					file.delete();
				}
				System.out.println("Goodbye!");
				shouldContinue = false;
				sc.close();
				fb.save();
				break;

			}
		}
			
				
		}
		while(shouldContinue);
				
	}
	
	public Facebook(){
		users = new ArrayList<>();
	}
	
	public void addUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("User to add: ");
		String user = sc.nextLine();
		FacebookUser found = findUser(user);
		
		if(users.contains(found)) {
			System.out.println("User already exists!");
		}else {
			System.out.println("Please enter a password for this user: ");
			String password = sc.nextLine();
			FacebookUser newUser = new FacebookUser(user, password);
			System.out.println("Enter a password hint: ");
			String hint = sc.nextLine();
			newUser.setPasswordHint(hint);
			users.add(newUser);
		}
	}
	
	public void listUsersAZ() {
		//Sort the list of users alphabetically
		Collections.sort(this.users, new Facebook());
		for(int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
		}
	}
	
	public void listUsersByFriends() {
		Collections.sort(this.users, new UserCompareByFriends());
		for(FacebookUser user : this.users) {
			System.out.println(user.getUsername());
		}
	}
	
	public void deleteUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("User to delete: ");
		String user = sc.nextLine();
		
		FacebookUser found = findUser(user);
		
		if(users.contains(found)) {
			System.out.println("Please enter the password for this user: ");
			String password = sc.nextLine();
			if(found.getPassword().equals(password)) {
				users.remove(found);
			}else {
				System.out.println("**Incorrect**");
			}
		}else {
			System.out.println("User does NOT exists!");
		}
	}
	
	public void displayPasswordHint(String user) {
		List<String> stringOfUsers = new ArrayList<>(); //New arrayList for holding objects converted to strings
		//Conversion process
		for(int i = 0; i < users.size(); i++) {
			stringOfUsers.add(users.get(i).getUsername());
		}
		
		//Determine if this user already exists
		if(stringOfUsers.contains(user)) {
			//If it does exist, loop through users to find the correct username and delete it
			for(FacebookUser person : users ) {
				if(person.getUsername().equals(user)){
					System.out.println(person.getPasswordHelp());
				}
			}
		}else {
			//Do nothing
		}
	}

	public void load() throws FileNotFoundException, ClassNotFoundException {
		File file = new File(PATH + FILE_NAME);
		File path = new File(PATH);

		Facebook fb = null;
		
		if(!path.exists()) {
			System.out.println("Path does not exists. Creating...");
			new File(PATH).mkdirs();
		}
		
		if(file.exists()) {	
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(PATH + FILE_NAME));){
				this.users = (List<FacebookUser>) input.readObject();	
				this.friends = (HashSet<FacebookUser>) input.readObject();
			}catch (IOException e){
				e.printStackTrace();
			}
		}else {
			this.equals(fb);
		}
		file.delete();
	}
	
	public void save() {
		File file = new File(PATH);
		if(file.exists()) {
		
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(PATH + FILE_NAME));){
			output.writeObject(this.users);
			output.writeObject(this.friends);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		}else {
			System.out.println("Directory does not exist");
		}
	}
	private FacebookUser findUser(String username) {
		 final FacebookUser user;

		 // indexOf returns the index within the ArrayList that contains the supplied FacebookUser
		 // or -1 if not found.
		 int idx = users.indexOf(new FacebookUser(username, ""));

		 if (idx < 0) { 
		  user = null;
		 } else {
		  user = users.get(idx);
		 }

		 return user;
		}
	
	private FacebookUser getValidUser(Scanner sc) {	
		sc.nextLine();
		System.out.println("Username: ");
		String username = sc.nextLine();
		FacebookUser nullUser = null;
		
		FacebookUser user = findUser(username);
		
		if(!this.users.contains(user)) {
			System.out.println("User does not exist!");
			return nullUser;
		}else {
			System.out.println("Password: ");
			String password = sc.nextLine();
			if(user.getPassword().equals(password)) {
				return user;
			}else {
				System.out.println("Incorrect Password");
				return nullUser;
				
			}
		}
		
	}
	
	public void listFriends(Scanner sc){
		FacebookUser user = getValidUser(sc);
		if(user == null) {
			//Do nothing
		}else {
		if(user.getFriends().isEmpty()) {
			System.out.println("You have no friends, what a loser! lol XD ");
		}else {
			for(FacebookUser friend : user.getFriends()) {
				System.out.println(friend.getUsername());
			}
		}	
		}
	}
	
	public void addFriend(Scanner sc) throws FacebookException {
		
		FacebookUser user = getValidUser(sc);
		
		if(user == null) {
			//Do nothing
		}else {
			System.out.println("Enter a username to add: ");
			String username = sc.nextLine();
			FacebookUser friend = findUser(username);
		if(users.contains(friend)) {
			if(user.getFriends().contains(friend)) {
				System.out.println("This person is already your friend");
			}else {
				user.addFriend(friend);
			}
		}else {
			System.out.println("This user does not exist");
		}
		}
	}
	
	public void deleteFriend(Scanner sc) throws FacebookException {
		FacebookUser user = getValidUser(sc);
		
		if(user == null) {
			//Do nothing
		}else {
		System.out.println("Enter a username to delete: ");
		String username = sc.nextLine();
		FacebookUser friend = findUser(username);
		
		if(user.getFriends().contains(friend)) {
				user.removeFriend(friend);
		}else {
			System.out.println("This user is NOT your friend");
	}
		}
}
	private List<FacebookUser> getRecommendations (FacebookUser receivingUser){
		Set<FacebookUser> ignore = new HashSet<>();
		ignore.add(receivingUser);
		return getRecommendations(ignore, receivingUser.getFriends());
	}	
	
	private List<FacebookUser> getRecommendations (Set<FacebookUser> ignore, List<FacebookUser> userFriends){
		List<FacebookUser> recommendedFriends = new ArrayList<>();		
		if(userFriends.isEmpty()) {
			return recommendedFriends;
		}
		
		for(FacebookUser user : userFriends) {
			if(ignore.contains(user)) {
				continue;
			}

				recommendedFriends.add(user);
				ignore.add(user);
				
				recommendedFriends.addAll(getRecommendations(ignore, user.getFriends()));
		}
		return recommendedFriends;
	}

	@Override
	public int compare(FacebookUser arg0, FacebookUser arg1) {
		return arg0.compareTo(arg1);
	}

	public void like(Scanner sc) {
		FacebookUser user = getValidUser(sc);
		if(user == null) {
			//Do nothing
		}else {
			System.out.println("What do you like: ");
			user.like(sc.nextLine());
		}
	}
	
	public void listLikes(Scanner sc) {
		FacebookUser user = getValidUser(sc);
		user.listLikes();
	}

	
}
