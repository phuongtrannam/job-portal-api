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

    public JSONObject getJobRelated(String idJob){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job realted");

        final JSONArray jobArr = new JSONArray();

        List<Object[]> list = jobRepository.getJobRelated(idJob);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[2].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("numJob", ob[1].toString());
            // jobObject.put("gender", ob[4].toString());
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getSkillRequired(String idJob){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job info");

        final JSONArray skillArr = new JSONArray();

        List<Object[]> list = jobRepository.getSkillRequired(idJob);
        for(Object[] ob : list){
            HashMap<String, String> skillObject = new HashMap<String, String>();
            skillObject.put("id", ob[0].toString());
            skillObject.put("name", ob[1].toString());
            skillObject.put("description", ob[2].toString());
            
            skillArr.add(skillObject);
        }
        jsonObject.put("result", skillArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by period of time");

        final JSONArray periods = new JSONArray();

        List<Object[]> list = jobRepository.getJobDemandByPeriodOfTime(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("timestamp", ob[0].toString());
            period.put("numJob", ob[1].toString());
            period.put("growth", ob[2].toString());
            
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

    public JSONObject getAverageSalary(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary");

        final JSONArray periods = new JSONArray();

        List<Object[]> list = jobRepository.getAverageSalary(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> period = new HashMap<String, String>();
            period.put("timestamp", ob[0].toString());
            period.put("averageSalay", ob[1].toString());
            period.put("growth", ob[2].toString());
            
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;
    }

    public JSONObject getJobDemandInSubRegion(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand in sub region");

        final JSONArray subRegions = new JSONArray();

        List<Object[]> list = jobRepository.getJobDemandInSubRegion(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> subRegion = new HashMap<String, String>();
            subRegion.put("timestamp", ob[0].toString());
            subRegion.put("idSubRegion", ob[0].toString());
            subRegion.put("regionName", ob[0].toString());
            subRegion.put("numJob", ob[1].toString());
            subRegion.put("growth", ob[2].toString());
            
            subRegions.add(subRegion);
        }
        jsonObject.put("result", subRegions);
        return jsonObject;
    }

    public JSONObject getAverageSalaryInSubRegion(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary in sub region");

        final JSONArray subRegions = new JSONArray();

        List<Object[]> list = jobRepository.getAverageSalaryInSubRegion(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> subRegion = new HashMap<String, String>();
            subRegion.put("timestamp", ob[0].toString());
            subRegion.put("idSubRegion", ob[0].toString());
            subRegion.put("regionName", ob[0].toString());
            subRegion.put("averageSalary", ob[1].toString());
            subRegion.put("growth", ob[2].toString());
            
            subRegions.add(subRegion);
        }
        jsonObject.put("result", subRegions);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age");

        final JSONArray ageRanges = new JSONArray();

        List<Object[]> list = jobRepository.getJobDemandByAge(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("timestamp", ob[0].toString());
            ageRange.put("numJob", ob[0].toString());
            ageRange.put("gender", ob[0].toString());
            ageRange.put("age", ob[1].toString());
            
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(String idJob, String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();

        List<Object[]> list = jobRepository.getJobDemandByLiteracy(idJob, idLocation);
        for(Object[] ob : list){
            HashMap<String, String> literacy = new HashMap<String, String>();
            literacy.put("timestamp", ob[0].toString());
            literacy.put("numJob", ob[0].toString());
            literacy.put("literacy", ob[0].toString());
            literacy.put("growth", ob[1].toString());
            
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }
}