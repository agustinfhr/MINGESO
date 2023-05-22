package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tingeso.mingeso.pep1.entities.SubirDataEntity;
import tingeso.mingeso.pep1.repositories.SubirDataRepository;
import tingeso.mingeso.pep1.services.SubirDataService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SubirDataServiceTest {

    @MockBean
    private SubirDataRepository subirDataRepository;

    @Autowired
    private SubirDataService subirDataService;

    @Test
    void testGuardarDataDB() {

        String fecha = "2023-12-31";
        String turno = "M";
        String proveedor = "XXXXX";
        String kls_leche = "100";


        SubirDataEntity dataEsperado = new SubirDataEntity();
        dataEsperado.setFecha(fecha);
        dataEsperado.setProveedor(proveedor);
        dataEsperado.setTurno(turno);
        dataEsperado.setKls_leche(kls_leche);

        subirDataService.guardarDataDB(fecha, turno, proveedor, kls_leche);

        Mockito.verify(subirDataRepository).save(Mockito.refEq(dataEsperado));
    }

    @Test
    void testObtenerData() {
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023-12-31");
        data1.setProveedor("XXXXX");
        data1.setTurno("M");
        data1.setKls_leche("100");

        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023-12-31");
        data2.setProveedor("XXXXX");
        data2.setTurno("T");
        data2.setKls_leche("100");

        ArrayList<SubirDataEntity> datasEsperados = new ArrayList<>(Arrays.asList(data1, data2));

        Mockito.when(subirDataRepository.findAllOrderByDateAsc()).thenReturn(datasEsperados);

        ArrayList<SubirDataEntity> datasObtenidos = subirDataService.obtenerData();

        assertEquals(datasEsperados, datasObtenidos);
    }

    @Test
    void testGuardarData() {

        SubirDataEntity dataEsperado = new SubirDataEntity();

        subirDataService.guardarData(dataEsperado);

        Mockito.verify(subirDataRepository).save(dataEsperado);
    }

}

