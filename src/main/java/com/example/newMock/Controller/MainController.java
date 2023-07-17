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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();



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

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000.00);
                responseDTO.setCurrency("US");
            }
            else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000.00);
                responseDTO.setCurrency("EU");
            }
            else {
                maxLimit = new BigDecimal(10000.00);
                responseDTO.setCurrency("RU");
            }

            int randomNum = ThreadLocalRandom.current().nextInt(0,maxLimit.intValue());
            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientID);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setBalance(Integer.toString(randomNum));
            responseDTO.setMaxLimit(maxLimit);
            log.error("***** Request ******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Response ******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;
        } catch(Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}