package com.hust.company;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public JSONObject getCompanyList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list");

        final JSONArray companyList = new JSONArray();

        List<Object[]> list = companyRepository.getCompanyList();
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
//            companyObject.put("phonenumber", ob[2].toString());
//            companyObject.put("description", ob[1].toString());
//            companyObject.put("founded_year", ob[4].toString());
            // companyObject.put("industry", ob[1].toString());
//            companyObject.put("website", ob[5].toString());
            companyObject.put("location", ob[6].toString());
//            companyObject.put("logo", ob[1].toString());
            companyList.add(companyObject);
        }
        jsonObject.put("result", companyList);
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

    public JSONObject getCompanyInfo(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company info");

        List<Object[]> list = companyRepository.getCompanyInfo(id);
        System.out.println(id);
        HashMap<String, String> companyObject = new HashMap<String, String>();
        for(Object[] ob : list){
            
            companyObject.put("id", id);
            companyObject.put("name", ob[0].toString());
//            companyObject.put("phonenumber", ob[1].toString());
//            companyObject.put("description", ob[0].toString());
//            companyObject.put("founded_year", ob[3].toString());
//            companyObject.put("website", ob[4].toString());
            companyObject.put("location", ob[5].toString());
            companyObject.put("num_job", ob[6].toString());
//            companyObject.put("logo", ob[0].toString());
        }
        jsonObject.put("result", companyObject);
        return jsonObject;

    }

    public JSONObject getRelatedCompany(String id){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company related");

        final JSONArray relatedCompany = new JSONArray();

        List<Object[]> list = companyRepository.getRelatedCompany(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
//            companyObject.put("phonenumber", ob[2].toString());
//            companyObject.put("description", ob[1].toString());
//            companyObject.put("founded_year", ob[4].toString());
//            companyObject.put("website", ob[5].toString());
            companyObject.put("location", ob[6].toString());
//            companyObject.put("logo", ob[0].toString());
            relatedCompany.add(companyObject);
        }
        jsonObject.put("result", relatedCompany);
        return jsonObject;
    }


    public JSONObject getRecentJobByCompany(String id){
        System.out.println(id);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The current job by company");

        final JSONArray jobList = new JSONArray();

        List<Object[]> list = companyRepository.getRecentJobByCompany(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("job_name", ob[1].toString());
            jobObject.put("province", ob[2].toString());
            jobObject.put("min_salary", ob[3].toString());
            jobObject.put("max_salary", ob[4].toString());
            jobObject.put("num_job", ob[5].toString());
            jobObject.put("industry", ob[0].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("result", jobList);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by period of time");

        final JSONArray periods = new JSONArray();
        System.out.println(id);
        List<Object[]> list = companyRepository.getJobDemandByPeriodOfTime(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("id", ob[0].toString());
            period.put("province", ob[1].toString());
            period.put("timestamp", ob[2].toString());
            period.put("num_job", ob[3].toString());
            // period.put("year", ob[0].toString());
            // period.put("quarter", ob[0].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;

    }

    public JSONObject getJobDemandByLiteracy(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        System.out.println(id);
        List<Object[]> list = companyRepository.getJobDemandByLiteracy(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> literacy = new HashMap<String, String>();
            literacy.put("id", ob[0].toString());
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[2].toString());
            literacy.put("literacy", ob[3].toString());
            literacy.put("num_job", ob[4].toString());
            // literacy.put("year", ob[0].toString());
            // literacy.put("quarter", ob[0].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        System.out.println(id);
        List<Object[]> list = companyRepository.getJobDemandByAge(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("id", ob[0].toString());
            ageRange.put("idTime", ob[1].toString());
            ageRange.put("timestamp", ob[2].toString());
            ageRange.put("age", ob[3].toString());
            ageRange.put("gender", ob[4].toString());
            ageRange.put("num_job", ob[5].toString());
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;

    }


    // public JSONObject getNumberOfJob(){
    //     final JSONObject jsonObject = new JSONObject();
        
    //     return jsonObject;

    // }

    // public JSONObject getJobsHighestSalary(){
    //     final JSONObject jsonObject = new JSONObject();
    //     jsonObject.put("description", "The highest paid jobs");
        
    //     final JSONArray jobList = new JSONArray();
    //     HashMap<String, String> jobObject = new HashMap<String, String>();
    //     jobObject.put("name", "Kĩ sư hệ thống");
    //     jobObject.put("value", "50");
    //     jobObject.put("growth", "2");
    //     jobList.add(jobObject);
    //     jobList.add(jobObject);
    //     jsonObject.put("jobList", jobList);
    //     return jsonObject;

    // }
    

}