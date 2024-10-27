package com.sw19.sofa.domain.folder.repository;

import com.sw19.sofa.domain.folder.entity.FolderTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderTagRepository extends JpaRepository<FolderTag, Long>, FolderTagCustomRepository {
}
