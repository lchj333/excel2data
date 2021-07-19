package other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.krysalis.barcode4j.BarcodeClassResolver;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;

/**
 * 바코드를 이미지파일로 출력
 * @author kdarkdev
 */
public class Barcode4j {
	
    public Barcode4j(String dir) {
    	init(dir);
	}

    /**
	 * 바코드 생성전 데이터 초기화
	 * @param g
	 */
	private static void init(String dir) {
		/* 바코드 타입 
    	 * "codabar", "code39", "postnet", "intl2of5", "ean-128"
    	 * "royal-mail-cbc", "ean-13", "itf-14", "datamatrix", "code128"
    	 * "pdf417", "upc-a", "upc-e", "usps4cb", "ean-8", "ean-13" */
    	String barcodeType = "code128";
    	
    	/* 바코드 데이터 */
    	String barcodeData = "100000000001";
    	
    	/* 이미지의 dpi */
    	final int dpi = 203;
    	
    	/* 이미지 파일 포맷 
    	 * SVG, EPS, TIFF, JPEG, PNG, GIF, BMP */
    	String fileFormat = "jpg";
    	
    	/* 출력될 파일 */
//    	String dir = "C:/";
    	String fileName = "barcodetest_"+barcodeType;
    	String outputFile = dir + fileName+"."+fileFormat;
    	
    	/* anti-aliasing */
    	boolean isAntiAliasing = false;
    	
    	/* 이미지 생성 */
    	createBarcode(barcodeType, barcodeData, fileFormat, isAntiAliasing, dpi, outputFile);
	}
    
    /**
     * 바코드 생성
     * @param barcodeType
     * @param barcodeData
     * @param dpi
     */
    private static void createBarcode(String barcodeType, String barcodeData, String fileFormat, boolean isAntiAliasing, int dpi, String outputFile){
    	try {
        	AbstractBarcodeBean bean = null;

        	BarcodeClassResolver resolver = new DefaultBarcodeClassResolver();
        	Class clazz = resolver.resolveBean(barcodeType);
        	bean = (AbstractBarcodeBean)clazz.newInstance();
            bean.doQuietZone(true);
            
            //Open output file
            OutputStream out = new FileOutputStream(new File(outputFile));
            try {
                //Set up the canvas provider for monochrome JPEG output 
            	String mimeType = MimeTypes.expandFormat(fileFormat);
            	int imageType   = BufferedImage.TYPE_BYTE_BINARY;
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, mimeType, dpi, imageType, isAntiAliasing, 0);
                
                //Generate the barcode
                bean.generateBarcode(canvas, barcodeData);
                
                //Signal end of generation
                canvas.finish();
                
                System.out.println("create image success.");
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

