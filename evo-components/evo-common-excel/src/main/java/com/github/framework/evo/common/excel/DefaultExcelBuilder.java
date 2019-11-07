package com.github.framework.evo.common.excel;

import com.github.framework.evo.common.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
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

		this.headerCellStyle = ExcelTool.createCellStyle(workbook, IndexedColors.SKY_BLUE);
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

	public DefaultExcelBuilder headers(String... headers) {
		this.headerRow = sheet.createRow(0);

		for (String header : headers) {
			ExcelTool.createXSSFCell(headerRow, headerRow.getPhysicalNumberOfCells(), headerCellStyle, header);
		}

		return this;
	}

	public <T> DefaultExcelBuilder datas(List<T> list, RowData<T> rowData) {
		for (T t : list) {
			String[] values = rowData.getValues(t);

			int numberOfCells = headerRow.getPhysicalNumberOfCells();
			if (values.length != numberOfCells) {
				throw new ExcelOperateException(String.format("列标题数量[%s]与数据项数量[%s]不符", numberOfCells, values.length));
			}

			XSSFRow row = ExcelTool.createXSSFRow(sheet);
			for (int i = 0; i < numberOfCells; i++) {
				ExcelTool.createXSSFCell(row, i, row.getRowNum() % 2 == 0 ? evenCellStyle : oddCellStyle, values[i]);
			}
		}

		return this;
	}

	public XSSFWorkbook build() {
		return workbook;
	}

	public interface RowData<T> {
		String[] getValues(T t);
	}
}