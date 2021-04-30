package uz.pdp.program_47.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

private String username;

private Long number;

private Double balance;

private boolean active  = true;



}
