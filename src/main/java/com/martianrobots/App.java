package com.martianrobots;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxX = scanner.nextInt();
        int maxY = scanner.nextInt();
        Grid grid = new Grid(maxX, maxY);
        SimulationRunner runner = new SimulationRunner(grid);
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String posLine = scanner.nextLine().trim();
            if (posLine.isEmpty()) continue;
            String[] parts = posLine.split("\\s+");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Direction dir = Direction.fromString(parts[2]);
            String instructions = scanner.nextLine().trim();
            System.out.println(runner.runRobot(new Position(x, y, dir), instructions));
        }
    }
}
