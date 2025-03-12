package prog3.apphospital2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.scene.control.Alert;
import prog3.appbackend.enums.Genero;
import prog3.appbackend.models.AtendenteHospitalar;
import prog3.appbackend.models.DadoPessoal;
import prog3.appbackend.models.Endereco;
import prog3.appbackend.models.ContatoTelEmail;
import prog3.appbackend.models.Medico;
import prog3.appbackend.services.MedicoServiceJSON;
import prog3.appbackend.services.MedicoServiceXML;

public class TelaCadastroMedicosController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        generoBox.getItems().addAll(generos);
        
        // Decide qual método de carregamento usar com base no estado
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaMedicos = MedicoServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaMedicos = MedicoServiceXML.carregar();
        }
        
        
    }

    @FXML
    private TextField areaEspecialidadeField;

    @FXML
    private TextField bairroField;

    @FXML
    private Button cadastrarBT;

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
    private Label erroMsg;

    @FXML
    private TextField estadoField;

    @FXML
    private ChoiceBox<String> generoBox;

    private String[] generos = {"Masculino", "Feminino"};

    @FXML
    private DatePicker nascimentoField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField ruaField;

    @FXML
    private TextField setorField;

    @FXML
    private TextField telefoneField;

    @FXML
    private Button voltarBT;

    private List<Medico> listaMedicos = new ArrayList<>();

    @FXML
    void confirmarCadastro(ActionEvent event) throws IOException 
    {
        try 
        {
            if (nomeField.getText().isEmpty() || nascimentoField.getValue() == null || generoBox.getValue() == null) 
            {
                erroMsg.setText("ERRO! Preencha todos os campos obrigatórios!");
                return;
            }

            String nome = nomeField.getText();
            LocalDate nascimentoLocalDate = nascimentoField.getValue();
            Date nascimento = Date.from(nascimentoLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String generoStr = generoBox.getValue();
            Genero genero = generoStr.equals("Masculino") ? Genero.MASCULINO : Genero.FEMININO;

            String rua = ruaField.getText();
            int numero = Integer.parseInt(numeroField.getText());
            String bairro = bairroField.getText();
            String cidade = cidadeField.getText();
            String estado = estadoField.getText();
            int cep = Integer.parseInt(cepField.getText());
            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

            String telefone = telefoneField.getText();
            String celular = celularField.getText();
            String email = emailField.getText();
            ContatoTelEmail contato = new ContatoTelEmail(telefone, celular, email);

            String setor = setorField.getText();
            int chSemanal = Integer.parseInt(chSemanalField.getText());
            AtendenteHospitalar atendente = new AtendenteHospitalar(setor, chSemanal, new DadoPessoal(nome, nascimento, endereco, contato, genero));

            long idMedico = listaMedicos.size() + 1;
            int crm = Integer.parseInt(crmField.getText());
            String areaEspecialidade = areaEspecialidadeField.getText();
            boolean cirurgiao = cirurgiaoBox.isSelected();

            Medico novoMedico = new Medico(idMedico, crm, areaEspecialidade, cirurgiao, atendente);

            listaMedicos.add(novoMedico);
            MedicoServiceJSON.salvar(listaMedicos);
            MedicoServiceXML.salvar(listaMedicos);

            limparCampos();
            exibirMsgInfo();
            App.setRoot("telamedicos");
        } catch (NumberFormatException e) {
            erroMsg.setText("Erro: Certifique-se de preencher os campos numéricos corretamente.");
        } catch (Exception e) {
            erroMsg.setText("Erro ao cadastrar médico: " + e.getMessage());
        }
    }

    @FXML
    void voltarTelaMedico(ActionEvent event) throws IOException {
        App.setRoot("telamedicos");
    }

    private void limparCampos() {
        nomeField.clear();
        nascimentoField.setValue(null);
        generoBox.setValue(null);
        ruaField.clear();
        numeroField.clear();
        bairroField.clear();
        cidadeField.clear();
        estadoField.clear();
        cepField.clear();
        telefoneField.clear();
        celularField.clear();
        emailField.clear();
        setorField.clear();
        chSemanalField.clear();
        crmField.clear();
        areaEspecialidadeField.clear();
        cirurgiaoBox.setSelected(false);
    }

    private void exibirMsgInfo() {
        Alert msg_alerta = new Alert(Alert.AlertType.INFORMATION);
        msg_alerta.setTitle("");
        msg_alerta.setHeaderText("Cadastro Confirmado");
        msg_alerta.showAndWait();
    }
}
