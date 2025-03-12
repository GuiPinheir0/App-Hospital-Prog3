package prog3.apphospital2;

public class AppState 
{
    private static String fileMode = "JSON"; // Valor padrão é JSON

    public static String getFileMode() 
    {
        return fileMode;
    }

    public static void setFileMode(String mode) 
    {
        fileMode = mode;
    }
}
