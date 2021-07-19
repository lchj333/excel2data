package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import other.CodeImageUtil;
import other.MainFrame;
import other.TestImageMerge;
import other.UpdateDatabaseFromExcel;

public class Controller {
	
	public void doProcess(String file_path, File file1) {
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
			
			excel(file_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		MainFrame frame = new MainFrame();
//		System.out.println(frame.getClass().toString());
//		frame.makeFrame();
	}
	
	/**
	3. QR 생성테스트를 위한 임의의 컨트롤러 호출 

	꼭 컨트롤러가 아니여도 상관없다 핵심은 QRUtil에 들어있는 makeQR이 핵심임
	*/
	public void makeqr(String filePath, String fileName) {
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
	
	public void makebar(String filePath, String fileName) {
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
	
	public void excel(String file_path) throws IOException {
		UpdateDatabaseFromExcel eamCsvLoad = new UpdateDatabaseFromExcel();  //객체 생성	
		String Location = file_path + "excelfile.xlsx";		// 파일의 위치 및 이름	
		ArrayList<ArrayList<String>> list = eamCsvLoad.readFilter(Location);	//파일에서 각 셀들을 읽어서 Arraylist에 저장
		System.out.println("파일 update 시작");
//		eamCsvLoad.excute(list);	//list에 저장되 있는 데이터들을 데이터베이스에 업로드하는 함수 시작
		eamCsvLoad.toText(list, file_path);	//list에 저장되 있는 데이터들을 데이터베이스에 업로드하는 함수 시작
	}

}
