package in.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.example.entity.CitizenPlan;

public interface CitizenRepository 
extends JpaRepository<CitizenPlan, Integer>{

	@Query("SELECT DISTINCT c.planName FROM CitizenPlan c")
	public List<String> getPlanName();
	
	@Query("SELECT DISTINCT c.planStatus FROM CitizenPlan c")
	public List<String> getPlanStatus();
}
