package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import tingeso.mingeso.pep1.entities.SubirDataEntity;
import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.services.PlanillaService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlanillaServiceTest {

    @Autowired
    PlanillaService planillaService;

    @Test
    void testKlsLeche() {
        SubirDataEntity data = new SubirDataEntity();
        data.setKls_leche("50");
        int totalKlsLeche = planillaService.klsLeche(100,data);
        assertEquals(150,totalKlsLeche);
    }

    @Test
    void testPagoPorLeche() {
        int pago = planillaService.pagoPorLeche("A", 100);
        assertEquals(70000, pago);
        pago = planillaService.pagoPorLeche("B", 100);
        assertEquals(55000, pago);
        pago = planillaService.pagoPorLeche("C",100);
        assertEquals(40000, pago);
        pago = planillaService.pagoPorLeche("D",100);
        assertEquals(25000, pago);
        pago = planillaService.pagoPorLeche("E",100);
        assertEquals(0, pago);
    }

    @Test
    void testPagoPorGrasa() {

        SubirValorEntity valor = new SubirValorEntity();
        valor.setPct_grasa("25");
        int pago = planillaService.pagoPorGrasa("A", 100, valor, 0);
        assertEquals(8000, pago);
        valor.setPct_grasa("15");
        pago = planillaService.pagoPorGrasa("A", 100, valor, 0);
        assertEquals(3000, pago);
        valor.setPct_grasa("50");
        pago = planillaService.pagoPorGrasa("A", 100, valor, 0);
        assertEquals(12000, pago);
        pago = planillaService.pagoPorGrasa("A", 100, valor, 1);
        assertEquals(50, pago);
    }


    @Test
    void testPagoPorST() {

        SubirValorEntity valor = new SubirValorEntity();
        valor.setPct_solido_total("6");
        int pago = planillaService.pagoPorST("A", 100, valor, 0);
        assertEquals(-13000, pago);
        valor.setPct_solido_total("10");
        pago = planillaService.pagoPorST("A", 100, valor, 0);
        assertEquals(-9000, pago);
        valor.setPct_solido_total("20");
        pago = planillaService.pagoPorST("A", 100, valor, 0);
        assertEquals(9500, pago);
        valor.setPct_solido_total("40");
        pago = planillaService.pagoPorST("A", 100, valor, 0);
        assertEquals(15000, pago);
        pago = planillaService.pagoPorST("A", 100, valor, 1);
        assertEquals(40, pago);
    }

    @Test
    void testPromklsLeche() {

        int promedio = planillaService.promklsLeche(1000, 10);
        assertEquals(100, promedio);
        promedio = planillaService.promklsLeche(1500, 30);
        assertEquals(50, promedio);
        promedio = planillaService.promklsLeche(2000, 20);
        assertEquals(100, promedio);
        promedio = planillaService.promklsLeche(2400, 24);
        assertEquals(100, promedio);
    }

    @Test
    void testVariacionLeche() {

        int variacion = planillaService.variacionLeche(0, 0, 1000);
        assertEquals(0, variacion);
        variacion = planillaService.variacionLeche(0, 500, 1000);
        assertEquals(100, variacion);
        variacion = planillaService.variacionLeche(0, 1000, 1500);
        assertEquals(50, variacion);
        variacion = planillaService.variacionLeche(0, 1000, 500);
        assertEquals(-50, variacion);
    }

    @Test
    void testVariacionGrasa() {

        int variacion = planillaService.variacionGrasa(0, 0, 20);
        assertEquals(0, variacion);
        variacion = planillaService.variacionGrasa(0, 10, 20);
        assertEquals(100, variacion);
        variacion = planillaService.variacionGrasa(0, 20, 30);
        assertEquals(50, variacion);
        variacion = planillaService.variacionGrasa(0, 20, 10);
        assertEquals(-50, variacion);
    }

    @Test
    void testVariacionST() {

        int variacion = planillaService.variacionST(0, 0, 30);
        assertEquals(0, variacion);
        variacion = planillaService.variacionST(0, 10, 20);
        assertEquals(100, variacion);
        variacion = planillaService.variacionST(0, 20, 30);
        assertEquals(50, variacion);
        variacion = planillaService.variacionST(0, 20, 10);
        assertEquals(-50, variacion);
    }

    @Test
    void testBonificacionFrec() {

        int bonificacion = planillaService.bonificacionFrec(1, 1, 0, 10000, 10);
        assertEquals(2000, bonificacion);
        bonificacion = planillaService.bonificacionFrec(1, 0, 0, 10000, 10);
        assertEquals(1200, bonificacion);
        bonificacion = planillaService.bonificacionFrec(0, 1, 0, 10000, 10);
        assertEquals(800, bonificacion);
        bonificacion = planillaService.bonificacionFrec(0, 0, 0, 10000, 10);
        assertEquals(0, bonificacion);
        bonificacion = planillaService.bonificacionFrec(1, 1, 0, 10000, 9);
        assertEquals(0, bonificacion);
    }

    @Test
    void testVariacionNegativaLeche() {

        int dctoVariacionLeche = planillaService.variacionNegativaLeche(-5, 0, 10000);
        assertEquals(0, dctoVariacionLeche);
        dctoVariacionLeche = planillaService.variacionNegativaLeche(-20, 0, 10000);
        assertEquals(700, dctoVariacionLeche);
        dctoVariacionLeche = planillaService.variacionNegativaLeche(-30, 0, 10000);
        assertEquals(1500, dctoVariacionLeche);
        dctoVariacionLeche = planillaService.variacionNegativaLeche(-50, 0, 10000);
        assertEquals(3000, dctoVariacionLeche);
    }

    @Test
    void testVariacionNegativaGrasa() {

        int dctoVariacionGrasa = planillaService.variacionNegativaGrasa(-10, 0, 10000);
        assertEquals(0, dctoVariacionGrasa);
        dctoVariacionGrasa = planillaService.variacionNegativaGrasa(-20, 0, 10000);
        assertEquals(700, dctoVariacionGrasa);
        dctoVariacionGrasa = planillaService.variacionNegativaGrasa(-30, 0, 10000);
        assertEquals(1500, dctoVariacionGrasa);
        dctoVariacionGrasa = planillaService.variacionNegativaGrasa(-50, 0, 10000);
        assertEquals(3000, dctoVariacionGrasa);
    }

    @Test
    void testVariacionNegativaST() {

        int dctoVariacionST = planillaService.variacionNegativaST(-5, 0, 10000);
        assertEquals(0, dctoVariacionST);
        dctoVariacionST = planillaService.variacionNegativaST(-10, 0, 10000);
        assertEquals(700, dctoVariacionST);
        dctoVariacionST = planillaService.variacionNegativaST(-20, 0, 10000);
        assertEquals(1500, dctoVariacionST);
        dctoVariacionST = planillaService.variacionNegativaST(-40, 0, 10000);
        assertEquals(3000, dctoVariacionST);
    }

    @Test
    void testImpuestoRetencion() {

        int montoRetencion = planillaService.impuestoRetencion("Si", 0, 950000);
        assertEquals(123500, montoRetencion);
        montoRetencion = planillaService.impuestoRetencion("No", 0, 1000000);
        assertEquals(0, montoRetencion);
        montoRetencion = planillaService.impuestoRetencion("Si", 0, 900000);
        assertEquals(0, montoRetencion);
    }

}
