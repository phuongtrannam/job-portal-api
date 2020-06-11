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

    public JSONObject getRootRegion(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The root region");

        if(regionId.contains("P")){
            final JSONArray rootRegion = new JSONArray();
            HashMap<String, String> regionObject = new HashMap<String, String>();
            regionObject.put("id", "");
            regionObject.put("name", "Cả nước");
            regionObject.put("type", "Country");
            rootRegion.add(regionObject);
            jsonObject.put("result", rootRegion);
        }
        return jsonObject;
    }

    public JSONObject getSubRegion(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The sub region");

        final JSONArray subRegion = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getProvinceRegion();
            System.out.println(regionId);
            for(Object[] ob : list){
                HashMap<String, String> regionObject = new HashMap<String, String>();
                if (ob[1].toString().equals("Khác") || ob[1].toString().equals("Toàn quốc")){
                    continue;
                }
                regionObject.put("id", "P" +  ob[0].toString());
                regionObject.put("name", ob[1].toString());
                regionObject.put("type", "Province");
                subRegion.add(regionObject);
            }
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getDistinctByProvince(idProvince);
            System.out.println(regionId);
            for(Object[] ob : list){
                HashMap<String, String> regionObject = new HashMap<String, String>();
                regionObject.put("id", "");
                regionObject.put("name", ob[0].toString());
                regionObject.put("type", "District");
                subRegion.add(regionObject);
            }
        }
        jsonObject.put("result", subRegion);
        return jsonObject;
    }

    public JSONObject getRootAndSubRegions(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        if(regionId.equals("")){
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
            regionObject.put("type", ob[2].toString()); // country, province, district
            regionArr.add(regionObject);
        }
        jsonObject.put("result", regionArr);
        return jsonObject;
    }

    public JSONObject getNumberJobPostingInRegion(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");
        HashMap<String, Object> numJobObject = new HashMap<>();
        if(regionId.equals("")){
            getDataDashboardToJSONObject(numJobObject, regionRepository.getNumberJobPostingInCountry());
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            getDataDashboardToJSONObject(numJobObject, regionRepository.getNumberJobPostingInProvince(idProvince));
        }
        jsonObject.put("result", numJobObject);
        return jsonObject;
    }
    
    public JSONObject getNumberCompanyInRegion(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        HashMap<String, Object> numCompanyObject = new HashMap<>();

        if(regionId.equals("")){
            getDataDashboardToJSONObject(numCompanyObject, regionRepository.getNumberCompanyInCountry());
        }
        else if (regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            getDataDashboardToJSONObject(numCompanyObject, regionRepository.getNumberCompanyInProvince(idProvince));
        }
        jsonObject.put("result", numCompanyObject);
        return jsonObject;
    }

    private void getDataDashboardToJSONObject(HashMap<String, Object> jsonObject, List<Object[]> numberCompanyInCountry) {
        List<Object[]> list = numberCompanyInCountry;
        if(checkListObjectNull(list)){
           return;
        };
        jsonObject.put("idTime", "T" + list.get(0)[0].toString());
        jsonObject.put("timeStamp", list.get(0)[1].toString());
        double numJob = Double.parseDouble(list.get(0)[2].toString());
        double preNumJob = Double.parseDouble(list.get(1)[2].toString());
        double growth = ((numJob / preNumJob) - 1) * 100;
        jsonObject.put("data", (int) numJob);
        jsonObject.put("growth", round(growth, 2));
    }

    public JSONObject getAverageSalaryInRegion(String regionId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        HashMap<String, Object> avgSalaryObject = new HashMap<>();

        double sumSalary = 0;
        double numJob = 0;
        double avgSalary = 0;
        double preAvgSalary = 0;
        double growth = 100.0;

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getListSalaryInLastYearWithCountry();
            getJSONObjectAverageSalary(jsonObject, avgSalaryObject, sumSalary, numJob, preAvgSalary, growth, list, "data");
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getListSalaryInLastYearWithProvince(idProvince);
            getJSONObjectAverageSalary(jsonObject, avgSalaryObject, sumSalary, numJob, preAvgSalary, growth, list, "data");
        }
        return jsonObject;
    }

    private void getJSONObjectAverageSalary(JSONObject jsonObject, HashMap<String, Object> avgSalaryObject, double sumSalary, double numJob, double preAvgSalary, double growth, List<Object[]> list, String salary) {
        double avgSalary;
        String time = list.get(0)[0].toString();
        System.out.println(time);
        for (Object[] ob : list) {
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


    public JSONObject getAverageAgeInRegion(String regionId) {

        HashMap<String, Double> mapAvgAge = mapAvgAge();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The number of jos posting in the region");

        HashMap<String, Object> avgAgeObject = new HashMap<>();

        double sumAge = 0;
        double numJob = 0;
        double avgAge = 0;
        double preAvgAge = 0;
        double growth = 0;

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getAverageAgeInCountry();
            getJSONObjectAverageAge(mapAvgAge, jsonObject, avgAgeObject, sumAge, numJob, preAvgAge, growth, list);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getAverageAgeInProvince(idProvince);
            getJSONObjectAverageAge(mapAvgAge, jsonObject, avgAgeObject, sumAge, numJob, preAvgAge, growth, list);
        }
        return jsonObject;
    }

    private void getJSONObjectAverageAge(HashMap<String, Double> mapAvgAge, JSONObject jsonObject, HashMap<String, Object> avgAgeObject, double sumAge, double numJob, double preAvgAge, double growth, List<Object[]> list) {
        double avgAge;
        String time = list.get(0)[0].toString();
        System.out.println(time);
        for(Object[] ob: list){
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
            Double age = mapAvgAge.get(ob[2].toString());
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
        HashMap<String, Double> mapAvgAge = new HashMap<>();
        mapAvgAge.put("0-18", 18.0);
        mapAvgAge.put("18-25", 21.5);
        mapAvgAge.put("25-35", 30.0);
        mapAvgAge.put("35-50", 42.5);
        mapAvgAge.put("50+", 50.0);
        return mapAvgAge;
    }


    public JSONObject getDashboardData(String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The dashboard data");

        final JSONObject resultObject = new JSONObject();


         Object numJobPosting = getNumberJobPostingInRegion(id).get("result");
         Object numCompany = getNumberCompanyInRegion(id).get("result");
         Object averageSalary = getAverageSalaryInRegion(id).get("result");
         Object averageAge = getAverageAgeInRegion(id).get("result");
         resultObject.put("numJobPosting", numJobPosting);
         resultObject.put("numCompany", numCompany);
         resultObject.put("averageSalary", averageSalary);
         resultObject.put("averageAge", averageAge);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    public JSONObject getAverageSalaryByIndustry(String regionId){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The average salary by industry");
        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray industryArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithCountry();
            String time = list.get(0)[5].toString();
            int timeStamp = (int) list.get(0)[4];
            for(Object[] ob : list){
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
                int idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByIndusrtryWithCountry(timeStamp, idIndustry);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int timeStamp =  (int) list.get(0)[4];
            for(Object[] ob : list){
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
                int idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByIndustryWithProvince(timeStamp, idIndustry, idProvince);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataAvgSalaryToJSONObject(resultObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        System.out.println(resultObject);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void putDataAvgSalaryToJSONObject(JSONObject jsonObject, JSONObject timeObject, JSONArray industryArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, String time) {
        timeObject.put("industry", industryArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    private int getJSONDataAvgSalary(JSONArray industryArray, JSONArray salaryArray, JSONArray growthArray, Object[] ob) {
        int idIndustry = (int) ob[0];
        HashMap<String, Object> industryObject = new HashMap<>();
        industryObject.put("id", "I" + idIndustry);
        industryObject.put("name", ob[1].toString());
        industryArray.add(industryObject);
        salaryArray.add(ob[2]);
        growthArray.add(ob[ob.length - 3]);
        return idIndustry;
    }

    public JSONObject getJobDemandByIndustry(String regionId){
        System.out.println(regionId);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job demand by industry");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray industryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getJobDemandByIndustryWithCountry();
            getJSONObjectJobDemandByIndustry(resultObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
        else if( regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getJobDemandByIndustryWithProvince(idProvince);
            getJSONObjectJobDemandByIndustry(resultObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getJSONObjectJobDemandByIndustry(JSONObject jsonObject, JSONObject timeObject, JSONArray industryArray, JSONArray growthArray, JSONArray numJobArray, List<Object[]> list) {
        String time = list.get(0)[4].toString();
        for(Object[] ob: list){
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
            HashMap<String, Object> industryObject = new HashMap<>();
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

    public JSONObject getHighestSalaryJobs(String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest salary jobs");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestSalaryJobsWithCountry();
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob : list){
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
                int idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByJobWithCountry(idTime, idJob);
                numJobArray.add((int)(double)listNumJob.get(0)[0]);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getHighestSalaryJobsWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob : list){
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
                int idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByJobWithProvince(idTime, idJob, idProvince);
                numJobArray.add((int) (double)listNumJob.get(0)[0]);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private int getJSONDataHighestJobs(JSONArray jobArray, JSONArray salarayArray, JSONArray growthArray, Object[] ob) {
        HashMap<String, Object> jobObject = new HashMap<>();
        int idJob = (int) ob[0];
        jobObject.put("id", "J" + idJob);
        jobObject.put("name", ob[1].toString());
        jobArray.add(jobObject);
        salarayArray.add(ob[2]);
        growthArray.add(ob[3]);
        return idJob;
    }

    private void putDataHighestJobsToJSON(JSONObject jsonObject, JSONObject timeObject, JSONArray jobArray, JSONArray salarayArray, JSONArray growthArray, JSONArray numJobArray, String time) {
        timeObject.put("job", jobArray);
        timeObject.put("salary", salarayArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getHighestDemandJobs(String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest demand jobs");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestDemandJobsWithCountry();
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob : list){
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
                int idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                List<Object[]> listSalary = regionRepository.getSalaryByJobWithCountry(idTime, idJob);
                HashMap<String, Object> salaryObject = new HashMap<>();
                salaryObject.put("min", listSalary.get(0)[0]);
                salaryObject.put("max", listSalary.get(0)[1]);
                salarayArray.add(salaryObject);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getHighestDemandJobsWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob : list){
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
                int idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                List<Object[]> listSalary = regionRepository.getSalaryByJobWithProvince(idTime, idJob, idProvince);
                HashMap<String, Object> salaryObject = new HashMap<>();
                salaryObject.put("min", listSalary.get(0)[0]);
                salaryObject.put("max", listSalary.get(0)[1]);
                salarayArray.add(salaryObject);
            }
            putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private boolean checkListObjectNull(List<Object[]> list) {
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    public JSONObject getHighestPayingCompanies(String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest paying companies");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray companyArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();


        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestPayingCompaniesWithCountry();
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob: list){
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
                HashMap<String, Object> companyObject = new HashMap<>();
                int idCompany = (int) ob[0];
                List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithCountry(idTime, idCompany);
                int lastIdTime = idTime - 1;
                List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithCountry(lastIdTime, idCompany);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getHighestPayingCompaniesWithProvince(idProvince);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for( Object[] ob: list){
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
                HashMap<String, Object> companyObject = new HashMap<>();
                int idCompany = (int) ob[0];
                List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithProvince(idTime, idCompany, idProvince);
                int lastIdTime = idTime - 1;
                List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithProvince(lastIdTime, idCompany, idProvince);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(resultObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getDataHighestPayingCompanyToJSON(JSONArray companyArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, Object[] ob, HashMap<String, Object> companyObject, int idCompany, List<Object[]> listNumJobNow, List<Object[]> listNumJobPast) {
        companyObject.put("id", "C" + idCompany);
        companyObject.put("name", ob[1].toString());
        companyArray.add(companyObject);
        salaryArray.add(ob[ob.length - 4]);
        double numJob = (double) listNumJobNow.get(0)[0];
        numJobArray.add((int)numJob);
        getGrowthValue(growthArray,numJob,listNumJobPast);
    }

    private void putDataHighestPayingCompanyToJSON(JSONObject jsonObject, JSONObject timeObject, JSONArray companyArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, String time) {
        timeObject.put("company", companyArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("numJob", numJobArray);
        timeObject.put("growth", growthArray);
        jsonObject.put(time,timeObject);
    }

    public JSONObject getTopHiringCompanies(String regionId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job highest paying companies");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray companyArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getTopHiringCompaniesWithCountry();
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for(Object[] ob: list){
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
                int idCompany = (int) ob[0];
                List<Object[]> listSalaryCompany = regionRepository.getSalaryByCompanyWithCountry(idTime, idCompany);
                getDataTopHiringCompaniesToJSON(companyArray, numJobArray, growthArray, salaryArray, ob, idCompany, listSalaryCompany);
            }
            putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
        }
        else if(regionId.contains("P")){
            int idProvince = Integer.valueOf(regionId.replace("P",""));
            List<Object[]> list = regionRepository.getTopHiringCompaniesWithProvince(idProvince);
            if(checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            int idTime = (int) list.get(0)[4];
            for(Object[] ob: list){
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
                int idCompany = (int) ob[0];
                List<Object[]> listSalaryCompany = regionRepository.getSalaryByCompanyWithProvince(idTime, idCompany, idProvince);
                getDataTopHiringCompaniesToJSON(companyArray, numJobArray, growthArray, salaryArray, ob, idCompany, listSalaryCompany);
            }
            putDataTopHiringCompaniesToJSON(resultObject, timeObject, companyArray, numJobArray, growthArray, salaryArray, time);
        }
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void getDataTopHiringCompaniesToJSON(JSONArray companyArray, JSONArray numJobArray, JSONArray growthArray, JSONArray salaryArray, Object[] ob, int idCompany, List<Object[]> listSalaryCompany) {
        HashMap<String, Object> companyObject = new HashMap<>();
        HashMap<String, Object> salaryObject = new HashMap<>();
        companyObject.put("id", "C" +  idCompany);
        companyObject.put("name", ob[1].toString());
        companyArray.add(companyObject);
        numJobArray.add((int)(double)ob[ob.length - 4]);
        growthArray.add(ob[ob.length - 3]);
        salaryObject.put("min", listSalaryCompany.get(0)[0]);
        salaryObject.put("max", listSalaryCompany.get(0)[1]);
        salaryArray.add(salaryObject);
    }

    private void putDataTopHiringCompaniesToJSON(JSONObject jsonObject, JSONObject timeObject, JSONArray companyArray, JSONArray numJobArray, JSONArray growthArray, JSONArray salaryArray, String time) {
        timeObject.put("company", companyArray);
        timeObject.put("numJob", numJobArray);
        timeObject.put("growth", growthArray);
        timeObject.put("salary", salaryArray);
        jsonObject.put(time, timeObject);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void getGrowthValue(JSONArray growthArray, double valueNow, List<Object[]> pastValue) {
        double growth = 0;
        try {
            double valueInLastQuarter = (double) pastValue.get(0)[0];
            growth = ((valueNow / valueInLastQuarter) - 1) * 100;
            growthArray.add(String.valueOf(round(growth, 2)));
        } catch (Exception e) {
            growthArray.add("");
        }
    }
}