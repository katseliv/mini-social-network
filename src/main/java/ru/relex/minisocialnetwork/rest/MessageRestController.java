package ru.relex.minisocialnetwork.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.relex.minisocialnetwork.model.dto.MessageDto;
import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
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

    @Value("${authentication.defaultPrincipal}")
    private String defaultPrincipal;

    private final MessageService messageService;
    private final SecurityContextFacade securityContextFacade;

    @PostMapping
    public ResponseEntity<Integer> createMessage(@RequestBody @Valid final MessageDto messageDto) {
        final Authentication authentication = securityContextFacade.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(defaultPrincipal)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final int senderId = userDetailsDto.getId();
        final int id = messageService.createMessage(senderId, messageDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/history/{receiverId}")
    public ViewListPage<MessageForListDto> getMessageHistory(@PathVariable final int receiverId, @RequestParam(required = false) final Map<String, String> allParams) {
        final Authentication authentication = securityContextFacade.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(defaultPrincipal)) {
            return ViewListPage.<MessageForListDto>builder().build();
        }
        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final int senderId = userDetailsDto.getId();
        return messageService.getViewListPage(senderId, receiverId, allParams.get("page"), allParams.get("size"));
    }

}
