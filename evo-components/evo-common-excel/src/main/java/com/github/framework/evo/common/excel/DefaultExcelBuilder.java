package com.github.framework.evo.common.excel;

import com.github.framework.evo.common.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * User: Kyll
 * Date: 2019-11-07 16:43
 */
public class DefaultExcelBuilder {
	private XSSFWorkbook workbook;
	private CellStyle headerCellStyle;// 列标题样式
	private CellStyle oddCellStyle;// 奇数行样式
	private CellStyle evenCellStyle;// 偶数行样式
	private XSSFSheet sheet;
	private XSSFRow headerRow;

	public DefaultExcelBuilder() {
		this.workbook = ExcelTool.createXSSFWorkbook();

		XSSFFont font = ExcelTool.createFont(workbook);
		font.setBold(true);

		this.headerCellStyle = ExcelTool.createCellStyle(workbook, IndexedColors.SKY_BLUE);
		this.headerCellStyle.setFont(font);

		this.oddCellStyle = ExcelTool.createCellStyle(workbook, IndexedColors.GREY_25_PERCENT);
		this.evenCellStyle = ExcelTool.createCellStyle(workbook, IndexedColors.WHITE);
	}

	public DefaultExcelBuilder sheet() {
		this.sheet = ExcelTool.createSheet(workbook);
		return this;
	}

	public DefaultExcelBuilder sheet(String sheetName) {
		this.sheet = ExcelTool.createSheet(workbook, sheetName);
		return this;
	}

	public DefaultExcelBuilder columnWidth(int width) {
		for (int i = 0, numberOfCells = headerRow.getPhysicalNumberOfCells(); i < numberOfCells; i++) {
			ExcelTool.setColumnWidth(sheet, i, width);
		}

		return this;
	}

	public DefaultExcelBuilder columnWidth(int columnIndex, int width) {
		ExcelTool.setColumnWidth(sheet, columnIndex, width);
		return this;
	}

	public DefaultExcelBuilder headers(String... headers) {
		this.headerRow = sheet.createRow(0);

		for (String header : headers) {
			ExcelTool.createXSSFCell(headerRow, headerRow.getPhysicalNumberOfCells(), headerCellStyle, header);
		}

		return this;
	}

	public <T> DefaultExcelBuilder datas(List<T> list, RowData<T> rowData) {
		for (T t : list) {
			XSSFRow row = ExcelTool.createXSSFRow(sheet);

			String[] values = rowData.getValues(t, row.getRowNum());

			int numberOfCells = headerRow.getPhysicalNumberOfCells();
			if (values.length != numberOfCells) {
				throw new ExcelOperateException(String.format("列标题数量[%s]与数据项数量[%s]不符", numberOfCells, values.length));
			}

			for (int i = 0; i < numberOfCells; i++) {
				ExcelTool.createXSSFCell(row, i, row.getRowNum() % 2 == 0 ? evenCellStyle : oddCellStyle, values[i]);
			}
		}

		return this;
	}

	public DefaultExcelBuilder forceFormulaRecalculation(boolean value) {
		sheet.setForceFormulaRecalculation(value);
		return this;
	}

	public XSSFWorkbook build() {
		return workbook;
	}

	public interface RowData<T> {
		String[] getValues(T t, int rowNum);
	}
}
