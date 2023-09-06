public class deal {
    private String p1id;
    private String p2id;
    private int prime;
    private int elite;
    private int normal;

    public String getP1id() {
        return p1id;
    }

    public String getP2id() {
        return p2id;
    }

    public int getPrime() {
        return prime;
    }

    public int getElite() {
        return elite;
    }

    public int getNormal() {
        return normal;
    }

    public deal(String p1id, String p2id, int prime, int elite, int normal) {
        this.p1id = p1id;
        this.p2id = p2id;
        this.prime = prime;
        this.elite = elite;
        this.normal = normal;
    }
}
