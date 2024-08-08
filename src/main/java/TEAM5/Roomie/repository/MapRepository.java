package TEAM5.Roomie.repository;

import TEAM5.Roomie.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {
}
