package com.hust.company;

import java.util.ArrayList;
import java.util.Arrays;
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

    public JSONObject searchCompany(final String companyName){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list search");
        final List<Object[]> listId = companyRepository.getIdCompanyByName(companyName);
        List<String> idComanyList = new ArrayList<>();
        if(listId.size() != 0){
            for(final Object[] ob : listId){
                System.out.println(ob[0].toString());
                idComanyList.add(ob[0].toString());
            }
            // idComanyList.add("712");
            // idComanyList.add("1");
            final List<Object[]> listCompany = companyRepository.searchCompany(idComanyList);
            final JSONArray companyList = new JSONArray();
            for(final Object[] ob1 : listCompany){
                final HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id", ob1[1].toString());
                companyObject.put("name", ob1[2].toString());
                companyObject.put("numJob", ob1[3].toString());
                companyObject.put("location", ob1[5].toString());
                companyList.add(companyObject);
            }
            jsonObject.put("result", companyList);
        }
        
        return jsonObject;
    }

    public JSONObject getCompanyList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list");

        final JSONArray companyList = new JSONArray();

        final List<Object[]> list = companyRepository.getCompanyList();
        for(final Object[] ob : list){
            final HashMap<String, Object> companyObject = new HashMap<String, Object>();
            List<Object[]> numJob = companyRepository.getNumJobByCompany((int)ob[0]);
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
            try {
                companyObject.put("numJob", numJob.get(0)[0]);
            }
            catch (Exception e ){
                System.out.println(e);
                continue;
            }
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

    public JSONObject getBusinessLinesOfTheCompany(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The business lines of the company");

        final JSONArray businessLines = new JSONArray();

        final List<Object[]> list = companyRepository.getBusinessLinesOfTheCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            
            businessLines.add(industryObject);
        }
        jsonObject.put("result", businessLines);
        return jsonObject;

    }

    public JSONObject getCompanyInfo(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company info");

        final List<Object[]> list = companyRepository.getCompanyInfo(id);
        System.out.println(id);
        final HashMap<String, Object> companyObject = new HashMap<String, Object>();
        for(final Object[] ob : list){
            
            companyObject.put("id", id);
            companyObject.put("name", ob[0].toString());
//            companyObject.put("phonenumber", ob[1].toString());
//            companyObject.put("description", ob[0].toString());
//            companyObject.put("founded_year", ob[3].toString());
//            companyObject.put("website", ob[4].toString());
            companyObject.put("location", ob[5].toString());
            companyObject.put("num_job",(int) Double.parseDouble(ob[6].toString()));
//            companyObject.put("logo", ob[0].toString());
        }
        jsonObject.put("result", companyObject);
        return jsonObject;

    }

    public JSONObject getRelatedCompany(final String id){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company related");

        final JSONArray relatedCompany = new JSONArray();

        final List<Object[]> list = companyRepository.getRelatedCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, String> companyObject = new HashMap<String, String>();
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


    public JSONObject getRecentJobByCompany(final String id){
        System.out.println(id);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The current job by company");

        final JSONArray jobList = new JSONArray();

        final List<Object[]> list = companyRepository.getRecentJobByCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> jobObject = new HashMap<String, Object>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("job_name", ob[1].toString());
            jobObject.put("province", ob[2].toString());
            jobObject.put("min_salary", ob[3].toString());
            jobObject.put("max_salary", ob[4].toString());
            jobObject.put("num_job",(int) (double) ob[5]);
            // jobObject.put("industry", ob[0].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("result", jobList);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by period of time");

        final JSONArray periods = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByPeriodOfTime(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> period = new HashMap<String, Object>();
            period.put("id", ob[0].toString());
            period.put("province", ob[1].toString());
            period.put("timestamp", ob[2].toString());
            period.put("num_job", (int) (double) ob[3]);
            // period.put("year", ob[0].toString());
            // period.put("quarter", ob[0].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;

    }

    public JSONObject getJobDemandByLiteracy(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByLiteracy(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> literacy = new HashMap<String, Object>();
            literacy.put("id", ob[0].toString());
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[2].toString());
            literacy.put("literacy", ob[3].toString());
            literacy.put("num_job", (int) (double) ob[4]);
            // literacy.put("year", ob[0].toString());
            // literacy.put("quarter", ob[0].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByAge(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> ageRange = new HashMap<String, Object>();
            ageRange.put("id", ob[0].toString());
            ageRange.put("idTime", ob[1].toString());
            ageRange.put("timestamp", ob[2].toString());
            ageRange.put("age", ob[3].toString());
            ageRange.put("gender", ob[4].toString());
            ageRange.put("num_job", (int) (double) ob[5]);
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