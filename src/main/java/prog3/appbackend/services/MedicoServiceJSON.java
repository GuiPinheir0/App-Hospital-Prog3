
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
import prog3.appbackend.models.Medico;

public class MedicoServiceJSON {

    public static List<Medico> carregar() 
    {
        File arquivoJson = new File("medicos.json");

        if (!arquivoJson.exists() || arquivoJson.length() == 0) 
        {
            System.out.println("Arquivo de médicos não existe ou está vazio.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivoJson)) 
        {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            Type listType = new TypeToken<ArrayList<Medico>>() {}.getType();
            List<Medico> listaMedicos = gson.fromJson(reader, listType);
            return listaMedicos != null ? listaMedicos : new ArrayList<>();
        } 
        catch (IOException e) 
        {
            System.err.println("Erro ao carregar médicos de JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<Medico> listaMedicos) 
    {
        if (listaMedicos == null || listaMedicos.isEmpty()) 
        {
            System.out.println("Lista de médicos está vazia. Nada para salvar.");
            return;
        }

        try (FileWriter writer = new FileWriter("medicos.json")) 
        {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            gson.toJson(listaMedicos, writer);
            System.out.println("Lista de médicos salva no arquivo 'medicos.json'.");
        } 
        catch (IOException e) 
        {
            System.err.println("Erro ao salvar médicos em JSON: " + e.getMessage());
        }
    }

    public static void atualizarMedico(Medico medicoEditado) 
    {
        List<Medico> listaMedicos = carregar();

        for (int i = 0; i < listaMedicos.size(); i++) 
        {
            if (listaMedicos.get(i).getIdMedico() == medicoEditado.getIdMedico()) 
            {
                listaMedicos.set(i, medicoEditado);
                break;
            }
        }

        salvar(listaMedicos);
    }
}
