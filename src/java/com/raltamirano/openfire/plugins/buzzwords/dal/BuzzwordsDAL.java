package com.raltamirano.openfire.plugins.buzzwords.dal;

import com.raltamirano.openfire.plugins.buzzwords.dal.impl.BuzzwordsDAOFactoryImpl;

public class BuzzwordsDAL {
	private final BuzzwordsDAOFactory factory;
	
	private static final BuzzwordsDAL INSTANCE = new BuzzwordsDAL();
	
	private BuzzwordsDAL() {
		//TODO: remove hard-coding of the DAO factory
		this.factory = new BuzzwordsDAOFactoryImpl();
	}
	
	public static BuzzwordsDAL getInstance() {
		return INSTANCE;
	}
	
	public WordCounterDAO getWordCounterDAO() {
		return this.factory.createWordCounterDAO();				
	}
}	
