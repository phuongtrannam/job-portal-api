package com.hust.market.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;


public class Utils {

    // Tra ve dinh danh json cho cac doi truong top10 jobs, companies
    public JSONArray convertTopEntityHighestToJSON(List<Object[]> listJobs){
        String timeStamp = listJobs.get(0)[2].toString();
        System.out.println(timeStamp);
        String timeStramp = listJobs.get(0)[2].toString();
        // System.out.println(timeStramp);
        JSONArray arrayTimeStamp = new JSONArray();
        JSONObject objectTimeStamp = new JSONObject();
        JSONArray arrayJobs = new JSONArray();
        for (Object[] ob: listJobs) {
//                System.out.println(ob[3].toString());
//            System.out.println(ob[0].toString());
//                System.out.println(ob[1].toString().equals(timeStramp));
            if (!ob[2].toString().equals(timeStamp)) {
                objectTimeStamp.put(timeStamp, arrayJobs);
                arrayTimeStamp.add(objectTimeStamp);
//                    System.out.println(arrayJobs.toString());
                objectTimeStamp = new JSONObject();
                arrayJobs = new JSONArray();
                timeStamp = ob[2].toString();
            }
     
            HashMap<String, String> jobObject = new HashMap<>();
            jobObject.put("name", ob[ob.length - 4].toString());
            jobObject.put("value", ob[ob.length-3].toString());
            jobObject.put("growth", ob[ob.length -2].toString());
            jobObject.put("rank", ob[ob.length - 1].toString());
            jobObject.put("timestamp", ob[1].toString());
//                System.out.println(jobObject.toString());
            arrayJobs.add(jobObject);

        }
        objectTimeStamp = new JSONObject();
        objectTimeStamp.put(timeStamp, arrayJobs);
        arrayTimeStamp.add(objectTimeStamp);

        return arrayTimeStamp;
    }


    public JSONArray convertTopIndustriesToJSON(List<Object[]> listIndustries,List<Object[]> listAmountRecruitment){
        String timeStamp = listIndustries.get(0)[2].toString();
        System.out.println(timeStamp);
        double sumAmount = 0;
        double sumRatio = 0;
        double amountRecruitment = getAmountRecruitment(listAmountRecruitment,listIndustries.get(0)[0].toString());
        JSONArray arrayTimeStamp = new JSONArray();
        JSONObject objectTimeStamp = new JSONObject();
        JSONArray arrayJobs = new JSONArray();
        for (Object[] ob: listIndustries) {
//                System.out.println(ob[3].toString());
//            System.out.println(ob[0].toString());
//                System.out.println(ob[1].toString().equals(timeStramp));
            if (!ob[2].toString().equals(timeStamp)) {
                HashMap<String, String> jobObject = new HashMap<>();
                jobObject.put("name", "other");
                jobObject.put("value", String.valueOf(amountRecruitment - sumAmount));
                jobObject.put("ratio", String.valueOf((double)Math.round((100.0 - sumRatio)*100)/100));
                jobObject.put("timestamp", timeStamp);
//                System.out.println(jobObject.toString());
                arrayJobs.add(jobObject);

                objectTimeStamp.put(timeStamp, arrayJobs);
                arrayTimeStamp.add(objectTimeStamp);
//                    System.out.println(arrayJobs.toString());
                objectTimeStamp = new JSONObject();
                arrayJobs = new JSONArray();
                timeStamp = ob[2].toString();
            }
            double amountIndustry = (double) ob[ob.length -3];
            double ratio = (double) Math.round((amountIndustry/amountRecruitment)*10000)/100;
            sumAmount += amountIndustry;
            sumRatio += ratio;
            HashMap<String, String> jobObject = new HashMap<>();
            jobObject.put("name", ob[ob.length - 4].toString());
            jobObject.put("value", ob[ob.length-3].toString());
            jobObject.put("growth", ob[ob.length -2].toString());
            jobObject.put("rank", ob[ob.length - 1].toString());
            jobObject.put("ratio", String.valueOf(ratio));
            jobObject.put("timestamp", timeStamp);
//                System.out.println(jobObject.toString());
            arrayJobs.add(jobObject);

        }
        HashMap<String, String> jobObject = new HashMap<>();
        jobObject.put("name", "other");
        jobObject.put("value", String.valueOf(amountRecruitment - sumAmount));
        jobObject.put("ratio", String.valueOf((double)Math.round((100.0 - sumRatio)*100)/100));
        jobObject.put("timestamp", timeStamp);
//                System.out.println(jobObject.toString());
        arrayJobs.add(jobObject);

        objectTimeStamp = new JSONObject();
        objectTimeStamp.put(timeStamp, arrayJobs);
        arrayTimeStamp.add(objectTimeStamp);

        return arrayTimeStamp;
    }
    public Double getAmountRecruitment(List<Object[]> listAmountRecruitment, String idTime){
        for (Object[] ob : listAmountRecruitment){
            if( idTime.equals(ob[0].toString())){
                return (Double) ob[ob.length-1];
            }
        }
        return null;
    }

    public JSONObject convertRecruitmentWithAgeGenderToJSON(List<Object[]> listRecruitmentWithAgeGender){
        JSONObject  jsonObject = new JSONObject();
        Object[] startObject = listRecruitmentWithAgeGender.get(0);
        String timeStamp = startObject[2].toString();
        String check_age = startObject[startObject.length - 3].toString();
        JSONArray arrayAge = new JSONArray();
        JSONObject objectAge = new JSONObject();
        JSONArray arrayGender = new JSONArray();


        for (Object[] ob: listRecruitmentWithAgeGender){
            if(!check_age.equals(ob[ob.length -3].toString())){
                objectAge.put(check_age,arrayGender);
                arrayAge.add(objectAge);
                if(!timeStamp.equals(ob[2].toString())) {
                    jsonObject.put(timeStamp,arrayAge);
                    arrayAge = new JSONArray();
                    timeStamp = ob[2].toString();
                }
                objectAge = new JSONObject();
                arrayGender= new JSONArray();
                check_age = ob[ob.length - 3].toString();
            }

            HashMap<String, String> genderObject = new HashMap<>();
            genderObject.put(ob[ob.length - 2].toString(),ob[ob.length -1].toString());
            arrayGender.add(genderObject);
        }
        objectAge.put(check_age,arrayGender);
        arrayAge.add(objectAge);
        jsonObject.put(timeStamp,arrayAge);

        return jsonObject;
    }

    public JSONObject convertRecruitmentWithLiteracyToJSON(List<Object[]> listRecruitmentWithLiteracy){
        final JSONObject jsonObject = new JSONObject();

        String timeStamp = listRecruitmentWithLiteracy.get(0)[2].toString();

        JSONArray arrayLiteracy = new JSONArray();

        for (Object[] ob : listRecruitmentWithLiteracy){
            if(!timeStamp.equals(ob[2].toString())){
                jsonObject.put(timeStamp,arrayLiteracy);
                arrayLiteracy = new JSONArray();
                timeStamp = ob[2].toString();
            }
            HashMap<String, String> literacyObject = new HashMap<>();
            literacyObject.put(ob[ob.length -2].toString(),ob[ob.length -1].toString());
            arrayLiteracy.add(literacyObject);
        }
        jsonObject.put(timeStamp,arrayLiteracy);
        return jsonObject;
    }
}
