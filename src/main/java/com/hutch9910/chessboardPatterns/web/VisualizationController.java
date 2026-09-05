package com.hutch9910.chessboardPatterns.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;

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
    public String index() {
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

    @GetMapping("/visualisation/desktop")
    public String desktopVisualisation() {
        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> new VisualFrame().createStartPanel());
        }
        return "redirect:/visualisation";
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
        model.addAttribute("setup", setup);
        model.addAttribute("board", new BoardView(boardGenerationService.generate(setup)));
        model.addAttribute("teamCount", boardGenerationService.countTeams(setup));
        model.addAttribute("pieceTypes", TypeOfPiece.values());
        model.addAttribute("teams", Team.values());
        return "visualisation";
    }
}