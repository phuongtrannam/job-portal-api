package com.hust.industry;

import java.beans.IntrospectionException;
import java.text.DecimalFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import javax.persistence.criteria.CriteriaBuilder;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    public JSONObject getIndustryList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The industry list");

        final JSONArray industryList = new JSONArray();
        String idIndustry = "";
        double numJob = 0;
        double growth = 0;
        boolean flag = false;
        HashMap<String, Object> industryObject = new HashMap<String, Object>();

        List<Object[]> list = industryRepository.getIndustryList();
        for(Object[] ob : list){
            if(idIndustry.equals(ob[1].toString())){
                flag = true;
                growth = ((numJob/(double)ob[ob.length -3]) - 1)*100;
                industryObject.put("growth", round(growth,2));
                industryList.add(industryObject);
                industryObject = new HashMap<>();
                continue;

            }
            if (!idIndustry.equals("") && !idIndustry.equals(ob[1].toString()) && !flag){
                industryList.add(industryObject);
                industryObject = new HashMap<>();
                flag = false;
            }
            idIndustry = ob[1].toString();
            numJob = (double) ob[ob.length -3];
            industryObject.put("numJob", (int) numJob );
            industryObject.put("id", ob[1].toString());
            industryObject.put("name", ob[2].toString());
//                industryObject.put("description", ob[ob.length -1].toString());
            double avgSalary = (double) ob[ob.length -2];
            industryObject.put("averageSalary", round(avgSalary,2));
//            industryObject.put("growth", ob[0].toString());
//            industryList.add(industryObject);
        }
        jsonObject.put("result", industryList);
        return jsonObject;
    }

    public JSONObject getJobListByIndustry(String industryId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job list by industry");

        final JSONArray jobList = new JSONArray();

        int idIndustry = Integer.valueOf(industryId.replace("I",""));

        List<Object[]> list = industryRepository.getJobListByIndustry(idIndustry);
        System.out.println(list);
        String idJob = "";
        double lastNumJob = 0;
        double numJob = 0;
        HashMap<String, Object> jobObject = new HashMap<String, Object>();
//        System.out.println(list);
        for(Object[] ob : list){
            if(ob[0].toString().equals(idJob)) {
                lastNumJob = (double) ob[2];
                double growth = ((numJob/lastNumJob) - 1)*100;
                jobObject.put("growth", round(growth,2));
                jobList.add(jobObject);
                jobObject = new HashMap<String, Object>();
                continue;
            }
            else if (list.indexOf(ob) != 0){
                double growth = 100.0;
                jobObject.put("growth", round(growth,2));
                jobList.add(jobObject);
                jobObject = new HashMap<String, Object>();
            }
            idJob = ob[0].toString();
            numJob = (double) ob[2];
            jobObject.put("numJob", (int) numJob);
            jobObject.put("id", ob[0].toString());
            jobObject.put("timestamp", "T" + ob[ob.length - 1].toString());
            jobObject.put("name", ob[1].toString());
//            jobObject.put("averageSalary", ob[3].toString());
            jobObject.put("minSalary", ob[3]);
            jobObject.put("maxSalary", ob[4]);
            // jobObject.put("description", ob[2].toString());
        }
        jsonObject.put("value", jobList);
        System.out.println(jsonObject);
        return jsonObject;
    }

    public JSONObject getTopCompanyByIndustry(String industryId, String locationId ){

        List<Object[]> list = null;
        JSONArray companyList = new JSONArray();
//        System.out.println(locationId.contains("P"));
        if(locationId.equals("P0")){
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            list = industryRepository.getTopCompanyByIndustryWithCountry(idIndustry);

            for(Object[] ob : list){
                int idCompany = (int) ob[0];
                int idTime = (int) ob[ob.length - 1];
                HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id", idCompany);
                companyObject.put("timestamp", ob[ob.length - 2].toString());
                companyObject.put("name", ob[1].toString());
                List<Object[]> avgSalaryOfCompany = industryRepository.getAvgSalaryForCompanyByCountry(idTime, idCompany, idIndustry);
                companyObject.put("averageSalary", round((double) avgSalaryOfCompany.get(0)[1],2));
                double numJob = (double) ob[2];
                companyObject.put("numJob", (int) numJob);
                companyObject.put("growth", ob[3]);
                companyList.add(companyObject);
            }
        }
        else{
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            int idProvince = Integer.valueOf(locationId.replace("P",""));
            list = industryRepository.getTopCompanyByIndustryWithProvince(idProvince, idIndustry);
            System.out.println(list);
            for( Object[] ob: list){
                int idCompany = (int) ob[0];
//                System.out.println(idCompany);
                int idTime = (int) ob[ob.length - 1];
                HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id", ob[0].toString());
                companyObject.put("timestamp", ob[ob.length - 2].toString());
                companyObject.put("name", ob[1].toString());
                companyObject.put("averageSalary", round((double)ob[3],2));
                double numJob = (double) ob[2];
                companyObject.put("numJob", (int) numJob);
                int lastIdTime = idTime - 1 ;
                System.out.println(lastIdTime);
                List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getRecruitmentOfCompanyInQuarter(lastIdTime,idCompany,idProvince, idIndustry);
                double growth = 0;
                try {
                    double numberRecruitmentInLastQuarter = (double) recruitmentOfCompanyInQuarter.get(0)[1];
                    growth = (((double)ob[2]/numberRecruitmentInLastQuarter) - 1)*100;
                    companyObject.put("growth",round(growth,2));
                }
                catch (Exception e){
                    System.out.println(e);
                    companyObject.put("growth",0);
                }
                companyList.add(companyObject);
            }
        }


        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top company by industry");

        jsonObject.put("result", companyList);
        return jsonObject;
    }



//    public JSONObject getTopSalaryCompanyByIndustry(String industryId, String locationId ){
//
//        List<Object[]> list = null;
//        JSONArray companyList = new JSONArray();
////        System.out.println(locationId.contains("P"));
//        if(locationId.equals("P0")){
//            int idIndustry = Integer.valueOf(industryId.replace("I",""));
//            list = industryRepository.getTopSalaryCompanyByIndustryWithCountry(idIndustry);
//
//            for(Object[] ob : list){
//                int idCompany = (int) ob[0];
//                int idTime = (int) ob[ob.length - 1];
//                HashMap<String, Object> companyObject = new HashMap<String, Object>();
//                companyObject.put("id", "C" + idCompany);
//                companyObject.put("timestamp", ob[ob.length - 2].toString());
//                companyObject.put("name", ob[1].toString());
//                List<Object[]> numJobCompany = industryRepository.getNumJobForCompanyByCountry(idTime, idCompany, idIndustry);
//                companyObject.put("numJob", round((double) numJobCompany.get(0)[1],2));
//                double salary = (double) ob[2];
//                companyObject.put("averageSalary", round(salary,2));
//                companyObject.put("growth", ob[3]);
//                companyList.add(companyObject);
//            }
//        }
//        else{
//            int idIndustry = Integer.valueOf(industryId.replace("I",""));
//            int idProvince = Integer.valueOf(locationId.replace("P",""));
//            list = industryRepository.getTopSalaryCompanyByIndustryWithProvince(idProvince, idIndustry);
//            System.out.println(list);
//            for( Object[] ob: list){
//                int idCompany = (int) ob[0];
////                System.out.println(idCompany);
//                int idTime = (int) ob[ob.length - 1];
//                HashMap<String, Object> companyObject = new HashMap<String, Object>();
//                companyObject.put("id","C" +  ob[0].toString());
//                companyObject.put("timestamp", ob[ob.length - 2].toString());
//                companyObject.put("name", ob[1].toString());
//                double avgSalary = (double) ob[3];
//                companyObject.put("averageSalary", round(avgSalary,2));
//                companyObject.put("numJob", (int) (double) ob[2]);
//                int lastIdTime = idTime - 1 ;
//                System.out.println(lastIdTime);
//                List<Object[]> salaryOfCompanyInQuarter = industryRepository.getSalaryOfCompanyInQuarter(lastIdTime,idCompany,idProvince, idIndustry);
//                double growth = 0;
//                try {
//                    double salaryInLastQuarter = (double) salaryOfCompanyInQuarter.get(0)[1];
//                    growth = (((double)ob[3]/salaryInLastQuarter) - 1)*100;
//                    companyObject.put("growth",round(growth,2));
//                }
//                catch (Exception e){
//                    System.out.println(e);
//                    companyObject.put("growth",0);
//                }
//                companyList.add(companyObject);
//            }
//        }

//
//        final JSONObject jsonObject = new JSONObject();
//        jsonObject.put("description", "The top company by industry");
//
//        jsonObject.put("result", companyList);
//        return jsonObject;
//    }


    public JSONObject getJobDemandByIndustry(String industryId, List<String> regionList){
        final JSONObject jsonObject = new JSONObject();
        // final JSONObject regionObject = new JSONObject();
        
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();

        if(regionList.contains("P0")){
            // int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithCountry(industryId);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list, false);
            // jsonObject.put("name", "Cả nước");
            jsonObject.put("description", "The job demand by industry - all country");
            jsonObject.put("data", dataArray);
            jsonObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            // jsonObject.put("ALL", regionObject);
        }
        else {
            // int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            // int idProvince = Integer.valueOf(locationId.replace("P", ""));
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithProvince(industryId, regionList);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list, false);
            // jsonObject.put("name", list.get(0)[1]);
            jsonObject.put("description", "The job demand by industry - region");
            jsonObject.put("data", dataArray);
            jsonObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            // jsonObject.put(locationId, regionObject);
        }

        return jsonObject;
    }

    public JSONObject getSalaryByIndustry(String industryId, List<String> locationId ){
        final JSONObject jsonObject = new JSONObject();
        // final JSONObject regionObject = new JSONObject();
        System.out.println("ok ok ok ok ok ");
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();

        if(locationId.contains("P0")){
            // int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getSalaryByIndustryWithCountry(industryId);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list, true);
            // jsonObject.put("name", "Cả nước");
            jsonObject.put("description", "The salary by industry - country");
            jsonObject.put("data", dataArray);
            jsonObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            // jsonObject.put("ALL", regionObject);
        }
        else {
            // int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            // int idProvince = Integer.valueOf(locationId.replace("P", ""));
            List<Object[]> list = industryRepository.getSalaryByIndustryWithProvince(industryId, locationId);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list,true);
            // jsonObject.put("name", list.get(0)[1]);
            jsonObject.put("description", "The salary by industry - region");
            jsonObject.put("data", dataArray);
            jsonObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            // jsonObject.put(locationId, regionObject);
        }

        return jsonObject;
    }

    private void extractDataJob(double growth, double preNumJob, JSONArray timeArray, JSONArray dataArray, JSONArray growthArray, List<Object[]> list , boolean isSalary) {
        for(Object[] ob : list){
            if(preNumJob != 0){
                growth = (((double) ob[ob.length -1]/preNumJob) -1)*100;
            }
            timeArray.add(ob[ob.length -2].toString());
            if (isSalary){
                dataArray.add(round((Double) ob[ob.length -1],2));
            }
            else {
                dataArray.add(ob[ob.length -1]);
            }
            growthArray.add(round(growth,2));
            preNumJob = (double) ob[ob.length -1];
        }
    }

    public JSONObject getJobDemandInSubRegion(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry in subregion");
        double growth = 0;
        double preNumJob = 0;
        double numJob = 0;
        JSONArray timeArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();

        if( locationId.equals("P0")){
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            List<Object[]> list = industryRepository.getJobDemandInSubRegionOfCountry(idIndustry);
            List<Object[]> listTime = industryRepository.getTime();
            for (Object[] ob : listTime){
                timeArray.add(ob[0].toString());
            }
            Integer[] listData = new Integer[timeArray.size()];
            Double[] listGrowth = new Double[timeArray.size()];
            int regionId = (int) list.get(0)[1];
            String nameSubRegion = list.get(0)[2].toString();
            for (Object[] ob: list){
                if(regionId != (int)ob[1]){
                    JSONObject subRegionObject = new JSONObject();
                    subRegionObject.put("nameRegion",nameSubRegion);
                    dataArray = convertArrayToJSON(listData);
                    subRegionObject.put("data", dataArray);
                    growthArray = convertArrayToJSON(listGrowth);
                    subRegionObject.put("growth", growthArray);
                    listData = new Integer[timeArray.size()];
                    listGrowth = new Double[timeArray.size()];
                    jsonObject.put( regionId, subRegionObject);
                    nameSubRegion = ob[2].toString();
                    regionId = (int) ob[1];
                    preNumJob = 0;
                    growth = 0;
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                }
                for(Object time : timeArray){
                    if (ob[ob.length -2].toString().equals(time.toString())){
                        if(preNumJob != 0){
                            growth = (((double) ob[ob.length -1]/preNumJob) -1)*100;
                        }
                        numJob = (double) ob[ob.length -1];
                        int index = timeArray.indexOf(time);
                        listData[index] = (int) numJob;
                        listGrowth[index] = round(growth,2);
                        preNumJob = (double) ob[ob.length -1];
                        break;
                    }
                }
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

        if(locationId.equals("P0")){
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getTopHiringCompanyWithCountry(idIndustry);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);

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
                HashMap<String , Object> companyObject = new HashMap<>();
                companyObject.put("id", ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                double numJob = (double) ob[ob.length -2];
                dataArray.add((int) numJob);
                growthArray.add(ob[ob.length -1]);
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            // timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else {
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            int idProvince  = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getTopHiringCompanyWithProvince(idIndustry, idProvince);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String timestamp = list.get(0)[0].toString();
            int idTime = 0;

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
                companyObject.put("id", ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                double numJob = (double) ob[ob.length -2];
                dataArray.add((int)numJob);
                idTime = (int) ob[ob.length-1];
                int lastIdTime = idTime - 1;
                // List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getRecruitmentOfCompanyInQuarter(lastIdTime,(int) ob[1], idProvince, idIndustry);
                // getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfCompanyInQuarter);
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            // timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }

    public JSONObject getTopSalaryHiringCompany(String industryId, String locationId ){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring company");

        JSONArray companyArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONObject timeObject = new JSONObject();

        if(locationId.equals("P0")){
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getTopSalaryHiringCompanyWithCountry(idIndustry);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);

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
                HashMap<String , Object> companyObject = new HashMap<>();
                companyObject.put("id",  ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                double salary = (double) ob[ob.length -2];
                dataArray.add((int) salary);
                growthArray.add(ob[ob.length -1]);
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else {
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            int idProvince  = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getTopSalaryHiringCompanyWithProvince(idIndustry, idProvince);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String timestamp = list.get(0)[0].toString();
            int idTime = 0;

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
                if (ob[ob.length -2] != null){
                    HashMap<String , String> companyObject = new HashMap<>();
                    companyObject.put("id",  ob[1].toString());
                    companyObject.put("name", ob[2].toString());
                    companyArray.add(companyObject);
                    double salary = (double) ob[ob.length -2];
                    dataArray.add((int)salary);
                    idTime = (int) ob[ob.length-1];
                    int lastIdTime = idTime - 1;
                }
                // List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getSalaryOfCompanyInQuarter(lastIdTime,(int) ob[1], locationId, industryId);
                // getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfCompanyInQuarter);
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            // timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }


    public JSONObject getTopHiringJob(String industryId, String locationId ){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top hiring job");

        JSONArray jobArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONObject timeObject = new JSONObject();

        if(locationId.equals("P0")){
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            List<Object[]> list = industryRepository.getTopHiringJobWithCountry(idIndustry);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            String timestamp = list.get(0)[0].toString();
            int idTime = 0;

            for(Object[] ob : list){
                if(!timestamp.equals(ob[0].toString())){
                    timeObject.put("job",jobArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    jobArray = new JSONArray();
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                HashMap<String , Object> jobObject = new HashMap<>();
                jobObject.put("id", ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                dataArray.add((int)(double)ob[ob.length -2]);
                idTime = (int) ob[ob.length-1];
                int lastIdTime = idTime - 1;
                System.out.println(idTime);
                // List<Object[]> recruitmentOfJobInQuarter = industryRepository.getRecruitmentJobInQuarterWithCountry((int)ob[1],idIndustry, lastIdTime);
                // getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfJobInQuarter);
            }
            timeObject.put("job",jobArray);
            timeObject.put("data", dataArray);
            // timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else{
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            int idProvince = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getTopHiringJobWithProvince(idIndustry, idProvince);

            String timestamp = list.get(0)[0].toString();
            int idTime = 0;
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);

            for(Object[] ob : list){
                if(!ob[0].toString().equals(timestamp)){
                    timeObject.put("job",jobArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    jobArray = new JSONArray();
                    dataArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                HashMap<String , String> jobObject = new HashMap<>();
                jobObject.put("id", ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                dataArray.add((int)(double)ob[ob.length -2]);
                idTime = (int) ob[ob.length-1];
                int lastIdTime = idTime - 1;
                System.out.println(idTime);
                // List<Object[]> recruitmentOfJobInQuarter = industryRepository.getRecruitmentJobInQuarterWithProvince((int)ob[1],idIndustry, lastIdTime, idProvince);
                // getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfJobInQuarter);
            }
            timeObject.put("job",jobArray);
            timeObject.put("data", dataArray);
            // timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }

    private void getGrowthValue(JSONArray growthArray, double valueNow, List<Object[]> pastValue) {
        double growth = 0;
        try {
            double valueInLastQuarter = (double) pastValue.get(0)[1];
            growth = ((valueNow / valueInLastQuarter) - 1) * 100;
            growthArray.add(round(growth, 2));
        } catch (Exception e) {
            growthArray.add(0.0);
        }
    }

    public JSONObject getHighestSalaryJob(String industryId, String locationId){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The highest salary job");

        JSONArray jobArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONObject timeObject = new JSONObject();

        if(locationId.equals("P0")){
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getHighestSalaryJobWithCountry(idIndustry);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);

            String timestamp = list.get(0)[0].toString();
            int idTime = 0;
            double salary = 0;

            for(Object[] ob : list){
                if(!timestamp.equals(ob[0].toString())){
                    timeObject.put("job",jobArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("numJob", numJobArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    jobArray = new JSONArray();
                    dataArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                if(ob[ob.length -3] != null){
                    HashMap<String , String> jobObject = new HashMap<>();
                    jobObject.put("id", ob[1].toString());
                    jobObject.put("name", ob[2].toString());
                    jobArray.add(jobObject);
                    salary =  (double) ob[ob.length - 3];
                    numJobArray.add((int) (double)ob[ob.length -2]);
                    dataArray.add(round(salary,2));
                    idTime = (int) ob[ob.length-1];
                    int lastIdTime = idTime - 1;
                    System.out.println(idTime);
                    List<Object[]> recruitmentOfJobInQuarter = industryRepository.getSalaryJobInQuarterWithCountry((int)ob[1],idIndustry, lastIdTime);
                    getGrowthValue(growthArray, (double) ob[ob.length -3], recruitmentOfJobInQuarter);
                }
            }
            timeObject.put("job",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else{
            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            int idProvince  = Integer.valueOf(locationId.replace("P", ""));
            List<Object[]> list = industryRepository.getHighestSalaryJobWithProvince(idIndustry, idProvince);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);

            String timestamp = list.get(0)[0].toString();
            int idTime = 0;
            double salary = 0;

            for(Object[] ob : list){
                if(!ob[0].toString().equals(timestamp)){
                    timeObject.put("job",jobArray);
                    timeObject.put("data", dataArray);
                    timeObject.put("numJob", numJobArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(timestamp, timeObject);
                    timeObject = new JSONObject();
                    jobArray = new JSONArray();
                    dataArray = new JSONArray();
                    numJobArray = new JSONArray();
                    growthArray = new JSONArray();
                    timestamp = ob[0].toString();
                }
                if(ob[ob.length -3] != null){
                    HashMap<String , Object> jobObject = new HashMap<>();
                    jobObject.put("id", ob[1].toString());
                    jobObject.put("name", ob[2].toString());
                    jobArray.add(jobObject);
                    salary = (double) ob[ob.length -3];
                    dataArray.add(round(salary,2));
                    numJobArray.add((int) (double) ob[ob.length -2]);
                    idTime = (int) ob[ob.length-1];
                    int lastIdTime = idTime - 1;
                    System.out.println(idTime);
                    List<Object[]> recruitmentOfJobInQuarter = industryRepository.getSalaryJobInQuarterWithProvince((int)ob[1],idIndustry, lastIdTime, idProvince);
                    getGrowthValue(growthArray, (double) ob[ob.length -3], recruitmentOfJobInQuarter);
                }
            }
            timeObject.put("job",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("numJob", numJobArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }

    public JSONObject getTopHiringRegion(final String industryId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "Top hiring region");
        final List<Object[]> list = industryRepository.getTopHiringRegion(industryId);
        exactDataCompany(jsonObject, list, false);  
        return jsonObject;
    }

    public JSONObject getHighestSalaryRegion(final String industryId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "Top Highest Salary region");
        final List<Object[]> list = industryRepository.getHighestSalaryRegion(industryId);
        exactDataCompany(jsonObject, list, true);  
        return jsonObject;
    }

    public void exactDataCompany(JSONObject jsonObject, List<Object[]> list, boolean isSalary){
        DecimalFormat df = new DecimalFormat("##.##");
        Set<String> timeSet = new LinkedHashSet<>();
        for (Object[] ob : list) {
            timeSet.add(ob[3].toString());
        }
        jsonObject.put("timestamps", timeSet);
        for (String time : timeSet) {
            final JSONObject timeObject = new JSONObject();
            JSONArray companyList = new JSONArray();
            int i = 0;
            if(isSalary){
                List<Float> data = new ArrayList<Float>();
                for (Object[] ob : list) {
                    if(time.equals(ob[3].toString()) && i<=10){
                        if(ob[2] != null){
                            HashMap<String, String> companyObject = new HashMap<String, String>();
                            companyObject.put("id", ob[0].toString());
                            companyObject.put("name", ob[1].toString());
                            companyList.add(companyObject);
                            float value = Float.parseFloat(ob[2].toString());
                            value = Float.parseFloat(df.format(value));
                            data.add(i, value);
                            i++;
                        }
                        
                    }
                }
                timeObject.put("data", data);
            }else{
                List<Integer> data = new ArrayList<Integer>();
                for (Object[] ob : list) {
                    if(time.equals(ob[3].toString()) && i<=10){
                        if(ob[2] != null){
                            HashMap<String, String> companyObject = new HashMap<String, String>();
                            companyObject.put("id", ob[0].toString());
                            companyObject.put("name", ob[1].toString());
                            companyList.add(companyObject);
                            data.add(i, Math.round(Float.parseFloat(ob[2].toString())));
                            i++;
                        }
                    }
                }
                timeObject.put("data", data);
            }
            
            timeObject.put("region", companyList);
            jsonObject.put(time, timeObject);
        }

        // return jsonObject;
    }
    
    public JSONObject getJobDemandByAge(String industryId, List<String> locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age and gender");

        JSONObject timeObject = new JSONObject();
        JSONArray ageRangeArray = new JSONArray();
        JSONArray maleArray = new JSONArray();
        JSONArray femaleArray = new JSONArray();

//        List<Object[]> listObjectAgeRange = industryRepository.getAgeRange();
        List<String> listAgeRange = Arrays.asList("0-18", "18-25", "25-35", "35-50", "50+","Không xác định");
        for( String ob : listAgeRange){
            ageRangeArray.add(ob);
            System.out.println(ob);
        };
        jsonObject.put("ageRange", ageRangeArray);

        int count = 0;
        Object[] listMale = new ArrayList<Integer>(Collections.nCopies(ageRangeArray.size(), 0)).toArray();
        Object[] listFeMale = new ArrayList<Integer>(Collections.nCopies( ageRangeArray.size(), 0)).toArray();
        if( locationId.contains("P0")){
            // int idIndustry = Integer.valueOf(industryId.replace("I",""));
            List<Object[]> list = industryRepository.getJobDemandByAgeWithCountry(industryId);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);

        }
        else{
            // int idIndustry = Integer.valueOf(industryId.replace("I",""));
            // int idProvince = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getJobDemandByAgeWithProvince(industryId, locationId);
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

    public JSONObject getJobDemandByLiteracy(String industryId, List<String> locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        List<String> listLiteracy = new ArrayList<>();

        JSONObject timeObject = new JSONObject();
        JSONArray literacyArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        List<Object[]> listObjectLiteracy = industryRepository.getLiteracy();
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
        if(locationId.contains("P0")){
            // int idIndustry = Integer.valueOf(industryId.replace("I",""));
            List<Object[]> list = industryRepository.getJobDemandByLiteracyWithCountry(industryId);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[0].toString());
            }
            jsonObject.put("timestamps",timeSet);
            getJSONJobDemandLiteracy(jsonObject, listLiteracy, timeObject, listValueLiteracy, listPastValueLiteracy, listGrowth, list);
        }
        else {
            // int idIndustry = Integer.valueOf(industryId.replace("I",""));
            // int idProvince = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getjobDemandByLiteracyWithProvince(industryId, locationId);
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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