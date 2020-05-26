package com.hust.region;

import java.util.List;

import org.hibernate.cache.spi.entry.StructuredMapCacheEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RegionRepository extends CrudRepository<Region, String> {


    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRootRegion(@Param("id") String id);

    @Query(value = "select idProvince, Province\n" +
            "from province;", nativeQuery = true)
    List<Object[]> getProvinceRegion();

    @Query( value = "select district\n" +
            "from province, location\n" +
            "where province.idProvince = location.IdProvince\n" +
            "    and province.idProvince = :id ;", nativeQuery = true)
    List<Object[]> getDistinctByProvince(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRelatedRegions(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getNumberJobPostingInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getNumberCompanyInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryInRegion(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageAgeInRegion(@Param("id") String id);

    @Query(value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_industries as top10, timed, industries\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getAverageSalaryByIndustryWithCountry();

    @Query( value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_industries_by_region as top10, timed, province, industries\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "  and province.idProvince = :idProvince \n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getAverageSalaryByIndustryWithProvince(@Param("idProvince") String idProvince);
    
    @Query(value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.number_of_recruitment, top10.growth, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_industries_with_the_highest_recruitment as top10, timed, industries\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "order by timed.idTime, top10.number_of_recruitment desc", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithCountry();

    @Query( value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.number_of_recruitment, top10.growth, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_industries_with_the_highest_recruitment_by_region as top10, timed, province, industries\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "  and province.idProvince = :idProvince \n" +
            "order by timed.idTime, top10.number_of_recruitment desc", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithProvince(@Param("idProvince") String idProvince);

    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.salary, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_jobs as top10, timed, job\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobsWithCountry();

    @Query( value = "select job.idJob, job.name_job,\n" +
            "       top10.salary, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_jobs_by_region as top10, timed, province,  job\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and province.idProvince = :idProvince\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, province.idProvince, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobsWithProvince(@Param("idProvince") String idProvince);

    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_jobs_with_the_highest_recruitment as top10, timed, job\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getHighestDemandJobsWithCountry();

    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_jobs_with_the_highest_recruitment_by_region as top10, timed, province,  job\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and province.idProvince = :idProvince \n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, province.idProvince, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getHighestDemandJobsWithProvince(@Param("idProvince") String idProvince);

    @Query(value = "select company.idCompany, company.name_company,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_companies as top10, timed,  company\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idCompany = company.idCompany\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestPayingCompaniesWithCountry();

    @Query( value = "select company.idCompany, company.name_company,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_companies_by_region as top10, timed,  company, province\n" +
            "where top10.idTime = timed.idTime and top10.IdProvince = province.idProvince\n" +
            "  and top10.idCompany = company.idCompany and province.idProvince = :idProvince \n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestPayingCompaniesWithProvince(@Param("idProvince") String idProvince);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getTopHiringCompanies(@Param("id") String id);


    @Query( value = "select sum(number_of_recruitment)\n" +
            "from market_fact, industries, timed\n" +
            "where market_fact.idIndustry = industries.idIndustry\n" +
            "  and market_fact.idTime = timed.idTime\n" +
            "  and timed.idTime = :idTime\n" +
            "  and industries.idIndustry = :idIndustry \n" +
            "group by timed.idTime, industries.idIndustry;", nativeQuery = true)
    List<Object[]> getDemandByIndusrtryWithCountry(@Param("idTime") String idTime, @Param("idIndustry") String idIndustry);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from market_fact, industries, province, timed\n" +
            "where market_fact.idProvince = province.idProvince and market_fact.idIndustry = industries.idIndustry\n" +
            "    and market_fact.idTime = timed.idTime\n" +
            "    and province.idProvince = :idProvince and timed.idTime = :idTime \n" +
            "    and industries.idIndustry = :idIndustry \n" +
            "group by timed.idTime, province.idProvince, industries.idIndustry;", nativeQuery = true)
    List<Object[]> getDemandByIndustryWithProvince( @Param("idTime") String idTime, @Param("idIndustry") String idIndustry,
                                                    @Param("idProvince") String idProvince);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob \n" +
            "group by idTime, idJob;", nativeQuery = true
    )
    List<Object[]> getDemandByJobWithCountry(@Param("idTime") String idTime, @Param("idJob") String idJob);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob and IdProvince = :idProvince \n" +
            "group by idTime,IdProvince, idJob;", nativeQuery = true
    )
    List<Object[]> getDemandByJobWithProvince(@Param("idTime") String idTime, @Param("idJob") String idJob,
                                             @Param("idProvince") String idProvince);

    @Query( value = "select avg(salary)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob\n" +
            "group by idTime, idJob;\n", nativeQuery = true
    )
    List<Object[]> getSalaryByJobWithCountry(@Param("idTime") String idTime, @Param("idJob") String idJob);

    @Query( value = "select avg(salary)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob and IdProvince = :idProvince \n" +
            "group by idTime, idJob, IdProvince;", nativeQuery = true
    )
    List<Object[]> getSalaryByJobWithProvince(@Param("idTime") String idTime, @Param("idJob") String idJob,
                                              @Param("idProvince") String idProvince);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from company_fact, timed\n" +
            "where company_fact.idTime = timed.idTime\n" +
            "    and idCompany = :idCompany and timed.idTime = :idTime\n" +
            "group by timed.idTime, company_fact.idCompany;", nativeQuery = true)
    List<Object[]> getDemandByCompanyWithCountry(@Param("idTime") String idTime, @Param("idCompany") String idCompany);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from company_fact, timed\n" +
            "where company_fact.idTime = timed.idTime\n" +
            "    and idCompany = :idCompany and idProvince = :idProvince and timed.idTime = :idTime \n" +
            "group by timed.idTime, company_fact.idCompany;", nativeQuery =true)
    List<Object[]> getDemandByCompanyWithProvince(@Param("idTime") String idTime, @Param("idCompany") String idCompany,
                                                  @Param("idProvince") String idProvince);

}