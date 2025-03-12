package prog3.appbackend.services;

import prog3.appbackend.models.Enfermeiro;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EnfermeiroServiceXML {

    private static final String ARQUIVO_XML = "enfermeiros.xml";

    @XmlRootElement(name = "Enfermeiros")
    public static class ListaEnfermeirosWrapper 
    {
        private List<Enfermeiro> enfermeiros;

        @XmlElement(name = "Enfermeiro")
        public List<Enfermeiro> getEnfermeiros() 
        {
            return enfermeiros;
        }

        public void setEnfermeiros(List<Enfermeiro> enfermeiros) 
        {
            this.enfermeiros = enfermeiros;
        }
    }

    public static List<Enfermeiro> carregar() 
    {
        File arquivoXml = new File(ARQUIVO_XML);

        if (!arquivoXml.exists()) 
        {
            System.out.println("Arquivo de enfermeiros XML não existe.");
            return new ArrayList<>();
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaEnfermeirosWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaEnfermeirosWrapper wrapper = (ListaEnfermeirosWrapper) unmarshaller.unmarshal(arquivoXml);
            System.out.println("Lista de enfermeiros carregada do arquivo 'enfermeiros.xml'.");
            return wrapper.getEnfermeiros() != null ? wrapper.getEnfermeiros() : new ArrayList<>();
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar enfermeiros do XML: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<Enfermeiro> listaEnfermeiros) 
    {
        if (listaEnfermeiros == null || listaEnfermeiros.isEmpty()) 
        {
            System.out.println("Lista de enfermeiros está vazia. Nada para salvar.");
            return;
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaEnfermeirosWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ListaEnfermeirosWrapper wrapper = new ListaEnfermeirosWrapper();
            wrapper.setEnfermeiros(listaEnfermeiros);

            marshaller.marshal(wrapper, new File(ARQUIVO_XML));
            System.out.println("Lista de enfermeiros salva no arquivo 'enfermeiros.xml'.");
            
        } catch (Exception e) {
            if (e instanceof JAXBException) {
                JAXBException jaxbException = (JAXBException) e;
                Throwable linkedException = jaxbException.getLinkedException();
                if (linkedException != null) {
                    linkedException.printStackTrace();
                } else {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void atualizarEnfermeiro(Enfermeiro enfermeiroEditado) 
    {
        List<Enfermeiro> listaEnfermeiros = carregar();

        for (int i = 0; i < listaEnfermeiros.size(); i++) 
        {
            if (listaEnfermeiros.get(i).getIdEnfermeiro() == enfermeiroEditado.getIdEnfermeiro()) 
            {
                listaEnfermeiros.set(i, enfermeiroEditado);
                break;
            }
        }

        salvar(listaEnfermeiros);
    }
}
