package com.awsdevops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.InetAddress;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String index() {
        return getHtmlResponse();
    }

    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"UP\"}";
    }

    private String getHtmlResponse() {
        try {
            String instanceId = System.getenv("INSTANCE_ID");
            if (instanceId == null || instanceId.isEmpty()) {
                // Fallback: try to get from EC2 metadata
                instanceId = getInstanceMetadata();
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostIp = InetAddress.getLocalHost().getHostAddress();

            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>AWS Free Tier DevOps</title>\n" +
                    "    <style>\n" +
                    "        * { margin: 0; padding: 0; box-sizing: border-box; }\n" +
                    "        body {\n" +
                    "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                    "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                    "            min-height: 100vh;\n" +
                    "            display: flex;\n" +
                    "            justify-content: center;\n" +
                    "            align-items: center;\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            background: white;\n" +
                    "            border-radius: 10px;\n" +
                    "            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);\n" +
                    "            padding: 40px;\n" +
                    "            max-width: 600px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        h1 {\n" +
                    "            color: #333;\n" +
                    "            margin-bottom: 20px;\n" +
                    "            font-size: 2.5em;\n" +
                    "        }\n" +
                    "        .badge {\n" +
                    "            display: inline-block;\n" +
                    "            background: #FF9900;\n" +
                    "            color: white;\n" +
                    "            padding: 8px 16px;\n" +
                    "            border-radius: 5px;\n" +
                    "            margin: 10px 0;\n" +
                    "            font-weight: bold;\n" +
                    "        }\n" +
                    "        .info-box {\n" +
                    "            background: #f5f5f5;\n" +
                    "            border-left: 4px solid #667eea;\n" +
                    "            padding: 15px;\n" +
                    "            margin: 20px 0;\n" +
                    "            text-align: left;\n" +
                    "            border-radius: 5px;\n" +
                    "        }\n" +
                    "        .info-box p {\n" +
                    "            margin: 10px 0;\n" +
                    "            font-size: 0.95em;\n" +
                    "            color: #555;\n" +
                    "        }\n" +
                    "        .label {\n" +
                    "            font-weight: bold;\n" +
                    "            color: #333;\n" +
                    "        }\n" +
                    "        .value {\n" +
                    "            color: #667eea;\n" +
                    "            font-family: 'Courier New', monospace;\n" +
                    "        }\n" +
                    "        .tech-stack {\n" +
                    "            display: flex;\n" +
                    "            justify-content: space-around;\n" +
                    "            margin-top: 30px;\n" +
                    "            flex-wrap: wrap;\n" +
                    "        }\n" +
                    "        .tech {\n" +
                    "            flex: 1;\n" +
                    "            min-width: 120px;\n" +
                    "            padding: 15px;\n" +
                    "            background: #f9f9f9;\n" +
                    "            border-radius: 5px;\n" +
                    "            margin: 5px;\n" +
                    "        }\n" +
                    "        .tech strong {\n" +
                    "            color: #764ba2;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            margin-top: 30px;\n" +
                    "            font-size: 0.85em;\n" +
                    "            color: #999;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h1>🚀 AWS DevOps Pipeline</h1>\n" +
                    "        <div class=\"badge\">✅ Deployed Successfully via AWS CodePipeline</div>\n" +
                    "        \n" +
                    "        <div class=\"info-box\">\n" +
                    "            <p><span class=\"label\">📅 Deployment Timestamp:</span></p>\n" +
                    "            <p><span class=\"value\">" + timestamp + "</span></p>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"info-box\">\n" +
                    "            <p><span class=\"label\">🖥️ Instance Details:</span></p>\n" +
                    "            <p>Hostname: <span class=\"value\">" + hostName + "</span></p>\n" +
                    "            <p>Private IP: <span class=\"value\">" + hostIp + "</span></p>\n" +
                    "            <p>Instance ID: <span class=\"value\">" + (instanceId != null ? instanceId : "N/A") + "</span></p>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"tech-stack\">\n" +
                    "            <div class=\"tech\">\n" +
                    "                <strong>Java 11</strong>\n" +
                    "            </div>\n" +
                    "            <div class=\"tech\">\n" +
                    "                <strong>Spring Boot</strong>\n" +
                    "            </div>\n" +
                    "            <div class=\"tech\">\n" +
                    "                <strong>AWS CodePipeline</strong>\n" +
                    "            </div>\n" +
                    "            <div class=\"tech\">\n" +
                    "                <strong>CloudFormation</strong>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>💰 Deployed on AWS Free Tier | 📊 Infrastructure as Code (IaC) Demo</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

        } catch (Exception e) {
            return "<h1>Error: " + e.getMessage() + "</h1>";
        }
    }

    private String getInstanceMetadata() {
        try {
            java.net.URL url = new java.net.URL("http://169.254.169.254/latest/meta-data/instance-id");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);

            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream()));
            String line = reader.readLine();
            reader.close();
            return line;
        } catch (Exception e) {
            return "Unknown";
        }
    }
}