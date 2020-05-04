package com.hust.market;

import com.hust.market.service.MarketService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {

    @Autowired
    private MarketService marketService;

    @RequestMapping("/markets/get_jobs_highest_salary")
    public JSONObject getJobsHighestSalary(){
        return marketService.getJobsHighestSalary();
    }

    @RequestMapping("/markets/get_jobs_highest_recruitment")
    public JSONObject getJobsHighestRecruitment(){
        return marketService.getJobsHighestRecruitment();
    }

    @RequestMapping("/markets/get_highest_recruitment_demand_by_company")
    public JSONObject getRecruitmentDemandByCompany(){
        return marketService.getRecruitmentDemandByCompany();
    }

    @RequestMapping("/markets/get_recruitment_demand_by_age")
    public JSONObject getRecruitmentDemandByAge(){
        return marketService.getRecruitmentDemandByAge();
    }

    @RequestMapping("/markets/get_recruitment_demand_by_literacy")
    public JSONObject getRecruitmentDemandByLiteracy(){
        return marketService.getRecruitmentDemandByLiteracy();
    }

    @RequestMapping("/markets/get_recruitment_demand_by_industry")
    public JSONObject getRecruitmentDemandByIndustry(){
        return marketService.getRecruitmentDemandByIndustry();
    }

    //Nhu cầu tuyển dụng theo thời gian
    @RequestMapping("/markets/get_recruitment_demand_by_period_of_time")
    public JSONObject getRecruitmentDemandByPeriodOfTime(){
        return marketService.getRecruitmentDemandByPeriodOfTime();
    }

    @RequestMapping("/markets/get_average_salary_by_industry")
    public JSONObject getAverageSalaryByIndustry(){
        return marketService.getAverageSalaryByIndustry();
    }

}