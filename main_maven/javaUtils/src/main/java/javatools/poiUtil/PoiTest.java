package javatools.poiUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiTest {
	
public static void getDataFromExcel(String filePath) {
        
        //判断是否为excel类型文件
        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }
        FileInputStream fis =null;
        Workbook wookbook = null;
        try {
            //获取一个绝对地址的流
        	fis = new FileInputStream(filePath);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        try {
            //2003版本的excel，用.xls结尾
        	//2007版本的excel，用.xlsx结尾
            //wookbook = new HSSFWorkbook(fis);//得到工作簿
        	wookbook = new XSSFWorkbook(fis);//得到工作簿
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            try
            {
                //2007版本的excel，用.xlsx结尾
                
                wookbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        
        //获得表头
        Row rowHead = sheet.getRow(0);
        
        //判断表头是否正确
        if(rowHead.getPhysicalNumberOfCells() != 3)
        {
            System.out.println("表头的数量不对!");
        }
        
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        System.out.println(rowHead.getPhysicalNumberOfCells() + " --表头的数量不对!--" + totalRowNum);
        
        //要获得属性
       //获得所有数据
        for(int i = 1 ; i <= totalRowNum ; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            System.out.println(row.getLastCellNum());
            
            //获得获得第i行第0列的 String类型对象
            
            //获得一个数字类型的数据
            Cell cell2 = row.getCell(1);
            Cell cell3 = row.getCell(2);
            Cell cell4 = row.getCell(3);
            Cell cell5 = row.getCell(4);
            //Cell cell6 = row.getCell(14);
            System.out.println(cell3.getDateCellValue());
            System.out.println("cell2："+cell2.getCellType()+",cell3度："+cell3.getCellType()+",cell4度："+cell4.getCellType()+",cell5度："+cell5.getCellType());
            
        }
    }
	
    public static void main( String[] args ) {
    	getDataFromExcel("E:\\file\\t.xlsx");
        System.out.println( "Hello World!" );
    }
    
    /**
     * 导出Excel
     * @param name sheet名称
     * @param title 标题
     * @param alias 数据别名
     * @param list 数据
     * @return HSSFWorkbook excel表单
     */
    public XSSFWorkbook exportExcel(String name, String[] title, String[] alias, List<Map<String,Object>> list) throws Exception{
        if(title.length != alias.length) {
            throw new Exception("参数有误");
        }
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(name);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("宋体");
        style.setAlignment(HorizontalAlignment.CENTER);     //设置居中
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());     //单元格颜色
        style.setFont(font);
        //声明列对象
        XSSFCell cell = null;
        //创建标题
        for(int i = 0; i < title.length; i++){
            cell = row.createCell(i);   //创建cell对象
            cell.setCellValue(title[i]);    //设置cell值
            cell.setCellStyle(style);       //设置cell样式
        }
        //创建内容
        if(null != list && list.size() > 0) {
            for(int i = 0; i < list.size(); i++){
                row = sheet.createRow(i + 1);
                for(int j = 0; j < alias.length; j++){
                    //将内容赋给对应的列对象
                    row.createCell(j).setCellValue(null == list.get(i).get(alias[j]) ? "" : list.get(i).get(alias[j]).toString());
                }
            }
        }
        return wb;
    }

}
