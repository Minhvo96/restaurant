package com.example.booking_restaurant.domain;

import com.example.booking_restaurant.domain.enumration.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "foods")
@NoArgsConstructor
@Getter
@Setter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private String description;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "food")
    private List<FoodImage> images;
}
