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
                regionObject.put("id", ob[0].toString());
                regionObject.put("name", ob[1].toString());
                regionObject.put("type", "Province");
                subRegion.add(regionObject);
            }
        }
        else if(regionId.contains("P")){
            List<Object[]> list = regionRepository.getDistinctByProvince(regionId);
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

    public JSONObject getAverageSalaryByIndustry(String regionId){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The average salary by industry");

        JSONObject timeObject = new JSONObject();
        JSONArray industryArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithCountry();
            String time = list.get(0)[5].toString();
            String timeStamp = list.get(0)[4].toString();
            for(Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataAvgSalaryToJSONObject(jsonObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    industryArray = new JSONArray();
                    salaryArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    timeStamp = ob[ob.length -2].toString();
                }
                String idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByIndusrtryWithCountry(timeStamp, idIndustry);
                numJobArray.add(listNumJob.get(0)[0].toString());
            }
            putDataAvgSalaryToJSONObject(jsonObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            List<Object[]> list = regionRepository.getAverageSalaryByIndustryWithProvince(regionId);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            String timeStamp = list.get(0)[4].toString();
            for(Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataAvgSalaryToJSONObject(jsonObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    industryArray = new JSONArray();
                    salaryArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    timeStamp = ob[ob.length -2].toString();
                }
                String idIndustry = getJSONDataAvgSalary(industryArray, salaryArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByIndustryWithProvince(timeStamp, idIndustry, regionId);
                numJobArray.add(listNumJob.get(0)[0].toString());
            }
            putDataAvgSalaryToJSONObject(jsonObject, timeObject, industryArray, salaryArray, growthArray, numJobArray, time);
        }
        return jsonObject;
    }

    private void putDataAvgSalaryToJSONObject(JSONObject jsonObject, JSONObject timeObject, JSONArray industryArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, String time) {
        timeObject.put("industry", industryArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("growth", growthArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    private String getJSONDataAvgSalary(JSONArray industryArray, JSONArray salaryArray, JSONArray growthArray, Object[] ob) {
        String idIndustry = ob[0].toString();
        HashMap<String, String> industryObject = new HashMap<>();
        industryObject.put("id", idIndustry);
        industryObject.put("name", ob[1].toString());
        industryArray.add(industryObject);
        salaryArray.add(ob[2].toString());
        growthArray.add(ob[ob.length - 3].toString());
        return idIndustry;
    }

    public JSONObject getJobDemandByIndustry(String regionId){
        System.out.println(regionId);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", regionId);
        jsonObject.put("description", "The job demand by industry");

        JSONObject timeObject = new JSONObject();
        JSONArray industryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getJobDemandByIndustryWithCountry();
            getJSONObjectJobDemandByIndustry(jsonObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
        else if( regionId.contains("P")){
            List<Object[]> list = regionRepository.getJobDemandByIndustryWithProvince(regionId);
            getJSONObjectJobDemandByIndustry(jsonObject, timeObject, industryArray, growthArray, numJobArray, list);
        }
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
            HashMap<String, String> industryObject = new HashMap<>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryArray.add(industryObject);
            numJobArray.add(ob[ob.length -3].toString());
            growthArray.add(ob[ob.length -2].toString());
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

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestSalaryJobsWithCountry();
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                String idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByJobWithCountry(idTime, idJob);
                numJobArray.add(listNumJob.get(0)[0].toString());
            }
            putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            List<Object[]> list = regionRepository.getHighestSalaryJobsWithProvince(regionId);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                String idJob = getJSONDataHighestJobs(jobArray, salarayArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getDemandByJobWithProvince(idTime, idJob, regionId);
                numJobArray.add(listNumJob.get(0)[0].toString());
            }
            putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        return jsonObject;
    }

    private String getJSONDataHighestJobs(JSONArray jobArray, JSONArray salarayArray, JSONArray growthArray, Object[] ob) {
        HashMap<String, String> jobObject = new HashMap<>();
        String idJob = ob[0].toString();
        jobObject.put("id", idJob);
        jobObject.put("name", ob[1].toString());
        jobArray.add(jobObject);
        salarayArray.add(ob[2].toString());
        growthArray.add(ob[3].toString());
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
        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestDemandJobsWithCountry();
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                String idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getSalaryByJobWithCountry(idTime, idJob);
                double salary = (double) listNumJob.get(0)[0];
                salarayArray.add(String.valueOf(round(salary, 2)));
            }
            putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            List<Object[]> list = regionRepository.getHighestDemandJobsWithProvince(regionId);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob : list){
                if(!time.equals(ob[ob.length -1].toString())){
                    putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
                    jobArray = new JSONArray();
                    salarayArray = new JSONArray();
                    growthArray = new JSONArray();
                    numJobArray = new JSONArray();
                    timeObject = new JSONObject();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                String idJob = getJSONDataHighestJobs(jobArray, numJobArray, growthArray, ob);
                List<Object[]> listNumJob = regionRepository.getSalaryByJobWithProvince(idTime, idJob, regionId);
                double salary = (double) listNumJob.get(0)[0];
                salarayArray.add(String.valueOf(round(salary, 2)));
            }
            putDataHighestJobsToJSON(jsonObject, timeObject, jobArray, salarayArray, growthArray, numJobArray, time);
        }
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

        JSONObject timeObject = new JSONObject();
        JSONArray companyArray = new JSONArray();
        JSONArray salaryArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();


        if(regionId.equals("")){
            List<Object[]> list = regionRepository.getHighestPayingCompaniesWithCountry();
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob: list){
                if(!time.equals(ob[ob.length -1])){
                    putDataHighestPayingCompanyToJSON(jsonObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    salaryArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                HashMap<String, String> companyObject = new HashMap<>();
                String idCompany = ob[0].toString();
                List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithCountry(idTime, idCompany);
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithCountry(lastIdTime, idCompany);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(jsonObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        else if(regionId.contains("P")){
            List<Object[]> list = regionRepository.getHighestPayingCompaniesWithProvince(regionId);
            if (checkListObjectNull(list)) return null;
            String time = list.get(0)[5].toString();
            String idTime = list.get(0)[4].toString();
            for( Object[] ob: list){
                if(!time.equals(ob[ob.length -1])){
                    putDataHighestPayingCompanyToJSON(jsonObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    salaryArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    time = ob[ob.length -1].toString();
                    idTime = ob[ob.length -2].toString();
                }
                HashMap<String, String> companyObject = new HashMap<>();
                String idCompany = ob[0].toString();
                List<Object[]> listNumJobNow = regionRepository.getDemandByCompanyWithProvince(idTime, idCompany, regionId);
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                List<Object[]> listNumJobPast = regionRepository.getDemandByCompanyWithProvince(lastIdTime, idCompany, regionId);
                getDataHighestPayingCompanyToJSON(companyArray, salaryArray, growthArray, numJobArray, ob, companyObject, idCompany, listNumJobNow, listNumJobPast);
            }
            putDataHighestPayingCompanyToJSON(jsonObject, timeObject, companyArray, salaryArray, growthArray, numJobArray, time);
        }
        return jsonObject;
    }

    private void getDataHighestPayingCompanyToJSON(JSONArray companyArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, Object[] ob, HashMap<String, String> companyObject, String idCompany, List<Object[]> listNumJobNow, List<Object[]> listNumJobPast) {
        companyObject.put("id", idCompany);
        companyObject.put("name", ob[1].toString());
        companyArray.add(companyObject);
        salaryArray.add(ob[ob.length - 4].toString());
        double numJob = (double) listNumJobNow.get(0)[0];
        numJobArray.add(String.valueOf(numJob));
        getGrowthValue(growthArray,numJob,listNumJobPast);
    }

    private void putDataHighestPayingCompanyToJSON(JSONObject jsonObject, JSONObject timeObject, JSONArray companyArray, JSONArray salaryArray, JSONArray growthArray, JSONArray numJobArray, String time) {
        timeObject.put("company", companyArray);
        timeObject.put("salary", salaryArray);
        timeObject.put("numJob", numJobArray);
        timeObject.put("growth", growthArray);
        jsonObject.put(time,timeObject);
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