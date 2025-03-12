package prog3.apphospital2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import prog3.appbackend.models.ConsultaMedica;
import prog3.appbackend.services.ConsultaServiceJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ChoiceBox;
import prog3.appbackend.models.Medico;
import prog3.appbackend.models.Paciente;
import prog3.appbackend.services.ConsultaServiceXML;
import prog3.appbackend.services.MedicoServiceJSON;
import prog3.appbackend.services.PacienteServiceJSON;
import prog3.appbackend.services.PacienteServiceXML;

public class TelaRegistroConsultasController {

    @FXML
    private TextField diagnosticoField;

    @FXML
    private Label erroMsg;

    @FXML
    private TextField exameQueixaField;


    @FXML
    private CheckBox indicacaoCirurgicaField;

    @FXML
    private TextField prescricaoField;

    @FXML
    private Button registrarBT;

    @FXML
    private Button voltarBT;
    
    @FXML
    private ChoiceBox<String> idMedicoBox;
    
    @FXML
    private ChoiceBox<String> idPacienteBox;

    private List<ConsultaMedica> listaConsultas = new ArrayList<>();

    @FXML
    public void initialize() {
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaConsultas = ConsultaServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaConsultas = ConsultaServiceXML.carregar();
        }
        
        preencherPacienteChoiceBox();
        
        preencherMedicoChoiceBox();
    }
    
    private void preencherPacienteChoiceBox()
    {
        List<Paciente> listaPacientes = PacienteServiceJSON.carregar();
        
        idPacienteBox.getItems().clear();
        
        for (Paciente paciente : listaPacientes) 
        {
            String displayText = paciente.getIdPaciente() + " - " + paciente.getNomeCompleto();
            idPacienteBox.getItems().add(displayText);
        }
    }
    
    private void preencherMedicoChoiceBox()
    {
        List<Medico> listaMedicos = MedicoServiceJSON.carregar();
        
        idMedicoBox.getItems().clear();
        
        for (Medico medico : listaMedicos) 
        {
            String displayText = medico.getIdMedico() + " - " + medico.getDadoPessoal().getNomeCompleto();
            idMedicoBox.getItems().add(displayText);
        }
    }

    @FXML
    void registrarConsulta(ActionEvent event) {
        try {
            // Validações
            if (idPacienteBox.getValue().isEmpty() || idMedicoBox.getValue().isEmpty() || exameQueixaField.getText().isEmpty()) {
                erroMsg.setText("ERRO! Preencha todos os campos obrigatórios!");
                return;
            }
            
            long idPaciente = Long.parseLong(idPacienteBox.getValue().split(" - ")[0]);
            long idMedico = Long.parseLong(idMedicoBox.getValue().split(" - ")[0]);

            long idConsulta = listaConsultas.size() + 1;
            
            String exameQueixa = exameQueixaField.getText();
            String diagnostico = diagnosticoField.getText();
            String prescricao = prescricaoField.getText();
            boolean indicacaoCirurgica = indicacaoCirurgicaField.isSelected();

            // Criação da nova consulta
            ConsultaMedica novaConsulta = new ConsultaMedica(
                idConsulta, idPaciente, idMedico, exameQueixa, diagnostico, prescricao, indicacaoCirurgica
            );
            
            adicionarConsultaAoPaciente(idPaciente,novaConsulta);

            // Adicionar à lista e salvar
            listaConsultas.add(novaConsulta);
            ConsultaServiceJSON.salvar(listaConsultas);
            ConsultaServiceXML.salvar(listaConsultas);
            

            // Limpar campos e mostrar mensagem de sucesso
            limparCampos();
            exibirMsgInfo();
        } catch (NumberFormatException e) {
            erroMsg.setText("Erro: Certifique-se de preencher os campos numéricos corretamente.");
        } catch (Exception e) {
            erroMsg.setText("Erro ao registrar consulta: " + e.getMessage());
        }
    }
    
    private void adicionarConsultaAoPaciente(long idPaciente, ConsultaMedica novaConsulta) 
    {
        
        List<Paciente> listaPacientes = PacienteServiceJSON.carregar();

        boolean pacienteEncontrado = false;

        // Procura o paciente pelo ID
        for (Paciente paciente : listaPacientes) 
        {
            if (paciente.getIdPaciente() == idPaciente) {
                paciente.adicionarConsulta(novaConsulta); // Adiciona a consulta ao histórico
                pacienteEncontrado = true;
                break;
            }
        }

        if (!pacienteEncontrado) {
            erroMsg.setText("Paciente com ID " + idPaciente + " não encontrado.");
            return;
        }

        // Salva a lista de pacientes atualizada no JSON
        PacienteServiceJSON.salvar(listaPacientes);
        PacienteServiceXML.salvar(listaPacientes);
    }

    @FXML
    void voltarTelaConsultas(ActionEvent event) throws IOException {
        App.setRoot("telaconsultas");
    }

    private void limparCampos() {
        exameQueixaField.clear();
        diagnosticoField.clear();
        prescricaoField.clear();
        indicacaoCirurgicaField.setSelected(false);
    }

    private void exibirMsgInfo() {
        Alert msg_alerta = new Alert(Alert.AlertType.INFORMATION);
        msg_alerta.setTitle(" ");
        msg_alerta.setHeaderText("Consulta registrada com sucesso!");
        msg_alerta.showAndWait();
    }
}

