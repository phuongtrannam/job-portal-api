package com.hust.job;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/top_job", method = RequestMethod.POST)
    public JSONObject getTopJob(@RequestBody Map<String, Object> payload) throws Exception {
        String numJob = payload.get("numJob").toString();
        return jobService.getTopJob(numJob);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/basic_search_job", method = RequestMethod.POST)
    public JSONObject basicSearchJob(@RequestBody Map<String, Object> payload) throws Exception {
        String queryContent = payload.get("queryContent").toString();
        return jobService.basicSearchJob(queryContent);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/advanced_search_job", method = RequestMethod.POST)
    public JSONObject advancedSearchJob(@RequestBody Map<String, Object> payload) throws Exception {
        String queryContent = payload.get("queryContent").toString();
        String location = payload.get("location").toString();
        String industry = payload.get("industry").toString();
        int minSalary = Integer.parseInt(payload.get("minSalary").toString());
        int maxSalary = Integer.parseInt(payload.get("maxSalary").toString());
        return jobService.advancedSearchJob(queryContent, location, industry, minSalary, maxSalary);
    }
    
    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_job_info", method = RequestMethod.POST)
    public JSONObject getJobInfo(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getJobInfo(idJob);
    }


    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_related_job", method = RequestMethod.POST)
    public JSONObject getJobRelated(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getJobRelated(idJob);
    }
    
    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_skill_required", method = RequestMethod.POST)
    public JSONObject getSkillRequired(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getSkillRequired(idJob);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_job_demand_by_period_of_time", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getJobDemandByPeriodOfTime(idJob, idLocation);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_average_salary", method = RequestMethod.POST)
    public JSONObject getAverageSalary(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getAverageSalary(idJob, idLocation);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_job_demand_in_sub_region", method = RequestMethod.POST)
    public JSONObject getJobDemandInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getJobDemandInSubRegion(idJob, idLocation);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_average_salary_in_sub_region", method = RequestMethod.POST)
    public JSONObject getAverageSalaryInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getAverageSalaryInSubRegion(idJob, idLocation);
    }
    
    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_job_demand_by_age", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getJobDemandByAge(idJob, idLocation);
    }

    @CrossOrigin(origins =  "http://localhost:4200")
    @RequestMapping(value = "/jobs/get_job_demand_by_literacy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getJobDemandByLiteracy(idJob, idLocation);
    }

}