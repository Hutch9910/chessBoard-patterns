package modelli;

import variEnum.Team;
import variEnum.TipoPedina;

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

        Tabellone tabellone = new Tabellone(numeroDiTeams, latoTabellone);


        cicloSimulazione(tabellone);
    }

    private void cicloSimulazione(Tabellone tabellone) {

        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.ROSSO, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.BLU, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
        if (tabellone.aggiungiPedina(Team.MAGENTA, TipoPedina.CAVALLO)) {
            System.out.println("Pedina aggiunta con successo");
            System.out.println("\n");
        }
    }
}
