package inmoclick.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "propiedades")
public class PropiedadEntity{
    public PropiedadEntity(){}

    @Id
    @Column
    public Long id;
    @Column
    public Integer propiedad_id;
    @Column
    public String url;
    @Column
    public String tip_desc;
    @Column
    public String con_desc;
    @Column
    public String loc_desc;
    @Column
    public String pro_desc;
    @Column
    public String prp_dom;
    @Column
    public Integer superficie_total;
    @Column
    public Integer superficie_cubierta;
    @Column
    public Integer prp_pre_dol;
    @Column
    public Integer oportunidad_dolares;
    @Column
    public String agua;
    @Column
    public String luz;
    @Column
    public String gas;
    @Column
    public String baños;
    @Column
    public String cochera;
    @Column
    public String dormitorios;
    @Column
    public String prp_lat;
    @Column
    public String prp_lng;
    @Column
    public String prp_alta;
    @Column
    public String prp_mod;
    @Column
    public String usuario_id;
    @Column
    public String nombre;
    @Column
    public int nueva;
    @Column
    public int activa;

    public static PropiedadEntity from(InmoclickPropiedad prop){
        PropiedadEntity entity  = new PropiedadEntity();
        entity.id = prop.id;
        entity.propiedad_id = prop.propiedad_id;
        entity.url = prop.url;
        entity.tip_desc = prop.tip_desc;
        entity.con_desc = prop.con_desc;
        entity.loc_desc = prop.loc_desc;
        entity.pro_desc = prop.pro_desc;
        entity.prp_dom = prop.prp_dom;
        entity.superficie_total = prop.superficie_total;
        entity.superficie_cubierta = prop.superficie_cubierta;
        entity.prp_pre_dol = prop.prp_pre_dol;
        entity.oportunidad_dolares = prop.oportunidad_dolares;
        entity.agua = prop.agua;
        entity.luz = prop.luz;
        entity.gas = prop.gas;
        entity.baños = prop.baños;
        entity.cochera = prop.cochera;
        entity.dormitorios = prop.dormitorios;
        entity.prp_lat = prop.prp_lat;
        entity.prp_lng = prop.prp_lng;
        entity.prp_alta = prop.prp_alta;
        entity.prp_mod = prop.prp_mod;
        entity.usuario_id = prop.usuario_id;
        entity.nombre = prop.nombre;
        entity.nueva = prop.nueva;
        entity.activa = prop.activa;

        return entity;
    }
    public InmoclickPropiedad to(){
        InmoclickPropiedad entity  = new InmoclickPropiedad();
        entity.id = this.id;
        entity.propiedad_id = this.propiedad_id;
        entity.url = this.url;
        entity.tip_desc = this.tip_desc;
        entity.con_desc = this.con_desc;
        entity.loc_desc = this.loc_desc;
        entity.pro_desc = this.pro_desc;
        entity.prp_dom = this.prp_dom;
        entity.superficie_total = this.superficie_total;
        entity.superficie_cubierta = this.superficie_cubierta;
        entity.prp_pre_dol = this.prp_pre_dol;
        entity.oportunidad_dolares = oportunidad_dolares;
        entity.agua = this.agua;
        entity.luz = this.luz;
        entity.gas = this.gas;
        entity.baños = this.baños;
        entity.cochera = this.cochera;
        entity.dormitorios = this.dormitorios;
        entity.prp_lat = this.prp_lat;
        entity.prp_lng = this.prp_lng;
        entity.prp_alta = this.prp_alta;
        entity.prp_mod = this.prp_mod;
        entity.usuario_id = this.usuario_id;
        entity.nombre = this.nombre;
        entity.nueva = this.nueva;
        entity.activa = this.activa;
        return entity;
    }
}
