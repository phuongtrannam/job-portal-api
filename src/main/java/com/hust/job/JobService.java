package com.hust.job;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JSONObject basicSearchJob(String queryContent){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The basic search job");

        final JSONArray jobArr = new JSONArray();

        List<Object[]> list = jobRepository.basicSearchJob(queryContent);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[2].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("numJob", ob[1].toString());
            jobObject.put("gender", ob[4].toString());
            jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }
    
    public JSONObject getJobInfo(String idJob){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job info");

        final JSONArray jobArr = new JSONArray();

        List<Object[]> list = jobRepository.getJobInfo(idJob);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[2].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("numJob", ob[1].toString());
            jobObject.put("gender", ob[4].toString());
            jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject advancedSearchJob(String queryContent, String idLocation, String idIndustry, String salary){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The advanced search job");

        final JSONArray jobArr = new JSONArray();

        List<Object[]> list = jobRepository.advancedSearchJob(queryContent, idLocation, idIndustry, salary);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[2].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("numJob", ob[1].toString());
            jobObject.put("gender", ob[4].toString());
            jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }









    public JSONObject getBusinessLinesOfTheCompany(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The business lines of the company");

        final JSONArray businessLines = new JSONArray();

        List<Object[]> list = companyRepository.getBusinessLinesOfTheCompany(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            
            businessLines.add(industryObject);
        }
        jsonObject.put("result", businessLines);
        return jsonObject;
    }
}