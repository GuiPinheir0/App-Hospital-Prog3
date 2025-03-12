package prog3.appbackend.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.concurrent.atomic.AtomicLong;

import prog3.appbackend.models.Paciente;

public class PacienteServiceJSON {

    public static List<Paciente> carregar() 
    {
        File arquivoJson = new File("pacientes.json");

        // Verifica se o arquivo não existe ou está vazio
        if (!arquivoJson.exists() || arquivoJson.length() == 0) 
        {
            System.out.println("Arquivo de pacientes não existe ou está vazio.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivoJson)) 
        {
            Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

            Type listType = new TypeToken<ArrayList<Paciente>>() {}.getType();

            // Verifica se há conteúdo para parse
            if (arquivoJson.length() == 0) 
            {
                System.out.println("Arquivo de pacientes está vazio.");
                return new ArrayList<>();
            }

            // Faz o parse diretamente
            List<Paciente> listaPacientes = gson.fromJson(reader, listType);
            System.out.println("Lista de pacientes carregada do arquivo 'pacientes.json'.");
            return listaPacientes != null ? listaPacientes : new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("Erro ao carregar pacientes de JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public static void salvar(List<Paciente> listaPacientes) 
    {
        // Verifica se a lista está vazia
        if (listaPacientes == null || listaPacientes.isEmpty()) 
        {
            System.out.println("Lista de pacientes está vazia. Nada para salvar.");
            return;
        }

        try (FileWriter writer = new FileWriter("pacientes.json")) 
        {
            Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd")
                .create();
            gson.toJson(listaPacientes, writer);
            System.out.println("Lista de pacientes salva no arquivo 'pacientes.json'.");
        }   
        catch (IOException e) 
        {
            System.err.println("Erro ao salvar pacientes em JSON: " + e.getMessage());
        }
    }
    
    public static void atualizarPaciente(Paciente pacienteEditado) 
    {
        List<Paciente> listaPacientes = carregar();

        // Localiza e atualiza o paciente na lista
        for (int i = 0; i < listaPacientes.size(); i++) 
        {
            if (listaPacientes.get(i).getIdPaciente() == pacienteEditado.getIdPaciente()) 
            {
                listaPacientes.set(i, pacienteEditado);
                break;
            }
        }

        salvar(listaPacientes); 
    }
    



    private static final String CONTADOR_ID_PATH = "contador_id.json";
    private static AtomicLong contadorId = new AtomicLong();

  
    static 
    {
        try 
        {
            File file = new File(CONTADOR_ID_PATH);
            if (file.exists()) 
            {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                contadorId.set(Long.parseLong(line));
                reader.close();
            } 
            else 
            {
                contadorId.set(1); // Inicia com 1 se o arquivo não existir
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            contadorId.set(1); 
        }
    }

    // Salva o contador no arquivo
    private static void salvarContador() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTADOR_ID_PATH))) 
        {
            writer.write(String.valueOf(contadorId.get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retorna o próximo ID único
    public static long gerarIdUnico() 
    {
        long id = contadorId.getAndIncrement();
        salvarContador(); // Atualiza o arquivo do contador
        return id;
    }
    
    
}



