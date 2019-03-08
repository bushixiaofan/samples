package com.song.samples.triangulation;

//import image.BMP;
//import color.RGB;
//import draw.Draw;

public class Triangulation {
    /**
     * @param args
     */
    private int n;
    private double[][] weight;//����ε��ڽӾ���

    public void MinWeightTriangulation(int n, double[][] t, int[][] s) {

        for (int i = 0; i < n; i++) {// ��ʼ��t��ֵ
            t[i][i] = 0;
        }
        for (int r = 2; r <= n - 1; r++) {
//            System.out.println("-----" + r);
            for (int i = 1; i <= n - r; i++) {
                int j = i + r - 1;// j-i=r-1
                if (Math.abs(i - 1 - j % n) == 1 || j == n - 1 && i == 1) {
                    t[i][j] = t[i + 1][j];
                } else {
                    t[i][j] = t[i + 1][j] + weight[i - 1][j];
                }
                s[i][j] = i;
                for (int k = i + 1; k <= j - 1; k++) {
                    double u;
                    if (Math.abs(i - 1 - k % n) == 1 || k == n - 1 && i == 1) {
                        u = t[i][k] + t[k + 1][j];
                    } else {
                        u = t[i][k] + t[k + 1][j] + weight[i - 1][j % n];
                    }
                    if (u < t[i][j]) {
                        t[i][j] = u;
                        s[i][j] = k;
                    }
//                    System.out.print(s[i][j]);
                }
                System.out.println(" ");
                System.out.print(" " + i + j + " " + s[i][j] + " " + t[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ReadTxt myReadTxt = new ReadTxt();
        double[] x = myReadTxt.getX();
        double[] y = myReadTxt.getY();
        double[][] weight = myReadTxt.getWeight();
        int n = myReadTxt.getN();
        myReadTxt.createWeight(x, y, weight);
        Triangulation myTriangulation = new Triangulation();
        myTriangulation.setWeight(weight);
        myTriangulation.setN(n);
        double[][] t = new double[n][n];// t[i][j]��ʾ����{Vi-1,Vi...Vj}��ɵĶ���ε������������ʷֵ�Ȩֵ
        int[][] s = new int[n][n];// s[i][j]��ʾ��Vi-1��Vjһ�𹹳������εĵ����������λ��
        myTriangulation.MinWeightTriangulation(n, t, s);
        @SuppressWarnings("unused")
        WriteTxt myWriteTxt = new WriteTxt(n, t, s);
//        BMP bmp = new BMP(512, 512, RGB.Black);
//        Draw dw = new Draw(bmp);
//        for(int i=0;i<n;i++)
//        {
//            dw.DrawLine((int)x[i]*40+10, (int)y[i]*40+10, (int)x[(i+1)%n]*40+10, (int)y[(i+1)%n]*40+10,RGB.Yellow);
////            dw.drawString("("+x[i]+y[i]+")",x[i]*50+10, y[i]*50+10);
//
//        }
//        int[] X=new int[n];
//        int[] Y=new int[n];
//        for(int i=0;i<n;i++)
//        {
//            X[i]=(int)x[i];
//            Y[i]=(int)y[i];
//        }
//        TraceBack(1,n-1,X,Y,s,dw);
//        bmp.Save("C:/Users/Administrator/Desktop/rst.bmp");
//        System.out.println(' ');
//        System.out.println(t[1][n - 1]);
        //TraceBack(1,n-1,s,g1);

//        for(int i=0; i < n;i++)
//            dw.DrawLine(x0, y0, x1, y1, RGB.Yellow);
//        bmp.Save("rst.bmp");

        DrawTriangulation myDraw = new DrawTriangulation();
        int[] X = new int[n];
        int[] Y = new int[n];
        for (int i = 0; i < n; i++) {
            X[i] = (int) x[i];
            Y[i] = (int) y[i];
        }
        myDraw.setN(n);
        myDraw.setX(X);
        myDraw.setY(Y);
        myDraw.setS(s);
        myDraw.setT(t);
    }

    //    public static void TraceBack(int i,int j,int[] x,int[] y,int[][] s,Draw dw)
//    {
//        if(i==j)
//            return;
//        else{
//            TraceBack(i,s[i][j],x,y,s,dw);
//            //System.out.println(s[i][j]);
//            dw.DrawLine(x[i-1]*40+10, y[i-1]*40+10, x[j]*40+10, y[j]*40+10,RGB.Blue);
//            TraceBack(s[i][j]+1,j,x,y,s,dw);
//        }
//    }
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] getWeight() {
        return weight;
    }

    public void setWeight(double[][] weight) {
        this.weight = weight;
    }

}
