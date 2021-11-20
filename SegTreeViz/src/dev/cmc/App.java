package dev.cmc;

public class App {
    public static void main(String[] args) throws Exception {
        // 49, 49, 49 -> Background
        // 81, 172, 51 -> Segment Tree
        SegTreeViz stv = new SegTreeViz("SegTreeViz", 800, 800, 60);
        stv.run();
    }
}
