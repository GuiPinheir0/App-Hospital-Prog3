package prog3.appbackend.services;

import prog3.appbackend.models.ConsultaMedica;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConsultaServiceXML {

    private static final String ARQUIVO_XML = "consultas.xml";

    @XmlRootElement(name = "Consultas")
    public static class ListaConsultasWrapper 
    {
        private List<ConsultaMedica> consultas;

        @XmlElement(name = "Consulta")
        public List<ConsultaMedica> getConsultas() 
        {
            return consultas;
        }

        public void setConsultas(List<ConsultaMedica> consultas) 
        {
            this.consultas = consultas;
        }
    }

    public static List<ConsultaMedica> carregar() 
    {
        File arquivoXml = new File(ARQUIVO_XML);

        if (!arquivoXml.exists()) 
        {
            System.out.println("Arquivo de consultas XML não existe.");
            return new ArrayList<>();
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaConsultasWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaConsultasWrapper wrapper = (ListaConsultasWrapper) unmarshaller.unmarshal(arquivoXml);
            System.out.println("Lista de consultas carregada do arquivo 'consultas.xml'.");
            return wrapper.getConsultas() != null ? wrapper.getConsultas() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao carregar consultas do XML: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<ConsultaMedica> listaConsultas) 
    {
        if (listaConsultas == null || listaConsultas.isEmpty()) 
        {
            System.out.println("Lista de consultas está vazia. Nada para salvar.");
            return;
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaConsultasWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ListaConsultasWrapper wrapper = new ListaConsultasWrapper();
            wrapper.setConsultas(listaConsultas);

            marshaller.marshal(wrapper, new File(ARQUIVO_XML));
            System.out.println("Lista de consultas salva no arquivo 'consultas.xml'.");
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

    public static void atualizarConsulta(ConsultaMedica consultaEditada) 
    {
        List<ConsultaMedica> listaConsultas = carregar();

        for (int i = 0; i < listaConsultas.size(); i++) 
        {
            if (listaConsultas.get(i).getIdConsulta() == consultaEditada.getIdConsulta()) 
            {
                listaConsultas.set(i, consultaEditada);
                break;
            }
        }

        salvar(listaConsultas);
    }
}
