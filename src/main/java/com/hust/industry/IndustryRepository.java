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
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from job_industry, industries, job, job_fact,timed\n" +
            "where job.idJob = job_industry.idJob and industries.idIndustry = job_industry.idIndustry\n" +
            "  and job.idJob = job_fact.idJob and timed.idTime = job_fact.idTime\n" +
            "  and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 2) as t )\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by job_fact.idTime,industries.idIndustry, job.idJob\n" +
            "order by industries.idIndustry, job.idJob, timed.idTime desc;", nativeQuery = true)
    List<Object[]> getJobListByIndustry(@Param("industryId") String industryId);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustry(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandByIndustry(@Param("industryId") String industryId, @Param("locationId") String locationId );
    
    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegion(@Param("industryId") String industryId, @Param("locationId") String locationId );

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
        
    


}