package prog3.apphospital2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import prog3.appbackend.models.Paciente;
import java.io.IOException;
import prog3.appbackend.services.PacienteServiceJSON;
import prog3.appbackend.services.PacienteServiceXML;

public class TelaEditarPacientesController {

    private static Paciente pacienteParaEditar;

    @FXML
    private TextField bairroField;

    @FXML
    private TextField celularField;

    @FXML
    private TextField cepField;

    @FXML
    private TextField cidadeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField estadoField;

    @FXML
    private TextField idadeField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField obsgeralField;

    @FXML
    private TextField ruaField;

    @FXML
    private TextField telefoneField;

    @FXML
    public void initialize() 
    {
        if (pacienteParaEditar != null) 
        {
            carregarDadosPaciente(pacienteParaEditar);
        }
    }

    public static void setPacienteParaEditar(Paciente paciente) 
    {
        pacienteParaEditar = paciente;
    }

    private void carregarDadosPaciente(Paciente paciente) 
    {
        // Preenche os campos com os dados do paciente
        ruaField.setText(paciente.getEndereco().getRua());
        numeroField.setText(String.valueOf(paciente.getEndereco().getNumero()));
        bairroField.setText(paciente.getEndereco().getBairro());
        cidadeField.setText(paciente.getEndereco().getCidade());
        estadoField.setText(paciente.getEndereco().getEstado());
        cepField.setText(String.valueOf(paciente.getEndereco().getCep()));

        telefoneField.setText(paciente.getContato().getTelefone());
        celularField.setText(paciente.getContato().getCelular());
        emailField.setText(paciente.getContato().getEmail());

        idadeField.setText(String.valueOf(paciente.getIdade()));
        obsgeralField.setText(paciente.getObsGeral());
    }

    @FXML
    void editarPaciente() 
    {
        try 
        {
            // Atualiza os dados do paciente
            pacienteParaEditar.getEndereco().setRua(ruaField.getText());
            pacienteParaEditar.getEndereco().setNumero(Integer.parseInt(numeroField.getText()));
            pacienteParaEditar.getEndereco().setBairro(bairroField.getText());
            pacienteParaEditar.getEndereco().setCidade(cidadeField.getText());
            pacienteParaEditar.getEndereco().setEstado(estadoField.getText());
            pacienteParaEditar.getEndereco().setCep(Integer.parseInt(cepField.getText()));

            pacienteParaEditar.getContato().setTelefone(telefoneField.getText());
            pacienteParaEditar.getContato().setCelular(celularField.getText());
            pacienteParaEditar.getContato().setEmail(emailField.getText());

            pacienteParaEditar.setIdade(Integer.parseInt(idadeField.getText()));
            pacienteParaEditar.setObsGeral(obsgeralField.getText());
            
            var historicoAtual = pacienteParaEditar.getHistoricoConsultasMedicas();
            pacienteParaEditar.setHistoricoConsultasMedicas(historicoAtual);

            // Regrava os pacientes no arquivo JSON
            PacienteServiceJSON.atualizarPaciente(pacienteParaEditar);
            PacienteServiceXML.atualizarPaciente(pacienteParaEditar);

            App.setRoot("telapacientes");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    @FXML
    void voltarTelaPaciente() throws IOException 
    {
        App.setRoot("telapacientes");
    }
}

