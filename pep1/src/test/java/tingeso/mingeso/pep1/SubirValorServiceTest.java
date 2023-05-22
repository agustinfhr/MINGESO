package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.repositories.SubirValorRepository;
import tingeso.mingeso.pep1.services.SubirValorService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SubirValorServiceTest {

    @MockBean
    private SubirValorRepository subirValorRepository;

    @Autowired
    private SubirValorService subirValorService;

    @Test
    void testGuardarValorDB() {

        String proveedor = "XXXXX";
        String pct_grasa = "10";
        String pct_solido_total = "20";


        SubirValorEntity valorEsperado = new SubirValorEntity();
        valorEsperado.setProveedor(proveedor);
        valorEsperado.setPct_grasa(pct_grasa);
        valorEsperado.setPct_solido_total(pct_solido_total);


        subirValorService.guardarValorDB(proveedor, pct_grasa, pct_solido_total);

        Mockito.verify(subirValorRepository).save(Mockito.refEq(valorEsperado));
    }

    @Test
    void testObtenerValor() {
        SubirValorEntity valor1 = new SubirValorEntity();
        valor1.setProveedor("XXXXX");
        valor1.setPct_grasa("10");
        valor1.setPct_solido_total("20");


        SubirValorEntity valor2 = new SubirValorEntity();
        valor2.setProveedor("YYYYY");
        valor2.setPct_grasa("20");
        valor2.setPct_solido_total("30");

        ArrayList<SubirValorEntity> valoresEsperados = new ArrayList<>(Arrays.asList(valor1, valor2));

        Mockito.when(subirValorRepository.findAll()).thenReturn(valoresEsperados);

        ArrayList<SubirValorEntity> valoresObtenidos = subirValorService.obtenerValor();

        assertEquals(valoresEsperados, valoresObtenidos);
    }

    @Test
    void testGuardarValor() {

        SubirValorEntity valorEsperado = new SubirValorEntity();

        subirValorService.guardarValor(valorEsperado);

        Mockito.verify(subirValorRepository).save(valorEsperado);
    }

}
