package com.hust.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

        @Query( value = " select * from age order by age;", nativeQuery = true)
        List<Object[]> getAgeRange();

        @Query( value = " select * from academic_level order by academic_level;", nativeQuery = true)
        List<Object[]> getLiteracy();
        @Query(value = "select company.idCompany, company.name_company, company.phone, "
                        + "company.`description`,company.founded_year,company.website, "
                        + "location.location as location_detail " + "from company, location "
                        + "where company.idLocation = location.idLocation ;", nativeQuery = true)
        List<Object[]> getCompanyList();

        @Query(value = "select count(idJob)\n" +
                "from (select distinct idTime, idCompany, idJob from company_fact where idCompany = :idCompany) as fact\n" +
                "where idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t )\n" +
                "group by idTime, idCompany;", nativeQuery = true)
        List<Object[]> getNumJobByCompany(@Param("idCompany") int idCompany);

        @Query(value = "select * from company where company.name_company = :jobName", nativeQuery = true)
        List<Object[]> getIdCompanyByName(@Param("jobName") String jobName);

        @Query(value =  "select fact.idTime, company.idCompany ,company.name_company, " +
                        "sum(fact.number_of_recruitment) as `so luong tuyen dung`, count(fact.idJob) as `so luong cong viec`, location.location \n" +
                        "from company,location, company_fact as fact " +
                        "where fact.idCompany = company.idCompany and fact.idCompany = (:id) and company.idLocation = location.idLocation \n" +
                        "and fact.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                        "group by fact.idTime, fact.idCompany; " , nativeQuery = true)
        List<Object[]> searchCompany(@Param("id") List<String> id);

        @Query(value = "select industries.idIndustry, industries.name_industry, company.name_company "
                        + "from company, companies_industries, industries "
                        + "where companies_industries.idCompany = company.idCompany "
                        + "and companies_industries.idIndustry = industries.idIndustry "
                        + "and company.idCompany = :id " + "order by company.name_company;", nativeQuery = true)
        List<Object[]> getBusinessLinesOfTheCompany(@Param("id") String id);

        @Query(value = "select company.name_company, company.phone, "
                        + "company.`description`,company.founded_year,company.website, "
                        + "location.location as location_detail, " + "count(company_fact.idJob) as total_job "
                        + "from company, company_fact, location, industries "
                        + "where company_fact.idCompany = company.idCompany "
                        + "and company.idLocation = location.idLocation "
                        + "and company_fact.idIndustry = industries.idIndustry " + "and company_fact.idTime in "
                        + "( select idTime from "
                        + "( select idTime from timed order by timestampD desc limit 1 ) as t )"
                        + "and company.idCompany = :id " + "group by company.idCompany "
                        + "order by company.name_company;", nativeQuery = true)
        List<Object[]> getCompanyInfo(@Param("id") String id);

        @Query(value = "select company.idLocation, company.name_company, company.phone, "
                        + "company.`description`,company.founded_year,company.website, "
                        + "location.location as location_detail " + "from company, location "
                        + "where company.idLocation = location.idLocation " + "and (company.idCompany in "
                        + "( select idCompany2 from relate_companies " + "where relate_companies.idCompany1 = :id) "
                        + "or company.idCompany in " + "( select idCompany1 from relate_companies "
                        + "where relate_companies.idCompany2 = :id)) limit 3;", nativeQuery = true)
        List<Object[]> getRelatedCompany(@Param("id") String id);

        @Query(value = "select job.idJob, job.name_job, province.province, "
                        + "min(fact.salary), max(fact.salary), sum(number_of_recruitment) "
                        + "from company, company_fact as fact, job, province "
                        + "where company.idCompany = fact.idCompany and fact.idJob = job.idJob and fact.idCompany = :id"
                        + " and fact.idProvince = province.idProvince " + "and fact.idTime in " + "( select idTime from "
                        + "( select idTime from timed order by timestampD desc limit 1 ) as t )"
                        + "group by fact.idJob, fact.idProvince "
                        + "order by sum(number_of_recruitment) desc  limit 5;", nativeQuery = true)
        List<Object[]> getRecentJobByCompany(@Param("id") String id);

        @Query(value = "select fact.idCompany, province.province, "
                        + "concat(timed.quarterD,\"/\",timed.yearD) as `time`, "
                        + "sum(fact.number_of_recruitment) as so_luong_tuyen_dung "
                        + "from company_fact as fact, province, timed "
                        + "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince and fact.idCompany = :id \n"
                        + "group by fact.idCompany,province.province, timed.idTime "
                        + "order by province.province, timed.timestampD;", nativeQuery = true)
        List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);

        @Query(value = "select fact.idCompany,timed.idTime, " + "concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, "
                        + "academic_level.academic_level, sum(fact.number_of_recruitment) "
                        + "from company_fact as fact, academic_level, timed "
                        + "where fact.idTime = timed.idTime and fact.idCompany = :companyId "
                        + "and fact.idAcademic_Level = academic_level.idAcademic_Level " + "and fact.idTime in "
                        + "(select idTime from " + "(select idTime from timed order by timestampD desc limit 3) as t ) "
                        + "group by fact.idCompany,timed.idTime,timed.timestampD,academic_level.academic_level "
                        + "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
        List<Object[]> getJobDemandByLiteracy(@Param("companyId") String companyId);

        @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment),\n" +
                        "age.idAge,bin(age.`0-18`),bin(age.`18-25`), bin(age.`25-35`), bin(age.`35-50`), bin(age.`50+`),\n" +
                        "gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
                        "from (select distinct idTime, idCompany,idAge, idGender, idJob, number_of_recruitment " +
                                "from company_fact where idCompany = :companyId) as fact, gender, age, timed " + 
                        "where fact.idTime = timed.idTime and fact.idGender = gender.idGender " +
                        "and fact.idAge = age.idAge and fact.idTime in (select idTime from  " +
                        "( select idTime from timed order by timestampD desc limit 3) as t ) " +
                        "group by timed.idTime, age.idAge, gender.idGender " +
        "order by timed.timestampD, age.idAge, gender.idGender;", nativeQuery = true)
        List<Object[]> getJobDemandByAge(@Param("companyId") String companyId);

        @Query( value = "select timed.idTime,company.name_company,\n" +
                "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
                "       sum(company_fact.number_of_recruitment)\n" +
                "from company_fact, timed, company\n" +
                "where company_fact.idCompany = :companyId and company_fact.idTime = timed.idTime\n" +
                "  and company_fact.idCompany = company.idCompany\n" +
                "  and company_fact.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 4 ) as t )\n" +
                "group by timed.idTime, company_fact.idCompany\n" +
                "order by timed.idTime;", nativeQuery = true)
        List<Object[]> getJobDemandByCompany(@Param("companyId") String companyId);

        @Query( value = "select timed.idTime,company.name_company,\n" +
                "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
                "       avg(company_fact.salary)\n" +
                "from company_fact, timed, company\n" +
                "where company_fact.idCompany = :companyId and company_fact.idTime = timed.idTime\n" +
                "  and company_fact.idCompany = company.idCompany\n" +
                "  and company_fact.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 4 ) as t )\n" +
                "group by timed.idTime, company_fact.idCompany\n" +
                "order by timed.idTime;", nativeQuery = true)
        List<Object[]> getSalaryByCompany(@Param("companyId") String companyId);

        @Query( value = "with job_1 as (\n" +
                "    select fact.idTime, fact.idCompany, job.idJob, job.name_job,\n" +
                "           sum(fact.number_of_recruitment) as `so luong tuyen dung`, min(salary) as `min`, max(salary) as `max`,\n" +
                "           rank() over(partition by fact.idTime, fact.idCompany order by sum(fact.number_of_recruitment) desc) as `rank`\n" +
                "    from (select distinct idTime,idCompany, idJob, number_of_recruitment, salary from company_fact) as fact, job\n" +
                "    where fact.idJob = job.idJob\n" +
                "      and fact.idCompany = :id \n" +
                "    group by fact.idTime, fact.idCompany, job.idJob\n" +
                "    order by fact.idTime, fact.idCompany, sum(fact.number_of_recruitment) desc)\n" +
                "select idJob, name_job, `so luong tuyen dung`,`min`,`max`, timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
                "from job_1, timed\n" +
                "where `rank` <= 10 and job_1.idTime = timed.idTime\n" +
                "order by timed.idTime,idCompany,`rank`;", nativeQuery = true)
        List<Object[]> getHighestDemandJobCompany(@Param("id") int id);

        @Query( value = "with job_1 as (\n" +
                "    select fact.idTime, fact.idCompany, job.idJob, job.name_job,\n" +
                "           max(salary) as `max`, sum(number_of_recruitment) as `so luong tuyen dung`,\n" +
                "           rank() over(partition by fact.idTime, fact.idCompany order by max(fact.salary) desc) as `rank`\n" +
                "    from (select distinct idTime,idCompany, idJob, number_of_recruitment, salary from company_fact) as fact, job\n" +
                "    where fact.idJob = job.idJob\n" +
                "      and fact.idCompany = :id\n" +
                "    group by fact.idTime, fact.idCompany, job.idJob\n" +
                "    order by fact.idTime, fact.idCompany, sum(fact.number_of_recruitment) desc)\n" +
                "select idJob, name_job, `max`,`so luong tuyen dung`, timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
                "from job_1, timed\n" +
                "where `rank` <= 10 and job_1.idTime = timed.idTime\n" +
                "order by timed.idTime,idCompany,`rank`;", nativeQuery = true)
        List<Object[]> getHighestSalaryJobCompany(@Param("id") int id);

        @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
                "from timed\n" +
                "order by idTime desc;", nativeQuery = true)
        List<Object[]> getTimeStamps();
}