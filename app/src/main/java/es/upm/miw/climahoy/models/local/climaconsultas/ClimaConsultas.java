package es.upm.miw.climahoy.models.local.climaconsultas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ClimaConsultas.TABLA)
public class ClimaConsultas {

    static public final String TABLA = "climahistoriales";

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    private String ciudad;
    private String pais;
    private double temperatura;
    private double viento;
    public String descripcionClima;
    private String fechaConsulta;

    public ClimaConsultas() {
    }

    public ClimaConsultas(String ciudad, String pais, double temperatura, double viento, String descripcionClima, String fechaConsulta) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.temperatura = temperatura;
        this.viento = viento;
        this.descripcionClima = descripcionClima;
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

    public String getDescripcionClima() {
        return descripcionClima;
    }

    public void setDescripcionClima(String descripcionClima) {
        this.descripcionClima = descripcionClima;
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
                ", descripcionClima='" + descripcionClima + '\'' +
                ", fechaConsulta='" + fechaConsulta + '\'' +
                '}';
    }

}
