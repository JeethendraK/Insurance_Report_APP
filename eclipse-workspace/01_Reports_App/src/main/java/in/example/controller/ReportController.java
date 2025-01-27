package in.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.example.entity.CitizenPlan;
import in.example.request.SearchRequest;
import in.example.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse response) throws Exception{
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachement;filename=plans.pdf");
		service.exportPdf(response);
	}
	
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachement;filename=plans.xls");
		service.exportExcel(response);
	}
	
	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		System.out.println(search);
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		init(model);
		return "index";
	}
	
	/**
	 * This method is used to load index page
	 * @param model
	 * @return string
	 */
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());
		init(model);
		
		return "index";
	}

	private void init(Model model) {
		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatus());
	}
}
