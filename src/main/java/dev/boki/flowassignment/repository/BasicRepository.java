package dev.boki.flowassignment.repository;

import dev.boki.flowassignment.entity.Basic;
import org.springframework.stereotype.Repository;

//public interface BasicRepository extends JpaRepository<Basic, Long> {
@Repository
public interface BasicRepository extends ExtensionRepository<Basic> {
    
}