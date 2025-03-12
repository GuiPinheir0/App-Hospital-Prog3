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
import java.util.List;
import prog3.appbackend.models.Paciente;
import prog3.appbackend.services.ConsultaServiceXML;
import prog3.appbackend.services.PacienteServiceJSON;

public class TelaEditarConsultasController {

    private static ConsultaMedica consultaParaEditar;

    @FXML
    private TextField diagnosticoField;

    @FXML
    private Button editarBT;

    @FXML
    private Label erroMsg;

    @FXML
    private TextField exameQueixaField;

    @FXML
    private TextField idMedicoField;

    @FXML
    private TextField idPacienteField;

    @FXML
    private CheckBox indicacaoCirurgicaField;

    @FXML
    private TextField prescricaoField;

    @FXML
    private Button voltarBT;

    @FXML
    public void initialize() {
        if (consultaParaEditar != null) {
            carregarDadosConsulta(consultaParaEditar);
        }
    }
    

    public static void setConsultaParaEditar(ConsultaMedica consulta) 
    {
        consultaParaEditar = consulta;
    }

    private void carregarDadosConsulta(ConsultaMedica consulta) {
        // Preenche os campos com os dados da consulta
        idPacienteField.setText(String.valueOf(consulta.getIdPaciente()));
        idMedicoField.setText(String.valueOf(consulta.getIdMedico()));
        exameQueixaField.setText(consulta.getExameQueixa());
        diagnosticoField.setText(consulta.getDiagnostico());
        prescricaoField.setText(consulta.getPrescricao());
        indicacaoCirurgicaField.setSelected(consulta.getIndicacaoCirurgica());
    }

    @FXML
    void editarConsulta(ActionEvent event) {
        try {
            // Atualiza os dados da consulta
            consultaParaEditar.setIdPaciente(Long.parseLong(idPacienteField.getText()));
            consultaParaEditar.setIdMedico(Long.parseLong(idMedicoField.getText()));
            consultaParaEditar.setExameQueixa(exameQueixaField.getText());
            consultaParaEditar.setDiagnostico(diagnosticoField.getText());
            consultaParaEditar.setPrescricao(prescricaoField.getText());
            consultaParaEditar.setIndicacaoCirurgica(indicacaoCirurgicaField.isSelected());

            // Atualiza o JSON
            ConsultaServiceJSON.atualizarConsulta(consultaParaEditar);
            ConsultaServiceXML.atualizarConsulta(consultaParaEditar);
            
   

            // Retorna para a tela de consultas
            App.setRoot("telaconsultas");

        } catch (NumberFormatException e) {
            exibirMsgErro("Erro: IDs devem ser números válidos.");
        } catch (Exception e) {
            e.printStackTrace();
            exibirMsgErro("Erro ao salvar a consulta. Verifique os dados.");
        }
    }

    @FXML
    void voltarTelaConsultas(ActionEvent event) throws IOException {
        App.setRoot("telaconsultas");
    }

    private void exibirMsgErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}


