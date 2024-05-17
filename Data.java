/**
 * The Data class represents a set of parameters related to a workout routine.
 * It includes intensity, whether the person lifts or not, hours of sleep, days between workouts,
 * slow twitch percentage, muscle mass, anabolic hormone, and catabolic hormone.
 */
public class Data {
    // Constants, classes, and instance variables
    private int intensity; // The intensity of the workout
    private boolean lift; // Whether the person lifts or not
    private double hoursOfSleep; // The hours of sleep the person gets
    private int daysBetweenWorkouts; // The days between workouts
    private int slowTwitchPercentage; // The percentage of slow twitch muscles
    private double muscleMass; // The mass of the muscles
    private double anabolicHormone; // The level of anabolic hormones
    private double catabolicHormone; // The level of catabolic hormones

    /**
     * Constructs a new Data object with the given parameters.
     * @param intensity The intensity of the workout.
     * @param lift Whether the person lifts or not.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     * @param muscleMass The mass of the muscles.
     * @param anabolicHormone The level of anabolic hormones.
     * @param catabolicHormone The level of catabolic hormones.
     */
    public Data(int intensity, boolean lift, double hoursOfSleep,
                int daysBetweenWorkouts, int slowTwitchPercentage, double muscleMass,
                double anabolicHormone, double catabolicHormone) {
        this.intensity = intensity;
        this.lift = lift;
        this.hoursOfSleep = hoursOfSleep;
        this.daysBetweenWorkouts = daysBetweenWorkouts;
        this.slowTwitchPercentage = slowTwitchPercentage;
        this.muscleMass = muscleMass;
        this.anabolicHormone = anabolicHormone;
        this.catabolicHormone = catabolicHormone;
    }

    /**
     * Returns the intensity of the workout.
     * @return The intensity of the workout.
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the workout.
     * @param intensity The intensity of the workout.
     */
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns whether the person lifts or not.
     * @return Whether the person lifts or not.
     */
    public boolean isLift() {
        return lift;
    }

    /**
     * Sets whether the person lifts or not.
     * @param lift Whether the person lifts or not.
     */
    public void setLift(boolean lift) {
        this.lift = lift;
    }

    /**
     * Returns the hours of sleep the person gets.
     * @return The hours of sleep the person gets.
     */
    public double getHoursOfSleep() {
        return hoursOfSleep;
    }

    /**
     * Sets the hours of sleep the person gets.
     * @param hoursOfSleep The hours of sleep the person gets.
     */
    public void setHoursOfSleep(double hoursOfSleep) {
        this.hoursOfSleep = hoursOfSleep;
    }

    /**
     * Returns the days between workouts.
     * @return The days between workouts.
     */
    public int getDaysBetweenWorkouts() {
        return daysBetweenWorkouts;
    }

    /**
     * Sets the days between workouts.
     * @param daysBetweenWorkouts The days between workouts.
     */
    public void setDaysBetweenWorkouts(int daysBetweenWorkouts) {
        this.daysBetweenWorkouts = daysBetweenWorkouts;
    }

    /**
     * Returns the percentage of slow twitch muscles.
     * @return The percentage of slow twitch muscles.
     */
    public int getSlowTwitchPercentage() {
        return slowTwitchPercentage;
    }

    /**
     * Sets the percentage of slow twitch muscles.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     */
    public void setSlowTwitchPercentage(int slowTwitchPercentage) {
        this.slowTwitchPercentage = slowTwitchPercentage;
    }

    /**
     * Returns the mass of the muscles.
     * @return The mass of the muscles.
     */
    public double getMuscleMass() {
        return muscleMass;
    }

    /**
     * Sets the mass of the muscles.
     * @param muscleMass The mass of the muscles.
     */
    public void setMuscleMass(double muscleMass) {
        this.muscleMass = muscleMass;
    }

    /**
     * Returns the level of anabolic hormones.
     * @return The level of anabolic hormones.
     */
    public double getAnabolicHormone() {
        return anabolicHormone;
    }

    /**
     * Sets the level of anabolic hormones.
     * @param anabolicHormone The level of anabolic hormones.
     */
    public void setAnabolicHormone(double anabolicHormone) {
        this.anabolicHormone = anabolicHormone;
    }

    /**
     * Returns the level of catabolic hormones.
     * @return The level of catabolic hormones.
     */
    public double getCatabolicHormone() {
        return catabolicHormone;
    }

    /**
     * Sets the level of catabolic hormones.
     * @param catabolicHormone The level of catabolic hormones.
     */
    public void setCatabolicHormone(double catabolicHormone) {
        this.catabolicHormone = catabolicHormone;
    }

    /**
     * Returns a string representation of the Data object.
     * @return A string representation of the Data object.
     */
    @Override
    public String toString() {
        return "Data{" +
                "intensity=" + intensity +
                ", lift=" + lift +
                ", hoursOfSleep=" + hoursOfSleep +
                ", daysBetweenWorkouts=" + daysBetweenWorkouts +
                ", slowTwitchPercentage=" + slowTwitchPercentage +
                ", muscleMass=" + muscleMass +
                ", anabolicHormone=" + anabolicHormone +
                ", catabolicHormone=" + catabolicHormone +
                '}';
    }
}
