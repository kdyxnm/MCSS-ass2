public class Data {
    private int intensity;
    private boolean lift;
    private double hoursOfSleep;
    private int daysBetweenWorkouts;
    private int slowTwitchPercentage;
    private double muscleMass;
    private double anabolicHormone;
    private double catabolicHormone;

    public Data(int intensity, boolean lift, double hoursOfSleep, int daysBetweenWorkouts, int slowTwitchPercentage, double muscleMass, double anabolicHormone, double catabolicHormone) {
        this.intensity = intensity;
        this.lift = lift;
        this.hoursOfSleep = hoursOfSleep;
        this.daysBetweenWorkouts = daysBetweenWorkouts;
        this.slowTwitchPercentage = slowTwitchPercentage;
        this.muscleMass = muscleMass;
        this.anabolicHormone = anabolicHormone;
        this.catabolicHormone = catabolicHormone;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }

    public double getHoursOfSleep() {
        return hoursOfSleep;
    }

    public void setHoursOfSleep(double hoursOfSleep) {
        this.hoursOfSleep = hoursOfSleep;
    }

    public int getDaysBetweenWorkouts() {
        return daysBetweenWorkouts;
    }

    public void setDaysBetweenWorkouts(int daysBetweenWorkouts) {
        this.daysBetweenWorkouts = daysBetweenWorkouts;
    }

    public int getSlowTwitchPercentage() {
        return slowTwitchPercentage;
    }

    public void setSlowTwitchPercentage(int slowTwitchPercentage) {
        this.slowTwitchPercentage = slowTwitchPercentage;
    }

    public double getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(double muscleMass) {
        this.muscleMass = muscleMass;
    }

    public double getAnabolicHormone() {
        return anabolicHormone;
    }

    public void setAnabolicHormone(double anabolicHormone) {
        this.anabolicHormone = anabolicHormone;
    }

    public double getCatabolicHormone() {
        return catabolicHormone;
    }

    public void setCatabolicHormone(double catabolicHormone) {
        this.catabolicHormone = catabolicHormone;
    }

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
