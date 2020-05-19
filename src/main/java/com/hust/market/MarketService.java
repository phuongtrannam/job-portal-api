package com.hust.market;
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

    private final MarketRepository marketRepository;

    private List<String> majorRegion = Arrays.asList("Hà Nội", "Thành phố Hồ Chí Minh");

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }


    public JSONObject getJobsHighestSalary(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest salary jobs");

        return jsonObject;
    }

    public JSONObject getJobsHighestRecruitment(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest recruitment jobs");

        return jsonObject;
    }

    public JSONObject getCompaniesHighestRecruitment(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest recruitment companies");

        return jsonObject;
    }

    public JSONObject getCompaniesHighestSalary(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest salary companies");

        System.out.println(jsonObject);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandWithAgeGender(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "Recruitment Demand With Age Gender");

        System.out.println(jsonObject);
        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByLiteracy(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest paid jobs");

        return jsonObject;
    }

    public JSONObject getRecruitmentDemandByIndustry(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "Recruitment Industries");

        System.out.println(jsonObject);
        return jsonObject;
    }
    

    public JSONObject getRecruitmentDemandByPeriodOfTime(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "Recruitment By Period Of Time");


        return jsonObject;
    }
    public JSONObject getAverageSalaryByIndustry(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", "the highest salary industries");

        System.out.println(jsonObject);
        return jsonObject;
    }
}