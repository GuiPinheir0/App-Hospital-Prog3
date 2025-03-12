
package prog3.appbackend.models;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Enfermeiro extends AtendenteHospitalar
{
    @Expose
    private long idEnfermeiro;
    
    @Expose
    private boolean treinadoOpRX;
    
    public Enfermeiro()
    {
        
    }
    
    public Enfermeiro(long idEnfermeiro, boolean treinadoOpRX, AtendenteHospitalar atendenteHospitalar)
    {
        super(atendenteHospitalar.getSetor(),atendenteHospitalar.getChSemanal(),atendenteHospitalar.getDadoPessoal());
        this.idEnfermeiro = idEnfermeiro;
        this.treinadoOpRX = treinadoOpRX;
    }
    
    @XmlElement
    public long getIdEnfermeiro()
    {
        return idEnfermeiro;
    }
    public void setIdEnfermeiro(long idEnfermeiro)
    {
        this.idEnfermeiro = idEnfermeiro;
    }

    @XmlElement
    public boolean isTreinadoOpRX()
    {
        return treinadoOpRX;
    }
    public void setTreinadoOpRX(boolean treinadoOpRX)
    {
        this.treinadoOpRX = treinadoOpRX;
    }
}
