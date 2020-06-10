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
        JSONArray jobList;
        jsonObject.put("Name", "the highest salary jobs");
        List<Object[]> listObject  = marketRepository.getJobsHighestSalary();
        jobList = convertQueryToJSON(listObject, "Ca nuoc","Country");
        for(String region: majorRegion){
            listObject = marketRepository.getJobsHighestSalaryByRegion(region);
            jobList = concatArray(jobList, convertQueryToJSON(listObject, region,"Province"));
        }
//        System.out.println(jobList.size());
        jsonObject.put("result",jobList);
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

    private JSONArray convertQueryToJSON(List<Object[]> resultQuery,String region , String type_area){
        final JSONArray listObject = new JSONArray();
        for(Object[] ob : resultQuery){
            HashMap<String, String> object = new HashMap<>();
            object.put("time",ob[1].toString());
            object.put("name",ob[ob.length - 4].toString());
            object.put("value",ob[ob.length - 3].toString());
            object.put("growth",ob[ob.length - 2].toString());
            object.put("area", region);
            object.put("type",type_area);
            listObject.add(object);
        }
        return listObject;
    }

    private JSONArray concatArray(JSONArray... arrs) {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.size(); i++) {
                result.add(arr.get(i));
            }
        }
        return result;
    }
}