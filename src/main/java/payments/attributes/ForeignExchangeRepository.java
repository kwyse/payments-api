package payments.attributes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForeignExchangeRepository extends JpaRepository<ForeignExchange, Long> {
    Optional<ForeignExchange> findById(Long id);
}
