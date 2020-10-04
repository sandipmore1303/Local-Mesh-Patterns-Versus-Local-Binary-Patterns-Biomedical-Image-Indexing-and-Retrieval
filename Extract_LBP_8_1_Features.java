
import java.io.BufferedReader;
import java.io.File;
import javax.swing.JFileChooser;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import jxl.*;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.write.*; 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
public class Extract_LBP_8_1_Features {
    
  public    Extract_LBP_8_1_Features() throws IOException, WriteException{
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
      File file = new File(filepath);

      String list[];
      String sCurrentLine;
      BufferedReader br = null;
      WritableWorkbook workbook = Workbook.createWorkbook(new File("LBP_8_1_Features.xls")); 
      WritableSheet sheet = workbook.createSheet("Features", 0); 
      //Number number = null;      
      if (file.isDirectory()) {
          System.out.println("Given file is a directory ");
          list = file.list();

          for (int i = 0; i < list.length; i++) {
              String filenamepath = new String(chooser.getSelectedFile() + "\\" + list[i]);
              System.out.println("Current file=" + filenamepath);

              Mat source = Highgui.imread(filenamepath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);//loads color image and saves in matrix form
              LBP_8_1 lbp = new LBP_8_1(source);
              lbp.LBP_8_1_compute(source);
              //lbp.LBP_8_1_display();
              lbp.LBP_8_1_computeHistogram();
              lbp.LBP_8_1_displayHistogram();
              for(int ws_c=0;ws_c<lbp.HISTOGRAM.length;ws_c++){
                jxl.write.Number number = new jxl.write.Number(ws_c, i,lbp.HISTOGRAM[ws_c]);
                sheet.addCell(number); 
              }
              //System.out.println(source.dump());
          }
      }
      workbook.write();
      workbook.close(); 
    }
    public static void main(String[] ar) throws IOException, WriteException{
     System.loadLibrary( Core.NATIVE_LIBRARY_NAME );//run time library load
     Extract_LBP_8_1_Features elpbf=new Extract_LBP_8_1_Features();
      
    
    //File f_oi;
    //ij.io.OpenDialog oi =new ij.io.OpenDialog("Select stego image",                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",                  "lenna.bmp");
    
  
  
   
      
     
          
         //
         //
        // Mat source = Highgui.imread(oi.getDirectory() + oi.getFileName(),Highgui.CV_LOAD_IMAGE_GRAYSCALE);//loads color image and saves in matrix form
          
          // source = new Mat(3, 3, CvType.CV_8UC1, new Scalar(1));

        
        
        // Extract_LBP_8_1_Features();
       }  
}
