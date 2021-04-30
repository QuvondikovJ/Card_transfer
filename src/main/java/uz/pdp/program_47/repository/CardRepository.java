package uz.pdp.program_47.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.program_47.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByNumberAndActive(Long number, boolean active);
    boolean existsByActive(boolean active);
    Page<Card> getByActive(boolean active, Pageable pageable);
    boolean existsByIdAndActive(Integer id, boolean active);
boolean existsByNumberAndActiveAndIdNot(Long number, boolean active, Integer id);


}
