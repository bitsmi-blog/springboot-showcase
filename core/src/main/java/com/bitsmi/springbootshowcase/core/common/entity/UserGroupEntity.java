package com.bitsmi.springbootshowcase.core.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
import java.util.Set;

@SequenceGenerator(name="USER_GROUP_ID_GENERATOR",
        sequenceName="SEQ_APP_USER_GROUP",
        allocationSize=1)
@Entity
@Table(name="APP_USER_GROUP")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="USER_GROUP_ID_GENERATOR")
    private Long id;

    @Version
    private Long version;

    @Column(unique=true)
    private String name;

    private String description;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "APP_USER_GROUP_AUTH",
            joinColumns = { @JoinColumn(name = "GROUP_ID") },
            inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") }
    )
    private Set<AuthorityEntity> authorities;

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
                .append("name", name)
                .append("description", description)
                .append("authorities", authorities)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
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

        UserGroupEntity other = (UserGroupEntity) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
