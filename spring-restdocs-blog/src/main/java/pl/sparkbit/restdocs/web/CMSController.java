package pl.sparkbit.restdocs.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.sparkbit.restdocs.domain.Document;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Tomasz Kopczynski
 */

@RestController
@RequestMapping("/cms")
public class CMSController {

    private ConcurrentMap<Long, Document> data = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(3);

    @PostConstruct
    public void init() {
        data.put(1L, new Document("Harry Smith", "Meeting report"));
        data.put(2L, new Document("Jack Williams", "Board meeting presentation"));
    }

    @RequestMapping(value = "/document", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createDocument(@RequestBody Document document) {
        if (document != null) {
            long index = counter.getAndIncrement();
            data.put(index, document);

            return new ResponseEntity<>(Collections.singletonMap("id", index), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/document/{id}")
    public ResponseEntity<Document> retrieveDocument(@PathVariable("id") Long id) {
        Document document = data.get(id);

        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> status() {
        return Collections.singletonMap("status", "OK");
    }
}
