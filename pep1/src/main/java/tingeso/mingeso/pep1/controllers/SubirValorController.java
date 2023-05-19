package tingeso.mingeso.pep1.controllers;

import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.services.SubirValorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirValorController {

    @Autowired
    private SubirValorService subirValor;

    @GetMapping("/fileValorUpload")
    public String main() {
        return "fileValorUpload";
    }

    @PostMapping("/fileValorUpload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        subirValor.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");

        // Obtén el nombre del archivo cargado
        String uploadedFileName = file.getOriginalFilename();

        // Pasa el nombre del archivo al método leerCsv()
        subirValor.leerCsv(uploadedFileName);

        return "redirect:/fileValorUpload";
    }


    @GetMapping("/fileValorInformation")
    public String listar(Model model) {
        ArrayList<SubirValorEntity> valores = subirValor.obtenerValor();
        model.addAttribute("valores", valores);
        return "fileValorInformation";
    }
}
