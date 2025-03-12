
package prog3.appbackend.models;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContatoTelEmail 
{
    @Expose
    private String telefone;
    
    @Expose
    private String celular;
    
    @Expose
    private String email;
    
    public ContatoTelEmail()
    {
        
    }
    
    public ContatoTelEmail(String telefone, String celular, String email)
    {
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
    }
    
    @XmlElement
    public String getTelefone()
    {
        return telefone;
    }
    public void setTelefone(String telefone)
    {
        this.telefone = telefone;
    }
    
    @XmlElement
    public String getCelular()
    {
        return celular;
    }
    public void setCelular(String celular)
    {
        this.celular = celular;
    }
    
    @XmlElement
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}
