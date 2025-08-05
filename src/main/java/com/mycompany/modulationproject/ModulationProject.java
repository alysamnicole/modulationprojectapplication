/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.modulationproject;


/**
 *
 * @author alysamiller
 */


import org.knowm.xchart.*;
import java.util.*;
import java.awt.Color;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ModulationProject {

    public static void main(String[] args) {
        int[] bitSequence = new int[15];
        Random rand = new Random();

        for (int i = 0; i < bitSequence.length; i++) {
            bitSequence[i] = rand.nextBoolean() ? 1 : 0;
        }

        double carrierFreq = 1000.0; // Hz
        double bitDuration = 0.01;   // 10 ms
        double samplingRate = 100000; // 100 kHz
        double timeStep = 1.0 / samplingRate;

        double ampLow = 1.0;
        double ampHigh = 5.0;

        double freqLow = 500.0;
        double freqHigh = 1500.0;

        double phase0 = 0;
        double phase1 = Math.PI;

        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> askSignal = new ArrayList<>();
        ArrayList<Double> fskSignal = new ArrayList<>();
        ArrayList<Double> pskSignal = new ArrayList<>();

        for (int i = 0; i < bitSequence.length; i++) {
            int bit = bitSequence[i];
            for (double t = 0; t < bitDuration; t += timeStep) {
                double absoluteTime = i * bitDuration + t;
                time.add(absoluteTime);

                // ASK
                double amp = (bit == 1) ? ampHigh : ampLow;
                askSignal.add(amp * Math.sin(2 * Math.PI * carrierFreq * t));

                // FSK
                double freq = (bit == 1) ? freqHigh : freqLow;
                fskSignal.add(Math.sin(2 * Math.PI * freq * t));

                // PSK
                double phase = (bit == 1) ? phase1 : phase0;
                pskSignal.add(Math.sin(2 * Math.PI * carrierFreq * t + phase));
            }
        }

        // Plot using XChart
        XYChart chart = new XYChartBuilder().width(1000).height(600).title("Modulation Techniques").xAxisTitle("Time (s)").yAxisTitle("Amplitude").build();
            chart.addSeries("ASK", time, askSignal).setMarker(SeriesMarkers.NONE).setLineColor(Color.BLUE);
            chart.addSeries("FSK", time, fskSignal).setMarker(SeriesMarkers.NONE).setLineColor(Color.ORANGE);
            chart.addSeries("PSK", time, pskSignal).setMarker(SeriesMarkers.NONE).setLineColor(Color.MAGENTA);
        new SwingWrapper<>(chart).displayChart();
    }
}

