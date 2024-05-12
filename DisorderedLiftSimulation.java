import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class DisorderedLiftSimulation extends Simulation {
    private float exerciseProbability;
    private Random random;
    private ArrayList<Double> dailyAdjustedSleepHours; // 存储每天调整后的睡眠时间

    public DisorderedLiftSimulation(
        int intensity,
        double hoursOfSleep,
        int daysBetweenWorkouts,
        int slowTwitchPercentage,
        float exerciseProbability
    ) {
        super(intensity, false, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage);
        this.exerciseProbability = exerciseProbability;
        this.random = new Random();
        this.dailyAdjustedSleepHours = new ArrayList<>();
    }

    @Override
    public void liftWeights() {
        if (random.nextFloat() < exerciseProbability) {
            super.liftWeights();
        }
    }

    @Override
    public void sleep() {
        super.sleep();
        dailyAdjustedSleepHours.add(getHoursOfSleep()); // 记录调整后的睡眠时间
    }

    public void saveResults(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Adjusted Sleeping Hours,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i < dailyAdjustedSleepHours.size(); i++) {
                writer.printf("%d,%.2f,%.2f,%.2f,%.2f\n", i, dailyAdjustedSleepHours.get(i), muscleMass(), averageAnabolicHormone(), averageCatabolicHormone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Usage: java DisorderedLiftSimulation <intensity> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <exerciseProbability>");
            return;
        }

        int intensity = Integer.parseInt(args[0]);
        double hoursOfSleep = Double.parseDouble(args[1]);
        int daysBetweenWorkouts = Integer.parseInt(args[2]);
        int slowTwitchPercentage = Integer.parseInt(args[3]);
        float exerciseProbability = Float.parseFloat(args[4]);
        int days = Integer.parseInt(args[5]);

        DisorderedLiftSimulation simulation = new DisorderedLiftSimulation(
            intensity,
            hoursOfSleep,
            daysBetweenWorkouts,
            slowTwitchPercentage,
            exerciseProbability
        );

        for (int i = 0; i < days; i++) {
            simulation.performDailyActivity();
            if (i % daysBetweenWorkouts == 0) {
                simulation.liftWeights();
            }
            simulation.sleep();
            simulation.regulateHormones();
            simulation.developMuscle();
        }

        String filename = "DisorderedLiftSimulationResults.csv";
        simulation.saveResults(filename);
    }
}
