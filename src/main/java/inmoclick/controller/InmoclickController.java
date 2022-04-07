package inmoclick.controller;

import inmoclick.consumer.InmoclickConsumer;
import inmoclick.entity.InmoclickPropiedad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general")
public class InmoclickController {

    @Autowired
    private InmoclickConsumer consumer;

    @GetMapping("/welcome")
    public ResponseEntity welcome(){
        try {
            return  ResponseEntity.status(200).body("Hola Gato");
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/refresh")
    public ResponseEntity refresh(){
        try {
            consumer.LoadValues();
            return  ResponseEntity.status(200).body("Listo pa, refresheada la data ya le podes pegar a la otra url");
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/eliminadas/{filter}")
    public ResponseEntity getDifference(@PathVariable String filter, @RequestBody List<InmoclickPropiedad> propiedades){
        try {
            var res = consumer.getRemoveds(filter, propiedades);
            return  ResponseEntity.status(200).body(res);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/nuevas/{filter}")
    public ResponseEntity getNuevas(@PathVariable String filter, @RequestBody List<InmoclickPropiedad> propiedades){
        try {
            var res = consumer.getNewOnes(filter, propiedades);
            return  ResponseEntity.status(200).body(res);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
