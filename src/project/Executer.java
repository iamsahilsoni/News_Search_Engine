package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Executer {

	Scanner sc = new Scanner(System.in);
	//Displays Main menu
	public static void menu1() throws IOException {
		InvertedIndex.parseHTML();
		Scanner sc_1 = new Scanner(System.in);
		Scanner sc_2 = new Scanner(System.in);
		boolean menu = true;

		while (menu) {
			System.out.println("\n-----------------------------------------\nSearch Engine\n-----------------------------------------");
			System.out.println("Press 1 to crawl the pages again (It can take about 15 to 30 minuites to crawl)");
			System.out.println("Press 2 to Inverted Index");
			System.out.println("Press 3 to Word Suggestions");
			System.out.println("Press 4 to Spell Checker");
			System.out.println("Press 0 to exit");
			System.out.println("-----------------------------------------");
			System.out.print("Select an option: ");
			String ans = sc_1.nextLine();

			switch (ans) {
				case "1":
					menu2();
					menu = false;
					break;
				case "2":
					{
						String user_input;
						System.out.println("Enter the keyword for inverted indexing");
						user_input = sc_1.nextLine();
						InvertedIndex.search(user_input);
						break;
					}

				case "3":
					{
						String user_input;
						System.out.println("Enter the keyword for word suggestions");
						user_input = sc_1.nextLine();
						ArrayList<String> suggestions = Suggestions.wordSuggestion(user_input);

						if (suggestions.size() <= 0) {
							System.out.println("No Suggestion Exist");
						} else {
							for (String string: suggestions) {
								System.out.println(string);
							}
						}

						break;
					}

				case "4":
					{
						String user_input;
						System.out.println("Enter the word for spell checking");
						user_input = sc_1.nextLine();
						ArrayList<String> finalResult = SpellCheck.SpellCheckSuggestions(user_input);

						for (int i = 0; i < finalResult.size(); i++) {
							System.out.println(finalResult.get(i));
						}

						break;
					}

				case "0":
					System.out.println("Exited, Thanks");
					System.exit(0);
					break;
				default:
					System.out.println("Wrong Input, Try again.");
			}
		}

		sc_1.close();
		sc_2.close();

	}
	//Displays CrwalMenu.
	public static void menu2() {
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		boolean menu = true;

		while (menu) {
			System.out.println("\n\n-----------------------------------------\nWeb Crawling\n-----------------------------------------");
			System.out.println("Press 1 to enter websites to crawl");
			System.out.println("Press 2 to crawl the default web pages");
			System.out.println("Press 3 to Erase the webpages crawled");
			System.out.println("Press 0 to exit");
			System.out.println("-----------------------------------------");
			System.out.print("Select an option: ");
			String ans = sc.nextLine();

			switch (ans) {
				case "1":
					System.out.println("Enter websites to crawl saperated by a whitespace\n");
					WebCrawler.crawlCustom(sc2.nextLine());
					break;
				case "2":
					System.out.println("Executing crawl on default links");
					WebCrawler.crawlDefault();
					break;
				case "3":
					System.out.println("Wiping WebPages");
					WebCrawler.wipeWebPages();
					break;
				case "0":
					System.out.println("Exiting, thanks for using our search");
					System.exit(0);
					break;
				default:
					System.out.println("Wrong Input!");
			}

			menu = false;
		}

		System.out.println("Exiting Program.");
		sc.close();
		sc2.close();
	}

	public static void main(String[] args) throws IOException {
		menu1();
	}
}