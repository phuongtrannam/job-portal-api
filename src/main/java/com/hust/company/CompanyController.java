package com.hust.company;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/companies/get_company_list")
    public JSONObject getCompanyList() {
        return companyService.getCompanyList();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/search_company", method = RequestMethod.POST)
    public JSONObject searchCompany(@RequestBody Map<String, Object> payload) throws Exception {
        String companyName = payload.get("companyName").toString();
        return companyService.searchCompany(companyName);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_business_lines_of_the_company", method = RequestMethod.POST)
    public JSONObject getBusinessLinesOfTheCompany(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getBusinessLinesOfTheCompany(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_company_info", method = RequestMethod.POST)
    public JSONObject getCompanyInfo(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getCompanyInfo(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_related_company", method = RequestMethod.POST)
    public JSONObject getRelatedCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String companyId = payload.get("id").toString();
        return companyService.getRelatedCompany(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_recent_job_by_company", method = RequestMethod.POST)
    public JSONObject getRecentJobByCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String companyId = payload.get("id").toString();
        return companyService.getRecentJobByCompany(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_job_demand_by_period_of_time", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByPeriodOfTime(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_job_demand_by_literacy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByLiteracy(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_job_demand_by_age", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByAge(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_job_demand", method = RequestMethod.POST)
    public JSONObject getJobDemandByCompany(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByCompany(companyId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/companies/get_salary", method = RequestMethod.POST)
    public JSONObject getSalaryByCompany(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getSalaryByCompany(companyId);
    }





    // @RequestMapping("/companies/getnumberofjob")
    // public JSONObject getNumberOfJob() {
    //     return companyService.getNumberOfJob();
    // }

    // @RequestMapping("/companies/getjobshighestsalary")
    // public JSONObject getJobsHighestSalary() {
    //     return companyService.getJobsHighestSalary();
    // }

    
}