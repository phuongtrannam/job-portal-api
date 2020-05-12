package com.hust.industry;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndustryController {

    @Autowired
    private IndustryService industryService; 
    
    @RequestMapping("/industries/get_job_list")
    public JSONObject getJobList() {
        return industryService.getJobList();
    }

    @RequestMapping("/industries/getindustrylist")
    public JSONObject getIndustryList(){
        return industryService.getIndustryList();
    }

    // @RequestMapping("/industries/getjoblistbyindustry")
    // public JSONObject getJobListByIndustry(String idustryId){
    //     String industryId = idustryId;
    //     return industryService.getJobListByIndustry(industryId);
    // }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/getrelatedjob", method = RequestMethod.POST)
    public JSONObject getRelatedJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getRelatedJob(jobId);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_by_period_of_time", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByPeriodOfTime(jobId);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_by_age", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByAge(jobId);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_by_literacy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByLiteracy(jobId);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_average_salary_by_period_of_time", method = RequestMethod.POST)
    public JSONObject getAverageSalaryByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getAverageSalaryByPeriodOfTime(jobId);
    }

    
}