package com.example.optimization;

import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;

public interface Iimplementation {
     ArrayList<String> bisection(Expression eq, double xl, double xu, double eps, int pre);
     ArrayList<String> false_position(Expression eq, double xl, double xu, double eps,int pre);
     ArrayList<String> secant(Expression e, double x0, double x1, int MaxI, int precision, double er);
     ArrayList<String> fixed_point(Expression e, double x0, int iter_max, int precision, double es);
     ArrayList<String> newton(Expression e,double x0, int iter_max, int precision, double es);
    }
