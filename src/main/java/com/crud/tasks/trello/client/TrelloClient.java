package com.crud.tasks.trello.client;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.user}")
    private String trelloUser;
    @Autowired
    private RestTemplate restTemplate;
    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(makeUrl(), TrelloBoardDto[].class);
        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }
    private URI makeUrl(){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + trelloUser)
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .queryParam("badges","all").build().encode().toUri();
        return  url;
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .queryParam("badges", "votes,attachmentsByType")
                .queryParam("votes", trelloCardDto.getBadges().getVotes())
                .queryParam("attachmentsByType", "trello")
                .queryParam("trello", "board,card")
                .queryParam("board", trelloCardDto.getBadges().getAttachmentByType().getTrello().getBoard())
                .queryParam("card", trelloCardDto.getBadges().getAttachmentByType().getTrello().getCard()).build().encode().toUri();
        System.out.println("GetBoard: "+ trelloCardDto.getBadges().getAttachmentByType().getTrello().getBoard());
        System.out.println("GetCard: "+trelloCardDto.getBadges().getAttachmentByType().getTrello().getCard());
        return restTemplate.postForObject(url,null,CreatedTrelloCard.class);
    }
}