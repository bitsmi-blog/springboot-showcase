package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@SequenceGenerator(name="STOCK_ID_GENERATOR",
        sequenceName="SEQ_STOCK",
        allocationSize=1)
@Entity
@Table(name="STOCK")
@Getter
@Setter
@Builder(toBuilder = true, builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="STOCK_ID_GENERATOR")
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    @NotNull
    private ProductEntity product;
    @ManyToOne
    @NotNull
    private StoreEntity store;
    @NotNull
    @Min(0)
    private Integer quantity;

    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateDefaultValues() {
        LocalDateTime currentDate = LocalDateTime.now();
        if (creationDate == null) {
            creationDate = currentDate;
        }
        lastUpdated = currentDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("version", version)
                .append("product", product)
                .append("store", store)
                .append("quantity", quantity)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || o instanceof StockEntity other
                && Objects.equals(id, other.id);
    }
}
