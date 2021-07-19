package main;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		String file_path = "C:"+File.separator+"Users"+File.separator+"MSI"+File.separator+"Desktop"+File.separator;  // 이미지경로
		File file1 = new File("C:\\Users\\MSI\\Desktop\\202106_개인경비\\1.jpg");
		
		Controller test = new Controller();
		test.doProcess(file_path, file1);
		
	}
	
}
