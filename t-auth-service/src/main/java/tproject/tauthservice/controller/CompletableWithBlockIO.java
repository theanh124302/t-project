package tproject.tauthservice.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tauthservice.repository.nonblockingrepo.BlockingUserRepository;

@Slf4j
@RestController
@RequestMapping("/blocking")
@AllArgsConstructor
public class CompletableWithBlockIO {

    private final BlockingUserRepository blockingUserRepository;

    @GetMapping()
    public void get() {
        log.info("Blocking IO thread: {}", Thread.currentThread().getName());
        blockingUserRepository.findAll();
        log.info("Blocking IO thread: {}", Thread.currentThread().getName());
    }

}
