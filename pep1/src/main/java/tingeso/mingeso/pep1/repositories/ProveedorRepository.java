package tingeso.mingeso.pep1.repositories;

import tingeso.mingeso.pep1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String>{

}
