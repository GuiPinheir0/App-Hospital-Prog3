package prog3.apphospital2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

import prog3.appbackend.models.Paciente;
import prog3.appbackend.services.PacienteServiceJSON;
import prog3.appbackend.services.PacienteServiceXML;


public class TelaPacientesController implements Initializable{
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        
        // Decide qual método de carregamento usar com base no estado
        if (AppState.getFileMode().equals("JSON")) 
        {
            listaPacientes = PacienteServiceJSON.carregar();
        } 
        else if (AppState.getFileMode().equals("XML")) 
        {
            listaPacientes = PacienteServiceXML.carregar();
        }
        
         // Configuração das colunas
        idC.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        nomeC.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto")); // Herdado de DadoPessoal
        idadeC.setCellValueFactory(new PropertyValueFactory<>("idade"));
        celularC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getContato().getCelular())); // Herdado
        emailC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getContato().getEmail())); // Herdado
        enderecoC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getEndereco().toString())); // Herdado
        generoC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getGenero().toString())); // Herdado
        obsC.setCellValueFactory(new PropertyValueFactory<>("obsGeral"));
        dataNascimentoC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getDataNascimento().toString())); // Herdado
        dataCadastroC.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getDataCadastro().toString()));

        // Adicionar os dados na tabela
        tabelaPacientes.getItems().setAll(listaPacientes);
    }
    
    
    private List<Paciente> listaPacientes = new ArrayList<>();

    @FXML
    private TableColumn<?, ?> Selecionar;

    @FXML
    private TableView<Paciente> tabelaPacientes;

    @FXML
    private TableColumn<Paciente, Long> idC;

    @FXML
    private TableColumn<Paciente, String> nomeC;

    @FXML
    private TableColumn<Paciente, Integer> idadeC;

    @FXML
    private TableColumn<Paciente, String> celularC;

    @FXML
    private TableColumn<Paciente, String> emailC;

    @FXML
    private TableColumn<Paciente, String> enderecoC;

    @FXML
    private TableColumn<Paciente, String> generoC;

    @FXML
    private TableColumn<Paciente, String> obsC;

    @FXML
    private TableColumn<Paciente, String> dataNascimentoC;

    @FXML
    private TableColumn<Paciente, String> dataCadastroC;

    
    @FXML
    void telaCadastroPaciente()throws IOException
    {
        App.setRoot("telacadastropacientes");
    }
    
    @FXML
    void voltarMenu()throws IOException 
    {
        App.setRoot("telainicial");
    }
    
    @FXML
    void excluirPaciente() throws IOException 
    {
        // Obtém o paciente selecionado na tabela
        Paciente pacienteSelecionado = tabelaPacientes.getSelectionModel().getSelectedItem();

        
        if (pacienteSelecionado == null) 
        {
            exibirMsgInfo("Por favor, selecione um paciente para excluir.");
            return;
        }

        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir este paciente?");
        alert.setContentText("Paciente: " + pacienteSelecionado.getNomeCompleto());

        
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");

        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        // Espera pela resposta do usuário
        ButtonType resposta = alert.showAndWait().orElse(botaoNao);

        
        if (resposta == botaoSim) 
        {
            listaPacientes.remove(pacienteSelecionado); // Remove da lista

            PacienteServiceJSON.salvar(listaPacientes); // Atualiza o arquivo JSON
            PacienteServiceXML.salvar(listaPacientes); // Atualiza o arquivo XML

            tabelaPacientes.getItems().setAll(listaPacientes); // Atualiza a tabela

            exibirMsgInfo("Paciente excluído com sucesso."); 
        }
    }
    
    @FXML
    void editarPaciente() throws IOException 
    {
        
        Paciente pacienteSelecionado = tabelaPacientes.getSelectionModel().getSelectedItem();

        
        if (pacienteSelecionado == null)
        {
            exibirMsgInfo("Por favor, selecione um paciente para editar.");
            return;
        }

        // Passa o paciente para o próximo controlador
        TelaEditarPacientesController.setPacienteParaEditar(pacienteSelecionado);

        
        App.setRoot("telaeditarpacientes");
    }
    
    
    @FXML
    void telaHistoricoPaciente() throws IOException 
    {
        
        Paciente pacienteSelecionado = tabelaPacientes.getSelectionModel().getSelectedItem();

        
        if (pacienteSelecionado == null) {
            exibirMsgInfo("Por favor, selecione um paciente para ver o histórico.");
            return;
        }

        // Passa o paciente selecionado para o controlador da tela de histórico
        TelaHistoricoPacienteController.setPacienteSelecionado(pacienteSelecionado);
        App.setRoot("telahistoricopaciente");
    }



    
    private void exibirMsgInfo(String msg) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}