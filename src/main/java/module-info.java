module prog3.apphospital2 
{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jakarta.xml.bind;
    requires org.glassfish.jaxb.runtime;

    opens prog3.apphospital2 to javafx.fxml;
    opens prog3.appbackend.models to com.google.gson, javafx.base, jakarta.xml.bind, org.glassfish.jaxb.runtime;
    opens prog3.appbackend.services to com.google.gson, jakarta.xml.bind, org.glassfish.jaxb.runtime;
    opens prog3.appbackend.enums to com.google.gson, jakarta.xml.bind, org.glassfish.jaxb.runtime;
    
    
    exports prog3.apphospital2;
    exports prog3.appbackend.enums;
    exports prog3.appbackend.models;
    
}
