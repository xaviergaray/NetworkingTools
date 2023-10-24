import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkTools {
    public static String GetConnectedPassword() {
        String networkPassword;
        try {
            // Get the currently connected network name
            String networkName = getConnectedNetworkName();

            if (networkName != null) {
                // Retrieve the password for the network
                networkPassword = getPasswordForNetwork(networkName);
            } else {
                return ("Not connected to any WiFi network.");
            }
        } catch (Exception e) {
           return(e.toString());
        }
        return networkPassword;
    }

    public static String getConnectedNetworkName() throws IOException {
        Process process = Runtime.getRuntime().exec("netsh wlan show interfaces");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("SSID")) {
                return line.split(":")[1].trim();
            }
        }
        return null;
    }

    public static String getPasswordForNetwork(String networkName) throws IOException {
        Process process = Runtime.getRuntime().exec("netsh wlan show profile name=\"" + networkName + "\" key=clear");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("Key Content")) {
                return(line.split(":")[1].trim());
            }
        }
        return null;
    }
}
