package prog3.apphospital2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import prog3.appbackend.models.ConsultaMedica;
import prog3.appbackend.services.ConsultaServiceJSON;
import prog3.appbackend.services.ConsultaServiceXML;

public class TelaConsultasController implements Initializable {

    private List<ConsultaMedica> listaConsultas = new ArrayList<>();

    @FXML
    private TableColumn<ConsultaMedica, Long> idConsultaC;

    @FXML
    private TableColumn<ConsultaMedica, Long> idMedicoC;

    @FXML
    private TableColumn<ConsultaMedica, Long> idPacienteC;

    @FXML
    private TableColumn<ConsultaMedica, String> diagnosticoC;

    @FXML
    private TableColumn<ConsultaMedica, String> indicacaoCirurgicaC;

    @FXML
    private TableColumn<ConsultaMedica, String> prescricaoC;

    @FXML
    private TableColumn<ConsultaMedica, String> queixaPrincipalC;

    @FXML
    private TableView<ConsultaMedica> tabelaConsultas;

    @FXML
    private Button editarBT;

    @FXML
    private Button excluirBT;

    @FXML
    private Button registrarConsultaBT;

    @FXML
    private Button voltarMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaConsultas = ConsultaServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaConsultas = ConsultaServiceXML.carregar();
        }
    

        // Configurar colunas
        idConsultaC.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        idPacienteC.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        idMedicoC.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        queixaPrincipalC.setCellValueFactory(new PropertyValueFactory<>("exameQueixa"));
        diagnosticoC.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        prescricaoC.setCellValueFactory(new PropertyValueFactory<>("prescricao"));
        indicacaoCirurgicaC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getIndicacaoCirurgica() ? "Sim" : "Não"));

        // Adicionar dados à tabela
        tabelaConsultas.getItems().setAll(listaConsultas);
    }

    @FXML
    void editarConsulta(ActionEvent event) throws IOException 
    {
        ConsultaMedica consultaSelecionada = tabelaConsultas.getSelectionModel().getSelectedItem();
        

        if (consultaSelecionada == null) 
        {
            exibirMsgInfo("Por favor, selecione uma consulta para editar.");
            return;
        }

        TelaEditarConsultasController.setConsultaParaEditar(consultaSelecionada);
        App.setRoot("telaeditarconsultas");
    }

    @FXML
    void excluirConsulta(ActionEvent event) throws IOException {
        ConsultaMedica consultaSelecionada = tabelaConsultas.getSelectionModel().getSelectedItem();

        if (consultaSelecionada == null) {
            exibirMsgInfo("Por favor, selecione uma consulta para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir esta consulta?");
        alert.setContentText("Consulta ID: " + consultaSelecionada.getIdConsulta());

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            listaConsultas.remove(consultaSelecionada);
            ConsultaServiceJSON.salvar(listaConsultas);
            ConsultaServiceXML.salvar(listaConsultas);
            tabelaConsultas.getItems().setAll(listaConsultas);
            exibirMsgInfo("Consulta excluída com sucesso.");
        }
    }

    @FXML
    void telaRegistroConsulta() throws IOException {
        App.setRoot("telaregistroconsultas");
    }

    @FXML
    void voltarMenu(ActionEvent event) throws IOException {
        App.setRoot("telainicial");
    }

    private void exibirMsgInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

