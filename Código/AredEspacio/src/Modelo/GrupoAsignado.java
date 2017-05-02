package Modelo;

import java.util.ArrayList;
import java.util.List;

public class GrupoAsignado {
    private final Integer id;
    private final String salon;
    private final String tipoDeDanza;
    private final String nivel;
    private final String dia;
    private final String hora;

    public GrupoAsignado(Integer id, String salon, String tipoDeDanza, String nivel, String dia, String hora) {
        this.id = id;
        this.salon = salon;
        this.tipoDeDanza = tipoDeDanza;
        this.nivel = nivel;
        this.dia = dia;
        this.hora = hora;
    }
    
    public Integer getId() {
        return id;
    }

    public String getSalon() {
        return salon;
    }

    public String getTipoDeDanza() {
        return tipoDeDanza;
    }

    public String getNivel() {
        return nivel;
    }
    
    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }
    
    public static List<GrupoAsignado> obtenerGruposAsignado(Maestro maestro){
        List<GrupoAsignado> grupos = new ArrayList<>();
        
        /*for(Grupo grupo : maestro.getGrupoList()){
            for(Horario horario : grupo.getHorarioList()){
                grupos.add(
                    new GrupoAsignado(grupo.getIdGrupo(),grupo.getSalon(),grupo.getTipoDeDanza()
                            ,horario.getDia(),horario.getHora()
                    )
                );
            }
        }
        */
        for(Grupo grupo : maestro.getGrupoList()){
            String dia = "";
            String hora= "";
            for(Horario horario : grupo.getHorarioList()){
               dia+=horario.getDia()+"\n";
               hora+=horario.getHora()+"\n";
            }
            grupos.add(new GrupoAsignado(grupo.getIdGrupo(),grupo.getSalon()
                    ,grupo.getTipoDeDanza(), grupo.getNivel(),dia,hora));
        }
        
        return grupos;
    }
    
}
