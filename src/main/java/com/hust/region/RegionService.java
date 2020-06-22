package com.hust.region;

import java.util.*;

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

    public JSONObject getRootRegion(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The root region");

        if(regionId.contains("P")){
            final JSONArray rootRegion = new JSONArray();
            final HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", "");
            regionObject.put("name", "Cả nước");
            regionObject.put("type", "Country");
            rootRegion.add(regionObject);
            jsonObject.put("result", rootRegion);
        }
        return jsonObject;
    }

    public JSONObject getSubRegion(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The sub region");

        final JSONArray subRegion = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getProvinceRegion();
            System.out.println(regionId);
            for(final Object[] ob : list){
                final HashMap<String, String> regionObject = new HashMap<String, String>();
                if (ob[1].toString().equals("Khác") || ob[1].toString().equals("Toàn quốc")){
                    continue;
                }
                regionObject.put("id", "P" +  ob[0].toString());
                regionObject.put("name", ob[1].toString());
                regionObject.put("type", "Province");
                subRegion.add(regionObject);
            }
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getDistinctByProvince(idProvince);
            System.out.println(regionId);
            for(final Object[] ob : list){
                final HashMap<String, String> regionObject = new HashMap<String, String>();
                regionObject.put("id", "");
                regionObject.put("name", ob[0].toString());
                regionObject.put("type", "District");
                subRegion.add(regionObject);
            }
        }
        jsonObject.put("result", subRegion);
        return jsonObject;
    }

    public JSONObject getRootAndSubRegions(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        if(regionId.equals("P0")){
            final JSONObject subObject = getSubRegion(regionId);
            jsonObject.put("id", regionId);
            jsonObject.put("description", "The root and sub region");
            jsonObject.put("sub", subObject.get("result"));
        }
        else if(regionId.contains("P")){
            final JSONObject rootObject = getRootRegion(regionId);
            final JSONObject subObject = getSubRegion(regionId);
            jsonObject.put("id", regionId);
            jsonObject.put("description", "The root and sub region");
            jsonObject.put("sub", subObject.get("result"));
            jsonObject.put("root", rootObject.get("result"));
        }
        return jsonObject;
    }

    public JSONObject getRelatedRegions(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The related region");

        final JSONArray regionArr = new JSONArray();

        final List<Object[]> list = regionRepository.getRelatedRegions(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", ob[0].toString());
            regionObject.put("name", ob[1].toString());
            regionObject.put("type", ob[2].toString()); // country, province, district
            regionArr.add(regionObject);
        }
        jsonObject.put("result", regionArr);
        return jsonObject;
    }

    public JSONObject getNumberJobPostingInRegion(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");
        final HashMap<String, Object> numJobObject = new HashMap<>();
        if(regionId.equals("P0")){
            getDataDashboardToJSONObject(numJobObject, regionRepository.getNumberJobPostingInCountry());
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            getDataDashboardToJSONObject(numJobObject, regionRepository.getNumberJobPostingInProvince(idProvince));
        }
        jsonObject.put("result", numJobObject);
        return jsonObject;
    }
    
    public JSONObject getNumberCompanyInRegion(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        final HashMap<String, Object> numCompanyObject = new HashMap<>();

        if(regionId.equals("P0")){
            getDataDashboardToJSONObject(numCompanyObject, regionRepository.getNumberCompanyInCountry());
        }
        else {
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            getDataDashboardToJSONObject(numCompanyObject, regionRepository.getNumberCompanyInProvince(idProvince));
        }
        jsonObject.put("result", numCompanyObject);
        return jsonObject;
    }

    private void getDataDashboardToJSONObject(final HashMap<String, Object> jsonObject, final List<Object[]> numberCompanyInCountry) {
        final List<Object[]> list = numberCompanyInCountry;
        if(checkListObjectNull(list)){
           return;
        };
        jsonObject.put("idTime", "T" + list.get(0)[0].toString());
        jsonObject.put("timeStamp", list.get(0)[1].toString());
        final double numJob = Double.parseDouble(list.get(0)[2].toString());
        final double preNumJob = Double.parseDouble(list.get(1)[2].toString());
        final double growth = ((numJob / preNumJob) - 1) * 100;
        jsonObject.put("data", (int) numJob);
        jsonObject.put("growth", round(growth, 2));
    }

    public JSONObject getAverageSalaryInRegion(final String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        final HashMap<String, Object> avgSalaryObject = new HashMap<>();

        final double sumSalary = 0;
        final double numJob = 0;
        final double avgSalary = 0;
        final double preAvgSalary = 0;
        final double growth = 100.0;

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getListSalaryInLastYearWithCountry();
            getJSONObjectAverageSalary(jsonObject, avgSalaryObject, sumSalary, numJob, preAvgSalary, growth, list, "data");
        }
        else {
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getListSalaryInLastYearWithProvince(idProvince);
            getJSONObjectAverageSalary(jsonObject, avgSalaryObject, sumSalary, numJob, preAvgSalary, growth, list, "data");
        }
        return jsonObject;
    }

    private void getJSONObjectAverageSalary(final JSONObject jsonObject, final HashMap<String, Object> avgSalaryObject, double sumSalary, double numJob, double preAvgSalary, double growth, final List<Object[]> list, final String salary) {
        double avgSalary;
        final String time = list.get(0)[0].toString();
        System.out.println(time);
        for (final Object[] ob : list) {
            if (!time.equals(ob[0].toString())) {
                avgSalary = sumSalary / numJob;
                if (preAvgSalary != 0) {
                    growth = ((avgSalary / preAvgSalary) - 1) * 100;
                }
                avgSalaryObject.put("idTime", "T" + time);
                avgSalaryObject.put("timeStamp", time);
                avgSalaryObject.put(salary, round(avgSalary, 2));
                avgSalaryObject.put("growth", round(growth, 2));
                preAvgSalary = avgSalary;
                avgSalary = 0;
                sumSalary = 0;
                numJob = 0;
            }
            sumSalary += (double) ob[1] * (double) ob[2];
            numJob += (double) ob[1];
        }
        if (growth == 100.0) {
            avgSalary = sumSalary / numJob;
            avgSalaryObject.put("idTime", time);
            avgSalaryObject.put("timeStamp", time);
            avgSalaryObject.put(salary, round(avgSalary, 2));
            avgSalaryObject.put("growth", round(growth, 2));
        }
        jsonObject.put("result", avgSalaryObject);
    }


    public JSONObject getAverageAgeInRegion(final String regionId) {

        final HashMap<String, Double> mapAvgAge = mapAvgAge();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        final HashMap<String, Object> avgAgeObject = new HashMap<>();

        final double sumAge = 0;
        final double numJob = 0;
        final double avgAge = 0;
        final double preAvgAge = 0;
        final double growth = 0;

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getAverageAgeInCountry();
            getJSONObjectAverageAge(mapAvgAge, jsonObject, avgAgeObject, sumAge, numJob, preAvgAge, growth, list);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getAverageAgeInProvince(idProvince);
            getJSONObjectAverageAge(mapAvgAge, jsonObject, avgAgeObject, sumAge, numJob, preAvgAge, growth, list);
        }
        return jsonObject;
    }

    private void getJSONObjectAverageAge(final HashMap<String, Double> mapAvgAge, final JSONObject jsonObject, final HashMap<String, Object> avgAgeObject, double sumAge, double numJob, double preAvgAge, double growth, final List<Object[]> list) {
        double avgAge;
        final String time = list.get(0)[0].toString();
        System.out.println(time);
        for(final Object[] ob: list){
            if(!time.equals(ob[0].toString())){
                avgAge = sumAge/numJob;
                if(preAvgAge != 0){
                    growth = avgAge - preAvgAge;
                }
                avgAgeObject.put("idTime", "T" +  time);
                avgAgeObject.put("timeStamp", time);
                avgAgeObject.put("data", round(avgAge, 2));
                avgAgeObject.put("growth", round(growth,2));
                preAvgAge = avgAge;
                avgAge = 0;
                sumAge = 0;
                numJob = 0;
            }
            final Double age = mapAvgAge.get(ob[2].toString());
            System.out.println(age + ":" + ob[2].toString());
            sumAge += (double)ob[1] * age;
            numJob += (double)ob[1];
        }
        if(growth == 0){
            avgAge = sumAge/numJob;
            avgAgeObject.put("idTime", time);
            avgAgeObject.put("timeStamp", time);
            avgAgeObject.put("data", round(avgAge, 2));
            avgAgeObject.put("growth", round(growth,2));
        }
        jsonObject.put("result", avgAgeObject);
    }

    private HashMap<String, Double> mapAvgAge() {
        final HashMap<String, Double> mapAvgAge = new HashMap<>();
        mapAvgAge.put("0-18", 18.0);
        mapAvgAge.put("18-25", 21.5);
        mapAvgAge.put("25-35", 30.0);
        mapAvgAge.put("35-50", 42.5);
        mapAvgAge.put("50+", 50.0);
        return mapAvgAge;
    }


    public JSONObject getDashboardData(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The dashboard data");

        final JSONObject resultObject = new JSONObject();


         final Object numJobPosting = getNumberJobPostingInRegion(id).get("result");
         final Object numCompany = getNumberCompanyInRegion(id).get("result");
         final Object averageSalary = getAverageSalaryInRegion(id).get("result");
//         final Object averageAge = getAverageAgeInRegion(id).get("result");
         resultObject.put("numJobPosting", numJobPosting);
         resultObject.put("numCompany", numCompany);
         resultObject.put("averageSalary", averageSalary);
//         resultObject.put("averageAge", averageAge);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    public JSONObject getAverageSalaryByIndustry(final String regionId){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The average salary by industry");
        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray industryArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int timeStamp = (int) list.get(0)[4];
            for(final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    industryArray = new JSONArray();
                    salaryArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    timeStamp = (int) ob[ob.length -2];
                }
                final int idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                final List<Object[]> listNumJob = regionRepository.getDemandByIndusrtryWithCountry(timeStamp, idIndustry);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithProvince(idProvince);
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int timeStamp =  (int) list.get(0)[4];
            for(final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    industryArray = new JSONArray();
                    salaryArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    timeStamp = (int) ob[ob.length -2];
                }
                final int idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                final List<Object[]> listNumJob = regionRepository.getDemandByIndustryWithProvince(timeStamp, idIndustry, idProvince);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        System.out.println(resultObject);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void putDataAvgSalaryToJSONObject(final JSONObject jsonObject, final JSONObject timeObject, final JSONArray industryArray, final JSONArray salaryArray, final JSONArray growthArray, final JSONArray numJobArray, final String time) {
        timeObject.put("industry", industryArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    private int getJSONDataAvgSalary(final JSONArray industryArray, final JSONArray salaryArray, final JSONArray growthArray, final Object[] ob) {
        final int idIndustry = (int) ob[0];
        final HashMap<String, Object> industryObject = new HashMap<>();
        industryObject.put("id", "I" + idIndustry);
        industryObject.put("name", ob[1].toString());
        industryArray.add(industryObject);
        salaryArray.add(ob[2]);
        growthArray.add(ob[ob.length - 3]);
        return idIndustry;
    }

    public JSONObject getJobDemandByIndustry(final String regionId){
        System.out.println(regionId);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job demand by industry");

        final JSONObject resultObject = new JSONObject();

        final JSONObject timeObject = new JSONObject();
        final JSONArray industryArray = new JSONArray();
        final JSONArray growthArray = new JSONArray();
        final JSONArray numJobArray = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getJobDemandByIndustryWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[4].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONObjectJobDemandByIndustry(resultObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getJobDemandByIndustryWithProvince(idProvince);
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[4].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONObjectJobDemandByIndustry(resultObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getJSONObjectJobDemandByIndustry(final JSONObject jsonObject, JSONObject timeObject, JSONArray industryArray, JSONArray growthArray, JSONArray numJobArray, final List<Object[]> list) {
        String time = list.get(0)[4].toString();
        for(final Object[] ob: list){
            if(!time.equals(ob[ob.length -1].toString())){
                timeObject.put("industry", industryArray);
                timeObject.put("growth", growthArray);
                timeObject.put("numJob", numJobArray);
                jsonObject.put(time, timeObject);
                timeObject = new JSONObject();
                industryArray = new JSONArray();
                growthArray = new JSONArray();
                numJobArray = new JSONArray();
                time = ob[ob.length -1].toString();
            }
            final HashMap<String, Object> industryObject = new HashMap<>();
            industryObject.put("id", "I" + ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryArray.add(industryObject);
            numJobArray.add((int) (double)ob[ob.length -3]);
            growthArray.add((int) (double)ob[ob.length -2]);
        }
        timeObject.put("industry", industryArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getHighestSalaryJobs(final String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest salary jobs");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getHighestSalaryJobsWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                final List<Object[]> listNumJob = regionRepository.getDemandByJobWithCountry(idTime, idJob);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getHighestSalaryJobsWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                final List<Object[]> listNumJob = regionRepository.getDemandByJobWithProvince(idTime, idJob, idProvince);
                numJobArray.add((int) (double)listNumJob.get(0)[0]);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private int getJSONDataHighestJobs(final JSONArray jobArray, final JSONArray salarayArray, final JSONArray growthArray, final Object[] ob) {
        final HashMap<String, Object> jobObject = new HashMap<>();
        final int idJob = (int) ob[0];
        jobObject.put("id", "J" + idJob);
        jobObject.put("name", ob[1].toString());
        jobArray.add(jobObject);
        salarayArray.add(ob[2]);
        growthArray.add(ob[3]);
        return idJob;
    }

    private void putDataHighestJobsToJSON(final JSONObject jsonObject, final JSONObject timeObject, final JSONArray jobArray, final JSONArray salarayArray, final JSONArray growthArray, final JSONArray numJobArray, final String time) {
        timeObject.put("job", jobArray);
        timeObject.put("salary", salarayArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getHighestDemandJobs(final String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest demand jobs");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getHighestDemandJobsWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                final List<Object[]> listSalary = regionRepository.getSalaryByJobWithCountry(idTime, idJob);
                final HashMap<String, Object> salaryObject = new HashMap<>();
                salaryObject.put("min", listSalary.get(0)[0]);
                salaryObject.put("max", listSalary.get(0)[1]);
                salarayArray.add(salaryObject);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getHighestDemandJobsWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                final List<Object[]> listSalary = regionRepository.getSalaryByJobWithProvince(idTime, idJob, idProvince);
                final HashMap<String, Object> salaryObject = new HashMap<>();
                salaryObject.put("min", listSalary.get(0)[0]);
                salaryObject.put("max", listSalary.get(0)[1]);
                salarayArray.add(salaryObject);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private boolean checkListObjectNull(final List<Object[]> list) {
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    public JSONObject getHighestPayingCompanies(final String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest paying companies");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray companyArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();


        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getHighestPayingCompaniesWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob: list){
                if(!time.equals(ob[ob.length -1])){
                    putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    salaryArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final HashMap<String, Object> companyObject = new HashMap<>();
                final int idCompany = (int) ob[0];
                final List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithCountry(idTime, idCompany);
                final int lastIdTime = idTime - 1;
                final List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithCountry(lastIdTime, idCompany);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getHighestPayingCompaniesWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( final Object[] ob: list){
                if(!time.equals(ob[ob.length -1])){
                    putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    salaryArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final HashMap<String, Object> companyObject = new HashMap<>();
                final int idCompany = (int) ob[0];
                final List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithProvince(idTime, idCompany, idProvince);
                final int lastIdTime = idTime - 1;
                final List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithProvince(lastIdTime, idCompany, idProvince);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getDataHighestPayingCompanyToJSON(final JSONArray companyArray, final JSONArray salaryArray, final JSONArray growthArray, final JSONArray numJobArray, final Object[] ob, final HashMap<String, Object> companyObject, final int idCompany, final List<Object[]> listNumJobNow, final List<Object[]> listNumJobPast) {
        companyObject.put("id", "C" + idCompany);
        companyObject.put("name", ob[1].toString());
        companyArray.add(companyObject);
        salaryArray.add(ob[ob.length - 4]);
        final double numJob = (double) listNumJobNow.get(0)[0];
        numJobArray.add((int)numJob);
        getGrowthValue(growthArray,numJob,listNumJobPast);
    }

    private void putDataHighestPayingCompanyToJSON(final JSONObject jsonObject, final JSONObject timeObject, final JSONArray companyArray, final JSONArray salaryArray, final JSONArray growthArray, final JSONArray numJobArray, final String time) {
        timeObject.put("company", companyArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("numJob", numJobArray);
        timeObject.put("growth", growthArray);
        jsonObject.put(time,timeObject);
    }

    public JSONObject getTopHiringCompanies(final String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest paying companies");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray companyArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();

        if(regionId.equals("P0")){
            final List<Object[]> list = regionRepository.getTopHiringCompaniesWithCountry();
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for(final Object[] ob: list){
                if(!time.equals(ob[ob.length - 1])){
                    putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    salaryArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idCompany = (int) ob[0];
                final List<Object[]> listSalaryCompany = regionRepository.getSalaryByCompanyWithCountry(idTime, idCompany);
                getDataTopHiringCompaniesToJSON(companyArray, numJobArray, growthArray, salaryArray, ob, idCompany, listSalaryCompany);
            }
            putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
        }
        else{
            final int idProvince = Integer.valueOf(regionId.replace("P",""));
            final List<Object[]> list = regionRepository.getTopHiringCompaniesWithProvince(idProvince);
            if(checkListObjectNull(list)) return null;
            final Set<String> timeSet = new LinkedHashSet<>();
            for (final Object[] ob : list) {
                timeSet.add(ob[5].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for(final Object[] ob: list){
                if(!time.equals(ob[ob.length - 1])){
                    putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    salaryArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = (int) ob[ob.length -2];
                }
                final int idCompany = (int) ob[0];
                final List<Object[]> listSalaryCompany = regionRepository.getSalaryByCompanyWithProvince(idTime, idCompany, idProvince);
                getDataTopHiringCompaniesToJSON(companyArray, numJobArray, growthArray, salaryArray, ob, idCompany, listSalaryCompany);
            }
            putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getDataTopHiringCompaniesToJSON(final JSONArray companyArray, final JSONArray numJobArray, final JSONArray growthArray, final JSONArray salaryArray, final Object[] ob, final int idCompany, final List<Object[]> listSalaryCompany) {
        final HashMap<String, Object> companyObject = new HashMap<>();
        final HashMap<String, Object> salaryObject = new HashMap<>();
        companyObject.put("id", "C" +  idCompany);
        companyObject.put("name", ob[1].toString());
        companyArray.add(companyObject);
        numJobArray.add((int)(double)ob[ob.length - 4]);
        growthArray.add(ob[ob.length - 3]);
        salaryObject.put("min", listSalaryCompany.get(0)[0]);
        salaryObject.put("max", listSalaryCompany.get(0)[1]);
        salaryArray.add(salaryObject);
    }

    private void putDataTopHiringCompaniesToJSON(final JSONObject jsonObject, final JSONObject timeObject, final JSONArray companyArray, final JSONArray numJobArray, final JSONArray growthArray, final JSONArray salaryArray, final String time) {
        timeObject.put("company", companyArray);
        timeObject.put("numJob", numJobArray);
        timeObject.put("growth", growthArray);
        timeObject.put("salary", salaryArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getJobDemandByAge(String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age and gender");

        JSONObject timeObject = new JSONObject();
        JSONArray ageRangeArray = new JSONArray();
        JSONArray maleArray = new JSONArray();
        JSONArray femaleArray = new JSONArray();

        List<String> listAgeRange = Arrays.asList("0-18", "18-25", "25-35", "35-50", "50+","Không xác định");
        for( String ob : listAgeRange){
            ageRangeArray.add(ob);
            System.out.println(ob);
        };
        jsonObject.put("ageRange", ageRangeArray);

        int count = 0;
        Object[] listMale = new ArrayList<Integer>(Collections.nCopies(ageRangeArray.size(), 0)).toArray();
        Object[] listFeMale = new ArrayList<Integer>(Collections.nCopies( ageRangeArray.size(), 0)).toArray();
        if( locationId.equals("P0")){
            List<Object[]> list = regionRepository.getJobDemandByAgeWithCountry();
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);

        }
        else{
            int idProvince = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = regionRepository.getJobDemandByAgeWithProvince( idProvince);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);
        }


        return jsonObject;
    }

    private void getJSONJobDemandByAge(JSONObject jsonObject, JSONObject timeObject, JSONArray ageRangeArray, Object[] listMale, Object[] listFeMale, List<Object[]> list) {
        JSONArray maleArray;
        JSONArray femaleArray;
        String time = list.get(0)[0].toString();
        for(Object[] ob: list){
            if(!time.equals(ob[0].toString())){
                maleArray = convertArrayToJSON(listMale);
                femaleArray = convertArrayToJSON(listFeMale);
                timeObject.put("male", maleArray);
                timeObject.put("female", femaleArray);
                jsonObject.put(time, timeObject);
                timeObject = new JSONObject();
//                        maleArray = new JSONArray();
//                        femaleArray = new JSONArray();
                time = ob[0].toString();

            }
            boolean flag = false;
            System.out.println(ob[ob.length -2]);
            System.out.println(ob[ob.length -1]);
            if (ob[ob.length -2].toString().equals("1")){
                for (Object age : ageRangeArray){
                    int index = ageRangeArray.indexOf(age.toString());
                    if(index == ageRangeArray.size() - 1) {
                        if(flag == false){
                            listMale[index] = (int) listMale[index] + (int)(double) ob[1];
                            System.out.println(listMale[index]);
                        }
                        break;
                    }
                    if(ob[index + 3].toString().equals("1")){
                        listMale[index] = (int) listMale[index] + (int)(double) ob[1];
                        System.out.println(listMale[index]);
                        flag = true;
                    }
                }
            }
            if (ob[ob.length -1].toString().equals("1")){
                for (Object age : ageRangeArray){
                    int index = ageRangeArray.indexOf(age.toString());
                    if(index == ageRangeArray.size() - 1) {
                        if(flag == false){
                            listFeMale[index] = (int) listFeMale[index] + (int)(double) ob[1];
                        }
                        break;
                    }
                    if(ob[index + 3].toString().equals("1")){
                        listFeMale[index] = (int) listFeMale[index] + (int)(double) ob[1];
                        flag = true;
                    }
                }
            }
//                if(ob[ob.length -2].toString().equals(age.toString())){
//                    System.out.println(ageRangeArray.indexOf(age));
//                    int index = ageRangeArray.indexOf(age);
//                    if(ob[ob.length-1].toString().equals("Nam")){
//                        listMale[index] = (int) (double)ob[1];
//                    }
//                    else {
//                        listFeMale[index] = (int) (double) ob[1];
//                    }
//                }
        }
        maleArray = convertArrayToJSON(listMale);
        femaleArray = convertArrayToJSON(listFeMale);
        timeObject.put("male", maleArray);
        timeObject.put("female", femaleArray);
        jsonObject.put(time, timeObject);
    }


    public JSONObject getJobDemandByLiteracy( String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        List<String> listLiteracy = new ArrayList<>();

        JSONObject timeObject = new JSONObject();
        JSONArray literacyArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        List<Object[]> listObjectLiteracy = regionRepository.getLiteracy();
        for( Object[] ob : listObjectLiteracy){
            HashMap<String, Object> literacyObject = new HashMap<>();
            literacyObject.put("id", ob[0].toString());
            literacyObject.put("name",ob[1].toString());
            literacyArray.add(literacyObject);
            listLiteracy.add(ob[0].toString());
        }
        jsonObject.put("literacy",literacyArray);
        Object[] listValueLiteracy = new Object[listLiteracy.size()];
        Object[] listPastValueLiteracy = new Object[listLiteracy.size()];
        Object[] listGrowth = new Object[listLiteracy.size()];
        System.out.println(listLiteracy.toString());
        if(locationId.equals("P0")){
            List<Object[]> list = regionRepository.getJobDemandByLiteracyWithCountry();
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandLiteracy(jsonObject, listLiteracy, timeObject, listValueLiteracy, listPastValueLiteracy, listGrowth, list);
        }
        else {
            int idProvince = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = regionRepository.getjobDemandByLiteracyWithProvince( idProvince);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandLiteracy(jsonObject, listLiteracy, timeObject, listValueLiteracy, listPastValueLiteracy, listGrowth, list);
        }
        return jsonObject;
    }

    private void getJSONJobDemandLiteracy(JSONObject jsonObject, List<String> listLiteracy, JSONObject timeObject, Object[] listValueLiteracy, Object[] listPastValueLiteracy, Object[] listGrowth, List<Object[]> list) {
        JSONArray dataArray;
        JSONArray growthArray;
        String time = list.get(0)[0].toString();
        for(Object[] ob : list){
//            System.out.println(ob[0].toString());
//            System.out.println(ob[1].toString());
//            System.out.println(ob[2].toString());
            if(!time.equals(ob[0].toString())) {
                dataArray = convertArrayToJSON(listValueLiteracy);
                growthArray = convertArrayToJSON(listGrowth);
                timeObject.put("data", dataArray);
                timeObject.put("growth", growthArray);
                jsonObject.put(time, timeObject);
                listPastValueLiteracy = listValueLiteracy.clone();
                listValueLiteracy = new Object[listLiteracy.size()];
                listGrowth = new Object[listLiteracy.size()];
                timeObject = new JSONObject();
                time = ob[0].toString();
            }
            for(String literacy: listLiteracy){
                int index = listLiteracy.indexOf(literacy);
                if(ob[ob.length - 2].toString().equals(literacy)){
                    listValueLiteracy[index] = (int) (double) ob[1];
                    try {
                        double growth = (( (double)(int)listValueLiteracy[index] / (double) (int) listPastValueLiteracy[index]) - 1) * 100;
                        listGrowth[index] = round(growth, 2);
                    } catch (Exception e) {
                        System.out.println(e);
                        listGrowth[index] = 0.0;
                    }
                }
            }
        }
        dataArray = convertArrayToJSON(listValueLiteracy);
        growthArray = convertArrayToJSON(listGrowth);
//            System.out.println(growthArray.toString());
        timeObject.put("data", dataArray);
        timeObject.put("growth", growthArray);
        jsonObject.put(time, timeObject);
    }



    public static double round(double value, final int places) {
        if (places < 0) throw new IllegalArgumentException();

        final long factor = (long) Math.pow(10, places);
        value = value * factor;
        final long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void getGrowthValue(final JSONArray growthArray, final double valueNow, final List<Object[]> pastValue) {
        double growth = 0;
        try {
            final double valueInLastQuarter = (double) pastValue.get(0)[0];
            growth = ((valueNow / valueInLastQuarter) - 1) * 100;
            growthArray.add(String.valueOf(round(growth, 2)));
        } catch (final Exception e) {
            growthArray.add("");
        }
    }

    public static JSONArray convertArrayToJSON( Object[] myArray){
        JSONArray jsArray = new JSONArray();

        for (int i = 0; i < myArray.length; i++) {
            if(myArray[i] == null){
                try{
                    myArray[i] = 0.0;
                }
                catch (Exception e){
                    myArray[i] = 0;
                }
            }
            jsArray.add(myArray[i]);
        }
        return jsArray;
    }

    public static boolean checkArrayStringNotNull( Object[] list){
        for( Object i: list){
            if( i != null){
                return true;
            }
        }
        return false;
    }
}