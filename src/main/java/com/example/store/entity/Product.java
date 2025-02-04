package com.example.store.entity;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Data
@Table(name = "product")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {
    @Id
    @GeneratedValue(generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> order;
}
