import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Get the currently connected network name
            String networkName = getConnectedNetworkName();

            if (networkName != null) {
                // Retrieve the password for the network
                getPasswordForNetwork(networkName);
            } else {
                System.out.println("Not connected to any WiFi network.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void getPasswordForNetwork(String networkName) throws IOException {
        Process process = Runtime.getRuntime().exec("netsh wlan show profile name=\"" + networkName + "\" key=clear");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("Key Content")) {
                String password = line.split(":")[1].trim();
                System.out.println("Password for " + networkName + ": " + password);
                break;
            }
        }
    }
}