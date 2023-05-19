package tingeso.mingeso.pep1.repositories;

import tingeso.mingeso.pep1.entities.PlanillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso.mingeso.pep1.entities.SubirDataEntity;
import tingeso.mingeso.pep1.entities.SubirValorEntity;

import java.util.ArrayList;


@Repository
public interface PlanillaRepository extends JpaRepository <PlanillaEntity, String>{

    @Query("select e from PlanillaEntity e where e.codigo_proveedor = :proveedor order by e.quincena desc")
    ArrayList<PlanillaEntity> findByCodigo_proveedorOrderByQuincenaDesc(@Param("proveedor") String proveedor);

    @Query("select count(e) > 0 from PlanillaEntity e where e.codigo_proveedor = :proveedor")
    boolean existsAny(@Param("proveedor") String proveedor);

}
