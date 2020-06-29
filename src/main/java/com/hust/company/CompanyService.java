package com.hust.company;

import java.text.DecimalFormat;
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
//            List<Object[]> numJob = companyRepository.getNumJobByCompany((int)ob[0]);
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
//            try {
//                companyObject.put("numJob", numJob.get(0)[0]);
//            }
//            catch (Exception e ){
//                System.out.println(e);
//                continue;
//            }
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

    public JSONObject advancedSearchCompany(List<String> industryList, String companyName, String minSalary, String maxSalary){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The company list");
        final JSONArray companyList = new JSONArray();
        final List<Object[]> list = companyRepository.advancedSearchCompany(industryList, companyName, minSalary, maxSalary);
        for(final Object[] ob : list){
            final HashMap<String, Object> companyObject = new HashMap<String, Object>();
            companyObject.put("id", ob[0].toString());
            companyObject.put("name", ob[1].toString());
            companyObject.put("location", ob[12].toString());
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
            // jobObject.put("industry", ob[0].toString());
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

    public JSONObject getJobDemandByAge(String companyId){
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
        List<Object[]> list = companyRepository.getJobDemandByAge(companyId);
        Set<String> timeSet = new LinkedHashSet<>();
        for (Object[] ob : list) {
            timeSet.add(ob[0].toString());
        }
        jsonObject.put("timestamps",timeSet);
        getJSONJobDemandByAge(jsonObject, timeObject, ageRangeArray, listMale, listFeMale, list);
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
        }
        maleArray = convertArrayToJSON(listMale);
        femaleArray = convertArrayToJSON(listFeMale);
        timeObject.put("male", maleArray);
        timeObject.put("female", femaleArray);
        jsonObject.put(time, timeObject);
    }

    public JSONObject getJobDemandByLiteracy(String companyId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by literacy");

        List<String> literacies = new ArrayList<String>();

        final List<Object[]> listLiteracy = companyRepository.getLiteracy();
        for (Object[] ob : listLiteracy) {
            literacies.add(ob[1].toString());
        }
        jsonObject.put("literacy", literacies);
        int numLiteracy = literacies.size();

        final List<Object[]> list = companyRepository.getJobDemandByLiteracy(companyId);
        Set<String> timeSet = new LinkedHashSet<>();
        for (Object[] ob : list) {
            timeSet.add(ob[2].toString());
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
                String tempTime = ob[2].toString();
                if (time.equals(tempTime)) {
                    index = literacies.indexOf(ob[3].toString());
                    currentValue = Math.round(Float.parseFloat(ob[4].toString()));
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
                System.out.println(ob[0].toString());
                System.out.println(ob[1].toString());
                System.out.println(ob[2].toString());
                System.out.println(ob[3].toString());
                System.out.println(ob[4].toString());
            }
            previousData = data;
            count++;
            timeObject.put("data", data);
            timeObject.put("growth", growth);
            jsonObject.put(time, timeObject);
        }
        jsonObject.put("timestamps", timeSet);
        return jsonObject;

    }

    public JSONObject getJobDemandByCompany(String companyId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The job demand by company");

        List<String> timestamp = new ArrayList<String>();
        List<Float> data = new ArrayList<Float>();
        List<Float> growth = new ArrayList<Float>();

        final List<Object[]> list = companyRepository.getJobDemandByCompany(companyId);
        String companyName = list.get(0)[1].toString();
        int i = 0;
        float previousValue = 1f;
        float currentGrowth;
        DecimalFormat df = new DecimalFormat("##.##");
        for (final Object[] ob : list) {
            timestamp.add(ob[2].toString());
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
        jsonObject.put("company", companyName);
        return jsonObject;
    }

    public JSONObject getSalaryByCompany(String companyId){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("description", "The average salary");

        List<String> timestamp = new ArrayList<String>();
        List<Float> data = new ArrayList<Float>();
        List<Float> growth = new ArrayList<Float>();

        final List<Object[]> list = companyRepository.getSalaryByCompany(companyId);
        String companyName = list.get(0)[1].toString();
        int i = 0;
        float previousValue = 1f;
        float currentGrowth;
        DecimalFormat df = new DecimalFormat("##.##");
        for (final Object[] ob : list) {
            timestamp.add(ob[2].toString());
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
        jsonObject.put("company", companyName);
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
            getJSONDataHighestJobs(jobArray, numJobArray, ob, false);
            final HashMap<String, Object> salaryObject = new HashMap<>();
            salaryObject.put("min", ob[3]);
            salaryObject.put("max", ob[4]);
            salarayArray.add(salaryObject);
        }
        putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, numJobArray, time);
        jsonObject.put("result", resultObject);
        return jsonObject;
    }

    public JSONObject getHighestSalaryJobs(final String companyId){
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
        final List<Object[]> list = companyRepository.getHighestSalaryJobCompany(idCompany);
        final Set<String> timeSet = new LinkedHashSet<>();
        for (final Object[] ob : list) {
            timeSet.add(ob[5].toString());
        }
        jsonObject.put("timestamps",timeSet);
        String time = list.get(0)[5].toString();
        for( final Object[] ob : list){
            if(!time.equals(ob[ob.length -1].toString())){
                putDataHighestJobsToJSON(resultObject, timeObject, jobArray, salarayArray, numJobArray, time);
                jobArray = new JSONArray();
                salarayArray = new JSONArray();
                numJobArray = new JSONArray();
                timeObject = new JSONObject();
                time = ob[ob.length -1].toString();
            }
            getJSONDataHighestJobs(jobArray, salarayArray, ob, true);
            if(ob[2] != null){
                numJobArray.add((int)(double)ob[3]);
            }
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

    private void getJSONDataHighestJobs(final JSONArray jobArray, final JSONArray dataArray, final Object[] ob, boolean isSalary) {
        if (ob[2] != null){
            final HashMap<String, Object> jobObject = new HashMap<>();
            final int idJob = (int) ob[0];
            jobObject.put("id", idJob);
            jobObject.put("name", ob[1].toString());
            jobArray.add(jobObject);
            if(isSalary) {
                dataArray.add(round((double) ob[2], 2));
            }
            else{
                dataArray.add((int)(double)ob[2]);
            }
        }
        else {
            return ;
        }
//        growthArray.add(ob[3]);
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


}