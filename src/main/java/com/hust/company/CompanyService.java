package com.hust.company;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public JSONObject searchCompany(final String companyName){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list search");
        final List<Object[]> listId = companyRepository.getIdCompanyByName(companyName);
        List<String> idComanyList = new ArrayList<>();
        if(listId.size() != 0){
            for(final Object[] ob : listId){
                System.out.println(ob[0].toString());
                idComanyList.add(ob[0].toString());
            }
            // idComanyList.add("712");
            // idComanyList.add("1");
            final List<Object[]> listCompany = companyRepository.searchCompany(idComanyList);
            final JSONArray companyList = new JSONArray();
            for(final Object[] ob1 : listCompany){
                final HashMap<String, Object> companyObject = new HashMap<String, Object>();
                companyObject.put("id", ob1[1].toString());
                companyObject.put("name", ob1[2].toString());
                companyObject.put("numJob", ob1[3].toString());
                companyObject.put("location", ob1[5].toString());
                companyList.add(companyObject);
            }
            jsonObject.put("result", companyList);
        }
        
        return jsonObject;
    }

    public JSONObject getCompanyList(){

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list");

        final JSONArray companyList = new JSONArray();

        final List<Object[]> list = companyRepository.getCompanyList();
        for(final Object[] ob : list){
            final HashMap<String, Object> companyObject = new HashMap<String, Object>();
            List<Object[]> numJob = companyRepository.getNumJobByCompany((int)ob[0]);
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
            try {
                companyObject.put("numJob", numJob.get(0)[0]);
            }
            catch (Exception e ){
                System.out.println(e);
                continue;
            }
//            companyObject.put("phonenumber", ob[2].toString());
//            companyObject.put("description", ob[1].toString());
//            companyObject.put("founded_year", ob[4].toString());
            // companyObject.put("industry", ob[1].toString());
//            companyObject.put("website", ob[5].toString());
            companyObject.put("location", ob[6].toString());
//            companyObject.put("logo", ob[1].toString());
            companyList.add(companyObject);
        }
        jsonObject.put("result", companyList);
        return jsonObject;

    }

    public JSONObject getBusinessLinesOfTheCompany(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The business lines of the company");

        final JSONArray businessLines = new JSONArray();

        final List<Object[]> list = companyRepository.getBusinessLinesOfTheCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, String> industryObject = new HashMap<String, String>();
            industryObject.put("id", ob[0].toString());
            industryObject.put("name", ob[1].toString());
            
            businessLines.add(industryObject);
        }
        jsonObject.put("result", businessLines);
        return jsonObject;

    }

    public JSONObject getCompanyInfo(final String id) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company info");

        final List<Object[]> list = companyRepository.getCompanyInfo(id);
        System.out.println(id);
        final HashMap<String, Object> companyObject = new HashMap<String, Object>();
        for(final Object[] ob : list){
            
            companyObject.put("id", id);
            companyObject.put("name", ob[0].toString());
//            companyObject.put("phonenumber", ob[1].toString());
//            companyObject.put("description", ob[0].toString());
//            companyObject.put("founded_year", ob[3].toString());
//            companyObject.put("website", ob[4].toString());
            companyObject.put("location", ob[5].toString());
            companyObject.put("num_job",(int) Double.parseDouble(ob[6].toString()));
//            companyObject.put("logo", ob[0].toString());
        }
        jsonObject.put("result", companyObject);
        return jsonObject;

    }

    public JSONObject getRelatedCompany(final String id){
        
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The company related");

        final JSONArray relatedCompany = new JSONArray();

        final List<Object[]> list = companyRepository.getRelatedCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, String> companyObject = new HashMap<String, String>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
//            companyObject.put("phonenumber", ob[2].toString());
//            companyObject.put("description", ob[1].toString());
//            companyObject.put("founded_year", ob[4].toString());
//            companyObject.put("website", ob[5].toString());
            companyObject.put("location", ob[6].toString());
//            companyObject.put("logo", ob[0].toString());
            relatedCompany.add(companyObject);
        }
        jsonObject.put("result", relatedCompany);
        return jsonObject;
    }


    public JSONObject getRecentJobByCompany(final String id){
        System.out.println(id);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The current job by company");

        final JSONArray jobList = new JSONArray();

        final List<Object[]> list = companyRepository.getRecentJobByCompany(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> jobObject = new HashMap<String, Object>();
            jobObject.put("id", ob[0].toString());
            jobObject.put("job_name", ob[1].toString());
            jobObject.put("province", ob[2].toString());
            jobObject.put("min_salary", ob[3].toString());
            jobObject.put("max_salary", ob[4].toString());
            jobObject.put("num_job",(int) (double) ob[5]);
            jobObject.put("industry", ob[0].toString());
            jobList.add(jobObject);
        }
        jsonObject.put("result", jobList);
        return jsonObject;
    }

    public JSONObject getJobDemandByPeriodOfTime(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by period of time");

        final JSONArray periods = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByPeriodOfTime(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> period = new HashMap<String, Object>();
            period.put("id", ob[0].toString());
            period.put("province", ob[1].toString());
            period.put("timestamp", ob[2].toString());
            period.put("num_job", (int) (double) ob[3]);
            // period.put("year", ob[0].toString());
            // period.put("quarter", ob[0].toString());
            periods.add(period);
        }
        jsonObject.put("result", periods);
        return jsonObject;

    }

    public JSONObject getJobDemandByLiteracy(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by literacy");

        final JSONArray literacies = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByLiteracy(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> literacy = new HashMap<String, Object>();
            literacy.put("id", ob[0].toString());
            literacy.put("idTime", ob[1].toString());
            literacy.put("timestamp", ob[2].toString());
            literacy.put("literacy", ob[3].toString());
            literacy.put("num_job", (int) (double) ob[4]);
            // literacy.put("year", ob[0].toString());
            // literacy.put("quarter", ob[0].toString());
            literacies.add(literacy);
        }
        jsonObject.put("result", literacies);
        return jsonObject;
    }

    public JSONObject getJobDemandByAge(final String id){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", "The job demand by age and gender");

        final JSONArray ageRanges = new JSONArray();
        System.out.println(id);
        final List<Object[]> list = companyRepository.getJobDemandByAge(id);
        System.out.println(id);
        for(final Object[] ob : list){
            final HashMap<String, Object> ageRange = new HashMap<String, Object>();
            ageRange.put("id", ob[0].toString());
            ageRange.put("idTime", ob[1].toString());
            ageRange.put("timestamp", ob[2].toString());
            ageRange.put("age", ob[3].toString());
            ageRange.put("gender", ob[4].toString());
            ageRange.put("num_job", (int) (double) ob[5]);
            ageRanges.add(ageRange);
        }
        jsonObject.put("result", ageRanges);
        return jsonObject;

    }

    public JSONObject getJobDemandByCompany(String companyId){
        final JSONObject jsonObject = new JSONObject();
        final JSONObject companyObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry");
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();

        List<Object[]> listTimeStamp = companyRepository.getTimeStamps();
        for (Object[] ob : listTimeStamp){
            timeArray.add(ob[0].toString());
        }
        Object[] listData = new ArrayList<Integer>(Collections.nCopies(timeArray.size(), 0)).toArray();
        Object[] listGrowth = new ArrayList<Integer>(Collections.nCopies( timeArray.size(), 0)).toArray();

        int idCompany = Integer.valueOf(companyId.replace("C", ""));
        List<Object[]> list = companyRepository.getJobDemandByCompany(idCompany);
        extractDataJob(growth, preNumJob, timeArray, listData, listGrowth, list, false);
        jsonObject.put("data", convertArrayToJSON(listData));
        jsonObject.put("growth", convertArrayToJSON(listGrowth));
        jsonObject.put("timestamps", timeArray);
//        jsonObject.put("", companyObject);

        return jsonObject;
    }

    public JSONObject getSalaryByCompany(String companyId){
        final JSONObject jsonObject = new JSONObject();
        final JSONObject companyObject = new JSONObject();
        jsonObject.put("description", "The job demand by industry");
        double growth = 0;
        double preNumJob = 0;
        JSONArray timeArray = new JSONArray();

        List<Object[]> listTimeStamp = companyRepository.getTimeStamps();
        for (Object[] ob : listTimeStamp){
            timeArray.add(ob[0].toString());
        }
        Object[] listData = new ArrayList<Integer>(Collections.nCopies(timeArray.size(), 0)).toArray();
        Object[] listGrowth = new ArrayList<Integer>(Collections.nCopies( timeArray.size(), 0)).toArray();

        int idCompany = Integer.valueOf(companyId.replace("C", ""));
        List<Object[]> list = companyRepository.getSalaryByCompany(idCompany);
        extractDataJob(growth, preNumJob, timeArray, listData, listGrowth, list, true);
        jsonObject.put("data", convertArrayToJSON(listData));
        jsonObject.put("growth", convertArrayToJSON(listGrowth));
        jsonObject.put("timestamps", timeArray);
//        jsonObject.put("", companyObject);

        return jsonObject;
    }

    public JSONObject getHighestDemandJobs(final String companyId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", companyId);
        jsonObject.put("description", "The job highest demand jobs");

        final JSONObject resultObject = new JSONObject();

        JSONObject timeObject = new JSONObject();
        JSONArray jobArray = new JSONArray();
        JSONArray salarayArray = new JSONArray();
        JSONArray growthArray = new JSONArray();
        JSONArray numJobArray = new JSONArray();

        int idCompany = Integer.valueOf(companyId.replace("C", ""));
        final List<Object[]> list = companyRepository.getHighestDemandJobCompany(idCompany);
        final Set<String> timeSet = new LinkedHashSet<>();
        for (final Object[] ob : list) {
            timeSet.add(ob[6].toString());
        }
        jsonObject.put("timestamps",timeSet);
        String time = list.get(0)[6].toString();
        for( final Object[] ob : list){
            if(!time.equals(ob[ob.length -1].toString())){
                putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, numJobArray, time);
                jobArray = new JSONArray();
                salarayArray = new JSONArray();
                numJobArray = new JSONArray();
                timeObject = new JSONObject();
                time = ob[ob.length -1].toString();
            }
            final int idJob = getJSONDataHighestJobs(jobArray, numJobArray, ob, false);
            final HashMap<String, Object> salaryObject = new HashMap<>();
            salaryObject.put("min", ob[3]);
            salaryObject.put("max", ob[4]);
            salarayArray.add(salaryObject);
        }
        putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, numJobArray, time);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    private void putDataHighestJobsToJSON(final JSONObject jsonObject, final JSONObject timeObject, final JSONArray jobArray, final JSONArray salarayArray, final JSONArray numJobArray, final String time) {
        timeObject.put("job", jobArray);
        timeObject.put("salary", salarayArray);
        timeObject.put("numJob", numJobArray);
        jsonObject.put(time, timeObject);
    }

    private int getJSONDataHighestJobs(final JSONArray jobArray, final JSONArray dataArray, final Object[] ob, boolean isSalary) {
        final HashMap<String, Object> jobObject = new HashMap<>();
        final int idJob = (int) ob[0];
        jobObject.put("id", "J" + idJob);
        jobObject.put("name", ob[1].toString());
        jobArray.add(jobObject);
        if(isSalary){
            dataArray.add(round((double)ob[2],2));
        }
        else{
            dataArray.add((int)(double)ob[2]);
        }
//        growthArray.add(ob[3]);
        return idJob;
    }

    private void extractDataJob(double growth, double preNumJob, JSONArray timeArray, Object[] listData, Object[] listGrowth, List<Object[]> list , boolean isSalary) {
        for(Object[] ob : list){
            int index = timeArray.indexOf(ob[ob.length -2].toString());
            if(preNumJob != 0){
                growth = (((double) ob[ob.length -1]/preNumJob) -1)*100;
            }
//            timeArray.add(ob[ob.length -2].toString());
            if (isSalary){
                listData[index] = round((Double) ob[ob.length -1],2);
            }
            else {
                listData[index] = (int) (double) ob[ob.length -1];
            }
            listGrowth[index] = round(growth,2);
            preNumJob = (double) ob[ob.length -1];
        }
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

    // public JSONObject getNumberOfJob(){
    //     final JSONObject jsonObject = new JSONObject();
        
    //     return jsonObject;

    // }

    // public JSONObject getJobsHighestSalary(){
    //     final JSONObject jsonObject = new JSONObject();
    //     jsonObject.put("description", "The highest paid jobs");
        
    //     final JSONArray jobList = new JSONArray();
    //     HashMap<String, String> jobObject = new HashMap<String, String>();
    //     jobObject.put("name", "Kĩ sư hệ thống");
    //     jobObject.put("value", "50");
    //     jobObject.put("growth", "2");
    //     jobList.add(jobObject);
    //     jobList.add(jobObject);
    //     jsonObject.put("jobList", jobList);
    //     return jsonObject;

    // }
    

}