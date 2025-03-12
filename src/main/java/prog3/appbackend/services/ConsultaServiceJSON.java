
package prog3.appbackend.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import prog3.appbackend.models.ConsultaMedica;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConsultaServiceJSON {

    private static final String ARQUIVO_JSON = "consultas.json";

    public static List<ConsultaMedica> carregar() 
    {
        File arquivoJson = new File(ARQUIVO_JSON);

        if (!arquivoJson.exists() || arquivoJson.length() == 0) 
        {
            System.out.println("Arquivo de consultas não existe ou está vazio.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivoJson)) 
        {
            Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

            Type listType = new TypeToken<ArrayList<ConsultaMedica>>() {}.getType();
            List<ConsultaMedica> listaConsultas = gson.fromJson(reader, listType);
            return listaConsultas != null ? listaConsultas : new ArrayList<>();
        } 
        catch (IOException e) 
        {
            System.err.println("Erro ao carregar consultas de JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<ConsultaMedica> listaConsultas) 
    {
        if (listaConsultas == null || listaConsultas.isEmpty()) 
        {
            System.out.println("Lista de consultas está vazia. Nada para salvar.");
            return;
        }

        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) 
        {
            Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
            gson.toJson(listaConsultas, writer);
            System.out.println("Lista de consultas salva no arquivo '" + ARQUIVO_JSON + "'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas em JSON: " + e.getMessage());
        }
    }
    
    public static void atualizarConsulta(ConsultaMedica consultaEditada) 
    {
        List<ConsultaMedica> listaConsultas = carregar(); // Carrega todas as consultas do JSON

        // Localiza e atualiza a consulta na lista
        for (int i = 0; i < listaConsultas.size(); i++) 
        {
            if (listaConsultas.get(i).getIdConsulta() == consultaEditada.getIdConsulta()) 
            {
                listaConsultas.set(i, consultaEditada);
                break;
            }
        }

        salvar(listaConsultas); // Regrava o JSON com os dados atualizados
    }
    
    


}