package com.hust.industry;

import java.beans.IntrospectionException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

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
            industryObject.put("id", "I" + ob[1].toString());
            industryObject.put("name", ob[2].toString());
//                industryObject.put("description", ob[ob.length -1].toString());
            double avgSalary = (double) ob[ob.length -2];
            industryObject.put("averageSalary", round(avgSalary,2));
//            industryObject.put("growth", ob[0].toString());
//            industryList.add(industryObject);
        }
        jsonObject.put("value", industryList);
        return jsonObject;
    }

    public JSONObject getJobListByIndustry(String industryId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job list by industry");

        final JSONArray jobList = new JSONArray();

        int idIndustry = Integer.valueOf(industryId.replace("I",""));

        List<Object[]> list = industryRepository.getJobListByIndustry(idIndustry);
        String idJob = "";
        double lastNumJob = 0;
        HashMap<String, Object> jobObject = new HashMap<String, Object>();
//        System.out.println(list);
        for(Object[] ob : list){
            if(ob[0].toString().equals(idJob)) {
                double numJob = (double) ob[2];
                double growth = ((numJob/lastNumJob) - 1)*100;
                jobObject.put("numJob", (int) numJob);
                jobObject.put("growth", round(growth,2));
                jobObject.put("id", "J" + ob[0].toString());
                jobObject.put("timestamp", "T" + ob[ob.length - 1].toString());
                jobObject.put("name", ob[1].toString());
//            jobObject.put("averageSalary", ob[3].toString());
                jobObject.put("minSalary", ob[3]);
                jobObject.put("maxSalary", ob[4]);
                jobList.add(jobObject);
                jobObject = new HashMap<String, Object>();
                continue;
            }
            idJob = ob[0].toString();
            lastNumJob = (double) ob[2];
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
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            list = industryRepository.getTopCompanyByIndustryWithCountry(idIndustry);

            for(Object[] ob : list){
                int idCompany = (int) ob[0];
                int idTime = (int) ob[ob.length - 1];
                HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id", "C" + idCompany);
                companyObject.put("timestamp", ob[ob.length - 2].toString());
                companyObject.put("name", ob[1].toString());
                List<Object[]> avgSalaryOfCompany = industryRepository.getAvgSalaryForCompanyByCountry(idTime, idCompany);
                companyObject.put("averageSalary", round((double) avgSalaryOfCompany.get(0)[1],2));
                double numJob = (double) ob[2];
                companyObject.put("numJob", (int) numJob);
                companyObject.put("growth", ob[3]);
                companyList.add(companyObject);
            }
        }
        else if(locationId.contains("P")){
            int idIndustry = Integer.valueOf(industryId.replace("I",""));
            int idProvince = Integer.valueOf(locationId.replace("P",""));
            list = industryRepository.getTopCompanyByIndustryWithProvince(idProvince, idIndustry);
            System.out.println(list);
            for( Object[] ob: list){
                int idCompany = (int) ob[0];
//                System.out.println(idCompany);
                int idTime = (int) ob[ob.length - 1];
                HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id","C" +  ob[0].toString());
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
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithCountry(idIndustry);
            extractDataJob(growth, preNumJob, timeArray, dataArray, growthArray, list);
            regionObject.put("name", "Cả nước");
            regionObject.put("data", dataArray);
            regionObject.put("growth", growthArray);
            jsonObject.put("timestamps", timeArray);
            jsonObject.put("ALL", regionObject);
        }
        else if(locationId.contains("P")){
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            int idProvince = Integer.valueOf(locationId.replace("P", ""));
            List<Object[]> list = industryRepository.getJobDemandByIndustryWithProvince(idIndustry, idProvince);
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
            dataArray.add(ob[ob.length -1]);
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

        if( locationId.equals("")){
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
                    jsonObject.put("P" + regionId, subRegionObject);
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

        if(locationId.equals("")){
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            List<Object[]> list = industryRepository.getTopHiringCompanyWithCountry(idIndustry);

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
                companyObject.put("id", "C" + ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                double numJob = (double) ob[ob.length -2];
                dataArray.add((int) numJob);
                growthArray.add(ob[ob.length -1]);
            }
            timeObject.put("company",companyArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else if(locationId.contains("P")){
//            System.out.println(industryId);
            int idIndustry = Integer.valueOf(industryId.replace("I", ""));
            int idProvince  = Integer.valueOf(locationId.replace("P",""));
            List<Object[]> list = industryRepository.getTopHiringCompanyWithProvince(idIndustry, idProvince);

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
                companyObject.put("id", "C" + ob[1].toString());
                companyObject.put("name", ob[2].toString());
                companyArray.add(companyObject);
                double numJob = (double) ob[ob.length -2];
                dataArray.add((int)numJob);
                idTime = (int) ob[ob.length-1];
                int lastIdTime = idTime - 1;
                List<Object[]> recruitmentOfCompanyInQuarter = industryRepository.getRecruitmentOfCompanyInQuarter(lastIdTime,(int) ob[1], idProvince, idIndustry);
                getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfCompanyInQuarter);
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
        jsonObject.put("description", "The top hiring company");

        JSONArray jobArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONObject timeObject = new JSONObject();

        if(locationId.equals("")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getTopHiringJobWithCountry(industryId);

            String timestamp = list.get(0)[0].toString();
            String idTime = null;

            for(Object[] ob : list){
                if(!timestamp.equals(ob[0].toString())){
                    timeObject.put("company",jobArray);
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
                jobObject.put("id",ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                dataArray.add(ob[ob.length -2].toString());
                idTime = ob[ob.length-1].toString();
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                System.out.println(idTime);
                List<Object[]> recruitmentOfJobInQuarter = industryRepository.getRecruitmentJobInQuarterWithCountry(ob[1].toString(),industryId, lastIdTime);
                getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfJobInQuarter);
            }
            timeObject.put("company",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else if(locationId.contains("P")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getTopHiringJobWithProvince(industryId, locationId);

            String timestamp = list.get(0)[0].toString();
            String idTime = null;

            for(Object[] ob : list){
                if(!ob[0].toString().equals(timestamp)){
                    timeObject.put("company",jobArray);
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
                jobObject.put("id",ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                dataArray.add(ob[ob.length -2].toString());
                idTime = ob[ob.length-1].toString();
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                System.out.println(idTime);
                List<Object[]> recruitmentOfJobInQuarter = industryRepository.getRecruitmentJobInQuarterWithProvince(ob[1].toString(),industryId, lastIdTime, locationId);
                getGrowthValue(growthArray, (double) ob[ob.length -2], recruitmentOfJobInQuarter);
            }
            timeObject.put("company",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
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

        if(locationId.equals("")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getHighestSalaryJobWithCountry(industryId);

            String timestamp = list.get(0)[0].toString();
            String idTime = null;
            double salary = 0;

            for(Object[] ob : list){
                if(!timestamp.equals(ob[0].toString())){
                    timeObject.put("company",jobArray);
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
                HashMap<String , String> jobObject = new HashMap<>();
                jobObject.put("id",ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                salary =  (double) ob[ob.length - 3];
                numJobArray.add(ob[ob.length -2].toString());
                dataArray.add(String.valueOf(round(salary,2)));
                idTime = ob[ob.length-1].toString();
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                System.out.println(idTime);
                List<Object[]> recruitmentOfJobInQuarter = industryRepository.getSalaryJobInQuarterWithCountry(ob[1].toString(),industryId, lastIdTime);
                getGrowthValue(growthArray, (double) ob[ob.length -3], recruitmentOfJobInQuarter);
            }
            timeObject.put("company",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        else if(locationId.contains("P")){
            System.out.println(industryId);
            List<Object[]> list = industryRepository.getHighestSalaryJobWithProvince(industryId, locationId);

            String timestamp = list.get(0)[0].toString();
            String idTime = null;
            double salary = 0;

            for(Object[] ob : list){
                if(!ob[0].toString().equals(timestamp)){
                    timeObject.put("company",jobArray);
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
                HashMap<String , String> jobObject = new HashMap<>();
                jobObject.put("id",ob[1].toString());
                jobObject.put("name", ob[2].toString());
                jobArray.add(jobObject);
                salary = (double) ob[ob.length -3];
                dataArray.add(String.valueOf(round(salary,2)));
                numJobArray.add(ob[ob.length -2].toString());
                idTime = ob[ob.length-1].toString();
                String lastIdTime = "T" + ((Character.getNumericValue(idTime.charAt(1))) - 1);
                System.out.println(idTime);
                List<Object[]> recruitmentOfJobInQuarter = industryRepository.getSalaryJobInQuarterWithProvince(ob[1].toString(),industryId, lastIdTime, locationId);
                getGrowthValue(growthArray, (double) ob[ob.length -3], recruitmentOfJobInQuarter);
            }
            timeObject.put("company",jobArray);
            timeObject.put("data", dataArray);
            timeObject.put("numJob", numJobArray);
            timeObject.put("growth", growthArray);
            jsonObject.put(timestamp, timeObject);
        }
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age and gender");

        JSONObject timeObject = new JSONObject();
        JSONArray ageRangeArray = new JSONArray();
        JSONArray maleArray = new JSONArray();
        JSONArray femaleArray = new JSONArray();

        List<Object[]> listObjectAgeRange = industryRepository.getAgeRange();

        for( Object[] ob : listObjectAgeRange){
            ageRangeArray.add(ob[1].toString());
            System.out.println(ob[1].toString());
        };
        jsonObject.put("ageRange", ageRangeArray);

        int count = 0;
        String[] listMale = new String[ageRangeArray.size()];
        String[] listFeMale = new String[ageRangeArray.size()];
        if( locationId.equals("")){
            List<Object[]> list = industryRepository.getJobDemandByAgeWithCountry(industryId);
            getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);

        }
        else if ( locationId.contains("P")){
            List<Object[]> list = industryRepository.getJobDemandByAgeWithProvince(industryId, locationId);
            getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);
        }


        return jsonObject;
    }

    private void getJSONJobDemandByAge(JSONObject jsonObject, JSONObject timeObject, JSONArray ageRangeArray, String[] listMale, String[] listFeMale, List<Object[]> list) {
        JSONArray maleArray;
        JSONArray femaleArray;
        String time = list.get(0)[0].toString();
        for(Object[] ob: list){
            for(Object age : ageRangeArray){
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
                if(ob[ob.length -2].toString().equals(age.toString())){
                    System.out.println(ageRangeArray.indexOf(age));
                    int index = ageRangeArray.indexOf(age);
                    if(ob[ob.length-1].toString().equals("Nam")){
                        listMale[index] = ob[1].toString();
                    }
                    else {
                        listFeMale[index] = ob[1].toString();
                    }
                }
            }
        }
        maleArray = convertArrayToJSON(listMale);
        femaleArray = convertArrayToJSON(listFeMale);
        timeObject.put("male", maleArray);
        timeObject.put("female", femaleArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getJobDemandByLiteracy(String industryId, String locationId ){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        List<String> listLiteracy = new ArrayList<>();

        JSONObject timeObject = new JSONObject();
        JSONArray literacyArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        List<Object[]> listObjectLiteracy = industryRepository.getLiteracy();
        for( Object[] ob : listObjectLiteracy){
            HashMap<String, String> literacyObject = new HashMap<>();
            literacyObject.put("id", ob[0].toString());
            literacyObject.put("name",ob[1].toString());
            literacyArray.add(literacyObject);
            listLiteracy.add(ob[0].toString());
        }
        jsonObject.put("literacy",literacyArray);
        String[] listValueLiteracy = new String[listLiteracy.size()];
        String[] listPastValueLiteracy = new String[listLiteracy.size()];
        String[] listGrowth = new String[listLiteracy.size()];
        System.out.println(listLiteracy.toString());
        if(locationId.equals("")){
            List<Object[]> list = industryRepository.getJobDemandByLiteracyWithCountry(industryId);
            getJSONJobDemandLiteracy(jsonObject, listLiteracy, timeObject, listValueLiteracy, listPastValueLiteracy, listGrowth, list);
        }
        else if (locationId.contains("P")){
            List<Object[]> list = industryRepository.getjobDemandByLiteracyWithProvince(industryId, locationId);
            getJSONJobDemandLiteracy(jsonObject, listLiteracy, timeObject, listValueLiteracy, listPastValueLiteracy, listGrowth, list);
        }
        return jsonObject;
    }

    private void getJSONJobDemandLiteracy(JSONObject jsonObject, List<String> listLiteracy, JSONObject timeObject, String[] listValueLiteracy, String[] listPastValueLiteracy, String[] listGrowth, List<Object[]> list) {
        JSONArray dataArray;
        JSONArray growthArray;
        String time = list.get(0)[0].toString();
        for(Object[] ob : list){
            System.out.println(ob[0].toString());
            System.out.println(ob[1].toString());
            System.out.println(ob[2].toString());
            if(!time.equals(ob[0].toString())) {
                if(!checkArrayStringNotNull(listPastValueLiteracy)){
                    time = ob[0].toString();
                    listPastValueLiteracy = listValueLiteracy.clone();
                    listValueLiteracy = new String[listLiteracy.size()];
                }
                else{
                    dataArray = convertArrayToJSON(listValueLiteracy);
                    growthArray = convertArrayToJSON(listGrowth);
                    timeObject.put("data", dataArray);
                    timeObject.put("growth", growthArray);
                    jsonObject.put(time, timeObject);
                    listPastValueLiteracy = listValueLiteracy.clone();
                    listValueLiteracy = new String[listLiteracy.size()];
                    listGrowth = new String[listLiteracy.size()];
                    timeObject = new JSONObject();
                    time = ob[0].toString();
                }
            }
            for(String literacy: listLiteracy){
                int index = listLiteracy.indexOf(literacy);
                if(ob[ob.length - 2].toString().equals(literacy)){
                    listValueLiteracy[index] = ob[1].toString();
                    try {
                        double growth = ((Double.parseDouble(listValueLiteracy[index]) / Double.parseDouble(listPastValueLiteracy[index])) - 1) * 100;
                        listGrowth[index] = String.valueOf(round(growth, 2));
                    } catch (Exception e) {
                        listGrowth[index] = null;
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

    public static boolean checkArrayStringNotNull( String[] list){
        for( String i: list){
            if( i != null){
                return true;
            }
        }
        return false;
    }
}