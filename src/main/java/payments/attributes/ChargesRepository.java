package payments.attributes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargesRepository extends JpaRepository<Charges, Long> {
    Optional<Charges> findById(Long id);
}
