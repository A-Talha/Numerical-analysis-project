package com.example.optimization;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class phase1code {
    private int i = 0;
    private String methodc = "Gauss Elimination";
    private String numEq = "0";
    private ArrayList<Double> row = new ArrayList();
    private ArrayList<Double> iterateVec = new ArrayList();
    private ArrayList<ArrayList<Double>> All = new ArrayList<>();
    private ArrayList<ArrayList<Double>> sol2D = new ArrayList<>();
    private ArrayList<Double> sol1D = new ArrayList<>();
    private ArrayList<Double> sol = new ArrayList<>();
    private int k = 0;
    private int j = 0;
    private int it = 0;
    private String in = "Manual";
    private File file ;
    private String itchoice = "10";
    private int itunput =2;
    private int precition = 5;
    private String sols;
    private Double free;
    private String LUchoice = "Cholesky Form";
    private boolean scaleflag = true;
    private String scalechoice = "yes";

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
    ChoiceBox scale = new ChoiceBox();
    @FXML
    Button text = new Button();
    @FXML
    ChoiceBox<String> formatlu = new ChoiceBox<>();
    @FXML
    Label formatLu = new Label();
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
    TextField inputit = new TextField();
    @FXML
    Label veciterate = new Label();
    @FXML
    Label itOrrela = new Label();
    @FXML
    TextField itOrrelaC = new TextField();
    @FXML
    Button numiterations = new Button();
    @FXML
    Button relativeerror = new Button();
    @FXML
    VBox page3 = new VBox();

    @FXML
    Label input = new Label();
    @FXML
    ChoiceBox inputc = new ChoiceBox();
    @FXML
    VBox page2 = new VBox();
    @FXML
    Button Enter = new Button();
    @FXML
    Label element = new Label();
    @FXML
    TextField elements = new TextField();
    @FXML
    Button resset = new Button();

    @FXML
    TextField equations_num = new TextField();
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
    //abstractHelper.isDouble
    @FXML
    protected void readtext() throws FileNotFoundException {
        re();
        AbstractHelper abstractHelper = new AbstractHelper();
        // pass the path to the file as a parameter
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("text","*.txt")
        );
        File file = fc.showOpenDialog(null);
        if(file!= null){
            ArrayList<String> tl = new ArrayList();
            ArrayList<ArrayList<String>> sl = new ArrayList<>();
            boolean flagt = false;
            boolean flagf = false;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String[] s = sc.nextLine().split(",| |_ ");
                for (int l = 0; l < s.length; l++) {
                    tl.add(s[l]);
                }
                sl.add(new ArrayList<>(tl));
                tl.clear();
            }
            System.out.println(sl);
            for ( k = 0; k < sl.size() ; k++) {
                for (j = 0; j<sl.get(k).size();++j){
                    if (abstractHelper.isDouble(sl.get(k).get(j))|| abstractHelper.isInteger(sl.get(k).get(j))){
                        row.add(Double.valueOf(sl.get(k).get(j)));
                    }
                    else{
                        flagt = true;
                    }
                }
                while (row.size()< Integer.parseInt(numEq)+1){
                    row.add(0.0);
                }
                All.add(new ArrayList<>(row));
                row.clear();
                if (flagt){break;}
            }
            while (row.size()< Integer.parseInt(numEq)+1){
                row.add(0.0);
            }
            while (All.size()<Integer.parseInt(numEq)){
                All.add(new ArrayList<>(row));
            }
            for (int l = 0; l < All.size(); l++) {
                if(All.get(l).size()-1>Integer.parseInt(numEq)){
                    abstractHelper.displayError("file problem","wrong variables number");
                    flagf = true;
                    break;
                }
            }
            if (!flagf) {
                if (flagt) {
                    abstractHelper.displayError("file problem", "the text file contains a non numeric value");
                    re();
                } else {
                    System.out.println(All);
                    next.setVisible(true);
                    element.setText("matrix is full");
                    Enter.setVisible(false);
                    elements.setVisible(false);
                }
            }

        }
        else{
            abstractHelper.displayError("file problem","please choose a file");
        }
    }


    //"Gauss Elimination", "Gauss Jordan", "LU Decomposition", "Gauss Seidil", "Jacobi Iteration"
    @FXML
    protected void solving(){
        Implementation s = new Implementation();
        ImplementationHelper ih = new ImplementationHelper();
        Clock cl = Clock.systemDefaultZone();
        double st,end;
        st = cl.millis();
        switch(methodc) {
            case "Gauss Elimination":
                sol2D = s.firstMethod(new ArrayList<>(All),precition);
                if(sol2D.get(0).get(0)==1){
                    sol = new ArrayList<>(sol2D.get(1));
                    answer.setText("sol:"+sol+"\n");
                }
                else if (sol2D.get(0).get(0)==2){
                    free = sol2D.get(0).get(1);
                    sol = new ArrayList<>(sol2D.get(1));
                    answer.setText("infinite number of solutions"+"\n"+"number of free variables: "+free+"\n");
                    //answer.setText("number of free variables:"+free+"\n"+"solution at free variable  0 ="+sol+"\n");
                }
                else {
                    answer.setText("the system is inconsistent"+"\n");
                }
                break;
            case "Gauss Jordan":
                sol2D = s.secondMethod(new ArrayList<>(All),precition);
                if(sol2D.get(0).get(0)==1){
                    sol = new ArrayList<>(sol2D.get(1));
                    answer.setText("sol:"+sol+"\n");
                }
                else if (sol2D.get(0).get(0)==2){
                    free = sol2D.get(0).get(1);
                    sol = new ArrayList<>(sol2D.get(1));
                    answer.setText("infinite number of solutions"+"\n"+"number of free variables: "+free+"\n");
                    //answer.setText("number of free variables:"+free+"\n"+"solution at free variable  0 ="+sol+"\n");
                }
                else {
                    answer.setText("the system is inconsistent"+"\n");
                }
                break;
            case "LU Decomposition":
                sol1D = s.thirdMethod(new ArrayList<>(All),LUchoice,precition,scaleflag);
                answer.setText(ih.showResult(sol1D,All.size(),LUchoice));
                break;
            case "Gauss Seidil":
                double[][] A = new double[All.size()][All.size()+1];
                double[] b = new double[iterateVec.size()];
                for(int i = 0; i < All.size(); i++)
                {
                    for(int j = 0; j < All.size()+1; j++)
                    {
                        A[i][j] = All.get(i).get(j);
                    }
                }
                for(int i = 0; i < iterateVec.size(); i++)
                {
                    b[i] = iterateVec.get(i);
                }
                if(itunput == 2) {
                    sol1D = s.FifthMethodI(A,b,Integer.parseInt(itchoice),precition);
                }
                else if (itunput == 1){
                    sol1D = s.FifthMethod(A,b,Double.valueOf(itchoice),precition);
                }
                if(sol1D.size()==1){
                    answer.setText("The system can't be solved using this method");
                }
                else {
                    String y = "it seems it will converge";
                    for(int i = 0; i < iterateVec.size(); i++)
                    {
                        b[i] = sol1D.get(i);
                    }
                    if (sol1D.get(iterateVec.size()+1)==0){
                        y = "this is the exact solution";
                    }
                    else if (sol1D.get(iterateVec.size()+1)>0.5){
                        y = "it seems it will diverge, choose another method ";
                    }
                    answer.setText("the solution is: "+ Arrays.toString(b) +"\n"+"numbrer of iterations "+sol1D.get(iterateVec.size())+"\n"+"the error is "+sol1D.get(iterateVec.size()+1)+"\n"+y);
                }
                break;
            case "Jacobi Iteration":
                double[][] A2 = new double[All.size()][All.size()+1];
                double[] b2 = new double[iterateVec.size()];
                for(int i = 0; i < All.size(); i++)
                {
                    for(int j = 0; j < All.size()+1; j++)
                    {
                        A2[i][j] = All.get(i).get(j);
                    }
                }
                for(int i = 0; i < iterateVec.size(); i++)
                {
                    b2[i] = iterateVec.get(i);
                }
                if(itunput == 2) {
                    sol1D = s.fourthMethodI(A2,b2,Integer.parseInt(itchoice),precition);
                }
                else if (itunput == 1){
                    sol1D = s.fourthMethod(A2,b2,Double.valueOf(itchoice),precition);
                }
                if(sol1D.size()==1){
                    answer.setText("The system can't be solved using this method");
                }
                else {
                    String y = "it seems it will converge";
                    for(int i = 0; i < iterateVec.size(); i++)
                    {
                        b2[i] = sol1D.get(i);
                    }
                    if (sol1D.get(iterateVec.size()+1)==0){
                        y = "this is the exact solution";
                    }
                    else if (sol1D.get(iterateVec.size()+1)>0.5){
                        y = "it seems it will diverge, choose another method ";
                    }
                    answer.setText("the solution is: "+ Arrays.toString(b2) +"\n"+"numbrer of iterations "+sol1D.get(iterateVec.size())+"\n"+"the error is "+sol1D.get(iterateVec.size()+1)+"\n"+y);
                }
                break;
            default:
                // code block
        }
        end = cl.millis();
        time.setText("time="+String.valueOf((end-st))+" mil");
    }
    @FXML
    protected void re2(){
        iterateVec.clear();
        it = 0;
        next.setVisible(false);
        veciterate.setText("enter vector elements");
        Enter2.setVisible(true);
        inputit.setVisible(true);

    }
    @FXML
    protected void relative(){
        itOrrela.setText("enter relative error");
        itOrrela.setVisible(true);
        itOrrelaC.setVisible(true);
        itOrrelaC.setText(itchoice);
        itunput = 1;

    }
    @FXML
    protected void numit(){
        itOrrela.setText("enter number of iterations");
        itOrrela.setVisible(true);
        itOrrelaC.setVisible(true);
        itOrrelaC.setText(itchoice);
        itunput = 2;

    }
    @FXML
    protected void re(){
        next.setVisible(false);
        k = 0;
        j = 0;
        element.setText("Enter element at (" + (k + 1) + "," + (j + 1) + ")");
        elements.setVisible(true);
        Enter.setVisible(true);
        All.clear();
        row.clear();
    }
    @FXML
    protected void next(){
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
    protected void next1() {
        back.setVisible(true);
        welcomeText.setVisible(false);
        page1.setVisible(true);
        method.getItems().setAll();
        formatlu.getItems().setAll();
        scale.getItems().setAll();
        //method = new  ChoiceBox();
        scale.getItems().addAll("yes","no");
        scale.setValue(scalechoice);
        method.getItems().addAll("Gauss Elimination", "Gauss Jordan", "LU Decomposition", "Gauss Seidil", "Jacobi Iteration");
        method.setValue(methodc);
        //formatlu = new ChoiceBox<>();
        formatlu.getItems().addAll("Cholesky Form", "Downlittle Form", "Crout Form");
        formatlu.setValue("Cholesky Form");
        equations_num.setText(numEq);
        Tpre.setText(String.valueOf(precition));
    }

    protected void next2() {
        AbstractHelper abstractHelper = new AbstractHelper();
        methodc = (String) method.getValue();
        LUchoice = formatlu.getValue();
        in = (String) inputc.getValue();
        scalechoice = (String) scale.getValue();
        if (abstractHelper.isInteger(Tpre.getText())) {
            if (Integer.parseInt(Tpre.getText()) < 0) {
                abstractHelper.displayError("precision error", "This is a joke, right? please insert a number bigger than one");
                --i;
                next1();
            }
            else{
                precition = Integer.parseInt(Tpre.getText());
            }
        }
        else {
            abstractHelper.displayError("precision error", "Wrong number format, Integer Needed.");
            --i;
            next1();
        }
        if (scalechoice.equals("yes")){
            scaleflag = true;
        }
        else{
            scaleflag = false;
        }
        if (false) {
        } else{
            //System.out.print(methodc);
            if (!numEq.equals(equations_num.getText())) {
                re();
            }
            numEq = equations_num.getText();
            //System.out.println(numEq);
            if (numEq.equals(""))
                numEq = "0";
            if (abstractHelper.isInteger(numEq)) {
                if (Integer.parseInt(numEq) <= 0) {
                    abstractHelper.displayError("number of variables error ", "This is a joke, right? please insert a number bigger than one");
                    --i;
                    next1();
                } else {
                    if (k < Integer.parseInt(numEq) ) {
                        next.setVisible(false);
                        page1.setVisible(false);
                        page2.setVisible(true);
                        System.out.println("k=" + k);
                        System.out.println("j=" + j);
                        Enter.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent t) {
                                String x = elements.getText();
                                System.out.println(x);
                                if (x.equals(""))
                                    x = "0";
                                if (k < Integer.parseInt(numEq)) {
                                    if (abstractHelper.isDouble(x)) {
                                        j++;
                                        row.add(Double.parseDouble(x));
                                        System.out.println(All);
                                        if (j == Integer.parseInt(numEq) + 1) {
                                            j = 0;
                                            All.add((ArrayList<Double>) row.clone());
                                            row.clear();
                                            k++;
                                        }
                                    } else {
                                        abstractHelper.displayError("matrix error", "Wrong number format, Double Needed.");
                                    }
                                }
                                if (k >= Integer.parseInt(numEq)) {
                                    next.setVisible(true);
                                    element.setText("matrix is full");
                                    Enter.setVisible(false);
                                    elements.setVisible(false);
                                } else {
                                    element.setText("Enter element at (" + (k + 1) + "," + (j + 1) + ")");
                                }
                            }
                        });
                    }
                    else {
                        next.setVisible(true);
                        page1.setVisible(false);
                        page2.setVisible(true);
                    }
                }
            } else {
                abstractHelper.displayError("number of variables error", "Wrong number format, Integer Needed.");
                --i;
                next1();
            }
        }
    }
    //"Jacobi Iteration"
    protected void next3(){
        methodc = (String) method.getValue();
        page2.setVisible(false);
        if(methodc.equals("Gauss Seidil")||methodc.equals("Jacobi Iteration")){
            nextiterate();
        }
        else{
            next();
        }
    }
    protected void next4(){
        itchoice = itOrrelaC.getText();
        pageiterate.setVisible(false);
        next.setVisible(false);
        page3.setVisible(true);
    }
    protected void nextiterate() {
        if (it < Integer.parseInt(numEq) ) {
            next.setVisible(false);
            page2.setVisible(false);
            itOrrelaC.setText(itchoice);
            pageiterate.setVisible(true);
            System.out.println("it=" + it);
            AbstractHelper abstractHelper = new AbstractHelper();
            Enter2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    String x = inputit.getText();
                    System.out.println(x);
                    if (x.equals(""))
                        x = "1";
                    if (it < Integer.parseInt(numEq)) {
                        if (abstractHelper.isDouble(x)) {
                            it++;
                            iterateVec.add(Double.parseDouble(x));

                        } else {
                            abstractHelper.displayError("Error :(", "Wrong number format, Double Needed.");
                        }
                    }
                    if (it >= Integer.parseInt(numEq)) {
                        next.setVisible(true);
                        veciterate.setText("vector is full");
                        Enter2.setVisible(false);
                        inputit.setVisible(false);
                    }
                }
            });
        }
        else {
            page2.setVisible(false);
            pageiterate.setVisible(true);
            next.setVisible(true);
            veciterate.setText("vector is full");
            Enter2.setVisible(false);
            inputit.setVisible(false);
        }
    }

    @FXML
    protected void back(){
        switch(i) {
            case 1:
                --i;
                back1();
                break;
            case 2:
                --i;
                back2();
                break;
            case 3:
                --i;
                back3();
                break;
            case 4:
                --i;
                back4();
                break;
            default:
                // code block
        }
    }

    private void back4() {
        page3.setVisible(false);
        next.setVisible(true);
        if(methodc.equals("Gauss Seidil")||methodc.equals("Jacobi Iteration")){
            pageiterate.setVisible(true);
        }
        else {
            pageiterate.setVisible(false);
            back();
        }
    }

    private void back3() {
        pageiterate.setVisible(false);
        page2.setVisible(true);
    }


    private void back1() {
        back.setVisible(false);
        welcomeText.setVisible(true);
        page1.setVisible(false);
    }
    private void back2() {
        page1.setVisible(true);
        page2.setVisible(false);
        next.setVisible(true);
        System.out.println("k="+k);
        System.out.println("j="+j);
    }
}
