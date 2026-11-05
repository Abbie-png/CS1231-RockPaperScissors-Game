package GameUI;

import GameLogic.rps;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class rpsUI extends Application {
    int[] score = {0, 0};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainScene(primaryStage, score);
    }
    public  void mainScene(Stage stage, int[] score) {
        stage.setTitle("RockPaperScissors");

        Pane pane = new Pane();
        VBox Vcolumn = new VBox(20);

        Label playerScore = createLabel("Your Score: " + rps.getScore(0, score), 20);
        Label PCScore = createLabel("My Score: " + rps.getScore(1, score), 20);
        Vcolumn.getChildren().addAll(playerScore, PCScore);

        Button paperBtn =  createButton("Paper", "handPaperPNG.png", 200, 200, 180);
        paperBtn.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(100));
        paperBtn.layoutYProperty().bind(pane.heightProperty().divide(4).subtract(100));
        paperBtn.setOnAction(e -> gameScene(stage, "paper", score));

        Button rockBtn =  createButton("Rock", "handRockPNG.png", 200, 200, 15);
        rockBtn.layoutXProperty().bind(pane.widthProperty().divide(32).multiply(10).subtract(100));
        rockBtn.layoutYProperty().bind(pane.heightProperty().divide(8).multiply(5).subtract(100));
        rockBtn.setOnAction(e -> gameScene(stage, "rock", score));

        Button scissorsBtn =
                createButton("Scissors", "handScissorsPNG.png", 200, 200, -15);
        scissorsBtn.layoutXProperty().bind(pane.widthProperty().divide(32).multiply(22).subtract(100));
        scissorsBtn.layoutYProperty().bind(pane.heightProperty().divide(8).multiply(5).subtract(100));
        scissorsBtn.setOnAction(e -> gameScene(stage, "scissors", score));

        pane.getChildren().addAll(paperBtn,rockBtn,scissorsBtn, Vcolumn);

        Scene scene = new Scene(pane, 600, 500);
        stage.setScene(scene);
        stage.show();

    }

    public void gameScene(Stage primaryStage, String player, int[] score) {
        StackPane sPane = new StackPane();

        Label result = new Label();
        result.setFont(Font.font("Times New Roman", 40));

        int playRound = rps.round(player, score);
        String choiceOfPC = getChoice(player, playRound);

        String message;
        if (playRound == 0)
            message = "You lost!";
        else if (playRound == 1)
            message = "You won!";
        else
            message = "It's a tie!";

        PauseTransition pause1 = new PauseTransition(Duration.seconds(0.75));

        typeText(result, "My choice is...", 90, () -> {
            pause1.setOnFinished(e -> {

                result.setText(choiceOfPC);

                PauseTransition pause2 = new PauseTransition(Duration.seconds(0.9));
                pause2.setOnFinished(e2 -> {
                    result.setText(message);

                    PauseTransition pause3 = new PauseTransition(Duration.seconds(1));
                    pause3.setOnFinished(e3 -> mainScene(primaryStage, score));
                    pause3.play();
                });
                pause2.play();


            });
            pause1.play();
        });

        sPane.getChildren().addAll(result);

        Scene scene = new Scene(sPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void typeText(Label label, String text, int delayMS, Runnable onComplete) {
        label.setText("");

        Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {

            final int index = i;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(index * delayMS),
                    e -> label.setText(text.substring(0, index + 1))
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(e -> {
            if (onComplete != null) {
                onComplete.run();
            }
        });

        timeline.play();
    }

    public static String getChoice(String player, int result) {

        if (result == 2) {
            player = player.substring(0, 1).toUpperCase() + player.substring(1);
            return player + ", too!";
        } //tie
        else if (result == 1) {
            if (player.equals("rock")) //player wins
                return "Scissors!";
            else if (player.equals("paper"))
                return "Rock!";
            else return "Paper!";
        }
        else {
            if (player.equals("rock"))
                return "Paper!";
            else if (player.equals("paper"))
                return "Scissors!";
            else return "Rock!";
        }
    }

    public static Button createButton(String name, String address, double width, double height, int rotation) {
        Button button = new Button();

        Image image = new Image("file:\\C:\\Users\\twili\\IdeaProjects\\RPS\\src" +
                "\\main\\java\\Images\\" + address);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.rotateProperty().bind(new SimpleDoubleProperty(rotation));

        String buttonStyle = "-fx-cursor: hand; -fx-background-color: transparent;";
        button.setStyle(buttonStyle);

        return button;
    }

    public static Label createLabel(String name, int size) {
        Label label = new Label();

        label.setText(name);
        label.setFont(Font.font("Times New Roman", 20));

        return label;
    }
}