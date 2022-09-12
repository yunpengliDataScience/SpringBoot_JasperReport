package org.dragon.yunpeng.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dragon.yunpeng.entities.Product;
import org.dragon.yunpeng.report.datasource.ReportDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class ProductReportController {

	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> downloadInvoice() throws JRException, IOException {

		JRDataSource beanCollectionDataSource = ReportDataSource.getReportDataSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("total", "2377");

		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/invoice.jrxml"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint,
		// System.currentTimeMillis() + ".pdf");

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Product_Report.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}

	@GetMapping(value = "/pdf2")
	public void getPdf(HttpServletResponse response) throws JRException, IOException {

		JRDataSource beanCollectionDataSource = ReportDataSource.getReportDataSource();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("total", "2377");

		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/invoice.jrxml"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=Product_Report2.pdf");

		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}

//	@RequestMapping(path = "/pdf", method = RequestMethod.GET)
//    public ModelAndView report() {
//
//        JasperReportsPdfView view = new JasperReportsPdfView();
//        view.setUrl("classpath:report2.jrxml");
//        view.setApplicationContext(appContext);
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("datasource", carService.findAll());
//
//        return new ModelAndView(view, params);
//    }
}
