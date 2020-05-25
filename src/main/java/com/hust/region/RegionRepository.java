package com.hust.region;

import java.util.List;

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

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestSalaryJobs(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestDemandJobs(@Param("id") String id);

    @Query(value = "select * from job fact", nativeQuery = true)
    List<Object[]> getHighestPayingCompanies(@Param("id") String id);

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


}