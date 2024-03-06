package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepository extends JpaRepository<TagEntity, Long>
{

}
