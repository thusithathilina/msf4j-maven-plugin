package org.wso2.msf4j.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Goal which touches a timestamp file.
 *
 */
@Mojo(name = "run", defaultPhase = LifecyclePhase.PACKAGE)
@Execute(phase = LifecyclePhase.PACKAGE)
public class MSF4JRunMojo extends AbstractMojo {

    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(property = "microservice.mainClass", required = true)
    private String mainClass;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    private static final Log logger = new SystemStreamLog();

    public void execute() throws MojoExecutionException {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            String jarFile = project.getBuild().getDirectory() + "/" + project.getBuild().getFinalName() + ".jar";
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile);
            processBuilder.redirectErrorStream(true);
            Process start = processBuilder.start();
            Thread.sleep(1000);
            logger.info("Starting " + jarFile);
            inputStream = start.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while (reader.ready()) {
                logger.info(reader.readLine());
            }
            start.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("Error while closing BufferedReader.", e);
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    logger.error("Error while closing InputStreamReader.", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Error while closing InputStream.", e);
                }
            }
        }
    }
}
