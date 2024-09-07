package in.example.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import in.example.entity.CitizenPlan;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExcelGenerator {
	
	public void generate(HttpServletResponse response, List<CitizenPlan> records, File file) throws Exception {
		Workbook workbook = new HSSFWorkbook(); // it supports only xls extension
		Sheet sheet = workbook.createSheet("plans-data");
		Row headerrow = sheet.createRow(0);
		headerrow.createCell(0).setCellValue("Citizen Id");
		headerrow.createCell(1).setCellValue("Citizen Name");
		headerrow.createCell(2).setCellValue("Gender");
		headerrow.createCell(3).setCellValue("Plan Name");
		headerrow.createCell(4).setCellValue("Plan Status");
		headerrow.createCell(5).setCellValue("Start Date");
		headerrow.createCell(6).setCellValue("End Date");
		headerrow.createCell(7).setCellValue("Beneficiary Amount");


		int rowindex = 1;
		for (CitizenPlan plan : records) {
			Row datarow = sheet.createRow(rowindex);
			datarow.createCell(0).setCellValue(plan.getCitizenId());
			datarow.createCell(1).setCellValue(plan.getCitizenName());
			datarow.createCell(2).setCellValue(plan.getGender());
			datarow.createCell(3).setCellValue(plan.getPlanName());
			datarow.createCell(4).setCellValue(plan.getPlanStatus());
			if (null != plan.getPlanStartDate()) {
				datarow.createCell(5).setCellValue(plan.getPlanStartDate() + "");
			} else {
				datarow.createCell(5).setCellValue("N/A");
			}
			if (null != plan.getPlanStartDate()) {
				datarow.createCell(6).setCellValue(plan.getPlanEndDate() + "");
			} else {
				datarow.createCell(6).setCellValue("N/A");
			}
			if (null != plan.getPlanStartDate()) {
				datarow.createCell(7).setCellValue(plan.getBenefitAmt());
			} else {
				datarow.createCell(7).setCellValue("N/A");
			}

			rowindex++;
		}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		
		ServletOutputStream sos = response.getOutputStream();
		workbook.write(sos);
		workbook.close();
	}
}
