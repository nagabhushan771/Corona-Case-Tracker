package com.tracker.corona.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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
		Map<String, CoronaStats> newIndiaConfirmedCoronaStatsMap = new HashMap<>();
		StringReader reader = utilities.getStringReaderByURL(CONFIRMED_CORONA_INDIA_DATA_URL);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

		    CoronaStats confirmedCoronaStat = new CoronaStats();
		    confirmedCoronaStat.setState(record.get("STATE/UT"));
		    confirmedCoronaStat.setCountry("India");
		    confirmedCoronaStat.setCases(Integer.parseInt(record.get(record.size()-1)));

		    String key = confirmedCoronaStat.getState() == "" ? confirmedCoronaStat.getCountry() : confirmedCoronaStat.getState();
		    if(!key.equals("Total"))
		    	newIndiaConfirmedCoronaStatsMap.put(key.toUpperCase(), confirmedCoronaStat);
		}
		setIndiaConfirmedCoronaStatsMap(newIndiaConfirmedCoronaStatsMap);
		
	}
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getRecoveredData() throws IOException, InterruptedException {
		Map<String, CoronaStats> newIndiaRecoveredCoronaStatsMap = new HashMap<>();
		StringReader reader = utilities.getStringReaderByURL(RECOVERED_CORONA_INDIA_DATA_URL);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

		    CoronaStats recoveredCoronaStat = new CoronaStats();
		    recoveredCoronaStat.setState(record.get("STATE/UT"));
		    recoveredCoronaStat.setCountry("India");
		    recoveredCoronaStat.setCases(Integer.parseInt(record.get(record.size()-1)));

		    String key = recoveredCoronaStat.getState() == "" ? recoveredCoronaStat.getCountry() : recoveredCoronaStat.getState();
		    if(!key.equals("Total"))
		    	newIndiaRecoveredCoronaStatsMap.put(key.toUpperCase(), recoveredCoronaStat);
		}
		setIndiaRecoveredCoronaStatsMap(newIndiaRecoveredCoronaStatsMap);
		
	}
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getDeathData() throws IOException, InterruptedException {
		Map<String, CoronaStats> newIndiaDeathCoronaStatsMap = new HashMap<>();
		StringReader reader = utilities.getStringReaderByURL(DEATH_CORONA_INDIA_DATA_URL);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

		    CoronaStats deathCoronaStat = new CoronaStats();
		    deathCoronaStat.setState(record.get("STATE/UT"));
		    deathCoronaStat.setCountry("India");
		    deathCoronaStat.setCases(Integer.parseInt(record.get(record.size()-1)));

		    String key = deathCoronaStat.getState() == "" ? deathCoronaStat.getCountry() : deathCoronaStat.getState();
		    if(!key.equals("Total"))
		    	newIndiaDeathCoronaStatsMap.put(key.toUpperCase(), deathCoronaStat);
		}
		setIndiaDeathCoronaStatsMap(newIndiaDeathCoronaStatsMap);
		
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
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
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
