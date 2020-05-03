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
            jobObject.put("name", ob[1].toString());
            jobObject.put("num_job", ob[0].toString());
            jobObject.put("description", ob[0].toString());
            jobObject.put("average_salary", ob[0].toString());
            jobObject.put("industry", ob[1].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("job_list", jobList);
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
            industryObject.put("num_job", ob[0].toString());
            // industryObject.put("description", ob[0].toString());
            // industryObject.put("average_salary", ob[0].toString());
            // industryObject.put("industry", ob[1].toString());
            industryList.add(industryObject);
        }
        jsonObject.put("industry_list", industryList);
        return jsonObject;
    }





    public JSONObject getJobListByIndustry(String industryId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the job list by industry");

        final JSONArray jobList = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("id", "1111");
        jobDetail.put("name", "Lập trình viên");
        jobList.add(jobDetail);
        jobList.add(jobDetail);
        jsonObject.put("detail",jobList);
        return jsonObject;
    }
    public JSONObject getJobDemandByPeriodOfTime(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The jobs demand by period of time");
        
        final JSONArray periodOfTImes = new JSONArray();
        HashMap<String, String> timesObject = new HashMap<String, String>();
        timesObject.put("timestime", "QIV/2017");
        timesObject.put("value", "50");
        timesObject.put("growth", "2");
        periodOfTImes.add(timesObject);
        periodOfTImes.add(timesObject);
        jsonObject.put("periodOfTImes", periodOfTImes);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The jobs demand by literacy");
        
        final JSONArray literacies = new JSONArray();
        HashMap<String, String> literacyObject = new HashMap<String, String>();
        literacyObject.put("name", "Trung học cơ sở");
        literacyObject.put("value", "5000");
        literacyObject.put("growth", "-2");
        literacies.add(literacyObject);
        literacies.add(literacyObject);
        jsonObject.put("literacies", literacies);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The jobs demand by age");
        
        final JSONArray ageRanges = new JSONArray();
        HashMap<String, String> ageRangeObject = new HashMap<String, String>();
        ageRangeObject.put("name", "18-25");
        ageRangeObject.put("value", "5000");
        ageRangeObject.put("growth", "-2");
        ageRanges.add(ageRangeObject);
        ageRanges.add(ageRangeObject);
        jsonObject.put("literacies", ageRanges);
        return jsonObject;
    }

    public JSONObject getAverageSalaryByPeriodOfTime(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary  by period of time");
        
        final JSONArray periodOfTImes = new JSONArray();
        HashMap<String, String> averageSalaryObject = new HashMap<String, String>();
        averageSalaryObject.put("timestime", "QIV/2017");
        averageSalaryObject.put("value", "25");
        averageSalaryObject.put("growth", "2");
        periodOfTImes.add(averageSalaryObject);
        periodOfTImes.add(averageSalaryObject);
        jsonObject.put("periodOfTImes", periodOfTImes);
        return jsonObject;
    }

}