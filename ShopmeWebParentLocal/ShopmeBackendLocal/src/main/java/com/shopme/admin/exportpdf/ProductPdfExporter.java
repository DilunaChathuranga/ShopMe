package com.shopme.admin.exportpdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.util.AbstractExporter;
import com.shopme.common.entity.product.Product;

public class ProductPdfExporter extends AbstractExporter {
public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {
		
		super.setResponseHeader(response, "application/pdf", ".pdf", "products_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("List of Product", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.2f, 3.5f});

		writeTableHeader(table);
		writeTableData(table, listProducts);

		document.add(table);

		document.close();
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);		

		cell.setPhrase(new Phrase("Product ID", font));		
		table.addCell(cell);

		cell.setPhrase(new Phrase("product Name", font));		
		table.addCell(cell);

	}
	
	private void writeTableData(PdfPTable table, List<Product> listProducts) {
		for (Product product : listProducts) {
			table.addCell(String.valueOf(product.getId()));
			table.addCell(product.getName());
		}
	}
}
