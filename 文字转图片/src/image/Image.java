package image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Image {
    private BufferedImage bufferedImage;

    //构造函数
    //给出宽高创建新图片
    public Image(int x,int y){
        bufferedImage=new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    }
    //给出File读取图片
    public Image(File file) throws Exception {
        bufferedImage= ImageIO.read(file);
    }

    //设置像素颜色
    public void setRGB(int x,int y,int[] rgb){
        bufferedImage.setRGB(x,y,byteRGB_IntRGB(rgb));
    }
    public void setRGB(int startX,int startY,int w,int h,int[][] rgbArray,int offset,int scansize){
        int[] rgb=new int[rgbArray.length];
        for (int i=0;i<rgb.length;i++){
            rgb[i]=byteRGB_IntRGB(rgbArray[i]);
        }
        bufferedImage.setRGB(startX,startY,w,h,rgb,offset,scansize);
    }

    //获取颜色
    public int[] getRGB(int x,int y){
        int rgb=bufferedImage.getRGB(x,y) & 0xffffff;
        return intRGB_ByteRGB(rgb);
    }

    //输出图片
    public void write(File file) throws Exception{
        ImageIO.write(bufferedImage,"BMP",file);
    }

    //整数rgb和字节rgb的互相转换
    public int[] intRGB_ByteRGB(int rgb){
        return new int[]{(rgb & 0xff0000) >> 16,(rgb & 0xff00) >> 8,(rgb & 0xff)};
    }
    public int byteRGB_IntRGB(int[] rgb){
        return ((rgb[0] << 16)+(rgb[1] << 8)+(rgb[2]));
    }

    //获取宽高
    public int getWight(){
        return bufferedImage.getWidth();
    }
    public int getHeight(){
        return bufferedImage.getHeight();
    }
}
