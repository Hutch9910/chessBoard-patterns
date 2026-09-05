package com.hutch9910.chessboardPatterns.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;

import com.hutch9910.chessboardPatterns.models.Board;
import com.hutch9910.chessboardPatterns.visualisation.VisualFrame;

import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

@Controller
public class VisualizationController {

    private final BoardGenerationService boardGenerationService;

    public VisualizationController(BoardGenerationService boardGenerationService) {
        this.boardGenerationService = boardGenerationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        VisualizationSetup landingSetup = VisualizationSetup.defaults();
        landingSetup.setBoardSide(5);
        model.addAttribute("landingBoard", new BoardView(boardGenerationService.generate(landingSetup)));
        return "index";
    }

    @GetMapping("/visualisation")
    public String visualisation(Model model) {
        return renderWorkspace(model, VisualizationSetup.defaults());
    }

    @PostMapping("/visualisation")
    public String generate(@ModelAttribute VisualizationSetup setup, Model model) {
        return renderWorkspace(model, setup);
    }

    @PostMapping("/visualisation/desktop")
    public String desktopVisualisation(@ModelAttribute VisualizationSetup setup, Model model) {
        if (GraphicsEnvironment.isHeadless()) {
            return renderWorkspace(model, setup, new BoardView(boardGenerationService.emptyBoard(setup)));
        }

        try {
            Board board = boardGenerationService.generateDesktop(setup);
            SwingUtilities.invokeLater(() -> new VisualFrame().createBoardPanel(board));
        } catch (IllegalArgumentException exception) {
            return renderWorkspace(model, setup, new BoardView(boardGenerationService.emptyBoard(setup)));
        }
        return renderWorkspace(model, setup, new BoardView(boardGenerationService.emptyBoard(setup)));
    }

    @GetMapping("/attack-range")
    public String attackRange(Model model) {
        model.addAttribute("pieceTypes", TypeOfPiece.values());
        model.addAttribute("selectedPiece", TypeOfPiece.KNIGHT);
        model.addAttribute("attack", new AttackRangeView(TypeOfPiece.KNIGHT));
        return "attack-range";
    }

    @GetMapping("/attack-range/{piece}")
    public String attackRange(@org.springframework.web.bind.annotation.PathVariable TypeOfPiece piece, Model model) {
        model.addAttribute("pieceTypes", TypeOfPiece.values());
        model.addAttribute("selectedPiece", piece);
        model.addAttribute("attack", new AttackRangeView(piece));
        return "attack-range";
    }

    private String renderWorkspace(Model model, VisualizationSetup setup) {
        return renderWorkspace(model, setup, new BoardView(boardGenerationService.generate(setup)));
    }

    private String renderWorkspace(Model model, VisualizationSetup setup, BoardView board) {
        model.addAttribute("setup", setup);
        model.addAttribute("board", board);
        model.addAttribute("pieceTypes", TypeOfPiece.values());
        model.addAttribute("teams", Team.values());
        return "visualisation";
    }
}