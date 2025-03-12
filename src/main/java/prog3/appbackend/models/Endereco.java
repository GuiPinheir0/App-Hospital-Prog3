package prog3.appbackend.models;
import com.google.gson.annotations.Expose;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Endereco 
{
    @Expose
    private String rua;
    
    @Expose
    private int numero;
    
    @Expose
    private String bairro;
    
    @Expose
    private String cidade;
    
    @Expose
    private String estado;
    
    @Expose
    private int cep;    
    
    public Endereco()
    {
        
    }
    
    public Endereco(String rua, int numero, String bairro, String cidade, String estado, int cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    
    
    
    @XmlElement
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    
    @XmlElement
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    @XmlElement
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    @XmlElement
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    @XmlElement
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @XmlElement
    public int getCep() {
        return cep;
    }
    public void setCep(int cep) {
        this.cep = cep;
    }

    // Método toString para exibir os dados formatados
    @Override
    public String toString() 
    {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + " (CEP: " + cep + ")";
    }
    
}
