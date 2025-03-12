
package prog3.appbackend.models;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Medico extends AtendenteHospitalar
{
    @Expose
    private long idMedico;
    
    @Expose
    private int numeroCRM;
    
    @Expose
    private String areasEspecialidade;
    
    @Expose
    private boolean cirurgiao;
    
    public Medico()
    {
        
    }
    
    public Medico(long idMedico, int numeroCRM, String areasEspecialidade,boolean cirurgiao, AtendenteHospitalar atendenteHospitalar)
    {
        super(atendenteHospitalar.getSetor(),atendenteHospitalar.getChSemanal(),atendenteHospitalar.getDadoPessoal());
        this.idMedico = idMedico;
        this.numeroCRM = numeroCRM;
        this.areasEspecialidade = areasEspecialidade;
        this.cirurgiao = cirurgiao;
    }
    
    @XmlElement
    public long getIdMedico()
    {
        return idMedico;
    }
    public void setIdMedico(long idMedico)
    {
        this.idMedico = idMedico;
    }
    
    @XmlElement
    public int getNumeroCRM()
    {
        return numeroCRM;
    }
    public void setNumeroCRM(int numeroCRM)
    {
        this.numeroCRM = numeroCRM;
    }
    
    @XmlElement
    public String getAreasEspecialidade()
    {
        return areasEspecialidade;
    }
    public void setAreasEspecialidade(String areasEspecialidade)
    {
        this.areasEspecialidade = areasEspecialidade;
    }
    
    @XmlElement
    public boolean isCirurgiao()
    {
        return cirurgiao;
    }
    public void setCirurgiao(boolean cirurgiao)
    {
        this.cirurgiao = cirurgiao;
    }
}
