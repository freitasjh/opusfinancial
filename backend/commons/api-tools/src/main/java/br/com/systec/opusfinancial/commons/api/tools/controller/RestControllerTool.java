package br.com.systec.opusfinancial.commons.api.tools.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class RestControllerTool {

    private RestControllerTool() {
    }

    public static RestControllerTool of() {
        return new RestControllerTool();
    }

    public <T> ResponseEntity<T> buildSuccessResponseNoContent() {
        return ResponseEntity.noContent().build();
    }

    public <T> ResponseEntity<T> buildSuccessResponse(T object) {
        return ResponseEntity.ok(object);
    }

    public  <T> ResponseEntity<List<T>> buildSuccessResponse(List<T> object) {
        return ResponseEntity.ok(object);
    }

    public  <T> ResponseEntity<T> buildSuccessResponseCreated(T object, UriComponentsBuilder uriBuilder, String pathMapping, Long id) {
        URI uri = uriBuilder.path(pathMapping + "/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(object);
    }

    public  <T> ResponseEntity<T> buildSuccessResponseAccepted() {
        return ResponseEntity.accepted().build();
    }

    public  <T> ResponseEntity<T> buildBadRequest() {
        return ResponseEntity.badRequest().build();
    }
}