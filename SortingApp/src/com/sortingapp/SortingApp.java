package com.sortingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SortingApp extends JFrame {
    private JTextArea outputTextArea;
    private JButton generateButton;
    private JButton sortButton;
    private JTextArea timeTextArea;

    private ArrayList<String> randomStrings;

    public SortingApp() {
        setTitle("Sorting App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        generateButton = new JButton("Generate Random Strings");
        sortButton = new JButton("Sort and Evaluate");
        buttonPanel.add(generateButton);
        buttonPanel.add(sortButton);
        add(buttonPanel, BorderLayout.SOUTH);

        timeTextArea = new JTextArea(10, 15);
        timeTextArea.setEditable(false);
        JScrollPane timeScrollPane = new JScrollPane(timeTextArea);
        add(timeScrollPane, BorderLayout.EAST);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomStrings();
                updateOutputTextArea();
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (randomStrings == null || randomStrings.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please generate random strings first.");
                    return;
                }

                evaluateSortingAlgorithms();
            }
        });
    }

    private void generateRandomStrings() {
        randomStrings = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            int length = random.nextInt(5) + 1;
            StringBuilder randomString = new StringBuilder();
            for (int j = 0; j < length; j++) {
                char randomChar = (char) (random.nextInt(26) + 'a');
                if (random.nextBoolean()) {
                    randomChar = Character.toUpperCase(randomChar);
                }
                randomString.append(randomChar);
            }
            randomStrings.add(randomString.toString());
        }
    }

    private void updateOutputTextArea() {
        outputTextArea.setText("");
        for (String str : randomStrings) {
            outputTextArea.append(str + "\n");
        }
    }

    private void evaluateSortingAlgorithms() {
        ArrayList<String> copy1 = new ArrayList<>(randomStrings);
        ArrayList<String> copy2 = new ArrayList<>(randomStrings);
        ArrayList<String> copy3 = new ArrayList<>(randomStrings);
        ArrayList<String> copy4 = new ArrayList<>(randomStrings);
        ArrayList<String> copy5 = new ArrayList<>(randomStrings);

        long startTime, endTime;

        // Bubble Sort
        startTime = System.nanoTime();
        bubbleSort(copy1);
        endTime = System.nanoTime();
        long bubbleSortTime = endTime - startTime;

        // Selection Sort
        startTime = System.nanoTime();
        selectionSort(copy2);
        endTime = System.nanoTime();
        long selectionSortTime = endTime - startTime;

        // Insertion Sort
        startTime = System.nanoTime();
        insertionSort(copy3);
        endTime = System.nanoTime();
        long insertionSortTime = endTime - startTime;

        // Merge Sort
        startTime = System.nanoTime();
        mergeSort(copy4);
        endTime = System.nanoTime();
        long mergeSortTime = endTime - startTime;

        // Quick Sort
        startTime = System.nanoTime();
        quickSort(copy5);
        endTime = System.nanoTime();
        long quickSortTime = endTime - startTime;

        timeTextArea.setText("Sorting Algorithm Evaluation (Time in nanoseconds):\n");
        timeTextArea.append("Bubble Sort: " + bubbleSortTime + "\n");
        timeTextArea.append("Selection Sort: " + selectionSortTime + "\n");
        timeTextArea.append("Insertion Sort: " + insertionSortTime + "\n");
        timeTextArea.append("Merge Sort: " + mergeSortTime + "\n");
        timeTextArea.append("Quick Sort: " + quickSortTime + "\n");
    }

    private void bubbleSort(ArrayList<String> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j).compareTo(arr.get(j + 1)) > 0) {
                    Collections.swap(arr, j, j + 1);
                }
            }
        }
    }

    private void selectionSort(ArrayList<String> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr.get(j).compareTo(arr.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(arr, i, minIndex);
        }
    }

    private void insertionSort(ArrayList<String> arr) {
        int n = arr.size();
        for (int i = 1; i < n; i++) {
            String key = arr.get(i);
            int j = i - 1;
            while (j >= 0 && arr.get(j).compareTo(key) > 0) {
                arr.set(j + 1, arr.get(j));
                j--;
            }
            arr.set(j + 1, key);
        }
    }

    private void mergeSort(ArrayList<String> arr) {
        if (arr.size() <= 1) {
            return;
        }
        int mid = arr.size() / 2;
        ArrayList<String> left = new ArrayList<>(arr.subList(0, mid));
        ArrayList<String> right = new ArrayList<>(arr.subList(mid, arr.size()));
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private void merge(ArrayList<String> arr, ArrayList<String> left, ArrayList<String> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                arr.set(k++, left.get(i++));
            } else {
                arr.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            arr.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            arr.set(k++, right.get(j++));
        }
    }

    private void quickSort(ArrayList<String> arr) {
        quickSortHelper(arr, 0, arr.size() - 1);
    }

    private void quickSortHelper(ArrayList<String> arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }

    private int partition(ArrayList<String> arr, int low, int high) {
        String pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j).compareTo(pivot) < 0) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        SortingApp sortingApp = new SortingApp();
        sortingApp.setVisible(true);
    }
}

