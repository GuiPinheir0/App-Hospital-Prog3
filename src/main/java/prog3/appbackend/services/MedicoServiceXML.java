package prog3.appbackend.services;

import prog3.appbackend.models.Medico;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MedicoServiceXML {

    private static final String ARQUIVO_XML = "medicos.xml";

    @XmlRootElement(name = "Medicos")
    public static class ListaMedicosWrapper 
    {
        private List<Medico> medicos;

        @XmlElement(name = "Medico")
        public List<Medico> getMedicos() 
        {
            return medicos;
        }

        public void setMedicos(List<Medico> medicos) {
            this.medicos = medicos;
        }
    }

    public static List<Medico> carregar() 
    {
        File arquivoXml = new File(ARQUIVO_XML);

        if (!arquivoXml.exists()) 
        {
            System.out.println("Arquivo de médicos XML não existe.");
            return new ArrayList<>();
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaMedicosWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaMedicosWrapper wrapper = (ListaMedicosWrapper) unmarshaller.unmarshal(arquivoXml);
            System.out.println("Lista de médicos carregada do arquivo 'medicos.xml'.");
            return wrapper.getMedicos() != null ? wrapper.getMedicos() : new ArrayList<>();
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar médicos do XML: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<Medico> listaMedicos) 
    {
        if (listaMedicos == null || listaMedicos.isEmpty()) 
        {
            System.out.println("Lista de médicos está vazia. Nada para salvar.");
            return;
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaMedicosWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ListaMedicosWrapper wrapper = new ListaMedicosWrapper();
            wrapper.setMedicos(listaMedicos);

            marshaller.marshal(wrapper, new File(ARQUIVO_XML));
            System.out.println("Lista de médicos salva no arquivo 'medicos.xml'.");
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

    public static void atualizarMedico(Medico medicoEditado) 
    {
        List<Medico> listaMedicos = carregar();

        for (int i = 0; i < listaMedicos.size(); i++) 
        {
            if (listaMedicos.get(i).getIdMedico() == medicoEditado.getIdMedico()) 
            {
                listaMedicos.set(i, medicoEditado);
                break;
            }
        }

        salvar(listaMedicos);
    }
}
