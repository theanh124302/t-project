package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import tproject.userservice.entity.TUserEntity;

@RequestMapping
public interface TUserRepository extends JpaRepository<TUserEntity, Long> {
}
