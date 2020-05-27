package com.hust.job;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

    public JSONObject basicSearchJob(final String queryContent){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The basic search job");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.basicSearchJob(queryContent);
        for(final Object[] ob : list){
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            jobObject.put("gender", ob[3].toString());
            jobObject.put("minSalary", ob[4].toString());
            jobObject.put("maxSalary", ob[5].toString());
            jobObject.put("numJob", ob[6].toString());
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject advancedSearchJob(final String queryContent, final String location, final String industry, final int minSalary, final int maxSalary){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The advanced search job");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.advancedSearchJob(queryContent, location, industry, minSalary, maxSalary);
        for(final Object[] ob : list){
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            jobObject.put("gender", ob[3].toString());
            jobObject.put("minSalary", ob[4].toString());
            jobObject.put("maxSalary", ob[5].toString());
            jobObject.put("numJob", ob[6].toString());
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getJobDescription(String idJob){
        final JSONObject jobDescription = new JSONObject();
        List<Object[]> list = jobRepository.getJobDescription(idJob);
        jobDescription.put("name", list.get(0)[1].toString());
        jobDescription.put("descripton", list.get(0)[1].toString());
        return jobDescription;
    }

    public List<String> getJobLiteracy(String idJob){
        List<String> literacies = new ArrayList<String>(); 
        List<Object[]> list = jobRepository.getJobLiteracy(idJob);
        for(final Object[] ob : list){
            System.out.println(ob[2].toString());
            literacies.add(ob[2].toString());
        }
        return literacies;
    }

    public JSONObject getNumberOfJob(String idJob){   

        List<Object[]> list = jobRepository.getNumberOfJob(idJob);
        System.out.println(list.get(1)[4]);
        DecimalFormat df = new DecimalFormat("##.##");
        float growth = Float.parseFloat(list.get(1)[4].toString())/Float.parseFloat(list.get(0)[4].toString());
        final JSONObject jobDescription = new JSONObject();
        jobDescription.put("minSalary", list.get(1)[2].toString());
        jobDescription.put("maxSalary", list.get(1)[3].toString());
        jobDescription.put("numJob", list.get(1)[4].toString());
        jobDescription.put("growth", df.format(growth));
        return jobDescription;
    }

    public List<String> getJobIndustry(String idJob){
        List<String> industries = new ArrayList<String>(); 
        List<Object[]> list = jobRepository.getIndustryOfJob(idJob);
        for(final Object[] ob : list){
            System.out.println(ob[1].toString());
            industries.add(ob[1].toString());
        }
        return industries;
    }

    public JSONObject getJobInfo(String idJob){
        final JSONObject jobDescription = getJobDescription(idJob);
        final List<String> literacies = getJobLiteracy(idJob);
        final JSONObject jobSalaryAndNumber = getNumberOfJob(idJob);
        final List<String> industries = getJobIndustry(idJob);

        final JSONObject jobInfo = new JSONObject();
        jobInfo.put("name", jobDescription.get("name"));
        jobInfo.put("descripton", jobDescription.get("descripton"));
        jobInfo.put("minSalary", jobSalaryAndNumber.get("minSalary"));
        jobInfo.put("maxSalary", jobSalaryAndNumber.get("maxSalary"));
        jobInfo.put("numJob", jobSalaryAndNumber.get("numJob"));
        jobInfo.put("growth", jobSalaryAndNumber.get("growth"));
        jobInfo.put("industry", industries);
        jobInfo.put("literacy",literacies);
//      jobObject.put("gender", ob[4].toString());
//      jobObject.put("jobType", ob[5].toString());

        return jobInfo;
    }

    public JSONObject getJobRelated(final String idJob){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job realted");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.getJobRelated(idJob);
        for(final Object[] ob : list){
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[3].toString());
            jobObject.put("numJob", ob[4].toString());
            // jobObject.put("gender", ob[4].toString());
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getSkillRequired(final String idJob){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job info");

        final JSONArray skillArr = new JSONArray();

        final List<Object[]> list = jobRepository.getSkillRequired(idJob);
        for(final Object[] ob : list){
            final HashMap<String, String> skillObject = new HashMap<String, String>();
            // skillObject.put("id", ob[1].toString());
            skillObject.put("name", ob[2].toString());
            skillObject.put("description", ob[2].toString());
            
            skillArr.add(skillObject);
        }
        jsonObject.put("result", skillArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by period of time");
        List<String> timestamp = new ArrayList<String>(); 
        List<Float> data = new ArrayList<Float>(); 

        final List<Object[]> list = jobRepository.getJobDemandByPeriodOfTime(idJob, idLocation);
        String jobName = list.get(0)[2].toString();
        String region = list.get(0)[3].toString();
        for(final Object[] ob : list){
            timestamp.add(ob[1].toString());
            data.add(Float.parseFloat(ob[4].toString()));
        }
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("data", data);
        jsonObject.put("jobName", jobName);
        jsonObject.put("region", region);
        return jsonObject;
    }

    public JSONObject getAverageSalary(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary");

        List<String> timestamp = new ArrayList<String>(); 
        List<Integer> data = new ArrayList<Integer>(); 
        List<Float> growth = new ArrayList<Float>(); 
        final List<Object[]> list = jobRepository.getAverageSalary(idJob, idLocation);

        String jobName = list.get(0)[2].toString();
        String region = list.get(0)[3].toString();
        int i = 0;
        float previousValue = 1f;
        float currentGrowth;
        DecimalFormat df = new DecimalFormat("##.##");
        
        for(final Object[] ob : list){
            
            timestamp.add(ob[1].toString());
            data.add(Math.round(Float.parseFloat(ob[4].toString())));
            if(i == 0){
                growth.add(0f);
            }else{
                currentGrowth = Float.parseFloat(ob[4].toString())/previousValue;
                currentGrowth = Float.parseFloat(df.format(currentGrowth));
                growth.add(currentGrowth);
            }
            i++;
            previousValue = Float.parseFloat(ob[4].toString());
  
            
        }
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("data", data);
        jsonObject.put("growth", growth);
        jsonObject.put("jobName", jobName);
        jsonObject.put("region", region);
        return jsonObject;
    }

    public JSONObject getJobDemandInSubRegion(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand in sub region");

        final JSONArray subRegions = new JSONArray();

        final List<Object[]> list = jobRepository.getJobDemandInSubRegion(idJob, idLocation);
        for(final Object[] ob : list){
            final HashMap<String, String> subRegion = new HashMap<String, String>();
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

    public JSONObject getAverageSalaryInSubRegion(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary in sub region");

        final JSONArray subRegions = new JSONArray();

        final List<Object[]> list = jobRepository.getAverageSalaryInSubRegion(idJob, idLocation);
        for(final Object[] ob : list){
            final HashMap<String, String> subRegion = new HashMap<String, String>();
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

    public JSONObject getJobDemandByAge(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age");

        final JSONArray ageRanges = new JSONArray();

        final List<Object[]> list = jobRepository.getJobDemandByAge(idJob, idLocation);
        for(final Object[] ob : list){
            final HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("timestamp", ob[0].toString());
            ageRange.put("numJob", ob[0].toString());
            ageRange.put("gender", ob[0].toString());
            ageRange.put("age", ob[1].toString());
            
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(final String idJob, final String idLocation){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();

        final List<Object[]> list = jobRepository.getJobDemandByLiteracy(idJob, idLocation);
        for(final Object[] ob : list){
            final HashMap<String, String> literacy = new HashMap<String, String>();
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