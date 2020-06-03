package com.hust.region;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    //todo
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/basic_region_searching", method = RequestMethod.GET)
    public JSONObject basicRegionSearching(@RequestBody Map<String, Object> payload) throws Exception {
        String strRegion = payload.get("id").toString();
        return regionService.getRootAndSubRegions(strRegion);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_root_regions", method = RequestMethod.GET)
    public JSONObject getRootRegions(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getRootRegion(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping( value = "/regions/get_sub_regions", method = RequestMethod.GET)
    public JSONObject getSubRegions(@RequestBody Map<String, Object> payload) throws Exception{
        String regionId = payload.get("id").toString();
        return regionService.getSubRegion(regionId);
    }


    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping( value = "/regions/get_root_sub_regions", method = RequestMethod.GET)
    public JSONObject getRootAndSubRegions(@RequestBody Map<String, Object> payload) throws Exception{
        String regionId = payload.get("id").toString();
        return regionService.getRootAndSubRegions(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_dashboard_data", method = RequestMethod.GET)
    public JSONObject getDashboardData(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getDashboardData(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_average_salary_by_industry", method = RequestMethod.GET)
    public JSONObject getAverageSalaryByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getAverageSalaryByIndustry(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_job_demand_by_industry", method = RequestMethod.GET)
    public JSONObject getJobDemandByIndustry(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getJobDemandByIndustry(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_highest_salary_jobs", method = RequestMethod.GET)
    public JSONObject getHighestSalaryJobs(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getHighestSalaryJobs(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_highest_demand_jobs", method = RequestMethod.GET)
    public JSONObject getHighestDemandJobs(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getHighestDemandJobs(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_highest_paying_companies", method = RequestMethod.GET)
    public JSONObject getHighestPayingCompanies(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getHighestPayingCompanies(regionId);
    }

    //done
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_top_hiring_companies", method = RequestMethod.GET)
    public JSONObject getTopHiringCompanies(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getTopHiringCompanies(regionId);
    }

    //todo
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/regions/get_related_regions", method = RequestMethod.POST)
    public JSONObject getRelatedRegions(@RequestBody Map<String, Object> payload) throws Exception {
        String regionId = payload.get("id").toString();
        return regionService.getRelatedRegions(regionId);
    }

    
}