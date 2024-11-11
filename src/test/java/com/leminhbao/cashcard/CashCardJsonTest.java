package com.leminhbao.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        JsonContentAssert jsonContentAssert = assertThat(json.write(cashCard));

        // Compare the JSON content with the expected JSON content in the file "expected.json"
        jsonContentAssert.isStrictlyEqualToJson("expected.json");

        // Compare the JSON content with the expected JSON content in the file "expected.json" using JSON path
        jsonContentAssert.hasJsonPathNumberValue("@.id");
        jsonContentAssert.extractingJsonPathNumberValue("@.id").isEqualTo(99);

        // Compare the JSON content with the expected JSON content in the file "expected.json" using JSON path
        jsonContentAssert.hasJsonPathNumberValue("@.amount");
        jsonContentAssert.extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                        {
                            "id": 99,
                            "amount": 123.45
                        }
                        """;

        ObjectContent<CashCard> jsonContent = json.parse(expected);

        // Parse the JSON content and compare it with the expected CashCard object
        assertThat(jsonContent).isEqualTo(new CashCard( 99L, 123.45));

        // Parse the JSON content to a CashCard object and compare its properties with the expected values
        assertThat(json.parseObject(expected).id()).isEqualTo(99L);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}
