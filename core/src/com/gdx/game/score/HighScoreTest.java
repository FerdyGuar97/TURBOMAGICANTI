/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.score;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author salvatore
 */
public class HighScoreTest {
    private final HighScoreTable table;

    public HighScoreTest() throws FileNotFoundException {
        this.table = new HighScoreTable();
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        HighScoreTest test = new HighScoreTest();
        test.table.insertHighScore("zzz", 5000);
        
    }
    
}
