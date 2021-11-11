package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.CampaignDTO;
import ar.edu.unq.virtuaula.exception.PlayerAccountNotFoundException;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.NewGameService;
import ar.edu.unq.virtuaula.service.CampaignService;
import ar.edu.unq.virtuaula.vo.CampaignVO;
import ar.edu.unq.virtuaula.vo.MissionVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CampaignRestController {

    private final NewGameService newGameService;
    private final CampaignService campaignService;
    private final AccountService accountService;

    @GetMapping("/campaigns/{newGameId}")
    @ApiResponse(code = 200, message = "Successfully request", response = CampaignVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all campaigns by new game id", notes = "Get all campaigns of a new game")
    public List<CampaignVO> getByNewGameId(@PathVariable("newGameId") Long newGameId) {
        return campaignService.getAllByNewGame(newGameService.findById(newGameId));
    }
    
    @GetMapping("/campaigns/{newGameId}/{accountId}")
    @ApiResponse(code = 200, message = "Successfully request" , response = CampaignVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all campaigns by clasroom id", notes = "Get all campaigns of a new game")
    public List<CampaignVO> getByNewGameIdAndPlayer(@PathVariable("newGameId") Long newGameId, @PathVariable("accountId") Long accountId) throws PlayerAccountNotFoundException {
        return campaignService.getAllByNewGameAndPlayer(newGameService.findById(newGameId), accountService.findPlayerById(accountId));
    }

    @PostMapping("/campaigns/{newGameId}/{missionId}/{accountId}")
    @ApiResponse(code = 200, message = "Successfully complete mission", response = CampaignVO.class)
    @ApiOperation(value = "Post complete mission for player by new game id, campaign id and list missions list", notes = "Post complete mission for a player")
    public CampaignVO completeMissions(@PathVariable("newGameId") Long newGameId, @PathVariable("missionId") Long missionId, @PathVariable("accountId") Long accountId, @RequestBody List<MissionVO> missions) throws Exception {
        NewGame newGame = newGameService.findById(newGameId);
        return campaignService.completeMissions(newGame, missionId, accountService.findPlayerById(accountId), missions);
    }
    
    @PostMapping("/campaigns/create/{newGameId}/{accountId}")   
    @ApiResponse(code = 200, message = "Successfully create campaign " , response = CampaignDTO.class)
    @ApiOperation(value = "Post create campaign for leader by new game id, account id and campaign", notes = "Post create campaign for a player")
    public CampaignDTO create(@PathVariable("newGameId") Long newGameId, @PathVariable("accountId") Long accountId, @RequestBody CampaignDTO campaign) throws Exception {
        return campaignService.create(newGameService.findById(newGameId), accountService.findLeaderById(accountId), campaign);
    }

}