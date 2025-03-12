package prog3.apphospital2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class TelaInicialController {
    

    @FXML
    public void initialize() 
    {
        // Configuração inicial
        jsonRadioBT.setSelected(true); // Define JSON como o padrão
        AppState.setFileMode("JSON");

        // Listener para alteração do modo de arquivo
        modoArquivo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> 
        {
            if (newValue == xmlRadioBT) 
            {
                AppState.setFileMode("XML");
            } 
            else if (newValue == jsonRadioBT) 
            {
                AppState.setFileMode("JSON");
            }
        });
    }

    @FXML
    private void telaPaciente() throws IOException 
    {
        App.setRoot("telapacientes");
    }
    
    @FXML
    void openTelaEnfermeiros() throws IOException 
    {
        App.setRoot("telaenfermeiros");
    }
    
    @FXML
    void openTelaMedicos() throws IOException 
    {
        App.setRoot("telamedicos");
    }
    
    @FXML
    void openTelaConsultas() throws IOException
    {
        App.setRoot("telaconsultas");
    }
    
    
    @FXML
    private ToggleGroup modoArquivo;

    @FXML
    private RadioButton xmlRadioBT;
    
    @FXML
    private RadioButton jsonRadioBT;
    
    
}
