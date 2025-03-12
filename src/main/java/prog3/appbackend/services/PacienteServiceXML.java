package prog3.appbackend.services;

import prog3.appbackend.models.Paciente;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PacienteServiceXML {

    private static final String ARQUIVO_XML = "pacientes.xml";

    @XmlRootElement(name = "Pacientes")
    public static class ListaPacientesWrapper 
    {
        private List<Paciente> pacientes;

        @XmlElement(name = "Paciente")
        public List<Paciente> getPacientes() 
        {
            return pacientes;
        }

        public void setPacientes(List<Paciente> pacientes) 
        {
            this.pacientes = pacientes;
        }
    }

    public static List<Paciente> carregar() 
    {
        File arquivoXml = new File(ARQUIVO_XML);

        if (!arquivoXml.exists()) {
            System.out.println("Arquivo de pacientes XML não existe.");
            return new ArrayList<>();
        }

        try {
            JAXBContext context = JAXBContext.newInstance(ListaPacientesWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaPacientesWrapper wrapper = (ListaPacientesWrapper) unmarshaller.unmarshal(arquivoXml);
            System.out.println("Lista de pacientes carregada do arquivo 'pacientes.xml'.");
            return wrapper.getPacientes() != null ? wrapper.getPacientes() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao carregar pacientes do XML: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<Paciente> listaPacientes) 
    {
        if (listaPacientes == null || listaPacientes.isEmpty()) 
        {
            System.out.println("Lista de pacientes está vazia. Nada para salvar.");
            return;
        }

        try 
        {
            JAXBContext context = JAXBContext.newInstance(ListaPacientesWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ListaPacientesWrapper wrapper = new ListaPacientesWrapper();
            wrapper.setPacientes(listaPacientes);

            marshaller.marshal(wrapper, new File(ARQUIVO_XML));
            System.out.println("Lista de pacientes salva no arquivo 'pacientes.xml'.");
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

    public static void atualizarPaciente(Paciente pacienteEditado) 
    {
        List<Paciente> listaPacientes = carregar();

        for (int i = 0; i < listaPacientes.size(); i++) 
        {
            if (listaPacientes.get(i).getIdPaciente() == pacienteEditado.getIdPaciente()) 
            {
                listaPacientes.set(i, pacienteEditado);
                break;
            }
        }

        salvar(listaPacientes);
    }
}
