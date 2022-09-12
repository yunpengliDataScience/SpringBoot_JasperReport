package org.dragon.yunpeng.report.datasource;

import java.util.Arrays;

import org.dragon.yunpeng.entities.Product;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportDataSource {

	public static JRDataSource getReportDataSource() {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Arrays.asList(

				new Product(121, "Keyboard", 25), new Product(122, "Mouse", 15), new Product(123, "Laptop", 1999),
				new Product(124, "Mobile", 299), new Product(125, "Headphone", 39)

		), false);

		return beanCollectionDataSource;
	}
}
