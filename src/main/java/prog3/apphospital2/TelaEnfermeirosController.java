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

import prog3.appbackend.models.Enfermeiro;
import prog3.appbackend.services.EnfermeiroServiceJSON;
import prog3.appbackend.services.EnfermeiroServiceXML;

public class TelaEnfermeirosController implements Initializable {

    private List<Enfermeiro> listaEnfermeiros = new ArrayList<>();

    @FXML
    private TableView<Enfermeiro> tabelaEnfermeiros;

    @FXML
    private TableColumn<Enfermeiro, Long> idC;

    @FXML
    private TableColumn<Enfermeiro, String> nomeC;

    @FXML
    private TableColumn<Enfermeiro, String> celularC;

    @FXML
    private TableColumn<Enfermeiro, String> emailC;

    @FXML
    private TableColumn<Enfermeiro, String> enderecoC;

    @FXML
    private TableColumn<Enfermeiro, String> generoC;

    @FXML
    private TableColumn<Enfermeiro, String> dataNascimentoC;

    @FXML
    private TableColumn<Enfermeiro, String> treinadoOpRXC;

    @FXML
    private TableColumn<Enfermeiro, String> chSemanalC;

    @FXML
    private TableColumn<Enfermeiro, String> SetorC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Decide qual método de carregamento usar com base no estado
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaEnfermeiros = EnfermeiroServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaEnfermeiros = EnfermeiroServiceXML.carregar();
        }

        // Configuração das colunas
        idC.setCellValueFactory(new PropertyValueFactory<>("idEnfermeiro"));
        nomeC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getNomeCompleto()));
        celularC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getContato().getCelular()));
        emailC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getContato().getEmail()));
        enderecoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getEndereco().toString()));
        generoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getGenero().toString()));
        dataNascimentoC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDadoPessoal().getDataNascimento().toString()));
        treinadoOpRXC.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().isTreinadoOpRX() ? "Sim" : "Não"));
        chSemanalC.setCellValueFactory(data ->
            new SimpleStringProperty(String.valueOf(data.getValue().getChSemanal())));
        SetorC.setCellValueFactory(new PropertyValueFactory<>("setor"));

        // Adicionar os dados na tabela
        tabelaEnfermeiros.getItems().setAll(listaEnfermeiros);
    }

    @FXML
    void telaCadastroEnfermeiro() throws IOException {
        App.setRoot("telacadastroenfermeiros");
    }

    @FXML
    void voltarMenu() throws IOException {
        App.setRoot("telainicial");
    }

    @FXML
    void excluirEnfermeiro() throws IOException {
        Enfermeiro enfermeiroSelecionado = tabelaEnfermeiros.getSelectionModel().getSelectedItem();

        if (enfermeiroSelecionado == null) {
            exibirMsgInfo("Por favor, selecione um enfermeiro para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir este enfermeiro?");
        alert.setContentText("Enfermeiro: " + enfermeiroSelecionado.getDadoPessoal().getNomeCompleto());

        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        ButtonType resposta = alert.showAndWait().orElse(botaoNao);

        if (resposta == botaoSim) {
            listaEnfermeiros.remove(enfermeiroSelecionado);
            EnfermeiroServiceJSON.salvar(listaEnfermeiros);
            EnfermeiroServiceXML.salvar(listaEnfermeiros);
            tabelaEnfermeiros.getItems().setAll(listaEnfermeiros);
            exibirMsgInfo("Enfermeiro excluído com sucesso.");
        }
    }

    @FXML
    void editarEnfermeiro() throws IOException {
        Enfermeiro enfermeiroSelecionado = tabelaEnfermeiros.getSelectionModel().getSelectedItem();

        if (enfermeiroSelecionado == null) {
            exibirMsgInfo("Por favor, selecione um enfermeiro para editar.");
            return;
        }

        TelaEditarEnfermeirosController.setEnfermeiroParaEditar(enfermeiroSelecionado);
        App.setRoot("telaeditarenfermeiros");
    }

    private void exibirMsgInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}


