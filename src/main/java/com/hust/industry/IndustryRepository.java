package com.hust.industry;

import java.util.List;

import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends CrudRepository<Industry, String> {


    @Query(value = "select timed.yearD,industries.idIndustry, industries.name_industry,\n" +
            "       sum(market_fact.number_of_recruitment), avg(market_fact.salary), industries.`description`\n" +
            "from market_fact, industries, timed\n" +
            "where market_fact.idIndustry = industries.idIndustry and timed.idTime = market_fact.idTime\n" +
            "  and timed.yearD between year(curdate())-2 and year(curdate())-1\n" +
            "group by timed.yearD, industries.idIndustry\n" +
            "order by industries.idIndustry, sum(market_fact.number_of_recruitment) desc;" , nativeQuery = true)
    List<Object[]> getIndustryList();

    @Query(value = "select job.idJob, job.name_job,\n" +
            "       sum(job_fact.number_of_recruitment), min(job_fact.salary), max(job_fact.salary),\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime\n" +
            "from job_industry, industries, job, job_fact,timed\n" +
            "where job.idJob = job_industry.idJob and industries.idIndustry = job_industry.idIndustry\n" +
            "  and job.idJob = job_fact.idJob and timed.idTime = job_fact.idTime\n" +
            "  and job_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 2) as t )\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by job_fact.idTime,industries.idIndustry, job.idJob\n" +
            "order by industries.idIndustry, job.idJob, timed.idTime;", nativeQuery = true)
    List<Object[]> getJobListByIndustry(@Param("industryId") int industryId);

    @Query(value = "select company.idCompany, company.name_company,\n" +
            "       top.number_of_recruitment, top.growth, top.rank_company,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime\n" +
            "from top10_companies_with_the_highest_recruitment_by_industries as top, timed, company, industries\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId\n" +
            "  and top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t )\n" +
            "order by top.idTime,top.idIndustry, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustryWithCountry(@Param("industryId") int industryId);

    @Query(value = "with rank_companies_1 as (\n " +
            "       select company.idCompany, company.name_company,\n" +
            "       sum(number_of_recruitment) as `so luong tuyen dung`, avg(salary) as `luong`,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime,\n" +
            "      rank() over (partition by idTime,province.idProvince, industries.idIndustry order by sum(number_of_recruitment) desc) as `rank`\n" +
            "        from company_fact as fact, timed, company, industries, province \n" +
            "        where fact.idTime = timed.idTime and fact.idProvince = province.idProvince \n" +
            "            and fact.idIndustry = industries.idIndustry and fact.idCompany = company.idCompany \n" +
            "            and fact.idProvince = :idProvince and fact.idIndustry = :industryId \n" +
            "        group by timed.idTime, province.idProvince, industries.idIndustry, fact.idCompany\n" +
            "        order by idTime, province.idProvince, industries.idIndustry,sum(number_of_recruitment) desc)\n" +
            "select idCompany, name_company, `so luong tuyen dung`, `luong`, `time`, idTime\n" +
            "from rank_companies_1\n" +
            "where `rank` <= 10\n" +
            "  and rank_companies_1.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t );", nativeQuery = true)
    List<Object[]> getTopCompanyByIndustryWithProvince(@Param("idProvince") int idProvince, @Param("industryId") int industryId);

//    @Query(value = "select company.idCompany, company.name_company,\n" +
//            "       top.salary, top.growth, top.rank_company,\n" +
//            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime\n" +
//            "from top10_highest_salary_companies_by_industries as top, timed, company, industries\n" +
//            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idIndustry = industries.idIndustry\n" +
//            "  and industries.idIndustry = :industryId\n" +
//            "  and top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t )\n" +
//            "order by top.idTime,top.idIndustry, top.salary desc limit 10;", nativeQuery = true)
//    List<Object[]> getTopSalaryCompanyByIndustryWithCountry(@Param("industryId") int industryId);

//    @Query(value = "with rank_companies_1 as (\n" +
//            "select company.idCompany, company.name_company,\n" +
//            "       sum(number_of_recruitment) as `so luong tuyen dung`, avg(salary) as `luong`,\n" +
//            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime,\n" +
//            "       rank() over (partition by idTime,province.idProvince, industries.idIndustry order by avg(salary) desc) as `rank`\n" +
//            "from company_fact as fact, timed, company, industries, province\n" +
//            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
//            "    and fact.idProvince = :idProvince and fact.idIndustry = :industryId \n" +
//            "    and fact.idIndustry = industries.idIndustry and fact.idCompany = company.idCompany\n" +
//            "group by timed.idTime, province.idProvince, industries.idIndustry, fact.idCompany\n" +
//            "order by idTime, province.idProvince, industries.idIndustry,sum(number_of_recruitment) desc)\n" +
//            "select idCompany, name_company, `so luong tuyen dung`, `luong`, `time`, idTime \n" +
//            "from rank_companies_1\n" +
//            "where `rank` <= 10\n" +
//            "  and rank_companies_1.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 1 ) as t ) \n " +
//            "order by fact.idTime, fact.idIndustry, fact.idProvince, avg(salary) limit 10;", nativeQuery = true)
//    List<Object[]> getTopSalaryCompanyByIndustryWithProvince(@Param("idProvince") int idProvince, @Param("industryId") int industryId);


    @Query(value = "select timed.idTime, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(fact.number_of_recruitment)\n" +
            "from market_fact as fact, timed, industries\n" +
            "where fact.idTime = timed.idTime and fact.idIndustry = :industryId \n" +
            "  and fact.idIndustry = industries.idIndustry\n" +
            "group by fact.idTime,industries.idIndustry\n" +
            "order by fact.idTime;", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithCountry(@Param("industryId") int industryId );

    @Query( value = "select timed.idTime, province.province, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(fact.number_of_recruitment)\n" +
            "from market_fact as fact, timed, industries, province\n" +
            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
            "  and fact.idProvince = :locationId and fact.idIndustry = :industryId \n" +
            "  and fact.idIndustry = industries.idIndustry\n" +
            "group by fact.idTime, province.idProvince, industries.idIndustry\n" +
            "order by fact.idTime;", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);

    @Query(value = "select timed.idTime, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, avg(fact.salary)\n" +
            "from market_fact as fact, timed, industries\n" +
            "where fact.idTime = timed.idTime and fact.idIndustry = :industryId \n" +
            "  and fact.idIndustry = industries.idIndustry\n" +
            "group by fact.idTime,industries.idIndustry\n" +
            "order by fact.idTime;", nativeQuery = true)
    List<Object[]> getSalaryByIndustryWithCountry(@Param("industryId") int industryId );

    @Query( value = "select timed.idTime, province.province, industries.name_industry,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, avg(fact.salary)\n" +
            "from market_fact as fact, timed, industries, province\n" +
            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
            "  and fact.idIndustry = industries.idIndustry\n" +
            "  and fact.idProvince = :locationId and fact.idIndustry = :industryId \n" +
            "group by fact.idTime, province.idProvince, industries.idIndustry\n" +
            "order by fact.idTime;", nativeQuery = true)
    List<Object[]> getSalaryByIndustryWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);


    @Query(value = "select industries.name_industry, province.idProvince, Province, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, timed, industries, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idProvince = province.idProvince\n" +
            "  and market_fact.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId\n" +
            "group by province.idProvince, market_fact.idTime, industries.idIndustry\n" +
            "order by province.idProvince,timed.idTime;", nativeQuery = true)
    List<Object[]> getJobDemandInSubRegionOfCountry(@Param("industryId") int industryId);

    @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, company.idCompany,\n" +
            "       company.name_company, top.number_of_recruitment, top.growth\n" +
            "from top10_companies_with_the_highest_recruitment_by_industries as top, timed, industries, company\n" +
            "where top.idIndustry = industries.idIndustry and top.idCompany = company.idCompany\n" +
            "    and top.idTime = timed.idTime\n" +
            "    and top.idTime in (select idTime from (select idTime from timed order by timestampD asc limit 8) as t)\n" +
            "    and industries.idIndustry = :industryId \n" +
            "order by top.idTime asc,top.number_of_recruitment desc;;", nativeQuery = true)
    List<Object[]> getTopHiringCompanyWithCountry(@Param("industryId") int industryId);

    @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, company.idCompany,\n" +
            "       company.name_company, top.salary, top.growth\n" +
            "from top10_highest_salary_companies_by_industries as top, timed, industries, company\n" +
            "where top.idIndustry = industries.idIndustry and top.idCompany = company.idCompany\n" +
            "    and top.idTime = timed.idTime\n" +
            "    and top.idTime in (select idTime from (select idTime from timed order by timestampD asc limit 8) as t)\n" +
            "    and industries.idIndustry = :industryId \n" +
            "order by top.idTime asc,top.salary desc;;", nativeQuery = true)
    List<Object[]> getTopSalaryHiringCompanyWithCountry(@Param("industryId") int industryId);

    @Query( value = "with rank_companies_1 as (\n" +
            "select company.idCompany, company.name_company,\n" +
            "       sum(number_of_recruitment) as `so luong tuyen dung`,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime,\n" +
            "       rank() over (partition by idTime,province.idProvince, industries.idIndustry order by sum(number_of_recruitment) desc) as `rank`\n" +
            "from\n" +
            "    company_fact as fact, timed, company, industries, province\n" +
            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
            "    and fact.idIndustry = industries.idIndustry and fact.idCompany = company.idCompany\n" +
            "    and fact.idProvince = :locationId and fact.idIndustry = :industryId\n" +
            "group by timed.idTime, province.idProvince, industries.idIndustry, fact.idCompany\n" +
            "order by idTime, province.idProvince, industries.idIndustry,sum(number_of_recruitment) desc)\n" +
            "select `time`, idCompany, name_company, `so luong tuyen dung`, idTime\n" +
            "from rank_companies_1\n" +
            "where `rank` <= 10\n" +
            "  and rank_companies_1.idTime in ( select idTime from ( select idTime from timed order by timestampD asc limit 8 ) as t );", nativeQuery = true)
            List<Object[]> getTopHiringCompanyWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);

    @Query( value = "with rank_companies_1 as (\n" +
            "select company.idCompany, company.name_company,\n" +
            "       avg(salary) as `luong`,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, timed.idTime,\n" +
            "       rank() over (partition by idTime,province.idProvince, industries.idIndustry order by avg(salary) desc) as `rank`\n" +
            "from\n" +
            "    company_fact as fact, timed, company, industries, province\n" +
            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
            "    and fact.idIndustry = industries.idIndustry and fact.idCompany = company.idCompany\n" +
            "    and fact.idProvince = :locationId and fact.idIndustry = :industryId\n" +
            "group by timed.idTime, province.idProvince, industries.idIndustry, fact.idCompany\n" +
            "order by idTime, province.idProvince, industries.idIndustry,sum(number_of_recruitment) desc)\n" +
            "select `time`, idCompany, name_company, `luong`, idTime\n" +
            "from rank_companies_1\n" +
            "where `rank` <= 10\n" +
            "  and rank_companies_1.idTime in ( select idTime from ( select idTime from timed order by timestampD asc limit 8 ) as t );", nativeQuery = true)
    List<Object[]> getTopSalaryHiringCompanyWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);
    
    @Query(value = "with rank_companies_4 as (\n" +
            "    select idTime ,fact.idIndustry, fact.idJob, sum(number_of_recruitment) as `so luong cong viec`,\n" +
            "           rank()  over ( partition by idTime, idIndustry order by sum(number_of_recruitment) desc ) as \"rankd\"\n" +
            "    from job_fact as fact, job_industry\n" +
            "    where fact.idJob = job_industry.idJob and job_industry.idIndustry = :industryId \n" +
            "    group by idTime, idIndustry, fact.idJob\n" +
            ")\n" +
            "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.idJob, job.name_job,\n" +
            "    `so luong cong viec`, timed.idTime\n" +
            "from rank_companies_4, job, timed\n" +
            "where rank_companies_4.idJob = job.idJob and timed.idTime = rank_companies_4.idTime\n" +
            "    and rankd <= 10\n" +
            "    and rank_companies_4.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t)\n" +
            "order by timed.idTime, `so luong cong viec` desc;", nativeQuery = true)
    List<Object[]> getTopHiringJobWithCountry(@Param("industryId") int industryId );

    @Query( value = "with rank_companies_4 as (\n" +
            "    select idTime ,fact.idIndustry,province.idProvince, fact.idJob, sum(number_of_recruitment) as `so luong cong viec`,\n" +
            "           rank()  over ( partition by idTime, idIndustry, province.idProvince order by sum(number_of_recruitment) desc ) as \"rankd\"\n" +
            "    from job_fact as fact, job_industry, province\n" +
            "    where fact.idJob = job_industry.idJob and fact.IdProvince = province.idProvince\n" +
            "       and job_industry.idIndustry = :industryId and fact.idProvince = :locationId \n" +
            "    group by idTime, idIndustry, province.idProvince, fact.idJob\n" +
            ")\n" +
            "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.idJob, job.name_job,\n" +
            "    `so luong cong viec`, timed.idTime\n" +
            "from rank_companies_4, job, timed\n" +
            "where rank_companies_4.idJob = job.idJob and timed.idTime = rank_companies_4.idTime\n" +
            "    and rankd <= 10\n" +
            "    and rank_companies_4.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t)\n" +
            "order by timed.idTime, `so luong cong viec` desc;", nativeQuery = true)
    List<Object[]> getTopHiringJobWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);

    @Query(value = "with rank_companies_4 as (\n" +
            "    select idTime ,idIndustry, job_fact.idJob, avg(salary) as `luong`, sum(number_of_recruitment) as `so luong tuyen dung`,\n" +
            "           rank()  over ( partition by idTime, idIndustry order by avg(salary) desc ) as \"rankd\"\n" +
            "    from job_fact, job_industry, province\n" +
            "    where job_fact.idJob = job_industry.idJob\n" +
            "      and idIndustry = :industryId\n" +
            "    group by idTime, idIndustry,job_fact.idJob)\n" +
            "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.idJob, job.name_job,\n" +
            "       `luong`,`so luong tuyen dung`, timed.idTime\n" +
            "from rank_companies_4, job, timed\n" +
            "where rank_companies_4.idJob = job.idJob and timed.idTime = rank_companies_4.idTime\n" +
            "  and rankd <= 10\n" +
            "  and rank_companies_4.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t)\n" +
            "order by timed.idTime, `luong` desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobWithCountry(@Param("industryId") int industryId);

    @Query( value = "with rank_companies_4 as (\n" +
            "    select idTime ,idIndustry,province.idProvince, job_fact.idJob, avg(salary) as `luong`, sum(number_of_recruitment) as `so luong tuyen dung`,\n" +
            "           rank()  over ( partition by idTime, idIndustry, province.idProvince order by avg(salary) desc ) as \"rankd\"\n" +
            "    from job_fact, job_industry, province\n" +
            "    where job_fact.idJob = job_industry.idJob and job_fact.IdProvince = province.idProvince\n" +
            "        and idIndustry = :industryId and province.idProvince = :locationId\n" +
            "    group by idTime, idIndustry, province.idProvince,job_fact.idJob\n" +
            ")\n" +
            "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.idJob, job.name_job,\n" +
            "    `luong`, `so luong tuyen dung`, timed.idTime\n" +
            "from rank_companies_4, job, timed\n" +
            "where rank_companies_4.idJob = job.idJob and timed.idTime = rank_companies_4.idTime\n" +
            "    and rankd <= 10\n" +
            "    and rank_companies_4.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t)\n" +
            "order by timed.idTime, `luong` desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);


    @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment),\n" +
            "       age.idAge,bin(age.`0-18`),bin(age.`18-25`), bin(age.`25-35`), bin(age.`35-50`), bin(age.`50+`),\n" +
            "       gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
            "from market_fact, gender, age, timed, industries\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idGender = gender.idGender\n" +
            "  and market_fact.idAge = age.idAge and market_fact.idIndustry = industries.idIndustry\n" +
            "  and industries.idIndustry = :industryId \n" +
            "  and market_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime,industries.idIndustry, gender.idGender, age.idAge\n" +
            "order by timed.idTime, industries.idIndustry, gender.idGender, age.idAge;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeWithCountry(@Param("industryId") int industryId);


    @Query( value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment),\n" +
            "       age.idAge,bin(age.`0-18`),bin(age.`18-25`), bin(age.`25-35`), bin(age.`35-50`), bin(age.`50+`),\n" +
            "       gender.idGender, bin(gender.`male`), bin(gender.`female`)\n" +
            "from market_fact, gender, age, timed, industries, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idGender = gender.idGender\n" +
            "  and market_fact.idAge = age.idAge and market_fact.idIndustry = industries.idIndustry\n" +
            "  and market_fact.idProvince = province.idProvince\n" +
            "  and industries.idIndustry = :industryId and province.idProvince = :locationId \n" +
            "  and market_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime,industries.idIndustry, province.idProvince, gender.idGender, age.idAge\n" +
            "order by timed.idTime, industries.idIndustry, gender.idGender, age.idAge;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);

    @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
            "       sum(fact.number_of_recruitment),\n" +
            "       academic_level.idAcademic_Level,academic_level.academic_level\n" +
            "from market_fact as fact, academic_level, timed, industries\n" +
            "where fact.idTime = timed.idTime and fact.idAcademic_Level = academic_level.idAcademic_Level\n" +
            "    and fact.idIndustry = industries.idIndustry and fact.idIndustry = :industryId \n" +
            "    and fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime,industries.idIndustry, academic_level.idAcademic_Level\n" +
            "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getJobDemandByLiteracyWithCountry(@Param("industryId") int industryId);

    @Query( value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
            "       sum(fact.number_of_recruitment),\n" +
            "       academic_level.idAcademic_Level,academic_level.academic_level\n" +
            "from market_fact as fact, academic_level, timed, industries, province\n" +
            "where fact.idTime = timed.idTime and fact.idAcademic_Level = academic_level.idAcademic_Level\n" +
            "  and fact.idIndustry = industries.idIndustry and fact.idProvince = province.idProvince\n" +
            "  and fact.idIndustry = :industryId and fact.idProvince = :locationId \n" +
            "  and fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime,industries.idIndustry,province.idProvince, academic_level.idAcademic_Level\n" +
            "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getjobDemandByLiteracyWithProvince(@Param("industryId") int industryId, @Param("locationId") int locationId);




    // @Query(value = "select job_fact.idTime,concat(timed.quarterD,\"/\",timed.yearD) as `time`, " + 
    //                     "job_fact.idJob, province.province, timed.timestampD, sum(job_fact.number_of_recruitment) " +  
    //                 "from job_fact, province, timed " +
    //                 "where job_fact.idTime = timed.idTime " + 	
    //                 "and job_fact.idProvince = province.idProvince " +
    //                 "and job_fact.idJob = :id " +
    //                 // "--and province.province = \"Hà Nội\" " + 
    //                 "group by job_fact.idTime,job_fact.idJob,province.province, timed.timestampD " +
    //                 "order by province.province, timed.timestampD;", nativeQuery = true)
    // List<Object[]> getJobDemandByPeriodOfTime(@Param("id") String id);

    @Query(value = "select company.name_company, avg(salary)\n" +
            "from company_fact, company, timed\n" +
            "where company_fact.idCompany = company.idCompany and company_fact.idTime = timed.idTime\n" +
            "    and timed.idTime = :idTime and company.idCompany = :idCompany and company_fact.idIndustry = :idIndustry\n" +
            "group by company_fact.idTime, company_fact.idCompany, company_fact.idIndustry;", nativeQuery = true)
    List<Object[]> getAvgSalaryForCompanyByCountry(@Param("idTime") int idTime, @Param("idCompany") int idCompany,
                                                   @Param("idIndustry") int idIndustry);


//    @Query(value = "select company.name_company, sum(number_of_recruitment)\n" +
//            "from (select distinct idTime, idCompany, idJob,idIndustry, number_of_recruitment\n" +
//            "           from company_fact where idTime = :idTime and idCompany = :idCompany and idIndustry = :idIndustry) as fact, company, timed, industries\n" +
//            "where fact.idCompany = company.idCompany and fact.idTime = timed.idTime and fact.idIndustry = industries.idIndustry\n" +
//            "group by fact.idTime,fact.idIndustry, fact.idCompany;", nativeQuery = true)
//    List<Object[]> getNumJobForCompanyByCountry(@Param("idTime") int idTime, @Param("idCompany") int idCompany,
//                                                @Param("idIndustry") int idIndustry);

    // lay thong tin  so luong tuyen dung cong ty theo quy
    @Query(value = "select company.name_company, sum(number_of_recruitment)\n" +
            "from company_fact as fact, company, timed, province, industries\n" +
            "where fact.idCompany = company.idCompany and fact.idTime = timed.idTime\n" +
            "    and fact.idProvince = province.idProvince and fact.idIndustry = industries.idIndustry\n" +
            "    and fact.idTime = :idTime and fact.idCompany = :idCompany and fact.idProvince = :idProvince and fact.idIndustry = :industryId" +
            "group by fact.idTime, fact.idCompany,industries.idIndustry, province.idProvince;", nativeQuery = true)
    List<Object[]> getRecruitmentOfCompanyInQuarter( @Param("idTime") int idTime, @Param("idCompany") int idCompany,
                                                     @Param("idProvince") int idProvince, @Param("industryId") int industryId);

    @Query(value = "select company.name_company, avg(salary) \n" +
            "from company_fact as fact, company, timed, province, industries\n" +
            "where fact.idCompany = company.idCompany and fact.idTime = timed.idTime\n" +
            "    and fact.idProvince = province.idProvince and fact.idIndustry = industries.idIndustry\n" +
            "    and fact.idTime = :idTime and fact.idCompany = :idCompany and fact.idProvince = :idProvince and fact.idIndustry = :industryId" +
            "group by fact.idTime, fact.idCompany,industries.idIndustry, province.idProvince;", nativeQuery = true)
    List<Object[]> getSalaryOfCompanyInQuarter( @Param("idTime") int idTime, @Param("idCompany") int idCompany,
                                                     @Param("idProvince") int idProvince, @Param("industryId") int industryId);


    @Query( value = "select job.name_job, sum(number_of_recruitment)\n" +
            "from job_fact, job, timed, job_industry\n" +
            "where job_fact.idJob = job.idJob and job_fact.idTime = timed.idTime\n" +
            "  and job_fact.idJob = job_industry.idJob\n" +
            "  and job.idJob = :jobId\n" +
            "  and job_industry.idIndustry = :industryId and timed.idTime = :timeId\n" +
        "group by job_fact.idTime, job_fact.idJob,job_industry.idIndustry;", nativeQuery = true)
    List<Object[]> getRecruitmentJobInQuarterWithCountry( @Param("jobId") int jobId, @Param("industryId") int industryId,
                                                          @Param("timeId") int timedId);

    @Query( value = "select job.name_job, sum(number_of_recruitment)\n" +
            "from job_fact, job, timed, job_industry, province\n" +
            "where job_fact.idJob = job.idJob and job_fact.idTime = timed.idTime\n" +
            "  and job_fact.idJob = job_industry.idJob and job_fact.IdProvince = province.idProvince\n" +
            "  and job.idJob = :jobId and province.idProvince = :locationId\n" +
            "  and job_industry.idIndustry = :industryId and timed.idTime = :timeId\n" +
            "group by job_fact.idTime, job_fact.idJob,job_industry.idIndustry;", nativeQuery = true)
    List<Object[]> getRecruitmentJobInQuarterWithProvince( @Param("jobId") int jobId, @Param("industryId") int industryId,
                                                           @Param("timeId") int timeId, @Param("locationId") int locationId);

    @Query( value = "select job.name_job, avg(salary)\n" +
            "from job_fact, job, timed, job_industry\n" +
            "where job_fact.idJob = job.idJob and job_fact.idTime = timed.idTime\n" +
            "  and job_fact.idJob = job_industry.idJob\n" +
            "  and job.idJob = :jobId\n" +
            "  and job_industry.idIndustry = :industryId and timed.idTime = :timeId\n" +
            "group by job_fact.idTime, job_fact.idJob,job_industry.idIndustry;", nativeQuery = true)
    List<Object[]> getSalaryJobInQuarterWithCountry( @Param("jobId") int jobId, @Param("industryId") int industryId,
                                                          @Param("timeId") int timedId);

    @Query( value = "select job.name_job, avg(salary)\n" +
            "from job_fact, job, timed, job_industry, province\n" +
            "where job_fact.idJob = job.idJob and job_fact.idTime = timed.idTime\n" +
            "  and job_fact.idJob = job_industry.idJob and job_fact.IdProvince = province.idProvince\n" +
            "  and job.idJob = :jobId and province.idProvince = :locationId\n" +
            "  and job_industry.idIndustry = :industryId and timed.idTime = :timeId\n" +
            "group by job_fact.idTime, job_fact.idJob,job_industry.idIndustry;", nativeQuery = true)
    List<Object[]> getSalaryJobInQuarterWithProvince( @Param("jobId") int jobId, @Param("industryId") int industryId,
                                                      @Param("timeId") int timeId, @Param("locationId") int locationId);


    @Query( value = " select * from age;", nativeQuery = true)
    List<Object[]> getAgeRange();

    @Query( value = " select * from academic_level;", nativeQuery = true)
    List<Object[]> getLiteracy();

    @Query( value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from timed\n" +
            "order by idTime;", nativeQuery = true)
    List<Object[]> getTime();

}