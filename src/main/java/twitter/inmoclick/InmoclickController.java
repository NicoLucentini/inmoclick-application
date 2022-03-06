package twitter.inmoclick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import twitter.users.DuplicateUserException;
import twitter.users.User;

@RestController
@RequestMapping("/casas")
public class InmoclickController {

    @Autowired
    private InmoclickConsumer consumer;


    @GetMapping("/list")
    public ResponseEntity listCasas(){
        try {
            //var res = consumer.listCasas();
            var res = consumer.casas;
            return  ResponseEntity.status(200).body(res);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
