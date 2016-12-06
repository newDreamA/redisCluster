package com.txw.util;


import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;

import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by tangxiewen on 2016/12/5.
 */
public class PDFUtil  {
    private static List<BufferedImage> deleteImgs = new ArrayList<BufferedImage>();

    static{
        try {
            BufferedImage delImg1 = ImageIO.read(new File("d:\\delImg1.png"));
            BufferedImage delImg2 = ImageIO.read(new File("d:\\delImg2.png"));
            BufferedImage delImg3 = ImageIO.read(new File("d:\\delImg3.png"));



            deleteImgs.add(delImg1);
            deleteImgs.add(delImg2);
            deleteImgs.add(delImg3);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

   public static void main(String[] args)throws Exception {
        new PDFUtil().resetPage("D:/download/route2.pdf","D:/notify5.pdf");
    }

    /**
     * 去除PDF里特定的图片
     * @param src
     * @param dest
     * @throws Exception
     */
    public void resetPage(String src,String dest)throws Exception{
        PDDocument document = null;
        PDDocument newDcoument =null;
        try {

            document = PDDocument.load( new File(src) );

            long start = System.currentTimeMillis();
            newDcoument = new PDDocument();
            for( PDPage page : document.getPages() ) {

                PDResources resources =  page.getResources();
                Iterable<COSName> cosNames =resources.getXObjectNames();
                Iterator cosNamesIt = cosNames.iterator();
                while (cosNamesIt.hasNext()){
                    COSName   cosName = (COSName)cosNamesIt.next();
                    PDXObject xobject = resources.getXObject( cosName );
                    if( xobject instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject)xobject;
                        int imageWidth = image.getWidth();
                        int imageHeight = image.getHeight();

                        System.out.println("*******************************************************************");

                        BufferedImage im =image.getImage();
                        for(BufferedImage localImage:deleteImgs ){
                            ImageComparer imageCom = new ImageComparer(localImage, im);
                            double similarity=imageCom.modelMatch();
                            if(similarity>0.95){
                                System.out.println("图片相似");

                                BufferedImage tmpIm = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
                                PDImageXObject tmpImg =LosslessFactory.createFromImage(newDcoument,tmpIm);
                                resources.put(cosName, tmpImg);

                            }
                        }


                    }
                }
                newDcoument.addPage(page);
            }
            newDcoument.save(dest);
            long end = System.currentTimeMillis();
            System.out.println("time:"+(end-start));
        } finally {
            if(newDcoument!=null){
                newDcoument.close();
            }
            if( document != null ) {
                document.close();
            }

        }
    }

    /**
     *  读取PDF里的图片
     * @param src
     * @param dest
     * @throws Exception
     */
    public void generateImgs(String src,String dest)throws Exception{
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(src));
            for( PDPage page : document.getPages() ) {
                PDResources resources =  page.getResources();
                Iterable<COSName> cosNames =resources.getXObjectNames();
                Iterator cosNamesIt = cosNames.iterator();
                while (cosNamesIt.hasNext()) {
                    COSName cosName = (COSName) cosNamesIt.next();
                    PDXObject xobject = resources.getXObject(cosName);


                    if( xobject instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject) xobject;
                        BufferedImage im =image.getImage();
                        File f=new File(dest+"/"+cosName.getName()+".png");
                        try {
                            ImageIO.write(im, "png",f);
                            im.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }



                }
            }
        }finally {

            if( document != null ) {
                document.close();
            }

        }

    }

}
