package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.TipoDeMenu;

public class MainController{
    protected EscenaPrincipal escena;
    protected Object parametros;
    protected TipoDeMenu tipoMenu;
    
    
    public void setEscena(EscenaPrincipal escena) {
        this.escena = escena;
    }
    
    public void setParametros(Object args){
        parametros = args;
    }
    public void setTipoMenu(TipoDeMenu nenu) {
        tipoMenu = nenu;
    }
    
}
