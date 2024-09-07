package in.example.service;

import java.util.List;

import in.example.entity.CitizenPlan;
import in.example.request.SearchRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	public List<String> getPlanNames();
	public List<String> getPlanStatus();
	public List<CitizenPlan> search(SearchRequest request); //search button and criteria
	public boolean exportExcel(HttpServletResponse response) throws Exception; //send excel to mail is true or false
	public boolean exportPdf(HttpServletResponse response)throws Exception; //send pdf to mail, if it send then it is success / fail
}
