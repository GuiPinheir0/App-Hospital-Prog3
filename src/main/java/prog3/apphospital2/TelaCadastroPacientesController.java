package prog3.apphospital2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import prog3.appbackend.enums.Genero;
import prog3.appbackend.models.ContatoTelEmail;
import prog3.appbackend.models.DadoPessoal;
import prog3.appbackend.models.Endereco;
import prog3.appbackend.models.Paciente;
import prog3.appbackend.services.PacienteServiceJSON;
import prog3.appbackend.services.PacienteServiceXML;


public class TelaCadastroPacientesController implements Initializable {
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        generoBox.getItems().addAll(genero);
        
        // Decide qual método de carregamento usar com base no estado
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaPacientes = PacienteServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaPacientes = PacienteServiceXML.carregar();
        }
    }

    @FXML
    private TextField bairroField;

    @FXML
    private Button cadastrarBT;

    @FXML
    private TextField celularField;

    @FXML
    private TextField cepField;

    @FXML
    private TextField cidadeField;

    @FXML
    private TextField emailField;

    @FXML
    private Label erroMsg;

    @FXML
    private TextField estadoField;

    @FXML
    private ChoiceBox<String> generoBox;
    
    private String[] genero = {"Masculino", "Feminino"};

    @FXML
    private TextField idadeField;

    @FXML
    private DatePicker nascimentoField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField obsgeralField;

    @FXML
    private TextField ruaField;

    @FXML
    private TextField telefoneField;

    @FXML
    private Button voltarBT;
    
    private List<Paciente> listaPacientes = new ArrayList<>();

    @FXML
    void confirmarCadastro()throws IOException 
    {
        try 
        {
            // Validações
            if (nomeField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Nome' é obrigatório!");
                return;
            }

            if (nascimentoField.getValue() == null) {
                erroMsg.setText("ERRO! O campo 'Data de Nascimento' é obrigatório!");
                return;
            }

            if (generoBox.getValue() == null) {
                erroMsg.setText("ERRO! O campo 'Gênero' é obrigatório!");
                return;
            }

            if (idadeField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Idade' é obrigatório!");
                return;
            }

            if (ruaField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Rua' é obrigatório!");
                return;
            }

            if (numeroField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Número' é obrigatório!");
                return;
            }

            if (cidadeField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Cidade' é obrigatório!");
                return;
            }

            if (estadoField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'Estado' é obrigatório!");
                return;
            }

            if (cepField.getText().isEmpty()) {
                erroMsg.setText("ERRO! O campo 'CEP' é obrigatório!");
                return;
            }

            // Validate Contato (at least one method of contact)
            if (celularField.getText().isEmpty() || emailField.getText().isEmpty()) {
                erroMsg.setText("ERRO! Os campos 'Celular' e 'Email' são obrigatórios!");
                return;
            }
            else
            {

                String nome = nomeField.getText();
                LocalDate nascimentoLocalDate = nascimentoField.getValue();
                Date nascimento = java.util.Date.from(nascimentoLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

                int idade = Integer.parseInt(idadeField.getText());
                Date dataCadastro = new Date(); // Data atual
                String obsGeral = obsgeralField.getText();
                
                long idNovo = PacienteServiceJSON.gerarIdUnico();

                DadoPessoal dadoPessoal = new DadoPessoal(nome, nascimento, endereco, contato, genero); 
                Paciente novoPaciente = new Paciente(idNovo, idade, dataCadastro, obsGeral, dadoPessoal);
                
                novoPaciente.setHistoricoConsultasMedicas(new ArrayList<>());

                
                listaPacientes.add(novoPaciente);
                
                
                PacienteServiceJSON.salvar(listaPacientes);
                PacienteServiceXML.salvar(listaPacientes);
                

                
                limparCampos();
                
                
                
                System.out.println("Pacientes na lista antes de salvar: " + listaPacientes);
                exibirMsgInfo();
                App.setRoot("telapacientes");
                
                
            }

        } catch (NumberFormatException e) {
            erroMsg.setText("Erro: Certifique-se de preencher os campos numéricos corretamente.");
        } catch (Exception e) {
            erroMsg.setText("Erro ao cadastrar paciente: " + e.getMessage());
        }
    }

    // Método auxiliar para limpar os campos
    
    @FXML
    void voltarTelaPaciente()throws IOException
    {
        App.setRoot("telapacientes");
    }
    
    private void limparCampos() 
    {
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
        idadeField.clear();
        obsgeralField.clear();
    }
    
    private void exibirMsgInfo() throws IOException
    {
        Alert msg_alerta = new Alert(Alert.AlertType.INFORMATION);
        msg_alerta.setTitle(" ");
        msg_alerta.setHeaderText("Cadastro Confirmado");
        msg_alerta.showAndWait();
    }
    
}
