package prog3.apphospital2;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import prog3.appbackend.models.Medico;
import prog3.appbackend.services.MedicoServiceJSON;
import java.io.IOException;
import prog3.appbackend.services.MedicoServiceXML;

public class TelaEditarMedicosController {

    private static Medico medicoParaEditar;

    @FXML
    private TextField areaEspecialidadeField;

    @FXML
    private TextField bairroField;

    @FXML
    private TextField celularField;

    @FXML
    private TextField cepField;

    @FXML
    private TextField chSemanalField;

    @FXML
    private TextField cidadeField;

    @FXML
    private CheckBox cirurgiaoBox;

    @FXML
    private TextField crmField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField estadoField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField ruaField;

    @FXML
    private TextField setorField;

    @FXML
    private TextField telefoneField;

    @FXML
    public void initialize() {
        if (medicoParaEditar != null) {
            carregarDadosMedico(medicoParaEditar);
        }
    }

    public static void setMedicoParaEditar(Medico medico) {
        medicoParaEditar = medico;
    }

    private void carregarDadosMedico(Medico medico) {
        // Preenche os campos com os dados do médico
        ruaField.setText(medico.getEndereco().getRua());
        numeroField.setText(String.valueOf(medico.getEndereco().getNumero()));
        bairroField.setText(medico.getEndereco().getBairro());
        cidadeField.setText(medico.getEndereco().getCidade());
        estadoField.setText(medico.getEndereco().getEstado());
        cepField.setText(String.valueOf(medico.getEndereco().getCep()));

        telefoneField.setText(medico.getContato().getTelefone());
        celularField.setText(medico.getContato().getCelular());
        emailField.setText(medico.getContato().getEmail());

        crmField.setText(String.valueOf(medico.getNumeroCRM()));
        setorField.setText(medico.getSetor());
        areaEspecialidadeField.setText(medico.getAreasEspecialidade());
        chSemanalField.setText(String.valueOf(medico.getChSemanal()));
        cirurgiaoBox.setSelected(medico.isCirurgiao());
    }

    @FXML
    void editarMedico() {
        try {
            // Atualiza os dados do médico
            medicoParaEditar.getEndereco().setRua(ruaField.getText());
            medicoParaEditar.getEndereco().setNumero(Integer.parseInt(numeroField.getText()));
            medicoParaEditar.getEndereco().setBairro(bairroField.getText());
            medicoParaEditar.getEndereco().setCidade(cidadeField.getText());
            medicoParaEditar.getEndereco().setEstado(estadoField.getText());
            medicoParaEditar.getEndereco().setCep(Integer.parseInt(cepField.getText()));

            medicoParaEditar.getContato().setTelefone(telefoneField.getText());
            medicoParaEditar.getContato().setCelular(celularField.getText());
            medicoParaEditar.getContato().setEmail(emailField.getText());

            medicoParaEditar.setNumeroCRM(Integer.parseInt(crmField.getText()));
            medicoParaEditar.setSetor(setorField.getText());
            medicoParaEditar.setAreasEspecialidade(areaEspecialidadeField.getText());
            medicoParaEditar.setChSemanal(Integer.parseInt(chSemanalField.getText()));
            medicoParaEditar.setCirurgiao(cirurgiaoBox.isSelected());

            // Regrava os médicos no arquivo JSON
            MedicoServiceJSON.atualizarMedico(medicoParaEditar);
            MedicoServiceXML.atualizarMedico(medicoParaEditar);

            App.setRoot("telamedicos");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    @FXML
    void voltarTelaMedico() throws IOException {
        App.setRoot("telamedicos");
    }
}

