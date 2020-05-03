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

    // @Query(value = "select * from company where idCompany = :id" , nativeQuery = true)
    // List<Object[]> getCompanyInfo(@Param("id") String id);

    // @Query(value = "select * from company " + 
    // "where company.idCompany in ( select idCompany2 from relate_companies where relate_companies.idCompany1 = :id) " + 
    // "or company.idCompany in ( select idCompany1 from relate_companies where relate_companies.idCompany2 = :id);", nativeQuery = true)
    // List<Object[]> getRelatedCompany(@Param("id") String id);

    // @Query(value = "select company.idCompany, company.name_company, job.name_job, " + 
    //                 "province.province, min(company_fact.salary), max(company_fact.salary) " + 
    //                 "from company, company_fact, job, province " + 
    //                 "where company.idCompany = company_fact.idCompany and company_fact.idJob = job.idJob " + 
    //                     "and company_fact.idProvince = province.idProvince " + 
    //                     "and company_fact.idTime in ( " +
    //                         "select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t ) " + 
    //                     "and company.idCompany = :id " +
    //                 "group by company_fact.idJob, company_fact.idProvince ;", nativeQuery = true)
    // List<Object[]> getCurrentJobByCompany(@Param("id") String id);

    
    // @Query(value = "select company_fact.idCompany, province.province, timed.timestampD, " +  
    //                                 "sum(company_fact.number_of_recruitment) " + 
    //                 "from company_fact, province, timed " +
    //                 "where company_fact.idTime = timed.idTime " + 
    //                         "and company_fact.idProvince = province.idProvince " + 
    //                         "and company_fact.idCompany = :id " + 
    //                 "group by province.province, timed.timestampD " +  
    //                 "order by province.province, timed.timestampD;", nativeQuery = true)
    // List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);

}