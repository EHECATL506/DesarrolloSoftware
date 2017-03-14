package GUI.Controllers;

import GUI.EscenaPrincipal;
import Interfaces.ControlDeEscena;
import Modelo.Enumeradores.TipoMenu;

public class MainController implements ControlDeEscena{
    protected EscenaPrincipal escena;
    protected String parametros;
    protected TipoMenu tipoMenu;
    
    @Override
    public void setEscena(EscenaPrincipal escena) {
        this.escena=escena;
    }
    public void setParametros(String parametros){
        this.parametros=parametros;
    }
    public void setTipoMenu(TipoMenu tipoMenu) {
        this.tipoMenu = tipoMenu;
    }
}
