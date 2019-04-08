package com.walmart.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.walmart.model.MyExcel;
/** 
 * excel��д������ */  
public class POIUtil {  
    private static Logger logger  = Logger.getLogger(POIUtil.class);  
    private final static String xls = "xls";  
    private final static String xlsx = "xlsx";  
      
    /** 
     * �ϴ�excel�ļ� ����ģʽģʽ
     * @param file 
     * @throws IOException  
     */  
    public static List<MyExcel> readExcel(MultipartFile file) throws IOException{  
        //����ļ�  
        checkFile(file);  
        //���Workbook����������  
        Workbook workbook = getWorkBook(file);  
        //�������ض��󣬰�ÿ���е�ֵ��Ϊһ�����飬��������Ϊһ�����Ϸ���  
        List<MyExcel> list = new ArrayList<MyExcel>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //��õ�ǰsheet������  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                //��õ�ǰsheet�Ŀ�ʼ��  
                int firstRowNum  = sheet.getFirstRowNum();  
                //��õ�ǰsheet�Ľ�����  
                int lastRowNum = sheet.getLastRowNum();  
                //ѭ�����˵�һ�е�������   
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //��õ�ǰ��  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //��õ�ǰ�еĿ�ʼ��  
                    int firstCellNum = row.getFirstCellNum();  
                    //��õ�ǰ�е�����  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    //ѭ����ǰ��  
                    MyExcel myExcel =  new MyExcel();
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);
                        if(cellNum==0){
                    		myExcel.setUserid(cells[cellNum]);
                    	}
                    	if(cellNum==1){
                    		myExcel.setUsername(cells[cellNum]);
                    	}
                    	if(cellNum==2){
                    		myExcel.setEnglishname(cells[cellNum]);
                    	}
                    	if(cellNum==3){
                    		myExcel.setNumber(cells[cellNum]);
                    	}
                    }  
                    list.add(myExcel);  
                }  
            }  
            workbook.close();  
        }  
        return list;  
    }  
    
    public static MyExcel  getMyExcelObject(int num,String value){
    	MyExcel myExcel = new MyExcel();
    	if(num==0){
    		myExcel.setId(Long.valueOf(value));
    	}
    	if(num==1){
    		myExcel.setUsername(value);
    	}
    	if(num==2){
    		myExcel.setEnglishname(value);
    	}
    	if(num==3){
    		myExcel.setNumber(value);
    	}
    	return myExcel;
    	
    }
    
    /**
     * �ж��ϴ����ļ��Ƿ���excel�ļ�
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException{  
        //�ж��ļ��Ƿ����  
        if(null == file){  
            logger.error("�ļ������ڣ�");  
            throw new FileNotFoundException("�ļ������ڣ�");  
        }  
        //����ļ���  
        String fileName = file.getOriginalFilename();  
        //�ж��ļ��Ƿ���excel�ļ�  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
            logger.error(fileName + "����excel�ļ�");  
            throw new IOException(fileName + "����excel�ļ�");  
        }  
    }  
    /**
     * �ж��ļ���xls����xlsx
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {  
        //����ļ���  
        String fileName = file.getOriginalFilename();  
        //����Workbook���������󣬱�ʾ����excel  
        Workbook workbook = null;  
        try {  
            //��ȡexcel�ļ���io��  
            InputStream is = file.getInputStream();  
            //�����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
            logger.info(e.getMessage());  
        }  
        return workbook;  
    }  
    
    public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        //�����ֵ���String�������������1����1.0�����  
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
            cell.setCellType(Cell.CELL_TYPE_STRING);  
        }  
        //�ж����ݵ�����  
        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //����  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case Cell.CELL_TYPE_STRING: //�ַ���  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //��ʽ  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //��ֵ   
                cellValue = "";  
                break;  
            case Cell.CELL_TYPE_ERROR: //����  
                cellValue = "�Ƿ��ַ�";  
                break;  
            default:  
                cellValue = "δ֪����";  
                break;  
        }  
        return cellValue;  
    }

	/**
	 * ����excel ֻ����ģʽxls�汾
	 * 
	 * @param list ���ݿ��ѯ������
	 */
	public static void createExcel(List<MyExcel> list) { 
		 // ����һ��Excel�ļ�
		HSSFWorkbook workbook = new HSSFWorkbook();
        // ����һ��������
        HSSFSheet sheet = workbook.createSheet("myExcel");
        // ��ӱ�ͷ��
        HSSFRow hssfRow = sheet.createRow(0);
        // ���õ�Ԫ���ʽ����
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // ��ӱ�ͷ����
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("id");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(1);
        headCell.setCellValue("userId");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(2);
        headCell.setCellValue("userName");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(3);
        headCell.setCellValue("englishName");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(4);
        headCell.setCellValue("number");
        headCell.setCellStyle(cellStyle);
        
     // �����������
        for (int i = 0; i < list.size(); i++) {
        	hssfRow = sheet.createRow((int) i + 1);
        	MyExcel student = list.get(i);
        	// ������Ԫ�񣬲�����ֵ
        	HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(student.getId());
            cell.setCellStyle(cellStyle);
        	
            cell = hssfRow.createCell(1);
            cell.setCellValue(student.getUserid());
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(2);
            cell.setCellValue(student.getUsername());
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(3);
            cell.setCellValue(student.getEnglishname());
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(4);
            cell.setCellValue(student.getNumber());
            cell.setCellStyle(cellStyle);
        }
        
        // ����Excel�ļ�
        try {
            OutputStream outputStream = new FileOutputStream("D:/myself.xls");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * ԭʼ������excel �ļ�����ģʽxls ��xlsx �汾
	 * @param outPath
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static boolean write(String outPath,List<MyExcel> list) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// ���������ĵ�����
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("�����ĵ���ʽ����ȷ��");
			return false;
		}
		// ����sheet����
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// ѭ��д��������
		for (int i = 0; i < 5; i++) {
			Row row = (Row) sheet1.createRow(i);
			// ѭ��д��������
			for (int j = 0; j < 8; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("����" + j);
			}
		}
		// �����ļ���
		OutputStream stream = new FileOutputStream(outPath);
		// д������
		wb.write(stream);
		// �ر��ļ���
		stream.close();
		return true;
	}
	
	/**
	 * ����excel �ļ� ����ģʽģʽ ��ҵ���߼��汾
	 * @param outPath
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static boolean write1(String outPath,List<MyExcel> list) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// ���������ĵ�����
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("�����ĵ���ʽ����ȷ��");
			return false;
		}
		// ����sheet����
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// ��ӱ�ͷ��
        Row hssfRow = sheet1.createRow(0);
        
        // ���õ�Ԫ���ʽ����
        CellStyle cellStyle =  wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // ��ӱ�ͷ����
        Cell headCell =  hssfRow.createCell(0);
        headCell.setCellValue("id");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(1);
        headCell.setCellValue("userId");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(2);
        headCell.setCellValue("userName");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(3);
        headCell.setCellValue("englishName");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(4);
        headCell.setCellValue("number");
        headCell.setCellStyle(cellStyle);
        
        
        // �����������
        for (int i = 0; i < list.size(); i++) {
        	hssfRow = sheet1.createRow((int) i + 1);
        	MyExcel student = list.get(i);
        	// ������Ԫ�񣬲�����ֵ
        	Cell cell =  hssfRow.createCell(0);
            cell.setCellValue(student.getId());
            cell.setCellStyle(cellStyle);
        	
            cell =  hssfRow.createCell(1);
            cell.setCellValue(student.getUserid());
            cell.setCellStyle(cellStyle);
            
            cell =  hssfRow.createCell(2);
            cell.setCellValue(student.getUsername());
            cell.setCellStyle(cellStyle);
            
            cell =  hssfRow.createCell(3);
            cell.setCellValue(student.getEnglishname());
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(4);
            cell.setCellValue(student.getNumber());
            cell.setCellStyle(cellStyle);
        }
		// �����ļ���
		OutputStream stream = new FileOutputStream(outPath);
		// д������
		wb.write(stream);
		// �ر��ļ���
		stream.close();
		return true;
	}
	

	
   }