package com.jsp.client.csvmover;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FileMove implements StepExecutionListener {

    private static final String PROCESSING_DIR = "csv/processing/";
    private static final String PROCESSED_DIR = "csv/output/";
    private static final String ERROR_DIR = "csv/errored/";

    @Override
    public void beforeStep(StepExecution stepExecution) {

        String fileName = stepExecution.getJobParameters().getString("fileName");

        if (fileName != null) {
            stepExecution.getExecutionContext().putString("fileName", fileName);
        } else {
            System.out.println("File name not found in job parameters.");
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        try {

            String fileName = stepExecution.getExecutionContext().getString("fileName", null);

            if (fileName == null) {
                System.out.println("No file found in execution context");
                return stepExecution.getExitStatus();
            }

            Path source = Paths.get(PROCESSING_DIR + fileName);

            // 🔴 FIX: Check if file exists before moving
            if (!Files.exists(source)) {
                System.out.println("File not found in processing folder: " + source);
                return stepExecution.getExitStatus();
            }

            Path target;

            if (stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
                target = Paths.get(PROCESSED_DIR + fileName);
            } else {
                target = Paths.get(ERROR_DIR + fileName);
            }

            Files.move(source, target);

            System.out.println("File moved to: " + target);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stepExecution.getExitStatus();
    }
}