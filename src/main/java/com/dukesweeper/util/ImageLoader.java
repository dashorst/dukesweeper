package com.dukesweeper.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Utility class for loading images from the classpath.
 */
public class ImageLoader {
    
    /**
     * Loads an image from the classpath.
     * 
     * @param path the path to the image file
     * @return the loaded image or null if the image could not be loaded
     */
    public static BufferedImage loadImage(String path) {
        try (InputStream is = ImageLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Could not find resource: " + path);
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Loads and scales an image from the classpath.
     * 
     * @param path the path to the image file
     * @param width the desired width
     * @param height the desired height
     * @return the loaded and scaled image
     */
    public static Image loadAndScaleImage(String path, int width, int height) {
        BufferedImage img = loadImage(path);
        if (img == null) {
            return null;
        }
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
