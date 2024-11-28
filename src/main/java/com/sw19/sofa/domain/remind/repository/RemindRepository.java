package com.sw19.sofa.domain.remind.repository;

import com.sw19.sofa.domain.remind.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindRepository extends JpaRepository<Remind, Long> {
}
