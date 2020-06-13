package com.hust.region;

import java.util.List;

import org.hibernate.cache.spi.entry.StructuredMapCacheEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RegionRepository extends CrudRepository<Region, String> {
    // Pending
    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRootRegion(@Param("id") String id);
    
    //Done
    @Query(value = "select idProvince, Province\n" +
            "from province;", nativeQuery = true)
    List<Object[]> getProvinceRegion();

    //Pending
    @Query( value = "select district\n" +
            "from province, location\n" +
            "where province.idProvince = location.IdProvince\n" +
            "    and province.idProvince = :id ;", nativeQuery = true)
    List<Object[]> getDistinctByProvince(@Param("id") int id);

    //Pending
    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getRelatedRegions(@Param("id") String id);

    // Done - slow      
    @Query(value = "select timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany, idJob, number_of_recruitment\n" +
            "      from company_fact) as fact, timed\n" +
            "where timed.idTime = fact.idTime\n" +
            "      and fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 2) as t)\n" +
            "group by timed.idTime;", nativeQuery = true)
    List<Object[]> getNumberJobPostingInCountry();

    //Done - slow
    @Query( value = "select timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(fact.number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany,idProvince, idJob, number_of_recruitment\n" +
            "      from company_fact \n" +
            "where idProvince = :idProvince) as fact, timed, province\n" +
            "where fact.idTime = timed.idTime and fact.idProvince = province.idProvince\n" +
            "    and fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 2) as t)\n" +
            "group by timed.idTime, province.idProvince;", nativeQuery = true)
    List<Object[]> getNumberJobPostingInProvince(@Param("idProvince") int idProvince);

    //Done - slow
    @Query(value = "with tmp as (\n" +
            "    select distinct idTime, idCompany\n" +
            "    from company_fact\n" +
            "    where company_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 2) as t)\n" +
            ")\n" +
            "select timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, count(idCompany)\n" +
            "from tmp, timed\n" +
            "where tmp.idTime = timed.idTime\n" +
            "group by idTime\n" +
            "order by idTime desc;", nativeQuery = true)
    List<Object[]> getNumberCompanyInCountry();

     //Done - slow
    @Query( value = "with tmp as (\n" +
            "    select distinct idTime, idCompany\n" +
            "    from company_fact\n" +
            "    where company_fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 2) as t)\n" +
            "        and idProvince = :idProvince \n" +
            ")\n" +
            "select timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, count(idCompany)\n" +
            "from tmp, timed\n" +
            "where tmp.idTime = timed.idTime\n" +
            "group by idTime\n" +
            "order by idTime desc;\n", nativeQuery = true)
    List<Object[]> getNumberCompanyInProvince(@Param("idProvince") int idProvince);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getAverageSalaryInRegion(@Param("id") String id);

    // Done - Không hiểu câu này ra cái gì     
    @Query( value = "select year(timestampD) as `thoi gian`, number_of_recruitment, salary\n" +
            "from company_fact, timed\n" +
            "where company_fact.idTime = timed.idTime\n" +
            "    and timed.yearD between year(curdate()) - 2 and year(curdate()) -1 \n" +
            "order by timed.idTime desc;", nativeQuery = true)
    List<Object[]> getListSalaryInLastYearWithCountry();
    // Done - Không hiểu câu này ra cái gì      
    @Query( value = "select year(timestampD) as `thoi gian`, number_of_recruitment, salary\n" +
            "from company_fact, timed\n" +
            "where company_fact.idTime = timed.idTime and idProvince = :idProvince \n" +
            "    and timed.yearD between year(curdate()) - 2 and year(curdate()) -1 \n" +
            "order by timed.idTime desc;", nativeQuery = true)
    List<Object[]> getListSalaryInLastYearWithProvince(@Param("idProvince") int idProvince);
    // Done - Không hiểu câu này ra cái gì 
    @Query(value = "select year(timestampD) as `thoi gian`, number_of_recruitment, age\n" +
            "from company_fact, timed, age\n" +
            "where company_fact.idTime = timed.idTime\n" +
            "  and company_fact.idAge = age.idAge\n" +
            "  and timed.yearD between year(curdate())-2 and year(curdate())-1 \n" +
            "order by timed.idTime desc;", nativeQuery = true)
    List<Object[]> getAverageAgeInCountry();
    // Done - slow - Không hiểu câu này ra cái gì 
    @Query( value = "select year(timestampD) as `thoi gian`, number_of_recruitment, age\n" +
            "from company_fact, timed, age, province\n" +
            "where company_fact.idTime = timed.idTime and company_fact.idProvince = province.idProvince\n" +
            "  and company_fact.idAge = age.idAge and province.idProvince = :idProvince \n" +
            "  and timed.yearD between year(curdate())-1 and year(curdate());", nativeQuery = true)
    List<Object[]> getAverageAgeInProvince(@Param("idProvince") int idProvince);
    // Done - slow 
    @Query(value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_industries as top10, timed, industries\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getAverageSalaryByIndustryWithCountry();
    
    // Done - slow 
    @Query( value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_industries_by_region as top10, timed, province, industries\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "  and province.idProvince = :idProvince \n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getAverageSalaryByIndustryWithProvince(@Param("idProvince") int idProvince);
    //Done
    @Query(value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.number_of_recruitment, top10.growth, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_industries_with_the_highest_recruitment as top10, timed, industries\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "order by timed.idTime, top10.number_of_recruitment desc", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithCountry();
    //Done
    @Query( value = "select industries.idIndustry, industries.name_industry,\n" +
            "       top10.number_of_recruitment, top10.growth, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_industries_with_the_highest_recruitment_by_region as top10, timed, province, industries\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idTime in ( select idTime from ( select idTime from timed order by timestampD desc limit 3 ) as t )\n" +
            "  and top10.idIndustry = industries.idIndustry\n" +
            "  and province.idProvince = :idProvince \n" +
            "order by timed.idTime, top10.number_of_recruitment desc", nativeQuery = true)
    List<Object[]> getJobDemandByIndustryWithProvince(@Param("idProvince") int idProvince);
    //Done
    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.salary, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_jobs as top10, timed, job\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobsWithCountry();
    //Done
    @Query( value = "select job.idJob, job.name_job,\n" +
            "       top10.salary, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_jobs_by_region as top10, timed, province,  job\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and province.idProvince = :idProvince\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, province.idProvince, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestSalaryJobsWithProvince(@Param("idProvince") int idProvince);
    //Done
    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_jobs_with_the_highest_recruitment as top10, timed, job\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getHighestDemandJobsWithCountry();
    //Done
    @Query(value = "select job.idJob, job.name_job,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime,concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_jobs_with_the_highest_recruitment_by_region as top10, timed, province,  job\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idJob = job.idJob\n" +
            "  and province.idProvince = :idProvince \n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, province.idProvince, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getHighestDemandJobsWithProvince(@Param("idProvince") int idProvince);
    //Done
    @Query(value = "select company.idCompany, company.name_company,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_companies as top10, timed,  company\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idCompany = company.idCompany\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestPayingCompaniesWithCountry();
    //Done
    @Query( value = "select company.idCompany, company.name_company,\n" +
            "       top10.salary, top10.growth, timed.idTime,\n" +
            "       concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_highest_salary_companies_by_region as top10, timed,  company, province\n" +
            "where top10.idTime = timed.idTime and top10.IdProvince = province.idProvince\n" +
            "  and top10.idCompany = company.idCompany and province.idProvince = :idProvince \n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.salary desc;", nativeQuery = true)
    List<Object[]> getHighestPayingCompaniesWithProvince(@Param("idProvince") int idProvince);
    //Done
    @Query(value = "select company.idCompany, company.name_company,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_companies_with_the_highest_recruitment as top10, timed,  company\n" +
            "where top10.idTime = timed.idTime\n" +
            "  and top10.idCompany = company.idCompany\n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getTopHiringCompaniesWithCountry();
    //Done
    @Query( value ="select company.idCompany, company.name_company,\n" +
            "       top10.number_of_recruitment, top10.growth, timed.idTime, concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`\n" +
            "from top10_companies_with_the_highest_recruitment_by_region as top10, timed,  company, province\n" +
            "where top10.idTime = timed.idTime and top10.idProvince = province.idProvince\n" +
            "  and top10.idCompany = company.idCompany and province.idProvince = :idProvince \n" +
            "  and top10.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 3) as t)\n" +
            "order by timed.idTime, top10.number_of_recruitment desc;", nativeQuery = true)
    List<Object[]> getTopHiringCompaniesWithProvince(@Param("idProvince") int idProvince);


    @Query( value = "select sum(number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany,idIndustry, idJob, number_of_recruitment\n" +
            "      from company_fact \n" +
            " where idTime = :idTime and  idIndustry = :idIndustry) as fact, industries, timed\n" +
            "where fact.idIndustry = industries.idIndustry\n" +
            "  and fact.idTime = timed.idTime\n" +
            "group by timed.idTime, industries.idIndustry;", nativeQuery = true)
    List<Object[]> getDemandByIndusrtryWithCountry(@Param("idTime") int idTime, @Param("idIndustry") int idIndustry);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany,idProvince,idIndustry, idJob, number_of_recruitment\n" +
            "      from company_fact \n" +
            "where idProvince = :idProvince and idTime = :idTime and idIndustry = :idIndustry) as fact, industries, province, timed\n" +
            "where fact.idProvince = province.idProvince and fact.idIndustry = industries.idIndustry\n" +
            "    and fact.idTime = timed.idTime\n" +
            "group by timed.idTime, province.idProvince, industries.idIndustry;", nativeQuery = true)
    List<Object[]> getDemandByIndustryWithProvince( @Param("idTime") int idTime, @Param("idIndustry") int idIndustry,
                                                    @Param("idProvince") int idProvince);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob \n" +
            "group by idTime, idJob;", nativeQuery = true
    )
    List<Object[]> getDemandByJobWithCountry(@Param("idTime") int idTime, @Param("idJob") int idJob);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob and IdProvince = :idProvince \n" +
            "group by idTime,IdProvince, idJob;", nativeQuery = true
    )
    List<Object[]> getDemandByJobWithProvince(@Param("idTime") int idTime, @Param("idJob") int idJob,
                                             @Param("idProvince") int idProvince);

    @Query( value = "select min(salary), max(salary)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob\n" +
            "group by idTime, idJob;\n", nativeQuery = true
    )
    List<Object[]> getSalaryByJobWithCountry(@Param("idTime") int idTime, @Param("idJob") int idJob);

    @Query( value = "select min(salary), max(salary)\n" +
            "from job_fact\n" +
            "where job_fact.idTime = :idTime and job_fact.idJob = :idJob and IdProvince = :idProvince \n" +
            "group by idTime, idJob, IdProvince;", nativeQuery = true
    )
    List<Object[]> getSalaryByJobWithProvince(@Param("idTime") int idTime, @Param("idJob") int idJob,
                                              @Param("idProvince") int idProvince);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany, idJob, number_of_recruitment\n" +
            "      from company_fact \n" +
            "where idCompany = :idCompany and idTime = :idTime) as fact, timed\n" +
            "where fact.idTime = timed.idTime\n" +
            "group by timed.idTime, fact.idCompany;", nativeQuery = true)
    List<Object[]> getDemandByCompanyWithCountry(@Param("idTime") int idTime, @Param("idCompany") int idCompany);

    @Query( value = "select sum(number_of_recruitment)\n" +
            "from (select distinct idTime, idCompany, idJob, number_of_recruitment\n" +
            "      from company_fact \n" +
            "where idCompany = :idCompany and idProvince = :idProvince and idTime = :idTime) as fact, timed\n" +
            "where fact.idTime = timed.idTime\n" +
            "group by timed.idTime, fact.idCompany;", nativeQuery =true)
    List<Object[]> getDemandByCompanyWithProvince(@Param("idTime") int idTime, @Param("idCompany") int idCompany,
                                                  @Param("idProvince") int idProvince);

    @Query( value = "select min(salary), max(salary)\n" +
            "from company_fact\n" +
            "where idTime = :idTime and idCompany = :idCompany \n" +
            "group by idTime, idCompany;", nativeQuery = true)
    List<Object[]> getSalaryByCompanyWithCountry(@Param("idTime") int idTime, @Param("idCompany") int idCompany);

    @Query( value = "select min(salary), max(salary)\n" +
            "from company_fact\n" +
            "where idTime = :idTime and idCompany = :idCompany and idProvince = :idProvince \n" +
            "group by idTime,idProvince, idCompany;", nativeQuery = true)
    List<Object[]> getSalaryByCompanyWithProvince(@Param("idTime") int idTime, @Param("idCompany") int idCompany,
                                                  @Param("idProvince") int idProvince);

    @Query( value = " select * from age;", nativeQuery = true)
    List<Object[]> getAgeRange();

    @Query(value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment), age.age, gender.gender\n" +
            "from (select distinct idTime, idCompany,idAge, idGender, idJob, number_of_recruitment\n" +
            "           from company_fact) as fact, gender, age, timed\n" +
            "where fact.idTime = timed.idTime and fact.idGender = gender.idGender\n" +
            "  and fact.idAge = age.idAge\n" +
            "  and fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime, gender.idGender, age.idAge\n" +
            "order by timed.idTime, gender.idGender, age.age;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeWithCountry();

    @Query( value = "select concat(\"Quý \",timed.quarterD,\"/\",timed.yearD) as `time`, sum(number_of_recruitment), age.age, gender.gender\n" +
            "from (select distinct idTime, idCompany,idAge, idGender,idProvince, idJob, number_of_recruitment \n" +
            "        from company_fact where idProvince = :locationId ) as fact, gender, age, timed, province\n" +
            "where fact.idTime = timed.idTime and fact.idGender = gender.idGender\n" +
            "  and fact.idAge = age.idAge\n" +
            "  and fact.idProvince = province.idProvince\n" +
            "  and fact.idTime in (select idTime from ( select idTime from timed order by timestampD desc limit 4) as t )\n" +
            "group by timed.idTime, province.idProvince, gender.idGender, age.idAge\n" +
            "order by timed.idTime, gender.idGender, age.age;", nativeQuery = true)
    List<Object[]> getJobDemandByAgeWithProvince(@Param("locationId") int locationId);

}