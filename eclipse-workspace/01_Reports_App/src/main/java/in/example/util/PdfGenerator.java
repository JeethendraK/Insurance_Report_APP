package in.example.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.example.entity.CitizenPlan;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {
	
	public void generator(HttpServletResponse response, List<CitizenPlan> plans2, File file) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		
		Font title = FontFactory.getFont(FontFactory.TIMES_BOLD);
		title.setSize(20);
		
		Paragraph p = new Paragraph("Citizen Plans Info", title);
		
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		
		PdfPTable table = new PdfPTable(8);
		
		table.setSpacingBefore(20);
		table.addCell ("Citizen Id");
		table.addCell("Citizen Name");
		table.addCell("Gender");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");
		table.addCell("Beneficiary Amount");
				
		for (CitizenPlan plan : plans2) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getGender());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			if( null != plan.getPlanStartDate()) {
				table.addCell(plan.getPlanStartDate()+"");
			}else {
				table.addCell("N/A");
			}
			if( null != plan.getPlanEndDate()) {
				table.addCell(plan.getPlanEndDate()+"");
			}else {
				table.addCell("N/A");
			}
			if( null != plan.getBenefitAmt()) {
				table.addCell(plan.getBenefitAmt()+"");
			}else {
				table.addCell("N/A");
			}
		}
		document.add(table);
		
		document.close();
	}

}
