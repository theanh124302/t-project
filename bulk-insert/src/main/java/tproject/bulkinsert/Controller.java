package tproject.bulkinsert;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/engineers")
@AllArgsConstructor
@Slf4j
public class EngineerController {

    private final EngineerService engineerService;

    @PostMapping("/batch")
    public ResponseEntity<String> batchInsertEngineers(@RequestParam(defaultValue = "10") int count) {
        log.info("Received request to batch insert {} engineers", count);

        // Create sample data
        List<EngineerEntity> engineers = engineerService.createSampleEngineers(count);

        // Perform batch insert
        engineerService.batchInsertEngineers(engineers);

        return ResponseEntity.ok("Successfully batch inserted " + count + " engineers");
    }

    @PostMapping("/batch-with-data")
    public ResponseEntity<String> batchInsertEngineersWithData(@RequestBody List<EngineerEntity> engineers) {
        log.info("Received request to batch insert {} engineers with provided data", engineers.size());

        // Perform batch insert
        engineerService.batchInsertEngineers(engineers);

        return ResponseEntity.ok("Successfully batch inserted " + engineers.size() + " engineers");
    }
}
