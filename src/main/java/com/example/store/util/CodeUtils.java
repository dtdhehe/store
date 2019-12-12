package com.example.store.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/10 23:38
 * @description
 **/
public class CodeUtils {

    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.CODE_39, codeWidth, height, null);
            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 条形码解码
     *
     * @param file
     * @return String
     */
    public static String decode(File file) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(file);
            if (image == null) {
                System.out.println("the decode image may be not exit.");
                return "";
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //生成10张13位随机数的条形码
//        for (int i = 0;i<10;i++){
//            long num = (long)(Math.random() * (9999999999999L - 1000000000000L)) + 1000000000000L;
//            String msg = Long.toString(num);
//            String path = "D:\\uploads\\barcode2\\"+msg+".png";
//            int width = 105, height = 50;
//            encode(msg, width, height, path);
//        }
//        System.out.println("finished encode.");
//        String decodeContent = decode("D:\\uploads\\barcode2\\1276694977588.png");
//        System.out.println("解码内容如下：" + decodeContent);
//        System.out.println("finished decode.");
    }

}
