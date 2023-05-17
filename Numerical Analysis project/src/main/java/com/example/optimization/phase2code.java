package com.example.optimization;
import com.example.optimization.AbstractHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class phase2code {
    private int i = 0;
    private String methodc = "Bisection";

    private double[] parameterValue = {.00001, 50};
    private double parameterValueToBeSent;
    private int precition = 5;
    private boolean scaleflag = true;
    private String scalechoice = "no. Iterations";
    private Expression EQUATION;
    private Expression DERIVATIVE;
    AbstractHelper abstractHelper = new AbstractHelper();

    private double x0;
    private double x1;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void gotomenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    Label answer1 = new Label();
    @FXML
    Label x0Label = new Label();
    @FXML
    Label x1Label = new Label();
    @FXML
    TextField x0Text = new TextField();
    @FXML
    TextField x1Text = new TextField();
    @FXML
    ChoiceBox scale = new ChoiceBox(); // will be iter or eps
    @FXML
    Button text = new Button();
    @FXML
    TextField Tpre = new TextField();
    @FXML
    Label time = new Label();
    @FXML
    Label answer = new Label();
    @FXML
    Button solve = new Button();
    @FXML
    Button reset2 = new Button();
    @FXML
    Button Enter2 = new Button();
    @FXML
    VBox pageiterate = new VBox();

    @FXML
    TextField parameter = new TextField();

    @FXML
    VBox page3 = new VBox();

    @FXML
    VBox page2 = new VBox();

    @FXML
    Label element = new Label();
    @FXML
    TextField elements = new TextField();
    @FXML
    Button resset = new Button();

    @FXML
    ChoiceBox method = new ChoiceBox();
    @FXML
    VBox page1 = new VBox();
    @FXML
    private Label welcomeText = new Label("welcome");
    @FXML
    private Button next = new Button();
    @FXML
    private Button back = new Button();
    @FXML
    TextField derivatives = new TextField();
    @FXML
    Label derivative = new Label();
    //abstractHelper.isDouble
    /** Last Step */

    // For entering the iterations, these are called


    @FXML
    void next(){
        switch(i) {
            case 0:
                ++i;
                next1();
                System.out.print(i);
                break;
            case 1:
                ++i;
                next2();
                break;
            case 2:
                ++i;
                next3();
                break;
            case 3:
                ++i;
                next4();
                break;
            default:
                // code block
        }
    }
    void next1() {
        welcomeText.setVisible(false);
        page2.setVisible(false);
        page1.setVisible(true);
        pageiterate.setVisible(false);
        page3.setVisible(false);
        back.setVisible(false);
        method.getItems().setAll();
        //formatlu.getItems().setAll();
        scale.getItems().setAll();
        //method = new  ChoiceBox();
        scale.getItems().addAll("no. Iteration", "Epsilon");
        scale.setValue("no. Iterations");
        method.getItems().addAll("Bisection", "False-Position", "Fixed point", "Newton-Raphson", "Secant Method");
        method.setValue(methodc);
        //parameter.setText(".00001");
        Tpre.setText(String.valueOf(precition));
        next.setOnAction(e->
                {
                    int x = next2();
                    if(x == 1)
                        next3();
                }
        );
    }


    int next2() {

        methodc = (String) method.getValue();
        scalechoice = (String) scale.getValue();
        /** Switching the value of parameter */
        if(scalechoice.equals("no. Iterations"))
        {
            parameter.setText("50");
            parameterValueToBeSent = 50;
        }
        else
        {
            parameter.setText(".00001");
            parameterValueToBeSent = .00001;
        }
        /** Getting the precison value */
        String precision = Tpre.getText();
        if(precision.equals(""))
            precision = "0";
        if (abstractHelper.isInteger(precision)) {
            if (Integer.parseInt(Tpre.getText()) < 0)
            {
                abstractHelper.displayError("precision error", "This is a joke, right? please insert a number bigger than one");
                --i;
                return 0;
            }
            else{
                precition = Integer.parseInt(precision);
                return 1;
            }
        }
        else {
            abstractHelper.displayError("precision error", "Wrong number format, Integer Needed.");
            --i;
            return 0;
        }
        /** Precision Done ! */
    }
    //"Getting the equation and the precision"
    void next3()
    {
        back.setVisible(true);
        derivative.setVisible(false);
        derivatives.setVisible(false);
        if(methodc.equals("Fixed point"))
        {
            derivative.setVisible(true);
            derivatives.setVisible(true);
        }
        page1.setVisible(false);
        page2.setVisible(true);
        pageiterate.setVisible(false);
        page3.setVisible(false);
        next.setOnAction(
                e->
                {
                    int x = next4();
                    if(x==0);
                    else
                        next5();
                }
        );
        back.setOnAction(
                e-> next1()
        );
    }
    int next4()
    {
        /** Getting the parameter value */
        parameterValue[0] = .00001; parameterValue[1] = 50;
        String paramet = parameter.getText();
        String exp = elements.getText();
        String deriv = derivatives.getText();
        if(parameter.equals(""))
            paramet = "0.0";
        if(exp.equals(""))
            exp = "0.0";
        if(deriv.equals(""))
            deriv = "0.0";
        if (abstractHelper.isDouble(paramet) && abstractHelper.isEquation(exp) && abstractHelper.isEquation(deriv)) {
            if (Double.parseDouble(paramet) <= 0)
            {
                abstractHelper.displayError("parameter error", "This is a joke, right? please insert a double bigger than 0");
                --i;
                return 0;
            }
            else{
                if(scalechoice.equals("no. Iterations"))
                    parameterValue[1]=Double.parseDouble(paramet);
                else
                    parameterValue[0]=Double.parseDouble(paramet);
                EQUATION = new ExpressionBuilder(exp).variables("x").build().setVariable("e", Math.E).setVariable("π", Math.PI);
                DERIVATIVE = new ExpressionBuilder(deriv).variables("x").build().setVariable("e", Math.E).setVariable("π", Math.PI);;
                abstractHelper.displayError("Great!", "Final Step");
                return 1;
            }
        }
        else {
            abstractHelper.displayError("Error", "Wrong input format.");
            --i;
            return 0;
        }
    }

    void next5()
    {
        back.setVisible(true);
        back.setOnAction(e->next3());
        x0Text.setText("");
        x1Text.setText("");
        System.out.println(methodc);
        if(methodc.equals("Fixed point") || methodc.equals("Newton-Raphson"))
        {
            x1Label.setVisible(false);
            x1Text.setVisible(false);
        }
        pageiterate.setVisible(true);
        page1.setVisible(false);
        page2.setVisible(false);
        page3.setVisible(false);
        next.setOnAction(
                e->
                {
                    int x = next6();
                    if(x==0);
                    else
                        next7();
                }
        );
        back.setOnAction(
                e->next3()
        );
    }
    int next6()
    {
        page3.setVisible(false);
        String x0 = x0Text.getText();
        String x1 = x1Text.getText();
        System.out.println(x0);
        System.out.println(x1);
        if(x0.equals(""))
            x0 = "0.0";
        if(x1.equals(""))
            x1 = "0.0";
        if (abstractHelper.isDouble(x0) && abstractHelper.isDouble(x1)) {
            this.x0 = Double.parseDouble(x0);
            this.x1 = Double.parseDouble(x1);;
            abstractHelper.displayError("Great!", "Let's solve");
            return 1;
        }
        else {
            abstractHelper.displayError("Error", "Wrong input format.");
            --i;
            return 0;
        }
    }

    void next7()
    {
        back.setOnAction(
                e->next5()
        );
        page1.setVisible(false);
        page2.setVisible(false);
        pageiterate.setVisible(false);
        page3.setVisible(true);
        Iimplementation s = new Implementation2();
        Clock cl = Clock.systemDefaultZone();
        double st,end;
        st = cl.millis();
        ArrayList <String> ans = new ArrayList<>();
        switch (methodc)
        {
            case "Bisection":
            {
                ans = s.bisection(EQUATION,x0,x1,parameterValue[0], precition);
                break;
            }
            case "False-Position":
            {
                ans =s.false_position(EQUATION,x0,x1,parameterValue[0], precition);
                break;
            }
            case  "Secant Method":
            {
                ans =s.secant(EQUATION,x0,x1,(int)parameterValue[1],precition,parameterValue[0]);
                break;
            }
            case "Fixed point":
            {
                System.out.println(parameterValue[0]+ " ?");                System.out.println(parameterValue[0]+ " ?");
                System.out.println(parameterValue[1]+ " ?");

                ans =s.fixed_point(EQUATION,x0,(int)parameterValue[1],precition,parameterValue[0]);
                break;
            }
            case "Newton-Raphson":
            {
                ans =s.newton(EQUATION, x0, (int)parameterValue[1], precition, parameterValue[0]);
            }
        }
        if(ans.get(0).equals("Convergent")){
            answer1.setText("the root ="+ans.get(1));
        }
        else {
            answer1.setText("the method diverge");
        }
        end = cl.millis();
        time.setText("time="+String.valueOf((end-st))+" mil");


    }





}
