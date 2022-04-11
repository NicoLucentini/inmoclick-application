package inmoclick.repository;

import inmoclick.entity.PropiedadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadesRepository extends JpaRepository<PropiedadEntity, Long> {
}
