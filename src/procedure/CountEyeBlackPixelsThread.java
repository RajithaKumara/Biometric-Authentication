/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedure;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ImageProcessor;

/**
 *
 * @author rajitha
 */
public class CountEyeBlackPixelsThread extends Thread{

    private BufferedImage openEyeImg;
    private BufferedImage closedEyeImg;
    private long eyePixels;
            
    public CountEyeBlackPixelsThread(BufferedImage openEyeImg,BufferedImage closedEyeImg) {
        this.openEyeImg = openEyeImg;
        this.closedEyeImg = closedEyeImg;
    }
    
    @Override
    public void run(){
        ImageProcessor imgProcessor = new ImageProcessor();
        this.eyePixels = imgProcessor.countEyeBlackPixels(openEyeImg, closedEyeImg);
    }
    
    public long getEyePixels(){
        return this.eyePixels;
    }
    
}
