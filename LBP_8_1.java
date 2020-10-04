
import ij.ImagePlus;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import ij.process.*;
import java.io.BufferedReader;
import javax.swing.JFileChooser;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
public class LBP_8_1 {
   int rows,columns;
   int LBP_8_1[][];
   int HISTOGRAM[];
    public LBP_8_1(Mat image_oi){
        //initailize vars
        rows=image_oi.height();
        columns=image_oi.width();
        LBP_8_1=new int[rows][columns];
        HISTOGRAM=new int[58];
   } 
    public void LBP_8_1_display(){
        
        for(int m=0;m<rows;m++){
            System.out.println();
            for(int n=0;n<columns;n++){
               
               System.out.print(LBP_8_1[m][n]+"\t");
            }
        
    }
    }
    public void LBP_8_1_computeHistogram(){
        LookupTable_8_1 lt=new LookupTable_8_1();
        int k=0;
        for(int m=0;m<rows;m++){//System.out.println();
            for(int n=0;n<columns;n++){
                
                if(lt.getIndexofPattern(LBP_8_1[m][n])!=-1)
               HISTOGRAM[ lt.getIndexofPattern(LBP_8_1[m][n]) ] +=1;
            }
            }
    }
    public void LBP_8_1_displayHistogram(){
    //System.out.println();
    for(int i=0;i<HISTOGRAM.length;i++){
    System.out.println("HISTOGRAM["+i+"]="+HISTOGRAM[i]);
    }
    }
    public void LBP_8_1_compute(Mat image_oi){
         
        
        
        for(int m=0;m<rows;m++){
            for(int n=0;n<columns;n++){
               //System.out.println("\n");
                double data[] = null;
                int g[]=new int[8]; 
                boolean flag=true;
                flag=true;
                if(check_constrains(m,n+1))
                {
                 data = image_oi.get(m,n+1);
                 g[0]=(int)data[0];
                 
                }
                else
                {  
                    g[0]=0;
                }
                 
                if(check_constrains(m-1,n+1))
                {
                   data = image_oi.get(m-1, n+1); 
                   g[1]=(int)data[0];
                         
                }
                else 
                {g[1]=0;
                }
                 
                if(check_constrains(m-1,n))
                {
                  
                   data = image_oi.get(m-1, n); 
                   g[2]=(int)data[0];
                 
                }
                else
                {g[2]=0;
                }
                
                
                if(check_constrains(m-1,n-1))
                {
                 data = image_oi.get(m-1, n-1);
                 g[3]=(int)data[0];
                }
                else
                {g[3]=0;
                }
                
               
                if(check_constrains(m,n-1))
                {
                 data = image_oi.get(m, n-1);
                 g[4]=(int)data[0];
                }
                else 
                {g[4]=0;
                }
                 
                if(check_constrains(m+1,n-1))
                {
                 data= image_oi.get(m+1, n-1);
                 g[5]=(int)data[0];
                  }
                else
                {g[5]=0;
                }
                 
                if(check_constrains(m+1,n))
                {
                 data = image_oi.get(m+1, n);
                 g[6]=(int)data[0];           
                }
                else
                {g[6]=0;
                }
                
                 
                if(check_constrains(m+1,n+1))
                {
                data= image_oi.get(m+1, n+1);
                g[7]=(int)data[0];
                             
                }
                else
                {g[7]=0;
                }
                double gc[] = image_oi.get(m, n);
                LBP_8_1[m][n]=0;
                for(int k=0;k<g.length;k++){
                    //Math.pow(k, k)
                    //LBP_8_1[m][n] += (2^(k)*f1(g[k]-(int)gc[0]));
                    LBP_8_1[m][n] += (Math.pow(2.0, k*1.0)*f1(g[k]-(int)gc[0]));
                    //System.out.println(g[k]+"\t"+LBP_8_1[m][n]+"\t2^(k)="+(Math.pow(2.0, k*1.0))+"\tf1(g[k]-(int)gc[0])="+f1(g[k]-(int)gc[0]));
                }
                
            }
        }
   } 
    public int LBP_8_1_compute(int m,int n){
       return 0;
         
   } 
    public int f1(int x){
        if(x>=0) return 1;
        else return 0;
    }
    public boolean check_constrains(int x,int y){
       
        if((x>=0 && x<rows) && (y>=0 && y<columns))
        { //System.out.println("x="+x+"y="+y+" true");
            return true;
        }
        else 
        {//System.out.println("x="+x+"y="+y+" false");
            return false;
        }
    }
    
    
}
