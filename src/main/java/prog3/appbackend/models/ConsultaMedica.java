
package prog3.appbackend.models;

import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultaMedica 
{
    @Expose
    private long idConsulta;
    
    @Expose
    private long idPaciente;
    
    @Expose
    private long idMedico;
    
    @Expose
    private String exameQueixa;
    
    @Expose
    private String diagnostico;
    
    @Expose
    private String prescricao;
    
    @Expose
    private boolean indicacaoCirurgica;
    
    public ConsultaMedica()
    {
        
    }
    
    public ConsultaMedica(long idConsulta, long idPaciente, long idMedico, String exameQueixa, String diagnostico, String prescricao, boolean indicacaoCirurgica )
    {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.exameQueixa = exameQueixa;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.indicacaoCirurgica = indicacaoCirurgica;
    }
    
    @XmlElement
    public long getIdConsulta()
    {
        return idConsulta;
    }
    public void setIdConsulta(long idConsulta)
    {
        this.idConsulta = idConsulta;
    }
    
    @XmlElement
    public long getIdPaciente()
    {
        return idPaciente;
    }
    public void setIdPaciente(long idPaciente)
    {
        this.idPaciente = idPaciente;
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
    public String getExameQueixa()
    {
        return exameQueixa;
    }
    public void setExameQueixa(String exameQueixa)
    {
        this.exameQueixa = exameQueixa;
    }
    
    @XmlElement
    public String getDiagnostico()
    {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico)
    {
        this.diagnostico = diagnostico;
    }
    
    @XmlElement
    public String getPrescricao()
    {
        return prescricao;
    }
    public void setPrescricao(String prescricao)
    {
        this.prescricao = prescricao;
    }
    
    @XmlElement
    public boolean getIndicacaoCirurgica()
    {
        return indicacaoCirurgica;
    }
    public void setIndicacaoCirurgica(boolean indicacaoCirurgica)
    {
        this.indicacaoCirurgica = indicacaoCirurgica;
    }
    
    
}

