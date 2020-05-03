package com.hust.market;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Service
public class MarketService {

    public JSONObject getJobsHighestSalary(){
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

    public JSONObject getJobsHighestRecruitment(){
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