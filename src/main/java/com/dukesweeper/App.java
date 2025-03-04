package com.dukesweeper;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.dukesweeper.model.GameBoard;
import com.dukesweeper.ui.GamePanel;
import com.dukesweeper.util.GraphicsPrewarmer;

public class App {
    public static void main(String[] args) {
        // Pre-warm the graphics text rendering to avoid macOS 2-3 second delay
        GraphicsPrewarmer.prewarmGraphics();
        
        // Create the game board
        GameBoard gameBoard = new GameBoard();
        
        // Create the game panel
        GamePanel gamePanel = new GamePanel(gameBoard);
        
        // Create and set up the game window
        Frame frame = new Frame("DukeSweeper");
        frame.add(gamePanel);
        frame.pack(); // Adjust frame size to fit the panel
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}