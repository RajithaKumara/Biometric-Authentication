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
public class CropFaceThread extends Thread{

    private BufferedImage openEyeImg;
    private BufferedImage closedEyeImg;
    private int x;
    private int y;
    private int w;
    private int h;
    
    public CropFaceThread(BufferedImage openEyeImg,BufferedImage closedEyeImg) {
        this.openEyeImg = openEyeImg;
        this.closedEyeImg = closedEyeImg;
    }

    @Override
    public void run() {
        ImageProcessor imgProcessor = new ImageProcessor();
        BufferedImage negSubImage = imgProcessor.negativeSubstractImageColor(openEyeImg, closedEyeImg);
        
        BufferedImage filterImage = imgProcessor.filterEdges(negSubImage, 0.8);
        
        int[] heightRange = imgProcessor.faceHieght(filterImage);
        this.y = heightRange[0];
        this.h = heightRange[1] - heightRange[0];
        
        BufferedImage heightCropImg = filterImage.getSubimage(0,this.y, filterImage.getWidth(), this.h);
        
        int[] widthRange = imgProcessor.faceWidth(heightCropImg);
        this.x = widthRange[0];
        this.w = widthRange[1]-widthRange[0];
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getW(){
        return w;
    }
    
    public int getH(){
        return h;
    }
    
}
