import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class DisorderedLiftSimulation extends Simulation {
    private double exerciseProbability;
    private Random random;

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

    @Override
    public void liftWeights() {
        if (random.nextFloat() < exerciseProbability) {
            super.liftWeights();
        }
    }

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Usage: java DisorderedLiftSimulation <intensity> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <daysToSimulate> <exerciseProbability>");
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
    
        String filename = String.format("%d_%s_%.1f_%d_%d_%d_%.2f.csv",
                                        intensity,
                                        "true",
                                        hoursOfSleep,
                                        daysBetweenWorkouts,
                                        slowTwitchPercentage,
                                        days,
                                        exerciseProbability);
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i <= days; i++) {
                writer.printf("%d,%.2f,%.2f,%.2f\n", i, muscleMass[i], averageAnabolicHormone[i], averageCatabolicHormone[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
