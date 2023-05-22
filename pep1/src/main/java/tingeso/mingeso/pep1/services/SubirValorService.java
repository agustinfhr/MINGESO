package tingeso.mingeso.pep1.services;

import tingeso.mingeso.pep1.entities.SubirValorEntity;
import tingeso.mingeso.pep1.repositories.SubirValorRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class SubirValorService {

    @Autowired
    private SubirValorRepository valorRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirValorService.class);

    public ArrayList<SubirValorEntity> obtenerValor(){
        return (ArrayList<SubirValorEntity>) valorRepository.findAll();
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        // Comenta o elimina la siguiente línea para no eliminar los registros previos
        valorRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarValorDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarValor(SubirValorEntity valor){
        valorRepository.save(valor);
    }

    public void guardarValorDB(String proveedor, String pct_grasa, String pct_solido_total){
        SubirValorEntity newValor = new SubirValorEntity();
        newValor.setProveedor(proveedor);
        newValor.setPct_grasa(pct_grasa);
        newValor.setPct_solido_total(pct_solido_total);
        guardarValor(newValor);
    }

}
