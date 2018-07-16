package edu.sinclair;

import java.util.Comparator;

public class UserCompareByFriends implements Comparator<FacebookUser>{

	@Override
	public int compare(FacebookUser userOne, FacebookUser userTwo) {
		int rc = 0;
		
		if(userOne.getFriends().size() > userTwo.getFriends().size()) {
			rc = -1;
		}else if(userOne.getFriends().size() < userTwo.getFriends().size()){
			rc = 1;
		}
		
		return rc;
	}

}
