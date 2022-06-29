package com.tracker.corona.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tracker.corona.modal.CoronaAllStats;
import com.tracker.corona.modal.CoronaStats;
import com.tracker.corona.utility.Utilities;

@Service
public class GetIndiaCoronaData {

	@Value("${data.url.confirmed.india}")
	private String CONFIRMED_CORONA_INDIA_DATA_URL;
	
	@Value("${data.url.recovered.india}")
	private String RECOVERED_CORONA_INDIA_DATA_URL;
	
	@Value("${data.url.death.india}")
	private String DEATH_CORONA_INDIA_DATA_URL;
	
	@Autowired
	Utilities utilities;
	
	private Map<String, CoronaStats> indiaConfirmedCoronaStatsMap = new HashMap<>();
	private Map<String, CoronaStats> indiaRecoveredCoronaStatsMap = new HashMap<>();
	private Map<String, CoronaStats> indiaDeathCoronaStatsMap = new HashMap<>();
	private Map<String, CoronaAllStats> indiaAllCoronaStatsMap = new HashMap<>();
	
	
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getConfirmedData() throws IOException, InterruptedException {

		StringReader reader = utilities.getStringReaderByURL(CONFIRMED_CORONA_INDIA_DATA_URL);

		setIndiaConfirmedCoronaStatsMap(utilities.getCoronaStatsMap(reader));
		
	}
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getRecoveredData() throws IOException, InterruptedException {

		StringReader reader = utilities.getStringReaderByURL(RECOVERED_CORONA_INDIA_DATA_URL);

		setIndiaRecoveredCoronaStatsMap(utilities.getCoronaStatsMap(reader));
		
	}
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getDeathData() throws IOException, InterruptedException {

		StringReader reader = utilities.getStringReaderByURL(DEATH_CORONA_INDIA_DATA_URL);

		setIndiaDeathCoronaStatsMap(utilities.getCoronaStatsMap(reader));
		createIndiaAllStatsMap();
	}



	public List<CoronaStats> getIndiaConfirmedCoronaStatsList() {
		return new ArrayList<>(indiaConfirmedCoronaStatsMap.values()) ;
	}



	public void setIndiaConfirmedCoronaStatsMap(Map<String, CoronaStats> indiaConfirmedCoronaStatsMap) {
		this.indiaConfirmedCoronaStatsMap = indiaConfirmedCoronaStatsMap;
	}

	public CoronaStats getConfirmedCoronaStatsByState(String state) {
		return indiaConfirmedCoronaStatsMap.getOrDefault(state, null);
	}


	public List<CoronaStats> getIndiaRecoveredCoronaStatsList() {
		return new ArrayList<>(indiaRecoveredCoronaStatsMap.values());
	}



	public void setIndiaRecoveredCoronaStatsMap(Map<String, CoronaStats> indiaRecoveredCoronaStatsMap) {
		this.indiaRecoveredCoronaStatsMap = indiaRecoveredCoronaStatsMap;
	}


	public CoronaStats getRecoveredCoronaStatsByState(String state) {
		return indiaRecoveredCoronaStatsMap.getOrDefault(state, null);
	}

	public List<CoronaStats> getIndiaDeathCoronaStatsList() {
		return new ArrayList<>(indiaDeathCoronaStatsMap.values());
	}



	public void setIndiaDeathCoronaStatsMap(Map<String, CoronaStats> indiaDeathCoronaStatsMap) {
		this.indiaDeathCoronaStatsMap = indiaDeathCoronaStatsMap;
	}
	
	public CoronaStats getDeathCoronaStatsByState(String state) {
		return indiaDeathCoronaStatsMap.getOrDefault(state, null);
	}
	
	private void createIndiaAllStatsMap() {
		Map<String, CoronaAllStats> newIndiaAllCoronaStatsMap = new HashMap<>();
		
		for(String key : indiaConfirmedCoronaStatsMap.keySet()) {
			CoronaAllStats coronaAllStat = new CoronaAllStats();
			coronaAllStat.setCountry("India");
			coronaAllStat.setState(key);
			coronaAllStat.setConfirmed(indiaConfirmedCoronaStatsMap.get(key).getCases());
			coronaAllStat.setRecovered(indiaRecoveredCoronaStatsMap.get(key).getCases());
			coronaAllStat.setDeath(indiaDeathCoronaStatsMap.get(key).getCases());
			
			newIndiaAllCoronaStatsMap.put(key, coronaAllStat);
		}
		setIndiaAllCoronaStatsMap(newIndiaAllCoronaStatsMap);
		
	}

	public List<CoronaAllStats> getIndiaAllCoronaStatsList() {
		return new ArrayList<>(indiaAllCoronaStatsMap.values());
	}

	public void setIndiaAllCoronaStatsMap(Map<String, CoronaAllStats> indiaAllCoronaStatsMap) {
		this.indiaAllCoronaStatsMap = indiaAllCoronaStatsMap;
	}
	
	public CoronaAllStats getAllCoronaStatsByState(String state) {
		return indiaAllCoronaStatsMap.getOrDefault(state, null);
	}
}
