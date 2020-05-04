package com.hust.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

    @Query(value = "select * from company" , nativeQuery = true)
    List<Object[]> getCompanyList();

    @Query(value = "select company.*,location.location, " + 
                        "province.province, count(company_fact.idJob) " +
                    "from company, company_fact, province, location, industries " +
                    "where company_fact.idCompany = company.idCompany " + 
                        "and company.idLocation = location.idLocation " +
                        "and location.idProvince = province.idProvince " + 
                        "and company_fact.idIndustry = industries.idIndustry " + 
                        "and company_fact.idTime in " + 
                            "( select idTime from " + 
                                "( select idTime from timed " +
                                "order by timestampD desc limit 1 ) as t ) " +
                        "and company.idCompany = :id " +
                    "group by company.idCompany " +
                    "order by company.name_company;" , nativeQuery = true)
    List<Object[]> getCompanyInfo(@Param("id") String id);

    @Query(value = "select * from company " + 
    "where company.idCompany in ( select idCompany2 from relate_companies where relate_companies.idCompany1 = :id) " + 
    "or company.idCompany in ( select idCompany1 from relate_companies where relate_companies.idCompany2 = :id);", nativeQuery = true)
    List<Object[]> getRelatedCompany(@Param("id") String id);

    @Query(value = "select company.idCompany, company.name_company, job.name_job, " + 
                    "province.province, min(company_fact.salary), max(company_fact.salary) " + 
                    "from company, company_fact, job, province " + 
                    "where company.idCompany = company_fact.idCompany and company_fact.idJob = job.idJob " + 
                        "and company_fact.idProvince = province.idProvince " + 
                        "and company_fact.idTime in ( " +
                            "select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t ) " + 
                        "and company.idCompany = :id " +
                    "group by company_fact.idJob, company_fact.idProvince ;", nativeQuery = true)
    List<Object[]> getRecentJobByCompany(@Param("id") String id);

    

    @Query(value = "select company_fact.idCompany, province.province, timed.timestampD, " +  
                                    "sum(company_fact.number_of_recruitment) " + 
                    "from company_fact, province, timed " +
                    "where company_fact.idTime = timed.idTime " + 
                            "and company_fact.idProvince = province.idProvince " + 
                            "and company_fact.idCompany = :id " + 
                    "group by province.province, timed.timestampD " +  
                    "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);


    @Query(value = "select company_fact.idCompany,timed.idTime, timed.timestampD, " + 
                        "academic_level.academic_level, sum(company_fact.number_of_recruitment) " +
                    "from company_fact, academic_level, timed " +
                    "where company_fact.idTime = timed.idTime " + 
                        "and company_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                        "and company_fact.idCompany = :id " +
                        "and company_fact.idTime in " + 
                            "(select idTime from " + 
                                "(select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by company_fact.idCompany,timed.idTime,timed.timestampD, " + 
                            "academic_level.academic_level " +
                    "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("id") String id);
}