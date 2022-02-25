import image.Image;
import itt.ImageTransformationText;
import itt.ImageTransformationText1;
import tti.TextTransformationImage;
import tti.TextTransformationImage1;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        File textInputPath=new File("E:\\test\\三体-黑暗森林.txt");
        File textOutputPath=new File("E:\\test\\三体-黑暗森林1.txt");
        File imagePath=new File("E:\\test\\testTTI1.bmp");

        tti(textInputPath,imagePath);
//        itt(imagePath,textOutputPath);
    }

    public static void tti(File text,File imagePath) throws Exception {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(text));
        String str;
        StringBuffer stringBuffer=new StringBuffer();
        while((str=bufferedReader.readLine())!=null){
            stringBuffer.append(str).append("\r\n");
        }
        str=stringBuffer.toString();
        bufferedReader.close();

        int strLength=str.getBytes(StandardCharsets.UTF_8).length+9;
        strLength=strLength%3==0?strLength/3:strLength/3+1;
        double g=Math.sqrt(strLength);
        int wh=0;
        for (int i=0;i<strLength;i++){
            if (i==g){
                wh=(int) g;
                break;
            } else if (i>g){
                wh=(int) g+1;
                break;
            }
        }
        Image image=new Image(wh,wh);
        TextTransformationImage tti=new TextTransformationImage1();
        tti.startTransformation(str,image);
        image.write(imagePath);
    }

    public static void itt(File imagePath,File textPath) throws Exception{
        Image image=new Image(imagePath);
        ImageTransformationText itt=new ImageTransformationText1();
        itt.startTransformation(image,textPath);
    }
}
