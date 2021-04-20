package source;

class Node {
    Sim sim;
    Node left;
    Node right;
    int Id;

    Node(Sim sim) {
        this.sim = sim;
        this.Id = sim.getId();
        right = null;
        left = null;
    }
}
