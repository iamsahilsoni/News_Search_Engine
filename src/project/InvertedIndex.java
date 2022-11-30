package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class InvertedIndex {

	public static TrieST < HashMap<String, Integer>> tries = new TrieST < HashMap<String, Integer>>();

	public static HashMap < String, HashMap<String, Integer>> reverseIndex = new HashMap < String, HashMap<String, Integer>>();

	public static void parseHTML() throws IOException {
		String strHtmlFilesPath = "WebPages/";
		File path = new File(strHtmlFilesPath);
		File[] files = path.listFiles();

		String strContent;
		String[] arrTokens = {};

		HashMap < String, HashMap<String, Integer>> pageMapTokenFrequency = new HashMap < String, HashMap<String, Integer>>();

		for (int i = 0; i < files.length; i++) {

			HashMap<String, Integer> mapTokenFrequency = new HashMap<String, Integer>();
			//    		String str = files[i].getPath();
			if (files[i].isFile()) {
				try {
					Document document = Jsoup.parse(files[i], "UTF-8", strHtmlFilesPath);

					Element body = document.body();

					strContent = body.text();

					strContent = strContent.replaceAll("[^A-Za-z0-9]+", " ");

					strContent = strContent.toLowerCase();

					arrTokens = strContent.split("\\W");
					pageMapTokenFrequency.put(files[i].getPath(), mapTokenFrequency);

				} catch (FileNotFoundException e) {

					e.printStackTrace();

				}

				for (int p = 0; p < arrTokens.length; p++) {

					String strKey = arrTokens[p];

					if (strKey == "") continue;

					if (mapTokenFrequency.containsKey(strKey)) {

						int value = mapTokenFrequency.get(strKey);

						mapTokenFrequency.put(strKey, value + 1);
					} else {
						mapTokenFrequency.put(strKey, 1);
					}
				}

			}

		} // outer for loop
		// reverse index
		Set<String> setArticleKey = pageMapTokenFrequency.keySet();
		Iterator<String> iter = setArticleKey.iterator();
		reverseIndex = new HashMap < String, HashMap<String, Integer>>();

		for (int i = 0; i < setArticleKey.size(); i++) {
			String article = iter.next();

			HashMap<String, Integer> wordFrequencyMap = pageMapTokenFrequency.get(article);

			Set<String> setWordKey = wordFrequencyMap.keySet();

			Iterator<String> iter1 = setWordKey.iterator();

			for (int j = 0; j < setWordKey.size(); j++) {

				String word = iter1.next();
				int freq = wordFrequencyMap.get(word);

				if (reverseIndex.containsKey(word)) {
					HashMap<String, Integer> mapWord = reverseIndex.get(word);
					mapWord.put(article, freq);
				} else {
					HashMap<String, Integer> mapWord = new HashMap<String, Integer>();
					mapWord.put(article, freq);
					reverseIndex.put(word, mapWord);
				}
			}
		}
		// Trie
		Set<String> reverseIndexKeys = reverseIndex.keySet();
		Iterator<String> reverseiter = reverseIndexKeys.iterator();
		tries = new TrieST < HashMap<String, Integer>>();

		for (int i = 0; i < reverseIndexKeys.size(); i++) {
			String word = reverseiter.next();
			tries.put(word, reverseIndex.get(word));
		}

	}

	public static void search(String user_input) {
		HashMap<String, Integer> finalResult = tries.get(user_input);
		Set<String> finalKeys = finalResult.keySet();
		Iterator<String> finalIter = finalKeys.iterator();

		for (int i = 0; i < finalKeys.size(); i++) {
			String key = finalIter.next();
			System.out.println("Frequeuency: " + finalResult.get(key) + "\t" + key);
		}
	}
}