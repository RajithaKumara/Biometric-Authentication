/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import procedure.SymmetricFactorThread;
import procedure.CountEyeWhitePixelsThread;
import controller.BiometricAuthentication;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import procedure.CountEyeBlackPixelsThread;
import procedure.CropFaceThread;
import util.DbConn;
import util.FileIO;

/**
 *
 * @author rajitha
 */
public class User {
    DbConn dbConn;
    public User() throws SQLException{
        this.dbConn = new DbConn();
    }
    
    public boolean register(String openEyeLocation,String closedEyeLocation,String userName){
        //long time = System.currentTimeMillis();
        long eyeBlackPix = 1;
        long eyeWhitePix = 1;
        long symmetricFact = 1;

        try{
            FileIO fileIO = new FileIO();
            BufferedImage openEyeImg = fileIO.readImage(openEyeLocation);
            BufferedImage closedEyeImg = fileIO.readImage(closedEyeLocation);
            
            if (closedEyeImg.getHeight() != 0 && openEyeImg.getHeight() != 0){
                
                CropFaceThread cropFaceT = new CropFaceThread(openEyeImg, closedEyeImg);
                
                cropFaceT.start();
                
                CountEyeBlackPixelsThread countEyeBlackPixT = new CountEyeBlackPixelsThread(openEyeImg, closedEyeImg);
                
                CountEyeWhitePixelsThread countEyeWhitePixT = new CountEyeWhitePixelsThread(openEyeImg, closedEyeImg);
                
                countEyeBlackPixT.start();
                countEyeWhitePixT.start();
                cropFaceT.join();
                
                int faceWidth = cropFaceT.getW();
                int faceHeight = cropFaceT.getH();
                
                SymmetricFactorThread symmetricFacT = new SymmetricFactorThread(openEyeImg,cropFaceT.getX(),cropFaceT.getY(),faceWidth,faceHeight);
                
                symmetricFacT.start();
                
                countEyeBlackPixT.join();
                countEyeWhitePixT.join();
                symmetricFacT.join();

                eyeBlackPix = countEyeBlackPixT.getEyePixels();
                eyeWhitePix = countEyeWhitePixT.getEyePixels();
                symmetricFact = symmetricFacT.getSymmericFactor();
                
                double eyeWhiteRatio = ((eyeWhitePix*7.0)/faceWidth) + ((eyeWhitePix*7.0)/faceHeight);
                double eyeBlackRatio = ((eyeBlackPix*10.0)/faceWidth) + ((eyeBlackPix*10.0)/faceHeight);
                double symmetricFactRatio = ((symmetricFact*3.0)/faceWidth) + ((symmetricFact*3.0)/faceHeight);
                
                
                dbConn.registerUser(userName.toString(),String.valueOf(eyeWhiteRatio),String.valueOf(eyeBlackRatio),String.valueOf(symmetricFactRatio));
//                System.out.println("");
//                System.out.println("fact : "+symmetricFact);
//                System.out.println("eyeWhitePix : "+eyeWhitePix);
//                System.out.println("eyeBlackPix : "+eyeBlackPix);
//                System.out.println("faceW : "+faceWidth);
//                System.out.println("faceH : "+faceHeight);

            }
            //System.out.println("time: "+(System.currentTimeMillis()-time));
            return true;
        }catch(Exception e){
            System.out.println(e); 
            return false;
        }
        
        //return false;
    }
    
    public boolean login(String openEyeLocation,String closedEyeLocation,String userName){
        List<String> user = new ArrayList<>();
        long eyeBlackPix = 1;
        long eyeWhitePix = 1;
        long symmetricFact = 1;

        try{
            FileIO fileIO = new FileIO();
            BufferedImage openEyeImg = fileIO.readImage(openEyeLocation);
            BufferedImage closedEyeImg = fileIO.readImage(closedEyeLocation);
            
            if (closedEyeImg.getHeight() != 0 && openEyeImg.getHeight() != 0){
                
                CropFaceThread cropFaceT = new CropFaceThread(openEyeImg, closedEyeImg);
                
                cropFaceT.start();
                
                CountEyeBlackPixelsThread countEyeBlackPixT = new CountEyeBlackPixelsThread(openEyeImg, closedEyeImg);
                
                CountEyeWhitePixelsThread countEyeWhitePixT = new CountEyeWhitePixelsThread(openEyeImg, closedEyeImg);
                
                countEyeBlackPixT.start();
                countEyeWhitePixT.start();
                cropFaceT.join();
                
                int faceWidth = cropFaceT.getW();
                int faceHeight = cropFaceT.getH();
                
                SymmetricFactorThread symmetricFacT = new SymmetricFactorThread(openEyeImg,cropFaceT.getX(),cropFaceT.getY(),faceWidth,faceHeight);
                
                symmetricFacT.start();
                
                countEyeBlackPixT.join();
                countEyeWhitePixT.join();
                symmetricFacT.join();

                eyeBlackPix = countEyeBlackPixT.getEyePixels();
                eyeWhitePix = countEyeWhitePixT.getEyePixels();
                symmetricFact = symmetricFacT.getSymmericFactor();
                
                double eyeWhiteRatio = ((eyeWhitePix*7.0)/faceWidth) + ((eyeWhitePix*7.0)/faceHeight);
                double eyeBlackRatio = ((eyeBlackPix*10.0)/faceWidth) + ((eyeBlackPix*10.0)/faceHeight);
                double symmetricFactRatio = ((symmetricFact*3.0)/faceWidth) + ((symmetricFact*3.0)/faceHeight);
                
                
                user = dbConn.getUser(userName);
                System.out.println(user.toString());
                
                double eyeWhiteRatioDB = Double.valueOf(user.get(0));
                double eyeBlackRatioDB = Double.valueOf(user.get(1));
                double symmetricFactRatioDB = Double.valueOf(user.get(2));
                
                double eyeWhiteRatioFraction = eyeWhiteRatioDB/eyeWhiteRatio;
                double eyeBlackRatioFraction = eyeBlackRatioDB/eyeBlackRatio;
                double symmetricFactRatioFraction = symmetricFactRatioDB/symmetricFactRatio;
                
                double upperBound = 1.4;
                double lowerBound = 0.6;
                
                if ((eyeWhiteRatioFraction<upperBound && eyeWhiteRatioFraction>lowerBound) && (eyeBlackRatioFraction<upperBound && eyeBlackRatioFraction>lowerBound) && (symmetricFactRatioFraction<upperBound && symmetricFactRatioFraction>lowerBound)){
                    return true;
                }

            }
            return false;
        }catch(Exception e){
            System.out.println(e); 
            return false;
        }
    }
    
}
