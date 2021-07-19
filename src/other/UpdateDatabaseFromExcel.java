package other;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateDatabaseFromExcel {
	
	public ArrayList<ArrayList<String>> readFilter(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int rowindex = 0;
		int columnindex = 0;		
		ArrayList<ArrayList<String>> filters = new ArrayList<ArrayList<String>>();		
		
		int sheetCn = workbook.getNumberOfSheets();	// 시트 수
		for(int sheetnum=0; sheetnum<sheetCn; sheetnum++) {	// 시트 수만큼 반복
			
			int sheetnum2=sheetnum+1;
			System.out.println("sheet = " + sheetnum2);
			
			XSSFSheet sheet = workbook.getSheetAt(sheetnum);	// 읽어올 시트 선택
			int rows = sheet.getPhysicalNumberOfRows();    // 행의 수
			XSSFRow row = null;
			
			for (rowindex = 1; rowindex < rows; rowindex++) {	// 행의 수만큼 반복

				row = sheet.getRow(rowindex);	// rowindex 에 해당하는 행을 읽는다
				ArrayList<String> filter = new ArrayList<String>();	// 한 행을 읽어서 저장할 변수 선언

				if (row != null) {
					int cells = 13;	// 셀의 수
					cells = row.getPhysicalNumberOfCells();    // 열의 수
					for (columnindex = 0; columnindex <= cells; columnindex++) {	// 열의 수만큼 반복
						XSSFCell cell_filter = row.getCell(columnindex);	// 셀값을 읽는다
						String value = "";
								// 셀이 빈값일경우를 위한 널체크
						if (cell_filter == null) {
							continue;
						} else {
									// 타입별로 내용 읽기
							switch (cell_filter.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								value = cell_filter.getCellFormula();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								value = cell_filter.getNumericCellValue() + "";
								break;
							case XSSFCell.CELL_TYPE_STRING:
								value = cell_filter.getStringCellValue() + "";
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = cell_filter.getBooleanCellValue() + "";
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								value = cell_filter.getErrorCellValue() + "";
								break;
							}
						}
						filter.add(value);	//읽은 셀들을 filter에 추가 (행)
					}
				}
				filters.add(filter); //filter(행)을 filters(열)에 추가
			}
		}
		fis.close();	//파일 읽기 종료
		return filters;	//리스트 반환
	}
	
	/**
	3. toText 함수 : insert 쿼리를 생성해서 readFilter 함수로 읽어왔던 ArrayList 들을 읽어와서 한줄씩 출력하는 함수이다.
	*/
	public void toText(ArrayList<ArrayList<String>> list) throws IOException {
		
		String tableName = "";
		ArrayList<String> tableCulnms = null;
		String dbType = "";
		String insertStr = "INSERT INTO show_attribute_om (agentid, date, site_nm, source_type, title, content, polarity, pos_kw, pos_cnt, neg_kw, neg_cnt, attribute, filter_kw, url) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		ArrayList<String> result = new ArrayList<String>();
		
		for(String str : tableCulnms) {
			if(dbType.equals("ORACLE")) {
				insertStr += "" + ",";
			}else if(dbType.equals("MYSQL")) {
				insertStr += "" + ",";
			}
		}
		
		for (int i = 0; i < tableCulnms.size(); i++) {
			if(i == 0 || i % 1000 == 0) {
				if(dbType.equals("ORACLE")) {
					insertStr += "";
				}else if(dbType.equals("MYSQL")) {
					insertStr += "";
				}
			}
			
		}
		  

		System.out.println("총 라인 수 : "+list.size());
		
		try {
			String temp = ""; 
			String temptitle = "";

			for(int i=0; i<list.size(); i++) {      //매개변수로 받아온 ArrayList 의 길이만큼 반복한다.

	            //읽어온 각 셀들이 자신이 생성해준 table 제약조건과 일치하지 않을 경우 SqlException이 발생한다.
	            //그러한 조건이 발생하면 continue 를 해주는 부분을 추가해주면 된다.
				if(list.get(i).isEmpty()) continue;	//행에 값이 없을 경우에 그 행을 제외하고 진행
				
				//테이블이 utf-8 이라면 이모티콘은 utf-8 형식에 어긋나므로 content, title에서 이모티콘만 제거한다.
				Pattern emoticons = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
				Matcher emoticonsMatcher = emoticons.matcher(temp);
				temp = emoticonsMatcher.replaceAll("");
				emoticonsMatcher=emoticons.matcher(temptitle);
				temptitle = emoticonsMatcher.replaceAll("");
				
				//앞의 쿼리에서 물음표에 들어갈 항목들을 순서대로 기입
				
				//update query 실행
				
				if(i%1000==0) {
					System.out.println(i+"번 라인 쓰는 중...");
				}
			}

			System.out.println("insert를 완료했습니다.");
			   
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	 }
	
	/**
	2. getConn 함수 : 데이터베이스에 연결을 시도하는 함수이다. 본인 ip에 있는 database 에 update 를 하는게 아니라면 localhost 를 변경해주면되고 tablename과 id, pw 도 자신의 데이터베이스 환경설정에 맞게 써주면 된다.
	*/
	private Connection getConn() {
		//이것도 조만간 mybatis로 바꿔야겠다 너무 느리다..
		Connection conn = null;
		String dbUrl = "jdbc:mariadb://localhost/tablename";
		String id = "root";
		String pw = "password";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.getMessage();
			System.out.println("연결되지 않았습니다.");
		} // 드라이버 연결
		  
		try {
			conn = DriverManager.getConnection(dbUrl, id, pw);
			System.out.println("연결에 성공했습니다.");
		} catch (SQLException e){
			e.printStackTrace();
		}
			return conn;
	}
	
	/**
	3. excute 함수 : getConn 함수를 사용해서 데이터베이스에 연결을 하고 insert 쿼리를 생성해서 readFilter 함수로 읽어왔던 ArrayList 들을 읽어와서 한줄씩 insert를 실행해주는 함수이다.
	*/
	public void excute(ArrayList<ArrayList<String>> list) throws IOException {
		  
		Connection  conn  = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO show_attribute_om (agentid, date, site_nm, source_type, title, content, polarity, pos_kw, pos_cnt, neg_kw, neg_cnt, attribute, filter_kw, url) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		System.out.println("총 라인 수 : "+list.size());
		
		try {
			conn = getConn();	//데이터베이스 연결
			pstmt = conn.prepareStatement(query);	//쿼리 설정
			
			String temp = ""; 
			String temptitle = "";

			for(int i=0; i<list.size(); i++) {      //매개변수로 받아온 ArrayList 의 길이만큼 반복한다.

	            //읽어온 각 셀들이 자신이 생성해준 table 제약조건과 일치하지 않을 경우 SqlException이 발생한다.
	            //그러한 조건이 발생하면 continue 를 해주는 부분을 추가해주면 된다.
				if(list.get(i).isEmpty()) continue;	//행에 값이 없을 경우에 그 행을 제외하고 진행
				
				//테이블이 utf-8 이라면 이모티콘은 utf-8 형식에 어긋나므로 content, title에서 이모티콘만 제거한다.
				Pattern emoticons = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
				Matcher emoticonsMatcher = emoticons.matcher(temp);
				temp = emoticonsMatcher.replaceAll("");
				emoticonsMatcher=emoticons.matcher(temptitle);
				temptitle = emoticonsMatcher.replaceAll("");
				
				//앞의 쿼리에서 물음표에 들어갈 항목들을 순서대로 기입
				pstmt.setInt(1, Integer.parseInt(list.get(i).get(0)));
				pstmt.setInt(2, Integer.parseInt(list.get(i).get(1)));
				pstmt.setString(3, list.get(i).get(2));
				pstmt.setString(4, list.get(i).get(3));
				pstmt.setString(5, temptitle);
				pstmt.setString(6, temp);
				pstmt.setInt(7, Integer.parseInt(list.get(i).get(6)));
				pstmt.setString(8, list.get(i).get(7));
				pstmt.setInt(9, Integer.parseInt(list.get(i).get(8)));
				pstmt.setString(10, list.get(i).get(9));
				pstmt.setInt(11, Integer.parseInt(list.get(i).get(10)));
				pstmt.setString(12, list.get(i).get(11));
				pstmt.setString(13, list.get(i).get(12));
				pstmt.setString(14, list.get(i).get(13));
				
				//update query 실행
				pstmt.executeUpdate();
				
				if(i%1000==0) {
					System.out.println(i+"번 라인 쓰는 중...");
				}
			}

			System.out.println("insert를 완료했습니다.");
			   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if(conn  != null) try { conn.close();  } catch (SQLException e) {}
		} // DB 연결에 사용한 객체와 Query수행을 위해 사용한 객체를 닫는다.
	 }

}
