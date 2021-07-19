package other;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TestImageMerge {
	public void imageMerge(File file1, File file2, String writePath, int mergeHeight) {
		
		try {
			BufferedImage image1 = ImageIO.read(file1);
			BufferedImage image2 = ImageIO.read(file2);

			int width = Math.max(image1.getWidth(), image2.getWidth());
			int height = image1.getHeight() + image2.getHeight();

			BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();

//			graphics.setBackground(Color.WHITE);
			graphics.setBackground(null);
			graphics.drawImage(image1, 0, 0, null);
			
			int middleWidth = image1.getWidth()/2;
			graphics.drawImage(image2, middleWidth-image2.getWidth()/2, image1.getHeight()-mergeHeight, null);
			
//			ImageIO.write(mergedImage, "gif", new File(writePath + "mergedImage.gif"));
//			ImageIO.write(mergedImage, "jpg", new File(writePath + "mergedImage.gif"));
			ImageIO.write(mergedImage, "png", new File(writePath + "mergedImage.gif"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return ;
		}

		System.out.println("이미지 합성이 완료되었습니다...");
	}
	
	/*
	public void imageMerge(ArrayList<File> fileList, String writePath, int mergeHeight) {
		
		try {
			BufferedImage image1 = ImageIO.read(file1);
			BufferedImage image2 = ImageIO.read(file2);

			int width = Math.max(image1.getWidth(), image2.getWidth());
			int height = image1.getHeight() + image2.getHeight();

			BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();

//			graphics.setBackground(Color.WHITE);
			graphics.drawImage(image1, 0, 0, null);
			
			int middleWidth = image1.getWidth()/2;
			graphics.drawImage(image2, middleWidth-image2.getWidth()/2, image1.getHeight()-mergeHeight, null);
			
//			ImageIO.write(mergedImage, "gif", new File(writePath + "mergedImage.gif"));
//			ImageIO.write(mergedImage, "jpg", new File(writePath + "mergedImage.gif"));
			ImageIO.write(mergedImage, "png", new File(writePath + "mergedImage.gif"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return ;
		}

		System.out.println("이미지 합성이 완료되었습니다...");
	}
	*/
	
}
