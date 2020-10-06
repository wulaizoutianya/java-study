package test;

public class P {

    private int i;
    private String n;

    public P(int i, String n) {
        this.i = i;
        this.n = n;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        P p = (P)obj;
        return this.i == p.i;
    }

    /*@Override
    public int hashCode() {
        return (this.i + this.n).hashCode();
    }*/
}
