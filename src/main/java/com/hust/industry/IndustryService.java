package com.hust.industry;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    public JSONObject getJobList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job list");

        final JSONArray jobList = new JSONArray();

        List<Object[]> list = industryRepository.getJobList();
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("timestamp", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            jobObject.put("average_salary", ob[3].toString());
            jobObject.put("min_salary", ob[4].toString());
            jobObject.put("max_salary", ob[5].toString());
            jobObject.put("num_job", ob[6].toString());

            jobObject.put("description", ob[2].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("value", jobList);
        return jsonObject;
    }


    public JSONObject getIndustryList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list");

        final JSONArray industryList = new JSONArray();

        List<Object[]> list = industryRepository.getIndustryList();
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryObject.put("description", ob[1].toString());
            industryObject.put("num_job", ob[0].toString());
            industryObject.put("average_salary", ob[0].toString());
            industryList.add(industryObject);
        }
        jsonObject.put("value", industryList);
        return jsonObject;
    }

    
    // public JSONObject getJobListByIndustry(String industryId) {
    //     final JSONObject jsonObject = new JSONObject();
       
    //     return jsonObject;
    // }

    
    public JSONObject getRelatedJob(String id){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The related job list ");

        final JSONArray jobList = new JSONArray();

        List<Object[]> list = industryRepository.getRelatedJob(id);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("description", ob[1].toString());
            jobObject.put("num_job", ob[0].toString());
            jobObject.put("average_salary", ob[0].toString());
            jobObject.put("min_salary", ob[0].toString());
            jobObject.put("max_salary", ob[0].toString());
            jobObject.put("industry", ob[0].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("value", jobList);
        return jsonObject;
    }
    
    public JSONObject getJobDemandByPeriodOfTime(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by period of time");

        final JSONArray periods = new JSONArray();
        System.out.println(id);
        List<Object[]> list = industryRepository.getJobDemandByPeriodOfTime(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("id_time", ob[0].toString());
            period.put("timestamp", ob[1].toString());
            period.put("id_job", ob[2].toString());
            period.put("province", ob[3].toString());
            period.put("num_job", ob[5].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        System.out.println(id);
        List<Object[]> list = industryRepository.getJobDemandByAge(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("id_job", ob[0].toString());
            ageRange.put("id_time", ob[1].toString());
            ageRange.put("timestamp", ob[3].toString());
            ageRange.put("age_range", ob[4].toString());
            ageRange.put("gender", ob[5].toString());
            ageRange.put("num_job", ob[6].toString());
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        System.out.println(id);
        List<Object[]> list = industryRepository.getJobDemandByLiteracy(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> literacy = new HashMap<String, String>();
            literacy.put("id", ob[0].toString());
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[3].toString());
            literacy.put("literacy", ob[4].toString());
            literacy.put("num_job", ob[4].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }


    public JSONObject getAverageSalaryByPeriodOfTime(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The average salary of job");

        final JSONArray periods = new JSONArray();
        System.out.println(id);
        List<Object[]> list = industryRepository.getAverageSalaryByPeriodOfTime(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("id_time", ob[0].toString());
            period.put("timestamp", ob[2].toString());
            period.put("id_job", ob[3].toString());
            period.put("province", ob[4].toString());
            period.put("average_salaray", ob[6].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

}