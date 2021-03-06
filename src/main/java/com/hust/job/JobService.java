package com.hust.job;

import java.text.DecimalFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JSONObject getCityList() {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The city list");

        final JSONArray cityArr = new JSONArray();

        final List<Object[]> list = jobRepository.getCityList();
        for (final Object[] ob : list) {
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            // jobObject.put("area", ob[2].toString());
            cityArr.add(jobObject);
        }
        jsonObject.put("result", cityArr);
        return jsonObject;
    }

    public JSONObject getTopJob(final String numJob) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top job");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.getTopJob(Integer.parseInt(numJob));
        for (final Object[] ob : list) {
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            // String gender = "";
            // for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
            //     gender = gender + objects[0].toString() + ",";
            // }
            // jobObject.put("gender", gender.substring(0,gender.length()-1));

            String gender = "";
            boolean flagMale = false;
            boolean flagFeMale = false;
            for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
                if(objects[1].toString().equals("1") && !flagMale){
                    gender = gender + "Nam" + ",";
                    flagMale = true;
                }
                if(objects[2].toString().equals("1") && !flagFeMale){
                    gender = gender + "Nữ" + ",";
                    flagFeMale = true;
                }
            }
            jobObject.put("gender", gender.substring(0,gender.length()-1));

            jobObject.put("minSalary", ob[3].toString());
            jobObject.put("maxSalary", ob[4].toString());
            jobObject.put("numJob", String.valueOf(Math.round(Float.parseFloat(ob[5].toString()))));
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject basicSearchJob(final String queryContent) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The basic search job");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.basicSearchJob(queryContent);
        for (final Object[] ob : list) {
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            // String gender = "";
            // for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
            //     gender = gender + objects[0].toString() + ",";
            // }
            // jobObject.put("gender", gender.substring(0,gender.length()-1));

            String gender = "";
            boolean flagMale = false;
            boolean flagFeMale = false;
            for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
                if(objects[1].toString().equals("1") && !flagMale){
                    gender = gender + "Nam" + ",";
                    flagMale = true;
                }
                if(objects[2].toString().equals("1") && !flagFeMale){
                    gender = gender + "Nữ" + ",";
                    flagFeMale = true;
                }
            }
            jobObject.put("gender", gender.substring(0,gender.length()-1));

            jobObject.put("minSalary", ob[3].toString());
            jobObject.put("maxSalary", ob[4].toString());
            jobObject.put("numJob", String.valueOf(Math.round(Float.parseFloat(ob[5].toString()))));
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject advancedSearchJob(String jobName, List<String> industryList, List<String> regionList,
            String minSalary, String maxSalary) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The top job");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.advancedSearchJob(jobName, industryList, regionList, minSalary,  maxSalary);
        for (final Object[] ob : list) {
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[1].toString());
            jobObject.put("name", ob[2].toString());
            // String gender = "";
            // for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
            //     gender = gender + objects[0].toString() + ",";
            // }
            // jobObject.put("gender", gender.substring(0,gender.length()-1));

            String gender = "";
            boolean flagMale = false;
            boolean flagFeMale = false;
            for (Object[] objects : jobRepository.getGenderByJob((int)ob[1])){
                if(objects[1].toString().equals("1") && !flagMale){
                    gender = gender + "Nam" + ",";
                    flagMale = true;
                }
                if(objects[2].toString().equals("1") && !flagFeMale){
                    gender = gender + "Nữ" + ",";
                    flagFeMale = true;
                }
            }
            jobObject.put("gender", gender.substring(0,gender.length()-1));

            jobObject.put("minSalary", ob[3].toString());
            jobObject.put("maxSalary", ob[4].toString());
            jobObject.put("numJob", String.valueOf(Math.round(Float.parseFloat(ob[5].toString()))));
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getJobDescription(String idJob) {
        final JSONObject jobDescription = new JSONObject();
        List<Object[]> list = jobRepository.getJobDescription(idJob);
        jobDescription.put("name", list.get(0)[1].toString());
        jobDescription.put("descripton", list.get(0)[1].toString());
        return jobDescription;
    }

    public List<String> getJobLiteracy(String idJob) {
        List<String> literacies = new ArrayList<String>();
        List<Object[]> list = jobRepository.getJobLiteracy(idJob);
        for (final Object[] ob : list) {
            System.out.println(ob[2].toString());
            literacies.add(ob[2].toString());
        }
        return literacies;
    }

    public JSONObject getNumberOfJob(String idJob) {

        List<Object[]> list = jobRepository.getNumberOfJob(idJob);
        System.out.println(list.get(1)[4]);
        DecimalFormat df = new DecimalFormat("##.##");
        float growth = Float.parseFloat(list.get(0)[4].toString()) / Float.parseFloat(list.get(1)[4].toString()) - 1.0f;
        final JSONObject jobDescription = new JSONObject();
        jobDescription.put("minSalary", list.get(0)[2].toString());
        jobDescription.put("maxSalary", list.get(0)[3].toString());
        jobDescription.put("numJob", list.get(0)[4].toString());
        jobDescription.put("growth", df.format(growth));
        return jobDescription;
    }

    public List<String> getJobIndustry(String idJob) {
        List<String> industries = new ArrayList<String>();
        List<Object[]> list = jobRepository.getIndustryOfJob(idJob);
        for (final Object[] ob : list) {
            System.out.println(ob[1].toString());
            industries.add(ob[1].toString());
        }
        return industries;
    }

    public JSONObject getJobInfo(String idJob) {
        final JSONObject jobDescription = getJobDescription(idJob);
        final List<String> literacies = getJobLiteracy(idJob);
        final JSONObject jobSalaryAndNumber = getNumberOfJob(idJob);
        final List<String> industries = getJobIndustry(idJob);

        final JSONObject jobInfo = new JSONObject();
        jobInfo.put("name", jobDescription.get("name"));
        jobInfo.put("descripton", jobDescription.get("descripton"));
        jobInfo.put("minSalary", jobSalaryAndNumber.get("minSalary"));
        jobInfo.put("maxSalary", jobSalaryAndNumber.get("maxSalary"));
        jobInfo.put("numJob", jobSalaryAndNumber.get("numJob"));
        jobInfo.put("growth", jobSalaryAndNumber.get("growth"));
        jobInfo.put("industry", industries);
        jobInfo.put("literacy", literacies);
        // jobObject.put("gender", ob[4].toString());
        // jobObject.put("jobType", ob[5].toString());

        return jobInfo;
    }

    public JSONObject getCompanyHiringJob(final String idJob){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description","The company hiring job");

        final JSONArray companyArray = new JSONArray();
        final List<Object[]> list = jobRepository.getListCompanyHiringJob(Integer.valueOf(idJob));
        for (Object[] ob: list){
            HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id",ob[0].toString());
            companyObject.put("name", ob[1].toString());
            companyObject.put("location", ob[2].toString());

            companyArray.add(companyObject);
        }
        jsonObject.put("result",companyArray);
        return  jsonObject;
    }

    public JSONObject getJobRelated(final String idJob) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job realted");

        final JSONArray jobArr = new JSONArray();

        final List<Object[]> list = jobRepository.getJobRelated(idJob);
        for (final Object[] ob : list) {
            final HashMap<String, String> jobObject = new HashMap<String, String>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("name", ob[1].toString());
            jobObject.put("minSalary", ob[2].toString());
            jobObject.put("maxSalary", ob[3].toString());
            jobObject.put("numJob", ob[4].toString());
            // jobObject.put("gender", ob[4].toString());
            // jobObject.put("jobType", ob[5].toString());
            jobArr.add(jobObject);
        }
        jsonObject.put("result", jobArr);
        return jsonObject;
    }

    public JSONObject getSkillRequired(final String idJob) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job info");

        final JSONArray skillArr = new JSONArray();

        final List<Object[]> list = jobRepository.getSkillRequired(idJob);
        for (final Object[] ob : list) {
            final HashMap<String, String> skillObject = new HashMap<String, String>();
            // skillObject.put("id", ob[1].toString());
            skillObject.put("name", ob[2].toString());
            skillObject.put("description", ob[2].toString());

            skillArr.add(skillObject);
        }
        jsonObject.put("result", skillArr);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(String idJob, List<String> idLocation) {
        System.out.println(idLocation);
        System.out.println(idLocation.equals("P0"));
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by period of time");
        List<String> timestamp = new ArrayList<String>();
        List<Integer> data = new ArrayList<Integer>();
        List<Float> growth = new ArrayList<Float>();
        if (idLocation.contains("P0")) {
            System.out.println("VAO P0");
            final List<Object[]> list = jobRepository.getJobDemandByPeriodOfTimeCountry(idJob);
            String jobName = list.get(0)[2].toString();
            int i = 0;
            float previousValue = 1f;
            float currentGrowth;
            DecimalFormat df = new DecimalFormat("##.##");

            for (final Object[] ob : list) {
                timestamp.add(ob[1].toString());
                data.add(Math.round(Float.parseFloat(ob[3].toString())));
                if (i == 0) {
                    growth.add(0f);
                } else {
                    currentGrowth = 100 * (Float.parseFloat(ob[3].toString()) / previousValue - 1.0f);
                    currentGrowth = Float.parseFloat(df.format(currentGrowth));
                    growth.add(currentGrowth);
                }
                i++;
                previousValue = Float.parseFloat(ob[3].toString());
            }
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("data", data);
            jsonObject.put("growth", growth);
            jsonObject.put("jobName", jobName);
        } else {
            System.out.println("VAO KHAC P0");
            final List<Object[]> list = jobRepository.getJobDemandByPeriodOfTimeCity(idJob, idLocation);
            if (list.size() != 0) {
                String jobName = list.get(0)[2].toString();
                // String region = list.get(0)[3].toString();
                int i = 0;
                float previousValue = 1f;
                float currentGrowth;
                DecimalFormat df = new DecimalFormat("##.##");

                for (final Object[] ob : list) {
                    timestamp.add(ob[1].toString());
                    data.add(Math.round(Float.parseFloat(ob[3].toString())));
                    if (i == 0) {
                        growth.add(0f);
                    } else {
                        currentGrowth = 100 * (Float.parseFloat(ob[3].toString()) / previousValue - 1.0f);
                        currentGrowth = Float.parseFloat(df.format(currentGrowth));
                        growth.add(currentGrowth);
                    }
                    i++;
                    previousValue = Float.parseFloat(ob[3].toString());
                }
                jsonObject.put("timestamp", timestamp);
                jsonObject.put("data", data);
                jsonObject.put("growth", growth);
                jsonObject.put("jobName", jobName);
                // jsonObject.put("region", region);
            }

        }

        return jsonObject;
    }

    public JSONObject getAverageSalary(String idJob, List<String> idLocation) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary");

        List<String> timestamp = new ArrayList<String>();
        List<Float> data = new ArrayList<Float>();
        List<Float> growth = new ArrayList<Float>();

        if (idLocation.contains("P0")) {
            final List<Object[]> list = jobRepository.getAverageSalaryCountry(idJob);

            String jobName = list.get(0)[2].toString();
            int i = 0;
            float previousValue = 1f;
            float currentGrowth;
            DecimalFormat df = new DecimalFormat("##.##");

            for (final Object[] ob : list) {

                timestamp.add(ob[1].toString());
                data.add(Float.parseFloat(df.format(Float.parseFloat(ob[3].toString()))));
                if (i == 0) {
                    growth.add(0f);
                } else {
                    currentGrowth = 100 * (Math.round(Float.parseFloat(ob[3].toString())) / previousValue - 1.0f);
                    currentGrowth = Float.parseFloat(df.format(currentGrowth));
                    growth.add(currentGrowth);
                }
                i++;
                previousValue = Float.parseFloat(df.format(Float.parseFloat(ob[3].toString())));
            }
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("data", data);
            jsonObject.put("growth", growth);
            jsonObject.put("jobName", jobName);
        } else {
            final List<Object[]> list = jobRepository.getAverageSalaryCity(idJob, idLocation);
            if (list.size() != 0) {
                String jobName = list.get(0)[2].toString();
                // String region = list.get(0)[3].toString();
                int i = 0;
                float previousValue = 1f;
                float currentGrowth;
                DecimalFormat df = new DecimalFormat("##.##");

                // for(final Object[] ob : list){

                // timestamp.add(ob[1].toString());
                // Collections.reverse(timestamp);
                // }

                for (final Object[] ob : list) {

                    timestamp.add(ob[1].toString());
                    data.add(Float.parseFloat(df.format(Float.parseFloat(ob[3].toString()))));
                    if (i == 0) {
                        growth.add(0f);
                    } else {
                        currentGrowth = 100 * (Math.round(Float.parseFloat(ob[3].toString())) / previousValue - 1.0f);
                        currentGrowth = Float.parseFloat(df.format(currentGrowth));
                        growth.add(currentGrowth);
                    }
                    i++;
                    previousValue = Float.parseFloat(df.format(Float.parseFloat(ob[3].toString())));
                }
                jsonObject.put("timestamp", timestamp);
                jsonObject.put("data", data);
                jsonObject.put("growth", growth);
                jsonObject.put("jobName", jobName);
                // jsonObject.put("region", region);
            }

        }

        return jsonObject;
    }

    public JSONObject getJobDemandInSubRegion(final String idJob, final String idLocation) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand in sub region");

        final JSONArray subRegions = new JSONArray();

        final List<Object[]> list = jobRepository.getJobDemandInSubRegion(idJob, idLocation);
        for (final Object[] ob : list) {
            final HashMap<String, String> subRegion = new HashMap<String, String>();
            subRegion.put("timestamp", ob[0].toString());
            subRegion.put("idSubRegion", ob[0].toString());
            subRegion.put("regionName", ob[0].toString());
            subRegion.put("numJob", ob[1].toString());
            subRegion.put("growth", ob[2].toString());

            subRegions.add(subRegion);
        }
        jsonObject.put("result", subRegions);
        return jsonObject;
    }

    public JSONObject getAverageSalaryInSubRegion(final String idJob, final String idLocation) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary in sub region");

        final JSONArray subRegions = new JSONArray();

        final List<Object[]> list = jobRepository.getAverageSalaryInSubRegion(idJob, idLocation);
        for (final Object[] ob : list) {
            final HashMap<String, String> subRegion = new HashMap<String, String>();
            subRegion.put("timestamp", ob[0].toString());
            subRegion.put("idSubRegion", ob[0].toString());
            subRegion.put("regionName", ob[0].toString());
            subRegion.put("averageSalary", ob[1].toString());
            subRegion.put("growth", ob[2].toString());

            subRegions.add(subRegion);
        }
        jsonObject.put("result", subRegions);
        return jsonObject;
    }

    public JSONObject getTopHiringCompanies(final String idJob, List<String> regionList) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job hiring companies");
        if(regionList.contains("P0")){
            final List<Object[]> list = jobRepository.getTopHiringCompaniesCountry(idJob);
            exactDataCompany(jsonObject, list, false);
        }else{
            final List<Object[]> list = jobRepository.getTopHiringCompaniesCity(idJob, regionList);
            exactDataCompany(jsonObject, list, false);
        }   
        return jsonObject;
    }

    public JSONObject getTopHighestSalaryCompanies(final String idJob, List<String> regionList) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job highest paying companies");
        if(regionList.contains("P0")){
            final List<Object[]> list = jobRepository.getTopHighestSalaryCompaniesCountry(idJob);
            exactDataCompany(jsonObject, list, true);
        }else{
            final List<Object[]> list = jobRepository.getTopHighestSalaryCompaniesCity(idJob, regionList);
            exactDataCompany(jsonObject, list, true);
        }   
        return jsonObject;
    }

    public JSONObject getTopHiringRegion(final String idJob) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "Top hiring region");
        final List<Object[]> list = jobRepository.getTopHiringRegion(idJob);
        exactDataCompany(jsonObject, list, false);  
        return jsonObject;
    }

    public JSONObject getHighestSalaryRegion(final String idJob) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "Top Highest Salary region");
        final List<Object[]> list = jobRepository.getHighestSalaryRegion(idJob);
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
                        HashMap<String, String> companyObject = new HashMap<String, String>();
                        companyObject.put("id", ob[0].toString());
                        companyObject.put("name", ob[1].toString());
                        companyList.add(companyObject);
                        data.add(i, Math.round(Float.parseFloat(ob[2].toString())));
                        i++;
                    }
                }
                timeObject.put("data", data);
            }
            
            timeObject.put("object", companyList);
            jsonObject.put(time, timeObject);
        }
        // return jsonObject;
    }
    public JSONObject getJobDemandByAge(String idJob, List<String> idLocation) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by age");
        List<String> ageRanges = new ArrayList<String>();
        List<String> listAge = Arrays.asList("0-18", "18-25", "25-35", "35-50", "50+","Không xác định");
        for (String ob : listAge) {
            ageRanges.add(ob);
        }
        int lenAgeRange = ageRanges.size();
        jsonObject.put("ageRange", ageRanges);

        if (idLocation.contains("P0")) {
            final List<Object[]> list = jobRepository.getJobDemandByAgeCountry(idJob);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[1].toString());
            }
            int index = 0;
            int value = 0;
            for (String time : timeSet) {
                final JSONObject timeObject = new JSONObject();
                List<Integer> maleData = new ArrayList<Integer>(Collections.nCopies(lenAgeRange, 0));
                List<Integer> femaleData = new ArrayList<Integer>(Collections.nCopies(lenAgeRange, 0));
                for (Object[] ob : list) {
                    String tempTime = ob[1].toString();
                    boolean flag = false;
                    System.out.println(ob[ob.length -2]);
                    System.out.println(ob[ob.length -1]);
                    if (ob[ob.length -2].toString().equals("1")){
                        for (Object age : ageRanges){
                            index = ageRanges.indexOf(age.toString());
                            if(index == ageRanges.size() - 1) {
                                if(flag == false){
                                    maleData.set(index, (int) maleData.get(index) + (int) (double) ob[3]);
//                                    System.out.println(listMale[index]);
                                }
                                break;
                            }
                            if(ob[index + 5].toString().equals("1")){
                                maleData.set(index, (int) maleData.get(index) + (int) (double) ob[3]);
//                                System.out.println(listMale[index]);
                                flag = true;
                            }
                        }
                    }
                    if (ob[ob.length -1].toString().equals("1")){
                        for (Object age : ageRanges){
                            index = ageRanges.indexOf(age.toString());
                            if(index == ageRanges.size() - 1) {
                                if(flag == false){
                                    femaleData.set(index, (int) femaleData.get(index) + (int) (double) ob[3]);
                                }
                                break;
                            }
                            if(ob[index + 5].toString().equals("1")){
                                femaleData.set(index, (int) femaleData.get(index) + (int) (double) ob[3]);
                                flag = true;
                            }
                        }
                    }
//                    if (time.equals(tempTime)) {
//                        index = ageRanges.indexOf(ob[5].toString());
//                        value = Math.round(Float.parseFloat(ob[3].toString()));
//                        if (ob[4].toString().equals("Nữ")) {
//                            femaleData.set(index, value);
//                        }
//                        if (ob[4].toString().equals("Nam")) {
//                            maleData.set(index, value);
//                        }
//                    }
                }

                timeObject.put("male", maleData);
                timeObject.put("female", femaleData);
                jsonObject.put(time, timeObject);
            }
            jsonObject.put("timestamps", timeSet);
        } else {
            final List<Object[]> list = jobRepository.getJobDemandByAgeCity(idJob, idLocation);
            if (list.size() != 0) {
                Set<String> timeSet = new LinkedHashSet<>();
                for (Object[] ob : list) {
                    timeSet.add(ob[1].toString());
                }
                int index = 0;
                int value = 0;
                for (String time : timeSet) {
                    final JSONObject timeObject = new JSONObject();
                    List<Integer> maleData = new ArrayList<Integer>(Collections.nCopies(lenAgeRange, 0));
                    List<Integer> femaleData = new ArrayList<Integer>(Collections.nCopies(lenAgeRange, 0));
                    for (Object[] ob : list) {
                        String tempTime = ob[1].toString();
                        boolean flag = false;
                        System.out.println(ob[ob.length -2]);
                        System.out.println(ob[ob.length -1]);
                        if (ob[ob.length -2].toString().equals("1")){
                            for (Object age : ageRanges){
                                index = ageRanges.indexOf(age.toString());
                                if(index == ageRanges.size() - 1) {
                                    if(flag == false){
                                        maleData.set(index, (int) maleData.get(index) + (int) (double) ob[3]);
//                                    System.out.println(listMale[index]);
                                    }
                                    break;
                                }
                                if(ob[index + 5].toString().equals("1")){
                                    maleData.set(index, (int) maleData.get(index) + (int) (double) ob[3]);
//                                System.out.println(listMale[index]);
                                    flag = true;
                                }
                            }
                        }
                        if (ob[ob.length -1].toString().equals("1")){
                            for (Object age : ageRanges){
                                index = ageRanges.indexOf(age.toString());
                                if(index == ageRanges.size() - 1) {
                                    if(flag == false){
                                        femaleData.set(index, (int) femaleData.get(index) + (int) (double) ob[3]);
                                    }
                                    break;
                                }
                                if(ob[index + 5].toString().equals("1")){
                                    femaleData.set(index, (int) femaleData.get(index) + (int) (double) ob[3]);
                                    flag = true;
                                }
                            }
                        }
//                        if (time.equals(tempTime)) {
//                            index = ageRanges.indexOf(ob[6].toString());
//                            value = Math.round(Float.parseFloat(ob[4].toString()));
//                            if (ob[5].toString().equals("Nữ")) {
//                                femaleData.set(index, value);
//                            }
//                            if (ob[5].toString().equals("Nam")) {
//                                maleData.set(index, value);
//                            }
//                        }
                    }

                    timeObject.put("male", maleData);
                    timeObject.put("female", femaleData);
                    jsonObject.put(time, timeObject);
                }
                // for(final Object[] ob : list){
                // final HashMap<String, String> ageRange = new HashMap<String, String>();
                // ageRange.put("timestamp", ob[0].toString());
                // ageRange.put("numJob", ob[0].toString());
                // ageRange.put("gender", ob[0].toString());
                // ageRange.put("age", ob[1].toString());

                // ageRanges.add(ageRange);
                // }
                // jsonObject.put("result", ageRanges);
                jsonObject.put("timestamps", timeSet);
            }

        }

        return jsonObject;
    }

    public JSONObject getJobDemandByLiteracy(String idJob, List<String> idLocation) {

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        List<String> literacies = new ArrayList<String>();

        final List<Object[]> listLiteracy = jobRepository.getLiteracy();
        for (Object[] ob : listLiteracy) {
            literacies.add(ob[1].toString());
        }
        jsonObject.put("literacy", literacies);
        int numLiteracy = literacies.size();

        if (idLocation.contains("P0")) {
            final List<Object[]> list = jobRepository.getJobDemandByLiteracyCountry(idJob);
            Set<String> timeSet = new LinkedHashSet<>();
            for (Object[] ob : list) {
                timeSet.add(ob[1].toString());
            }

            DecimalFormat df = new DecimalFormat("##.##");
            int index = 0;
            int currentValue = 0;
            int previousValue = 0;
            float currentGrowth;
            List<Integer> previousData = new ArrayList<Integer>(Collections.nCopies(numLiteracy, 0));
            int count = 0;
            for (String time : timeSet) {
                final JSONObject timeObject = new JSONObject();
                List<Integer> data = new ArrayList<Integer>(Collections.nCopies(numLiteracy, 0));
                List<Float> growth = new ArrayList<Float>(Collections.nCopies(numLiteracy, 0.0f));
                for (Object[] ob : list) {
                    String tempTime = ob[1].toString();
                    if (time.equals(tempTime)) {
                        index = literacies.indexOf(ob[4].toString());
                        currentValue = Math.round(Float.parseFloat(ob[3].toString()));
                        data.set(index, currentValue);
                        if (count != 0) {
                            previousValue = previousData.get(index);
                            if (previousValue != 0) {
                                currentGrowth = 100 * (((float) currentValue / previousValue) - 1.0f);
                                currentGrowth = Float.parseFloat(df.format(currentGrowth));
                            } else {
                                currentGrowth = 100.0f;
                            }

                            growth.set(index, currentGrowth);
                        }
                    }
                }
                previousData = data;
                count++;

                timeObject.put("data", data);
                timeObject.put("growth", growth);
                jsonObject.put(time, timeObject);
            }
            jsonObject.put("timestamps", timeSet);
        } else {
            final List<Object[]> list = jobRepository.getJobDemandByLiteracyCity(idJob, idLocation);
            if (list.size() != 0) {
                Set<String> timeSet = new LinkedHashSet<>();
                for (Object[] ob : list) {
                    timeSet.add(ob[1].toString());
                }

                DecimalFormat df = new DecimalFormat("##.##");
                int index = 0;
                int currentValue = 0;
                int previousValue = 0;
                float currentGrowth;
                List<Integer> previousData = new ArrayList<Integer>(Collections.nCopies(numLiteracy, 0));
                int count = 0;
                for (String time : timeSet) {
                    final JSONObject timeObject = new JSONObject();
                    List<Integer> data = new ArrayList<Integer>(Collections.nCopies(numLiteracy, 0));
                    List<Float> growth = new ArrayList<Float>(Collections.nCopies(numLiteracy, 0.0f));
                    for (Object[] ob : list) {
                        String tempTime = ob[1].toString();
                        if (time.equals(tempTime)) {
                            index = literacies.indexOf(ob[4].toString());
                            currentValue = Math.round(Float.parseFloat(ob[3].toString()));
                            data.set(index, currentValue);
                            if (count != 0) {
                                previousValue = previousData.get(index);
                                if (previousValue != 0) {
                                    currentGrowth = 100 * (((float) currentValue / previousValue) - 1.0f);
                                    currentGrowth = Float.parseFloat(df.format(currentGrowth));
                                } else {
                                    currentGrowth = 100.0f;
                                }

                                growth.set(index, currentGrowth);
                            }
                        }
                    }
                    previousData = data;
                    count++;

                    timeObject.put("data", data);
                    timeObject.put("growth", growth);
                    jsonObject.put(time, timeObject);
                }
                jsonObject.put("timestamps", timeSet);
            }

        }

        return jsonObject;
    }
}