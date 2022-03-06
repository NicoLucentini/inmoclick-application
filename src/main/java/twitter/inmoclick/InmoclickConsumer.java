package twitter.inmoclick;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.valueOf;


@Service
public class InmoclickConsumer {


    private String urlLotes ="https://www.inmoclick.com.ar/inmuebles/lotes-y-terrenos-en-venta?favoritos=0&limit=2791&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=&precio_pesos_m2%5Bmin%5D=&precio_pesos_m2%5Bmax%5D=&precio_dolares_m2%5Bmin%5D=&precio_dolares_m2%5Bmax%5D=&expensas%5Bmin%5D=&expensas%5Bmax%5D=";
    private String urlCasas = "https://www.inmoclick.com.ar/inmuebles/casas-en-venta?favoritos=0&limit=10000&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=";
    private String urlDepartamentos = "https://www.inmoclick.com.ar/inmuebles/departamentos-en-venta?favoritos=0&limit=10000&prevEstadoMap=&q=Mendoza&lastZoom=13&precio%5Bmin%5D=&precio%5Bmax%5D=&moneda=1&sup_cubierta%5Bmin%5D=&sup_cubierta%5Bmax%5D=&sup_total%5Bmin%5D=&sup_total%5Bmax%5D=";


    public List<InmoclickPropiedad> casas = new ArrayList<>();

    public void LoadValues(){


        casas.clear();
        long start1 = System.currentTimeMillis();
        System.out.println("Start loading values");
        casas = listCasas();


        System.out.println("Finish loading values");
        long end = System.currentTimeMillis();

        System.out.println("Elapsed Time in milli seconds: "+ (end-start1));
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

}
