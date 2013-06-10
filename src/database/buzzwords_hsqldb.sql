INSERT INTO ofVersion (name, version) VALUES ('buzzwords', 0);
CREATE TABLE bwWord(wordText VARCHAR(100) NOT NULL, counter INT NOT NULL, CONSTRAINT bwWordPK PRIMARY KEY(wordText));
