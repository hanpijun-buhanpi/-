package itt;

import image.Image;
import text.Text;

import java.io.File;

public class ImageTransformationText1 implements ImageTransformationText{
    @Override
    public void startTransformation(Image image, File textFile) {
        int[][] rgb=getIntRGB(image);
        int byteLength=getByteLength(new int[][]{rgb[0],rgb[1]});
        rgb=getIntRGB(byteLength,rgb);
        byte[] bytes=rgb_byte(byteLength,rgb);
        try {
            output(bytes,textFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取rgb信息
    private int[][] getIntRGB(Image image){
        int w=image.getWight(),h=image.getHeight();
        int[][] rgb=new int[w*h][];
        int count=0;
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                rgb[count]=image.getRGB(x,y);
                count++;
            }
        }
        return rgb;
    }
    //裁剪信息，读取有效数据
    private int[][] getIntRGB(int byteLength,int[][] rgb){
        int[][] result=new int[byteLength%3>0?byteLength/3+1:byteLength/3][];
        System.arraycopy(rgb, 3, result, 0, result.length);
        return result;
    }
    //读取有效字节长度信息
    private int getByteLength(int[][] rgb){
        int result=((rgb[0][2] & 0xff) << 24);
        result+=((rgb[1][0] & 0xff) << 16) + ((rgb[1][1] & 0xff) << 8) + (rgb[1][2] & 0xff);
        return result;
    }
    //rgb转byte数组
    private byte[] rgb_byte(int byteLength,int[][] rgb){
        byte[] result=new byte[byteLength];
        if (byteLength==rgb.length*3){
            int i=0;
            for (int[] ints:rgb){
                result[i]=(byte) (ints[0] & 0xff);
                result[i+1]=(byte) (ints[1] & 0xff);
                result[i+2]=(byte) (ints[2] & 0xff);
                i+=3;
            }
        } else {
            for (int i=0;i<rgb.length;i++){
                if ((i+1)!=rgb.length){
                    result[i*3]=(byte) (rgb[i][0] & 0xff);
                    result[i*3+1]=(byte) (rgb[i][1] & 0xff);
                    result[i*3+2]=(byte) (rgb[i][2] & 0xff);
                } else{
                    if ((rgb.length*3-byteLength)==1){
                        result[i*3]=(byte) (rgb[i][0] & 0xff);
                        result[i*3+1]=(byte) (rgb[i][1] & 0xff);
                    } else if ((rgb.length*3-byteLength)==2){
                        result[i*3]=(byte) (rgb[i][0] & 0xff);
                    }
                }
            }
        }
        return result;
    }
    //输出文件
    private void output(byte[] bytes,File file) throws Exception{
        Text text=new Text(file);
        text.write(bytes);
    }
}
