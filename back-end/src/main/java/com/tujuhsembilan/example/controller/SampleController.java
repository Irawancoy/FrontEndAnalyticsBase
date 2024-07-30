package com.tujuhsembilan.example.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.example.exception.MultipleException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sample")
public class SampleController {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class SampleRequestBody {
    @Size(max = 10)
    private String input;

    @NotBlank
    private String mustHaveSomething;
  }

  @PostMapping("/post")
  public ResponseEntity<?> postSomething(@Valid @RequestBody SampleRequestBody body) {
    throw new EntityNotFoundException("Test");
  }

  @GetMapping("/multiple-exception")
  public ResponseEntity<?> multipleException() {
    throw new MultipleException(new EntityNotFoundException("Test1"), new IndexOutOfBoundsException("Test2"));
  }

  @PostMapping("/logError")
  public ResponseEntity<String> logError(@RequestBody Map<String, Object> errorDetails) {

    @SuppressWarnings("unchecked")
    //mengambil errorInfo dari errorDetails
    Map<String, Object> errorInfo = (Map<String, Object>) errorDetails.get("errorInfo");
    
    //mengambil data dari errorInfo
    String componentStack = (String) errorInfo.get("componentStack");
    String message = (String) errorInfo.get("message");
    String stack = (String) errorInfo.get("stack");

    //menampilkan error ke log 
    log.error("Component Stack: {}", componentStack);
    log.error("Message: {}", message);
    log.error("Stack: {}", stack);

    return ResponseEntity.ok("Logged");

  }

}
