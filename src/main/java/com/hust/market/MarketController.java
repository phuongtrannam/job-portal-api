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

    //done
    @RequestMapping("/markets/get_jobs_highest_salary")
    public JSONObject getJobsHighestSalary(){
        return marketService.getJobsHighestSalary();
    }

    //done
    @RequestMapping("/markets/get_jobs_highest_recruitment")
    public JSONObject getJobsHighestRecruitment(){
        return marketService.getJobsHighestRecruitment();
    }

    //done
    @RequestMapping("/markets/get_companies_highest_recruitment")
    public JSONObject getCompaniesHighestRecruitment(){
        return marketService.getCompaniesHighestRecruitment();
    }

    //done
    @RequestMapping("/markets/get_companies_highest_salary")
    public JSONObject getCompaniesHighestSalary(){
        return marketService.getCompaniesHighestSalary();
    }

    //done
    @RequestMapping("/markets/get_recruitment_demand_with_age_gender")
    public JSONObject getRecruitmentDemandWithAgeGender(){
        return marketService.getRecruitmentDemandWithAgeGender();
    }

    //done
    @RequestMapping("/markets/get_recruitment_demand_by_literacy")
    public JSONObject getRecruitmentDemandByLiteracy(){
        return marketService.getRecruitmentDemandByLiteracy();
    }

    //done
    @RequestMapping("/markets/get_recruitment_demand_by_industry")
    public JSONObject getRecruitmentDemandByIndustry(){
        return marketService.getRecruitmentDemandByIndustry();
    }

    //todo
    //Nhu cầu tuyển dụng theo thời gian
    @RequestMapping("/markets/get_recruitment_demand_by_period_of_time")
    public JSONObject getRecruitmentDemandByPeriodOfTime(){
        return marketService.getRecruitmentDemandByPeriodOfTime();
    }
    //done
    @RequestMapping("/markets/get_average_salary_by_industry")
    public JSONObject getAverageSalaryByIndustry(){
        return marketService.getAverageSalaryByIndustry();
    }

}