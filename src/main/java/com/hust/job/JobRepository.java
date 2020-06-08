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

    @Query(value = "select job_fact.idTime,job.idJob, job.name_job, gender.gender, " + 
                    "min(salary), max(salary), sum(job_fact.number_of_recruitment) as `so luong tuyen dung` " +
                    "from job, job_fact , job_industry, province, gender " +
                    "where job.idJob = job_fact.idJob and job.idJob = job_industry.idJob " +
                        "and job_fact.idGender = gender.idGender " +
                        "and job_fact.IdProvince = province.idProvince " + 
                        "and job_fact.idTime in ( select idTime from ( " + 
                            "select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                    "group by job_fact.idTime,gender.idGender,job_fact.idJob " +
                    "order by sum(job_fact.number_of_recruitment) desc limit :numJob", nativeQuery = true)
    List<Object[]> getTopJob(@Param("numJob") int numJob);
    
    @Query(value = "select job_fact.idTime,job.idJob, job.name_job, gender.gender, " + 
                    "min(salary), max(salary), sum(job_fact.number_of_recruitment) as `so luong tuyen dung` " +
                    "from job, job_fact , job_industry, province, gender " +
                    "where job.idJob = job_fact.idJob and job.idJob = job_industry.idJob " +
                    "and job_fact.idGender = gender.idGender " +
                    "and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 1 ) as t ) " +
                    "and job.name_job = :queryContent " +
                    "group by job_fact.idTime,gender.idGender,job_fact.idJob " +
                    "order by job_fact.idTime, job_fact.idJob desc " , nativeQuery = true)
    List<Object[]> basicSearchJob(@Param("queryContent") String queryContent);
    
    @Query(value = "with jobs_1 as ( " +
                    "select job_fact.idTime,job.idJob, job.name_job, " +
                        "min(salary) as `min salary`, max(salary) as `max salary`, " + 
                        "sum(job_fact.number_of_recruitment) as `so luong tuyen dung`, " +
                        "rank() over( partition by job_fact.idJob order by idTime desc) as `rank_time` " +
                    "from job, job_fact , job_industry, province, years_experience, industries, gender " +
                    "where job.idJob = job_fact.idJob and job.idJob = job_industry.idJob " +
                    "and job_industry.idIndustry = industries.idIndustry " +  
                    "and job_fact.idGender = gender.idGender " + 
                    "and job_fact.IdProvince = province.idProvince " + 
                    "and job_fact.idYears_Experience = years_experience.idYears_Experience " +
                    "and job.name_job = :queryContent " +
                    "and industries.name_industry = :industry " +
                    "and province.province = :location " +
                    "and job_fact.salary between :minSalary and :maxSalary " +
                    "group by job_fact.idTime,job_fact.idJob " +
                    "order by job_fact.idTime, job_fact.idJob desc) " + 
                    "select distinct jobs_1.idTime, jobs_1.idJob, jobs_1.name_job, gender.gender, " +
                    "jobs_1.`min salary`, jobs_1.`max salary`, jobs_1.`so luong tuyen dung` " +
                    "from jobs_1, gender, job_fact " +
                    "where job_fact.idJob = jobs_1.idJob " + 
                    "and gender.idGender = job_fact.idGender " +
                     "and rank_time = 1; ", nativeQuery = true)
    List<Object[]> advancedSearchJob(@Param("queryContent") String queryContent, 
                                    @Param("location") String location,
                                    @Param("industry") String industry,
                                    @Param("minSalary") int minSalary,
                                    @Param("maxSalary") int maxSalary);

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
                    "job.name_job, province.Province,   sum(job_fact.number_of_recruitment) " +
                    "from job, job_fact, timed, province " +
                    "where job.idJob = job_fact.idJob and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime = timed.idTime " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                            "select idTime from timed order by timestampD desc limit 8 ) as t ) " +
                    "and job.idJob = :idJob and province.IdProvince = :idLocation " +
                    "group by timed.idTime, job.idJob, province.IdProvince order by idTime;" , nativeQuery = true)
    List<Object[]> getJobDemandByPeriodOfTime(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job.name_job, province.Province, avg(job_fact.salary) " +
                    "from job, job_fact, timed, province " +
                    "where job.idJob = job_fact.idJob and job_fact.IdProvince = province.idProvince " +
                    "and job_fact.idTime = timed.idTime " +
                    "and job_fact.idTime in ( select idTime from ( " + 
                    "select idTime from timed order by timestampD desc limit 4 ) as t ) " +
                    "and job.idJob = :idJob and province.IdProvince = :idLocation " +
                    "group by timed.idTime, job.idJob, province.IdProvince order by idTime; ", nativeQuery = true)
    List<Object[]> getAverageSalary(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryInSubRegion(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " + 
                    "job_fact.idJob, province.province, sum(job_fact.number_of_recruitment), gender.gender, age.age " +
                    "from job_fact, gender, age, timed, province " +
                    "where job_fact.idTime = timed.idTime and job_fact.idGender = gender.idGender " +
                    "and job_fact.idAge = age.idAge and job_fact.IdProvince = province.IdProvince " +
                    "and job_fact.idJob = :idJob and province.idProvince = :idLocation " +
                    "and job_fact.idTime in (select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by timed.idTime,province.IdProvince, job_fact.idJob, age.age, gender.gender " +
                    "order by timed.idTime, province.IdProvince, job_fact.idJob, age.age, gender,gender;", nativeQuery = true)
    List<Object[]> getJobDemandByAge(@Param("idJob") String idJob, @Param("idLocation") String idLocation);

    @Query(value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, " +
                    "job_fact.idJob, province.province, sum(job_fact.number_of_recruitment), academic_level.academic_level " +
                    "from job_fact, academic_level, timed, province " +
                    "where job_fact.idTime = timed.idTime and " + 
                    "job_fact.idAcademic_Level = academic_level.idAcademic_Level " +
                    "and province.IdProvince = job_fact.IdProvince " +
                    "and job_fact.idJob = :idJob and province.IdProvince = :idLocation " +
                    "and job_fact.idTime in (select idTime from ( " + 
                        "select idTime from timed order by timestampD desc limit 3) as t ) " +
                    "group by timed.idTime,province.idProvince, job_fact.idJob, academic_level.academic_level " +
                    "order by timed.timestampD,province.idProvince, job_fact.idJob, academic_level.academic_level; ", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracy(@Param("idJob") String idJob, @Param("idLocation") String idLocation);


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


}