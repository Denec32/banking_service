package com.denec.clientservice.web;

import com.denec.clientservice.model.dto.TransactionCheckRequest;
import com.denec.clientservice.model.dto.TransactionCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
public class TransactionCheckWebClient extends BaseWebClient {

    @Value("${integration.resource}")
    private String resource;

    public TransactionCheckWebClient(WebClient webClient) {
        super(webClient);
    }

    public Optional<TransactionCheckResponse> check(Long id) {
        log.debug("Начало запроса с id {}", id);
        ResponseEntity<TransactionCheckResponse> post;
        TransactionCheckRequest request = TransactionCheckRequest.builder()
                .accountId(id)
                .build();

        post = this.post(
                uriBuilder -> uriBuilder.path(resource).build(),
                request,
                TransactionCheckResponse.class);

        log.debug("Окончание запроса с id {}", id);
        return Optional.ofNullable(post.getBody());
    }
}