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

import com.tracker.corona.modal.CoronaStats;
import com.tracker.corona.utility.Utilities;

@Service
public class GetWorldCoronaData {

	@Value("${data.url.confirmed.world}")
	private String CONFIRMED_CORONA_WORLD_DATA_URL;
	
//	@Value("${data.url.recovered.world}")
//	private String RECOVERED_CORONA_WORLD_DATA_URL;
	
	@Value("${data.url.death.world}")
	private String DEATH_CORONA_WORLD_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	
	@Autowired
	Utilities utilities;
	
	private Map<String, CoronaStats> worldConfirmedCoronaStatsMap = new HashMap<>();
	private Map<String, CoronaStats> worldDeathCoronaStatsMap = new HashMap<>();
	
	
	/*
	 * Method to get the data of the confirmed COVID-19 cases of the world
	 * by default it will run when we start the server
	 * also it will run every 5 minutes to update the data from the github repositary
	 */
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getConfirmedCoronaData() throws IOException, InterruptedException {

		StringReader reader = utilities.getStringReaderByURL(CONFIRMED_CORONA_WORLD_DATA_URL);

		setWorldConfirmedCoronaStatsMap(utilities.getWorldCoronaStatsMap(reader));
	}
	
	@PostConstruct
	@Scheduled(cron = "0 5 * * * 0-6")
	private void getDeathCoronaData() throws IOException, InterruptedException {

		StringReader reader = utilities.getStringReaderByURL(DEATH_CORONA_WORLD_DATA_URL);

		setWorldDeathCoronaStatsMap(utilities.getWorldCoronaStatsMap(reader));
		
	}

	public List<CoronaStats> getWorldConfirmedCoronaStatsList() {
		return  new ArrayList<>(worldConfirmedCoronaStatsMap.values());
	}

	private void setWorldConfirmedCoronaStatsMap(Map<String, CoronaStats> confirmedCoronaStatsList) {
		this.worldConfirmedCoronaStatsMap = confirmedCoronaStatsList;
	}
	
	public List<CoronaStats> getWorldDeathCoronaStatsList() {
		return new ArrayList<>(worldDeathCoronaStatsMap.values());
	}

	public void setWorldDeathCoronaStatsMap(Map<String, CoronaStats> worldDeathCoronaStatsMap) {
		this.worldDeathCoronaStatsMap = worldDeathCoronaStatsMap;
	}

	/*
	 * Return the confirmed corona stats for the specific country given as input
	 */
	public CoronaStats getConfirmedCoronaCaseStatsOfCountry(String country) {
		return worldConfirmedCoronaStatsMap.getOrDefault(country, null);
	}

	/*
	 * Return the Recovered corona stats for the specific country given as input
	 */
	public CoronaStats getDeathCoronaCaseStatsOfCountry(String country) {
		return worldDeathCoronaStatsMap.getOrDefault(country, null);
	}
	
	
}
