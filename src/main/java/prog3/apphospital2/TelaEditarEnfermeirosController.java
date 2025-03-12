package prog3.apphospital2;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import prog3.appbackend.models.Enfermeiro;
import prog3.appbackend.services.EnfermeiroServiceJSON;

import java.io.IOException;
import prog3.appbackend.services.EnfermeiroServiceXML;

public class TelaEditarEnfermeirosController {

    private static Enfermeiro enfermeiroParaEditar;

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
    private CheckBox treinadoOpRXBox;

    @FXML
    public void initialize() {
        if (enfermeiroParaEditar != null) {
            carregarDadosEnfermeiro(enfermeiroParaEditar);
        }
    }

    public static void setEnfermeiroParaEditar(Enfermeiro enfermeiro) {
        enfermeiroParaEditar = enfermeiro;
    }

    private void carregarDadosEnfermeiro(Enfermeiro enfermeiro) {
        // Preenche os campos com os dados do enfermeiro
        ruaField.setText(enfermeiro.getDadoPessoal().getEndereco().getRua());
        numeroField.setText(String.valueOf(enfermeiro.getDadoPessoal().getEndereco().getNumero()));
        bairroField.setText(enfermeiro.getDadoPessoal().getEndereco().getBairro());
        cidadeField.setText(enfermeiro.getDadoPessoal().getEndereco().getCidade());
        estadoField.setText(enfermeiro.getDadoPessoal().getEndereco().getEstado());
        cepField.setText(String.valueOf(enfermeiro.getDadoPessoal().getEndereco().getCep()));

        telefoneField.setText(enfermeiro.getDadoPessoal().getContato().getTelefone());
        celularField.setText(enfermeiro.getDadoPessoal().getContato().getCelular());
        emailField.setText(enfermeiro.getDadoPessoal().getContato().getEmail());

        setorField.setText(enfermeiro.getSetor());
        chSemanalField.setText(String.valueOf(enfermeiro.getChSemanal()));
        treinadoOpRXBox.setSelected(enfermeiro.isTreinadoOpRX());
    }

    @FXML
    void editarEnfermeiro() {
        try {
            // Atualiza os dados do enfermeiro
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setRua(ruaField.getText());
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setNumero(Integer.parseInt(numeroField.getText()));
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setBairro(bairroField.getText());
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setCidade(cidadeField.getText());
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setEstado(estadoField.getText());
            enfermeiroParaEditar.getDadoPessoal().getEndereco().setCep(Integer.parseInt(cepField.getText()));

            enfermeiroParaEditar.getDadoPessoal().getContato().setTelefone(telefoneField.getText());
            enfermeiroParaEditar.getDadoPessoal().getContato().setCelular(celularField.getText());
            enfermeiroParaEditar.getDadoPessoal().getContato().setEmail(emailField.getText());

            enfermeiroParaEditar.setSetor(setorField.getText());
            enfermeiroParaEditar.setChSemanal(Integer.parseInt(chSemanalField.getText()));
            enfermeiroParaEditar.setTreinadoOpRX(treinadoOpRXBox.isSelected());

            // Regrava os enfermeiros no arquivo JSON
            EnfermeiroServiceJSON.atualizarEnfermeiro(enfermeiroParaEditar);
            EnfermeiroServiceXML.atualizarEnfermeiro(enfermeiroParaEditar);

            App.setRoot("telaenfermeiros");
        } catch (Exception e) {
            e.printStackTrace();
            // Adicione lógica para lidar com erros, como mostrar mensagens ao usuário
        }
    }

    @FXML
    void voltarTelaEnfermeiro() throws IOException {
        App.setRoot("telaenfermeiros");
    }
}

