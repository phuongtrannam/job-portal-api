package com.hust.industry;

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
public class IndustryController {

    @Autowired
    private IndustryService industryService; 

    //done
    @CrossOrigin
    @RequestMapping("/industries/get_industry_list")
    public JSONObject getIndustryList(){
        return industryService.getIndustryList();
    }
    
    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_job_list", method = RequestMethod.POST)
    public JSONObject getJobListByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
//        System.out.println(payload.get("idIndustry"));
        String industryId = payload.get("industryId").toString();
        return industryService.getJobListByIndustry(industryId);
    }

    //done
    // thong tin cong ty den muc thanh pho, tinh
    @CrossOrigin
    @RequestMapping(value = "/industries/get_top_company", method = RequestMethod.POST)
    public JSONObject getTopCompanyByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopCompanyByIndustry(industryId, locationId);
    }

//    @CrossOrigin
//    @RequestMapping(value = "/industries/get_top_salary_company", method = RequestMethod.POST)
//    public JSONObject getTopSalaryCompanyByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
//        // System.out.println(payload.get("id"));
//        String industryId = payload.get("industryId").toString();
//        String locationId = payload.get("locationId").toString();
//        return industryService.getTopSalaryCompanyByIndustry(industryId, locationId);
//    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_job_demand", method = RequestMethod.POST)
    public JSONObject getJobDemandByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String regionId = payload.get("locationId").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return industryService.getJobDemandByIndustry(industryId, regionList);
    }

    @CrossOrigin
    @RequestMapping(value = "/industries/get_salary", method = RequestMethod.POST)
    public JSONObject getSalaryByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String regionId = payload.get("locationId").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return industryService.getSalaryByIndustry(industryId, regionList);
    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_job_demand_sub_region", method = RequestMethod.POST)
    public JSONObject getJobDemandInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getJobDemandInSubRegion(industryId, locationId);
    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_top_hiring_company", method = RequestMethod.POST)
    public JSONObject getTopHiringCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopHiringCompany(industryId, locationId);
    }

    @CrossOrigin
    @RequestMapping(value = "/industries/get_top_salary_company", method = RequestMethod.POST)
    public JSONObject getTopSalaryHiringCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopSalaryHiringCompany(industryId, locationId);
    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_top_hiring_job", method = RequestMethod.POST)
    public JSONObject getTopHiringJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopHiringJob(industryId, locationId);
    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_highest_salary_job", method = RequestMethod.POST)
    public JSONObject getHighestSalaryJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getHighestSalaryJob(industryId, locationId);
    }

    @CrossOrigin
    @RequestMapping(value = "/industries/get_top_hiring_region", method = RequestMethod.POST)
    public JSONObject getTopHiringRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String industryId = payload.get("industryId").toString();
        return industryService.getTopHiringRegion(industryId);
    }

    @CrossOrigin
    @RequestMapping(value = "/industries/get_highest_salary_region", method = RequestMethod.POST)
    public JSONObject getHighestSalaryRegion(@RequestBody Map<String, Object> payload) throws Exception {
        String industryId = payload.get("industryId").toString();
        return industryService.getHighestSalaryRegion(industryId);
    }


    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_job_demand_by_age", method = RequestMethod.POST)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String regionId = payload.get("locationId").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return industryService.getJobDemandByAge(industryId, regionList);
    }

    //done
    @CrossOrigin
    @RequestMapping(value = "/industries/get_job_demand_by_literacy", method = RequestMethod.POST)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("industryId").toString();
        String regionId = payload.get("locationId").toString();
        List<String> regionList = Arrays.asList(regionId.split(","));
        return industryService.getJobDemandByLiteracy(industryId, regionList);
    }

    
}