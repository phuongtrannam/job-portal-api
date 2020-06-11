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

    //done
    @RequestMapping("/industries/get_industry_list")
    public JSONObject getIndustryList(){
        return industryService.getIndustryList();
    }
    
    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_list", method = RequestMethod.GET)
    public JSONObject getJobListByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        System.out.println(payload.get("id"));
        String industryId = payload.get("id").toString();
        return industryService.getJobListByIndustry(industryId);
    }

    //done
    // thong tin cong ty den muc thanh pho, tinh
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_top_company", method = RequestMethod.GET)
    public JSONObject getTopCompanyByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopCompanyByIndustry(industryId, locationId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand", method = RequestMethod.GET)
    public JSONObject getJobDemandByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getJobDemandByIndustry(industryId, locationId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_sub_region", method = RequestMethod.GET)
    public JSONObject getJobDemandInSubRegion(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getJobDemandInSubRegion(industryId, locationId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_top_hiring_company", method = RequestMethod.GET)
    public JSONObject getTopHiringCompany(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopHiringCompany(industryId, locationId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_top_hiring_job", method = RequestMethod.GET)
    public JSONObject getTopHiringJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getTopHiringJob(industryId, locationId);
    }

    //todo
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_highest_salary_job", method = RequestMethod.GET)
    public JSONObject getHighestSalaryJob(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getHighestSalaryJob(industryId, locationId);
    }

    //todo
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_by_age", method = RequestMethod.GET)
    public JSONObject getJobDemandByAge(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getJobDemandByAge(industryId, locationId);
    }

    //todo
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/industries/get_job_demand_by_literacy", method = RequestMethod.GET)
    public JSONObject getJobDemandByLiteracy(@RequestBody Map<String, Object> payload) throws Exception {
        // System.out.println(payload.get("id"));
        String industryId = payload.get("idIndustry").toString();
        String locationId = payload.get("locationId").toString();
        return industryService.getJobDemandByLiteracy(industryId, locationId);
    }

    
}