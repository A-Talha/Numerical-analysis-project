package com.example.optimization;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;

public class ploter {
    ArrayList <XYChart.Series> lines = new ArrayList<>();
    public void add(XYChart.Series x){
        lines.add(x);
    }
    public void clear(){
        lines.clear();
    }
    private XYChart.Series datapoints(double x0, double x1, Expression eq, double precision){
        XYChart.Series series = new XYChart.Series();
        Helper helper = new Helper();
        double fx;
        for (double i = x0; i < x1; i += 0.005) {
            eq.setVariable("x", i);
            fx = helper.set_precision(eq.evaluate() , (int) precision);
            series.getData().add(new XYChart.Data(i,fx));
        }
        return series;
    }

    public void draw(double x0,double x1,Expression eq,double precision,Boolean xy){
        NumberAxis xAxis = new NumberAxis(x0, x1, 0.1);
        NumberAxis yAxis = new NumberAxis   (-10, 10, 0.1);
        LineChart linechart = new LineChart(xAxis, yAxis);
        XYChart.Series seriesXY = new XYChart.Series();
        linechart.getData().add(datapoints(x0,x1,eq,precision));
        seriesXY.getData().add(new XYChart.Data(100, 100));
        seriesXY.getData().add(new XYChart.Data(-100, -100));
        for (int i = 0; i < lines.size(); i++) {
            linechart.getData().add(lines.get(i));
        }
        if (xy) {
            linechart.getData().add(seriesXY);
        }
        linechart.setPrefHeight(4000);
        linechart.setPrefWidth(4000);
        linechart.setCreateSymbols(false);
        ScrollPane root = new ScrollPane(linechart);
        Scene scene = new Scene(root, 1500, 700);
        Stage stage = new Stage();
        stage.setTitle("graph");
        stage.setScene(scene);
        stage.show();
    }
}
