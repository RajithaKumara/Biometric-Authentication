/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author rajitha
 */
public class ImageProcessor {
    
    public BufferedImage grayScaleImage(BufferedImage bufImg){
      int width=bufImg.getWidth();
      int height=bufImg.getHeight();
      for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color c = new Color(bufImg.getRGB(j, i));
            int red = (int)(c.getRed() * 0.299);
            int green = (int)(c.getGreen() * 0.587);
            int blue = (int)(c.getBlue() *0.114);
            Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
            bufImg.setRGB(j,i,newColor.getRGB());      
          }
        }
      return bufImg; 
    }
    
    public BufferedImage grayScaleImageRGBRatio(BufferedImage bufImg,int redPercent,int greenPercent, int bluePercent){
      int width=bufImg.getWidth();
      int height=bufImg.getHeight();
      double redRatio=(redPercent*100/(redPercent+greenPercent+bluePercent))/100.0;
      double greenRatio=(greenPercent*100/(redPercent+greenPercent+bluePercent))/100.0;
      double blueRatio=(bluePercent*100/(redPercent+greenPercent+bluePercent))/100.0;
      
      System.out.println(redRatio);
      System.out.println(greenRatio);
      System.out.println(blueRatio);
      
      for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color c = new Color(bufImg.getRGB(j, i));
            int red = (int)(c.getRed() * redRatio);
            int green = (int)(c.getGreen() * greenRatio);
            int blue = (int)(c.getBlue() * blueRatio);
            Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
            bufImg.setRGB(j,i,newColor.getRGB());      
          }
        }
      return bufImg; 
    }
    
    public long countEyeWhitePixels(BufferedImage openEyeImg,BufferedImage closedEyeImg){
        int width=openEyeImg.getWidth();
        int height=openEyeImg.getHeight();
        long countOpenEyeImg = 0;
        long countClosedEyeImg = 0;
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color openEyeImgColor = new Color(openEyeImg.getRGB(j, i));
            int rgb_open = openEyeImgColor.getRed()+openEyeImgColor.getGreen()+openEyeImgColor.getBlue();

            Color closedEyeImgColor = new Color(closedEyeImg.getRGB(j, i));
            int rgb_closed = closedEyeImgColor.getRed()+closedEyeImgColor.getGreen()+closedEyeImgColor.getBlue();

            if (rgb_open > 700){
                countOpenEyeImg++;
            }
            if (rgb_closed > 700){
                countClosedEyeImg++;
            } 
          }
        }
        return Math.abs(countOpenEyeImg-countClosedEyeImg); 
    }
    
    public long countEyeBlackPixels(BufferedImage openEyeImg,BufferedImage closedEyeImg){
        int width=openEyeImg.getWidth();
        int height=openEyeImg.getHeight();
        long countOpenEyeImg = 0;
        long countClosedEyeImg = 0;
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color openEyeImgColor = new Color(openEyeImg.getRGB(j, i));
            int rgb_open = openEyeImgColor.getRed()+openEyeImgColor.getGreen()+openEyeImgColor.getBlue();

            Color closedEyeImgColor = new Color(closedEyeImg.getRGB(j, i));
            int rgb_closed = closedEyeImgColor.getRed()+closedEyeImgColor.getGreen()+closedEyeImgColor.getBlue();

            if (765-rgb_open > 635){
                countOpenEyeImg++;
            }
            if (765-rgb_closed > 635){
                countClosedEyeImg++;
            } 
          }
        }
        return Math.abs(countOpenEyeImg-countClosedEyeImg); 
    }
    
    public BufferedImage substractImageColor(BufferedImage openEyeImg,BufferedImage closedEyeImg){
        int width=openEyeImg.getWidth();
        int height=openEyeImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color openEyeImgColor = new Color(openEyeImg.getRGB(j, i));
            int red_open = (int)openEyeImgColor.getRed();
            int green_open = (int)openEyeImgColor.getGreen();
            int blue_open = (int)openEyeImgColor.getBlue();

            Color closedEyeImgColor = new Color(closedEyeImg.getRGB(j, i));
            int red_closed = (int)closedEyeImgColor.getRed();
            int green_closed = (int)closedEyeImgColor.getGreen();
            int blue_closed = (int)closedEyeImgColor.getBlue();  

            Color newColor = new Color(Math.abs(red_open-red_closed),Math.abs(green_open-green_closed),Math.abs(blue_open-blue_closed));
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage negativeSubstractImageColor(BufferedImage firstImg,BufferedImage secondImg){
        int width=firstImg.getWidth();
        int height=firstImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color firstImgColor = new Color(firstImg.getRGB(j, i));
            int r_first = (int)firstImgColor.getRed();
            int g_first = (int)firstImgColor.getGreen();
            int b_first = (int)firstImgColor.getBlue();

            Color secondImgColor = new Color(secondImg.getRGB(j, i));
            int r_second = (int)secondImgColor.getRed();
            int g_second = (int)secondImgColor.getGreen();
            int b_second = (int)secondImgColor.getBlue();  
            
            int r = 255 - Math.abs(r_first - r_second);
            int g = 255 - Math.abs(g_first - g_second);
            int b = 255 - Math.abs(b_first - b_second);

            Color newColor = new Color(r,g,b);
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage addImageColor(BufferedImage firstImg,BufferedImage secondImg,int ratio){
        int width=firstImg.getWidth();
        int height=firstImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color firstImgColor = new Color(firstImg.getRGB(j, i));
            int r_first = (int)firstImgColor.getRed();
            int g_first = (int)firstImgColor.getGreen();
            int b_first = (int)firstImgColor.getBlue();

            Color secondImgColor = new Color(secondImg.getRGB(j, i));
            int r_second = (int)secondImgColor.getRed();
            int g_second = (int)secondImgColor.getGreen();
            int b_second = (int)secondImgColor.getBlue();  
            
            int r = Math.min(r_first + (r_second * ratio),255);
            int g = Math.min(g_first + (g_second * ratio),255);
            int b = Math.min(b_first + (b_second * ratio),255);

            Color newColor = new Color(r,g,b);
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage negativeImage(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            Color newColor = new Color(255-red,255-green,255-blue);
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage logColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            double log_red=Math.log1p(red);
            double log_green=Math.log1p(green);
            double log_blue=Math.log1p(blue);

            Color newColor = new Color((int) Math.pow(2, log_red),(int) Math.pow(2, log_green),(int) Math.pow(2, log_blue));
            //Color newColor = new Color((int) log_red,(int) log_green,(int) log_blue);
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage divideColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int col=imgColor.getRGB();

            outImg.setRGB(j,i,-col/100); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage changeColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            Color newColor = new Color(blue,red,green);
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage reduceResolution(BufferedImage bufImg,int amount){
        // amount should < width and height
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        int max_step = Math.min(width,height)-1;
        if (amount > max_step){
            amount = max_step;
        }
        int step = amount;
        
        
        for(int i=0; i<height; i+=step){
          for(int j=0; j<width; j+=step){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int rgb=imgColor.getRGB();

            for (int y=0;y<step;y++){
                for (int x=0;x<step;x++){
                    try{
                        outImg.setRGB(j+x,i+y,rgb);
                    }catch(Exception e){
                        //System.out.println(e);
                    }
                    
                }
            } 
          }
        }
        return outImg; 
    }
    
    public BufferedImage multiplyColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            double redTan = Math.tan((Math.PI*red) / 512.0);
            double greenTan = Math.tan((Math.PI*green) / 512.0);
            double blueTan = Math.tan((Math.PI*blue) / 512.0);
            
            if (redTan>1.0) redTan=1.0;
            if (greenTan>1.0) greenTan=1.0;
            if (blueTan>1.0) blueTan=1.0;

            Color newColor = new Color((float) redTan,(float) greenTan,(float) blueTan);
            
            //outImg.setRGB(j,i,rgb); 
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage sinColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            double redSin = Math.sin((Math.PI * red) / 512.0);
            double greenSin = Math.sin((Math.PI * green) / 512.0);
            double blueSin = Math.sin((Math.PI * blue) / 512.0);

            Color newColor = new Color((float) redSin,(float) greenSin,(float) blueSin);
            
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage cosColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int red = (int)imgColor.getRed();
            int green = (int)imgColor.getGreen();
            int blue = (int)imgColor.getBlue();

            double redCos = Math.cos((Math.PI * red) / 512.0);
            double greenCos = Math.cos((Math.PI * green) / 512.0);
            double blueCos = Math.cos((Math.PI * blue) / 512.0);

            Color newColor = new Color((float) redCos,(float) greenCos,(float) blueCos);
            
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage darkColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            
            outImg.setRGB(j,i,imgColor.darker().getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage increaseDarkColor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int r = (int)imgColor.getRed();
            int g = (int)imgColor.getGreen();
            int b = (int)imgColor.getBlue();

            double red = Math.pow(r/256.0, 2);
            double green = Math.pow(g/256.0, 2);
            double blue = Math.pow(b/256.0, 2);
            
            Color newColor = new Color((float) red,(float) green,(float) blue);
            
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public BufferedImage filterEdges(BufferedImage bufImg, double filterAmount){
        // 0.0  < filterAmount < 1.0
        int width = bufImg.getWidth();
        int height = bufImg.getHeight();
        BufferedImage outImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        long blackCount = 0;
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int r = (int)imgColor.getRed();
            int g = (int)imgColor.getGreen();
            int b = (int)imgColor.getBlue();
            
            double rgb = Math.pow((r+g+b)/768.0,2);
            if (rgb < filterAmount){
                rgb = 0.0;
                blackCount++;
            }else{
                rgb=1.0;
            }

            Color newColor = new Color((float) rgb,(float) 0.0,(float) 0.0);
            
            outImg.setRGB(j,i,newColor.getRGB()); 
          }
        }
        return outImg; 
    }
    
    public long filterEdges(double filterAmount, BufferedImage bufImg){
        // 0.0  < filterAmount < 1.0
        int width = bufImg.getWidth();
        int height = bufImg.getHeight();
        long blackCount = 0;
        
        for(int i=0; i<height; i++){
          for(int j=0; j<width; j++){
            Color imgColor = new Color(bufImg.getRGB(j, i));
            int r = (int)imgColor.getRed();
            int g = (int)imgColor.getGreen();
            int b = (int)imgColor.getBlue();
            
            double rgb = Math.pow((r+g+b)/768.0,2);
            if (rgb < filterAmount){
                blackCount++;
            }
          }
        }
        return blackCount; 
    }
    
    public long symmetricFactor(BufferedImage bufImg){
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        BufferedImage outImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        int step=Math.max((int) (width*height*0.00001),1);
        //System.out.println(step);
        long factor=0;
        
        for(int i=0; i<height; i+=step){
          for(int j=0; j<width/2; j+=step){
            Color leftColor = new Color(bufImg.getRGB(j, i));
            Color rightColor = new Color(bufImg.getRGB(width-j-1, i));
            
            int r=leftColor.getRed();
            int g=leftColor.getGreen();
            int b=leftColor.getBlue();
            
            /*
            Color leftColorDark = new Color((int) (r*0.7),
                                            (int) (g*0.7),
                                            (int) (b*0.7));
            */
            int darkR = (int) (r*0.7);
            int darkG = (int) (g*0.7);
            int darkB = (int) (b*0.7);
            
            if ( r >= 0 && r < 3 ) r = 3;
            if ( g >= 0 && g < 3 ) g = 3;
            if ( b >= 0 && b < 3 ) b = 3;
            
            /*
            Color leftColorBright = new Color(Math.min((int) (r/0.7),255),
                                              Math.min((int) (g/0.7),255),
                                              Math.min((int) (b/0.7),255));
            */
            int brightR = Math.min((int) (r/0.7),255);
            int brightG = Math.min((int) (g/0.7),255);
            int brightB = Math.min((int) (b/0.7),255);
            
            r=rightColor.getRed();
            g=rightColor.getGreen();
            b=rightColor.getBlue();
            
            /*
            System.out.print(darkR+", "+brightR+", "+r);
            System.out.print(" || "+darkG+", "+brightG+", "+g);
            System.out.println(" || "+darkB+", "+brightB+", "+b);
            */
            
            if (darkR<r && r<brightR &&
                darkG<g && g<brightG &&
                darkB<b && b<brightB){
                factor++;
            }
            
            outImg.setRGB(j,i,rightColor.getRGB()); 
          }
        }
        //System.out.println(factor);
        return factor; 
    }
    
    public long eyeDistance(BufferedImage bufImg){
        long time = System.currentTimeMillis();
        //should enter filterEdges image
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        ArrayList<Long> array = new ArrayList<>();
        long horizontalKey = 0;
        long verticalKey = 0;
        long distance = 0;
        long thickness = 0;
        width = ((int) width/4)*4;
        height = ((int) height/4)*4;
        System.out.println(width+", "+height);
        
        for(int i=0; i<height; i+=4){
          for(int j=0; j<width; j+=4){
              
                Color c1 = new Color(bufImg.getRGB(j, i));
                Color c2 = new Color(bufImg.getRGB(j+2, i+2));
                
                int rgb1 = c1.getRGB();
                int rgb2 = c2.getRGB();
                
                if (rgb1 == -16777216 && rgb2 == -16777216){
                    thickness++;
                }else if (rgb1 == -16777216 && rgb2 != -16777216){
                    verticalKey = i;
                    horizontalKey = j;
                }else if (rgb1 != -16777216 && rgb2 == -16777216){
                    distance = j - horizontalKey;
                }
          } 
          System.out.println("d: "+distance+", t: "+thickness);
          horizontalKey = 0;
          thickness = 0;
        }
        System.out.println("time: "+(System.currentTimeMillis()-time));
        //System.out.println(distance);
        return 0;
    }
    
    public int[] faceHieght(BufferedImage bufImg){
        //long time = System.currentTimeMillis();
        //should enter filterEdges image
        int step = 5;
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        int leftTopLevel = 0;
        int leftBottomLevel = 0;
        int rightTopLevel = 0;
        int rightBottomLevel = 0;
        
        long leftTopLevelTotal = 0;
        long leftBottomLevelTotal = 0;
        long rightTopLevelTotal = 0;
        long rightBottomLevelTotal = 0;
        
        for(int i=0; i<width/2; i+=step){
            boolean leftTopDetect =  false;
            boolean leftBottomDetect = false;
            boolean rightTopDetect =  false;
            boolean rightBottomDetect = false;
            int count = 0;
            while( (!leftTopDetect || !leftBottomDetect || !rightTopDetect || !rightBottomDetect) && count<height/2 ){
                Color leftTopColor = new Color(bufImg.getRGB(i, count));
                Color leftBottomColor = new Color(bufImg.getRGB(i, height-count-1));
                Color rightTopColor = new Color(bufImg.getRGB(width-i-1, count));
                Color rightBottomColor = new Color(bufImg.getRGB(width-i-1, height-count-1));
                
                int rgbLeftTop = leftTopColor.getRGB();
                int rgbLeftBottom = leftBottomColor.getRGB();
                int rgbRightTop = rightTopColor.getRGB();
                int rgbRightBottom = rightBottomColor.getRGB();
                
                if (rgbLeftTop == -16777216 && !leftTopDetect){
                    leftTopLevel = count;
                    leftTopDetect = true;
                }
                if (rgbLeftBottom == -16777216 && !leftBottomDetect){
                    leftBottomLevel = count;
                    leftBottomDetect = true;
                }
                if (rgbRightTop == -16777216 && !rightTopDetect){
                    rightTopLevel = count;
                    rightTopDetect = true;
                }
                if (rgbRightBottom == -16777216 && !rightBottomDetect){
                    rightBottomLevel = count;
                    rightBottomDetect = true;
                }
                count++;
                
            }
            leftTopLevelTotal += leftTopLevel;
            leftBottomLevelTotal += leftBottomLevel;
            rightTopLevelTotal += rightTopLevel;
            rightBottomLevelTotal += rightBottomLevel;
            //System.out.println("topL :"+leftTopLevel+" ,bottomL :"+leftBottomLevel+" ,topR :"+rightTopLevel+" ,bottomR :"+rightBottomLevel+", count:"+count);
        }
        int topLevelAverage = (int) ( leftTopLevelTotal+rightTopLevelTotal )/(width/step);
        int bottomLevelAverage = (int) (leftBottomLevelTotal+rightBottomLevelTotal)/(width/step);
        int[] list = new int[2];
        list[0] = topLevelAverage;
        list[1] = height-bottomLevelAverage;
        //System.out.println("H time: "+(System.currentTimeMillis()-time)+" : "+list[0]+","+list[1]);
        return list;
    }
    
    public int[] faceWidth(BufferedImage bufImg){
        //long time = System.currentTimeMillis();
        //should enter filterEdges image
        int step = 5;
        int width=bufImg.getWidth();
        int height=bufImg.getHeight();
        int topLeftLevel = 0;
        int topRightLevel = 0;
        int bottomLeftLevel = 0;
        int bottomRightLevel = 0;
        
        long topLeftLevelTotal = 0;
        long topRightLevelTotal = 0;
        long bottomLeftLevelTotal = 0;
        long bottomRightLevelTotal = 0;
        
        for(int j=0; j<height/2; j+=step){
            boolean topLeftDetect =  false;
            boolean topRightDetect = false;
            boolean bottomLeftDetect =  false;
            boolean bottomRightDetect = false;
            int count = 0;
            while( (!topLeftDetect || !topRightDetect || !bottomLeftDetect || !bottomRightDetect) && count<width/2 ){
                Color topLeftColor = new Color(bufImg.getRGB(count,j));
                Color topRightColor = new Color(bufImg.getRGB(width-count-1,j));
                Color bottomLeftColor = new Color(bufImg.getRGB(count,height-j-1));
                Color bottomRightColor = new Color(bufImg.getRGB(width-count-1,height-j-1));
                
                int rgbTopLeft = topLeftColor.getRGB();
                int rgbTopRight = topRightColor.getRGB();
                int rgbBottomLeft = bottomLeftColor.getRGB();
                int rgbBottomRight = bottomRightColor.getRGB();
                
                if (rgbTopLeft == -16777216 && !topLeftDetect){
                    topLeftLevel = count;
                    topLeftDetect = true;
                }
                if (rgbTopRight == -16777216 && !topRightDetect){
                    topRightLevel = count;
                    topRightDetect = true;
                }
                if (rgbBottomLeft == -16777216 && !bottomLeftDetect){
                    bottomLeftLevel = count;
                    bottomLeftDetect = true;
                }
                if (rgbBottomRight == -16777216 && !bottomRightDetect){
                    bottomRightLevel = count;
                    bottomRightDetect = true;
                }
                count++;
                
            }
            topLeftLevelTotal += topLeftLevel;
            topRightLevelTotal += topRightLevel;
            bottomLeftLevelTotal += bottomLeftLevel;
            bottomRightLevelTotal += bottomRightLevel;
            //System.out.println("topL :"+leftTopLevel+" ,bottomL :"+leftBottomLevel+" ,topR :"+rightTopLevel+" ,bottomR :"+rightBottomLevel+", count:"+count);
        }
        int leftLevelAverage =(int) ( topLeftLevelTotal+bottomLeftLevelTotal )/(width/step);
        int rightLevelAverage =(int) (topRightLevelTotal+bottomRightLevelTotal)/(width/step);
        int[] list = new int[2];
        list[0] = leftLevelAverage;
        list[1] = width-rightLevelAverage;
        //System.out.println("W time: "+(System.currentTimeMillis()-time)+" : "+list[0]+","+list[1]);
        return list;
    }
}
