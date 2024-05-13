package com.example.can.port.outbound;

import com.example.can.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
}
