package com.dukesweeper.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility class to pre-warm the text rendering system in macOS to avoid the 
 * 2-3 second delay when first rendering text in an AWT/Swing application.
 */
public class GraphicsPrewarmer {
    
    /**
     * Initializes the text rendering engine in a virtual thread to avoid blocking
     * the main application thread during startup.
     */
    public static void prewarmGraphics() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                // Create a small non-visible image to render into
                BufferedImage img = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = img.createGraphics();
                
                // Set up some basic rendering properties
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
                
                // Draw some text to initialize the text rendering pipeline
                g2d.drawString("banana", 10, 20);
                
                // Clean up
                g2d.dispose();
                
                System.out.println("Graphics text rendering pipeline prewarmed successfully");
            });
        }
    }
}
