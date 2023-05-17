package com.example.optimization;

public class Helper {
    public double pow(double x, int p) {
        if(p == 0) {
            return 1;
        } else if ((p&1) == 1) {
            if (p < 0) return (x*(1/(pow(x, 1-p))));
            return x*pow(x, p-1);
        } else {
            double t;
            if (p < 0) {
                t = 1/pow(x, (-1)*p/2);
            } else {
                t = pow(x, p / 2);
            }
            return t*t;
        }
    }

    public double round(double x, int p) {
        return Math.round(x*pow(10, p))/pow(10, p);
    }

    public double set_precision(double x, int pre) {
        boolean neg = false;
        if(x < 0) {
            x *= -1;
            neg = true;
        }
        double t = 1;
        if (x >= 1) {
            int digits = 0;
            while (t <= x) {
                digits++;
                t *= 10;
            }
            if (digits <= pre) {
                int dif = pre - digits;
                x = round(x, dif);
            } else {
                int dif = digits - pre;
                x = Math.round(x / pow(10, dif)) * pow(10, dif);
            }
        } else {
            int zero = 0;
            t = 0.1;
            while (t > x) {
                zero++;
                t /= 10;
            }
            double temp = pow(10, zero+pre);
            x = Math.round(x * temp)/temp;
        }
        if(neg) x *= -1;
        return x;
    }





    public void printArray(double[] A) {
        for (double x : A) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public boolean isZero(double x) {
        return Math.abs(x) < 1e-9;
    }

    public void showIteration(int i, double x0, double x1, double fx0, double fx1, double root, double er, boolean err) {
        System.out.println("Itration " + i + ":");
        System.out.println("Xi-1 = " + x0);
        System.out.println("f(Xi-1) = " + fx0);
        System.out.println("Xi = " + x1);
        System.out.println("f(Xi) = " + fx1);
        System.out.println("Xi+1 = " + root);
        if (err) {
            System.out.println("Approximate absolute error = " + er + "\n");
        } else {
            System.out.println("Approximate relative error = " + er + "\n");
        }
    }

    public void showRoot(double root, double error,boolean div, boolean tol, boolean err, double time, int itr) {
        if (div && tol && err) {
            System.out.println("Error..can't iterate more!");
            System.out.println("The last value of the root = " + root);
            System.out.println("The approximate relative error = " + error);
        } else if (div) {
            System.out.println("Secant method diverges!");
        } else if (tol) {
            System.out.println("Secant reached the required tolerance after " + itr + " iterations\nApproximate root = " + root);
            if (err) {
                System.out.println("The approximate absolute error = " + error);
            } else {
                System.out.println("The approximate relative error = " + error);
            }
        } else {
            System.out.println("Secant reached the maximum number of iterations,\nthe last value of the root is " + root);
            if (err) {
                System.out.println("The approximate absolute error = " + error);
            } else {
                System.out.println("The approximate relative error = " + error);
            }
        }
        System.out.println("Secant takes " + time + "ms");
    }
}
