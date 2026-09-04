package com.hutch9910.chessboardPatterns.models;

import java.util.Scanner;

import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;
import com.hutch9910.chessboardPatterns.visualisation.VisualFrame;

public class Simulation {

    public void startSimulation() {

        Scanner in = new Scanner(System.in);

        VisualFrame visualFrame = new VisualFrame();

        visualFrame.createStartPanel();
        


        System.out.println(
            "\nType of pieces:" +
            "\nAntelope" +
            "\nDabbaba" +
            "\nDromedary" +
            "\nElephant" +
            "\nFerz" +
            "\nKnight" +
            "\nWazir" +
            "\nZebra"
        );


        System.out.print(
            "\nChoose the number of teams (MAX " +Team.values().length+ "): "
        );
        int numberOfTeams = in.nextInt();
        in.nextLine();

        System.out.println("Board side: ");
        int boardSide = in.nextInt();
        in.close();

        Board board = new Board(boardSide, numberOfTeams);

        System.out.print("\nBuilding the board...");
        simulationCycle(board);

        System.out.print("\nStarting the visualisation...");
        visualFrame.createBoardPanel(board);
    }

    private void simulationCycle(Board board) {

        // addition of sideBoard*sideBoard Pieces from different teams
        for (int i = 0; i < board.getBoardSide() * board.getBoardSide(); i++) {

            Team team = switch (i % board.getNumberOfTeams()) {
                case 0 -> Team.BLACK;
                case 1 -> Team.YELLOW;
                case 2 -> Team.BLUE;
                case 3 -> Team.MAGENTA;
                case 4 -> Team.GREEN;
                case 5 -> Team.YELLOW;
                default -> throw new IllegalStateException();
            };

            TypeOfPiece typeOfPiece;

            if (i%2 == 0) {
                typeOfPiece = TypeOfPiece.ELEPHANT;
            }
            else {
                typeOfPiece = TypeOfPiece.DROMEDARY;
            }
            board.addPiece(team, typeOfPiece);
        }
    }
}
