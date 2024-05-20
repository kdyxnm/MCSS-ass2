import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * The DisorderedLiftSimulation class extends the Simulation class and represents a simulation
 * where the decision to lift weights is random.
 */
public class DisorderedLiftSimulation extends Simulation {
    private double exerciseProbability; // The probability of exercising
    private Random random; // Random number generator

    /**
     * Constructs a new DisorderedLiftSimulation with the given parameters.
     * @param intensity The intensity of the workout.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     * @param exerciseProbability The probability of exercising.
     */
    public DisorderedLiftSimulation(
            int intensity,
            double hoursOfSleep,
            int daysBetweenWorkouts,
            int slowTwitchPercentage,
            double exerciseProbability
    ) {
        super(intensity, true, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage);
        this.exerciseProbability = exerciseProbability;
        this.random = new Random();
    }

    /**
     * Overrides the liftWeights method from the Simulation class.
     * The decision to lift weights is random, based on the exerciseProbability.
     */
    @Override
    public void liftWeights() {
        if (random.nextFloat() < exerciseProbability) {
            super.liftWeights();
        }
    }

    /**
     * The main method for the DisorderedLiftSimulation class.
     * It takes command line arguments for the simulation parameters, runs the simulation,
     * and writes the results to a CSV file.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Usage: java DisorderedLiftSimulation <intensity> <hoursOfSleep> " +
                    "<daysBetweenWorkouts> <slowTwitchPercentage> <daysToSimulate> " +
                    "<exerciseProbability>");
            return;
        }

        int intensity = Integer.parseInt(args[0]);
        double hoursOfSleep = Double.parseDouble(args[1]);
        int daysBetweenWorkouts = Integer.parseInt(args[2]);
        int slowTwitchPercentage = Integer.parseInt(args[3]);
        int days = Integer.parseInt(args[4]);
        double exerciseProbability = Double.parseDouble(args[5]);

        DisorderedLiftSimulation simulation = new DisorderedLiftSimulation(
                intensity,
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                exerciseProbability
        );

        double[] muscleMass = new double[days + 1];
        double[] averageAnabolicHormone = new double[days + 1];
        double[] averageCatabolicHormone = new double[days + 1];

        simulation.performDailyActivity(); // Perform initial daily activity
        muscleMass[0] = simulation.muscleMass();
        averageAnabolicHormone[0] = simulation.averageAnabolicHormone();
        averageCatabolicHormone[0] = simulation.averageCatabolicHormone();

        // Run the simulation for the specified number of days
        for (int i = 1; i <= days; i++) {
            simulation.performDailyActivity();
            simulation.liftWeights();
            simulation.sleep();
            simulation.regulateHormones();
            simulation.developMuscle();

            muscleMass[i] = simulation.muscleMass();
            averageAnabolicHormone[i] = simulation.averageAnabolicHormone();
            averageCatabolicHormone[i] = simulation.averageCatabolicHormone();
        }

        String directoryPath = "DisorderedLiftExperiment";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        String filename = String.format("%s/%d_%s_%.1f_%d_%d_%d_%.2f.csv",
                directoryPath,
                intensity,
                "true",
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                days,
                exerciseProbability);
        // Write the results to a CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i <= days; i++) {
                writer.printf("%d,%.2f,%.2f,%.2f\n", i, muscleMass[i],
                        averageAnabolicHormone[i], averageCatabolicHormone[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
