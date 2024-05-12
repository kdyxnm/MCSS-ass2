import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class AdvancedSimulation extends Simulation {
    private boolean irregularSleep;
    private Random random;
    private ArrayList<Double> dailyAdjustedSleepHours; // 存储每天调整后的睡眠时间

    public AdvancedSimulation(
        int intensity,
        boolean lift,
        double hoursOfSleep,
        int daysBetweenWorkouts,
        int slowTwitchPercentage,
        boolean irregularSleep
    ) {
        super(intensity, lift, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage);
        this.irregularSleep = irregularSleep;
        this.random = new Random();
        this.dailyAdjustedSleepHours = new ArrayList<>();
    }

    @Override
    public void sleep() {
        double adjustedSleep = getHoursOfSleep();
        if (irregularSleep) {
            double randomAdjustment = -5 + random.nextDouble() * 10; // Generate a random value between -5 and 5
            adjustedSleep += randomAdjustment;
            adjustedSleep = Math.max(adjustedSleep, 0); // Ensure that sleep hours do not go below 0
        }
        dailyAdjustedSleepHours.add(adjustedSleep); // 记录调整后的睡眠时间
        for (int i = 0; i < getGridSize(); i++) {
            for (int j = 0; j < getGridSize(); j++) {
                getPatches()[i][j].updateHormonesFromSleep(adjustedSleep);
            }
        }
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
        if (args.length < 7) {
            System.out.println("Usage: java AdvancedSimulation <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <daysToSimulate> <irregularSleep>");
            return;
        }

        int intensity = Integer.parseInt(args[0]);
        boolean lift = Boolean.parseBoolean(args[1]);
        double hoursOfSleep = Double.parseDouble(args[2]);
        int daysBetweenWorkouts = Integer.parseInt(args[3]);
        int slowTwitchPercentage = Integer.parseInt(args[4]);
        int days = Integer.parseInt(args[5]);
        boolean irregularSleep = Boolean.parseBoolean(args[6]);

        AdvancedSimulation simulation = new AdvancedSimulation(
            intensity,
            lift,
            hoursOfSleep,
            daysBetweenWorkouts,
            slowTwitchPercentage,
            irregularSleep
        );

        for (int i = 0; i < days; i++) {
            simulation.performDailyActivity();
            if (lift && i % daysBetweenWorkouts == 0) {
                simulation.liftWeights();
            }
            simulation.sleep();
            simulation.regulateHormones();
            simulation.developMuscle();
        }

        String filename = "AdvancedSimulationResults.csv";
        simulation.saveResults(filename);
    }
}
