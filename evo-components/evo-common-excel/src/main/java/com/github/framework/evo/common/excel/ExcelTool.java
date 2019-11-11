package com.github.framework.evo.common.excel;

import com.github.framework.evo.common.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
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

	public static CellStyle createCellStyle(XSSFWorkbook workbook, XSSFFont xssfFont, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, IndexedColors indexedColors, BorderStyle borderStyle) {
		CellStyle cellStyle = workbook.createCellStyle();

		if (xssfFont != null) {
			cellStyle.setFont(xssfFont);
		}
		if (horizontalAlignment != null) {
			cellStyle.setAlignment(horizontalAlignment);
		}
		if (verticalAlignment != null) {
			cellStyle.setVerticalAlignment(verticalAlignment);
		}
		if (indexedColors != null) {
			cellStyle.setFillForegroundColor(indexedColors.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		if (borderStyle != null) {
			cellStyle.setBorderBottom(borderStyle);
			cellStyle.setBorderLeft(borderStyle);
			cellStyle.setBorderRight(borderStyle);
			cellStyle.setBorderTop(borderStyle);
		}
		return cellStyle;
	}

	public static XSSFFont createFont(XSSFWorkbook workbook, Boolean bold, Boolean italic, IndexedColors indexedColors) {
		XSSFFont xssfFont = workbook.createFont();
		if (bold != null) {
			xssfFont.setBold(bold);
		}
		if (italic != null) {
			xssfFont.setItalic(italic);
		}
		if (indexedColors != null) {
			xssfFont.setColor(XSSFColor.toXSSFColor(new XSSFColor(indexedColors, new DefaultIndexedColorMap())));
		}
		return xssfFont;
	}

	public static XSSFRow createXSSFRow(XSSFSheet sheet) {
		return createXSSFRow(sheet, sheet.getPhysicalNumberOfRows());
	}

	public static XSSFRow createXSSFRow(XSSFSheet sheet, int rownum) {
		return sheet.createRow(rownum);
	}

	public static XSSFCell createXSSFCell(XSSFRow row, int columnIndex, CellStyle cellStyle, String value) {
		XSSFCell xssfCell = row.createCell(columnIndex);
		if (cellStyle != null) {
			xssfCell.setCellStyle(cellStyle);
		}
		xssfCell.setCellValue(new XSSFRichTextString(value));
		return xssfCell;
	}

	public static XSSFCell createXSSFCellNumber(XSSFRow row, int columnIndex, CellStyle cellStyle, Number value) {
		XSSFCell xssfCell = row.createCell(columnIndex);
		if (cellStyle != null) {
			xssfCell.setCellStyle(cellStyle);
		}
		xssfCell.setCellType(CellType.NUMERIC);
		xssfCell.setCellValue(value.doubleValue());
		return xssfCell;
	}

	public static XSSFCell createXSSFCellFormula(XSSFRow row, int columnIndex, CellStyle cellStyle, String formula) {
		XSSFCell xssfCell = row.createCell(columnIndex);
		if (cellStyle != null) {
			xssfCell.setCellStyle(cellStyle);
		}
		xssfCell.setCellFormula(formula);
		return xssfCell;
	}

	public static XSSFCell createXSSFCell(XSSFSheet sheet, int rowIndex, int columnIndex, CellStyle cellStyle, String value) {
		XSSFRow xssfRow = sheet.getRow(rowIndex);
		if (xssfRow == null) {
			xssfRow = createXSSFRow(sheet, rowIndex);
		}
		XSSFCell xssfCell = xssfRow.getCell(columnIndex);
		if (xssfCell == null) {
			xssfCell = createXSSFCell(xssfRow, columnIndex, cellStyle, value);
		}
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
