package inmoclick;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import inmoclick.consumer.InmoclickConsumer;
import inmoclick.entity.InmoclickPropiedad;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class InmoclickServiceTest {

    @Autowired
    private InmoclickConsumer consumer;

    @Test
    public  void testConsumerLotes(){

       List<InmoclickPropiedad> propiedades =  consumer.listLotes();
       Assert.assertTrue(!propiedades.isEmpty());
    }
    @Test
    public  void testConsumerCasas(){

        List<InmoclickPropiedad> propiedades =  consumer.listCasas();
        Assert.assertTrue(!propiedades.isEmpty());
    }
    @Test
    public  void testConsumerDepartamentos(){

        List<InmoclickPropiedad> propiedades =  consumer.listDepartamentos();
        Assert.assertTrue(!propiedades.isEmpty());
    }
}
