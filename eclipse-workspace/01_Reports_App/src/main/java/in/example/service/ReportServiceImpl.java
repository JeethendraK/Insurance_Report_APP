package in.example.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.example.entity.CitizenPlan;
import in.example.repo.CitizenRepository;
import in.example.request.SearchRequest;
import in.example.util.EmailUtils;
import in.example.util.ExcelGenerator;
import in.example.util.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenRepository planRepo;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public List<String> getPlanNames() {
		List<String> planNames = planRepo.getPlanName();
		return planNames;
	}

	@Override
	public List<String> getPlanStatus() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		// implementing the dynamic query by using query by example, form data is in
		// request object. Copy binding obj to enityt obj
		CitizenPlan entity = new CitizenPlan();

		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}
		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {
			String startDate = request.getStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localdate = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(localdate);
		}
		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {
			String endDate = request.getStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Converting String to LocalDate
			LocalDate localdate = LocalDate.parse(endDate, formatter);
			entity.setPlanEndDate(localdate);
		}

		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		File f  = new File("Plans_1.xls");
		
		List<CitizenPlan> records = planRepo.findAll();
		excelGenerator.generate(response, records, f);
		
		String subject = "Test mail for report geneation";
		String body = "Test mail body";
		String to = "jeethendra948@gmail.com";
		
		
		emailUtils.sendEmail(subject, body, to, f);
		f.delete();
		
		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		File f  = new File("Plans_1.pdf");
		
		List<CitizenPlan> plans = planRepo.findAll();
		
		pdfGenerator.generator(response, plans, f);
		
		String subject = "Test mail for report geneation";
		String body = "Test mail body";
		String to = "jeethendranewtiger@gmail.com";
		
		
		emailUtils.sendEmail(subject, body, to, f);
		f.delete();//delete file from loal system after sending thorugh email
		
		return true;
	}

}
