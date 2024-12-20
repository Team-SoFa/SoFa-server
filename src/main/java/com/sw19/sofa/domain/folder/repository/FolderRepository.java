package com.sw19.sofa.domain.folder.repository;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Long> {
    List<Folder> findAllByMember(Member member);
    Optional<Folder> findByNameAndMember(String name, Member member);
}
