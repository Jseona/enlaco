package com.example.enlaco.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "material")
@SequenceGenerator(
        name = "material_SEQ",
        sequenceName = "material_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class MaterialEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_SEQ")
    private Integer maid;   //식재료 판별 번호
    @Column(name = "mama")
    private String  mama;   //식재료
}
