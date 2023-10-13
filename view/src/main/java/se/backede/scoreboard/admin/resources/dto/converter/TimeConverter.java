import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("timeConverter")
public class TimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Konvertera från strängformatet "XX min YY sek ZZ hundradelar" till Long
        try {
            String[] parts = value.split(" ");
            long minutes = Long.parseLong(parts[0].replace("min", "").trim());
            long seconds = Long.parseLong(parts[2].replace("sek", "").trim());
            long millis = Long.parseLong(parts[4].replace("hundradelar", "").trim());
            return (minutes * 60 * 1000) + (seconds * 1000) + millis;
        } catch (Exception e) {
            // Om det finns ett fel i konverteringen, returnera 0L istället för null
            return 0L;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            long totalMillis = (Long) value;
            long minutes = (totalMillis / 1000) / 60;
            long seconds = (totalMillis / 1000) % 60;
            long millis = totalMillis % 1000;
            return String.format("%02d:%02d:%03d", minutes, seconds, millis);
        }
        // Om värdet inte är en instans av Long, returnera en tom sträng
        return "";
    }
}
