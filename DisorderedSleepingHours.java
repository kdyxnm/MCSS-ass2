import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class DisorderedSleepingHours extends Simulation {
    private boolean irregularSleep;
    private Random random;
    public ArrayList<Double> dailyAdjustedSleepHours; // 存储每天调整后的睡眠时间

    public DisorderedSleepingHours(
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

        // Adjust hormone levels based on sleep quality
        double sleepFactor = adjustedSleep / getHoursOfSleep();
        for (int i = 0; i < getGridSize(); i++) {
            for (int j = 0; j < getGridSize(); j++) {
                double currentAnabolic = getPatches()[i][j].getAnabolicHormone();
                double currentCatabolic = getPatches()[i][j].getCatabolicHormone();
                getPatches()[i][j].setAnabolicHormone(currentAnabolic * (1 + 0.1 * (sleepFactor - 1)));
                getPatches()[i][j].setCatabolicHormone(currentCatabolic * (1 - 0.1 * (sleepFactor - 1)));
                getPatches()[i][j].updateHormonesFromSleep(adjustedSleep);
            }
        }
    }

    
    public static void main(String[] args) {
        if (args.length < 7) {
            System.out.println("Usage: java DisorderedSleepingHours <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <daysToSimulate> <irregularSleep>");
            return;
        }
    
        int intensity = Integer.parseInt(args[0]);
        boolean lift = Boolean.parseBoolean(args[1]);
        double hoursOfSleep = Double.parseDouble(args[2]);
        int daysBetweenWorkouts = Integer.parseInt(args[3]);
        int slowTwitchPercentage = Integer.parseInt(args[4]);
        int days = Integer.parseInt(args[5]);
        boolean irregularSleep = Boolean.parseBoolean(args[6]);
    
        DisorderedSleepingHours simulation = new DisorderedSleepingHours(
            intensity,
            lift,
            hoursOfSleep,
            daysBetweenWorkouts,
            slowTwitchPercentage,
            irregularSleep
        );

        double[] muscleMass = new double[days + 1];
        double[] averageAnabolicHormone = new double[days + 1];
        double[] averageCatabolicHormone = new double[days + 1];
        double[] sleepingHours = new double[days + 1];
        simulation.sleep();
    
        muscleMass[0] = simulation.muscleMass();
        averageAnabolicHormone[0] = simulation.averageAnabolicHormone();
        averageCatabolicHormone[0] = simulation.averageCatabolicHormone();
        sleepingHours[0] = simulation.getHoursOfSleep();
    
        for (int i = 0; i < days; i++) {
            simulation.performDailyActivity();
            if (lift && i % daysBetweenWorkouts == 0) {
                simulation.liftWeights();
            }
            simulation.sleep();
            simulation.regulateHormones();
            simulation.developMuscle();

            muscleMass[i] = simulation.muscleMass();
            averageAnabolicHormone[i] = simulation.averageAnabolicHormone();
            averageCatabolicHormone[i] = simulation.averageCatabolicHormone();
            sleepingHours[i] = simulation.dailyAdjustedSleepHours.get(i); 
        }
    
        String sleepHoursFormatted = String.format("%.1f", hoursOfSleep).replace('.', 'n');
        String filename = String.format("%d_%s_%s_%d_%d_%d_%s.csv",
                                        intensity,
                                        lift ? "true" : "false",
                                        sleepHoursFormatted,
                                        daysBetweenWorkouts,
                                        slowTwitchPercentage,
                                        days,
                                        irregularSleep ? "true" : "false");
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        writer.println("Day,Sleeping Hours,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
        for (int i = 0; i <= days; i++) {
            writer.printf("%d,%.2f,%.2f,%.2f,%.2f\n", i, sleepingHours[i], muscleMass[i], averageAnabolicHormone[i], averageCatabolicHormone[i]);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    

    
}
