package com.hust.industry;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/industries/getjoblistbyindustry")
    public JSONObject getJobListByIndustry(){
        String industryId = "";
        return industryService.getJobListByIndustry(industryId);
    }

    @RequestMapping("/industries/getjobdemandbyperiodoftime")
    public JSONObject getJobDemandByPeriodOfTime(){
        return industryService.getJobDemandByPeriodOfTime();
    }

    @RequestMapping("/industries/getjobdemandbyliteracy")
    public JSONObject getJobDemandByLiteracy(){
        return industryService.getJobDemandByLiteracy();
    }

    @RequestMapping("/industries/getjobdemandbyage")
    public JSONObject getJobDemandByAge(){
        return industryService.getJobDemandByAge();
    }

    @RequestMapping("/industries/getaveragesalarybyperiodoftime")
    public JSONObject getAverageSalaryByPeriodOfTime(){
        return industryService.getAverageSalaryByPeriodOfTime();
    }
}