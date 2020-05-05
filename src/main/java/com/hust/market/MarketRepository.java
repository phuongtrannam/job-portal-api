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
    @Query(value = "select top.idTime, timed.timestampD,concat(timed.quarterD,\"/\",timed.yearD) as `time`, province.Province, job.name_job, top.salary, top.growth, top.rank_job\n" +
            "from top10_highest_salary_jobs_by_region as top , timed, job, province\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob and top.IdProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.idProvince, top.salary desc;", nativeQuery = true)
    List<Object[]> getJobsHighestSalaryByRegion(@Param("province") String province);

    @Query(value = "select top.idTime, timed.timestampD,concat(timed.quarterD,\"/\",timed.yearD) as `time`, job.name_job, top.salary, top.growth, top.rank_job\n" +
            "from top10_highest_salary_jobs as top , timed, job\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
        "order by top.idTime, top.salary desc;", nativeQuery = true)
    List<Object[]> getJobsHighestSalary();

    @Query(value = "select top.idTime, timed.timestampD,concat(timed.quarterD,\"/\",timed.yearD) as `time`, job.name_job, top.number_of_recruitment, top.growth, top.rank_job\n" +
            "from top10_jobs_with_the_highest_recruitment as top , timed, job\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getJobsHighestRecruitment();

    @Query(value = "select top.idTime, timed.timestampD,concat(timed.quarterD,\"/\",timed.yearD) as `time`,province.Province, job.name_job, top.number_of_recruitment, top.growth, top.rank_job\n" +
            "from top10_jobs_with_the_highest_recruitment_by_region as top , timed, job, province\n" +
            "where top.idTime = timed.idTime and top.idJob = job.idJob and top.IdProvince = province.idProvince\n" +
            "\tand province.province = :province \n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getJobsHighestRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select top.idTime, timed.timestampD,\n" +
            "\tconcat(timed.quarterD,\"/\",timed.yearD) as `time`,province.province, company.name_company,\n" +
            "    top.number_of_recruitment, top.growth, top.rank_company\n" +
            "from top10_companies_with_the_highest_recruitment_by_region as top, timed, company, province\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany and top.idProvince = province.idProvince\n" +
            "\tand province.province = :province\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime,top.idProvince, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getCompaniesHighestRecruitmentByRegion(@Param("province") String province);

    @Query( value = "select top.idTime, timed.timestampD,\n" +
            "\tconcat(timed.quarterD,\"/\",timed.yearD) as `time`, company.name_company, top.number_of_recruitment, top.growth, top.rank_company\n" +
            "from top10_companies_with_the_highest_recruitment as top, timed, company\n" +
            "where top.idTime = timed.idTime and top.idCompany = company.idCompany\n" +
            "\tand top.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "order by top.idTime, top.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getCompaniesHighestRecruitment();


}