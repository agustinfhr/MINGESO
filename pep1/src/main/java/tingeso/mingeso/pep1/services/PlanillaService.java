package tingeso.mingeso.pep1.services;

import lombok.Generated;
import org.springframework.stereotype.Service;
import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.entities.PlanillaEntity;
import tingeso.mingeso.pep1.entities.SubirDataEntity;
import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.repositories.SubirDataRepository;
import tingeso.mingeso.pep1.repositories.SubirValorRepository;
import tingeso.mingeso.pep1.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PlanillaService {

    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private SubirDataRepository dataRepository;
    @Autowired
    private PlanillaRepository planillaRepository;
    @Autowired
    private SubirValorRepository valorRepository;


    @Generated
    public void calcularPlanillas(){

        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();

        // VARIABLES FINALES
        String quincena = " ";
        String codigoProveedor;
        String nombreProveedor;

        // VARIABLES AUX
        String fechaAux;
        int dd = 0;
        int mm = 0;
        int yyyy = 0;
        int saveFecha = 0;
        int quincenaFecha = 0;
        int variacionLecheAnterior = 0;
        int variacionGrasaAnterior = 0;
        int variacionSolidosTotalesAnterior = 0;
        String auxMT;

        for (ProveedorEntity proveedor : proveedores) {

            int totalKlsLeche = 0;
            int pagoLeche = 0;
            int pctGrasa = 0;
            int pagoGrasa = 0;
            int pctSolidosTotales = 0;
            int pagoSolidosTotales = 0;
            int nroDiasEnvioLeche = 0;
            int promDiarioKlsLeche = 0;
            int pctVariacionLeche = 0;
            int pctVariacionGrasa = 0;
            int pctVariacionST = 0;
            int manana = 0;
            int tarde = 0;
            int bonificacionFrecuencia = 0;
            int pagoAcopioLeche = 0;
            int dctoVariacionLeche = 0;
            int dctoVariacionGrasa = 0;
            int dctoVariacionST = 0;
            int descuentos = 0;
            int pagoTotal = 0;
            int montoRetencion = 0;
            int montoFinal = 0;

            saveFecha = 0;
            quincenaFecha = 0;
            codigoProveedor = proveedor.getCodigo();
            nombreProveedor = proveedor.getNombre();
            String categoriaProveedor = proveedor.getCategoria();
            String retencion = proveedor.getRetencion();

            ArrayList<SubirDataEntity> datas = dataRepository.findAllByCodigoOrderByDateAsc(codigoProveedor);
            SubirValorEntity valor = valorRepository.findByProveedor(codigoProveedor);

            if (planillaRepository.existsAny(codigoProveedor)) {
                ArrayList<PlanillaEntity> planillas = planillaRepository.findByCodigo_proveedorOrderByQuincenaDesc(codigoProveedor);
                PlanillaEntity firstPlanilla = planillas.get(0);
                variacionLecheAnterior = firstPlanilla.getTotal_kls_leche();
                variacionGrasaAnterior = firstPlanilla.getPct_grasa();
                variacionSolidosTotalesAnterior = firstPlanilla.getPct_solidos_totales();

            }

            for (SubirDataEntity data : datas) {
                fechaAux = data.getFecha();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(fechaAux, formatter);
                dd = localDate.getDayOfMonth();
                mm = localDate.getMonthValue();
                yyyy = localDate.getYear();

                if (saveFecha == 0) {

                    saveFecha = 1; // Se guarda la primera fecha
                    if (dd <= 15) {
                        quincenaFecha = 15;

                    } else {
                        quincenaFecha = 31;

                    }

                    quincena = String.format("%d-%02d-%02d", yyyy, mm, quincenaFecha);
                }

                    totalKlsLeche = klsLeche(totalKlsLeche, data);
                    pagoLeche = pagoPorLeche(categoriaProveedor, totalKlsLeche);
                    pctGrasa = pagoPorGrasa(totalKlsLeche, valor, 1);
                    pagoGrasa = pagoPorGrasa(totalKlsLeche, valor, 0);
                    pctSolidosTotales = pagoPorST(totalKlsLeche, valor, 1);
                    pagoSolidosTotales = pagoPorST(totalKlsLeche, valor, 0);
                    nroDiasEnvioLeche = nroDiasEnvioLeche + 1;
                    promDiarioKlsLeche = promklsLeche(totalKlsLeche, nroDiasEnvioLeche);
                    pctVariacionLeche = variacionLeche(variacionLecheAnterior, totalKlsLeche);
                    pctVariacionGrasa = variacionGrasa(variacionGrasaAnterior, pctGrasa);
                    pctVariacionST = variacionST(variacionSolidosTotalesAnterior, pctSolidosTotales);

                    auxMT = data.getTurno();

                    if (auxMT.equals("M")) {
                        manana = 1;
                    } else {
                        tarde = 1;
                    }

                    bonificacionFrecuencia = bonificacionFrec(manana, tarde, pagoLeche, nroDiasEnvioLeche);
                    pagoAcopioLeche = pagoLeche + pagoGrasa + pagoSolidosTotales + bonificacionFrecuencia;
                    dctoVariacionLeche = variacionNegativaLeche(pctVariacionLeche, pagoAcopioLeche);
                    dctoVariacionGrasa = variacionNegativaGrasa(pctVariacionGrasa, pagoAcopioLeche);
                    dctoVariacionST = variacionNegativaST(pctVariacionST, pagoAcopioLeche);
                    descuentos = dctoVariacionLeche + dctoVariacionGrasa + dctoVariacionST;
                    pagoTotal = pagoAcopioLeche - descuentos;
                    montoRetencion = impuestoRetencion(retencion, pagoTotal);
                    montoFinal = pagoTotal - montoRetencion;

            }

                PlanillaEntity planilla = new PlanillaEntity();
                planilla.setQuincena(quincena);
                planilla.setCodigo_proveedor(codigoProveedor);
                planilla.setNombre_proveedor(nombreProveedor);
                planilla.setTotal_kls_leche(totalKlsLeche);
                planilla.setPago_por_leche(pagoLeche);
                planilla.setPct_grasa(pctGrasa);
                planilla.setPago_por_grasa(pagoGrasa);
                planilla.setPct_solidos_totales(pctSolidosTotales);
                planilla.setPago_por_solidos_totales(pagoSolidosTotales);
                planilla.setNro_dias_envio_leche(nroDiasEnvioLeche);
                planilla.setPromedio_diario_kls_leche(promDiarioKlsLeche);
                planilla.setPct_variacion_leche(pctVariacionLeche);
                planilla.setPct_variacion_grasa(pctVariacionGrasa);
                planilla.setPct_variacion_st(pctVariacionST);
                planilla.setBonificacion_frecuencia(bonificacionFrecuencia);
                planilla.setDcto_variacion_leche(dctoVariacionLeche);
                planilla.setDcto_variacion_grasa(dctoVariacionGrasa);
                planilla.setDcto_variacion_st(dctoVariacionST);
                planilla.setPago_total(pagoTotal);
                planilla.setMonto_retencion(montoRetencion);
                planilla.setMonto_final(montoFinal);

                planillaRepository.save(planilla);

        }

        dataRepository.deleteAll();
        valorRepository.deleteAll();

    }
    @Generated
    public ArrayList<PlanillaEntity> obtenerPlanillas(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }

    public int klsLeche(Integer totalKlsLeche, SubirDataEntity data){

        Integer totalKlsLecheResultado;
        String dataKlsProveedor = data.getKls_leche();
        totalKlsLecheResultado = totalKlsLeche + Integer.parseInt(dataKlsProveedor);
        return totalKlsLecheResultado;
    }
    public int pagoPorLeche(String categoriaProveedor, Integer klsLecheProveedor){

        switch(categoriaProveedor){
            case "A":
                return (klsLecheProveedor * 700);
            case "B":
                return (klsLecheProveedor * 550);
            case "C":
                return (klsLecheProveedor * 400);
            case "D":
                return (klsLecheProveedor * 250);
            default:
                return 0;
        }
    }

    public int pagoPorGrasa(Integer klsLecheProveedor, SubirValorEntity valor, Integer interruptor){

        int pctGrasa = 0;

        pctGrasa = Integer.parseInt(valor.getPct_grasa());

        if (interruptor == 1) {
            return pctGrasa;
        } else if (pctGrasa >= 0 && pctGrasa <= 20) {
                return (klsLecheProveedor * 30);
        } else if (pctGrasa >= 21 && pctGrasa <= 45) {
            return (klsLecheProveedor * 80);
        } else if (pctGrasa >= 46) {
            return (klsLecheProveedor * 120);
        } else {
            return 0;
        }
    }

    public int pagoPorST(Integer klsLecheProveedor, SubirValorEntity valor, Integer interruptor){

        int pctST = 0;
        pctST = Integer.parseInt(valor.getPct_solido_total());

        if (interruptor == 1) {
            return pctST;
        } else if (pctST >= 0 && pctST <= 7) {
            return (klsLecheProveedor * -130);
        } else if (pctST >= 8 && pctST <= 18) {
            return (klsLecheProveedor * -90);
        } else if (pctST >= 19 && pctST <= 35) {
            return (klsLecheProveedor * 95);
        } else if (pctST >= 36) {
            return (klsLecheProveedor * 150);
        } else {
            return 0;
        }
    }

    public int promklsLeche(Integer totalKlsLeche, Integer nroDiasEnvioLeche){

        int promedio = 0;
        promedio = totalKlsLeche / nroDiasEnvioLeche;
        return promedio;
    }

    public int variacionLeche(Integer variacionLecheAnterior, Integer totalKlsLeche){

        if (variacionLecheAnterior == 0) {
            return 0;
        } else {
            int pctVariacionLeche = ((totalKlsLeche * 100) / variacionLecheAnterior) - 100;

            return pctVariacionLeche;
        }


    }

    public int variacionGrasa(Integer variacionGrasaAnterior, Integer pctGrasa){

        if (variacionGrasaAnterior == 0) {
            return 0;
        } else {
            int pctVariacionGrasa = ((pctGrasa * 100) / variacionGrasaAnterior) - 100;

            return pctVariacionGrasa;
        }


    }

    public int variacionST(Integer variacionSolidosTotalesAnterior, Integer pctSolidosTotales){

        if (variacionSolidosTotalesAnterior == 0) {
            return 0;
        } else {
            int pctVariacionST = ((pctSolidosTotales * 100) / variacionSolidosTotalesAnterior) - 100;

            return pctVariacionST;
        }


    }

    public int bonificacionFrec(Integer manana, Integer tarde, Integer pagoLeche, Integer nroDiasEnvioLeche) {

        if (nroDiasEnvioLeche >= 10) {
            if (manana == 1 && tarde == 1) {
                int bonificacionFrecuencia = (pagoLeche * 20) / 100;
                return bonificacionFrecuencia;
            } else if (manana == 1 && tarde == 0) {
                int bonificacionFrecuencia = (pagoLeche * 12) / 100;
                return bonificacionFrecuencia;
            } else if (manana == 0 && tarde == 1) {
                int bonificacionFrecuencia = (pagoLeche * 8) / 100;
                return bonificacionFrecuencia;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    public int variacionNegativaLeche(Integer pctVariacionLeche, Integer pagoAcopioLeche){

        if (pctVariacionLeche >= -8) {
            int dctoVariacionLeche = 0;
            return dctoVariacionLeche;
        } else if (pctVariacionLeche >= -25 && pctVariacionLeche <= -9) {
            int dctoVariacionLeche = (pagoAcopioLeche * 7) / 100;
            return dctoVariacionLeche;
        } else if (pctVariacionLeche >= -45 && pctVariacionLeche <= -26) {
            int dctoVariacionLeche = (pagoAcopioLeche * 15) / 100;
            return dctoVariacionLeche;
        } else if (pctVariacionLeche <= -46) {
            int dctoVariacionLeche = (pagoAcopioLeche * 30) / 100;
            return dctoVariacionLeche;
        } else {
            return 0;
        }

    }

    public int variacionNegativaGrasa(Integer pctVariacionGrasa, Integer pagoAcopioLeche){

        if (pctVariacionGrasa >= -15) {
            int dctoVariacionGrasa = 0;
            return dctoVariacionGrasa;
        } else if (pctVariacionGrasa >= -25 && pctVariacionGrasa <= -16) {
            int dctoVariacionGrasa = (pagoAcopioLeche * 7) / 100;
            return dctoVariacionGrasa;
        } else if (pctVariacionGrasa >= -40 && pctVariacionGrasa <= -26) {
            int dctoVariacionGrasa = (pagoAcopioLeche * 15) / 100;
            return dctoVariacionGrasa;
        } else if (pctVariacionGrasa <= -41) {
            int dctoVariacionGrasa = (pagoAcopioLeche * 30) / 100;
            return dctoVariacionGrasa;
        } else {
            return 0;
        }

    }

    public int variacionNegativaST(Integer pctVariacionST, Integer pagoAcopioLeche){

        if (pctVariacionST >= -6) {

            return 0;
        } else if (pctVariacionST >= -12 && pctVariacionST <= -7) {

            return ((pagoAcopioLeche * 7) / 100);
        } else if (pctVariacionST >= -35 && pctVariacionST <= -13) {

            return ((pagoAcopioLeche * 15) / 100);
        } else if (pctVariacionST <= -36) {

            return ((pagoAcopioLeche * 30) / 100);
        } else {

            return 0;
        }

    }

    public int impuestoRetencion(String retencion, Integer pagoTotal){

        if (retencion.equals("Si")) {

            if (pagoTotal >= 950000) {

                return ((pagoTotal * 13) / 100);
            } else {

                return 0;
            }
        } else {

            return 0;
        }

    }

}