package ru.relex.minisocialnetwork.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.relex.minisocialnetwork.model.dto.MessageDto;
import ru.relex.minisocialnetwork.model.dto.view.MessageForListDto;
import ru.relex.minisocialnetwork.model.dto.view.ViewListPage;
import ru.relex.minisocialnetwork.service.MessageService;
import ru.relex.minisocialnetwork.utils.SecurityContextFacade;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessageRestController {

    private final MessageService messageService;
    private final SecurityContextFacade securityContextFacade;

    @PostMapping
    public ResponseEntity<Integer> createMessage(@RequestBody @Valid final MessageDto messageDto) {
        final String senderEmail = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        final int id = messageService.createMessage(senderEmail, messageDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/history/{receiverId}")
    public ViewListPage<MessageForListDto> getMessageHistory(@PathVariable final int receiverId, @RequestParam(required = false) final Map<String, String> allParams) {
        final String senderEmail = (String) securityContextFacade.getContext().getAuthentication().getPrincipal();
        return messageService.getViewListPage(senderEmail, receiverId, allParams.get("page"), allParams.get("size"));
    }

}
