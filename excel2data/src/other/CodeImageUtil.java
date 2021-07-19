package other;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CodeImageUtil {
	/**
	 * QR코드 유틸
	 * @param url : QR에 작성할 URL이다 
	 * @param width : QR 이미지 가로사이즈
	 * @param height : QR 이미지 세로사이즈
	 * @param file_path : 생성할파일의 디렉토리경로
	 * @param file_name : 생성할 파일의 파일명
	 */
	public static void makeQR(String url, int width, int height, String file_path, String file_name){
		try {
			File file = null;
			
			file = new File(file_path);
			if(!file.exists()) {
				file.mkdirs();
			}
			QRCodeWriter writer = new QRCodeWriter();
			url = new String(url.getBytes("UTF-8"), "ISO-8859-1");
	        BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE,width, height);
	        //QR코드 색상
//	        int qrColor	=	0xFF2e4e96;
	        int qrColor	=	0;
	        MatrixToImageConfig config = new MatrixToImageConfig(qrColor,0xFFFFFFFF);
	        BufferedImage qrImage	= MatrixToImageWriter.toBufferedImage(matrix,config);
	        
	        int idx = file_name.lastIndexOf(".");
			ImageIO.write(qrImage, file_name.substring(idx+1), new File(file_path + file_name));	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 바코드 유틸
	 * @param text : 바코드에 적용할 텍스트이다 
	 * @param width : 바코드 이미지 가로사이즈
	 * @param height : 바코드 이미지 세로사이즈
	 * @param file_path : 생성할파일의 디렉토리경로
	 * @param file_name : 생성할 파일의 파일명
	 */
	public static void makeBar(String text, int width, int height, String file_path, String file_name){
		try {
			File file = null;
			
			file = new File(file_path);
			if(!file.exists()) {
				file.mkdirs();
			}
			MultiFormatWriter barcode = new MultiFormatWriter();    // 바코드
			text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
	        BitMatrix bitMatrix = barcode.encode(text, BarcodeFormat.CODE_128, width, height);
	        //바코드 색상
//	        int barColor	=	0xFF2e4e96;
	        int barColor	=	0;
	        MatrixToImageConfig config = new MatrixToImageConfig(barColor, 0xFFFFFFFF);
	        BufferedImage barImage	= MatrixToImageWriter.toBufferedImage(bitMatrix,config);
	        
	        int idx = file_name.lastIndexOf(".");
	        ImageIO.write(barImage, file_name.substring(idx+1), new File(file_path + file_name));	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
