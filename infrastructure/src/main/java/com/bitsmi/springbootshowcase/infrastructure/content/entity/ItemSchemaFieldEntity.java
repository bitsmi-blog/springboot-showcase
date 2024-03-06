package com.bitsmi.springbootshowcase.infrastructure.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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

@SequenceGenerator(name="ITEM_SCHEMA_FIELD_ID_GENERATOR",
        sequenceName="SEQ_ITEM_SCHEMA_FIELD",
        allocationSize=1)
@Entity
@Table(name="ITEM_SCHEMA_FIELD")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ItemSchemaFieldEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ITEM_SCHEMA_FIELD_ID_GENERATOR")
    private Long id;

    @Version
    private Long version;

    @ManyToOne(optional = false)
    private ItemSchemaEntity schema;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DataType dataType;

    private String comments;

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
                .append("schema", schema)
                .append("name", name)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, schema);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemSchemaFieldEntity other = (ItemSchemaFieldEntity) o;
        return Objects.equals(name, other.name)
                && Objects.equals(schema, other.schema);
    }
}