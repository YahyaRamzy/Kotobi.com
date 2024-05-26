package com.kotobi.app.user_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kotobi.app.user_managment.entity.Seller;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
}
