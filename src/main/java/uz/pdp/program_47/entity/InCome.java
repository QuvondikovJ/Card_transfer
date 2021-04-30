package uz.pdp.program_47.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InCome {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

@OneToOne
private Card fromCard;

@OneToOne
    private Card toCard;

private Double amount;

@CreationTimestamp
private Timestamp timestamp;

}