/**
 * 
 */
package com.valuequest.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author Eki Nurhadi
 *
 */    
public class GenerateReport {
	

	public void generatePdfReport(String jrxmlName,String FileName, Map<String, Object> params,
			JRBeanCollectionDataSource beanColDataSource, String format, HttpServletResponse response) {
		try {
			InputStream jrxmlInput = this.getClass().getClassLoader().getResource("report/"+jrxmlName+".jrxml").openStream();
			JasperDesign design = JRXmlLoader.load(jrxmlInput);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);

			
			SimpleDateFormat f			= new SimpleDateFormat("ddMMyyyy-HHmmss");
			String reportName = FileName + "_(" + f.format(new Date()) + ")." + format;
			response.addHeader("Content-disposition", "attachment; filename="+ reportName);
			switch (format) {

			case "PDF":

				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
				break;

			case "CSV":
				
				response.setContentType("application/csv");
				JRCsvExporter exporter = new JRCsvExporter(DefaultJasperReportsContext.getInstance());
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				exporter.exportReport();
				break;
			}
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
