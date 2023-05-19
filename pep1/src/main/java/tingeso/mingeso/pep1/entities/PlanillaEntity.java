package tingeso.mingeso.pep1.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planillas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanillaEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String quincena;
    private String codigo_proveedor;
    private String nombre_proveedor;
    private Integer total_kls_leche;
    private Integer nro_dias_envio_leche;
    private Integer promedio_diario_kls_leche;
    private Integer pct_variacion_leche;
    private Integer pct_grasa;
    private Integer pct_variacion_grasa;
    private Integer pct_solidos_totales;
    private Integer pct_variacion_st;
    private Integer pago_acopio_leche;
    private Integer pago_por_leche;
    private Integer pago_por_grasa;
    private Integer pago_por_solidos_totales;
    private Integer bonificacion_frecuencia;
    private Integer dcto_variacion_leche;
    private Integer dcto_variacion_grasa;
    private Integer dcto_variacion_st;
    private Integer pago_total;
    private Integer monto_retencion;
    private Integer monto_final;

}
