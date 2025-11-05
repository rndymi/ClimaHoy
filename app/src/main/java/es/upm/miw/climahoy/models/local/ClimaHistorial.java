package es.upm.miw.climahoy.models.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ClimaHistorial.TABLA)
public class ClimaHistorial {

    static public final String TABLA = "climahistoriales";

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    private String ciudad;
    private String pais;
    private double temperatura;
    private double viento;
    private String fechaConsulta;

    public ClimaHistorial() {
    }

    public ClimaHistorial(String ciudad, String pais, double temperatura, double viento, String fechaConsulta) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.temperatura = temperatura;
        this.viento = viento;
        this.fechaConsulta = fechaConsulta;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    @Override
    public String toString() {
        return "ClimaHistorial{" +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", temperatura=" + temperatura +
                ", viento=" + viento +
                ", fechaConsulta='" + fechaConsulta + '\'' +
                '}';
    }
}
