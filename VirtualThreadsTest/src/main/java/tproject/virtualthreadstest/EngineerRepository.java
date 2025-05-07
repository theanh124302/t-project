package tproject.virtualthreadstest;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EngineerRepository extends JpaRepository<VirtualThreadsTestApplication.EngineerEntity, Long> {
    List<VirtualThreadsTestApplication.EngineerEntity> findByTitle(String title);
}

