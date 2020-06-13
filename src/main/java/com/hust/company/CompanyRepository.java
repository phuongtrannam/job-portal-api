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
                            "location.location as location_detail " +
                    "from company, location " +
                    "where company.idLocation = location.idLocation ;", nativeQuery = true)
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
                        "location.location as location_detail, " +
                        "count(company_fact.idJob) as total_job " +
                    "from company, company_fact, location, industries " +
                    "where company_fact.idCompany = company.idCompany " + 
                    "and company.idLocation = location.idLocation " +
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
                            "location.location as location_detail " +
                    "from company, location " +
                    "where company.idLocation = location.idLocation " +
                    "and (company.idCompany in " + 
                            "( select idCompany2 from relate_companies " + 
                            "where relate_companies.idCompany1 = :id) " +
                    "or company.idCompany in " + 
                            "( select idCompany1 from relate_companies " + 
                            "where relate_companies.idCompany2 = :id)) limit 3;", nativeQuery = true)
    List<Object[]> getRelatedCompany(@Param("id") String id);

    @Query(value = "select job.idJob, job.name_job, province.province, " +
                         "min(fact.salary), max(fact.salary), sum(number_of_recruitment) " +
                    "from company, (select distinct idTime, idCompany,idProvince, idJob,salary, number_of_recruitment\n" +
            "               from company_fact where idCompany = :id) as fact, job, province " +
                    "where company.idCompany = fact.idCompany " +
                    "and fact.idJob = job.idJob " +
                    "and fact.idProvince = province.idProvince " +
                    "and fact.idTime in " +
                        "( select idTime from " + 
                            "( select idTime from timed order by timestampD desc limit 1 ) as t )" +
                    "group by fact.idJob, fact.idProvince ;", nativeQuery = true)
    List<Object[]> getRecentJobByCompany(@Param("id") String id);

    

    @Query(value = "select fact.idCompany, province.province, " +
                    "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "sum(fact.number_of_recruitment) as so_luong_tuyen_dung " +
                    "from (select distinct idTime, idCompany,idProvince, idJob, number_of_recruitment\n" +
            "               from company_fact where idCompany = :id) as fact, province, timed " +
                    "where fact.idTime = timed.idTime " +
                    "and fact.idProvince = province.idProvince " +
                    "group by fact.idCompany,province.province, timed.idTime " +
                    "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);


    @Query(value = "select fact.idCompany,timed.idTime, " +
                        "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                        "academic_level.academic_level, sum(fact.number_of_recruitment) " +
                    "from (select distinct idTime, idCompany,idAcademic_Level, idJob, number_of_recruitment\n" +
            "               from company_fact where idCompany = :id) as fact, academic_level, timed " +
                    "where fact.idTime = timed.idTime " +
                    "and fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and fact.idTime in " +
                        "(select idTime from " + 
                            "(select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by fact.idCompany,timed.idTime,timed.timestampD,academic_level.academic_level " +
                    "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("id") String id);


    @Query(value = "select fact.idCompany,timed.idTime, " +
                    "concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "age.age, gender.gender, sum(fact.number_of_recruitment) " +
                    "from (select distinct idTime, idCompany,idAge, idGender, idJob, number_of_recruitment\n" +
            "               from company_fact where idCompany = :id) as fact, gender, age, timed " +
                    "where fact.idTime = timed.idTime " +
                    "and fact.idGender = gender.idGender " +
                    "and fact.idAge = age.idAge " +
                    "and fact.idTime in " +
                        "(select idTime from " + 
                            "( select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by timed.idTime, age.age, gender.gender " +
                    "order by timed.timestampD, age.age, gender,gender;", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("id") String id);
}