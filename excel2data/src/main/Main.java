package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.html.ImageView;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;

import other.Barcode4j;
import other.CodeImageUtil;
import other.MainFrame;
import other.TestImageMerge;
import other.UpdateDatabaseFromExcel;

public class Main {
	public static void main(String[] args) {
		String file_path = "C:"+File.separator+"Users"+File.separator+"MSI"+File.separator+"Desktop"+File.separator;  // 이미지경로
		File file1 = new File("C:\\Users\\MSI\\Desktop\\202106_개인경비\\1.jpg");
		
		try {
//			makeqr(file_path, "testQr.jpg");
//			File file2 = new File(file_path + "testQr.jpg");
			
//			makebar(file_path, "testBar.jpg");
//			File file3 = new File(file_path + "testBar.jpg");
			
//			new Barcode4j(file_path);
//			File file4 = new File(file_path + "barcodetest_code128.jpg");
			
//			TestImageMerge tim1 = new TestImageMerge();
//			tim1.imageMerge(file1, file2, file_path, 250);
			
//			TestImageMerge tim2 = new TestImageMerge();
//			tim2.imageMerge(file1, file3, file_path, 250);
			
//			excel(file_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MainFrame frame = new MainFrame();
		System.out.println(frame.getClass().toString());
		frame.makeFrame();
		
	}
	
	
	/**
	3. QR 생성테스트를 위한 임의의 컨트롤러 호출 

	꼭 컨트롤러가 아니여도 상관없다 핵심은 QRUtil에 들어있는 makeQR이 핵심임
	*/
	public static void makeqr(String filePath, String fileName) {
		String url = "http://roqkffhwk.tistory.com";
		int width = 100;
		int height = 100;
		
		if(!StringUtils.isEmpty(fileName)) 
		{
			int idx = fileName.lastIndexOf(".");
			System.out.println("ext : " + fileName.substring(idx));
			if(idx == -1) {
				fileName = fileName + ".png";
				System.out.println("fileName : " + fileName);
			}
		} else {
			fileName = "test.png";
			System.out.println("fileName : " + fileName);
		}
		
		CodeImageUtil.makeQR(url, width, height, filePath, fileName);
	}
	
	public static void makebar(String filePath, String fileName) {
		String text = "123412341234";
		int width = 0;
		int height = 50;
		
		if(!StringUtils.isEmpty(fileName)) 
		{
			int idx = fileName.lastIndexOf(".");
			System.out.println("ext : " + fileName.substring(idx));
			if(idx == -1) {
				fileName = fileName + ".png";
				System.out.println("fileName : " + fileName);
			}
		} else {
			fileName = "test.png";
			System.out.println("fileName : " + fileName);
		}
		
		CodeImageUtil.makeBar(text, width, height, filePath, fileName);
	}
	
	public static void excel(String file_path) throws IOException {
		System.out.println("main 시작");
		UpdateDatabaseFromExcel eamCsvLoad = new UpdateDatabaseFromExcel();  //객체 생성	
		String Location = file_path + "excelfile.xlsx";		// 파일의 위치 및 이름	
		ArrayList<ArrayList<String>> list = eamCsvLoad.readFilter(Location);	//파일에서 각 셀들을 읽어서 Arraylist에 저장
		System.out.println("파일 update 시작");
		eamCsvLoad.excute(list);	//list에 저장되 있는 데이터들을 데이터베이스에 업로드하는 함수 시작
	}
	
}


