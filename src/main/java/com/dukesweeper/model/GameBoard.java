package com.dukesweeper.model;

import java.util.Random;

/**
 * Represents the game board for DukeSweeper.
 * The board consists of a grid where each cell can either have a coffee bean or not.
 */
public class GameBoard {
    // Adjusted grid dimensions to fit within 800px width at 16px per cell
    private static final int COLS = 50; // 50 * 16px = 800px wide
    private static final int ROWS = 30; // Reduced for a reasonable aspect ratio
    private static final double BEAN_PROBABILITY = 1.0 / 20.0;
    
    // Grid of booleans where true represents a coffee bean
    private boolean[][] grid;
    // Grid to track which cells have been revealed by the player
    private boolean[][] revealed;
    private Random random;
    // Track if the game is over
    private boolean gameOver;
    
    /**
     * Creates a new game board and initializes it with randomly placed beans.
     */
    public GameBoard() {
        grid = new boolean[ROWS][COLS];
        revealed = new boolean[ROWS][COLS];
        random = new Random();
        gameOver = false;
        initializeGrid();
    }
    
    /**
     * Initializes the grid with randomly placed beans based on the bean probability.
     */
    private void initializeGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = random.nextDouble() < BEAN_PROBABILITY;
            }
        }
    }
    
    /**
     * Checks if the cell at the given position contains a bean.
     * 
     * @param row The row index
     * @param col The column index
     * @return true if the cell contains a bean, false otherwise
     */
    public boolean hasBean(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return false;
        }
        return grid[row][col];
    }
    
    /**
     * Counts the number of beans in the 8 cells surrounding the given position.
     * 
     * @param row The row index
     * @param col The column index
     * @return The count of beans in the surrounding cells
     */
    public int countSurroundingBeans(int row, int col) {
        int count = 0;
        
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                // Skip the center cell (the one we're counting around)
                if (r == row && c == col) continue;
                
                // Count if there's a bean in this adjacent cell
                if (hasBean(r, c)) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * @return the number of rows in the grid
     */
    public int getRows() {
        return ROWS;
    }
    
    /**
     * @return the number of columns in the grid
     */
    public int getCols() {
        return COLS;
    }
    
    /**
     * Checks if the cell at the given position has been revealed.
     * 
     * @param row The row index
     * @param col The column index
     * @return true if the cell has been revealed, false otherwise
     */
    public boolean isRevealed(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return false;
        }
        return revealed[row][col];
    }
    
    /**
     * Reveals the cell at the given position.
     * 
     * @param row The row index
     * @param col The column index
     * @return true if a bean was revealed, false otherwise
     */
    public boolean revealCell(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS || revealed[row][col] || gameOver) {
            return false;
        }
        
        revealed[row][col] = true;
        
        // Check if a bean was hit and set game over state
        if (grid[row][col]) {
            gameOver = true;
            return true;
        }
        
        // If this cell has no surrounding beans and is not a bean itself,
        // automatically reveal adjacent cells (flood fill)
        if (!hasBean(row, col) && countSurroundingBeans(row, col) == 0) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (r == row && c == col) continue;
                    revealCell(r, c);
                }
            }
        }
        
        return grid[row][col];
    }
    
    /**
     * Checks if the game is over.
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
