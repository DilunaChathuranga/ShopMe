package com.shopme.admin.exportcsv;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.admin.util.AbstractExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.product.Product;

public class ProductCsvExporter extends AbstractExporter {
	public void export(List<Product> listProducts, HttpServletResponse response) 
			throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "products_");

		Writer writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
		writer.write('\uFEFF');
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, 
				CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = {"Product ID", "Product Name"};
		String[] fieldMapping = {"id", "name"};

		csvWriter.writeHeader(csvHeader);

		for (Product product : listProducts) {
			product.setName(product.getName().replace("--", "  "));
			csvWriter.write(product, fieldMapping);
		}

		csvWriter.close();
	}
}
