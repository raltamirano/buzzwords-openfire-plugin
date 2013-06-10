package com.raltamirano.openfire.plugins.buzzwords;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.raltamirano.openfire.plugins.buzzwords.dal.BuzzwordsDAL;
import com.raltamirano.openfire.plugins.buzzwords.model.WordCounter;

public class WordManager {
	private static final Logger logger = Logger.getLogger(WordManager.class.getName());
	
	private static final Pattern WORD_REGEX = Pattern.compile("\\b(\\w+)\\b", Pattern.CASE_INSENSITIVE);
	
	private static final WordManager INSTANCE = new WordManager();
	
	private WordManager() {
	}
		
	public static WordManager getInstance() {
		return INSTANCE;
	}
	
	public void processText(String input) {
		logger.entering(WordManager.class.getName(), "processText", input);
		
		if (input != null) {
			String word = null;
			Matcher matcher = WORD_REGEX.matcher(input.trim().toLowerCase());
			while(matcher.find()) {
				word = matcher.group(1);
				incrementWordCounter(word);
			}
		}
	}
	
	public void printTopStats() {
		printTopStats(10);
	}
	
	public void printTopStats(int topWords) {		
		List<WordCounter> wordStats = BuzzwordsDAL.getInstance().getWordCounterDAO().getTopWords(topWords);
	    
	    System.out.println(String.format("%1$-50s %2$-5s", "WORD", "COUNT"));
	    System.out.println(String.format("%1$-50s %2$-5s", "----", "-----"));
		for(WordCounter wc : wordStats)
			System.out.println(String.format("%1$-50s %2$-5s", wc.getWord(), wc.getCounter()));
		System.out.println();
	}	

	private void incrementWordCounter(String word) {
		BuzzwordsDAL.getInstance().getWordCounterDAO().incrementCounter(word);
	}	
}
