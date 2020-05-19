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

    public JSONObject getIndustryList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The industry list");

        final JSONArray industryList = new JSONArray();

        List<Object[]> list = industryRepository.getIndustryList();
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryObject.put("description", ob[1].toString());
            industryObject.put("numJob", ob[0].toString());
            industryObject.put("averageSalary", ob[0].toString());
            industryObject.put("growth", ob[0].toString());
            industryList.add(industryObject);
        }
        jsonObject.put("value", industryList);
        return jsonObject;
    }

    public JSONObject getJobListByIndustry(String industryId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job list by industry");

        final JSONArray jobList = new JSONArray();

        List<Object[]> list = industryRepository.getJobListByIndustry(industryId);
        String idJob = "";
        double numJob = 0;
        HashMap<String, String> jobObject = new HashMap<String, String>();
//        System.out.println(list);
        for(Object[] ob : list){
            if(ob[0].toString().equals(idJob)) {
                double growth = ((numJob/(double)ob[2]) - 1)*100;
                jobObject.put("numJob", String.valueOf(numJob));
                jobObject.put("growth", String.valueOf(round(growth,2)));
                jobList.add(jobObject);
                jobObject = new HashMap<String, String>();
                continue;
            }
            idJob = ob[0].toString();
            jobObject.put("id", ob[0].toString());
            jobObject.put("timestamp", ob[ob.length - 1].toString());
            jobObject.put("name", ob[1].toString());
//            jobObject.put("averageSalary", ob[3].toString());
            jobObject.put("minSalary", ob[3].toString());
            jobObject.put("maxSalary", ob[4].toString());
            numJob = (double) ob[2];
            // jobObject.put("description", ob[2].toString());
        }
        jsonObject.put("value", jobList);
        return jsonObject;
    }

    public JSONObject getTopCompanyByIndustry(String industryId, String locationId ){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top company by industry");

        final JSONArray companyList = new JSONArray();

        List<Object[]> list = industryRepository.getTopCompanyByIndustry(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("timestamp", ob[1].toString());
            companyObject.put("name", ob[2].toString());
            companyObject.put("averageSalary", ob[3].toString());
            companyObject.put("numJob", ob[6].toString());
            companyObject.put("growth", ob[6].toString());
            companyList.add(companyObject);
        }
        jsonObject.put("value", companyList);
        return jsonObject;
    }


    public JSONObject getJobDemandByIndustry(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry");

        final JSONArray periods = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandByIndustry(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("idTime", ob[0].toString());
            period.put("timestamp", ob[1].toString());
            period.put("numJob", ob[5].toString());
            period.put("growth", ob[5].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

    public JSONObject getJobDemandInSubRegion(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry in subregion");

        final JSONArray periods = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandInSubRegion(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("idTime", ob[0].toString());
            period.put("timestamp", ob[1].toString());
            period.put("idSubRegion", ob[0].toString());
            period.put("regionName", ob[0].toString());
            period.put("numJob", ob[5].toString());
            period.put("growth", ob[5].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

    public JSONObject getTopHiringCompany(String industryId, String locationId ){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring company");

        final JSONArray companyList = new JSONArray();

        List<Object[]> list = industryRepository.getTopHiringCompany(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("idCompany", ob[0].toString());
            companyObject.put("timestamp", ob[1].toString());
            companyObject.put("name", ob[2].toString());
            companyObject.put("averageSalary", ob[3].toString());
            companyObject.put("numJob", ob[6].toString());
            companyObject.put("growth", ob[6].toString());
            companyList.add(companyObject);
        }
        jsonObject.put("value", companyList);
        return jsonObject;
    }


    public JSONObject getTopHiringJob(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring job");

        final JSONArray jobArr = new JSONArray();
        List<Object[]> list = industryRepository.getTopHiringJob(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> job = new HashMap<String, String>();
            job.put("idTime", ob[0].toString());
            job.put("timestamp", ob[1].toString());
            job.put("idJob", ob[0].toString());
            job.put("jobName", ob[0].toString());
            job.put("numJob", ob[5].toString());
            job.put("growth", ob[5].toString());
            jobArr.add(job);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getHighestSalaryJob(String industryId, String locationId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The highest salary job");

        final JSONArray jobArr = new JSONArray();
        List<Object[]> list = industryRepository.getHighestSalaryJob(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> job = new HashMap<String, String>();
            job.put("idTime", ob[0].toString());
            job.put("timestamp", ob[1].toString());
            job.put("idJob", ob[0].toString());
            job.put("jobName", ob[0].toString());
            job.put("averageSalary", ob[5].toString());
            job.put("growth", ob[5].toString());
            job.put("numJob", ob[5].toString());
            jobArr.add(job);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandByAge(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("id_time", ob[1].toString());
            ageRange.put("timestamp", ob[3].toString());
            ageRange.put("age_range", ob[4].toString());
            ageRange.put("gender", ob[5].toString());
            ageRange.put("numJob", ob[6].toString());
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandByLiteracy(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> literacy = new HashMap<String, String>();
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[3].toString());
            literacy.put("literacy", ob[4].toString());
            literacy.put("numJob", ob[4].toString());
            literacy.put("growth", ob[4].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}