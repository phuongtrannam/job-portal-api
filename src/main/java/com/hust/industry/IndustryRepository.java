package com.hust.industry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface IndustryRepository extends CrudRepository<Industry, String> {

    @Query(value = "select * from job" , nativeQuery = true)
    List<Object[]> getJobList();

    @Query(value = "select * from industries" , nativeQuery = true)
    List<Object[]> getIndustryList();


    // @Query(value = "select * from job" , nativeQuery = true)
    // List<Object[]> getJobListByIndustry();

    // @Query(value = "select * from company where idCompany = :id" , nativeQuery = true)
    // List<Object[]> getJobInfo(@Param("id") String id);

    @Query(value = "select * from job " + 
                    "where job.idJob in " + 
                        "( select id_job2 from relate_jobs where relate_jobs.id_job1 = :id)" +
                    "or job.idJob in " + 
                        "( select id_job1 from relate_jobs where relate_jobs.id_job2 = :id);", nativeQuery = true)
    List<Object[]> getRelatedJob(@Param("id") String id);

    
    @Query(value = "select job_fact.idTime,job_fact.idJob, province.province, " + 
                        "timed.timestampD, sum(job_fact.number_of_recruitment) " +
                    "from job_fact, province, timed " +
                    "where job_fact.idTime = timed.idTime " + 
                    "and job_fact.idProvince = province.idProvince " +
                    "and job_fact.idJob = :id " +
                    // "and province.province = \"Hà Nội\" " + 
                    "group by job_fact.idTime,job_fact.idJob,province.province, timed.timestampD " +
                    "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);
        
    @Query(value = "select job_fact.idJob,timed.idTime,timed.timestampD, " + 
                        "age.age, gender.gender, sum(job_fact.number_of_recruitment) " +
                    "from job_fact, gender, age, timed " + 
                    "where job_fact.idTime = timed.idTime " + 
                    "and job_fact.idGender = gender.idGender " + 
                    "and job_fact.idAge = age.idAge " +
                    "and job_fact.idJob = :id " + 
                    "and job_fact.idTime in " + 
                                "(select idTime from ( select idTime from timed " + 
                                                "order by timestampD desc limit 3) as t ) " +
                    "group by job_fact.idJob,job_fact.idTime, " +
                            "timed.timestampD, age.age, gender.gender " +
                    "order by timed.timestampD, age.age, gender,gender;", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("id") String id);

    @Query(value = "select job_fact.idJob,timed.idTime, timed.timestampD, " + 
                        "academic_level.academic_level, sum(job_fact.number_of_recruitment) " +
                    "from job_fact, academic_level, timed " +
                    "where job_fact.idTime = timed.idTime " + 
                    "and job_fact.idAcademic_Level = academic_level.idAcademic_Level " + 
                    "and job_fact.idJob = :id " +
                    "and job_fact.idTime in " + 
                        "(select idTime from " + 
                            "(select idTime from timed " + 
                            "order by timestampD desc limit 3) as t ) " +
                    "group by job_fact.idJob,timed.idTime,timed.timestampD, " + 		
                                "academic_level.academic_level " +
                    "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("id") String id);

    @Query(value = "select job_fact.idTime,job_fact.idJob, province.province, " + 
                            "timed.timestampD, avg(job_fact.salary) " +
                    "from job_fact, province, timed " +
                    "where job_fact.idTime = timed.idTime " + 
                    "and job_fact.idProvince = province.idProvince " + 
                    "and job_fact.idJob = :id " +
                    // "and province.province = \"Hà Nội\" " + 
                    "group by job_fact.idTime, job_fact.idJob,province.province, timed.timestampD " +
                    "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getAverageSalaryByPeriodOfTime(@Param("id") String id);
}