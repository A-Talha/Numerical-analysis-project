package com.example.optimization;

import java.util.ArrayList;
public class ImplementationHelper {
    int test_solution(ArrayList<ArrayList<Double>> eq ){
        int rank_Agu= eq.get(0).size(), rank= eq.get(0).size(),test=1;
        for (int i=0;i< eq.size();i++) {
            test=1;
            for (int j = 0; j < eq.get(0).size()-1; j++) {
                if(Math.abs(eq.get(i).get(j))>1e-10){
                    test=0;
                }


            }
            if (test==1){
                if(rank==1){
                    rank=1;
                }
                else {
                    rank--;
                }
            }
        }
        for (int i=0;i< eq.size();i++) {
            test=1;
            for (int j = 0; j < eq.get(0).size(); j++) {
                if(Math.abs(eq.get(i).get(j))>1e-10){
                    test=0;
                }


            }
            if (test==1){
                if(rank_Agu==1){
                    rank_Agu=1;
                }
                else {
                    rank_Agu--;
                }
            }
        }


        if(rank<rank_Agu){
            //System.out.println("inconsistent solution");
            return -3;
        }
        if(rank==rank_Agu&&rank_Agu!=(eq.get(0).size())){
            System.out.println("infinite solution:there are "+Math.abs((eq.get(0).size()-1)-rank)+" all free variable\none solution when free variable =0 is:");
            //to know number of free variable
            return Math.abs((eq.get(0).size())-rank);
        }
        return -1;
    }




    public ArrayList<ArrayList<Double>> pivoting(ArrayList<ArrayList<Double>> eq ,int column){
        ArrayList<Double> max=new ArrayList<>();
        ArrayList<Double> temp=new ArrayList<>();
        ArrayList<ArrayList<Double>>new_eq = new ArrayList<ArrayList<Double>>();
        double max_number=eq.get(column).get(column);
        max=eq.get(column);
        int j=column;

        for (int i=column+1; i<eq.size();i++){
            if(Math.abs(max_number)<Math.abs(eq.get(i).get(column))){
                max_number=eq.get(i).get(column);
                max=eq.get(i);
                j=i;
            }
        }

        temp= (ArrayList<Double>) eq.get(column).clone();
        for (int i=0; i<eq.size();i++){
            if(i==column){new_eq.add(max);}
            else if(j==i){new_eq.add(temp);}
            else{
                new_eq.add(eq.get(i));
            }
        }
        return new_eq;
    }

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

    public void printArray(double[][] A) {
        for (double[] doubles : A) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printArray(double[][] A, int[] pos, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(A[pos[i]][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printArray(int[] A) {
        for (double x : A) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
    public void printArray(double[] A) {
        for (double x : A) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public ArrayList <Double> setSolution (double[] b) {
        ArrayList<Double> s = new ArrayList<>();
        for (double i : b) {
            s.add(i);
        }
        return s;
    }

    public ArrayList <Double> setSolution (double[] b, int[] pos) {
        ArrayList<Double> s = new ArrayList<>();
        int sz = b.length;
        for (int i = 0; i < sz; i++) {
            s.add(b[pos[i]]);
        }
        return s;
    }

    public int getRank(double[][] A) {
        int rank = 0;
        for (double[] i : A) {
            for (double j : i) {
                if (j != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    public int getRank(double[][] A, double[] b, int n) {
        int rank = 0;
        for (int i = 0; i < n; i++) {
            if (b[i] != 0) {
                rank++;
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (A[i][j] != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    public int isSolvable(double[][] A, double[] b, int n) {
        int rankA = getRank(A), rankAB = getRank(A, b, n);
        if (rankA == rankAB) return rankA+1;
        else return 0;
    }
    public void setPos(int[] pos, int n) {
        for (int i = 0; i < n; i++) {
            pos[i] = i;
        }
    }

    public void setDiagonal(double[][] L, int n) {
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
        }
    }

    public void setArray(double[] SF, int n) {
        for(int i = 0; i < n; i++) {
            SF[i] = 1;
        }
    }

    public String printSolution(ArrayList<Double> x, int n) {
        String y = "";
        for (int i = 0; i < n; i++) {
            y += ("x" + (i) + " = " + x.get(i)) + "\n";
        }
        return y;
    }

    public String showResult(ArrayList<Double> x, int n, String requiredForm) {
        int sz = x.size();
        if (x.isEmpty()) {
            return "The system is inconsistent";
        } else if(sz > n) {
            return "The system has an infinite number of solutions and have " + Math.round(x.get(sz-1)) + " free variables"
                    + printSolution(x, n);
        } else if (sz == n) {
            return  printSolution(x, n);
        } else {
            if (x.get(0) == 0.0) {
                return "Invalid form";
            } else {
                return (requiredForm + " form can not solve this system");
            }
        }
    }
    public void mult (double[][] A, double[][] B, double[][] res, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double e = 0;
                for (int k = 0; k < n; k++) {
                    e += A[i][k]*B[k][j];
                }
                res[i][j] = e;
            }
        }
    }

    public boolean isSymmetric(double[][] A, int n) {
        for(int i = 0; i < n; i ++) {
            for(int j = 0; j < n; j++) {
                if(A[i][j] != A[j][i]) return false;
            }
        }
        return true;
    }

    boolean isZero(double x) {
        return Math.abs(x) < 1e-9;
    }

    protected boolean forward_sub(double[][] L, double[] b, int n, int pre, int free){
        for(int i = 0; i < n-free; i++) {
            double sum = 0;
            for(int j = i-1; j >= 0; j--) {
                sum += b[j]*L[i][j];
            }
            if (isZero(L[i][i])) return false;
            b[i] = set_precision((b[i] - sum)/L[i][i], pre);
        }
        return true;
    }

    protected boolean backward_sub(double[][] U, double[] b, int n, int pre, int free) {
        for(int i = n-1-free; i >= 0; i--) {
            double sum = 0;
            for(int j = i+1; j < n; j++) {
                sum += b[j]*U[i][j];
            }
            if (isZero(U[i][i])) return false;
            b[i] = set_precision((b[i] - sum)/U[i][i], pre);
        }
        return true;
    } // done

    public void doolittle_decompose(double[][] A, double[][] L, int n, int pre) {
        for(int i = 0; i < n-1; i++) {
            for(int j = i+1; j < n; j++) {
                double factor = A[j][i]/A[i][i];
                L[j][i] = set_precision(factor, pre);
                A[j][i] = 0;
                for(int k = i+1; k < n; k++) {
                    A[j][k] = set_precision(A[j][k] - A[i][k]*factor, pre);
                }
            }
        }
    }

    protected boolean crout_decompose(double[][] A, double[][] L, double[][] U, int n, int pre) {
        for (int i = 0; i < n; i++) {
            L[i][0] = set_precision(A[i][0], pre);
            U[i][i] = 1;
        }
        for (int j = 1; j < n; j++) {
            if (isZero(L[0][0])) return false;
            U[0][j] = set_precision(A[0][j]/L[0][0], pre);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= i; j++) {
                double sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += L[i][k]*U[k][j];
                }
                L[i][j] = set_precision(A[i][j] - sum, pre);
            }
            for(int j = i+1; j < n; j++) {
                double sumu = 0;
                for (int k = 0; k < i; k++) {
                    sumu += U[k][j]*L[i][k];
                }
                if (isZero(L[i][i])) return false;
                U[i][j] = set_precision((A[i][j] - sumu)/L[i][i], pre);
            }
        }
        return true;
    } //done

    public boolean cholesky_decompose(double[][] A, double[][] L, int n, int pre) {
        if(A[0][0] < 0) return false;
        L[0][0] = set_precision(Math.sqrt(A[0][0]), pre);
        double sum;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += L[j][k]*L[i][k];
                }
                if (isZero(L[j][j])) return false;
                L[i][j] = set_precision((A[i][j] - sum)/L[j][j], pre);
                L[j][i] = L[i][j];
            }
            sum = 0;
            for (int k = 0; k < i; k++) {
                sum += L[i][k]*L[i][k];
            }
            double t = A[i][i] - sum;
            if (t < 0) return false;
            L[i][i] = set_precision(Math.sqrt(t), pre);
        }
        return true;
    } //done
    protected void getScalingFactors(double[][] A, int[] pos, double[] SF, int sz) {
        for(int i = 0; i < sz; i++) {
            pos[i] = i;
            SF[i] = Math.abs(A[i][0]);
            for(int j = 1; j < sz; j++) {
                if(Math.abs(A[i][j]) > SF[i]) {
                    SF[i] = Math.abs(A[i][j]);
                }
            }
        }
    } //done

    protected void pivoting(double[][] A, int[] pos, double[] SF, int row, int sz) {
        int t = row;
        double pivot = 0;
        for(int i = row+1; i < sz; i++) {
            double temp = Math.abs(A[pos[i]][row])/SF[pos[i]];
            if(temp > pivot) {
                t = i;
                pivot = temp;
            }
        }
        int temp = pos[t];
        pos[t] = pos[row];
        pos[row] = temp;
    } //done

    protected boolean forward_sub(double[][] L, double[] b, int[] pos, int n, int pre){
        for(int i = 0; i < n; i++) {
            double sum = 0;
            for(int j = i-1; j >= 0; j--) {
                sum += b[pos[j]]*L[pos[i]][j];
            }
            b[pos[i]] = set_precision((b[pos[i]] - sum), pre);
        }
        return true;
    } //done

    protected boolean backward_sub(double[][] U, double[] b, int[] pos, int n, int pre, int free) {
        for(int i = n-1-free; i >= 0; i--) {
            double sum = 0;
            for(int j = i+1; j < n; j++) {
                sum += b[pos[j]]*U[pos[i]][j];
            }
            if (isZero(U[pos[i]][i])) return false;
            b[pos[i]] = set_precision((b[pos[i]] - sum)/U[pos[i]][i], pre);
        }
        return true;
    } //done

    protected boolean doolittle_decompose(double[][] A, double[][] L, int n, int[] pos, double[] SF, int pre) {
        for(int i = 0; i < n-1; i++) {
            pivoting(A, pos, SF, i, n);
            for(int j = i+1; j < n; j++) {
                if (isZero(A[pos[i]][i])) return false;
                double factor = A[pos[j]][i]/A[pos[i]][i];
                L[pos[j]][i] = set_precision(factor, pre);
                A[pos[j]][i] = 0;
                for(int k = i+1; k < n; k++) {
                    A[pos[j]][k] = set_precision(A[pos[j]][k] - A[pos[i]][k]*factor, pre);
                }
            }
        }
        return true;
    } //done

}
