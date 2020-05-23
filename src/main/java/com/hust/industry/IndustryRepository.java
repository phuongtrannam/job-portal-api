package com.hust.industry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends CrudRepository<Industry, String> {


    @Query(value = "select * from job fact" , nativeQuery = true)
    List<Object[]> getIndustryList();

    @Query(value = "select job.idJob, job.name_job,\n" +
            "       sum(job_fact.number_of_recruitment), min(job_fact.salary), max(job_fact.salary),\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime\n" +
            "from job_industry, industries, job, job_fact,timed\n" +
            "where job.idJob = job_industry.idJob and industries.idIndustry = job_industry.idIndustry\n" +
            "  and job.idJob = job_fact.idJob and timed.idTime = job_fact.idTime\n" +
            "  and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 2) as t )\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by job_fact.idTime,industries.idIndustry, job.idJob\n" +
            "order by industries.idIndustry, job.idJob, timed.idTime desc;", nativeQuery = true)
    List<Object[]> getJobListByIndustry(@Param("industryId") String industryId);

    @Query(value = "select company.idCompany, company.name_company,\n" +
            "       top.number_of_recruitment, top.growth, top.rank_company,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime\n" +
            "from top10_companies_with_the_highest_recruitment_by_industries as top, timed, company, industries\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId\n" +
            "  and top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t )\n" +
            "order by top.idTime,top.idIndustry, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustryWithCountry(@Param("industryId") String industryId);

    @Query(value = "with rank_companies_1 as (\n" +
            "select company.idCompany, company.name_company,\n" +
            "       sum(number_of_recruitment) as `so luong tuyen dung`, avg(salary) as `luong`,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime,\n" +
            "       rank() over (partition by idTime,province.idProvince, industries.idIndustry order by sum(number_of_recruitment) desc) as `rank`\n" +
            "from company_fact, timed, company, industries, province\n" +
            "where company_fact.idTime = timed.idTime and company_fact.idProvince = province.idProvince\n" +
            "    and company_fact.idIndustry = industries.idIndustry and company_fact.idCompany = company.idCompany\n" +
            "    and company_fact.idProvince = :idProvince and company_fact.idIndustry = :industryId\n" +
            "group by timed.idTime, province.idProvince, industries.idIndustry, company_fact.idCompany\n" +
            "order by idTime, province.idProvince, industries.idIndustry,sum(number_of_recruitment) desc)\n" +
            "select idCompany, name_company, `so luong tuyen dung`, `luong`, `time`, idTime \n" +
            "from rank_companies_1\n" +
            "where `rank` <= 10\n" +
            "  and rank_companies_1.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t );", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustryWithProvince(@Param("idProvince") String idProvince, @Param("industryId") String industryId);

    @Query(value = "select timed.idTime, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, timed, industries\n" +
            "where market_fact.idTime = timed.idTime\n" +
            "  and market_fact.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by market_fact.idTime,industries.idIndustry\n" +
            "order by market_fact.idTime;", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithCountry(@Param("industryId") String industryId );

    @Query( value = "select timed.idTime, province.province, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, timed, industries, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idProvince = province.idProvince\n" +
            "  and market_fact.idIndustry = industries.idIndustry\n" +
            "  and province.idProvince = :locationId and industries.idIndustry = :industryId\n" +
            "group by market_fact.idTime, province.idProvince, industries.idIndustry\n" +
            "order by market_fact.idTime;", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithProvince(@Param("industryId") String industryId, @Param("locationId") String locationId);
    
    @Query(value = "select industries.name_industry, province.idProvince, Province, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, timed, industries, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idProvince = province.idProvince\n" +
            "  and market_fact.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by province.idProvince, market_fact.idTime, industries.idIndustry\n" +
            "order by province.idProvince;", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegionOfCountry(@Param("industryId") String industryId);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getTopHiringCompany(@Param("industryId") String industryId, @Param("locationId") String locationId );
    
    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getTopHiringJob(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestSalaryJob(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("industryId") String industryId, @Param("locationId") String locationId );




    // @Query(value = "select job_fact.idTime,concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
    //                     "job_fact.idJob, province.province, timed.timestampD, sum(job_fact.number_of_recruitment) " +  
    //                 "from job_fact, province, timed " +
    //                 "where job_fact.idTime = timed.idTime " + 	
    //                 "and job_fact.idProvince = province.idProvince " +
    //                 "and job_fact.idJob = :id " +
    //                 // "--and province.province = \"Hà Nội\" " + 
    //                 "group by job_fact.idTime,job_fact.idJob,province.province, timed.timestampD " +
    //                 "order by province.province, timed.timestampD;", nativeQuery = true)
    // List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);

    @Query(value = "select company.name_company, avg(salary)\n" +
            "from company_fact, company, timed\n" +
            "where company_fact.idCompany = company.idCompany and company_fact.idTime = timed.idTime\n" +
            "    and timed.idTime = :idTime and company.idCompany = :idCompany\n" +
            "group by company_fact.idTime, company_fact.idCompany;", nativeQuery = true)
    List<Object[]> getAvgSalaryForCompanyByCountry(@Param("idTime") String idTime, @Param("idCompany") String idCompany);


    // lay thong tin  so luong tuyen dung cong ty theo quy
    @Query(value = "select company.name_company, sum(number_of_recruitment)\n" +
            "from company_fact, company, timed, province, industries\n" +
            "where company_fact.idCompany = company.idCompany and company_fact.idTime = timed.idTime\n" +
            "    and company_fact.idProvince = province.idProvince and company_fact.idIndustry = industries.idIndustry\n" +
            "    and timed.idTime = :idTime and company.idCompany = :idCompany and province.idProvince = :idProvince\n" +
            "    and industries.idIndustry = :industryId\n" +
            "group by company_fact.idTime, company_fact.idCompany,industries.idIndustry, province.idProvince;", nativeQuery = true)
    List<Object[]> getRecruitmentOfCompanyInQuarter( @Param("idTime") String idTime, @Param("idCompany") String idCompany,
                                                     @Param("idProvince") String idProvince, @Param("industryId") String industryId);

    


}