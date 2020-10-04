
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
public class LMeP_8_1 {
   int rows,columns;
   int LMeP_8_1_1[][];
   int LMeP_8_1_2[][];
   int LMeP_8_1_3[][];
   int HISTOGRAM_1[];
   int HISTOGRAM_2[];
   int HISTOGRAM_3[];
   int HISTOGRAM[];

    public LMeP_8_1(Mat image_oi){
        //initailize vars
        rows=image_oi.height();
        columns=image_oi.width();
        LMeP_8_1_1=new int[rows][columns];
        LMeP_8_1_2=new int[rows][columns];
        LMeP_8_1_3=new int[rows][columns];
        HISTOGRAM_1=new int[58];
        HISTOGRAM_2=new int[58];
        HISTOGRAM_3=new int[58];
        HISTOGRAM=new int[58*3];
   } 
    public void LMeP_8_1_1_display(){
        
        for(int m=0;m<rows;m++){
            System.out.println();
            for(int n=0;n<columns;n++){
               
               System.out.print(LMeP_8_1_1[m][n]+"\t");
            }
        
    }
    }
    public void LMeP_8_1_2_display(){
        
        for(int m=0;m<rows;m++){
            System.out.println();
            for(int n=0;n<columns;n++){
               
               System.out.print(LMeP_8_1_2[m][n]+"\t");
            }
        }
    }
    
    public void LMeP_8_1_3_display(){
        
        for(int m=0;m<rows;m++){
            System.out.println();
            for(int n=0;n<columns;n++){
               
               System.out.print(LMeP_8_1_3[m][n]+"\t");
            }
        }
    }
    public void LMeP_8_1_1_computeHistogram(){
        LookupTable_8_1 lt=new LookupTable_8_1();
        int k=0;
        for(int m=0;m<rows;m++){//System.out.println();
            for(int n=0;n<columns;n++){
                
                if(lt.getIndexofPattern(LMeP_8_1_1[m][n])!=-1)
               HISTOGRAM_1[ lt.getIndexofPattern(LMeP_8_1_1[m][n]) ] +=1;
            }
        }
    }
    
    
    
    public void LMeP_8_1_2_computeHistogram(){
        LookupTable_8_1 lt=new LookupTable_8_1();
        int k=0;
        for(int m=0;m<rows;m++){//System.out.println();
            for(int n=0;n<columns;n++){
                
                if(lt.getIndexofPattern(LMeP_8_1_2[m][n])!=-1)
               HISTOGRAM_2[ lt.getIndexofPattern(LMeP_8_1_2[m][n]) ] +=1;
            }
        }
    }
    
    public void LMeP_8_1_3_computeHistogram(){
        LookupTable_8_1 lt=new LookupTable_8_1();
        int k=0;
        for(int m=0;m<rows;m++){//System.out.println();
            for(int n=0;n<columns;n++){
                
                if(lt.getIndexofPattern(LMeP_8_1_3[m][n])!=-1)
               HISTOGRAM_3[ lt.getIndexofPattern(LMeP_8_1_3[m][n]) ] +=1;
            }
        }
    }
    
     public void LMeP_8_1_getHistogram(){
        //LookupTable_8_1 lt=new LookupTable_8_1();
        int k=0;
        for(int m=0;m<58;m++){//System.out.println();
            HISTOGRAM[k] =HISTOGRAM_1[m];  k++;          
        }
        for(int m=0;m<58;m++){//System.out.println();
            HISTOGRAM[k] =HISTOGRAM_2[m];  k++;          
        }
        for(int m=0;m<58;m++){//System.out.println();
            HISTOGRAM[k] =HISTOGRAM_3[m];  k++;          
        }
    }
    public void LMeP_8_1_1_displayHistogram(){
    //System.out.println();
    for(int i=0;i<HISTOGRAM_1.length;i++){
    System.out.println("HISTOGRAM_1["+i+"]="+HISTOGRAM_1[i]);
    }
    }
    public void LMeP_8_1_2_displayHistogram(){
    //System.out.println();
    for(int i=0;i<HISTOGRAM_1.length;i++){
    System.out.println("HISTOGRAM_2["+i+"]="+HISTOGRAM_2[i]);
    }
    }
    public void LMeP_8_1_3_displayHistogram(){
    //System.out.println();
    for(int i=0;i<HISTOGRAM_1.length;i++){
    System.out.println("HISTOGRAM_3["+i+"]="+HISTOGRAM_3[i]);
    }
    }
    public void LMeP_8_1_1_compute(Mat image_oi){      
        
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
                
                int G1[]=new int[8];
                int G2[]=new int[8];
                G1[0]=g[1];G1[1]=g[2];G1[2]=g[3];G1[3]=g[4];
                G1[4]=g[5];G1[5]=g[6];G1[6]=g[7];G1[7]=g[0];
                
                G2[0]=g[0];G2[1]=g[1];G2[2]=g[2];G2[3]=g[3];
                G2[4]=g[4];G2[5]=g[5];G2[6]=g[6];G2[7]=g[7];
                LMeP_8_1_1[m][n]=0;
                for(int k=0;k<g.length;k++){
                    //Math.pow(k, k)
                    //LMeP_8_1[m][n] += (2^(k)*f1(g[k]-(int)gc[0]));
                    LMeP_8_1_1[m][n] += (Math.pow(2.0, k*1.0)*f1(G1[k]-G2[k]));
                    //System.out.println(g[k]+"\t"+LMeP_8_1[m][n]+"\t2^(k)="+(Math.pow(2.0, k*1.0))+"\tf1(g[k]-(int)gc[0])="+f1(g[k]-(int)gc[0]));
                }
                
            }
        }
   } 
    
    public void LMeP_8_1_2_compute(Mat image_oi){      
        
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
                
                int G1[]=new int[8];
                int G2[]=new int[8];
                G1[0]=g[2];G1[1]=g[3];G1[2]=g[4];G1[3]=g[5];
                G1[4]=g[6];G1[5]=g[7];G1[6]=g[0];G1[7]=g[1];
                
                G2[0]=g[0];G2[1]=g[1];G2[2]=g[2];G2[3]=g[3];
                G2[4]=g[4];G2[5]=g[5];G2[6]=g[6];G2[7]=g[7];
                LMeP_8_1_2[m][n]=0;
                for(int k=0;k<g.length;k++){
                    //Math.pow(k, k)
                    //LMeP_8_1[m][n] += (2^(k)*f1(g[k]-(int)gc[0]));
                    LMeP_8_1_2[m][n] += (Math.pow(2.0, k*1.0)*f1(G1[k]-G2[k]));
                    //System.out.println(g[k]+"\t"+LMeP_8_1[m][n]+"\t2^(k)="+(Math.pow(2.0, k*1.0))+"\tf1(g[k]-(int)gc[0])="+f1(g[k]-(int)gc[0]));
                }
                
            }
        }
   } 
   
   public void LMeP_8_1_3_compute(Mat image_oi){      
        
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
                int G1[]=new int[8];
                int G2[]=new int[8];
                G1[0]=g[3];G1[1]=g[4];G1[2]=g[5];G1[3]=g[6];
                G1[4]=g[7];G1[5]=g[0];G1[6]=g[1];G1[7]=g[2];
                
                G2[0]=g[0];G2[1]=g[1];G2[2]=g[2];G2[3]=g[3];
                G2[4]=g[4];G2[5]=g[5];G2[6]=g[6];G2[7]=g[7];
                LMeP_8_1_3[m][n]=0;
                for(int k=0;k<g.length;k++){
                    //Math.pow(k, k)
                    //LMeP_8_1[m][n] += (2^(k)*f1(g[k]-(int)gc[0]));
                    LMeP_8_1_3[m][n] += (Math.pow(2.0, k*1.0)*f1(G1[k]-G2[k]));
                    //System.out.println(g[k]+"\t"+LMeP_8_1[m][n]+"\t2^(k)="+(Math.pow(2.0, k*1.0))+"\tf1(g[k]-(int)gc[0])="+f1(g[k]-(int)gc[0]));
                }
                
            }
        }
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
