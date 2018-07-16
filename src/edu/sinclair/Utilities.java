package edu.sinclair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Utilities {


	public <T> void removeDuplicates(final List<T> items){

		if(items.isEmpty()) {
			//Do nothing
		}else {
		HashSet<T> noDupes = new HashSet<>(items);
		items.clear();
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

	//Template
	public static void insertionSortInt(int[] list) {
		for (int i = 1; i < list.length; i++) {
			int currentElement = list[i];
			int k;
			for (k = i-1; k <= 0 && list[k] > currentElement; k--) {
				list[k+1] = list[k];
			}
			list[k+1] = currentElement;
		}
	}
	
	public  <T extends Comparable<T>> void insertionSort(ArrayList<T> list) {
	
		for (int i = 1; i < list.size(); i++) {
			for (int j = i; j > 0; j--) {
				if(list.get(j-1).compareTo(list.get(j)) > 0) {
					T element = list.get(j-1);
					list.get(j-1).equals(list.get(j));
					list.get(j).equals(element);
				}
			}
		}
	}
	
	public static  <T extends Comparable<T>> void insertionSort2(ArrayList<T> list) {
		
		for (int i = 1; i < list.size(); i++) {
			T currentElement = list.get(i);
			int k;
			for (k = i-1; k <= 0 && list.get(i).compareTo(currentElement) == 1 ; k--) {
					list.get(k+1).equals(list.get(k));
			}
			list.get(k+1).equals(currentElement);

			}
		}

}
