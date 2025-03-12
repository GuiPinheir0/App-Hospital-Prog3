package prog3.apphospital2;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import prog3.appbackend.models.ConsultaMedica;
import prog3.appbackend.models.Paciente;
import prog3.appbackend.services.ConsultaServiceJSON;

public class TelaHistoricoPacienteController implements Initializable {
    @FXML
    private TableColumn<ConsultaMedica, Long> diagnosticoC;

    @FXML
    private TableColumn<ConsultaMedica, Long> idConsultaC;

    @FXML
    private TableColumn<ConsultaMedica, Long> idMedicoC;

    @FXML
    private TableColumn<ConsultaMedica, Long> idPacienteC;

    @FXML
    private TableColumn<ConsultaMedica, Boolean> indicacaoCirurgicaC;

    @FXML
    private TableColumn<ConsultaMedica, String> prescricaoC;

    @FXML
    private TableColumn<ConsultaMedica, String> queixaPrincipalC;

    @FXML
    private TableView<ConsultaMedica> tabelaConsultas;

    @FXML
    private Button voltar;

    // Variável estática para armazenar o paciente selecionado
    private static Paciente pacienteSelecionado;

    // Método para definir o paciente selecionado (chamado pela tela anterior)
    public static void setPacienteSelecionado(Paciente paciente) 
    {
        pacienteSelecionado = paciente;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        // Configurar as colunas da tabela
        idConsultaC.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        idPacienteC.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        idMedicoC.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        queixaPrincipalC.setCellValueFactory(new PropertyValueFactory<>("exameQueixa"));
        diagnosticoC.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        prescricaoC.setCellValueFactory(new PropertyValueFactory<>("prescricao"));
        indicacaoCirurgicaC.setCellValueFactory(new PropertyValueFactory<>("indicacaoCirurgica"));

        // Carregar histórico de consultas do paciente
        carregarHistoricoConsultas();
    }

    private void carregarHistoricoConsultas() 
    {
        if (pacienteSelecionado != null) 
        {
            
            List<ConsultaMedica> todasConsultas = ConsultaServiceJSON.carregar();

            
            List<ConsultaMedica> consultasDoPaciente = todasConsultas.stream()
                .filter(consulta -> consulta.getIdPaciente() == pacienteSelecionado.getIdPaciente())
                .collect(Collectors.toList());

            // Popula a tabela com as consultas do paciente
            tabelaConsultas.getItems().setAll(consultasDoPaciente);
        }
    }

    @FXML
    void voltarTelaPacientes(ActionEvent event) throws IOException 
    {
        App.setRoot("telapacientes");
    }
}
