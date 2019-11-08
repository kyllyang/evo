package com.github.framework.evo.common.excel;

import com.github.framework.evo.common.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * User: Kyll
 * Date: 2019-11-07 13:54
 */
public class ExcelTool {
	public static XSSFWorkbook createXSSFWorkbook() {
		return new XSSFWorkbook();
	}

	public static XSSFSheet createSheet(XSSFWorkbook workbook) {
		return workbook.createSheet();
	}

	public static XSSFSheet createSheet(XSSFWorkbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}

	public static CellStyle createCellStyle(XSSFWorkbook workbook, IndexedColors indexedColors) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(indexedColors.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}

	public static XSSFFont createFont(XSSFWorkbook workbook) {
		return workbook.createFont();
	}

	public static XSSFRow createXSSFRow(XSSFSheet sheet) {
		return createXSSFRow(sheet, sheet.getPhysicalNumberOfRows());
	}

	public static XSSFRow createXSSFRow(XSSFSheet sheet, int rownum) {
		return sheet.createRow(rownum);
	}

	public static XSSFCell createXSSFCell(XSSFRow row, int columnIndex, CellStyle cellStyle, String text) {
		XSSFCell xssfCell = row.createCell(columnIndex);
		xssfCell.setCellStyle(cellStyle);
		xssfCell.setCellValue(new XSSFRichTextString(text));
		return xssfCell;
	}

	public static void setColumnWidth(XSSFSheet sheet, int columnIndex, int width) {
		sheet.setColumnWidth(columnIndex, calculateColumnWidth(width));
	}

	public static int calculateColumnWidth(int width) {
		return 256 * width + 184;
	}

	public static void out(Workbook workbook, String fileName, HttpServletResponse response) {
		response.setHeader("content-disposition", toContentCisposition(fileName + (workbook instanceof XSSFWorkbook ? ".xlsx" : ".xls")));
		response.setContentType("text/html;application/vnd.ms-excel");

		try {
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			throw new ExcelOperateException("输出Excel流失败", e);
		}
	}

	private static String toContentCisposition(String fileName) {
		String encodeFileName;
		try {
			encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new ExcelOperateException("转换Excel文件名称失败", e);
		}
		return String.format("attachment; filename=\"%s\"; filename*=%s''%s", encodeFileName, StandardCharsets.UTF_8.name(), encodeFileName);
	}
}
