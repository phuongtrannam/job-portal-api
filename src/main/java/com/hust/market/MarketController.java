package com.hust.market;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {

    @Autowired
    private MarketService marketService;

    @RequestMapping("/markets/getjobshighestsalary")
    public JSONObject getJobsHighestSalary(){
        return marketService.getJobsHighestSalary();
    }

    @RequestMapping("/markets/getjobshighestrecruitment")
    public JSONObject getJobsHighestRecruitment(){
        return marketService.getJobsHighestRecruitment();
    }

    @RequestMapping("/markets/getrecruitmentdemandbycompany")
    public JSONObject getRecruitmentDemandByCompany(){
        return marketService.getRecruitmentDemandByCompany();
    }

    @RequestMapping("/markets/getrecruitmentdemandbyage")
    public JSONObject getRecruitmentDemandByAge(){
        return marketService.getRecruitmentDemandByAge();
    }

    @RequestMapping("/markets/getrecruitmentdemandbyliteracy")
    public JSONObject getRecruitmentDemandByLiteracy(){
        return marketService.getRecruitmentDemandByLiteracy();
    }

    @RequestMapping("/markets/getrecruitmentdemandbyindustry")
    public JSONObject getRecruitmentDemandByIndustry(){
        return marketService.getRecruitmentDemandByIndustry();
    }

    //Nhu cầu tuyển dụng theo thời gian
    @RequestMapping("/markets/getrecruitmentdemandbyperiodoftime")
    public JSONObject getRecruitmentDemandByPeriodOfTime(){
        return marketService.getRecruitmentDemandByPeriodOfTime();
    }

    @RequestMapping("/markets/getaveragesalarybyindustry")
    public JSONObject getAverageSalaryByIndustry(){
        return marketService.getAverageSalaryByIndustry();
    }

}