/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedure;

import java.awt.image.BufferedImage;
import model.ImageProcessor;

/**
 *
 * @author rajitha
 */
public class SymmetricFactorThread extends Thread{

    private BufferedImage bufImg;
    private long factor;
    private int x;
    private int y;
    private int w;
    private int h;
    
    public SymmetricFactorThread(BufferedImage bufImg,int x,int y,int w,int h) {
        this.bufImg = bufImg;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    @Override
    public void run(){
        ImageProcessor imgProcessor = new ImageProcessor();
        BufferedImage Img = bufImg.getSubimage(this.x, this.y, this.w, this.h);
        this.factor = imgProcessor.symmetricFactor(Img);
    }
    
    public long getSymmericFactor(){
        return this.factor;
    }
}
