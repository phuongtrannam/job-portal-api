package com.hust.job;

import java.util.Arrays;
import java.util.List;
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

    @CrossOrigin
    @RequestMapping(value = "/jobs/city_list", method = RequestMethod.GET)
    public JSONObject getCityList() throws Exception {
        return jobService.getCityList();
    }
    
    @CrossOrigin
    @RequestMapping(value = "/jobs/top_job", method = RequestMethod.POST)
    public JSONObject getTopJob(@RequestBody Map<String, Object> payload) throws Exception {
        String numJob = payload.get("numJob").toString();
        return jobService.getTopJob(numJob);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/basic_search_job", method = RequestMethod.POST)
    public JSONObject basicSearchJob(@RequestBody Map<String, Object> payload) throws Exception {
        String queryContent = payload.get("queryContent").toString();
        return jobService.basicSearchJob(queryContent);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/advanced_search_job", method = RequestMethod.POST)
    public JSONObject advancedSearchJob(@RequestBody Map<String, Object> payload) throws Exception {
        String jobName = payload.get("jobName").toString();
        String regionId = payload.get("regionId").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        String industryId = payload.get("industryId").toString();
        List<String> industryList = Arrays.asList(industryId.split(","));
        String minSalary =  payload.get("minSalary").toString();
        String maxSalary =  payload.get("maxSalary").toString();
        return jobService.advancedSearchJob(jobName, industryList, regionList, minSalary,  maxSalary);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/jobs/get_job_info", method = RequestMethod.POST)
    public JSONObject getJobInfo(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getJobInfo(idJob);
    }


    @CrossOrigin
    @RequestMapping(value = "/jobs/get_related_job", method = RequestMethod.POST)
    public JSONObject getJobRelated(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getJobRelated(idJob);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/jobs/get_skill_required", method = RequestMethod.POST)
    public JSONObject getSkillRequired(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getSkillRequired(idJob);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_job_demand_by_period_of_time", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String regionId = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return jobService.getJobDemandByPeriodOfTime(idJob, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_average_salary", method = RequestMethod.POST)
    public JSONObject getAverageSalary(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String regionId = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return jobService.getAverageSalary(idJob, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_job_demand_in_sub_region", method = RequestMethod.POST)
    public JSONObject getJobDemandInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getJobDemandInSubRegion(idJob, idLocation);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_average_salary_in_sub_region", method = RequestMethod.POST)
    public JSONObject getAverageSalaryInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        return jobService.getAverageSalaryInSubRegion(idJob, idLocation);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/jobs/get_top_hiring_company", method = RequestMethod.POST)
    public JSONObject getTopHiringCompanies(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(idLocation.split(","));
        return jobService.getTopHiringCompanies(idJob, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_highest_salary_company", method = RequestMethod.POST)
    public JSONObject getTopHighestSalaryCompanies(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String idLocation = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(idLocation.split(","));
        return jobService.getTopHighestSalaryCompanies(idJob, regionList);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/jobs/get_top_hiring_region", method = RequestMethod.POST)
    public JSONObject getTopHiringRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getTopHiringRegion(idJob);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_highest_salary_region", method = RequestMethod.POST)
    public JSONObject getHighestSalaryRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getHighestSalaryRegion(idJob);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_job_demand_by_age", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String regionId = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return jobService.getJobDemandByAge(idJob, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_job_demand_by_literacy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        String regionId = payload.get("idLocation").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return jobService.getJobDemandByLiteracy(idJob, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/jobs/get_company_hiring_job", method = RequestMethod.POST)
    public JSONObject getCompanyHiringJob(@RequestBody Map<String, Object> payload) throws Exception {
        String idJob = payload.get("idJob").toString();
        return jobService.getCompanyHiringJob(idJob);
    }

}