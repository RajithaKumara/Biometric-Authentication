/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author rajitha
 */
public class FileIO {
    
    public BufferedImage readImage(String path){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println(e);
        }
        return img;
    }
    
    public void writeImage(BufferedImage bufImg,String path){
        try {
            File outputfile = new File(path);
            ImageIO.write(bufImg, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public ArrayList<String> readTextFile(String fileName) throws FileNotFoundException, IOException{
        FileReader fileReader=new FileReader(fileName);
        BufferedReader textBuffer=new BufferedReader(fileReader);
        ArrayList<String> stringArray=new ArrayList<>();
        
        String textLine;
        while ((textLine=textBuffer.readLine())!=null){
            stringArray.add(textLine);
        }
        textBuffer.close();
        
        return stringArray;
    }
    
    public void writeTextFile(String fileName,String content,boolean append) throws IOException{
        FileWriter fileWriter=new FileWriter(fileName,append);
        PrintWriter printLine=new PrintWriter(fileWriter);
        //printLine.printf("%s"+"%n",content);
        printLine.print(content);
        printLine.close();    
    }
    
}
