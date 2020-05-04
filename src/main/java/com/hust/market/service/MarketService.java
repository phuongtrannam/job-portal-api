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
        jsonObject.put("name", "the highest salary jobs");
        final JSONArray arrayRegion = new JSONArray();

        // muc luong ca nuoc
        List<Object[]> listJobsCountry = marketRepository.getJobsHighestSalary();
        arrayRegion.add(utils.convertJobsHighestToJSON(listJobsCountry, "Ca Nuoc"));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONObject regionObject = new JSONObject();
            List<Object[]> listJobsByRegion = marketRepository.getJobsHighestSalaryByRegion(region);
            regionObject = utils.convertJobsHighestToJSON(listJobsByRegion, region);
            arrayRegion.add(regionObject);
        }
        jsonObject.put("detail",arrayRegion);
        return jsonObject;
    }

    public JSONObject getJobsHighestRecruitment(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "the highest salary jobs");
        final JSONArray arrayRegion = new JSONArray();

        // muc luong ca nuoc
        List<Object[]> listJobsCountry = marketRepository.getJobsHighestRecruitment();
        arrayRegion.add(utils.convertJobsHighestToJSON(listJobsCountry, "Ca Nuoc"));

        // muc luong theo khu vuc
        for (String region : majorRegion){
            JSONObject regionObject = new JSONObject();
            List<Object[]> listJobsByRegion = marketRepository.getJobsHighestRecruitmentByRegion(region);
            regionObject = utils.convertJobsHighestToJSON(listJobsByRegion, region);
            arrayRegion.add(regionObject);
        }
        jsonObject.put("detail",arrayRegion);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByCompany(){
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