package reactive_sum.imperative;

public class ReactiveSumModel {
    private int a;
    private int b;
    private boolean aUpdated = true;
    private boolean bUpdated = true;

    public boolean isSumUpdated() {
        return aUpdated && bUpdated;
    }

    public int getSum() {
        return getA() + getB();
    }

    public void setA(int aUpdate) {
        if(aUpdate != a) {
            this.a = aUpdate;
            aUpdated = true;
            return;
        }

        aUpdated = false;
    }

    public int getA() {
        return a;
    }

    public void setB(int bUpdate) {
        if(bUpdate != b) {
            this.b = bUpdate;
            bUpdated = true;
            return;
        }

        bUpdated = false;
    }

    public int getB() {
        return b;
    }
}