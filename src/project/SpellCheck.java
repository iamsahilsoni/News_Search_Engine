package project;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class SpellCheck {
	public static ArrayList<String> SpellCheckSuggestions(String word) 
	{
		ArrayList<String> list = new ArrayList<String> ();
		ArrayList<Integer> listDistance = new ArrayList<Integer>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		listDistance.add(1000);
		listDistance.add(1000);
		listDistance.add(1000);
		listDistance.add(1000);
		listDistance.add(1000);
		int distance = 0;
		
		Set<String> setString = InvertedIndex.reverseIndex.keySet();
		Iterator<String> itter = setString.iterator(); 
		
		for(int i = 0; i < setString.size(); i++) 
		{
			String dictionaryWords = itter.next();
			distance = EditDistance.editDistance(word, dictionaryWords);
			if(distance == 0)
				return new ArrayList<String>();
			else 
			{
				if(distance < listDistance.get(4)) 
				{
					for(int j = 0; j < 5; j++) 
					{
						if(distance <= listDistance.get(j)) 
						{
							listDistance.add(j,distance);
							listDistance.remove(5);
							
							list.add(j,dictionaryWords);
							list.remove(5);
							break;
						}
					}
				}
			}
				
		}
		return list;
	}
}
