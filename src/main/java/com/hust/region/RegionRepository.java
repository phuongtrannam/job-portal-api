package com.hust.region;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RegionRepository extends CrudRepository<Region, String> {


    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRootRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getSubRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRelatedRegions(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getNumberJobPostingInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getNumberCompanyInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageAgeInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryByIndustry(@Param("id") String id);
    
    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandByIndustry(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestSalaryJobs(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestDemandJobs(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestPayingCompanies(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getTopHiringCompanies(@Param("id") String id);




}