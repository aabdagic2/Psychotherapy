package ba.unsa.etf.pnwt.grpc.repository;

import ba.unsa.etf.pnwt.grpc.entity.LoggingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<LoggingEntity, Integer> {
}
