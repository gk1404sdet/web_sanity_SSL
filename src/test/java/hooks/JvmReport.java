package hooks;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JvmReport {

    public static void report(String jsonPath) {
        File reportOutputDirectory = new File("target/jvm-report");  // <-- better to use relative path

        Configuration config = new Configuration(reportOutputDirectory, "Shoppers Stop - Web Sanity");
        config.addClassifications("Platform", "Mac OS X");
        config.addClassifications("Browser", "Chrome");
        config.addClassifications("Environment ","Production");
        config.addClassifications("Tester", "Gopalakrishnan Palanisamy");

        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(jsonPath);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();
    }
}
