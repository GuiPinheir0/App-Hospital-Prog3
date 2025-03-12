
package prog3.appbackend.models;
import java.util.Date;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import prog3.appbackend.enums.Genero;


@XmlRootElement
public class DadoPessoal 
{
    @Expose
    private String nomeCompleto;
    
    @Expose
    private Date dataNascimento;
    
    @Expose
    private Endereco endereco;
    
    @Expose
    private ContatoTelEmail contato;
    
    @Expose
    private Genero genero;
    
    public DadoPessoal()
    {
        
    }
    
    public DadoPessoal(String nomeCompleto, Date dataNascimento, Endereco endereco, ContatoTelEmail contato, Genero genero)
    {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.contato = contato;
        this.genero = genero;
    }
    
    @XmlElement
    public String getNomeCompleto()
    {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto)
    {
        this.nomeCompleto = nomeCompleto;
    }
    
    @XmlElement
    public Date getDataNascimento()
    {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }
    
    @XmlElement
    public Endereco getEndereco()
    {
        return endereco;
    }
    public void setEndereco(Endereco endereco)
    {
        this.endereco = endereco;
    }
    
    @XmlElement
    public ContatoTelEmail getContato()
    {
        return contato;
    }
    public void setContato(ContatoTelEmail contato)
    {
        this.contato = contato;
    }
    
    @XmlElement
    public Genero getGenero()
    {
        return genero;
    }
    public void setGenero(Genero genero)
    {
        this.genero = genero;
    }
}
