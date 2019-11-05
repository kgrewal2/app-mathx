package main.src.controllers.Operator;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class UnaryOperator implements Operator {
    @Override
    public StackPane produceOperator() {

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(200, 50);
        stackPane.setAlignment(Pos.CENTER);

        float shapeWidth = 165;
        float shapeHeight = 50;

        Rectangle rectangle = new Rectangle(shapeWidth, 50);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setOpacity(0.8);
        rectangle.setFill(Paint.valueOf("#007AFF"));
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.setStrokeLineJoin(StrokeLineJoin.ROUND);

        HBox hBox = new HBox();
        hBox.setMinWidth(shapeWidth);
        hBox.setAlignment(Pos.CENTER);

        TextField input1 = new TextField();
        input1.setMinWidth(40);
        input1.setPrefWidth(40);

        Label operator = new Label("+");
        operator.setMinWidth(40);
        operator.setPrefWidth(40);
        operator.setAlignment(Pos.CENTER);
        operator.setStyle("-fx-font-size: 14");
        operator.setTextFill(Color.WHITE);


        TextField input2 = new TextField();
        input2.setMinWidth(40);
        input2.setPrefWidth(40);

        hBox.getChildren().addAll(input1, operator, input2);


        stackPane.getChildren().addAll(rectangle, hBox);

        return stackPane;
    }

    @Override
    public Pane produceLabel() {
        Pane labelPane = new Pane();
        labelPane.setPrefSize(200, 30);
        Label label = new Label("Unary Operators");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        labelPane.getChildren().addAll(label);
        return labelPane;
    }
}
