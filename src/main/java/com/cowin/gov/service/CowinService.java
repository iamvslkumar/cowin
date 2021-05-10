package com.cowin.gov.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cowin.gov.dto.District;
import com.cowin.gov.dto.DistrictResponse;
import com.cowin.gov.dto.Session;
import com.cowin.gov.dto.StateResponse;

@Service
public class CowinService {

	public DistrictResponse getAll() {
		// TODO Auto-generated method stub
		
		/*
		 * RestTemplate restTemplate = new RestTemplate(); String fooResourceUrl =
		 * "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=100&date=10-05-2021";
		 * ResponseEntity<DistrictResponse> response =
		 * restTemplate.getForEntity(fooResourceUrl, DistrictResponse.class);
		 */
		try {
//            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            
            ResponseEntity<StateResponse>  stateResponse = restTemplate.exchange("https://cdn-api.co-vin.in/api/v2/admin/location/districts/9", HttpMethod.GET,entity,StateResponse.class);
            
            
            for(String date : getCalendarDate()) {
            	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
            	System.out.println(date);
            	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
            for(District district : stateResponse.getBody().getDistricts()) {
            
            ResponseEntity<DistrictResponse>  response = restTemplate.exchange("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="+district.getDistrictId()+"&date="+"10-05-2021", HttpMethod.GET,entity,DistrictResponse.class);
            DistrictResponse responseEntity = response.getBody();
            for(Session session: responseEntity.getSessions()) {
//            	if(session.getMinAgeLimit()==18) {
//            		//System.out.println(session);
//            		System.out.println("##########################");
//            		System.out.println("Center District :" + session.getDistrictName());
//            		System.out.println("Center Name :" + session.getName());
//            		System.out.println("Center Address :" + session.getAddress());
//            		System.out.println("Center Pincode :" + session.getPincode());
//            		System.out.println("Slot Availaible Capacity :" + session.getAvailableCapacity());
//            		System.out.println("Slot Availaible :" + session.getSlots());
//            		System.out.println("Vaccine Availaible :" + session.getVaccine());
//            		System.out.println("Vaccine fee :" + session.getFeeType()+":"+session.getFee());
//            		System.out.println("##########################");
//            	}else {
//            		System.out.println("##########################");
//            		System.out.println("No slots Available in :" + session.getDistrictName());
//            		System.out.println("##########################");
//            	}
            	if(session.getMinAgeLimit()==45 && session.getVaccine().equalsIgnoreCase("COVAXIN")) {
            		//System.out.println(session);
            		System.out.println("##########################");
            		System.out.println("Center District :" + session.getDistrictName());
            		System.out.println("Center Name :" + session.getName());
            		System.out.println("Center Address :" + session.getAddress());
            		System.out.println("Center Pincode :" + session.getPincode());
            		System.out.println("Slot Availaible Capacity :" + session.getAvailableCapacity());
            		System.out.println("Slot Availaible :" + session.getSlots());
            		System.out.println("Vaccine Availaible :" + session.getVaccine());
            		System.out.println("Vaccine fee :" + session.getFeeType()+":"+session.getFee());
            		System.out.println("##########################");
            	}
//            	else {
//            		System.out.println("##########################");
//            		System.out.println("No slots Available in :" + session.getDistrictName());
//            		System.out.println("##########################");
//            	}
            	
            }
//            System.out.println(responseEntity.getSessions());
            }
            }
            
        } catch (Exception ex) {
           ex.printStackTrace();

        }
		
		
		return null;
	}
	
	
	private List<String> getCalendarDate(){
		
		List<String> dateRange = new  ArrayList<String>();
		Calendar start = new GregorianCalendar(2021,4,10);;
		Calendar end = new GregorianCalendar(2021,4,18);;
		
		while(start.before(end) || start.equals(end)) {
			Date result = start.getTime();
			dateRange.add(getDateValue(result));
			start.add(Calendar.DATE, 1);
		}
		return dateRange;
	}


	private String getDateValue(Date date) {
		SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		String formattedDate = format.format(date);
		return formattedDate;
	}

}
