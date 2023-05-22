package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.repositories.ProveedorRepository;
import tingeso.mingeso.pep1.services.ProveedorService;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ProveedorServiceTest {

    @Autowired
    ProveedorService proveedorService;

}
