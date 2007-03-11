package antomology;

public class Duration {
    private long startTime;

    private long finishTime;

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTime() {
        return finishTime - startTime;
    }

}
