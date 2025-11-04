package es.upm.miw.climahoy.models;

public class HistorialClimaConsultada {

    private String ciudad;
    private String pais;
    private double temperatura;
    private double viento;
    private String fechaConsulta;

    public HistorialClimaConsultada() {
    }

    public HistorialClimaConsultada(String ciudad, String pais, double temperatura, double viento, String fechaConsulta) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.temperatura = temperatura;
        this.viento = viento;
        this.fechaConsulta = fechaConsulta;
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
        return "🌍 " + ciudad + " (" + pais + ")\n" +
                "🌡 Temperatura: " + temperatura + "°C\n" +
                "💨 Viento: " + viento + " km/h\n" +
                "📅 Fecha: " + fechaConsulta + "\n";
    }

}
