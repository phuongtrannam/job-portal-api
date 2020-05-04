package com.hust.market.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public class Utils {

    public JSONObject convertJobsHighestToJSON(List<Object[]> listJobs, String region){
        final JSONObject regionObject = new JSONObject();
        String timeStramp = listJobs.get(0)[1].toString();
        System.out.println(timeStramp);
        JSONArray arrayTimeStamp = new JSONArray();
        JSONObject objectTimeStamp = new JSONObject();
        JSONArray arrayJobs = new JSONArray();
        for (Object[] ob: listJobs) {
//                System.out.println(ob[3].toString());
            System.out.println(ob[0].toString());
//                System.out.println(ob[1].toString().equals(timeStramp));
            if (!ob[1].toString().equals(timeStramp)) {
                objectTimeStamp.put(timeStramp, arrayJobs);
                arrayTimeStamp.add(objectTimeStamp);
//                    System.out.println(arrayJobs.toString());
                objectTimeStamp = new JSONObject();
                arrayJobs = new JSONArray();
                timeStramp = ob[1].toString();
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
        objectTimeStamp.put(timeStramp, arrayJobs);
        arrayTimeStamp.add(objectTimeStamp);
        regionObject.put(region,arrayTimeStamp);

        return regionObject;
    }
}
