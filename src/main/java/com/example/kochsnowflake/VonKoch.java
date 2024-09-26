package com.example.kochsnowflake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class VonKoch extends Application {
    Pane panel = new Pane();
    List<Line> lines = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        int x1 = 150, y1 = 200;
        double angle = 0;

        // Generate the lines for the von Koch curve
        snowflake(3, x1, y1, 300, angle);

        // Create a Timeline to draw the lines sequentially
        Timeline timeline = new Timeline();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50 * i), e -> panel.getChildren().add(line));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();

        Scene scene = new Scene(panel, 800, 600);
        stage.setTitle("Koch Curve");
        stage.setScene(scene);
        stage.show();
    }

    public void vonKoch(int n, double x0, double y0, double length, double angle) {
        if (n == 0) {
            double x1 = x0 + length * Math.cos(angle);
            double y1 = y0 - length * Math.sin(angle);

            Line line = new Line(x0, y0, x1, y1);
            line.setStrokeWidth(2);
            lines.add(line); // Add the line to the list instead of the panel
        } else {
            length /= 3.0;
            // First segment
            vonKoch(n - 1, x0, y0, length, angle);

            // Second segment
            double x1 = x0 + length * Math.cos(angle);
            double y1 = y0 - length * Math.sin(angle);
            vonKoch(n - 1, x1, y1, length, angle + Math.PI / 3);

            // Third segment
            double x2 = x1 + length * Math.cos(angle + Math.PI / 3);
            double y2 = y1 - length * Math.sin(angle + Math.PI / 3);
            vonKoch(n - 1, x2, y2, length, angle - Math.PI / 3);

            // Fourth segment
            double x3 = x2 + length * Math.cos(angle - Math.PI / 3);
            double y3 = y2 - length * Math.sin(angle - Math.PI / 3);
            vonKoch(n - 1, x3, y3, length, angle);
        }
    }
    public void snowflake(int n, double x, double y, double length, double angle) {
        vonKoch(n, x, y, length, angle);
        double x1 = x + length * Math.cos(angle);
        double y1 = y - length * Math.sin(angle);

        vonKoch(n, x1, y1, length, angle - 2 * Math.PI / 3);
        double x2 = x1 + length * Math.cos(angle - 2 * Math.PI / 3);
        double y2 = y1 - length * Math.sin(angle - 2 * Math.PI / 3);

        vonKoch(n, x2, y2, length, angle + 2 * Math.PI / 3);
    }

    public static double aTanAngle(double x1, double y1, double x2, double y2, double length) {
        double angle1 = Math.atan2(y1-y2, x1-x2);
        double angle2 = Math.atan2(y2 - (y1+length), x2 - x1+(length/2));
        return  angle1-angle2;
    }
}
