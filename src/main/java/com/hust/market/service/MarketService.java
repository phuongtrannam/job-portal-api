package com.hust.market.service;
import com.hust.market.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private Utils utils;

    private List<String> majorRegion = Arrays.asList("Hà Nội", "Thành phố Hồ Chí Minh");


    public JSONObject getJobsHighestSalary(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest salary jobs");

        // muc luong ca nuoc
        List<Object[]> listJobsCountry = marketRepository.getJobsHighestSalary();
        jsonObject.put("Ca Nuoc",utils.convertTopEntityHighestToJSON(listJobsCountry));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONArray regionObject = new JSONArray();
            List<Object[]> listJobsByRegion = marketRepository.getJobsHighestSalaryByRegion(region);
            regionObject = utils.convertTopEntityHighestToJSON(listJobsByRegion);
            jsonObject.put(region,regionObject);
        }
        return jsonObject;
    }

    public JSONObject getJobsHighestRecruitment(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest recruitment jobs");

        // muc luong ca nuoc
        List<Object[]> listJobsCountry = marketRepository.getJobsHighestRecruitment();
        jsonObject.put("Ca Nuoc",utils.convertTopEntityHighestToJSON(listJobsCountry));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONArray regionObject = new JSONArray();
            List<Object[]> listJobsByRegion = marketRepository.getJobsHighestRecruitmentByRegion(region);
            regionObject = utils.convertTopEntityHighestToJSON(listJobsByRegion);
            jsonObject.put(region,regionObject);
        }
        return jsonObject;
    }

    public JSONObject getCompaniesHighestRecruitment(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest recruitment companies");

        // muc luong ca nuoc
        List<Object[]> listCompaniesCountry = marketRepository.getCompaniesHighestRecruitment();
        jsonObject.put("Ca Nuoc",utils.convertTopEntityHighestToJSON(listCompaniesCountry));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONArray regionObject = new JSONArray();
            List<Object[]> listCompaniesByRegion = marketRepository.getCompaniesHighestRecruitmentByRegion(region);
            regionObject = utils.convertTopEntityHighestToJSON(listCompaniesByRegion);
            jsonObject.put(region,regionObject);
        }
        return jsonObject;
    }

    public JSONObject getCompaniesHighestSalary(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest salary companies");

        // muc luong ca nuoc
        List<Object[]> listCompaniesCountry = marketRepository.getCompaniesHighestSalary();
        jsonObject.put("Ca Nuoc",utils.convertTopEntityHighestToJSON(listCompaniesCountry));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONArray regionObject = new JSONArray();
            List<Object[]> listCompaniesByRegion = marketRepository.getCompaniesHighestSalaryByRegion(region);
            regionObject = utils.convertTopEntityHighestToJSON(listCompaniesByRegion);
            System.out.println(regionObject);
            jsonObject.put(region,regionObject);
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByAge(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest paid jobs");

        final JSONArray array = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("name", "Giám đốc kinh doanh");
        jobDetail.put("value", "50");
        jobDetail.put("growth", "-0.5");
        jobDetail.put("hỉing", "86");
        array.add(jobDetail);
        array.add(jobDetail);
        jsonObject.put("detail",array);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByLiteracy(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest paid jobs");

        final JSONArray array = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("name", "Giám đốc kinh doanh");
        jobDetail.put("value", "50");
        jobDetail.put("growth", "-0.5");
        jobDetail.put("hỉing", "86");
        array.add(jobDetail);
        array.add(jobDetail);
        jsonObject.put("detail",array);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByIndustry(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest paid jobs");

        final JSONArray array = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("name", "Giám đốc kinh doanh");
        jobDetail.put("value", "50");
        jobDetail.put("growth", "-0.5");
        jobDetail.put("hỉing", "86");
        array.add(jobDetail);
        array.add(jobDetail);
        jsonObject.put("detail",array);
        return jsonObject;
    }
    

    public JSONObject getRecruitmentDemandByPeriodOfTime(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest paid jobs");

        final JSONArray array = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("name", "Giám đốc kinh doanh");
        jobDetail.put("value", "50");
        jobDetail.put("growth", "-0.5");
        jobDetail.put("hỉing", "86");
        array.add(jobDetail);
        array.add(jobDetail);
        jsonObject.put("detail",array);
        return jsonObject;
    }
    public JSONObject getAverageSalaryByIndustry(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest paid jobs");

        final JSONArray array = new JSONArray();
        HashMap<String, String> jobDetail = new HashMap<String, String>();
        jobDetail.put("name", "Giám đốc kinh doanh");
        jobDetail.put("value", "50");
        jobDetail.put("growth", "-0.5");
        jobDetail.put("hỉing", "86");
        array.add(jobDetail);
        array.add(jobDetail);
        jsonObject.put("detail",array);
        return jsonObject;
    }
}