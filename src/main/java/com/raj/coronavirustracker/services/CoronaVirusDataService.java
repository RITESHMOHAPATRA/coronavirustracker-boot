package com.raj.coronavirustracker.services;

import com.raj.coronavirustracker.models.LocationPoints;
//import com.raj.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@Service
public class CoronaVirusDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public int getIndia() {
        return india;
    }

    private int india;
    public int getTotalCase() {
        return totalCase;
    }

    private int totalCase;
    public TreeSet<LocationPoints> getAllStats() {
        return allStats;
    }

    private TreeSet<LocationPoints> allStats = new TreeSet<>();

    @PostConstruct
    @Scheduled(cron = "1 * * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        TreeSet<LocationPoints> newStat = new TreeSet<>(new Comparator<LocationPoints>() {
            @Override
            public int compare(LocationPoints o1, LocationPoints o2) {
                return o1.getCountry().compareTo(o2.getCountry());
            }
        });
        HttpClient client =HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader reader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        StringBuilder sb = new StringBuilder();
        int todayTotal=0;
        for (CSVRecord record : records) {
            if(record.get("Country/Region").equals("India")){
                this.india = Integer.parseInt(record.get(record.size()-1));
            }
            LocationPoints stat = new LocationPoints(record.get("Province/State"),record.get("Country/Region")
                    ,Integer.parseInt(record.get(record.size()-1)));
            stat.setDiffFromPrevCase(Integer.parseInt(record.get(record.size()-1)) - Integer.parseInt(record.get(record.size()-2)));
            newStat.add(stat);
//            System.out.println(stat);
            todayTotal+=stat.getLatestTotalCase();
        }
        System.out.println(LocalDate.now()+" "+ LocalTime.now());
        this.allStats = newStat;
        this.totalCase = todayTotal;

    }
}
