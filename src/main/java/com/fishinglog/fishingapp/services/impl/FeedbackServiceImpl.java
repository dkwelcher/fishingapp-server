package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.services.FeedbackService;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final String FEEDBACK_FILE_PATH = "feedback.txt";
    @Override
    public boolean collectFeedback(String feedback) {
        try (FileWriter fileWriter = new FileWriter(FEEDBACK_FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.println(feedback);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
