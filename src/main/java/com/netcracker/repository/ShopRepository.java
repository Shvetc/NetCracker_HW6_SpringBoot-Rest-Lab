package com.netcracker.repository;

import com.netcracker.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query(value = "SELECT s.id, s.name, s.region, s.commission FROM shop s" + " WHERE s.region = :region_1 " +
            "OR s.region = :region_2", nativeQuery = true)
    List<Shop> getNamesOfShopsInTheCurrentRegions(@Param("region_1") String region_1, @Param("region_2") String region_2);
}
