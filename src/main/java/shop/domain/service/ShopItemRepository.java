package shop.domain.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.domain.model.ShopItem;

import java.util.List;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    List<ShopItem> findAll();

    boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM ShopItem s WHERE s.name = :name AND s.id <> :id")
    boolean existsByNameAndExcludeId(@Param("username") String username, @Param("id") Long id);
}
