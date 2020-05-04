package com.hust.company;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @RequestMapping("/companies/getcompanylist")
    public JSONObject getCompanyList() {
        return companyService.getCompanyList();
    }

    @RequestMapping(value = "/companies/getcompanyinfo", method = RequestMethod.POST)
    public JSONObject getCompanyInfo(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getCompanyInfo(companyId);
    }

    @RequestMapping(value = "/companies/getrelatedcompany", method = RequestMethod.POST)
    public JSONObject getRelatedCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String companyId = payload.get("id").toString();
        return companyService.getRelatedCompany(companyId);
    }

    @RequestMapping(value = "/companies/getrecentjobbycompany", method = RequestMethod.POST)
    public JSONObject getRecentJobByCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String companyId = payload.get("id").toString();
        return companyService.getRecentJobByCompany(companyId);
    }

    @RequestMapping(value = "/companies/getjobdemandbyperiodoftime", method = RequestMethod.POST)
    public JSONObject getJobDemandByPeriodOfTime(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByPeriodOfTime(companyId);
    }

    @RequestMapping(value = "/companies/getjobdemandbyliteracy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        String companyId = payload.get("id").toString();
        return companyService.getJobDemandByLiteracy(companyId);
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