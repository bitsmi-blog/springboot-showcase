package com.bitsmi.springbootshowcase.infrastructure.content.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@SequenceGenerator(name="ITEM_FIELD_ID_GENERATOR",
        sequenceName="SEQ_ITEM_FIELD",
        allocationSize=1)
@Entity
@Table(name="ITEM_FIELD")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ItemFieldEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ITEM_FIELD_ID_GENERATOR")
    private Long id;

    @Version
    private Long version;

    private String fieldName;

    @ManyToOne(optional = false)
    private ItemEntity item;

    /* "value" is a reserved word in H2 (used to test)
     * https://github.com/h2database/h2database/issues/3292
     */
    @Column(name = "VAL")
    private String value;

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
                .append("item", item)
                .append("fieldName", fieldName)
                .append("value", value)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ItemFieldEntity other
                    && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
