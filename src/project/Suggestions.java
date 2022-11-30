package project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Suggestions {

	public static TST<Integer> trie = new TST<Integer>();

	public static void createTrie(File file) throws Exception {
		String str = new String(Files.readAllBytes(Paths.get(file.getPath())));

		Pattern pattern = Pattern.compile("[\\w]+");
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> words = new ArrayList<String>();

		while (matcher.find()) {
			words.add(matcher.group());
		}

		for (int i = 0; i < words.size(); i++) {
			if (trie.contains(words.get(i))) {
				trie.put(words.get(i), trie.get(words.get(i)) + 1);
			} else {
				trie.put(words.get(i), 1);
			}
		}
	}

	public static ArrayList<String> retrivingSuggestions(String word) {
		Queue<String> suggestions = (Queue<String> ) trie.prefixMatch(word);
		ArrayList<String> strings = new ArrayList<String>();

		for (int i = 0; i < suggestions.size(); i++) {
			strings.add(suggestions.dequeue());
		}

		return strings;
	}

	public static ArrayList<String> wordSuggestion(String args) {

		File path = new File("WebPages/");
		File[] listOfFiles = path.listFiles();

		for (File file: listOfFiles) {
			try {
				createTrie(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ArrayList<String> result = new ArrayList<>();
		result = retrivingSuggestions(args);
		return result;

	}
}