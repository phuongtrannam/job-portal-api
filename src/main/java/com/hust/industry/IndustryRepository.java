package com.hust.industry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface IndustryRepository extends CrudRepository<Industry, String> {


    @Query(value = "" , nativeQuery = true)
    List<Object[]> getIndustryList();

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobListByIndustry(@Param("industryId") String industryId);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustry(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandByIndustry(@Param("industryId") String industryId, @Param("locationId") String locationId );
    
    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegion(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "", nativeQuery = true)
    List<Object[]> getTopHiringCompany(@Param("industryId") String industryId, @Param("locationId") String locationId );
    
    @Query(value = "", nativeQuery = true)
    List<Object[]> getTopHiringJob(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "", nativeQuery = true)
    List<Object[]> getHighestSalaryJob(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("industryId") String industryId, @Param("locationId") String locationId );

    @Query(value = "", nativeQuery = true)
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