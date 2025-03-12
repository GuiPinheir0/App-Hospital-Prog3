package prog3.apphospital2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import prog3.appbackend.models.Medico;
import prog3.appbackend.services.MedicoServiceJSON;
import prog3.appbackend.services.MedicoServiceXML;

public class TelaMedicosController implements Initializable {

    private List<Medico> listaMedicos = new ArrayList<>();

    @FXML
    private TableView<Medico> tabelaMedicos;

    @FXML
    private TableColumn<Medico, Long> idC;

    @FXML
    private TableColumn<Medico, String> nomeC;

    @FXML
    private TableColumn<Medico, String> celularC;

    @FXML
    private TableColumn<Medico, String> emailC;

    @FXML
    private TableColumn<Medico, String> enderecoC;

    @FXML
    private TableColumn<Medico, String> generoC;

    @FXML
    private TableColumn<Medico, String> dataNascimentoC;

    @FXML
    private TableColumn<Medico, String> crmC;

    @FXML
    private TableColumn<Medico, String> especialidadeC;

    @FXML
    private TableColumn<Medico, String> chSemanalC;

    @FXML
    private TableColumn<Medico, String> cirurgiaoC;

    @FXML
    private TableColumn<Medico, String> SetorC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Decide qual método de carregamento usar com base no estado
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaMedicos = MedicoServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaMedicos = MedicoServiceXML.carregar();
        }

        // Configuração das colunas
        idC.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        nomeC.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto")); // Herdado de DadoPessoal
        celularC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getContato().getCelular())); // Herdado
        emailC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getContato().getEmail())); // Herdado
        enderecoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getEndereco().toString())); // Herdado
        generoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getGenero().toString())); // Herdado
        dataNascimentoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDataNascimento().toString())); // Herdado
        crmC.setCellValueFactory(data ->
            new SimpleStringProperty(String.valueOf(data.getValue().getNumeroCRM())));
        especialidadeC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getAreasEspecialidade()));
        chSemanalC.setCellValueFactory(data ->
            new SimpleStringProperty(String.valueOf(data.getValue().getChSemanal())));
        cirurgiaoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().isCirurgiao() ? "Sim" : "Não"));
        SetorC.setCellValueFactory(new PropertyValueFactory<>("setor"));

        // Adicionar os dados na tabela
        tabelaMedicos.getItems().setAll(listaMedicos);
    }

    @FXML
    void telaCadastroMedico() throws IOException {
        App.setRoot("telacadastromedicos");
    }

    @FXML
    void voltarMenu() throws IOException {
        App.setRoot("telainicial");
    }

    @FXML
    void excluirMedico() throws IOException {
        // Obtém o médico selecionado na tabela
        Medico medicoSelecionado = tabelaMedicos.getSelectionModel().getSelectedItem();

        // Verifica se algum médico foi selecionado
        if (medicoSelecionado == null) {
            exibirMsgInfo("Por favor, selecione um médico para excluir.");
            return;
        }

        // Cria um alerta de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir este médico?");
        alert.setContentText("Médico: " + medicoSelecionado.getNomeCompleto());

        // Adiciona os botões de sim e não
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        // Espera pela resposta do usuário
        ButtonType resposta = alert.showAndWait().orElse(botaoNao);

        // Se o usuário clicar em "Sim", realiza a exclusão
        if (resposta == botaoSim) {
            listaMedicos.remove(medicoSelecionado); // Remove da lista

            MedicoServiceJSON.salvar(listaMedicos); // Atualiza o arquivo JSON
            MedicoServiceXML.salvar(listaMedicos);

            tabelaMedicos.getItems().setAll(listaMedicos); // Atualiza a tabela

            exibirMsgInfo("Médico excluído com sucesso.");
        }
    }

    @FXML
    void editarMedico() throws IOException {
        // Obtém o médico selecionado
        Medico medicoSelecionado = tabelaMedicos.getSelectionModel().getSelectedItem();

        // Verifica se há um médico selecionado
        if (medicoSelecionado == null) {
            exibirMsgInfo("Por favor, selecione um médico para editar.");
            return;
        }

        // Passa o médico para o próximo controlador
        TelaEditarMedicosController.setMedicoParaEditar(medicoSelecionado);

        // Navega para a nova tela
        App.setRoot("telaeditarmedicos");
    }

    // Método auxiliar para exibir mensagens informativas
    private void exibirMsgInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
