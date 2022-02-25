package text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Text {
    private OutputStream textOutputStream;

    public Text(File textFile) throws Exception {
        textOutputStream=new FileOutputStream(textFile);
    }

    public void write(byte[] bytes) throws Exception{
        textOutputStream.write(bytes);
        textOutputStream.flush();
        textOutputStream.close();
    }
}
