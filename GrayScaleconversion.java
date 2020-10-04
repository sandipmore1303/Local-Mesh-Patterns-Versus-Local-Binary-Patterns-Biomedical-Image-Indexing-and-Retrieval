/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sa
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
 
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class GrayScaleconversion {
 public  void  GrayScaleconversion() throws IOException, WriteException{
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new java.io.File("."));
      chooser.setDialogTitle("Select Directory");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(false);
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
          System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
      } else {
          System.out.println("No Selection ");
      }

      String filepath = chooser.getSelectedFile().toString();
      
      //create dir for writing generated graphs
         File theDir = new File("GrayScaleconvertedImages");
         // if the directory  exist delete it
         if (theDir.exists()) {
             //System.out.println("creating directory: " + filepath+"\\GeneratedGraphs");
             boolean result = theDir.delete();
             if(result) {    
                 System.out.println("DIR removed");  
             }
         }
         if (!theDir.exists()) {
             //System.out.println("creating directory: " + filepath+"\\GeneratedGraphs");
             boolean result = theDir.mkdir();
             if(result) { System.out.println("DIR created"); 
             }
         }
      
      
      File file = new File(filepath);

      String list[];
       
      //Number number = null;      
      if (file.isDirectory()) {
          System.out.println("Given file is a directory ");
          list = file.list();

          for (int i = 0; i < list.length; i++) {
              String filenamepath = new String(chooser.getSelectedFile() + "\\" + list[i]);
              System.out.println("Current file=" + filenamepath);
              Mat source = Highgui.imread(filenamepath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);//loads color image and saves in matrix form
              //Highgui.imwrite("gray.jpg", source);
              String destfolder=new String(theDir.getAbsolutePath()+"\\"+list[i]);
              System.out.println("Current file=" +destfolder );
              //Mat destination = new Mat(source.rows(), source.cols(), source.type());//desination image matrix
              //Imgproc.GaussianBlur(source, destination, new Size(0, 0), 10);// adds guassian sharp in souce and saves in destination image
              //Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);//parameters of gaussian blur
              Highgui.imwrite(destfolder,source);// writes destination image as sharp.jpg

              //destination = new Mat(600, 800, source.type());
              //Improc.resize();
              //Imgproc.resize(source, destination, destination.size(), 1, 1, Imgproc.INTER_CUBIC);
              //Highgui.imwrite("resize.jpg", destination);
              }
              //System.out.println(source.dump());
          }
      
      
    }      
     
   public static void main( String[] args )
   { //ij.io.OpenDialog oi = null;
     //oi = new ij.io.OpenDialog("Select image", "", "lenna.bmp");
      try{
          
         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );//run time library load
         GrayScaleconversion pp=new GrayScaleconversion();
         pp.GrayScaleconversion();
         
        }catch (Exception e) {System.out.println("Error");
        }
   }
}