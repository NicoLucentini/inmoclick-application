package inmoclick.inmoclick;

import inmoclick.service.InmoclickConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LotesLoader extends Thread {
    @Autowired
    private InmoclickConsumer asdconsumer;
    public void run() {
        asdconsumer.listLotes();
        System.out.println("This code is running in a thread");
    }
}