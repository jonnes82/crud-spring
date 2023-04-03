package com.loiane.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
//@Table(name = "cursos")
@SQLDelete(sql = "update course set status = 'Inativo' where id = ?")
@Where(clause = "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;
    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Pattern(regexp = "Back-end|Front-End")
    @Column(length = 20, nullable = false)
    private String category;

    @NotNull
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = "Ativo|Inativo")
    @Column(length = 10, nullable = false)
    private String status = "Ativo";

    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Course() {
    }


}
