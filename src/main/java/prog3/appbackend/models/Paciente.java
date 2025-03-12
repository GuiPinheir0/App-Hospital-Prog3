
package prog3.appbackend.models;
import java.util.Date;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Paciente extends DadoPessoal
{
    @Expose
    private long idPaciente;
    
    @Expose
    private int idade;
    
    @Expose
    private Date dataCadastro;
    
    @Expose
    private String obsGeral;
    
    @Expose
    private List<ConsultaMedica> historicoConsultasMedicas;
    
    public Paciente()
    {
        
    }
    
    
    public Paciente(long idPaciente, int idade, Date dataCadastro, String obsGeral, DadoPessoal dadoPessoal)
    {
        super(dadoPessoal.getNomeCompleto(),dadoPessoal.getDataNascimento(), dadoPessoal.getEndereco(), dadoPessoal.getContato(), dadoPessoal.getGenero());
        this.idPaciente = idPaciente;
        this.idade = idade;
        this.dataCadastro = dataCadastro;
        this.obsGeral = obsGeral;
        this.historicoConsultasMedicas = new ArrayList<>();
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
    public int getIdade()
    {
        return idade;
    }
    public void setIdade(int idade)
    {
        this.idade = idade;
    }
    
    @XmlElement
    public Date getDataCadastro()
    {
        return dataCadastro;
    }
    public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }
    
    @XmlElement
    public String getObsGeral()
    {
        return obsGeral;
    }
    public void setObsGeral(String obsGeral)
    {
        this.obsGeral = obsGeral;
    }
    
    @XmlElementWrapper(name = "historicoConsultasMedicas")
    @XmlElement
    public List<ConsultaMedica> getHistoricoConsultasMedicas() {
        return historicoConsultasMedicas;
    }

    public void setHistoricoConsultasMedicas(List<ConsultaMedica> historicoConsultasMedicas) {
        this.historicoConsultasMedicas = historicoConsultasMedicas;
    }

    public void adicionarConsulta(ConsultaMedica consulta) {
        this.historicoConsultasMedicas.add(consulta);
    }

}
