package twitter.inmoclick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general")
public class InmoclickController {

    @Autowired
    private InmoclickConsumer consumer;

    @GetMapping("/tincho")
    public ResponseEntity listLotes(){
        try {
            return  ResponseEntity.status(200).body("Hola Gato");
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
