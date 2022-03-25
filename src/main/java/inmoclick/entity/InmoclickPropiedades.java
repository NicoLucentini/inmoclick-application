package inmoclick.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InmoclickPropiedades implements Serializable {

    //public List<InmoclickPropiedad> propiedades = new ArrayList<InmoclickPropiedad>();

    public Map<String, InmoclickPropiedad> propiedades = new HashMap<String, InmoclickPropiedad>();
    public InmoclickPropiedades(){}
}