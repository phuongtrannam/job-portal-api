package com.hust.market;

import java.util.List;

import com.hust.market.model.Market;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends CrudRepository<Market, String> {

    // query cong viec co muc luong tuyen dung cao nhat ca nuoc va cac khu vuc
    @Query(value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, province.Province, job.name_job, top.salary, top.growth, top.rank_job\n" +
            "from top10_highest_salary_jobs_by_region as top , timed, job, province\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob and top.IdProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.idProvince, top.salary desc;", nativeQuery = true)
    List<Object[]> getJobsHighestSalaryByRegion(@Param("province") String province);

    @Query(value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.name_job, top.salary, top.growth, top.rank_job\n" +
            "from top10_highest_salary_jobs as top , timed, job\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
        "order by top.idTime, top.salary desc;", nativeQuery = true)
    List<Object[]> getJobsHighestSalary();

    @Query(value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, job.name_job, top.number_of_recruitment, top.growth, top.rank_job\n" +
            "from top10_jobs_with_the_highest_recruitment as top , timed, job\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getJobsHighestRecruitment();

    @Query(value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.Province, job.name_job, top.number_of_recruitment, top.growth, top.rank_job\n" +
            "from top10_jobs_with_the_highest_recruitment_by_region as top , timed, job, province\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob and top.IdProvince = province.idProvince\n" +
            "\tand province.province = :province \n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getJobsHighestRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select top.idTime, timed.timestampD,\n" +
            "\tconcat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.province, company.name_company,\n" +
            "    top.number_of_recruitment, top.growth, top.rank_company\n" +
            "from top10_companies_with_the_highest_recruitment_by_region as top, timed, company, province\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getCompaniesHighestRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select top.idTime, timed.timestampD,\n" +
            "\tconcat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, company.name_company, top.number_of_recruitment, top.growth, top.rank_company\n" +
            "from top10_companies_with_the_highest_recruitment as top, timed, company\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getCompaniesHighestRecruitment();

    @Query( value = "select top.idTime, timed.timestampD,\n" +
            "\tconcat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, company.name_company, top.salary, top.growth, top.rank_company\n" +
            "from top10_highest_salary_companies as top, timed, company\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.salary desc;", nativeQuery = true)
    List<Object[]> getCompaniesHighestSalary();

    @Query( value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.province, company.name_company, top.salary, top.growth, top.rank_company\n" +
            "from top10_highest_salary_companies_by_region as top, timed, company, province\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.salary desc;\n", nativeQuery = true)
    List<Object[]> getCompaniesHighestSalaryByRegion(@Param("province") String province);

    @Query(value = "select timed.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, industries.name_industry, top.salary, top.growth, top.rank_industry\n" +
            "from top10_highest_salary_industries as top, timed, industries\n" +
            "where top.idTime = timed.idTime and top.idIndustry = industries.idIndustry\n" +
            "\tand top.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
        "order by timed.idTime, top.salary desc;", nativeQuery = true)
    List<Object[]> getIndustriesHighestSalary();

    @Query( value = " select timed.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
            "\tprovince.province, industries.name_industry, top.salary, top.growth, top.rank_industry\n" +
            " from top10_highest_salary_industries_by_region as top, timed, province,  industries\n" +
            " where top.idTime = timed.idTime and top.idProvince = province.idProvince \n" +
            "\tand top.idIndustry = industries.idIndustry\n" +
            "    and province.province = :province \n" +
            "\tand top.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, province.idProvince, top.salary desc;", nativeQuery = true)
    List<Object[]> getIndustriesHighestSalaryByRegion(@Param("province") String province);

    @Query( value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, industries.name_industry, top.number_of_recruitment, top.growth, top.rank_industry\n" +
            "from top10_industries_with_the_highest_recruitment as top, timed, industries\n" +
            "where top.idTime = timed.idTime and top.idIndustry = industries.idIndustry\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getIndustriesHighestRecruitment();

    @Query( value = "select top.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.idProvince, industries.name_industry, top.number_of_recruitment, top.growth, top.rank_industry\n" +
            "from top10_industries_with_the_highest_recruitment_by_region as top, timed, industries, province\n" +
            "where top.idTime = timed.idTime and top.idIndustry = industries.idIndustry and top.idProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getIndustriesHighestRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select idTime, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact\n" +
            "where market_fact.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "group by idTime;", nativeQuery = true )
    List<Object[]> getAllAmountRecuitment();

    @Query( value = "select market_fact.idTime,province.idProvince, sum(number_of_recruitment)\n" +
            "from market_fact, province\n" +
            "where market_fact.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "\tand market_fact.idProvince = province.idProvince and province.province = :province\n" +
            "group by market_fact.idTime, province.idProvince;", nativeQuery = true)
    List<Object[]> getAllAmountRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select timed.idTime,timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, age.age, gender.gender, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, gender, age, timed\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idGender = gender.idGender\n" +
            "\tand market_fact.idAge = age.idAge \n" +
            "    and market_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t )\n" +
            "group by timed.idTime, age.idAge, gender.idGender\n" +
            "order by timed.timestampD, age.age, gender,gender;", nativeQuery = true)
    List<Object[]> getRecruitmentWithAgeGender();

    @Query( value = "select timed.idTime ,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.province, age.age, gender.gender, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, gender, age, timed, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idGender = gender.idGender\n" +
            "\tand market_fact.idAge = age.idAge and market_fact.idProvince = province.idProvince\n" +
            "    and province.province = :province\n" +
            "    and market_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t )\n" +
            "group by timed.idTime,province.idProvince, age.idAge, gender.idGender\n" +
            "order by timed.timestampD,province.Province, age.age, gender.gender;", nativeQuery = true)
    List<Object[]> getRecruitmentWithAgeGenderByRegion(@Param("province") String province);

    @Query( value = "select timed.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,\n" +
            "\tacademic_level.academic_level, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, academic_level, timed\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idAcademic_Level = academic_level.idAcademic_Level\n" +
            "\tand market_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 3) as t )\n" +
            "group by timed.idTime, academic_level.idAcademic_Level\n" +
            "order by timed.timestampD, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getRecruitmentWithLiteracy();

    @Query( value = "select timed.idTime, timed.timestampD,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.Province, academic_level.academic_level, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, academic_level, timed, province\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idAcademic_Level = academic_level.idAcademic_Level\n" +
            "\tand market_fact.idProvince = province.idProvince\n" +
            "    and province.province = :province\n" +
            "\tand market_fact.idTime in (select idTime from (select idTime from timed order by timestampD desc limit 3) as t )\n" +
            "group by timed.idTime,province.idProvince, academic_level.idAcademic_Level\n" +
            "order by timed.timestampD,province.province, academic_level.academic_level;", nativeQuery = true)
    List<Object[]> getRecruitmentWithLiteracyByRegion(@Param("province") String province);

    @Query( value = "select timed.idtime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`,province.province, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, province, timed\n" +
            "where market_fact.idTime = timed.idTime and market_fact.idProvince = province.idProvince\n" +
            "\tand province.province = :province \n" +
            "group by province.idProvince, timed.idTime\n" +
            "order by province.province, timed.timestampD;", nativeQuery = true)
    List<Object[]> getRecruitmentWithPeriodOfTimeByRegion(@Param("province") String province);

    @Query( value = "select timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(market_fact.number_of_recruitment)\n" +
            "from market_fact, timed\n" +
            "where market_fact.idTime = timed.idTime\n" +
            "group by market_fact.idTime\n" +
            "order by market_fact.idTime;", nativeQuery = true)
    List<Object[]> getRecruitmentWithPeriodOfTime();

}