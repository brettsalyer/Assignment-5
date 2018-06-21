package edu.sinclair;

import java.util.HashSet;
import java.util.List;

public class Utilities {


	public <T> void removeDuplicates(final List<T> items){

		if(items.isEmpty()) {
			//Do nothing
		}else {
		HashSet<T> noDupes = new HashSet<>(items);
		items.addAll(noDupes);
		}
		
		} 
	
	public <E> E linearSearch(List<E> list, E key) {
		
		E found = null;
		
		if(list.isEmpty()) {
			//Nothing to do
		}else {
			
		for(E item : list) {
			if(key.equals(item)) {
				found = item;
			}
		}
		}
		
		return found;
	}


}
