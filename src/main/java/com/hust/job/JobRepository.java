package com.hust.job;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job, String> {

    @Query(value = "", nativeQuery = true)
    List<Object[]> basicSearchJob(@Param("queryContent") String queryContent);
    
    @Query(value = "", nativeQuery = true)
    List<Object[]> advancedSearchJob(@Param("queryContent") String queryContent, 
                                    @Param("idLocation") String idLocation,
                                    @Param("idIndustry") String idIndustry,
                                    @Param("salary") String salary);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobInfo(@Param("idJob") String idJob);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobRelated(@Param("idJob") String idJob);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getSkillRequired(@Param("idJob") String idJob);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getAverageSalary(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getAverageSalaryInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("idJob") String idJob, @Param("idLocation") String idLocation);


    // Danh sach nhom ki nang can thiet

    // Su thay doi yeu cau ki nang theo thoi gian


}