package modelli;

import variEnum.Team;
import variEnum.TipoPedina;
import visualisation.VisualFrame;

import java.util.Scanner;

public class Simulazione {
    //
    public void iniziaSimulazione() {

        Scanner in = new Scanner(System.in);

        System.out.println("Numero di teams: ");
        int numeroDiTeams = in.nextInt();
        in.nextLine();

        System.out.println("Lato tabellone");
        int latoTabellone = in.nextInt();
        in.nextLine();

        Tabellone tabellone = new Tabellone(latoTabellone, numeroDiTeams);

        cicloSimulazione(tabellone);

        new VisualFrame(tabellone);
    }

    private void cicloSimulazione(Tabellone tabellone) {

        // aggiunta di latoTabellone*latoTabellone Cavalli di 2 team diversi
        for (int i = 0; i < tabellone.getLatoTabellone() * tabellone.getLatoTabellone(); i++) {

            Team team = switch (i % tabellone.getNumeroDiTeams()) {
                case 0 -> Team.BLACK;
                case 1 -> Team.RED;
                case 2 -> Team.BLUE;
                case 3 -> Team.MAGENTA;
                case 4 -> Team.GREEN;
                case 5 -> Team.YELLOW;
                default -> throw new IllegalStateException();
            };

            tabellone.aggiungiPedina(team, TipoPedina.CAVALLO);
        }


//  Aggiunta di 9 Cavalli di 3 team diversi
//        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
//        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
//            System.out.println("Pedina aggiunta con successo");
//            System.out.println("\n");
//        }
    }
}
