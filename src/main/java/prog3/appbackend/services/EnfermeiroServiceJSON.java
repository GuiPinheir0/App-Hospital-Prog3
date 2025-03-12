package prog3.appbackend.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import prog3.appbackend.models.Enfermeiro;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EnfermeiroServiceJSON {

    public static List<Enfermeiro> carregar() {
        File arquivoJson = new File("enfermeiros.json");

        if (!arquivoJson.exists() || arquivoJson.length() == 0) {
            System.out.println("Arquivo de enfermeiros não existe ou está vazio.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivoJson)) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            Type listType = new TypeToken<ArrayList<Enfermeiro>>() {}.getType();
            List<Enfermeiro> listaEnfermeiros = gson.fromJson(reader, listType);
            return listaEnfermeiros != null ? listaEnfermeiros : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar enfermeiros de JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(List<Enfermeiro> listaEnfermeiros) {
        if (listaEnfermeiros == null || listaEnfermeiros.isEmpty()) {
            System.out.println("Lista de enfermeiros está vazia. Nada para salvar.");
            return;
        }

        try (FileWriter writer = new FileWriter("enfermeiros.json")) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            gson.toJson(listaEnfermeiros, writer);
            System.out.println("Lista de enfermeiros salva no arquivo 'enfermeiros.json'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar enfermeiros em JSON: " + e.getMessage());
        }
    }

    public static void atualizarEnfermeiro(Enfermeiro enfermeiroEditado) {
        List<Enfermeiro> listaEnfermeiros = carregar();

        for (int i = 0; i < listaEnfermeiros.size(); i++) {
            if (listaEnfermeiros.get(i).getIdEnfermeiro() == enfermeiroEditado.getIdEnfermeiro()) {
                listaEnfermeiros.set(i, enfermeiroEditado);
                break;
            }
        }

        salvar(listaEnfermeiros);
    }
}

