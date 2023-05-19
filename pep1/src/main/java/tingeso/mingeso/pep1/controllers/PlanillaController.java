package tingeso.mingeso.pep1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.repositories.PlanillaRepository;
import tingeso.mingeso.pep1.repositories.SubirDataRepository;
import tingeso.mingeso.pep1.repositories.SubirValorRepository;
import tingeso.mingeso.pep1.services.ProveedorService;
import tingeso.mingeso.pep1.entities.PlanillaEntity;
import tingeso.mingeso.pep1.services.PlanillaService;
import tingeso.mingeso.pep1.entities.SubirDataEntity;
import tingeso.mingeso.pep1.services.SubirDataService;
import tingeso.mingeso.pep1.services.SubirValorService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaController {

    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private PlanillaService planillaService;
    @Autowired
    private SubirDataService dataService;
    @Autowired
    private SubirValorService valorService;
    @Autowired
    private SubirValorRepository subirValorRepository;
    @Autowired
    private SubirDataRepository dataRepository;

    //@GetMapping("/planillaPago")
    //public String main() {
        //return "planillaPago";
    //}

    @GetMapping("/lista-planilla")
    public String listarPlanilla(Model model) {

            ArrayList<PlanillaEntity> planillas = planillaService.obtenerPlanillas();
            model.addAttribute("planillas", planillas);
            return "planillaPago";

    }

    @GetMapping("/lista-planilla-calculo")
    public String listarPlanillaCalculo(Model model) {

            if (dataRepository.existsAny() && subirValorRepository.existsAny()) {
                System.out.println("\nHay valores!!\n");
                planillaService.calcularPlanillas();
            }

            ArrayList<PlanillaEntity> planillas = planillaService.obtenerPlanillas();
            model.addAttribute("planillas", planillas);
            return "planillaPago";

    }


    //@PostMapping("/calcular-planilla")
    //public String calcularPlanilla(){
    //    ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
    //    planillaService.calcularPagosAcopioLeche(proveedores);
    //    return "/lista-planilla";
    //}
}
