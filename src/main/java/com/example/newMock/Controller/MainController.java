package com.example.newMock.Controller;
import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();
    public long start_time = 0L;


    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String clientID = requestDTO.getClientId();
            char firstDigit = clientID.charAt(0);
            BigDecimal maxLimit;
            int randomNum;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("US");
            }
            else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("EU");
            }
            else {
                maxLimit = new BigDecimal(10000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("RU");
            }

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientID);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setBalance(Integer.toString(randomNum));
            responseDTO.setMaxLimit(maxLimit);
            log.error("***** Request ****** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Response ****** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            long pacing = ThreadLocalRandom.current().nextLong(100, 500);
            long end_time = System.currentTimeMillis();
            if (end_time - start_time < pacing) {
                Thread.sleep(pacing - (end_time - start_time));
            }

            return responseDTO;
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            value = "/info/getBalances",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getBalances(@RequestBody RequestDTO requestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String clientID = requestDTO.getClientId();
            char firstDigit = clientID.charAt(0);
            BigDecimal maxLimit;
            int randomNum;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("US");
            }
            else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("EU");
            }
            else {
                maxLimit = new BigDecimal(10000.00);
                randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
                responseDTO.setCurrency("RU");
            }

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientID);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setBalance(Integer.toString(randomNum));
            responseDTO.setMaxLimit(maxLimit);
            log.error("\n***** Request ******\n " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("\n***** Response ******\n " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}