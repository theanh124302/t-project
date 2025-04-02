package tproject.tauthservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tauthservice.repository.blockingrepository.NonBlockingUserRepository;

@Slf4j
@RestController
@RequestMapping("/non-blocking")
@AllArgsConstructor
public class CompletableWithNonBlockingIO {

     @GetMapping()
     public void get() {
         log.info("Non-blocking IO thread: {}", Thread.currentThread().getName());
//         nonBlockingUserRepository.findAll();
         log.info("Non-blocking IO thread: {}", Thread.currentThread().getName());
     }
}
