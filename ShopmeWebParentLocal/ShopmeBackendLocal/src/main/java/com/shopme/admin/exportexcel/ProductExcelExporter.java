package com.shopme.admin.exportexcel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.admin.util.AbstractExporter;
import com.shopme.common.entity.product.Product;

public class ProductExcelExporter extends AbstractExporter{

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	

	public ProductExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Product");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);

		createCell(row, 0, "Product Id", cellStyle);
		createCell(row, 1, "Product Name", cellStyle);

	}

	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);

		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);		
	}

	public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "products_");

		writeHeaderLine();
		writeDataLines(listProducts);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();


	}

	private void writeDataLines(List<Product> listProducts) {
		int rowIndex = 1;

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		for (Product product : listProducts) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			createCell(row, columnIndex++, product.getId(), cellStyle);
			createCell(row, columnIndex++, product.getName(), cellStyle);
		}
	}
}
