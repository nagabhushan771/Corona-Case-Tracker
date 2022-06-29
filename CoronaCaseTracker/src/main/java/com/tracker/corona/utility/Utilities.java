package com.tracker.corona.utility;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.tracker.corona.modal.CoronaStats;

@Component
public class Utilities {
	
	public StringReader getStringReaderByURL(String uri) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).build();
		
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		return new StringReader(httpResponse.body());
	}
	
	public Map<String, CoronaStats> getCoronaStatsMap(StringReader reader) throws IOException{
		Map<String, CoronaStats> newIndiaCoronaStatsMap = new HashMap<>();
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

		    CoronaStats coronaStat = new CoronaStats();
		    coronaStat.setState(record.get("STATE/UT"));
		    coronaStat.setCountry("India");
		    coronaStat.setCases(Integer.parseInt(record.get(record.size()-1)));

		    String key = coronaStat.getState() == "" ? coronaStat.getCountry() : coronaStat.getState();
		    if(!key.equals("Total"))
		    	newIndiaCoronaStatsMap.put(key.toUpperCase(), coronaStat);
		}
		return newIndiaCoronaStatsMap;
	}
	
	public Map<String, CoronaStats> getWorldCoronaStatsMap(StringReader reader) throws IOException{
		Map<String, CoronaStats> newCoronaStatsMap = new HashMap<>();
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

		    CoronaStats coronaStat = new CoronaStats();
		    coronaStat.setState(record.get("Province/State"));
		    coronaStat.setCountry(record.get("Country/Region"));
		    coronaStat.setCases(Integer.parseInt(record.get(record.size()-1)));

		    String key = coronaStat.getState() == "" ? coronaStat.getCountry() : coronaStat.getState();
		    newCoronaStatsMap.put(key.toUpperCase(), coronaStat);
		}
		return newCoronaStatsMap;
	}
}
