package dev.boki.flowassignment.repository;

import dev.boki.flowassignment.entity.Custom;
import org.springframework.stereotype.Repository;

//public interface CustomRepository extends JpaRepository<Custom, Long>  {
@Repository
public interface CustomRepository extends ExtensionRepository<Custom> {

}