public class Patch {
    private double anabolicHormone;
    private double catabolicHormone;
    private static final double DIFFUSE_RATE = 0.75;
    private static final double ANABOLIC_HORMONE_MAX = 200.0;
    private static final double ANABOLIC_HORMONE_MIN = 50.0;
    private static final double CATABOLIC_HORMONE_MAX = 250.0;
    private static final double CATABOLIC_HORMONE_MIN = 52.0;

    public Patch() {
        this.anabolicHormone = 50.0;  // default starting value
        this.catabolicHormone = 52.0; // default starting value
        // No need to regulate hormones here, since they are already within the allowed limits
        //regulateHormones();
    }

    public Patch(double anabolicHormone, double catabolicHormone) {
        this.anabolicHormone = anabolicHormone;
        this.catabolicHormone = catabolicHormone;
    }

    public void diffuse(Patch[][] grid, int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        double anabolicShare = (anabolicHormone * DIFFUSE_RATE) / 8.0;
        double catabolicShare = (catabolicHormone * DIFFUSE_RATE) / 8.0;

        double anabolicRemaining = anabolicHormone;
        double catabolicRemaining = catabolicHormone;

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length) {
                grid[nx][ny].receiveHormones(anabolicShare, catabolicShare);
                anabolicRemaining -= anabolicShare;
                catabolicRemaining -= catabolicShare;
            }
        }

        // Set the remaining hormone levels back to the patch
        this.anabolicHormone = anabolicRemaining;
        this.catabolicHormone = catabolicRemaining;
    }

    public void updateHormonesFromDailyActivities(double fiberSize) {
        double logFiberSize = Math.log10(fiberSize);
        anabolicHormone += 2.5 * logFiberSize;
        catabolicHormone += 2.0 * logFiberSize;
    }

    public void updateHormonesFromWeightLifting(double fiberSize, int intensity) {
        double probability = Math.pow(intensity / 100.0, 2);
        if (Math.random() * 1.0 < probability) {
            double logFiberSize = Math.log10(fiberSize);
            anabolicHormone += logFiberSize * 55.0;
            catabolicHormone += logFiberSize * 44.0;
        }
    }

    public void updateHormonesFromSleep(double hoursOfSleep) {
        anabolicHormone -= 0.48 * Math.log10(anabolicHormone) * hoursOfSleep;
        catabolicHormone -= 0.5 * Math.log10(catabolicHormone) * hoursOfSleep;
    }

    public void balanceHormones() {
        anabolicHormone = Math.max(ANABOLIC_HORMONE_MIN, Math.min(this.anabolicHormone, ANABOLIC_HORMONE_MAX));
        catabolicHormone = Math.max(CATABOLIC_HORMONE_MIN, Math.min(this.catabolicHormone, CATABOLIC_HORMONE_MAX));
    }

    public void receiveHormones(double anabolic, double catabolic) {
        this.anabolicHormone += anabolic;
        this.catabolicHormone += catabolic;
    }

    public double getAnabolicHormone() {
        return anabolicHormone;
    }

    public double getCatabolicHormone() {
        return catabolicHormone;
    }

    public void setAnabolicHormone(double v) {
        this.anabolicHormone = v;
    }

    public void setCatabolicHormone(double v) {
        this.catabolicHormone = v;
    }
}

