package com.raltamirano.openfire.plugins.buzzwords.dal;

import java.util.List;

import com.raltamirano.openfire.plugins.buzzwords.model.WordCounter;

public interface WordCounterDAO {
	void incrementCounter(String word);
	List<WordCounter> getAllWords();
	List<WordCounter> getTopWords(int maxTopWords);
}
