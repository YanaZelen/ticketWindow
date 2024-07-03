package com.stm.kafka;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
public class MessageGenerator {

    private final Random random = new Random();

//    private final Faker faker = new Faker();
//
//    private JSONObject jsonObject;
//
//    private String clientId;
//
//    private String openBranchId;
//
//    private String closeBranchId;

//    public String generateAccountMessage() {
//        JSONObject jsonObject = new JSONObject();
//
//        String uniqueId = UUID.randomUUID().toString();
//        jsonObject.put("operationId", uniqueId);
//
//        String operationDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
//        jsonObject.put("operationDate", operationDate);
//
//        String operationTypeId = random.nextBoolean() ? "1" : "2";
//        jsonObject.put("operationTypeId", operationTypeId);
//
//        String amount = String.valueOf(random.nextInt(1_000_001));
//        jsonObject.put("amount", amount);
//
//        String operationBranchId = String.valueOf(random.nextInt(10) + 1);
//        jsonObject.put("operationBranchId", operationBranchId);
//
//        String userId = UUID.randomUUID().toString();
//        jsonObject.put("userId", userId);
//
//        String accountNumber = randomNumericString(20);
//        jsonObject.put("accountNumber", accountNumber);
//
//        String startDate = randomDateBetween("2020-01-01", operationDate);
//        jsonObject.put("startDate", startDate);
//
//        String openBranchId = random.nextBoolean() ? String.valueOf(random.nextInt(10) + 1) : null;
//        jsonObject.put("openBranchId", openBranchId);
//
//        String endDate = random.nextBoolean() ? randomDateBetween(startDate, operationDate) : null;
//        jsonObject.put("endDate", endDate);
//
//        String closeBranchId = random.nextBoolean() ? String.valueOf(random.nextInt(10) + 1) : null;
//        jsonObject.put("closeBranchId", closeBranchId);
//
//        String currency = String.valueOf(100 + random.nextInt(900));
//        jsonObject.put("currency", currency);
//
//        return jsonObject.toString();
//    }

    public String generateDepositOperationMessage() {
        JSONObject jsonObject = new JSONObject();

        String operationId = UUID.randomUUID().toString();
        jsonObject.put("operationId", operationId);

        String operationDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("operationDate", operationDate);

        String operationTypeId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationTypeId", operationTypeId);

        String amount = String.valueOf(random.nextInt(1_000_001));
        jsonObject.put("amount", amount);

        String operationBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationBranchId", operationBranchId);

        String clientId = UUID.randomUUID().toString();
        jsonObject.put("clientId", clientId);

        String depositNumber = randomNumericString(20);
        jsonObject.put("depositNumber", depositNumber);

        String startDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("startDate", startDate);

        String openBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("openBranchId", openBranchId);

        String endDate = random.nextBoolean() ? randomDateBetween(startDate, LocalDate.now().toString()) : null;
        jsonObject.put("endDate", endDate);

        String closeBranchId = random.nextBoolean() ? String.valueOf(random.nextInt(10) + 1) : null;
        jsonObject.put("closeBranchId", closeBranchId);

        String currencyNumber = String.valueOf(100 + random.nextInt(900));
        jsonObject.put("currencyNumber", currencyNumber);

        return jsonObject.toString();
    }

    public String generateDepositLossMessage() {
        JSONObject jsonObject = new JSONObject();

        String depositId = UUID.randomUUID().toString();
        jsonObject.put("depositId", depositId);

        String currencyNumber = String.valueOf(100 + random.nextInt(900));
        jsonObject.put("currencyNumber", currencyNumber);

        String operationDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("operationDate", operationDate);

        String operationBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationBranchId", operationBranchId);

        String depositRate = String.valueOf(random.nextDouble() * 10);
        jsonObject.put("depositRate", depositRate);

        String depositPayment = String.valueOf(random.nextInt(1_000_001));
        jsonObject.put("depositPayment", depositPayment);

        return jsonObject.toString();
    }

    public String generateAccountOperationMessage() {
        JSONObject jsonObject = new JSONObject();

        String operationId = UUID.randomUUID().toString();
        jsonObject.put("operationId", operationId);

        String operationDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("operationDate", operationDate);

        String operationTypeId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationTypeId", operationTypeId);

        String amount = String.valueOf(random.nextInt(1_000_001));
        jsonObject.put("amount", amount);

        String operationBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationBranchId", operationBranchId);

        String accountId = UUID.randomUUID().toString();
        jsonObject.put("accountId", accountId);

        String accountNumber = randomNumericString(20);
        jsonObject.put("accountNumber", accountNumber);

        String startDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("startDate", startDate);

        String openBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("openBranchId", openBranchId);

        String endDate = random.nextBoolean() ? randomDateBetween(startDate, LocalDate.now().toString()) : null;
        jsonObject.put("endDate", endDate);

        String closeBranchId = random.nextBoolean() ? String.valueOf(random.nextInt(10) + 1) : null;
        jsonObject.put("closeBranchId", closeBranchId);

        String currencyNumber = String.valueOf(100 + random.nextInt(900));
        jsonObject.put("currencyNumber", currencyNumber);

        return jsonObject.toString();
    }

    public String generateAccountFeeMessage() {
        JSONObject jsonObject = new JSONObject();

        String accountNumber = randomNumericString(20);
        jsonObject.put("accountNumber", accountNumber);

        String currencyNumber = String.valueOf(100 + random.nextInt(900));
        jsonObject.put("currencyNumber", currencyNumber);

        String operationDate = randomDateBetween("2023-01-01", LocalDate.now().toString());
        jsonObject.put("operationDate", operationDate);

        String operationBranchId = String.valueOf(random.nextInt(10) + 1);
        jsonObject.put("operationBranchId", operationBranchId);

        String amountFee = String.valueOf(random.nextInt(1_000_001));
        jsonObject.put("amountFee", amountFee);

        return jsonObject.toString();
    }

    private String randomDateBetween(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = startEpochDay + random.nextLong() % (endEpochDay - startEpochDay + 1);
        return LocalDate.ofEpochDay(randomDay).toString();
    }

    private String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}