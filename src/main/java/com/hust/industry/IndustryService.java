package com.hust.industry;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    public JSONObject getIndustryList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The industry list");

        final JSONArray industryList = new JSONArray();

        List<Object[]> list = industryRepository.getIndustryList();
        for(Object[] ob : list){
            HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            industryObject.put("description", ob[1].toString());
            industryObject.put("numJob", ob[0].toString());
            industryObject.put("averageSalary", ob[0].toString());
            industryObject.put("growth", ob[0].toString());
            industryList.add(industryObject);
        }
        jsonObject.put("value", industryList);
        return jsonObject;
    }

    public JSONObject getJobListByIndustry(String industryId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job list by industry");

        final JSONArray jobList = new JSONArray();

        List<Object[]> list = industryRepository.getJobListByIndustry(industryId);
        String idJob = "";
        double numJob = 0;
        HashMap<String, String> jobObject = new HashMap<String, String>();
//        System.out.println(list);
        for(Object[] ob : list){
            if(ob[0].toString().equals(idJob)) {
                double growth = ((numJob/(double)ob[2]) - 1)*100;
                jobObject.put("numJob", String.valueOf(numJob));
                jobObject.put("growth", String.valueOf(round(growth,2)));
                jobList.add(jobObject);
                jobObject = new HashMap<String, String>();
                continue;
            }
            idJob = ob[0].toString();
            jobObject.put("id", ob[0].toString());
            jobObject.put("timestamp", ob[ob.length - 1].toString());
            jobObject.put("name", ob[1].toString());
//            jobObject.put("averageSalary", ob[3].toString());
            jobObject.put("minSalary", ob[3].toString());
            jobObject.put("maxSalary", ob[4].toString());
            numJob = (double) ob[2];
            // jobObject.put("description", ob[2].toString());
        }
        jsonObject.put("value", jobList);
        return jsonObject;
    }

    public JSONObject getTopCompanyByIndustry(String industryId, String locationId ){

        List<Object[]> list = null;
        JSONArray companyList = new JSONArray();
//        System.out.println(locationId.contains("P"));
        if(locationId.equals("")){
            list = industryRepository.getTopCompanyByIndustryWithCountry(industryId);

            for(Object[] ob : list){
                String idCompany = ob[0].toString();
                String idTime = ob[ob.length - 1].toString();
                HashMap<String, String> companyObject = new HashMap<String, String>();
                companyObject.put("id", ob[0].toString());
                companyObject.put("timestamp", ob[ob.length - 2].toString());
                companyObject.put("name", ob[1].toString());
                List<Object[]> avgSalaryOfCompany = industryRepository.getAvgSalaryForCompanyByCountry(idTime, idCompany);
                companyObject.put("averageSalary", avgSalaryOfCompany.get(0)[1].toString());
                companyObject.put("numJob", ob[2].toString());
                companyObject.put("growth", ob[3].toString());
                companyList.add(companyObject);
            }
        }
        else if(locationId.contains("P")){
            list = industryRepository.getTopCompanyByIndustryWithProvince(locationId, industryId);
            System.out.println(list);
            for( Object[] ob: list){
                String idCompany = ob[0].toString();
//                System.out.println(idCompany);
                String idTime = ob[ob.length - 1].toString();
                HashMap<String, String> companyObject = new HashMap<String, String>();
                companyObject.put("id", ob[0].toString());
                companyObject.put("timestamp", ob[ob.length - 2].toString());
                companyObject.put("name", ob[1].toString());
                companyObject.put("averageSalary", ob[3].toString());
                companyObject.put("numJob", ob[2].toString());
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                System.out.println(lastIdTime);
                List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getRecruitmentOfCompanyInQuarter(lastIdTime,idCompany,locationId, industryId);
                double growth = 0;
                try {
                    double numberRecruitmentInLastQuarter = (double) recruitmentOfCompanyInQuarter.get(0)[1];
                    growth = (((double)ob[2]/numberRecruitmentInLastQuarter) - 1)*100;
                    companyObject.put("growth", String.valueOf(round(growth,2)));
                }
                catch (Exception e){
                    System.out.println(e);
                    companyObject.put("growth","");
                }
                companyList.add(companyObject);
            }
        }

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top company by industry");

        jsonObject.put("value", companyList);
        return jsonObject;
    }


    public JSONObject getJobDemandByIndustry(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        final JSONObject regionObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry");
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();

        if(locationId.equals("")){
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithCountry(industryId);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list);
            regionObject.put("name", "Cả nước");
            regionObject.put("data", dataArray);
            regionObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            jsonObject.put("ALL", regionObject);
        }
        else if(locationId.contains("P")){
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithProvince(industryId, locationId);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list);
            regionObject.put("name", list.get(0)[1]);
            regionObject.put("data", dataArray);
            regionObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            jsonObject.put(locationId, regionObject);
        }

        return jsonObject;
    }

    private void extractDataJob(double growth, double preNumJob, JSONArray timeArray, JSONArray dataArray, JSONArray growthArray, List<Object[]> list) {
        for(Object[] ob : list){
            if(preNumJob != 0){
                growth = (((double) ob[ob.length -1]/preNumJob) -1)*100;
            }
            timeArray.add(ob[ob.length -2].toString());
            dataArray.add(ob[ob.length -1].toString());
            growthArray.add(String.valueOf(round(growth,2)));
            preNumJob = (double) ob[ob.length -1];
        }
    }

    public JSONObject getJobDemandInSubRegion(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry in subregion");
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();

        if( locationId.equals("")){
            List<Object[]> list = industryRepository.getJobDemandInSubRegionOfCountry(industryId);
            String regionId = list.get(0)[1].toString();
            String nameSubRegion = list.get(0)[2].toString();
            for (Object[] ob: list){
                if(!regionId.equals(ob[1].toString())){
                    JSONObject subRegionObject = new JSONObject();
                    subRegionObject.put("nameRegion",nameSubRegion);
                    subRegionObject.put("data", dataArray);
                    subRegionObject.put("growth", growthArray);
                    jsonObject.put(regionId, subRegionObject);
                    nameSubRegion = ob[2].toString();
                    regionId = ob[1].toString();
                    preNumJob = 0;
                    growth = 0;
                    timeArray = new JSONArray();
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                }
                if(preNumJob != 0){
                    growth = (((double) ob[ob.length -1]/preNumJob) -1)*100;
                }
                timeArray.add(ob[ob.length -2].toString());
                dataArray.add(ob[ob.length -1].toString());
                growthArray.add(String.valueOf(round(growth,2)));
                preNumJob = (double) ob[ob.length -1];
            }
            JSONObject subRegionObject = new JSONObject();
            subRegionObject.put("nameRegion",nameSubRegion);
            subRegionObject.put("data", dataArray);
            subRegionObject.put("growth", growthArray);
            jsonObject.put("time", timeArray);
            jsonObject.put(regionId, subRegionObject);
        }
        return jsonObject;
    }

    public JSONObject getTopHiringCompany(String industryId, String locationId ){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring company");

        JSONArray companyArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONObject timeObject = new JSONObject();

        if(locationId.equals("")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getTopHiringCompanyWithCountry(industryId);

            String timestamp = list.get(0)[0].toString();

            for(Object[] ob : list){
                if(!timestamp.equals(ob[0].toString())){
                    timeObject.put("company",companyArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                HashMap<String , String> companyObject = new HashMap<>();
                companyObject.put("id",ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                dataArray.add(ob[ob.length -2].toString());
                growthArray.add(ob[ob.length -1].toString());
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else if(locationId.contains("P")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getTopHiringCompanyWithProvince(industryId, locationId);

            String timestamp = list.get(0)[0].toString();
            String idTime = null;

            for(Object[] ob : list){
                if(!Objects.equals(timestamp, ob[0].toString())){
                    timeObject.put("company",companyArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    companyArray = new JSONArray();
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                HashMap<String , String> companyObject = new HashMap<>();
                companyObject.put("id",ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                dataArray.add(ob[ob.length -2].toString());
                idTime = ob[ob.length-1].toString();
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getRecruitmentOfCompanyInQuarter(lastIdTime,ob[1].toString(),locationId, industryId);;
                double growth = 0;
                try {
                    double numberRecruitmentInLastQuarter = (double) recruitmentOfCompanyInQuarter.get(0)[1];
                    growth = (((double)ob[ob.length -2]/numberRecruitmentInLastQuarter) - 1)*100;
                    growthArray.add(String.valueOf(round(growth,2)));
                }
                catch (Exception e){
                    System.out.println(lastIdTime + ":" + ob[1].toString() + ":" + industryId + ":" + locationId);
                    growthArray.add("");
                }
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }


    public JSONObject getTopHiringJob(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring job");

        final JSONArray jobArr = new JSONArray();
        List<Object[]> list = industryRepository.getTopHiringJob(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> job = new HashMap<String, String>();
            job.put("idTime", ob[0].toString());
            job.put("timestamp", ob[1].toString());
            job.put("idJob", ob[0].toString());
            job.put("jobName", ob[0].toString());
            job.put("numJob", ob[5].toString());
            job.put("growth", ob[5].toString());
            jobArr.add(job);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getHighestSalaryJob(String industryId, String locationId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The highest salary job");

        final JSONArray jobArr = new JSONArray();
        List<Object[]> list = industryRepository.getHighestSalaryJob(industryId, locationId);

        for(Object[] ob : list){
            HashMap<String, String> job = new HashMap<String, String>();
            job.put("idTime", ob[0].toString());
            job.put("timestamp", ob[1].toString());
            job.put("idJob", ob[0].toString());
            job.put("jobName", ob[0].toString());
            job.put("averageSalary", ob[5].toString());
            job.put("growth", ob[5].toString());
            job.put("numJob", ob[5].toString());
            jobArr.add(job);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandByAge(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> ageRange = new HashMap<String, String>();
            ageRange.put("id_time", ob[1].toString());
            ageRange.put("timestamp", ob[3].toString());
            ageRange.put("age_range", ob[4].toString());
            ageRange.put("gender", ob[5].toString());
            ageRange.put("numJob", ob[6].toString());
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        List<Object[]> list = industryRepository.getJobDemandByLiteracy(industryId, locationId);
        for(Object[] ob : list){
            HashMap<String, String> literacy = new HashMap<String, String>();
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[3].toString());
            literacy.put("literacy", ob[4].toString());
            literacy.put("numJob", ob[4].toString());
            literacy.put("growth", ob[4].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}