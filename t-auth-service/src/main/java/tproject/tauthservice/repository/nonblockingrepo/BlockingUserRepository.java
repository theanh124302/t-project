package tproject.tauthservice.repository.nonblockingrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.tauthservice.entity.UserEntity;

public interface BlockingUserRepository extends JpaRepository<UserEntity, Long> {

}
