package uz.pdp.program_47.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.program_47.entity.InCome;

public interface InComeRepository extends JpaRepository<InCome, Integer> {

    @Query(value = "select count(*) > 0 from in_come", nativeQuery = true)
    boolean existsInCome();

    boolean existsByToCardId(Integer toCard_id);
    Page<InCome> getByToCardId(Integer toCard_id, Pageable pageable);



}
