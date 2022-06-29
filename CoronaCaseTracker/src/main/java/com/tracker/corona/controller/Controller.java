package com.tracker.corona.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.corona.modal.CoronaAllStats;
import com.tracker.corona.modal.CoronaStats;
import com.tracker.corona.service.GetIndiaCoronaData;
import com.tracker.corona.service.GetWorldCoronaData;

@RestController
public class Controller {
	
	@Autowired
	GetWorldCoronaData getWorldCoronaData;
	
	@Autowired
	GetIndiaCoronaData getIndiaCoronaData;

	@GetMapping("/world/confirmed")
	public List<CoronaStats> getWorldConfirmedCases(){
		return getWorldCoronaData.getWorldConfirmedCoronaStatsList();
	}
	
	@GetMapping("/world/confirmed/{country}")
	public CoronaStats getConfirmedCoronaCaseStatsOfCountry(@PathVariable String country) {
		return getWorldCoronaData.getConfirmedCoronaCaseStatsOfCountry(country.toUpperCase());
	}
	
	@GetMapping("/world/death")
	public List<CoronaStats> getWorldDeathCases(){
		return getWorldCoronaData.getWorldDeathCoronaStatsList();
	}
	
	@GetMapping("/world/death/{country}")
	public CoronaStats getDeathCoronaStatsOfCountry(@PathVariable String country) {
		return getWorldCoronaData.getDeathCoronaCaseStatsOfCountry(country.toUpperCase());
	}
	
	@GetMapping("/india/confirmed")
	public List<CoronaStats> getIndiaConfirmedCases(){
		return getIndiaCoronaData.getIndiaConfirmedCoronaStatsList();
	}
	
	@GetMapping("/india/confirmed/{state}")
	public CoronaStats getIndiaConfirmedCasesOfState(@PathVariable String state){
		return getIndiaCoronaData.getConfirmedCoronaStatsByState(state.toUpperCase());
	}
	
	@GetMapping("/india/recovered")
	public List<CoronaStats> getIndiaRecoveredCases(){
		return getIndiaCoronaData.getIndiaRecoveredCoronaStatsList();
	}
	
	@GetMapping("/india/recovered/{state}")
	public CoronaStats getIndiaRecoveredCasesOfState(@PathVariable String state){
		return getIndiaCoronaData.getRecoveredCoronaStatsByState(state.toUpperCase());
	}
	
	@GetMapping("/india/death")
	public List<CoronaStats> getIndiaDeathCases(){
		return getIndiaCoronaData.getIndiaDeathCoronaStatsList();
	}
	
	@GetMapping("/india/death/{state}")
	public CoronaStats getIndiaDeathCasesOfState(@PathVariable String state){
		return getIndiaCoronaData.getDeathCoronaStatsByState(state.toUpperCase());
	}
	
	@GetMapping("/india")
	public List<CoronaAllStats> getIndiaCoronaAllStats(){
		return getIndiaCoronaData.getIndiaAllCoronaStatsList();
	}
	
	@GetMapping("/india/{state}")
	public CoronaAllStats getIndiaCoronaAllStatsByState(@PathVariable String state) {
		return getIndiaCoronaData.getAllCoronaStatsByState(state.toUpperCase());
	}
}
