package com.hust.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

    @Query(value = "select company.idCompany, company.name_company, company.phone, " +
                            "company.`description`,company.founded_year,company.website, " +
                            "concat(location.location,\",\",province.province) as location_detail " +
                    "from company, location, province " +
                    "where company.idLocation = location.idLocation " +
                    "and location.idProvince = province.idProvince;", nativeQuery = true)
    List<Object[]> getCompanyList();


    @Query(value = "select industries.idIndustry, industries.name_industry, company.name_company " +
                    "from company, companies_industries, industries " +
                    "where companies_industries.idCompany = company.idCompany " + 
                    "and companies_industries.idIndustry = industries.idIndustry " +
                    "and company.idCompany = :id " +
                    "order by company.name_company;", nativeQuery = true)
    List<Object[]> getBusinessLinesOfTheCompany(@Param("id") String id);


    @Query(value = "select company.name_company, company.phone, " + 
                        "company.`description`,company.founded_year,company.website, " +
                        "concat(location.location,\",\", province.province) as location_detail, " + 
                        "count(company_fact.idJob) as total_job " +
                    "from company, company_fact, province, location, industries " +
                    "where company_fact.idCompany = company.idCompany " + 
                    "and company.idLocation = location.idLocation " +
                    "and location.idProvince = province.idProvince " + 
                    "and company_fact.idIndustry = industries.idIndustry " +
                    "and company_fact.idTime in " + 
                            "( select idTime from " + 
                                    "( select idTime from timed order by timestampD desc limit 1 ) as t )" +
                    "and company.idCompany = :id " +
                    "group by company.idCompany " +
                    "order by company.name_company;", nativeQuery = true)
    List<Object[]> getCompanyInfo(@Param("id") String id);

    @Query(value = "select company.idLocation, company.name_company, company.phone, " + 
                            "company.`description`,company.founded_year,company.website, " +
                            "concat(location.location,\",\",province.province) as location_detail " +
                    "from company, location, province " +
                    "where company.idLocation = location.idLocation " + 
                    "and location.IdProvince = province.IdProvince " +
                    "and (company.idCompany in " + 
                            "( select idCompany2 from relate_companies " + 
                            "where relate_companies.idCompany1 = :id) " +
                    "or company.idCompany in " + 
                            "( select idCompany1 from relate_companies " + 
                            "where relate_companies.idCompany2 = :id));", nativeQuery = true)
    List<Object[]> getRelatedCompany(@Param("id") String id);

    @Query(value = "select job.idJob, job.name_job, province.province, " +
                         "min(company_fact.salary), max(company_fact.salary), sum(number_of_recruitment) " +
                    "from company, company_fact, job, province " +
                    "where company.idCompany = company_fact.idCompany " + 
                    "and company_fact.idJob = job.idJob " +
                    "and company_fact.idProvince = province.idProvince " +
                    "and company_fact.idTime in " + 
                        "( select idTime from " + 
                            "( select idTime from timed order by timestampD desc limit 1 ) as t )" +
                    "and company.idCompany = :id " +
                    "group by company_fact.idJob, company_fact.idProvince ;", nativeQuery = true)
    List<Object[]> getRecentJobByCompany(@Param("id") String id);

    

    @Query(value = "select company_fact.idCompany, province.province, " + 
                    "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "sum(company_fact.number_of_recruitment) as so_luong_tuyen_dung " +
                    "from company_fact, province, timed " +
                    "where company_fact.idTime = timed.idTime " + 
                    "and company_fact.idProvince = province.idProvince " +
                    "and company_fact.idCompany = :id " +
                    "group by company_fact.idCompany,province.province, timed.idTime " +
                    "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);


    @Query(value = "select company_fact.idCompany,timed.idTime, " +
                        "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                        "academic_level.academic_level, sum(company_fact.number_of_recruitment) " +
                    "from company_fact, academic_level, timed " +
                    "where company_fact.idTime = timed.idTime " + 
                    "and company_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and company_fact.idCompany = :id " +
                    "and company_fact.idTime in " + 
                        "(select idTime from " + 
                            "(select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by company_fact.idCompany,timed.idTime,timed.timestampD,academic_level.academic_level " + 
                    "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("id") String id);


    @Query(value = "select company_fact.idCompany,timed.idTime, " + 
                    "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "age.age, gender.gender, sum(company_fact.number_of_recruitment) " +
                    "from company_fact, gender, age, timed " +
                    "where company_fact.idTime = timed.idTime " + 
                    "and company_fact.idGender = gender.idGender " +
                    "and company_fact.idAge = age.idAge " +
                    "and company_fact.idCompany = :id " +
                    "and company_fact.idTime in " + 
                        "(select idTime from " + 
                            "( select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by timed.idTime, age.age, gender.gender " +
                    "order by timed.timestampD, age.age, gender,gender;", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("id") String id);
}