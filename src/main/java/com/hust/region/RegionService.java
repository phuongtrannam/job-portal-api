package com.hust.region;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // public JSONObject basicRegionSearching(){

    //     final JSONObject jsonObject = new JSONObject();
    //     jsonObject.put("description", "The company list");

    //     final JSONArray companyList = new JSONArray();

    //     List<Object[]> list = regionRepository.getCompanyList();
    //     for(Object[] ob : list){
    //         HashMap<String, String> companyObject = new HashMap<String, String>();
    //         companyObject.put("id", ob[0].toString());
    //         companyObject.put("name", ob[1].toString());
    //         companyList.add(companyObject);
    //     }
    //     jsonObject.put("result", companyList);
    //     return jsonObject;

    // }

    public JSONObject getRootRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The root region");

        final JSONArray rootRegion = new JSONArray();

        List<Object[]> list = regionRepository.getRootRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", ob[0].toString());
            regionObject.put("name", ob[1].toString());
            regionObject.put("type", ob[2].toString());
            rootRegion.add(regionObject);
        }
        jsonObject.put("result", rootRegion);
        return jsonObject;
    }

    public JSONObject getSubRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The sub region");

        final JSONArray subRegion = new JSONArray();

        List<Object[]> list = regionRepository.getSubRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", ob[0].toString());
            regionObject.put("name", ob[1].toString());
            regionObject.put("type", ob[2].toString());
            subRegion.add(regionObject);
        }
        jsonObject.put("result", subRegion);
        return jsonObject;
    }

    public JSONObject getRootAndSubRegions(String id) {
        final JSONObject jsonObject = new JSONObject();
        final JSONObject rootObject = getRootRegion(id);
        final JSONObject subObject = getSubRegion(id);
        jsonObject.put("id", id);
        jsonObject.put("description", "The root and sub region");
        jsonObject.put("sub", subObject.get("result"));
        jsonObject.put("root", rootObject.get("result"));
        return jsonObject;
    }

    public JSONObject getRelatedRegions(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The related region");

        final JSONArray regionArr = new JSONArray();

        List<Object[]> list = regionRepository.getRelatedRegions(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", ob[0].toString());
            regionObject.put("name", ob[1].toString());
            regionObject.put("type", ob[2].toString());
            regionArr.add(regionObject);
        }
        jsonObject.put("result", regionArr);
        return jsonObject;
    }

    public JSONObject getNumberJobPostingInRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The number of jos posting in the region");

        final JSONArray data = new JSONArray();

        List<Object[]> list = regionRepository.getNumberJobPostingInRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> timestamp = new HashMap<String, String>();
            timestamp.put("id", ob[0].toString());
            timestamp.put("name", ob[1].toString());
            timestamp.put("type", ob[2].toString());
            data.add(timestamp);
        }
        jsonObject.put("result", data);
        return jsonObject;
    }
    
    public JSONObject getNumberCompanyInRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The number of jos posting in the region");

        final JSONArray data = new JSONArray();

        List<Object[]> list = regionRepository.getNumberCompanyInRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> timestamp = new HashMap<String, String>();
            timestamp.put("id", ob[0].toString());
            timestamp.put("name", ob[1].toString());
            timestamp.put("type", ob[2].toString());
            data.add(timestamp);
        }
        jsonObject.put("result", data);
        return jsonObject;
    }

    public JSONObject getAverageSalaryInRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The number of jos posting in the region");

        final JSONArray data = new JSONArray();

        List<Object[]> list = regionRepository.getAverageSalaryInRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> timestamp = new HashMap<String, String>();
            timestamp.put("id", ob[0].toString());
            timestamp.put("name", ob[1].toString());
            timestamp.put("type", ob[2].toString());
            data.add(timestamp);
        }
        jsonObject.put("result", data);
        return jsonObject;
    }


    public JSONObject getAverageAgeInRegion(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The number of jos posting in the region");

        final JSONArray data = new JSONArray();

        List<Object[]> list = regionRepository.getAverageAgeInRegion(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> timestamp = new HashMap<String, String>();
            timestamp.put("id", ob[0].toString());
            timestamp.put("name", ob[1].toString());
            timestamp.put("type", ob[2].toString());
            data.add(timestamp);
        }
        jsonObject.put("result", data);
        return jsonObject;
    }


    public JSONObject getDashboardData(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The dashboard data");

        // int numJobPosting = Integer.parseInt((String) getNumberJobPostingInRegion(id).get("result"));
        // int numCompany = regionRepository.getNumberCompanyInRegion(id).get("result");
        // float averageSalary = regionRepository.getAverageSalaryInRegion(id).get("result");
        // float averageAge = regionRepository.getAverageAgeInRegion(id).get("result");
        // jsonObject.put("numJobPosting", numJobPosting);
        // jsonObject.put("numCompany", numCompany);
        // jsonObject.put("averageSalary", averageSalary);
        // jsonObject.put("averageAge", averageAge);
        return jsonObject;
    }

    public JSONObject getAverageSalaryByIndustry(String id){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The average salary by industry");

        final JSONArray industryArr = new JSONArray();

        List<Object[]> list = regionRepository.getAverageSalaryByIndustry(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryObject.put("averageSalary", ob[2].toString());
            industryObject.put("growth", ob[1].toString());
            industryObject.put("idTime", ob[4].toString());
            industryObject.put("timestamp", ob[5].toString());
            industryArr.add(industryObject);
        }
        jsonObject.put("result", industryArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByIndustry(String id){
        System.out.println(id);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by industry");

        final JSONArray industryArr = new JSONArray();

        List<Object[]> list = regionRepository.getJobDemandByIndustry(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryObject.put("numJob", ob[2].toString());
            industryObject.put("growth", ob[1].toString());
            industryObject.put("idTime", ob[4].toString());
            industryObject.put("timestamp", ob[5].toString());
            industryArr.add(industryObject);
        }
        jsonObject.put("result", industryArr);
        return jsonObject;
    }

    public JSONObject getHighestSalaryJobs(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job highest salary jobs");

        final JSONArray jobArr = new JSONArray();
        System.out.println(id);
        List<Object[]> list = regionRepository.getHighestSalaryJobs(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("growth", ob[0].toString());
            jobObject.put("numJob", ob[3].toString());
            jobObject.put("timestamp", ob[0].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getHighestDemandJobs(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job highest demand jobs");

        final JSONArray jobArr = new JSONArray();
        System.out.println(id);
        List<Object[]> list = regionRepository.getHighestDemandJobs(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("numJob", ob[3].toString());
            jobObject.put("growth", ob[0].toString());
            jobObject.put("averageSalary", ob[2].toString());
            jobObject.put("timestamp", ob[0].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getHighestPayingCompanies(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job highest paying companies");

        final JSONArray companyArr = new JSONArray();
        System.out.println(id);
        List<Object[]> list = regionRepository.getHighestPayingCompanies(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
            companyObject.put("averageSalary", ob[3].toString());
            companyObject.put("growth", ob[0].toString());
            companyObject.put("numJob", ob[2].toString());
            companyObject.put("timestamp", ob[0].toString());
            companyArr.add(companyObject);
        }
        jsonObject.put("result", companyArr);
        return jsonObject;
    }
    
    public JSONObject getTopHiringCompanies(String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job highest paying companies");

        final JSONArray companyArr = new JSONArray();
        System.out.println(id);
        List<Object[]> list = regionRepository.getTopHiringCompanies(id);
        System.out.println(id);
        for(Object[] ob : list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
            companyObject.put("numJob", ob[3].toString());
            companyObject.put("growth", ob[0].toString());
            companyObject.put("averageSalary", ob[2].toString());
            companyObject.put("timestamp", ob[0].toString());
            companyArr.add(companyObject);
        }
        jsonObject.put("result", companyArr);
        return jsonObject;
    }
}