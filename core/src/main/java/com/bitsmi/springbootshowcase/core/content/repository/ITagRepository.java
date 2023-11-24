package com.bitsmi.springbootshowcase.core.content.repository;

import com.bitsmi.springbootshowcase.core.content.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepository extends JpaRepository<TagEntity, Long>
{

}
