package com.wallet.repositories;

import com.wallet.models.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

    @Query("SELECT COUNT(c) > 0 FROM CurrencyEntity c WHERE c.name = :name AND c.code = :code")
    boolean existsByNameAndCode(String name, String code);
}
