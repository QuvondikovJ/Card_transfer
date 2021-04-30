package uz.pdp.program_47.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.program_47.entity.OutCome;

public interface OutComeRepository extends JpaRepository<OutCome, Integer> {
    @Query(value = "select count(*) > 0 from out_come", nativeQuery = true)
    boolean existsOutCome();

    boolean existsByFromCardId(Integer fromCard_id);
    Page<OutCome> getByFromCardId(Integer fromCard_id, Pageable pageable);





}
