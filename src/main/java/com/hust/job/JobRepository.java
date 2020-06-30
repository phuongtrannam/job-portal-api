package com.hust.job;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job, String> {
   
    @Query(value = "select * from province order by Province", nativeQuery = true)
    List<Object[]> getCityList();

    @Query(value = "select fact.idTime,job.idJob, job.name_job,\n" +
            "min(salary), max(salary), sum(fact.number_of_recruitment) as `so luong tuyen dung`\n" +
            "from job, job_fact as fact\n" +
            "where job.idJob = fact.idJob\n" +
            "and fact.idTime in ( select idTime from (\n" +
            "select idTime from timed order by timestampD desc limit 1 ) as t )\n" +
            "group by fact.idTime,fact.idJob\n" +
            "order by sum(fact.number_of_recruitment) desc limit :numJob", nativeQuery = true)
    List<Object[]> getTopJob(@Param("numJob") int numJob);

    @Query( value = "select distinct gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
            "from job_fact, gender\n" +
            "where job_fact.idGender = gender.idGender\n" +
            "and job_fact.idJob = :idJob", nativeQuery = true)
    List<Object[]> getGenderByJob(@Param("idJob") int idJob);
    
    @Query(value = "select job_fact.idTime,job.idJob, job.name_job, " + 
                    "min(salary), max(salary), sum(job_fact.number_of_recruitment) as `so luong tuyen dung` " +
                    "from job, job_fact , job_industry, province " +
                    "where job.idJob = job_fact.idJob and job.idJob = job_industry.idJob " +
                    "and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                    "and job.name_job = :queryContent " +
                    "group by job_fact.idTime,job_fact.idJob " +
                    "order by  job_fact.idJob asc, job_fact.idTime desc " , nativeQuery = true)
    List<Object[]> basicSearchJob(@Param("queryContent") String queryContent);


    @Query(value = "with jobs_1 as ( " +
                    "select job_fact.idTime,job.idJob, job.name_job, " + 
                        "min(salary) as `min salary`, max(salary) as `max salary`, " + 
                        "sum(job_fact.number_of_recruitment) as `so luong tuyen dung`, " +
                        "rank() over( partition by job_fact.idJob order by idTime desc) as `rank_time` " + 
                    "from job, job_fact , job_industry, province, years_experience, industries " +
                    "where job.idJob = job_fact.idJob and job.idJob = job_industry.idJob " +
                    "and job_industry.idIndustry = industries.idIndustry " +   
                    "and job_fact.IdProvince = province.idProvince " +
                    "and job.name_job = :jobName " +
                    "and industries.idIndustry IN (:industryList) " +
                    "and province.idProvince IN (:regionList) " +
                    "and job_fact.salary between :minSalary and :maxSalary " +
                    "group by job_fact.idTime,job_fact.idJob " +
                    "order by job_fact.idTime, job_fact.idJob desc) " + 
                    "select distinct jobs_1.idTime, jobs_1.idJob, jobs_1.name_job, " + 
                    "jobs_1.`min salary`, jobs_1.`max salary`, jobs_1.`so luong tuyen dung` " +  
                    "from jobs_1,  job_fact " +
                    "where job_fact.idJob = jobs_1.idJob " + 
                    "and rank_time = 1;" , nativeQuery = true)
    List<Object[]> advancedSearchJob(@Param("jobName") String jobName, 
                                    @Param("industryList") List<String> industryList,
                                    @Param("regionList") List<String> regionList,
                                    @Param("minSalary") String minSalary,
                                    @Param("maxSalary") String maxSalary);

    @Query(value = "select * from job where job.idJob = :idJob ", nativeQuery = true)
    List<Object[]> getJobDescription(@Param("idJob") String idJob);


    @Query(value = "select distinct idTime, idJob, academic_level.academic_level " +
                    "from job_fact, academic_level " +
                    "where job_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and job_fact.idJob = :id " +
                    "and job_fact.idTime in ( select idTime from " + 
                        "( select idTime from timed order by timestampD desc limit 1 ) as t ); " , nativeQuery = true)
    List<Object[]> getJobLiteracy(@Param("id") String idJob);

    @Query(value = "select idTime,idJob, min(salary), max(salary), sum(job_fact.number_of_recruitment) " +
                    "from job_fact " +
                    "where idJob = :idJob " +
                    "and job_fact.idTime in ( select idTime from " + 	
                    "( select idTime from timed order by timestampD desc limit 2 ) as t ) " +
                    "group by idTime, idJob; " , nativeQuery = true)
    List<Object[]> getNumberOfJob(@Param("idJob") String idJob);

    @Query(value = "select job.name_job, industries.name_industry " + 
                    "from job_industry, job, industries " +
                    "where job.idJob = job_industry.idJob " + 
                    "and industries.idIndustry = job_industry.idIndustry " +
                    "and job.idJob = :idJob ;" , nativeQuery = true)
    List<Object[]> getIndustryOfJob(@Param("idJob") String idJob);

    @Query(value = "select job.idJob, job.name_job, min(salary), max(salary), sum(number_of_recruitment) " +
                    "from job, job_fact " +
                    "where job.idJob = job_fact.idJob " +
                    "and job_fact.idTime in ( select idTime from " + 
                    "( select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                    "and (job.idJob in ( select id_job2 from relate_jobs where relate_jobs.id_job1 = :idJob) " +
                    "or job.idJob in ( select id_job1 from relate_jobs where relate_jobs.id_job2 = :idJob)) " +
                    "group by job_fact.idTime, job_fact.idJob; ", nativeQuery = true)
    List<Object[]> getJobRelated(@Param("idJob") String idJob);

    @Query(value = "select distinct job.idJob, job.name_job, skill.name_skill, skill.`description` " +
                    "from job, trends_in_job_skills, skill " +
                    "where job.idJob= trends_in_job_skills.idJob " + 
                    "and skill.idSkill = trends_in_job_skills.idSkill " +
                    "and trends_in_job_skills.idTime in ( select idTime from " + 
                    "( select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                    "and job.idJob = :idJob ", nativeQuery = true)
    List<Object[]> getSkillRequired(@Param("idJob") String idJob);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "job.name_job,  sum(job_fact.number_of_recruitment) as `numJob` " + 
                    "from job, job_fact, timed, province " + 
                    "where job.idJob = job_fact.idJob and " + 
                    "job_fact.IdProvince = province.idProvince " +
                        "and job_fact.idTime = timed.idTime " +
                        "and job_fact.idTime in ( select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 4 ) as t ) " +
                        "and job.idJob = :idJob " +
                    "group by timed.idTime, job.idJob order by idTime ; ", nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTimeCountry(@Param("idJob") String idJob);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "job.name_job, province.Province,   sum(job_fact.number_of_recruitment) " +
                    "from job, job_fact, timed, province " +
                    "where job.idJob = job_fact.idJob and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime = timed.idTime " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                            "select idTime from timed order by timestampD desc limit 4 ) as t ) " +
                    "and job.idJob = :idJob and province.IdProvince = :idLocation " +
                    "group by timed.idTime, job.idJob, province.IdProvince order by idTime ;" , nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTimeCity(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.name_job, avg(job_fact.salary) " + 
                    "from job, job_fact, timed, province " +
                    "where job.idJob = job_fact.idJob and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime = timed.idTime " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 4 ) as t ) " +
                    "and job.idJob = :idJob " +
                    "group by timed.idTime, job.idJob order by idTime;", nativeQuery = true)
    List<Object[]> getAverageSalaryCountry(@Param("idJob") String idJob);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job.name_job, province.Province, avg(job_fact.salary) " +
                    "from job, job_fact, timed, province " +
                    "where job.idJob = job_fact.idJob and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime = timed.idTime " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                    "select idTime from timed order by timestampD desc limit 4 ) as t ) " +
                    "and job.idJob = :idJob and province.IdProvince = :idLocation " +
                    "group by timed.idTime, job.idJob, province.IdProvince order by idTime; ", nativeQuery = true)
    List<Object[]> getAverageSalaryCity(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "with company_1 as ( " +
                        "select company_fact.idTime,  company_fact.idJob, company.idCompany, company.name_company, " +
                            "sum(company_fact.number_of_recruitment) as numJob, " +
                            "rank() over(partition by company_fact.idTime, company_fact.idJob order by sum(company_fact.number_of_recruitment) desc) as `rank` " +
                        "from company_fact, company, timed " +
                        "where company_fact.idCompany = company.idCompany " +
                            "and company_fact.idJob = :idJob " +
                            "and company_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by company_fact.idTime,  company_fact.idJob, company_fact.idCompany " +
                        "order by company_fact.idTime, company_fact.idJob, sum(company_fact.number_of_recruitment) desc ) " +
                    "select company_1.idCompany, company_1.name_company, company_1.numJob, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " +
                    "from company_1, timed " +
                    "where `rank` <= 20 " +
                    "and company_1.idTime = timed.idTime " +
                    "order by company_1.idTime, company_1.idJob, `rank`;" , nativeQuery = true)
    List<Object[]> getTopHiringCompaniesCountry(@Param("idJob") String idJob);

    @Query(value = "with company_1 as ( " +
                        "select company_fact.idTime,  company_fact.idJob, company.idCompany, company.name_company, " +
                            "sum(company_fact.number_of_recruitment) as numJob, " +
                            "rank() over(partition by company_fact.idTime, company_fact.idJob order by sum(company_fact.number_of_recruitment) desc) as `rank` " +
                        "from company_fact, company, timed " +
                        "where company_fact.idCompany = company.idCompany " +
                            "and company_fact.idJob = :idJob " +
                            "and company_fact.idProvince IN (:regionList) " +
                            "and company_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by company_fact.idTime,  company_fact.idJob, company_fact.idCompany " +
                        "order by company_fact.idTime, company_fact.idJob, sum(company_fact.number_of_recruitment) desc ) " +
                    "select company_1.idCompany, company_1.name_company, company_1.numJob, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " +
                    "from company_1, timed " +
                    "where `rank` <= 20 " +
                    "and company_1.idTime = timed.idTime " +
                    "order by company_1.idTime, company_1.idJob, `rank`; " , nativeQuery = true)
    List<Object[]> getTopHiringCompaniesCity(@Param("idJob") String idJob, @Param("regionList") List<String> regionList);

    @Query(value = "with company_1 as ( " +
                        "select company_fact.idTime, company_fact.idJob, company.idCompany, company.name_company, " +
                            "avg(company_fact.salary) as salary, " +
                            "rank() over(partition by company_fact.idTime, company_fact.idJob order by avg(company_fact.salary) desc) as `rank` " +
                        "from company_fact, company " +
                        "where company_fact.idCompany = company.idCompany " +
                            "and company_fact.idJob = :idJob " +
                            "and company_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by company_fact.idTime, company_fact.idJob, company_fact.idCompany " +
                        "order by company_fact.idTime, company_fact.idJob, avg(company_fact.salary) desc ) " +
                    "select company_1.idCompany, company_1.name_company, company_1.salary, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " + 
                    "from company_1, timed " +
                    "where `rank` <= 20 " +
                    "and company_1.idTime = timed.idTime " + 
                    "order by company_1.idTime, company_1.idJob, `rank`; " , nativeQuery = true)
    List<Object[]> getTopHighestSalaryCompaniesCountry(@Param("idJob") String idJob);

    @Query(value = "with company_1 as ( " +
                        "select company_fact.idTime, company_fact.idJob, company.idCompany, company.name_company, " +
                            "avg(company_fact.salary) as salary, " +
                            "rank() over(partition by company_fact.idTime, company_fact.idJob order by avg(company_fact.salary) desc) as `rank` " +
                        "from company_fact, company " +
                        "where company_fact.idCompany = company.idCompany " +
                            "and company_fact.idJob = :idJob " +
                            "and company_fact.idProvince IN (:regionList) " +
                            "and company_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by company_fact.idTime, company_fact.idJob, company_fact.idCompany " +
                        "order by company_fact.idTime, company_fact.idJob, avg(company_fact.salary) desc ) " +
                    "select company_1.idCompany, company_1.name_company, company_1.salary, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " + 
                    "from company_1, timed " +
                    "where `rank` <= 20 " +
                    "and company_1.idTime = timed.idTime " + 
                    "order by company_1.idTime, company_1.idJob, `rank`; " , nativeQuery = true)
    List<Object[]> getTopHighestSalaryCompaniesCity(@Param("idJob") String idJob, @Param("regionList") List<String> regionList);

    @Query(value = "with province_1 as ( " +
                        "select job_fact.idTime, job_fact.idJob, province.idProvince, province.Province, " +
                            "sum(job_fact.number_of_recruitment) as numJob, "  +
                            "rank() over(partition by job_fact.idTime, job_fact.idJob order by sum(job_fact.number_of_recruitment) desc) as `rank` " +
                        "from job_fact, province " +
                        "where job_fact.IdProvince = province.idProvince " +
                            "and job_fact.idJob = :idJob " +
                            "and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by job_fact.idTime, job_fact.idJob, job_fact.IdProvince " +
                        "order by job_fact.idTime, job_fact.idJob, sum(job_fact.number_of_recruitment) desc) " +
                    "select province_1.idProvince, province_1.Province, province_1.numJob, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " +
                    "from province_1, timed " +
                    "where `rank` <= 20 " +
                    "and province_1.idTime = timed.idTime " +
                    "order by province_1.idTime, province_1.idJob, `rank`;" , nativeQuery = true)
    List<Object[]> getTopHiringRegion(@Param("idJob") String idJob);

    @Query(value = "with province_1 as ( " +
                        "select job_fact.idTime, job_fact.idJob, province.idProvince, province.Province, " +
                            "avg(job_fact.salary) as salary, " +
                            "rank() over(partition by job_fact.idTime, job_fact.idJob order by avg(job_fact.salary) desc) as `rank` " +
                        "from job_fact, province " +
                        "where job_fact.IdProvince = province.idProvince " +
                            "and job_fact.idJob = :idJob " +
                            "and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t ) " +
                        "group by job_fact.idTime, job_fact.idJob, job_fact.IdProvince " +
                        "order by job_fact.idTime, job_fact.idJob, avg(job_fact.salary) desc) " +
                    "select province_1.idProvince, province_1.Province, province_1.salary, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time` " +
                    "from province_1, timed " +
                    "where `rank` <= 20 " +
                    "and province_1.idTime = timed.idTime " +
                    "order by province_1.idTime, province_1.idJob, `rank`; " , nativeQuery = true)
    List<Object[]> getHighestSalaryRegion(@Param("idJob") String idJob);
    
    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,job_fact.idJob,\n" +
            "    sum(job_fact.number_of_recruitment),\n" +
            "   age.idAge,bin(age.`0-18`),bin(age.`18-25`), bin(age.`25-35`), bin(age.`35-50`), bin(age.`50+`),\n" +
            "   gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
            "from job_fact, gender, age, timed, province\n" +
            "where job_fact.idTime = timed.idTime and job_fact.idGender = gender.idGender\n" +
            "and job_fact.idAge = age.idAge and job_fact.IdProvince = province.IdProvince\n" +
            "and job_fact.idJob = :idJob \n" +
            "and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime, job_fact.idJob, age.idAge, gender.idGender\n" +
            "order by timed.idTime, job_fact.idJob, age.idAge, gender.idGender;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeCountry(@Param("idJob") String idJob);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,job_fact.idJob,province.Province,\n" +
            "    sum(job_fact.number_of_recruitment),\n" +
            "   age.idAge,bin(age.`0-18`),bin(age.`18-25`), bin(age.`25-35`), bin(age.`35-50`), bin(age.`50+`),\n" +
            "   gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
            "from job_fact, gender, age, timed, province\n" +
            "where job_fact.idTime = timed.idTime and job_fact.idGender = gender.idGender\n" +
            "and job_fact.idAge = age.idAge and job_fact.IdProvince = province.IdProvince\n" +
            "and job_fact.idJob = :idJob and province.idProvince = :idLocation \n" +
            "and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime,province.idProvince, job_fact.idJob, age.idAge, gender.idGender\n" +
            "order by timed.idTime,province.idProvince, job_fact.idJob, age.idAge, gender.idGender;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeCity(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value =  "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job_fact.idJob, sum(job_fact.number_of_recruitment), academic_level.academic_level " +
                    "from job_fact, academic_level, timed, province " +
                    "where job_fact.idTime = timed.idTime and " +
                    "job_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and province.IdProvince = job_fact.IdProvince " +
                    "and job_fact.idJob = :idJob " +
                    "and job_fact.idTime in (select idTime from ( " +
                        "select idTime from timed order by timestampD desc limit 4) as t ) " +
                    "group by timed.idTime, job_fact.idJob, academic_level.academic_level " +
                    "order by timed.timestampD, job_fact.idJob, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracyCountry(@Param("idJob") String idJob);
    
    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job_fact.idJob, province.province, sum(job_fact.number_of_recruitment), academic_level.academic_level " +
                    "from job_fact, academic_level, timed, province " +
                    "where job_fact.idTime = timed.idTime and " + 
                    "job_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and province.IdProvince = job_fact.IdProvince " +
                    "and job_fact.idJob = :idJob and province.IdProvince = :idLocation " +
                    "and job_fact.idTime in (select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 4) as t ) " +
                    "group by timed.idTime,province.idProvince, job_fact.idJob, academic_level.academic_level " +
                    "order by timed.timestampD,province.idProvince, job_fact.idJob, academic_level.academic_level; ", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracyCity(@Param("idJob") String idJob, @Param("idLocation") String idLocation);


    // Danh sach nhom ki nang can thiet
    @Query(value = "select timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job.name_job, skill.name_skill, skill.idSkill " +
                    "from trends_in_job_skills as trend, skill, job, timed " +
                    "where trend.idJob = job.idJob and trend.idTime = timed.idTime " +
                    "and trend.idTime in (select idTime from (" + 
                        "select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "and trend.idSkill = skill.idSkill and job.idJob = :idJob " + 
                    "order by timed.idTime, job.idJob desc;" , nativeQuery = true)
    List<Object[]> getJobSkillsDemandByTime(@Param("idJob") String idJob);

    // Su thay doi yeu cau ki nang theo thoi gian


    @Query( value = " select * from age order by age;", nativeQuery = true)
    List<Object[]> getAgeRange();

    @Query( value = " select * from academic_level order by academic_level;", nativeQuery = true)
    List<Object[]> getLiteracy();

    @Query(value = "select distinct company.idCompany, company.name_company,location.location\n" +
            "from company_fact, company, location\n" +
            "where company_fact.idCompany = company.idCompany\n" +
            "    and company.idLocation = location.idLocation\n" +
            "    and company_fact.idJob = :idJob", nativeQuery = true)
    List<Object[]> getListCompanyHiringJob(@Param("idJob") int idJob);


}