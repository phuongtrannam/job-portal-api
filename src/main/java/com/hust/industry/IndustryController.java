package com.hust.industry;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndustryController {

    @Autowired
    private IndustryService industryService; 
    
    @RequestMapping("/industries/getjoblist")
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
    
    @RequestMapping(value = "/industries/getrelatedjob", method = RequestMethod.POST)
    public JSONObject getRelatedJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getRelatedJob(jobId);
    }

    @RequestMapping(value = "/industries/getjobdemandbyperiodoftime", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByPeriodOfTime(jobId);
    }

    @RequestMapping(value = "/industries/getjobdemandbyage", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByAge(jobId);
    }

    @RequestMapping(value = "/industries/getjobdemandbyliteracy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getJobDemandByLiteracy(jobId);
    }

    @RequestMapping(value = "/industries/getaveragesalarybyperiodoftime", method = RequestMethod.POST)
    public JSONObject getAverageSalaryByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String jobId = payload.get("id").toString();
        return industryService.getAverageSalaryByPeriodOfTime(jobId);
    }

    
}