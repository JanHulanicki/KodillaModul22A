package com.crud.tasks.controller;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;
    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        Optional.ofNullable(trelloBoards)
                .orElse(Collections.emptyList())
                .forEach(trelloBoardDto -> {
                    if((trelloBoardDto.getName().contains("Kodilla"))) {
                        System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                    }
                });
            //zadanie podpunkt 3
            //trelloBoards.forEach(trelloBoardDto -> {
            //  if((trelloBoardDto.getId()!=null)&&
            //       (trelloBoardDto.getName()!=null)&&
            //       (trelloBoardDto.getName().contains("Kodilla"))) {
            //       System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
            //     }
            // });
    }
}
