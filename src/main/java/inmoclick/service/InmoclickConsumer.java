package inmoclick.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import inmoclick.entity.InmoclickPropiedad;
import inmoclick.entity.InmoclickPropiedades;
import inmoclick.entity.PropiedadEntity;
import inmoclick.repository.PropiedadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.valueOf;


@Service
public class InmoclickConsumer {

    public static final int count = 10000;

    private String urlLotes ="https://www.inmoclick.com.ar/inmuebles/lotes-y-terrenos-en-venta?favoritos=0&limit="+count+"&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=&precio_pesos_m2%5Bmin%5D=&precio_pesos_m2%5Bmax%5D=&precio_dolares_m2%5Bmin%5D=&precio_dolares_m2%5Bmax%5D=&expensas%5Bmin%5D=&expensas%5Bmax%5D=";
    private String urlCasas = "https://www.inmoclick.com.ar/inmuebles/casas-en-venta?favoritos=0&limit="+count+"&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=";
    private String urlDepartamentos = "https://www.inmoclick.com.ar/inmuebles/departamentos-en-venta?favoritos=0&limit="+count+"&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=";
    private String urlAlquilerDepartamentos = "https://www.inmoclick.com.ar/inmuebles/departamentos-en-alquiler?favoritos=0&limit="+count+"&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&expensas%5Bmin%5D=&expensas%5Bmax%5D=";

    public List<InmoclickPropiedad> casas = new ArrayList<>();
    public List<InmoclickPropiedad> dptos = new ArrayList<>();
    public List<InmoclickPropiedad> lotes = new ArrayList<>();
    public List<InmoclickPropiedad> dptosAlquiler = new ArrayList<>();

    @Autowired
    private PropiedadesRepository repository;

    public void LoadValues(){


        casas.clear();
        lotes.clear();
        dptos.clear();
        dptosAlquiler.clear();

        long start1 = System.currentTimeMillis();

        System.out.println("Start loading values");
        casas = listCasas();
        dptos = listDepartamentos();
        lotes = listLotes();
        dptosAlquiler = listDptosAlquiler();

        System.out.println("Finish loading values");
        long end = System.currentTimeMillis();

        System.out.println("Elapsed Time loading values: "+ (end-start1) + " ms");


        start1 = System.currentTimeMillis();
        System.out.println("Start saving values");
        doMagic();
        System.out.println("End saving values");
        end = System.currentTimeMillis();
        System.out.println("Elapsed Time saving values : "+ (end-start1) + " ms");

    }

    private Integer getAmount(String response){
        var split1 = response.split("h2");
        var lotes = split1[1].split("h2");
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(lotes[0]);
        while(m.find()) {
            return valueOf(m.group());
        }
        return -1;
    }

    private String getPropiedadesFromString(String response){
        var split = response.split("propiedades = ");
        var split2 = split[1].split("}}};");
        return split2[0] + "}}}";
    }

    private String cleanJsonToMakeItArray(String finalString, Integer amount){
        //Evitar esto luego

        for(int i = 0; i< amount +1;i++){
            finalString = finalString.replaceFirst("\""+i +"\":","");
        }



        finalString = finalString.substring(1, finalString.length() -1);

        finalString = "[" + finalString + "]";
        return finalString;
    }

    private List<InmoclickPropiedad> consumePage(String url){

        long start1 = System.currentTimeMillis();
        RestTemplate rest = new RestTemplate();
        String response = rest.getForObject(url, String.class);
        long end = System.currentTimeMillis();

        System.out.println("Elapsed Time in milli seconds: "+ (end-start1));

        Integer amountFromPage = getAmount(response);
        System.out.println("AmountFromPage: " + amountFromPage);

        String finalString = getPropiedadesFromString(response);

        finalString = cleanJsonToMakeItArray(finalString, amountFromPage);

        try {
            InmoclickPropiedad[] props = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(finalString, InmoclickPropiedad[].class);
            System.out.println(props.length);
            for(int i = 0; i< props.length;i++)
                props[i].doUrl();
            return Arrays.asList(props);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
       return new ArrayList<InmoclickPropiedad>();
    }

    public List<InmoclickPropiedad> listLotes(){
        return consumePage(urlLotes);
    }

    public List<InmoclickPropiedad> listCasas(){
        return consumePage(urlCasas);
    }

    public List<InmoclickPropiedad> listDepartamentos(){
        return consumePage(urlDepartamentos);
    }

    public List<InmoclickPropiedad> listDptosAlquiler() {return consumePage(urlAlquilerDepartamentos);}

    public void doMagic() {

        //old database
        List<PropiedadEntity> olds = repository.findAll();


        List<InmoclickPropiedad> allProps = new ArrayList<InmoclickPropiedad>();

        allProps.addAll(casas);
        allProps.addAll(lotes);
        allProps.addAll(dptos);
        allProps.addAll(dptosAlquiler);

        for(PropiedadEntity old : olds){
            boolean exists = allProps.stream().anyMatch(x->x.id.equals(old.id));

            if(!exists)
            {
                old.activa = 0;
                repository.save(old);
            }
        }


        for (InmoclickPropiedad prop : allProps){
            boolean exists = repository.existsById(prop.id);
            PropiedadEntity e = PropiedadEntity.from(prop);
            e.nueva = exists ? 0 : 1;
            prop.nueva = e.nueva;
            prop.activa = e.activa;
            repository.save(e);
        }
    }


    public List<InmoclickPropiedad> getRemoveds(String filter, List<InmoclickPropiedad> oldProperties){
        List<InmoclickPropiedad> removeds = new ArrayList<>();

        //Las removidas son las que antes estaban el old y ya no estan mas en new

        List<InmoclickPropiedad> propiedadesActuales = new ArrayList<>();

        switch (filter){
            case "casas" : { propiedadesActuales = casas;break;}
            case "lotes" :{ propiedadesActuales = lotes;break;}
            case "departamentos" : { propiedadesActuales = dptos;break;}
            case "departamentosAlquiler" : {propiedadesActuales = dptosAlquiler;break;}
        }


        boolean hasToAdd = true;
        for(InmoclickPropiedad old :oldProperties){
            hasToAdd = true;
            for(InmoclickPropiedad newOne : propiedadesActuales)
            {
                //Significa que sigue estando la propiedad
                if(old.isEqual(newOne))
                {
                    hasToAdd = false;
                    break;
                }
            }

            if(hasToAdd)
                removeds.add(old);
        }
        return removeds;
    }

    public List<InmoclickPropiedad> getNewOnes(String filter, List<InmoclickPropiedad> oldProperties){
        List<InmoclickPropiedad> newOnes = new ArrayList<>();

        //Las nuevas son las que estan en departamentos pero que no estaban en oldProperties

        List<InmoclickPropiedad> propiedadesActuales = new ArrayList<>();

        switch (filter){
            case "casas" : { propiedadesActuales = casas;break;}
            case "lotes" :{ propiedadesActuales = lotes;break;}
            case "departamentos" : { propiedadesActuales = dptos;break;}
            case "departamentosAlquiler" : {propiedadesActuales = dptosAlquiler;break;}
        }


        boolean hasToAdd = true;
        for(InmoclickPropiedad newOne : propiedadesActuales)
        {
            hasToAdd = true;
            for(InmoclickPropiedad old :oldProperties){
                //Significa que sigue estando la propiedad
                if(old.isEqual(newOne)) {
                    hasToAdd = false;
                    break;
                }
            }
            if(hasToAdd) {
                newOnes.add(newOne);
            }
        }
        return newOnes;
    }

}
