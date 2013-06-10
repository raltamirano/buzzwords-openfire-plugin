package com.raltamirano.openfire.plugins.buzzwords.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.database.DbConnectionManager;

import com.raltamirano.openfire.plugins.buzzwords.dal.WordCounterDAO;
import com.raltamirano.openfire.plugins.buzzwords.model.WordCounter;

public class WordCounterDAOImpl implements WordCounterDAO {

	public void incrementCounter(String word) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DbConnectionManager.getConnection();
			statement = connection.prepareStatement("UPDATE bwWord SET counter = counter + 1 WHERE wordText = ?");
			statement.setString(1, word);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				statement = connection.prepareStatement("INSERT INTO bwWord (wordText, counter) VALUES (?, ?)");
				statement.setString(1, word);
				statement.setInt(2, 1);
				statement.executeUpdate();
			}			
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		} finally {
			if (statement != null) try { statement.close(); } catch (Throwable t) { }
			if (connection != null) try { connection.close(); } catch (Throwable t) { }
		}
	}

	public List<WordCounter> getAllWords() {
		return this.getTopWords(Integer.MIN_VALUE);
	}

	public List<WordCounter> getTopWords(int maxTopWords) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnectionManager.getConnection();
			statement = connection.createStatement();
			if (maxTopWords > 0) {
				DbConnectionManager.setMaxRows(statement, maxTopWords);
			}
			
			resultSet = statement.executeQuery("SELECT wordText, counter FROM bwWord ORDER BY counter DESC");
			
			List<WordCounter> words = new ArrayList<WordCounter>();
			
			WordCounter wordCounter = null;
			while(resultSet.next()) {
				wordCounter = new WordCounter();
				wordCounter.setWord(resultSet.getString("wordText"));
				wordCounter.setCounter(resultSet.getInt("counter"));
				words.add(wordCounter);
			}
			
			return words;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (Throwable t) { }
			if (statement != null) try { statement.close(); } catch (Throwable t) { }
			if (connection != null) try { connection.close(); } catch (Throwable t) { }
		}
	}

}
