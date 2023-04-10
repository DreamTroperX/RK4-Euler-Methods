package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double x0 = 1.0;
        double y0 = 0.9;
        double a = 1.0;
        double b = 2.0;
        double h = 0.1;

        // Euler's Method
        double[] euler = eulerMethod(x0, y0, a, b, h);

        // Fourth-order Runge-Kutta Method
        double[] rk4 = rk4Method(x0, y0, a, b, h);

        // Print table of approximate values
        System.out.printf("%-15s%-15s%-15s%n", "x", "Euler", "RK4");
        System.out.println("-------------------------------------");
        for (int i = 0; i < euler.length; i++) {
            double x = a + i * h;
            System.out.printf("%-15.1f%-15.6f%-15.6f%n", x, euler[i], rk4[i]);
        }
    }

    public static double[] eulerMethod(double x0, double y0, double a, double b, double h) {
        int n = (int) ((b - a) / h) + 1;
        double[] y = new double[n];
        y[0] = y0;

        for (int i = 1; i < n; i++) {
            double x = a + (i - 1) * h;
            double f = Math.cos(1.5 * y[i - 1] + x) * Math.cos(1.5 * y[i - 1] + x) + 1.4;
            y[i] = y[i - 1] + h * f;
        }

        return y;
    }

    public static double[] rk4Method(double x0, double y0, double a, double b, double h) {
        int n = (int) ((b - a) / h) + 1;
        double[] y = new double[n];
        y[0] = y0;

        for (int i = 1; i < n; i++) {
            double x = a + (i - 1) * h;
            double k1 = h * f(x, y[i - 1]);
            double k2 = h * f(x + h / 2.0, y[i - 1] + k1 / 2.0);
            double k3 = h * f(x + h / 2.0, y[i - 1] + k2 / 2.0);
            double k4 = h * f(x + h, y[i - 1] + k3);
            y[i] = y[i - 1] + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
        }

        return y;
    }

    public static double f(double x, double y) {
        return Math.cos(1.5 * y + x) * Math.cos(1.5 * y + x) + 1.4;
    }
}
