 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException;
import java.util.Arrays; 
import javax.imageio.ImageIO; 
import javax.swing.JFileChooser;
import jxl.write.WriteException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
public class KMeans { 
    BufferedImage original; 
    BufferedImage result; 
    Cluster[] clusters; 
    public static final int MODE_CONTINUOUS = 1; 
    public static final int MODE_ITERATIVE = 2; 
     
     
    public static void main(String[] args) { 
        
       System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
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
         File theDir = new File("K-Means_out_Images");
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
              Mat source = Highgui.imread(filenamepath, Highgui.CV_LOAD_IMAGE_COLOR);//loads color image and saves in matrix form
              //Highgui.imwrite("gray.jpg", source);
              String destfolder = new String(theDir.getAbsolutePath() + "\\" + list[i]);
              System.out.println("Current file=" + destfolder);
               
              String ARGS[] = new String[4];
              ARGS[0] = filenamepath;
              ARGS[1] = destfolder;
              ARGS[2] = "255";
              ARGS[3] = "-i";

              String src = ARGS[0];
              String dst = ARGS[1];
              int k = Integer.parseInt(ARGS[2]);
              String m = ARGS[3];

              // create new KMeans object 
              KMeans kmeans = new KMeans();
              // call the function to actually start the clustering 
              BufferedImage dstImage = kmeans.calculate(loadImage(src),
                      k);
              // save the resulting image 
              saveImage(dst, dstImage);
              }
              //System.out.println(source.dump());
          }
      
      
    
        //ij.io.OpenDialog ti = null;
        //ti = new ij.io.OpenDialog("Select image", "E:\\DATA\\YES_MAN\\JAVA_IMPL\\", "lenna.bmp");
        
    } 
     
    public KMeans() {    } 
     
    public BufferedImage calculate(BufferedImage image,  
                                            int k) { 
        long start = System.currentTimeMillis(); 
        int mode=MODE_CONTINUOUS;
        int w = image.getWidth(); 
        int h = image.getHeight(); 
        // create clusters 
        clusters = createClusters(image,k); 
        // create cluster lookup table 
        int[] lut = new int[w*h]; 
        Arrays.fill(lut, -1); 
         
        // at first loop all pixels will move their clusters 
        boolean pixelChangedCluster = true; 
        // loop until all clusters are stable! 
        int loops = 0; 
        while (pixelChangedCluster) { 
            pixelChangedCluster = false; 
            loops++; 
            for (int y=0;y<h;y++) { 
                for (int x=0;x<w;x++) { 
                    int pixel = image.getRGB(x, y); 
                    Cluster cluster = findMinimalCluster(pixel); 
                    if (lut[w*y+x]!=cluster.getId()) { 
                        // cluster changed 
                        if (mode==MODE_CONTINUOUS) { 
                            if (lut[w*y+x]!=-1) { 
                                // remove from possible previous  
                                // cluster 
                                clusters[lut[w*y+x]].removePixel( 
                                                            pixel); 
                            } 
                            // add pixel to cluster 
                            cluster.addPixel(pixel); 
                        } 
                        // continue looping  
                        pixelChangedCluster = true; 
                     
                        // update lut 
                        lut[w*y+x] = cluster.getId(); 
                    } 
                } 
            } 
            if (mode==MODE_ITERATIVE) { 
                // update clusters 
                for (int i=0;i<clusters.length;i++) { 
                    clusters[i].clear(); 
                } 
                for (int y=0;y<h;y++) { 
                    for (int x=0;x<w;x++) { 
                        int clusterId = lut[w*y+x]; 
                        // add pixels to cluster 
                        clusters[clusterId].addPixel( 
                                            image.getRGB(x, y)); 
                    } 
                } 
            } 
             
        } 
        // create result image 
        BufferedImage result = new BufferedImage(w, h,  
                                    BufferedImage.TYPE_INT_RGB); 
        for (int y=0;y<h;y++) { 
            for (int x=0;x<w;x++) { 
                int clusterId = lut[w*y+x]; 
                result.setRGB(x, y, clusters[clusterId].getRGB()); 
            } 
        } 
        long end = System.currentTimeMillis(); 
        System.out.println("Clustered to "+k 
                            + " clusters in "+loops 
                            +" loops in "+(end-start)+" ms."); 
        return result; 
    } 
     
    public Cluster[] createClusters(BufferedImage image, int k) { 
        // Here the clusters are taken with specific steps, 
        // so the result looks always same with same image. 
        // You can randomize the cluster centers, if you like. 
        Cluster[] result = new Cluster[k]; 
        int x = 0; int y = 0; 
        int dx = image.getWidth()/k; 
        int dy = image.getHeight()/k; 
        for (int i=0;i<k;i++) { 
            result[i] = new Cluster(i,image.getRGB(x, y)); 
            x+=dx; y+=dy; 
        } 
        return result; 
    } 
     
    public Cluster findMinimalCluster(int rgb) { 
        Cluster cluster = null; 
        int min = Integer.MAX_VALUE; 
        for (int i=0;i<clusters.length;i++) { 
            int distance = clusters[i].distance(rgb); 
            if (distance<min) { 
                min = distance; 
                cluster = clusters[i]; 
            } 
        } 
        return cluster; 
    } 
     
    public static void saveImage(String filename,  
            BufferedImage image) { 
        File file = new File(filename); 
        try { 
            ImageIO.write(image, "png", file); 
        } catch (Exception e) { 
            System.out.println(e.toString()+" Image '"+filename 
                                +"' saving failed."); 
        } 
    } 
     
    public static BufferedImage loadImage(String filename) { 
        BufferedImage result = null; 
        try { 
            result = ImageIO.read(new File(filename)); 
        } catch (Exception e) { 
            System.out.println(e.toString()+" Image '" 
                                +filename+"' not found."); 
        } 
        return result; 
    } 
     
    class Cluster { 
        int id; 
        int pixelCount; 
        int red; 
        int green; 
        int blue; 
        int reds; 
        int greens; 
        int blues; 
         
        public Cluster(int id, int rgb) { 
            int r = rgb>>16&0x000000FF;  
            int g = rgb>> 8&0x000000FF;  
            int b = rgb>> 0&0x000000FF;  
            red = r; 
            green = g; 
            blue = b; 
            this.id = id; 
            addPixel(rgb); 
        } 
         
        public void clear() { 
            red = 0; 
            green = 0; 
            blue = 0; 
            reds = 0; 
            greens = 0; 
            blues = 0; 
            pixelCount = 0; 
        } 
         
        int getId() { 
            return id; 
        } 
         
        int getRGB() { 
            int r = reds / pixelCount; 
            int g = greens / pixelCount; 
            int b = blues / pixelCount; 
            return 0xff000000|r<<16|g<<8|b; 
        } 
        void addPixel(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;  
            reds+=r; 
            greens+=g; 
            blues+=b; 
            pixelCount++; 
            red   = reds/pixelCount; 
            green = greens/pixelCount; 
            blue  = blues/pixelCount; 
        } 
         
        void removePixel(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;  
            reds-=r; 
            greens-=g; 
            blues-=b; 
            pixelCount--; 
            red   = reds/pixelCount; 
            green = greens/pixelCount; 
            blue  = blues/pixelCount; 
        } 
         
        int distance(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;  
            int rx = Math.abs(red-r); 
            int gx = Math.abs(green-g); 
            int bx = Math.abs(blue-b); 
            int d = (rx+gx+bx) / 3; 
            return d; 
        } 
    } 
     
} 