package com.controlstest;

import org.bytedeco.javacpp.indexer.UShortIndexer;
import org.bytedeco.opencv.opencv_core.Mat;


//16 Bit Histogram Equalziation in opencv, only works on single channel 16bit images will throw errors otherwise
public class EqualizeHist16Bit {
    static Mat equalizeHist16bit(Mat image){
        int hist[] = new int[65535];
        UShortIndexer ind = image.createIndexer();
        for(int x = 0; x < ind.size(0); x++){
            for(int y = 0; y < ind.size(1); y++){
                int[] pixel = new int[1];
                ind.get(x,y,pixel);
                hist[pixel[0]] = hist[pixel[0]] +1; 
            }
        }
       for ( int i = 1; i < 65535; ++i ){
                hist[i] =  hist[i-1] + hist[i];
        }

        int max = hist[hist.length -1];

        for(int i = 0; i < hist.length; i++){
            hist[i] = (int) Math.round(hist[i] * (65535.0) / max);
        }
        for(int x = 0; x < ind.size(0); x++){
            for(int y = 0; y < ind.size(1); y++){
                int[] pixel = new int[1];
                ind.get(x,y,pixel);
                ind.put(x,y,hist[pixel[0]]);
            }   
        }

        

        return image;
    }

}


