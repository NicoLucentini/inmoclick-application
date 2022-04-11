package inmoclick.controller;

import inmoclick.service.InmoclickConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/casas")
public class CasasController {
    @Autowired
    private InmoclickConsumer consumer;

    @GetMapping("/list")
    public ResponseEntity listCasas(){
        try {
            var res = consumer.casas;
            return  ResponseEntity.status(200).body(res);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
