package com.raltamirano.openfire.plugins.buzzwords.dal.impl;

import com.raltamirano.openfire.plugins.buzzwords.dal.BuzzwordsDAOFactory;
import com.raltamirano.openfire.plugins.buzzwords.dal.WordCounterDAO;

public class BuzzwordsDAOFactoryImpl implements BuzzwordsDAOFactory {
	
	public WordCounterDAO createWordCounterDAO() {
		return new WordCounterDAOImpl();
	}
	
}
