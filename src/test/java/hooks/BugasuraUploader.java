package hooks;

public class BugasuraUploader {

    public static void uploadResultsToBugasura() {
        try {
            String command = "bugasura UPLOAD_RESULTS ./target/Reports/cucumber_Report.xml "
                    + "--api_key 8640ca1b2a22060ee6f9556a29d504541cef2fce "
                    + "--team_id 209 --project_id 11 --testrun_id 82 --server shoppersstop";

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            //prints output in console
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
            System.out.println("Bugasura upload complete.");
        } catch (Exception e) {
            System.err.println("Failed to upload to Bugasura: " + e.getMessage());
        }
    }
}

