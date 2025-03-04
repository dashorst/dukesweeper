package com.dukesweeper.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.dukesweeper.model.GameBoard;
import com.dukesweeper.util.ImageLoader;

/**
 * Canvas component that draws the DukeSweeper game grid and handles user interactions.
 */
public class GamePanel extends Canvas {
    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 16; // 16 pixels per cell
    private static final Color UNREVEALED_COLOR = Color.LIGHT_GRAY;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BEAN_COLOR = new Color(139, 69, 19); // Brown for coffee bean
    
    private GameBoard gameBoard;
    private Font font;
    private Image gameOverImage;
    
    public GamePanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        
        // Set up the canvas size based on the grid size
        int width = gameBoard.getCols() * CELL_SIZE; // Should be 800px (50 * 16)
        int height = gameBoard.getRows() * CELL_SIZE; // Should be 480px (30 * 16)
        setPreferredSize(new Dimension(width, height));
        
        // Increase font size to fit the larger cells
        font = new Font("Monospaced", Font.BOLD, 14);
        
        // Load game over image
        gameOverImage = ImageLoader.loadAndScaleImage("/images/gameover.png", width/2, -height/2);
        
        // Add mouse listener to handle cell clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                
                if (col >= 0 && col < gameBoard.getCols() && row >= 0 && row < gameBoard.getRows()) {
                    gameBoard.revealCell(row, col);
                    repaint();
                }
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        // Draw the grid and cell contents
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                
                // Draw cell background
                if (gameBoard.isRevealed(row, col)) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(UNREVEALED_COLOR);
                }
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // Draw cell content if revealed
                if (gameBoard.isRevealed(row, col)) {
                    if (gameBoard.hasBean(row, col)) {
                        // Draw a bean (*)
                        g.setColor(BEAN_COLOR);
                        g.setFont(font);
                        g.drawString("*", x + 5, y + 12);
                    } else {
                        // Draw count of surrounding beans
                        int count = gameBoard.countSurroundingBeans(row, col);
                        if (count > 0) {
                            g.setColor(getNumberColor(count));
                            g.setFont(font);
                            g.drawString(Integer.toString(count), x + 5, y + 12);
                        }
                    }
                }
                
                // Draw grid lines
                g.setColor(GRID_COLOR);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
        
        // Draw game over image if game is over
        if (gameBoard.isGameOver()) {
            int width = gameBoard.getCols() * CELL_SIZE; // Should be 800px (50 * 16)
            int height = gameBoard.getRows() * CELL_SIZE; // Should be 800px (50 * 16)

            g.drawImage(gameOverImage, width / 4, height / 2 - gameOverImage.getHeight(null) / 2, this);
        }
    }
    
    /**
     * Returns a color for the number based on the count, similar to classic minesweeper.
     */
    private Color getNumberColor(int count) {
        switch (count) {
            case 1: return new Color(0, 0, 255);    // Blue
            case 2: return new Color(0, 128, 0);    // Green
            case 3: return new Color(255, 0, 0);    // Red
            case 4: return new Color(0, 0, 128);    // Dark Blue
            case 5: return new Color(128, 0, 0);    // Dark Red
            case 6: return new Color(0, 128, 128);  // Teal
            case 7: return new Color(0, 0, 0);      // Black
            case 8: return new Color(128, 128, 128); // Gray
            default: return TEXT_COLOR;
        }
    }
}
