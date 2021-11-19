package com.duapps.affair.demo.util;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * @Author he.zhou
 * @Date 2021-11-17
 */
public class PdfUtils {

    /**
     * 生成一本书的缩略图
     *
     * @param inputFile  需要生成缩略图的书籍的完整路径
     * @param outputFile 生成缩略图的放置路径
     */
    public static void generateBookIamge(String inputFile, String outputFile) {
        Document document = null;

        try {
            float rotation = 0f;
            // 缩略图显示倍数，1表示不缩放，0.5表示缩小到50%
            float zoom = 0.8f;

            document = new Document();
            document.setFile(inputFile);
            System.out.println(document.getPageTree().getNumberOfPages());


            BufferedImage bufferedImage = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN,
                    Page.BOUNDARY_CROPBOX, rotation, zoom);

            Iterator iterator = ImageIO.getImageWritersBySuffix("jpg");
            ImageWriter writer = (ImageWriter) iterator.next();

            FileOutputStream out = new FileOutputStream(outputFile);
            ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
            writer.setOutput(outImage);
            writer.write(new IIOImage(bufferedImage, null, null));

        } catch (Exception e) {
            System.out.println("生成书的缩略图失败 : " + inputFile);
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        PdfUtils.generateBookIamge("/Users/admin/Downloads/千本内购.pdf", "/Users/admin/Downloads/千本内购.jpg");
    }
}
