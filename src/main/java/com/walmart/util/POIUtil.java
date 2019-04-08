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
 * excel读写工具类 */  
public class POIUtil {  
    private static Logger logger  = Logger.getLogger(POIUtil.class);  
    private final static String xls = "xls";  
    private final static String xlsx = "xlsx";  
      
    /** 
     * 上传excel文件 兼容模式模式
     * @param file 
     * @throws IOException  
     */  
    public static List<MyExcel> readExcel(MultipartFile file) throws IOException{  
        //检查文件  
        checkFile(file);  
        //获得Workbook工作薄对象  
        Workbook workbook = getWorkBook(file);  
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
        List<MyExcel> list = new ArrayList<MyExcel>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //获得当前sheet工作表  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                //获得当前sheet的开始行  
                int firstRowNum  = sheet.getFirstRowNum();  
                //获得当前sheet的结束行  
                int lastRowNum = sheet.getLastRowNum();  
                //循环除了第一行的所有行   
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //获得当前行的开始列  
                    int firstCellNum = row.getFirstCellNum();  
                    //获得当前行的列数  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    //循环当前行  
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
     * 判断上传的文件是否是excel文件
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException{  
        //判断文件是否存在  
        if(null == file){  
            logger.error("文件不存在！");  
            throw new FileNotFoundException("文件不存在！");  
        }  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //判断文件是否是excel文件  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
            logger.error(fileName + "不是excel文件");  
            throw new IOException(fileName + "不是excel文件");  
        }  
    }  
    /**
     * 判断文件是xls还是xlsx
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
            InputStream is = file.getInputStream();  
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
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
        //把数字当成String来读，避免出现1读成1.0的情况  
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
            cell.setCellType(Cell.CELL_TYPE_STRING);  
        }  
        //判断数据的类型  
        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //数字  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case Cell.CELL_TYPE_STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //空值   
                cellValue = "";  
                break;  
            case Cell.CELL_TYPE_ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }

	/**
	 * 下载excel 只兼容模式xls版本
	 * 
	 * @param list 数据库查询才数据
	 */
	public static void createExcel(List<MyExcel> list) { 
		 // 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("myExcel");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 添加表头内容
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
        
     // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
        	hssfRow = sheet.createRow((int) i + 1);
        	MyExcel student = list.get(i);
        	// 创建单元格，并设置值
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
        
        // 保存Excel文件
        try {
            OutputStream outputStream = new FileOutputStream("D:/myself.xls");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 原始版下载excel 文件兼容模式xls 和xlsx 版本
	 * @param outPath
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static boolean write(String outPath,List<MyExcel> list) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 循环写入行数据
		for (int i = 0; i < 5; i++) {
			Row row = (Row) sheet1.createRow(i);
			// 循环写入列数据
			for (int j = 0; j < 8; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("测试" + j);
			}
		}
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}
	
	/**
	 * 下载excel 文件 兼容模式模式 带业务逻辑版本
	 * @param outPath
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static boolean write1(String outPath,List<MyExcel> list) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 添加表头行
        Row hssfRow = sheet1.createRow(0);
        
        // 设置单元格格式居中
        CellStyle cellStyle =  wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 添加表头内容
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
        
        
        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
        	hssfRow = sheet1.createRow((int) i + 1);
        	MyExcel student = list.get(i);
        	// 创建单元格，并设置值
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
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}
	

	
   }