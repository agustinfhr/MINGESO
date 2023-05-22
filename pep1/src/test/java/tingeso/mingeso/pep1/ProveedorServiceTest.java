package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.services.ProveedorService;
import tingeso.mingeso.pep1.repositories.ProveedorRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProveedorServiceTest {

    @MockBean
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Test
    void testGuardarProveedor() {
        String codigo = "123";
        String nombre = "Proveedor Test";
        String categoria = "Categoria Test";
        String retencion = "Si";

        ProveedorEntity proveedorEsperado = new ProveedorEntity();
        proveedorEsperado.setCodigo(codigo);
        proveedorEsperado.setNombre(nombre);
        proveedorEsperado.setCategoria(categoria);
        proveedorEsperado.setRetencion(retencion);

        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);

        Mockito.verify(proveedorRepository).save(Mockito.refEq(proveedorEsperado));
    }

    @Test
    void testObtenerProveedores() {
        ProveedorEntity proveedor1 = new ProveedorEntity();
        proveedor1.setCodigo("123");
        proveedor1.setNombre("Proveedor Test 1");
        proveedor1.setCategoria("Categoria Test 1");
        proveedor1.setRetencion("Si");

        ProveedorEntity proveedor2 = new ProveedorEntity();
        proveedor2.setCodigo("456");
        proveedor2.setNombre("Proveedor Test 2");
        proveedor2.setCategoria("Categoria Test 2");
        proveedor2.setRetencion("No");

        ArrayList<ProveedorEntity> proveedoresEsperados = new ArrayList<>(Arrays.asList(proveedor1, proveedor2));

        Mockito.when(proveedorRepository.findAll()).thenReturn(proveedoresEsperados);

        ArrayList<ProveedorEntity> proveedoresObtenidos = proveedorService.obtenerProveedores();

        assertEquals(proveedoresEsperados, proveedoresObtenidos);
    }
}

