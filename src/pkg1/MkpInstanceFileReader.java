package pkg1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MkpInstanceFileReader {
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    public static Mkp readFromFile(String fileName) throws IOException, InvalidInstanceException,InvalidFileTypeException {
    	  if (!isAllowedFileType(fileName)) {
    	        throw new InvalidFileTypeException("Invalid file type: Only .txt and .csv files are allowed");
    	    }
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            String[] parts = line.split(",");

            if (parts.length != 3) {
                throw new InvalidInstanceException("Invalid instance format: Expected 3 values in the first line");
            }

            int nbKnapsacks, nbObjects, minValue;

            try {
                validateValues(parts[0], parts[1], parts[2]);
                nbKnapsacks = Integer.parseInt(parts[0]);
                nbObjects = Integer.parseInt(parts[1]);
                minValue = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new InvalidInstanceException("Invalid instance format: Non-numeric values in the first line");
            }

            ArrayList<KnapSack> knapsacks = new ArrayList<>();
            line = reader.readLine();
            parts = line.split(",");

            if (parts.length != nbKnapsacks) {
                throw new InvalidInstanceException("Invalid instance format: Number of knapsack capacities does not match the specified number of knapsacks");
            }

            for (int i = 0; i < nbKnapsacks; i++) {
                int capacity;
                try {
                    validateValue(parts[i]);
                    capacity = Integer.parseInt(parts[i]);
                } catch (NumberFormatException e) {
                    throw new InvalidInstanceException("Invalid instance format: Non-numeric knapsack capacity");
                }
                knapsacks.add(new KnapSack((i+1),capacity));
            }

            ArrayList<Item> objects = new ArrayList<>();
            int objectCount = 0;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                parts = line.split(",");
                if (parts.length != 2) {
                    throw new InvalidInstanceException("Invalid instance format: Expected 2 values for each object");
                }

                int weight, value;
                try {
                    validateValues(parts[0], parts[1]);
                    value = Integer.parseInt(parts[0]);
                    weight = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    throw new InvalidInstanceException("Invalid instance format: Non-numeric object weight or value");
                }
                objectCount++;
                objects.add(new Item((objectCount),weight, value));
            }

            if (objectCount != nbObjects) {
                throw new InvalidInstanceException("Invalid instance format: Number of objects does not match the specified number of objects");
            }

            return new Mkp(nbKnapsacks, nbObjects, minValue, objects, knapsacks);
        }
    }

    private static void validateValues(String... values) throws InvalidInstanceException {
        for (String value : values) {
            if (!DIGIT_PATTERN.matcher(value).matches()) {
                throw new InvalidInstanceException("Invalid instance format: Non-numeric value found");
            }
        }
    }

    private static void validateValue(String value) throws InvalidInstanceException {
        if (!DIGIT_PATTERN.matcher(value).matches()) {
            throw new InvalidInstanceException("Invalid instance format: Non-numeric value found");
        }
    }

    @SuppressWarnings("serial")
	public static class InvalidInstanceException extends Exception {
        public InvalidInstanceException(String message) {
            super(message);
        }
    }
    private static boolean isAllowedFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("txt") || extension.equals("csv");
    }
    @SuppressWarnings("serial")
	public static class InvalidFileTypeException extends Exception {
        public InvalidFileTypeException(String message) {
            super(message);
        }
    }
}