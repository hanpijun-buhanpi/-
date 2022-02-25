package tti;

import image.Image;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TextTransformationImage1 implements TextTransformationImage{
    @Override
    public void startTransformation(String str, Image image) {
        byte[] bytes=stringToByte(str);
        int[][] headerData=headerData(bytes.length,image);
        int[][] rgb=byteRGB_To_IntRGB(bytes);
        dataOutput(headerData,rgb,image);
    }

    //String转byte
    private byte[] stringToByte(String str){
        return str.getBytes(StandardCharsets.UTF_8);
    }

    //保存序号（2个字节），保存字节长度（4个字节），预留特殊加密数字（3个字节）
    private int[][] headerData(int byteLength,Image image){
        int byteLength1=(byteLength & 0xff000000) >> 24;
        int byteLength2=(byteLength & 0xff0000) >> 16;
        int byteLength3=(byteLength & 0xff00) >> 8;
        int byteLength4=(byteLength & 0xff);
        int[] rgb1=new int[]{0,1,byteLength1};
        int[] rgb2=new int[]{byteLength2,byteLength3,byteLength4};
        int[] rgb3=new int[]{0,0,0};
        return new int[][]{rgb1,rgb2,rgb3};
    }

    //byte数据转intRGB数组
    private int[][] byteRGB_To_IntRGB(byte[] bytes){
        int[][] rgb;
        if (bytes.length%3==0){
            rgb=new int[bytes.length/3][];
        } else {
            rgb=new int[bytes.length/3+1][];
        }
        for (int i=0;i<rgb.length;i++){
            int j=i*3;
            int z=bytes.length-j;
            int rgb1=0,rgb2=0,rgb3=0;
            if (z/3!=0){
                rgb1=b_to_i(bytes[j]);
                rgb2=b_to_i(bytes[j+1]);
                rgb3=b_to_i(bytes[j+2]);
            } else if (z%3==2){
                rgb1=b_to_i(bytes[j]);
                rgb2=b_to_i(bytes[j+1]);
                rgb3=getRandom();
            } else if (z%3==1){
                rgb1=b_to_i(bytes[j]);
                rgb2=getRandom();
                rgb3=getRandom();
            }
            rgb[i]=new int[]{rgb1,rgb2,rgb3};
        }
        return rgb;
    }
    private int b_to_i(byte b){
        int i=0;
        if (b>-1){
            i=b;
        } else {
            i=(1<<7)+(b&127);
        }
        return i;
    }

    //将rgb信息注入图片
    private void dataOutput(int[][] headerData,int[][] rgb,Image image){
        ArrayList<int[]> rgbList=new ArrayList<>();
        rgbList.addAll(Arrays.asList(headerData));
        rgbList.addAll(Arrays.asList(rgb));
        int w=image.getWight();
        int h=image.getHeight();
        int c=0;
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                if (c<rgbList.size()){
                    image.setRGB(x,y,rgbList.get(c));
                } else {
                    image.setRGB(x,y,getRandomRGB());
                }
                c++;
            }
        }
    }

    //生成0~255的随机数，和生成随机rgb
    private int getRandom(){
        return new Random().nextInt(255);
    }
    private int[] getRandomRGB(){
        return new int[]{getRandom(),getRandom(),getRandom()};
    }
}
