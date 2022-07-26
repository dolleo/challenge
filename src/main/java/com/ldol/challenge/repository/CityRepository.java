package com.ldol.challenge.repository;

import com.ldol.challenge.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

    Optional<City> findByGuid(String id);

    @Override
    List<City> findAll();

    @Query("select c from City c where c.isActive = :isActive and (c.tag0 = :tag or c.tag1 = :tag or c.tag2 = :tag or c.tag3 = :tag or c.tag4 = :tag or c.tag5 = :tag or c.tag6 = :tag)")
    List<City> findWithTagAndByIsActive(@Param("tag") String tag, @Param("isActive") Boolean isActive);
}
