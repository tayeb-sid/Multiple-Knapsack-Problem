package pkg1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MkpInstanceSaver {
    public  void saveInstance(Mkp instance) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./Instances"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(String.format("%d,%d,%d\n", instance.getNbKnapsacs(), instance.getNbObjects(), instance.getMinValue()));

                StringBuilder capacitiesLine = new StringBuilder();
                for (KnapSack knapsack : instance.getKnapsacks()) {
                    capacitiesLine.append(knapsack.getPMAX()).append(",");
                }
                writer.write(capacitiesLine.toString().substring(0, capacitiesLine.length() - 1) + "\n");

                for (Item item : instance.getObjects()) {
                    writer.write(String.format("%d,%d\n", item.getValue(), item.getWeight()));
                }

                JOptionPane.showMessageDialog(null, "Instance saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving instance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}