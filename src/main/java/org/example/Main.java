package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;

import static org.jfree.chart.ChartFactory.createXYLineChart;

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

        // Create dataset
        XYDataset dataset = createDataset(a, b, h, euler, rk4);
        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Approximate Solutions", // title
                "x", // x-axis label
                "y", // y-axis label
                dataset, // dataset
                PlotOrientation.VERTICAL, // plot orientation
                true, // show legend
                true, // use tooltips
                false // generate URLs
        );


        // Customize plot
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Customize renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // Customize axis
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);

        // Create chart panel and frame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Approximate Solutions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static double[] eulerMethod(double x0, double y0, double a, double b, double h) {
        int n = (int) ((b - a) / h) + 1;
        double[] y = new double[n];
        y[0] = y0;

        for (int i = 1; i < n; i++) {
            double x = a + (i - 1) * h;
            double yPrev = y[i - 1];
            double f = Math.cos(1.5 * yPrev + x) * Math.cos(1.5 * yPrev + x) + 1.4;
            y[i] = yPrev + h * f;
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
            double k2 = h * f(x + h / 2, y[i - 1] + k1 / 2);
            double k3 = h * f(x + h / 2, y[i - 1] + k2 / 2);
            double k4 = h * f(x + h, y[i - 1] + k3);
            y[i] = y[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }

        return y;
    }

    public static double f(double x, double y) {
        return Math.cos(1.5 * y + x) * Math.cos(1.5 * y + x) + 1.4;
    }

    public static XYDataset createDataset(double a, double b, double h, double[] euler, double[] rk4) {
        XYSeries eulerSeries = new XYSeries("Euler's Method");
        XYSeries rk4Series = new XYSeries("Fourth-order Runge-Kutta Method");

        for (int i = 0; i < euler.length; i++) {
            double x = a + i * h;
            eulerSeries.add(x, euler[i]);
            rk4Series.add(x, rk4[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(eulerSeries);
        dataset.addSeries(rk4Series);

        return dataset;
    }
}


