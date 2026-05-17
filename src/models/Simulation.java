package models;

import variousEnum.Team;
import variousEnum.TypeOfPiece;
import visualisation.VisualFrame;

import java.util.Scanner;

public class Simulation {

    public void startSimulation() {

        Scanner in = new Scanner(System.in);

        System.out.println("Number of teams: ");
        int numberOfTeams = in.nextInt();
        in.nextLine();

        System.out.println("Board side: ");
        int boardSide = in.nextInt();
        in.close();

        Board board = new Board(boardSide, numberOfTeams);

        simulationCycle(board);

        new VisualFrame(board);
    }

    private void simulationCycle(Board board) {

        // addition of sideBoard*sideBoard Pieces from different teams
        for (int i = 0; i < board.getBoardSide() * board.getBoardSide(); i++) {

            Team team = switch (i % board.getNumberOfTeams()) {
                case 0 -> Team.BLACK;
                case 1 -> Team.RED;
                case 2 -> Team.BLUE;
                case 3 -> Team.MAGENTA;
                case 4 -> Team.GREEN;
                case 5 -> Team.YELLOW;
                default -> throw new IllegalStateException();
            };

            board.addPiece(team, TypeOfPiece.KNIGHT);
        }
    }
}
