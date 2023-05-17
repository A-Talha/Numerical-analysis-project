package com.example.optimization;

import javafx.scene.chart.XYChart;
import net.objecthunter.exp4j.Expression;

import java.time.Clock;
import java.util.ArrayList;

public class Implementation2 implements Iimplementation
{
    Helper helper = new Helper();
    private double f(Expression e, double x) {
        e.setVariable("x", x);
        return e.evaluate();
    }
    public ArrayList<String> bisection(Expression eq, double xl, double xu, double eps, int pre) {
        XYChart.Series seriesXY = new XYChart.Series();
        ploter pg = new ploter();
        //seriesXY.getData().add(new XYChart.Data(100, 100));
        //seriesXY.getData().add(new XYChart.Data(-100, -100));
        ArrayList<String> solve=new ArrayList<>();
        String x;
        double xr=0;
        if(Math.abs(xl-xu)<1e-10){
            eq.setVariable("x", xl);
            if(eq.evaluate()==0){
                System.out.println("root="+xl);
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xl, 100));
                seriesXY.getData().add(new XYChart.Data(xl, -100));
                pg.add(seriesXY);
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xu, 100));
                seriesXY.getData().add(new XYChart.Data(xu, -100));
                pg.add(seriesXY);
                pg.draw(xl-1,xl+1,eq,pre,false);
                solve.add("Convergent");
                x= String.valueOf(xl);
                solve.add(x);
                return solve;
            }
            solve.clear();
            solve.add("diverge");
            System.out.println("no root between xl,xu");
            return solve;
        }

        if(xl>xu){
            System.out.println("xl>xu please enter xl<xr");
            solve.clear();
            solve.add("diverge");
            return solve;
        }

        int number_it = (int) ((Math.log(xu - xl) - Math.log(eps)) / Math.log(2)),item_it = 0;
        double fun_xl, fun_xu, fun_xr;

        eq.setVariable("x", xl);
        fun_xl = helper.set_precision(eq.evaluate(),pre);
        eq.setVariable("x", xu);
        fun_xu = helper.set_precision(eq.evaluate(),pre);
        if(fun_xl*fun_xu>0){
            System.out.println("no root between xl,xu");
            solve.clear();
            solve.add("diverge");
            return solve;
        }

        while (item_it != number_it + 1) {
            System.out.println("(" + item_it + ")iteration");
            eq.setVariable("x", xl);
            fun_xl = helper.set_precision(eq.evaluate(),pre);
            eq.setVariable("x", xu);
            fun_xu = helper.set_precision(eq.evaluate(),pre);
            System.out.println("f(" + xl + ")=" + fun_xl);
            System.out.println("f(" + xu + ")=" + fun_xu);
            xr = helper.set_precision(((xu + xl) / 2), pre);
            eq.setVariable("x", xr);
            fun_xr = helper.set_precision(eq.evaluate(),pre);
            System.out.println("xr=" + xr);
            System.out.println("f(" + xr + ")=" + fun_xr);

            if (fun_xr * fun_xl < 0) {
                xu = xr;
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xl, 100));
                seriesXY.getData().add(new XYChart.Data(xl, -100));
                pg.add(seriesXY);
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xu, 100));
                seriesXY.getData().add(new XYChart.Data(xu, -100));
                pg.add(seriesXY);
            } else if (fun_xr * fun_xl > 0) {
                xl = xr;
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xl, 100));
                seriesXY.getData().add(new XYChart.Data(xl, -100));
                pg.add(seriesXY);
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xu, 100));
                seriesXY.getData().add(new XYChart.Data(xu, -100));
                pg.add(seriesXY);
            }  else if (Math.abs(xl-xu)<eps){
                System.out.println("root="+xl);
                solve.clear();
                solve.add("Convergent");
                solve.add(String.valueOf(xl));
                pg.draw(xl-5,xl+5,eq,pre,false);
                return solve;
            }
            item_it++;
        }

        System.out.println("root=" + xr);
        solve.clear();
        solve.add("Convergent");
        solve.add(String.valueOf(xr));
        pg.draw(xr-5,xr+5,eq,pre,false);
        return solve;


    }

    public ArrayList<String> false_position(Expression eq, double xl, double xu, double eps, int pre) {
        XYChart.Series seriesXY = new XYChart.Series();
        ploter pg = new ploter();
        ArrayList<String> solve=new ArrayList<>();
        double item_it = 0, xr=0,pre_xr=0;
        if(Math.abs(xl-xu)<1e-10){
            eq.setVariable("x", xl);
            if(eq.evaluate()==0){
                pg.draw(xl-1,xl+1,eq,pre,false);
                System.out.println("root="+xl);
                solve.add("Convergent");
                solve.add(String.valueOf(xl));
                return solve;
            }
            System.out.println("no root between xl,xu");
            solve.clear();
            solve.add("diverge");
            return solve;

        }

        if(xl>xu){
            pg.draw(xr-1,xr+1,eq,pre,false);
            System.out.println("xl>xu please enter xl<xr");
            solve.add("diverge");
            return solve;
        }

        double fun_xl, fun_xu, fun_xr;
        int j=0;
        eq.setVariable("x", xl);
        fun_xl = helper.set_precision(eq.evaluate(),pre);
        eq.setVariable("x", xu);
        fun_xu = helper.set_precision(eq.evaluate(),pre);
        if(fun_xl*fun_xu>0){
            pg.draw(xr-1,xr+1,eq,pre,false);
            System.out.println("no root between xl,xu");
            solve.clear();
            solve.add("diverge");
            return solve;
        }
        while (true) {
            System.out.println("(" + (j+1) + ")iteration");
            eq.setVariable("x", xl);
            fun_xl = helper.set_precision(eq.evaluate(),pre);
            eq.setVariable("x", xu);
            fun_xu = helper.set_precision(eq.evaluate(),pre);
            System.out.println("f(" + xl + ")=" + fun_xl);
            System.out.println("f(" + xu + ")=" + fun_xu);

            xr = helper.set_precision(((fun_xu*xl-fun_xl*xu)/(fun_xu-fun_xl)),pre);
            if(Math.abs(xr-pre_xr)<eps&&j!=0){break;}
            j++;
            eq.setVariable("x", xr);
            fun_xr = helper.set_precision(eq.evaluate(),pre);
            System.out.println("xr=" + xr);
            System.out.println("f(" + xr + ")=" + fun_xr);
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xl, fun_xl));
            seriesXY.getData().add(new XYChart.Data(xu, fun_xu));
            pg.add(seriesXY);
            if (fun_xr * fun_xl < 0) {
                xu = xr;
            } else if (fun_xr * fun_xl > 0) {
                xl = xr;
            } else if (Math.abs(xl-xu)<eps){
                seriesXY = new XYChart.Series();
                seriesXY.getData().add(new XYChart.Data(xl, 100));
                seriesXY.getData().add(new XYChart.Data(xl, -100));
                pg.add(seriesXY);
                System.out.println("root="+xl);
                solve.clear();
                solve.add("Convergent");
                solve.add(String.valueOf(xr));
                pg.draw(xr-5,xr+5,eq,pre,false);
                return solve;

            }
            pre_xr=xr;
        }
        seriesXY = new XYChart.Series();
        seriesXY.getData().add(new XYChart.Data(xl, 100));
        seriesXY.getData().add(new XYChart.Data(xl, -100));
        pg.add(seriesXY);
        System.out.println("root=" + xr);
        solve.clear();
        solve.add("Convergent");
        solve.add(String.valueOf(xr));
        pg.draw(xr-5,xr+5,eq,pre,false);
        return solve;


    }

    public ArrayList<String> secant(Expression e, double x0, double x1, int MaxI, int precision, double er) {
        XYChart.Series seriesXY = new XYChart.Series();
        ploter pg = new ploter();
        ArrayList<String> res = new ArrayList<>();
        double root = 0, error = 0, prev_error = 1e10, st;
        Clock cl = Clock.systemDefaultZone();
        boolean diverge = false, tol = false, err = false;
        int i = 1;
        st = cl.millis();
        for (; i <= MaxI; i++) {
            double fx1 = helper.set_precision(f(e, x1), precision), fx0 = helper.set_precision(f(e, x0), precision);
            if (helper.isZero(fx0-fx1)) {
                diverge = tol = err = true;
                res.add("Convergent");
                break;
            }
            root = helper.set_precision(x1 - fx1*((x0-x1)/(fx0-fx1)), precision);
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(x0, fx0));
            seriesXY.getData().add(new XYChart.Data(x1, fx1));
            pg.add(seriesXY);
            if (!helper.isZero(root)){
                error = Math.abs((x1-root)/root)*100;
                err = false;
            } else {
                err = true;
                error = Math.abs(x1-root);
            }
            helper.showIteration(i, x0, x1, fx0, fx1, root, error, err);
            if (!err && error < er || root == x1) {
                tol = true;
                res.add("Convergent");
                break;
            }
            if (error > prev_error && diverge) {
                res.add("Divergent");
                break;
            }
            diverge = error > prev_error;
            prev_error = error;
            x0 = x1;
            x1 = root;
        }
        seriesXY = new XYChart.Series();
        seriesXY.getData().add(new XYChart.Data(x0, f(e,x0)));
        seriesXY.getData().add(new XYChart.Data(x1, f(e,x1)));
        pg.add(seriesXY);
        seriesXY = new XYChart.Series();
        seriesXY.getData().add(new XYChart.Data(root, 100));
        seriesXY.getData().add(new XYChart.Data(root, -100));
        pg.add(seriesXY);
        pg.draw(root-5,root+5,e,precision,false);
        helper.showRoot(root, error, diverge, tol, err, cl.millis()-st, i);
        res.add(root+"");
        return res;
    }

    public ArrayList<String> fixed_point(Expression e, double x0, int iter_max, int precision, double es) {
        System.out.println(iter_max+ " 64645616651");
        XYChart.Series seriesXY = new XYChart.Series();
        ploter pg = new ploter();
        double xr = helper.set_precision(x0 , precision);
        double xr_old;
        double iter = 0.0;
        double ea = 100.0;
        double ea_old;

        do {
            System.out.println("Iteration: " + iter);
            xr_old = xr;
            xr =helper.set_precision(f(e , xr_old) , precision);
            System.out.println("x(i) = " + xr_old);
            System.out.println("x(i+1) = " + xr);
            ea_old = ea;
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr_old, xr_old));
            seriesXY.getData().add(new XYChart.Data(xr_old, f(e , xr_old)));
            pg.add(seriesXY);
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr_old, f(e , xr_old)));
            seriesXY.getData().add(new XYChart.Data(f(e , xr_old), f(e , xr_old)));
            pg.add(seriesXY);
            if(xr != 0)
            {
                ea = Math.abs(((xr - xr_old)/xr) * 100);
            }
            System.out.println("Approximate relative error = " + ea);
            iter ++;
        } while(ea > es && iter < iter_max);

        ArrayList<String> out=new ArrayList<>(2);
        if((ea-ea_old) < 0.001)
        {
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr, 100));
            seriesXY.getData().add(new XYChart.Data(xr, -100));
            pg.add(seriesXY);
            pg.draw(xr-5,xr+5,e,precision,true);
            out.add("Convergent");
        }
        else
        {
            pg.draw(-5,5,e,precision,true);
            out.add("Divergent");
        }
        out.add("" + xr);
        return out;
    }

    public ArrayList<String> newton(Expression e, double x0, int iter_max, int precision, double es) {
        XYChart.Series seriesXY = new XYChart.Series();
        ploter pg = new ploter();
        double xr = helper.set_precision(x0 , precision);
        double xr_old;
        double iter = 0.0;
        double ea = 100.0;
        double ea_old;
        double h = 0.00001;

        double fx , fxD;

        do {
            System.out.println("Iteration: " + iter);
            ea_old = ea;
            xr_old = xr;
            fx = helper.set_precision(f(e , xr_old ) , precision);
            fxD = helper.set_precision(( -f(e , xr_old +2*h) + 4*f(e , xr_old +h) - 3*f(e , xr_old ))/(2*h) , precision);
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr_old, f(e , xr_old)));
            if(fxD == 0)
            {
                System.out.println("f(i) = " + fx);
                System.out.println("fD(i) = " + fxD);
                System.out.println("x(i) = " + xr_old);
                System.out.println("x(i+1) = " + xr);

                ArrayList<String> out= new ArrayList<>(2);
                if((ea-ea_old) < 0.001)
                {
                    seriesXY = new XYChart.Series();
                    seriesXY.getData().add(new XYChart.Data(xr, 100));
                    seriesXY.getData().add(new XYChart.Data(xr, -100));
                    pg.add(seriesXY);
                    pg.draw(xr-5,xr+5,e,precision,false);
                    out.add("Convergent");
                }
                else
                {
                    pg.draw(-5,5,e,precision,false);
                    out.add("Divergent");
                }
                out.add("" + xr);
                return out;
            }
            xr = helper.set_precision(xr_old - (fx/fxD) , precision);
            seriesXY.getData().add(new XYChart.Data(xr, 0));
            pg.add(seriesXY);
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr, 0));
            seriesXY.getData().add(new XYChart.Data(xr, f(e,xr)));
            pg.add(seriesXY);
            System.out.println("f(i) = " + fx);
            System.out.println("fD(i) = " + fxD);
            System.out.println("x(i) = " + xr_old);
            System.out.println("x(i+1) = " + xr);

            if(xr != 0)
            {
                ea = Math.abs(((xr - xr_old)/xr) * 100);
            }
            System.out.println("Approximate relative error = " + ea);
            iter ++;
        } while(ea > es && iter < iter_max);

        ArrayList<String> out= new ArrayList<>(2);
        if((ea-ea_old) < 0.001)
        {
            seriesXY = new XYChart.Series();
            seriesXY.getData().add(new XYChart.Data(xr, 100));
            seriesXY.getData().add(new XYChart.Data(xr, -100));
            pg.add(seriesXY);
            pg.draw(xr-5,xr+5,e,precision,false);
            out.add("Convergent");
        }
        else
        {
            pg.draw(-5,5,e,precision,false);
            out.add("Divergent");
        }
        out.add("" + xr);
        return out;
    }
}
