
package prog3.appbackend.models;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AtendenteHospitalar extends DadoPessoal
{
    @Expose
    private String setor;
    
    @Expose
    private int chSemanal;
    
    @Expose
    private DadoPessoal dadoPessoal;
    
    public AtendenteHospitalar()
    {
        
    }
    
    public AtendenteHospitalar(String setor, int chSemanal, DadoPessoal dadoPessoal)
    {
        super(dadoPessoal.getNomeCompleto(), dadoPessoal.getDataNascimento(), dadoPessoal.getEndereco(), dadoPessoal.getContato(), dadoPessoal.getGenero());
        this.setor = setor;
        this.chSemanal = chSemanal;    
        this.dadoPessoal = dadoPessoal;
    }
    
    @XmlElement
    public String getSetor()
    {
        return setor;
    }
    public void setSetor(String setor)
    {
        this.setor = setor;
    }
    
    @XmlElement
    public int getChSemanal()
    {
        return chSemanal;
    }
    public void setChSemanal(int chSemanal)
    {
        this.chSemanal = chSemanal;
    }
    
    @XmlElement
    public DadoPessoal getDadoPessoal()
    {
        return dadoPessoal;
    }
    public void setDadoPessoal(DadoPessoal dadoPessoal)
    {
        this.dadoPessoal = dadoPessoal;
    }
}
