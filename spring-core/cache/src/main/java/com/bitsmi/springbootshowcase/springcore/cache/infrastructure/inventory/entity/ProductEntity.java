package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@SequenceGenerator(name="PRODUCT_ID_GENERATOR",
        sequenceName="SEQ_PRODUCT",
        allocationSize=1)
@Entity
@Table(name="PRODUCT")
@Getter
@Setter
@Builder(toBuilder = true, builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="USER_ID_GENERATOR")
    private Long id;

    @Version
    private Long version;

    @Column(unique=true)
    private String externalId;
    private String name;

    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateDefaultValues()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        if(creationDate==null){
            creationDate = currentDate;
        }
        lastUpdated = currentDate;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("version", version)
                .append("externalId", externalId)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalId);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ProductEntity other
                && Objects.equals(externalId, other.externalId);
    }
}
